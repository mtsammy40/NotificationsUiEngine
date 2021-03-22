package com.vsms.portal.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheService {
    private Map<String, Object> store = new HashMap<>();

    public Map<String, Object> getStore() {
        return store;
    }

    public void clear() {
        store = new HashMap<>();
    }
}
