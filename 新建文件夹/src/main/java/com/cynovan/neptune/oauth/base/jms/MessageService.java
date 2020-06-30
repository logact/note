package com.cynovan.neptune.oauth.base.jms;

import com.cynovan.neptune.oauth.base.arch.base.controller.BaseService;
import com.cynovan.neptune.oauth.base.executor.ExecutorService;
import com.cynovan.neptune.oauth.base.jms.document.QMailMessage;
import com.cynovan.neptune.oauth.base.utils.DBUtilsNoCompany;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;
import com.cynovan.neptune.oauth.base.utils.FreeMarkerLib;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Aric on 2016/11/21.
 */
@Service
public class MessageService extends BaseService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(Document message, Map<String, Object> freemarkerMap) {
        String imgAsBase64 = "";
        try {
            String bannerImg = "com/cynovan/addons/index/web/img/banner.png";
            ClassPathResource resource = new ClassPathResource(bannerImg);
            byte[] imgBytes = IOUtils.toByteArray(resource.getInputStream());
            byte[] imgBytesAsBase64 = Base64.encodeBase64(imgBytes);
            String imgDataAsBase64 = new String(imgBytesAsBase64);
            imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
        } catch (IOException e) {
            e.printStackTrace();
        }
        freemarkerMap.put("url", imgAsBase64);
        String content = FreeMarkerLib.renderSysTemplate("email_template", freemarkerMap);
        message.put(QMailMessage.text, content);
        this.sendMail(message);
    }

    private String[] toStringArray(List list) {
        List<String> arrList = Lists.newArrayList();
        if (list != null && list.size() > 0) {
            list.stream().forEach(item -> {
                String str = StringLib.toString(item);
                arrList.add(str);
            });
        }
        return arrList.toArray(new String[arrList.size()]);
    }

    private void appendMailTemplate(Document message) {
        String imgAsBase64 = "";
        try {
            String bannerImg = "com/cynovan/addons/index/web/img/banner.png";
            ClassPathResource resource = new ClassPathResource(bannerImg);
            byte[] imgBytes = IOUtils.toByteArray(resource.getInputStream());
            byte[] imgBytesAsBase64 = Base64.encodeBase64(imgBytes);
            String imgDataAsBase64 = new String(imgBytesAsBase64);
            imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> freemarkerMap = Maps.newHashMap();
        freemarkerMap.put("url", imgAsBase64);
        freemarkerMap.put("message", DocumentLib.getString(message, QMailMessage.text));
        String text = FreeMarkerLib.renderSysTemplate("email_template", freemarkerMap);
        message.put(QMailMessage.text, text);
    }

    @Value("${username}")
    private String smtpUsername;

    public void sendMail(Document message) {
        appendMailTemplate(message);
        DBUtilsNoCompany.save(QMailMessage.collectionName, message);
        try {
            String[] to = toStringArray(DocumentLib.getList(message, QMailMessage.to));
            String text = DocumentLib.getString(message, QMailMessage.text);
            if (to != null && to.length > 0) {
                for (int i = 0; i < to.length; i++) {
                    String email = to[i];
                    if (StringLib.isNotEmpty(email)) {
                        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                        helper.setFrom(smtpUsername);
                        helper.setSubject(DocumentLib.getString(message, QMailMessage.subject));
                        helper.setText("", text);

                        helper.setTo(email);

                        String replyTo = DocumentLib.getString(message, QMailMessage.replyTo);
                        if (StringLib.isNotEmpty(replyTo)) {
                            helper.setReplyTo(replyTo);
                        }
                        ExecutorService.submit(new Callable() {
                            @Override
                            public Object call() throws Exception {
                                javaMailSender.send(helper.getMimeMessage());
                                return null;
                            }
                        });
                    }
                }
            }
        } catch (MessagingException e) {
            logger.error("Send mail error " + e.getMessage());
        }
    }
}
