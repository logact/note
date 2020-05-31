package com.logact.common.utils;

import java.util.UUID;

/**
 * @author: logact
 * @date: Created in 2020/5/25 10:39
 * @description:
 */

public class Uuid {
    public static String generate(){
        return UUID.randomUUID().toString();
    }
}
