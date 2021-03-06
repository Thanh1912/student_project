package com.example.dto;

public class RoleDTO extends AbstractDTO<RoleDTO> {

    private static final long serialVersionUID = -6377018292533104679L;
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
