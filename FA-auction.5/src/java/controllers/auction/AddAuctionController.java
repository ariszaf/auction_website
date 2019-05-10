package controllers.auction;

import controllers.auction.FileUploadController;
import controllers.sesion.LoginLogout;
import dao.AuctionDAO;
import dao.CategoryDAO;
import dao.UsersHasAuctionsDAO;
import entities.Auction;
import entities.Category;
import entities.Photo;
import entities.User;
import entities.UsersHasAuctions;
import entities.UsersHasAuctionsPK;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class AddAuctionController implements Serializable{
    
    @ManagedProperty(value="#{loginLogout}")
    private LoginLogout loginLogout;  
    @ManagedProperty(value="#{fileUploadController}")
    private FileUploadController fileUploadController;  
    private int item_id;
    private List<Auction> auctions;
    private String name;
    private double currently;
    private double buyprice;
    private double firstbid;
    private int numberofbids;
    private String city;
    private String country;
    private Date started;
    private Date ends;
    private String description;
    private User user;
    private List<String> photonames = new ArrayList<String>();
    private String[] selectedcategories;
    private List<Category> categories;

    public FileUploadController getFileUploadController() {
        return fileUploadController;
    }

    public void setFileUploadController(FileUploadController fileUploadController) {
        this.fileUploadController = fileUploadController;
    }
    
    public String[] getSelectedcategories() {
        return selectedcategories;
    }

    public void setSelectedcategories(String[] selectedcategories) {
        this.selectedcategories = selectedcategories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<String> getPhotonames() {
        return photonames;
    }

    public void setPhotonames(List<String> photonames) {
        this.photonames = photonames;
    }
    
    public LoginLogout getLoginLogout() {
        return loginLogout;
    }

    public void setLoginLogout(LoginLogout loginLogout) {
        this.loginLogout = loginLogout;
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

    public double getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(double buyprice) {
        this.buyprice = buyprice;
    }

    public double getFirstbid() {
        return firstbid;
    }

    public void setFirstbid(double firstbid) {
        this.firstbid = firstbid;
    }

    public int getNumberofbids() {
        return numberofbids;
    }

    public void setNumberofbids(int numberofbids) {
        this.numberofbids = numberofbids;
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

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }  

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
        
    @PostConstruct
    public void init() {
        CategoryDAO dao = new CategoryDAO();  
        categories = dao.getCategoriesname();
    } 
    
    public String insert()  throws IOException {
        Date now = new Date();
        if(getStarted().before(now)){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERROR: Η ημερομηνια και ώρα εναρξης της δημοπρασίας δεν μπορει να είναι προγενέστερη της τρέχουσας ωρας και ημερομηνίας !"));
            return null;
        }   
        
        if(getEnds().before(getStarted())){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERROR: Η ημερομηνια και ώρα λήξης της δημοπρασίας δεν μπορει να είναι προγενέστερη της ημερομηνιας και ωρας έναρξης!"));
            return null; 
        }
        
        if(getBuyprice()<getFirstbid()){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERROR:  I timi tou first bid den mporei na einai megaluteri apo tin timi  tis amesis agoras tou proiontos stin dimoprasia!"));
            return null;  
        }    
        
        Auction auction = new Auction(); 
        auction.setName(getName());
        auction.setCurrently(0);
        auction.setBuyPrice(getBuyprice());
        auction.setFirstBid(getFirstbid());
        auction.setNumberOfBids(0);
        auction.setCity(loginLogout.getUser().getCity());
        auction.setCountry(loginLogout.getUser().getCountry());
        auction.setStarted(getStarted());
        auction.setEnds(getEnds());
        auction.setDescription(getDescription());
        
        List<Category> categorylist = new ArrayList<Category>();
        int i =0;
        while(selectedcategories.length>i){
            Category category = new Category();
            category.setName(selectedcategories[i]);       
            categorylist.add(category);
            i++;
        }
        auction.setCategoryCollection(categorylist);
        
        photonames = fileUploadController.getPhotonames();
        
        List<Photo> photolist = new ArrayList<Photo>();
        int j =0; 
        while(photonames.size()>j){
            Photo photo = new Photo();
            photo.setName(photonames.get(j));
            photo.setItemId(auction);
            photolist.add(photo);
            j++;
        }
        auction.setPhotoCollection(photolist);       
        
        user = loginLogout.getUser();    
        
        AuctionDAO dao = new AuctionDAO();         
        String message = dao.insert(auction); 

        UsersHasAuctionsDAO uhadao = new UsersHasAuctionsDAO();
        UsersHasAuctionsPK uhapk = new UsersHasAuctionsPK ();
        UsersHasAuctions uha = new UsersHasAuctions ();
        uhapk.setAuctionsItemId(auction.getItemId());
        uhapk.setSellerUsername(user.getUsername());
        uha.setUsersHasAuctionsPK(uhapk);
        uhadao.insert(uha);
                 
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));          

        return null;
    }
}