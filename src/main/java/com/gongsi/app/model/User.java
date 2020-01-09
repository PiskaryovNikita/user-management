package com.gongsi.app.model;

import java.sql.Date;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.gongsi.soap.DateAdapter;
import com.gongsi.rest.resource.DateJsonbAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) // serialize just fields
@Entity
@Table(name = "USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	@Size(min = 4, max = 40, message = "login should be from 4 to 40 symbols")
	private String login;
	@Size(min = 6, max = 50, message = "password should be min. 6 symbols")
	private String password;
	@Size(min = 6, max = 50, message = "email should be min. 6 symbols")
	private String email;
	@Size(min = 2, max = 30, message = "Enter valid First name")
	private String firstName;
	@Size(min = 2, max = 30, message = "Enter valid Last name")
	private String lastName;
	@JsonbTypeAdapter(value = DateJsonbAdapter.class)
	@XmlJavaTypeAdapter(value = DateAdapter.class)
	private Date birthday;
	@ManyToOne(targetEntity = Role.class)
	private Role role;
	// HATEOAS
	@Transient
	private Link link;

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public User() {
	}

	public User(String login) {
		this.login = login;
	}

	public User(Long userId) {
		this.userId = userId;
	}

	public User(String login, String password, String email, String fN, String lN, Date birthday, Role role) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.firstName = fN;
		this.lastName = lN;
		this.birthday = birthday;
		this.role = role;
	}

	public User(Long userId, String login, String password, String email, String fN, String lN, Date birthday,
			Role role) {
		this.userId = userId;
		this.login = login;
		this.password = password;
		this.email = email;
		this.firstName = fN;
		this.lastName = lN;
		this.birthday = birthday;
		this.role = role;
	}

	public User(Role role) {
		this.role = role;
	}

	public User(Date birthday) {
		this.birthday = birthday;
	}

	public User(Date birthday, Role role) {
		this.birthday = birthday;
		this.role = role;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		User user = (User) obj;
		return (login == user.login || (login != null && login.equals(user.login)))
				&& (password == user.password || (password != null && password.equals(user.password)))
				&& (email == user.email || (email != null && email.equals(user.email)))
				&& (firstName == user.firstName || (firstName != null && firstName.equals(user.firstName)))
				&& (lastName == user.lastName || (lastName != null && lastName.equals(user.lastName)))
				&& (role == user.role || (role != null && role.equals(user.role)))
				&& (birthday == user.birthday || (birthday != null && birthday.equals(user.birthday)));
	}

	@Override
	public String toString() {
		return "{" + userId + ":" + login + ", " + password + ", " + email + ", " + firstName + ", " + lastName + ", "
				+ birthday + ", " + role + "(FK)}";
	}
}
