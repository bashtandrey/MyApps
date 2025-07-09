package org.bashtan.MyApps.data.services.interfaces;

public interface BlacklistServiceInterface {

    void addTokenToBlacklist(String token);

    boolean isTokenBlacklisted(String token);
}
