package com.msucil.dev.springbase.domain.manage.privilege;

public enum SystemPrivilege {
    SYSTEM_ADMIN("System Admin");

    private final String label;

    SystemPrivilege(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
