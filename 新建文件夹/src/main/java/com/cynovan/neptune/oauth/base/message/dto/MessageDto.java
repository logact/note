package com.cynovan.neptune.oauth.base.message.dto;

import com.cynovan.neptune.oauth.base.database.BaseJDO;
import com.cynovan.neptune.oauth.base.message.enums.MessageDtoLevel;
import com.cynovan.neptune.oauth.base.message.enums.MessageDtoType;
import com.cynovan.neptune.oauth.base.utils.JsonLib;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bson.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageDto extends BaseJDO {

    @JsonIgnore
    public static final String collectionName = "messageJdo";

    private List<MessageLinkDto> linkList = Lists.newArrayList();
    private List<MessageDataDto> dataList = Lists.newArrayList();

    private Map<String, Object> params = Maps.newHashMap();

    @Override
    public void createIndex() {

    }

    private String group_id;

    private String subject;
    private String content;
    private String company_id;

    private String module;
    private String data_id;

    private Boolean team = false;

    private Date create_date = new Date();
    private Date read_date;
    private String read_user;
    private Boolean read = false;

    private String level = MessageDtoLevel.alert.getValue();
    private String type = MessageDtoType.alert.getValue();

    public static MessageDto newInstance() {
        return new MessageDto();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MessageLinkDto> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<MessageLinkDto> linkList) {
        this.linkList = linkList;
    }

    public void addLink(String name, String url) {
        this.linkList.add(new MessageLinkDto(name, url));
    }

    public List<MessageDataDto> getDataList() {
        return dataList;
    }

    public void setDataList(List<MessageDataDto> dataList) {
        this.dataList = dataList;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addData(String name, String data) {
        this.dataList.add(new MessageDataDto(name, data));
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getRead_date() {
        return read_date;
    }

    public void setRead_date(Date read_date) {
        this.read_date = read_date;
    }

    public String getRead_user() {
        return read_user;
    }

    public void setRead_user(String read_user) {
        this.read_user = read_user;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Document toDocument() {
        JsonNode dataNode = JsonLib.toJSON(this);
        return Document.parse(dataNode.toString());
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void addParam(String key, Object value) {
        params.put(key, value);
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public Boolean getTeam() {
        return team;
    }

    public void setTeam(Boolean team) {
        this.team = team;
    }
}
