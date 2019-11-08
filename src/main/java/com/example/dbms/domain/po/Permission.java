package com.example.dbms.domain.po;

import java.io.Serializable;

public class Permission implements Serializable {
    private static final long serialVersionUID = -5680235862276163462L;
    private int menu_id;
    private int parent_id;
    private String menu_name;
    private String permission;

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
