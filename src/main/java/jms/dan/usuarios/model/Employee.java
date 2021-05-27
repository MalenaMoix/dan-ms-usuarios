package jms.dan.usuarios.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Employee {
    @Id
    private Integer id;
    private String mail;
    @OneToOne
    private User user;

    public Employee() {}

    public Employee(Integer id, String mail, User user) {
        this.id = id;
        this.mail = mail;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
