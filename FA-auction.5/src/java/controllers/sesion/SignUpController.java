package controllers.sesion;

import dao.UserDAO;
import dao.SellerDAO;
import dao.BuyerDAO;
import entities.User;
import entities.Seller;
import entities.Buyer;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class SignUpController implements Serializable{

    private String username;
    private String password;
    private String confirm_password;
    private String name;
    private String surname;
    private String email;
    private BigInteger phone;
    private String address;
    private String city;
    private String country;
    private Integer zip;
    
    @ManagedProperty(value="#{mapControler.lat}")
    private Double lat;
    @ManagedProperty(value="#{mapControler.lng}")
    private Double lng;
    private String vat;
    private int account_type;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    
    
    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }
   
    
    public int getAccount_type() {
        return account_type;
    }

    public void setAccount_type(int account_type) {
        this.account_type = account_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getPhone() {
        return phone;
    }

    public void setPhone(BigInteger phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

   

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }
    
    @PostConstruct
    public void init(){
        
    }
    
    public String signupUser() {
        
        
        String pass=this.getConfirm_password();
        if(!getPassword().equals(getConfirm_password())){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERROR: Your password and confirmation password do not match!"));
            return null;
        }
        
        UserDAO dao = new UserDAO();
        User usermail = new User();
        usermail = dao.selectEmail(getEmail());
        
        if(usermail!=null){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERROR: Email already exists !"));
            return null; 
        }
        
        User username = new User();
        username = dao.select(getUsername());
        
        if(username!=null){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERROR: Username already exists !"));
            return null; 
        }
        
        User user = new User();
        user.setAddress(getAddress());
        user.setCity(getCity());
        user.setCountry(getCountry());
        user.setEmail(getEmail());
        Date d = new Date();
        user.setDateRegistartion(d);
        user.setName(getName());
        user.setPassword(getPassword());
        user.setPhone(getPhone());
        user.setSurname(getSurname());
        user.setUsername(getUsername());

        user.setValidation(false);
        user.setVat(getVat());
        user.setZipCode(getZip());
        user.setAccount(account_type);
        user.setLatitude(this.lat);
        user.setLongitude(this.lng);
        String message = dao.insert(user);
        
        if(account_type==2){
            SellerDAO sdao = new SellerDAO();
            Seller seller = new Seller();
            seller.setUsername(getUsername());
            sdao.insert(seller);
        }
        if(account_type==3){
            BuyerDAO sdao = new BuyerDAO();
            Buyer buyer = new Buyer();
            buyer.setUsername(getUsername());
            sdao.insert(buyer);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));    
        return null;
    }   
}
