package com.gongsi.app.persistence.model;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Data

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthday;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String login) {
        this.login = login;
    }

    public User(Long id) {
        this.id = id;
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
}
