package com.cynovan.neptune.oauth.base.message.enums;

public enum MessageDtoLevel {
    alert("alert"), warning("warning"), error("error");

    private String value;

    MessageDtoLevel(String _value) {
        setValue(_value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
