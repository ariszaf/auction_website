package controllers.auction;

import controllers.sesion.LoginLogout;
import dao.AuctionDAO;
import dao.BidsDAO;
import dao.PhotoDAO;
import dao.UsersHasAuctionsDAO;
import entities.Auction;
import entities.Bids;
import entities.BidsPK;
import entities.Category;
import entities.Photo;
import entities.User;
import entities.UsersHasAuctions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean
@ViewScoped
public class ViewAuctionDetailsController implements Serializable{
    
    @ManagedProperty(value="#{loginLogout}")
    private LoginLogout loginLogout;  
    private String itemid_string;
    private  int item_id;
    private String name;
    private double currently;
    private Double buyprice;
    private double firstbid;
    private int numberofbids;
    private String city;
    private String country;
    private Date started;
    private Date ends;
    private String description;    
    private Auction auction;
    private List<Photo> photolist = new ArrayList<Photo>();
    private Collection<Category> itemcategories;
    private Collection<UsersHasAuctions> created;
    private User seller;
    private int phlistsize;
    private double rating_s;   
    private MapModel model;
    private String centerMap;
    private int bidPrice;

    public double getRating_s() {
        return rating_s;
    }

    public void setRating_s(double rating_s) {
        this.rating_s = rating_s;
    }
    
    public double getMinBid() {
        if(this.numberofbids==0){
            return this.firstbid;
        }
        return 0.0;
    }

    public String getCenterMap() {
        return centerMap;
    }

    public void setCenterMap(String centerMap) {
        this.centerMap = centerMap;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    public MapModel getModel() {
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
    }

    public Collection<UsersHasAuctions> getCreated() {
        return created;
    }

    public void setCreated(Collection<UsersHasAuctions> created) {
        this.created = created;
    }
    
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
    
    public int getPhlistsize() {
        return phlistsize;
    }

    public void setPhlistsize(int phlistsize) {
        this.phlistsize = phlistsize;
    }
    
    public LoginLogout getLoginLogout() {
        return loginLogout;
    }

    public void setLoginLogout(LoginLogout loginLogout) {
        this.loginLogout = loginLogout;
    }
    
    public Collection<Category> getItemcategories() {
        return itemcategories;
    }
    
    public List<Photo> getPhotolist() {
        return photolist;
    }

    public Auction getAuction() {
        return auction;
    }
    
    public int getItem_id() {
        return item_id;
    }

    public String getName() {
        return name;
    }

    public double getCurrently() {
        return currently;
    }

    public Double getBuyprice() {
        return buyprice;
    }

    public double getFirstbid() {
        return firstbid;
    }

    public int getNumberofbids() {
        return numberofbids;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Date getStarted() {
        return started;
    }

    public Date getEnds() {
        return ends;
    }

    public String getDescription() {
        return description;
    }
    
    public void  validateBid(){
        
        BidsPK bidspk = new BidsPK();
        Date d = new Date();
        bidspk.setTime(d);
        bidspk.setBidderUsername(loginLogout.getUsername());
        Bids bid = new Bids();
        bid.setBidsPK(bidspk);
        bid.setItemId(auction);
        bid.setBidAmount(bidPrice);
        BidsDAO bidsdao = new BidsDAO();
        
        AuctionDAO auctiondao= new AuctionDAO();
        auction.setNumberOfBids(auction.getNumberOfBids()+1);
        auctiondao.update(auction);
        
        bidsdao.insert(bid);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Bid placed successfully",  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        if(currently<bidPrice){
            AuctionDAO auct = new AuctionDAO();
            auction.setCurrently(bidPrice);
            auct.update(auction);
            this.currently=bidPrice;
        }
       
    }

    @PostConstruct
    public void init() {        

        Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        itemid_string = requestMap.get("itemId");
        item_id = Integer.parseInt(itemid_string);
        AuctionDAO dao = new AuctionDAO();
        auction = dao.select(item_id);
        name = auction.getName();
        currently = auction.getCurrently();
        buyprice = auction.getBuyPrice();
        firstbid = auction.getFirstBid();
        numberofbids = auction.getNumberOfBids();
        city = auction.getCity();
        country = auction.getCountry();
        started = auction.getStarted();
        ends = auction.getEnds();
        description = auction.getDescription();
        PhotoDAO photodao = new PhotoDAO();
        photolist = photodao.getItemPhotos(auction);
        
        phlistsize = photolist.size();
        
        itemcategories = auction.getCategoryCollection();
        created = auction.getUsersHasAuctionsCollection();
        itemcategories = auction.getCategoryCollection();                
 

        UsersHasAuctions uha = new UsersHasAuctions();
        UsersHasAuctionsDAO uhdao = new UsersHasAuctionsDAO();
        uha = uhdao.selectSeller(item_id);
        seller = uha.getUser();
        if(seller.getSellRating()==null){
         rating_s =0.0;    
        }
        else{
           rating_s = seller.getSellRating();
        } 
        
        
        
        model = new DefaultMapModel();
        LatLng coord1 = new LatLng(seller.getLatitude(), seller.getLongitude());
        Marker marker = new Marker(coord1,"SellLocation");
        model.addOverlay(marker);
        this.centerMap= coord1.getLat() + "," + coord1.getLng();
        
    }
}