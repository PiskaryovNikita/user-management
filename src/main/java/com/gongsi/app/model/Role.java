package com.gongsi.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "ROLE")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long roleId;
	private String name;
	// HATEOAS
	@Transient
	private Link link;

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Role(Long roleId) {
		this.roleId = roleId;
	}

	public Role(Long roleId, String name) {
		this.roleId = roleId;
		this.name = name;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result = prime * result + ((roleId == null) ? 0 : roleId.intValue());

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Role role = (Role) obj;
		return (roleId == role.roleId || (roleId != null && roleId.equals(role.getRoleId())))
				&& (name == role.name || (name != null && name.equals(role.getName())));
	}

	@Override
	public String toString() {
		return "{" + roleId + ", " + name + "}";
	}
}
