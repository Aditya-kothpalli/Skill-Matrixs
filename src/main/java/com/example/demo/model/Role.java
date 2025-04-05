package com.example.demo.model;

public enum Role {
    HR("HR"),
    EMPLOYEE("EMPLOYEE"),
    STUDENT("STUDENT"),
	ORGANIZATION("ORGANIZATION");
	
	 
    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
