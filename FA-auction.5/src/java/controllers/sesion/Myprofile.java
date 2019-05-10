/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.sesion;


import dao.UserDAO;
import entities.Administrator;
import entities.Auction;
import entities.Bids;
import entities.Message;
import entities.Seller;
import entities.User;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author lumberjack
 */
@ManagedBean
@ViewScoped
public class Myprofile implements Serializable{

    
    User user ;

    public void setUser(User user) {
        this.user = user;
    }
    
    public Myprofile(){
        HttpSession session = SessionUtils.getSession();
        LoginLogout login = (LoginLogout)session.getAttribute("user");
        this.user = login.getUser();
    }
    
    private boolean ePassword = false;
    private boolean eName = false;
    private boolean eSurname = false;
    private boolean eEmail = false;
    private boolean eAddress = false;
    private boolean eCity = false;
    private boolean eCountry = false;
    private boolean ePhone = false;
    
    
    public User getUser() {
        return user;
    }

    public boolean getEPassword() {
        return ePassword;
    }
    
    public String editPassword(){
        ePassword=!(this.ePassword);
        return null;
    }
    
    public boolean getEName() {
        return eName;
    }
    
    public String editName(){
        eName=!(this.eName);
        return null;
    }
    
    public boolean getESurname() {
        return eSurname;
    }
    
    public String editSurname(){
        eSurname=!(this.eSurname);
        return null;
    }
    
    public boolean getEEmail() {
        return eEmail;
    }
    
    public String editEmail(){
        eEmail=!(this.eEmail);
        return null;
    }
    
    public boolean getEAddress() {
        return eAddress;
    }
    
    public String editAddress(){
        eAddress=!(this.eAddress);
        return null;
    }
    
    public boolean getECity() {
        return eCity;
    }
    
    public String editCity(){
        eCity=!(this.eCity);
        return null;
    }
    
    public boolean getECountry() {
        return eCountry;
    }
    
    public String editCountry(){
        eCountry=!(this.eCountry);
        return null;
    }
    
    public boolean getEPhone() {
        return ePhone;
    }
    
    public String editPhone(){
        ePhone=!(this.ePhone);
        return null;
    }
    
    
    public void save(ActionEvent actionEvent) {
        UserDAO us = new UserDAO();
        us.update(this.user);
        ePassword = false;
        eName = false;
        eSurname = false;
        eEmail = false;
        eAddress = false;
        eCity = false;
        eCountry = false;
        ePhone = false;
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Profile Updated",  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }


   

  
    public String getUsername() {
        return user.getUsername();
    }

    

    public String getPassword() {
        return user.getPassword();
    }
    
    public void setPassword(String password){
        this.user.setPassword(password);
    }
    

    public String getName() {
        return user.getName();
    }
    
    public void setName(String name) {
        user.setName(name);
    }
    

    public String getSurname() {
        return user.getSurname();
    }
    
    public void setSurname(String surname) {
        user.setSurname(surname);
    }
    

    public String getEmail() {
        return user.getEmail();
    }
    
    public void setEmail(String email) {
        user.setEmail(email);
    }
    

    public BigInteger getPhone() {
        return user.getPhone();
    }
    
    public void setPhone(BigInteger phone) {
        user.setPhone(phone);
    }
    

    public String getAddress() {
        return user.getAddress();
    }
    
    public void setAddress(String address) {
        user.setAddress(address);
    }
    

    public String getCity() {
        return user.getCity();
    }
    
    public void setCity(String city) {
        user.setCity(city);
    }
    


    public String getCountry() {
        return user.getCountry();
    }
    
    public void setCountry(String country) {
        user.setCountry(country);
    }
    

    public Integer getZipCode() {
        return user.getZipCode();
    }
    
    public void setZipCode(Integer zipcode) {
        user.setZipCode(zipcode);
    }
    


    public String getVat() {
        return user.getVat();
    }

    public void setVat(String vat) {
        user.setVat(vat);
    }
    
    public Double getBidRating(){
       return user.getBidRating();
    }
    
    public Double getSellerRating(){
        return user.getSellRating();
    }


    public boolean getValidation() {
        return user.getValidation();
    }

    public Date getDateRegistartion() {
        return user.getDateRegistartion();
    }
    
    public int getAccountType(){
        return user.getAccount();
    }

    
}
