package com.cynovan.neptune.oauth.base.message.dto;

public class MessageDataDto {
    private String name;
    private String data;

    public MessageDataDto(String _name, String _data) {
        this.name = _name;
        this.data = _data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
