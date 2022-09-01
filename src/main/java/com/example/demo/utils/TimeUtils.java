package com.example.demo.utils;

import java.sql.Timestamp;

public class TimeUtils {

    public static Timestamp changeToCurrentIfOld(Timestamp timestamp) {

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (timestamp.before(currentTime)) {
            return currentTime;
        }

        return timestamp;
    }
}
