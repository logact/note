package com.cynovan.neptune.oauth.base.utils.jsonfilter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

@JsonFilter(value = "Filter1")
public class JsonPropertyFilter1 extends SimpleBeanPropertyFilter {

}
