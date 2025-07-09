package org.bashtan.MyApps.enums;

public enum UserRole {
    ADMIN,

    POST_EDITOR,

    VIDEO_USER,
    VIDEO_ADMIN,

    LIBRARY_USER,
    LIBRARY_ADMIN,

    GUEST;


    public String withPrefix() {
        return "ROLE_" + this.name();
    }
}
