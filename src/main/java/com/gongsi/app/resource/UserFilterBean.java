package com.gongsi.app.resource;

import com.gongsi.app.persistence.model.Role;
import javax.ws.rs.QueryParam;
import lombok.Data;

@Data
public class UserFilterBean {
    @QueryParam("year")
    private Integer year;
    @QueryParam("start")
    private Integer start;
    @QueryParam("size")
    private Integer size;
    @QueryParam("roleId")
    private Role role;
}
