package com.gongsi.app.resource;

import javax.ws.rs.QueryParam;

public class UserFilterBean {
    @QueryParam("year")
    private Integer year;
    @QueryParam("start")
    private Integer start;
    @QueryParam("size")
    private Integer size;
    @QueryParam("roleId")
    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
