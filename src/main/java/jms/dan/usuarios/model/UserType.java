package jms.dan.usuarios.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserType {
    @Id
    private Integer id;
    private String type;

    public UserType() {}

    public UserType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
