package jms.dan.usuarios.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String businessName;
    private String cuit;
    private String mail;
    private Double maxCurrentAccount;
    private Boolean onlineEnabled;
    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName="id", nullable=false)
    private User user;
    private Double currentBalance;
    private LocalDate dischargeDate;


    public Client() {
    }

    public Client(Integer id, String businessName, String cuit, String mail, Double maxCurrentAccount, Boolean onlineEnabled, User user, Double currentBalance) {
        this.id = id;
        this.businessName = businessName;
        this.cuit = cuit;
        this.mail = mail;
        this.maxCurrentAccount = maxCurrentAccount;
        this.onlineEnabled = onlineEnabled;
        this.currentBalance = currentBalance;
        this.user = user;
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

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
