package com.gongsi.app.tag;

import com.gongsi.app.persistence.model.User;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public class UserTag implements Tag {
    private PageContext pageContext;
    private List<User> users;

    public UserTag() {
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // outputs table
    @SuppressWarnings("deprecation")
    private String getUsersTable() {
        int year = new Date(System.currentTimeMillis()).getYear();
        String output = "<table>" + "<tr><td>UserId</td><td>Login</td><td>First Name</td><td>Last Name</td>"
                + "<td>Age</td><td>Role</td><td>Actions</td></tr>";
        for (User user : users) {
            String idlfnln = "<tr><td>" + user.getId() + "</td><td>" + user.getLogin() + "</td>" + "<td>"
                    + user.getFirstName() + "</td>" + "<td>" + user.getLastName() + "</td>";
            String age;
            if (user.getBirthday() != null) {
                age = "<td>" + (year - user.getBirthday().getYear()) + "</td>";
            } else {
                age = "<td>unknown</td>";
            }
            String role = "<td>unknown</td>";
            if (user.getRole() != null) {
                role = "<td>" + user.getRole().getName() + "</td>";
            }
            String actionEdit = "<td><a href=\"/adminEdit?userId=" + user.getId() + "\">Edit</a>";
            String actionDelete = "<a href=\"/adminDelete?userId=" + user.getId()
                    +
                    "\"onclick=\"return confirm('Are you sure you want to delete this user?');\">Delete</a></td></tr>";
            output += idlfnln + age + role + actionEdit + actionDelete;
        }
        return output + "</table>";
    }

    @Override
    public void setPageContext(PageContext pc) {
        this.pageContext = pc;
    }

    @Override
    public void setParent(Tag t) {
    }

    @Override
    public Tag getParent() {
        return null;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write(getUsersTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Tag.SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return Tag.EVAL_PAGE;
    }

    @Override
    public void release() {
    }
}
