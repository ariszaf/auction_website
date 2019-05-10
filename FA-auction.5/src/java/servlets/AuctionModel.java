package servlets;

import entities.Bids;
import entities.Category;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

public class AuctionModel implements Serializable {
    
    private Integer id;
    private String name;
    private Collection<String> category = new ArrayList();
    private Double currently;
    private Integer number_of_bids;
    private BidsWrapper bids; //To DO
    private String location; // ToDO
    private String Country;
    private Date started;
    private Date ends;
    private SellerModel seller;
    private String desrciption;
  
    
    @XmlElement(name="Category")
    public Collection<String> getCategory() {
        return category;
    }

    public void setCategory(Collection<String> category) {
        this.category = category;
    }
    
    @XmlElement(name="Description")
    public String getDesrciption() {
        return desrciption;
    }

    public void setDesrciption(String desrciption) {
        this.desrciption = desrciption;
    }

    @XmlElement(name="Seller")
    public SellerModel getSeller() {
        return seller;
    }

    public void setSeller(SellerModel seller) {
        this.seller = seller;
    }


    @XmlElement(name="Ends")
    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }
    
    @XmlElement(name="Started")
    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }
    

    @XmlElement(name="Country")
    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }
    
    @XmlElement(name="Location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlElement(name="Bids")
    public BidsWrapper getBids() {
        return bids;
    }

    public void setBids(BidsWrapper bids) {
        this.bids = bids;
    }

    @XmlElement(name="Number_of_Bids")
    public Integer getNumber_of_bids() {
        return number_of_bids;
    }

    public void setNumber_of_bids(Integer number_of_bids) {
        this.number_of_bids = number_of_bids;
    }
    
    @XmlElement(name="Currently")
    public Double getCurrently() {
        return currently;
    }
   

    public void setCurrently(Double currently) {
        this.currently = currently;
    }
    private Double firstbid;
    
    @XmlElement(name="First_Bid")
    public Double getFirstbid() {
        return firstbid;
    }

    public void setFirstbid(Double firstbid) {
        this.firstbid = firstbid;
    }

    
   

    @XmlAttribute(name="ItemID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @XmlElement(name="Name") 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuctionModel() {
    }
    
    
}
