package com.varc.bangflex.domain.user.entity;

public enum MemberRoleEnum {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authority;

    MemberRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }
}

