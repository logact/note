package com.cynovan.neptune.oauth.base.jms.document;

import com.cynovan.neptune.oauth.base.database.BaseJDO;

public class QMailMessage extends BaseJDO {

    public static final String collectionName = "mailMessage";

    public static final String from = "from";
    public static final String text = "text";
    public static final String replyTo = "replyTo";
    public static final String to = "to";
    public static final String cc = "cc";
    public static final String bcc = "bcc";
    public static final String sentDate = "sentDate";
    public static final String subject = "subject";
}
