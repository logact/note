package com.cynovan.neptune.oauth.base.utils.bean;

import com.cynovan.neptune.oauth.base.utils.NumberLib;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CnvNumberSerializer extends JsonSerializer<Number> {

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if (value instanceof Float || value instanceof Double) {
            gen.writeString(NumberLib.formatDouble(value));
        } else if (value instanceof Integer) {
            gen.writeNumber(StringLib.toInteger(value));
        } else {
            gen.writeNumber(StringLib.toLong(value));
        }
    }

}
