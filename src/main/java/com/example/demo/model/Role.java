package com.example.demo.model;

public enum Role {
    HR("ROLE_HR"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    STUDENT("ROLE_STUDENT");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
