/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import dao.AdministratorDAO;
import dao.BuyerDAO;
import dao.SellerDAO;
import dao.UserDAO;
import entities.Administrator;
import entities.Buyer;
import entities.Seller;
import entities.User;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author lumberjack
 */
@ManagedBean
@ViewScoped
public class ActivateUpdate implements Serializable {
    
    
    String meta_username;
    
    private int account_changed = 0;
    
    

    public String getMeta_username() {
        return meta_username;
    }

    public void setMeta_username(String meta_username) {
        this.meta_username = meta_username;
    }
    
    private User user;
    
    private boolean eUsername = false;
    private boolean ePassword = false;
    private boolean eName = false;
    private boolean eSurname = false;
    private boolean eEmail = false;
    private boolean eAddress = false;
    private boolean eCity = false;
    private boolean eCountry = false;
    private boolean ePhone = false;
    private boolean eZipcode = false;
    private boolean eDate_registartion = false;
    private boolean eAccount = false;
    private boolean eValidate = false;
    
    public boolean getEValidate(){
        return eValidate;
    }
    
    public String EditValidate(){
        eValidate=!(this.eValidate);
        return null;
    }
    
    public boolean getEAccount() {
        return eAccount;
    }
    
    public Integer editAccount(){
        eAccount=!(this.eAccount);
        return null;
    }
    
    public boolean getEUsername() {
        return eUsername;
    }
    
    public String editUsername(){
        eUsername=!(this.eUsername);
        return null;
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
    
    public boolean getEZipcode() {
        return eZipcode;
    }
    
    public String editZipcode(){
        eZipcode=!(this.eZipcode);
        return null;
    }
    
    public boolean getEDate_registartion() {
        return eDate_registartion;
    }
    
    public String editDate_registartion(){
        eDate_registartion=!(this.eDate_registartion);
        return null;
    }
    

    
  
    public void save(ActionEvent actionEvent) {
        UserDAO us = new UserDAO();
        us.update(this.user);
        eUsername = false;
        ePassword = false;
        eName = false;
        eSurname = false;
        eEmail = false;
        eAddress = false;
        eCity = false;
        eCountry = false;
        ePhone = false;
        eZipcode = false;
        eDate_registartion = false;
        
        if(this.account_changed!=0){
            if(this.account_changed==1){
                AdministratorDAO admindao = new AdministratorDAO();
                admindao.remove(meta_username);
            }
            else if(this.account_changed==2){
                SellerDAO sellerdao = new SellerDAO();
                sellerdao.remove(meta_username);
            }
            else{
                BuyerDAO buyerdao = new  BuyerDAO();
                buyerdao.remove(meta_username);
            }
            
            if(this.user.getAccount()==1){
                AdministratorDAO admindao = new AdministratorDAO();
                Administrator admin = new Administrator();
                admin.setUsername(meta_username);
                admindao.insert(admin);
            }
            else if(this.user.getAccount()==2){
                SellerDAO sellerdao = new SellerDAO();
                Seller seller = new Seller();
                seller.setUsername(meta_username);
                sellerdao.insert(seller);
            }
            else{
                BuyerDAO buyerdao = new BuyerDAO();
                Buyer buyer = new Buyer();
                buyer.setUsername(meta_username);
                buyerdao.insert(buyer);
            }
        }
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Profile Updated",  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }
    
    public void activateUser(String username){
        UserDAO userdao = new UserDAO();
        User user = userdao.select(username);
        user.setValidation(true);
        userdao.update(user);
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"User "+username+" activated !",  null);
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }
    
    public void loadUser(){
        
        UserDAO userdao = new UserDAO();
        this.user=userdao.select(meta_username);
        
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"User "+meta_username+" says hello",  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }
    
    public String getUsername() {
        return user.getUsername();
    }
    
    public void setUsername(String username){
        this.user.setUsername(username);
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
    
    public void setName(String name){
        this.user.setName(name);
    }
    
    public String getSurname() {
        return user.getSurname();
    }
    
    public void setSurname(String surname){
        this.user.setSurname(surname);
    }
    
    public String getEmail() {
        return user.getEmail();
    }
    
    public void setEmail(String email){
        this.user.setEmail(email);
    }
    
    public BigInteger getPhone() {
        return user.getPhone();
    }
    
    public void setPhone(BigInteger phone){
        this.user.setPhone(phone);
    }
    
    public String getAddress() {
        return user.getAddress();
    }
    
    public void setAddress(String Address){
        this.user.setAddress(Address);
    }
    public String getCity() {
        return user.getCity();
    }
    
    public void setCity(String city){
        this.user.setCity(city);
    }
    
    public String getCountry() {
        return user.getCountry();
    }
    
    public void setCountry(String country){
        this.user.setCountry(country);
    }
    
    public Date getDateRegistartion() {
        return user.getDateRegistartion();
    }
    
    public Double getBidRating(){
       return user.getBidRating();
    }
    
    public Double getSellerRating(){
        return user.getSellRating();
    }

    public int getAccountType(){
        return user.getAccount();
    }
    
    public void setAccountType(int account){
        this.account_changed= user.getAccount();
        this.user.setAccount(account);
    }
    
    public boolean getValidate(){
        return user.getValidation();
    }
    
    public void setValidate(Boolean val){
        this.user.setValidation(val);
        this.eValidate =!(this.eValidate);
    }
    public ActivateUpdate() {
    
    
    }
    
}
