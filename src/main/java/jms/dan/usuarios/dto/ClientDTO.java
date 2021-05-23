package jms.dan.usuarios.dto;

import jms.dan.usuarios.domain.Construction;
import jms.dan.usuarios.domain.User;

import java.util.List;

public class ClientDTO {
    private Integer id;
    private String businessName;
    private String cuit;
    private String mail;
    private Boolean onlineEnabled;
    private User user;
    private Float currentBalance;
    private List<Construction> constructions;

    public ClientDTO() {
    }

    public ClientDTO(Integer id, String businessName, String cuit, String mail, Boolean onlineEnabled, User user, List<Construction> constructions, Float currentBalance) {
        this.id = id;
        this.businessName = businessName;
        this.cuit = cuit;
        this.mail = mail;
        this.onlineEnabled = onlineEnabled;
        this.user = user;
        this.currentBalance = currentBalance;
        this.constructions = constructions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getOnlineEnabled() {
        return onlineEnabled;
    }

    public void setOnlineEnabled(Boolean onlineEnabled) {
        this.onlineEnabled = onlineEnabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Construction> getConstructions() {
        return constructions;
    }

    public void setConstructions(List<Construction> constructions) {
        this.constructions = constructions;
    }

    public Float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Float currentBalance) {
        this.currentBalance = currentBalance;
    }
}
