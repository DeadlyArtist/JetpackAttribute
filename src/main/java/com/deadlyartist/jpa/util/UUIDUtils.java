package com.deadlyartist.jpa.util;

import java.util.UUID;

public class UUIDUtils {
    public static UUID of(String string) {
        return UUID.nameUUIDFromBytes(string.getBytes());
    }
}
