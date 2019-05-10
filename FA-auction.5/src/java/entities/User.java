/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lumberjack
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findAllValidated", query = "SELECT u FROM User u where u.validation=true"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findBySurname", query = "SELECT u FROM User u WHERE u.surname = :surname"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPhone", query = "SELECT u FROM User u WHERE u.phone = :phone"),
    @NamedQuery(name = "User.findByAddress", query = "SELECT u FROM User u WHERE u.address = :address"),
    @NamedQuery(name = "User.findByCity", query = "SELECT u FROM User u WHERE u.city = :city"),
    @NamedQuery(name = "User.findByCountry", query = "SELECT u FROM User u WHERE u.country = :country"),
    @NamedQuery(name = "User.findByZipCode", query = "SELECT u FROM User u WHERE u.zipCode = :zipCode"),
    @NamedQuery(name = "User.findByVat", query = "SELECT u FROM User u WHERE u.vat = :vat"),
    @NamedQuery(name = "User.findByValidation", query = "SELECT u FROM User u WHERE u.validation = :validation"),
    @NamedQuery(name = "User.findByDateRegistartion", query = "SELECT u FROM User u WHERE u.dateRegistartion = :dateRegistartion"),
    @NamedQuery(name = "User.findByLongitude", query = "SELECT u FROM User u WHERE u.longitude = :longitude"),
    @NamedQuery(name = "User.findByLatitude", query = "SELECT u FROM User u WHERE u.latitude = :latitude"),
    @NamedQuery(name = "User.findByAccount", query = "SELECT u FROM User u WHERE u.account = :account"),
    @NamedQuery(name = "User.findBySellRating", query = "SELECT u FROM User u WHERE u.sellRating = :sellRating"),
    @NamedQuery(name = "User.findByBidRating", query = "SELECT u FROM User u WHERE u.bidRating = :bidRating")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 34)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "surname")
    private String surname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private BigInteger phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "country")
    private String country;
    @Column(name = "zip_code")
    private Integer zipCode;
    @Size(max = 45)
    @Column(name = "vat")
    private String vat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "validation")
    private boolean validation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_registartion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistartion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private double longitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "account")
    private Integer account;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sell_rating")
    private Double sellRating;
    @Column(name = "bid_rating")
    private Double bidRating;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Seller seller;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Administrator administrator;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Bids> bidsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "senderUsername")
    private Collection<Message> messageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipientUsername")
    private Collection<Message> messageCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UsersHasAuctions> usersHasAuctionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UsersWonAuctions> usersWonAuctionsCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Buyer buyer;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password, String name, String surname, String email, String address, String city, String country, boolean validation, Date dateRegistartion, double longitude, double latitude) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.validation = validation;
        this.dateRegistartion = dateRegistartion;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public boolean getValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    public Date getDateRegistartion() {
        return dateRegistartion;
    }

    public void setDateRegistartion(Date dateRegistartion) {
        this.dateRegistartion = dateRegistartion;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Double getSellRating() {
        return sellRating;
    }

    public void setSellRating(Double sellRating) {
        this.sellRating = sellRating;
    }

    public Double getBidRating() {
        return bidRating;
    }

    public void setBidRating(Double bidRating) {
        this.bidRating = bidRating;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    @XmlTransient
    public Collection<Bids> getBidsCollection() {
        return bidsCollection;
    }

    public void setBidsCollection(Collection<Bids> bidsCollection) {
        this.bidsCollection = bidsCollection;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection1() {
        return messageCollection1;
    }

    public void setMessageCollection1(Collection<Message> messageCollection1) {
        this.messageCollection1 = messageCollection1;
    }

    @XmlTransient
    public Collection<UsersHasAuctions> getUsersHasAuctionsCollection() {
        return usersHasAuctionsCollection;
    }

    public void setUsersHasAuctionsCollection(Collection<UsersHasAuctions> usersHasAuctionsCollection) {
        this.usersHasAuctionsCollection = usersHasAuctionsCollection;
    }

    @XmlTransient
    public Collection<UsersWonAuctions> getUsersWonAuctionsCollection() {
        return usersWonAuctionsCollection;
    }

    public void setUsersWonAuctionsCollection(Collection<UsersWonAuctions> usersWonAuctionsCollection) {
        this.usersWonAuctionsCollection = usersWonAuctionsCollection;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "entities.User[ username=" + username + " ]";
    }
    
}
