package com.cynovan.neptune.oauth.base.config.bean;

import com.cynovan.neptune.oauth.base.arch.jdo.QTemplate;
import com.cynovan.neptune.oauth.base.utils.DBUtilsNoCompany;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;
import com.cynovan.neptune.oauth.base.utils.JsonLib;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bson.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Aric on 2016/11/8.
 */
public class InitializeService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private Set<String> jsFiles;
    private Set<String> cssFiles;

    public void initialize() {
        scanAllFiles();
        InitializeData.removeAllCache();
        initializeTemplate();
    }

    private void scanAllFiles() {
        jsFiles = scanFile("com.cynovan.addons", ".*\\.js");
        cssFiles = scanFile("com.cynovan.addons", ".*\\.css");
    }

    private String processRequireJSPath(String folder, String file, Boolean isApp) {
        isApp = (isApp == null) ? false : isApp;
        StringBuilder builder = new StringBuilder();
        if (StringLib.contains(file, ".css")) {
            builder.append("css!");
        }
        if (!StringLib.contains(file, folder + "/")) {
            if (isApp) {
                builder.append("apps/");
            }
            builder.append(folder);
            builder.append("/");
        }
        if (StringLib.indexOf(file, ".") != -1) {
            builder.append(StringLib.substring(file, 0, StringLib.indexOf(file, ".")));
        } else {
            builder.append(file);
        }
        String path = builder.toString();
        path = StringLib.replace(path, "//", "/");
        return path;
    }

    private String processResourcePath(String folder, String file) {
        StringBuilder builder = new StringBuilder();
        if (!StringLib.contains(file, folder + "/")) {
            builder.append("apps/");
            builder.append(folder);
            builder.append("/");
        }
        builder.append(file);
        String path = builder.toString();
        path = StringLib.replace(path, "//", "/");
        return path;
    }

    public void initializeTemplate() {
        DBUtilsNoCompany.deleteMany(QTemplate.collectionName);

        Set<String> sets = scanFile("com.cynovan", ".*\\.html");
        List<Document> templateList = Lists.newArrayList();
        HtmlCompressor compressor = new HtmlCompressor();
        sets.stream().forEach(path -> {
            try {
                ClassPathResource resource = new ClassPathResource(path);
                InputStream stream = resource.getInputStream();
                String str = IOUtils.toString(stream, StringLib.UTF_8);
                org.jsoup.nodes.Document doc = Jsoup.parse(str, StringLib.UTF_8);
                Elements elements = doc.getElementsByTag("template");
                if (elements != null && elements.size() > 0) {
                    Iterator<Element> tor = elements.iterator();
                    while (tor.hasNext()) {
                        Element element = tor.next();
                        Document template = DocumentLib.newDoc();
                        template.put(QTemplate.name, element.attr("name"));
                        String html = StringEscapeUtils.unescapeHtml4(element.html());
                        html = compressor.compress(html);
                        template.put(QTemplate.template, html);
                        templateList.add(template);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        DBUtilsNoCompany.insertMany(QTemplate.collectionName, templateList);
    }

    public String getTemplateSecurity() {
        Set<String> sets = scanFile("com.cynovan.addons.company.security", ".*\\.json");

        JsonNode securityNode = JsonLib.createObjNode();
        if (CollectionUtils.isNotEmpty(sets)) {
            Iterator iter = sets.iterator();
            String str = StringLib.toString(iter.next());
            try {
                ClassPathResource resource = new ClassPathResource(str);
                InputStream stream = resource.getInputStream();
                String jsonStr = IOUtils.toString(stream, StringLib.UTF_8);

                JsonNode jsonNode = JsonLib.parseJSON(jsonStr);
                securityNode = jsonNode.get("defaultSecurity");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return JsonLib.toString(securityNode);

    }


    private Set<String> scanFile(String path, String filePattern) {
        Reflections reflections = new Reflections(path, new ResourcesScanner());
        Set<String> sets = reflections.getResources(Pattern.compile(filePattern));
        return sets;
    }

    private List transferDependList(List dependList, String folderName, Boolean isApp) {
        Set transferDependSet = Sets.newHashSet();
        // 相对路径
        String relativePath = StringLib.join("com/cynovan/addons/", folderName, "/");
        if (dependList != null && dependList.size() > 0) {
            dependList.forEach(obj -> {
                String item = StringLib.toString(obj);

                if (StringLib.endsWithAny(item, ".css", ".js")) {
                    item = processRequireJSPath(folderName, item, isApp);
                    transferDependSet.add(item);
                } else {
                    // 绝对路径
                    String absPath = StringLib.join(relativePath, item);

                    // 将 路径下的所有 js 转换成 requirejs 可用
                    jsFiles.stream().filter(str -> StringLib.startsWith(str, absPath)).forEach(js -> {
                        transferDependSet.add(processRequireJSPath(folderName, js.replace(relativePath, ""), isApp));
                    });
                    // 将 路径下的所有 css 转换成 requirejs 可用
                    cssFiles.stream().filter(str -> StringLib.startsWith(str, absPath)).forEach(css -> {
                        transferDependSet.add(processRequireJSPath(folderName, css.replace(relativePath, ""), isApp));
                    });
                }

            });
        }
        return Lists.newArrayList(transferDependSet);
    }
}
