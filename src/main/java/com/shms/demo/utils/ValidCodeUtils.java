package com.shms.demo.utils;

import java.util.UUID;

public class ValidCodeUtils {
    public static String makecode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    public static String securecode(String code) {
        return SecurityUtils.encodePassword(code);
    }

    public static boolean validcode(String nosecure, String secured) {
        return SecurityUtils.matchesPassword(nosecure, secured);
    }
}
