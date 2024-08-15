package com.ecommerce.id.vn.security;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JwtBlacklist {

    private static final Set<String> blacklist = new HashSet<>();

    public void add(String token) {
        blacklist.add(token);
    }

    public static boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
