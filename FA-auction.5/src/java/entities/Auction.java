/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "auction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auction.findAll", query = "SELECT a FROM Auction a"),
    @NamedQuery(name = "Auction.findByItemId", query = "SELECT a FROM Auction a WHERE a.itemId = :itemId"),
    @NamedQuery(name = "Auction.findByName", query = "SELECT a FROM Auction a WHERE a.name = :name"),
    @NamedQuery(name = "Auction.findByCurrently", query = "SELECT a FROM Auction a WHERE a.currently = :currently"),
    @NamedQuery(name = "Auction.findByBuyPrice", query = "SELECT a FROM Auction a WHERE a.buyPrice = :buyPrice"),
    @NamedQuery(name = "Auction.findByFirstBid", query = "SELECT a FROM Auction a WHERE a.firstBid = :firstBid"),
    @NamedQuery(name = "Auction.findByNumberOfBids", query = "SELECT a FROM Auction a WHERE a.numberOfBids = :numberOfBids"),
    @NamedQuery(name = "Auction.findByCity", query = "SELECT a FROM Auction a WHERE a.city = :city"),
    @NamedQuery(name = "Auction.findByCountry", query = "SELECT a FROM Auction a WHERE a.country = :country"),
    @NamedQuery(name = "Auction.findByStarted", query = "SELECT a FROM Auction a WHERE a.started = :started"),
    @NamedQuery(name = "Auction.findByEnds", query = "SELECT a FROM Auction a WHERE a.ends = :ends"),
    @NamedQuery(name = "Auction.findByDescription", query = "SELECT a FROM Auction a WHERE a.description = :description")})
public class Auction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "item_id")
    private Integer itemId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "currently")
    private double currently;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "buy_price")
    private Double buyPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "first_bid")
    private double firstBid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number_of_bids")
    private int numberOfBids;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "started")
    @Temporal(TemporalType.TIMESTAMP)
    private Date started;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ends")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ends;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2500)
    @Column(name = "description")
    private String description;
    @JoinTable(name = "item_categories", joinColumns = {
        @JoinColumn(name = "auctions_item_id", referencedColumnName = "item_id")}, inverseJoinColumns = {
        @JoinColumn(name = "categories_name", referencedColumnName = "name")})
    @ManyToMany
    private Collection<Category> categoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private Collection<Bids> bidsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private Collection<Photo> photoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    private Collection<UsersHasAuctions> usersHasAuctionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    private Collection<UsersWonAuctions> usersWonAuctionsCollection;

    public Auction() {
    }

    public Auction(Integer itemId) {
        this.itemId = itemId;
    }

    public Auction(Integer itemId, String name, double currently, double firstBid, int numberOfBids, String city, String country, Date started, Date ends, String description) {
        this.itemId = itemId;
        this.name = name;
        this.currently = currently;
        this.firstBid = firstBid;
        this.numberOfBids = numberOfBids;
        this.city = city;
        this.country = country;
        this.started = started;
        this.ends = ends;
        this.description = description;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrently() {
        return currently;
    }

    public void setCurrently(double currently) {
        this.currently = currently;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getFirstBid() {
        return firstBid;
    }

    public void setFirstBid(double firstBid) {
        this.firstBid = firstBid;
    }

    public int getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(int numberOfBids) {
        this.numberOfBids = numberOfBids;
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

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Category> getCategoryCollection() {
        return categoryCollection;
    }

    public void setCategoryCollection(Collection<Category> categoryCollection) {
        this.categoryCollection = categoryCollection;
    }

    @XmlTransient
    public Collection<Bids> getBidsCollection() {
        return bidsCollection;
    }

    public void setBidsCollection(Collection<Bids> bidsCollection) {
        this.bidsCollection = bidsCollection;
    }

    @XmlTransient
    public Collection<Photo> getPhotoCollection() {
        return photoCollection;
    }

    public void setPhotoCollection(Collection<Photo> photoCollection) {
        this.photoCollection = photoCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auction)) {
            return false;
        }
        Auction other = (Auction) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Auction[ itemId=" + itemId + " ]";
    }
    
}
