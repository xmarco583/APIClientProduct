package com.cortez.testapi.config.security;

import java.util.UUID;


import org.springframework.stereotype.Component;

@Component
public class Tracker {
    
    public static String generateTrackingId() {
         return UUID.randomUUID().toString();
    }
}
