package jms.dan.usuarios.domain;

import java.util.List;

public class Client {
    private Integer id;
    private String businessName;
    private String cuit;
    private String mail;
    private Double maxCurrentAccount;
    private Boolean onlineEnabled;
    private User user;
    private List<Construction> constructions;
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
    public Double getMaxCurrentAccount() {
        return maxCurrentAccount;
    }
    public void setMaxCurrentAccount(Double maxCurrentAccount) {
        this.maxCurrentAccount = maxCurrentAccount;
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
}
