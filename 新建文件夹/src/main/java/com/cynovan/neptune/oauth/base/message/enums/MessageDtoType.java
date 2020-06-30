package com.cynovan.neptune.oauth.base.message.enums;

public enum MessageDtoType {
    alert("alert"), deviceState("deviceStateChange"),
    dataSpeed("deviceDataSpeedException"),
    dataAnalysis("deviceDataAnalysisException"), dataTrigger("dataTrigger"), dataPush("dataPush"), deviceDataStorage("deviceDataStorage");

    private String value;

    MessageDtoType(String _value) {
        setValue(_value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
