package com.cynovan.neptune.oauth.base.jms.document.base;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * Created by Aric on 2016/11/22.
 */

public abstract class MQMessage implements java.io.Serializable, IMQMessage {

    @Id
    private String id;

    private String from_userid;
    @Indexed
    private String to_userid;

    @LastModifiedDate
    private Date update_date;

    @CreatedDate
    private Date create_date;

    private String text;

    public void prePersist() {
        this.create_date = new Date();
        this.update_date = update_date;
    }

    public void preUpdate() {
        this.update_date = update_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getFrom_userid() {
        return from_userid;
    }

    public void setFrom_userid(String from_userid) {
        this.from_userid = from_userid;
    }

    public String getTo_userid() {
        return to_userid;
    }

    public void setTo_userid(String to_userid) {
        this.to_userid = to_userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
