package com.company.mx.framework.utils;

import static java.util.UUID.randomUUID;

public class Generator {

    public static String Id(){
        return randomUUID()+""+System.currentTimeMillis();
    }
}
