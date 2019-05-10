package controllers.auction;

import dao.AuctionDAO;
import dao.CategoryDAO;
import dao.PhotoDAO;
import entities.Auction;
import entities.Category;
import entities.Photo;
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

@ManagedBean
@ViewScoped
public class UpdateAuctionController implements Serializable{
 
    @ManagedProperty(value="#{fileUploadController}")
    private FileUploadController fileUploadController;  
    private String itemid_string;
    private int item_id;
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
    private String[] selectedcategories;
    private List<Category> categories;
    private int photolist_size;
    private List<String> photonames = new ArrayList<String>();
    private List<Photo> photos = new ArrayList<Photo>(); 

    public FileUploadController getFileUploadController() {
        return fileUploadController;
    }

    public void setFileUploadController(FileUploadController fileUploadController) {
        this.fileUploadController = fileUploadController;
    }

    public List<String> getPhotonames() {
        return photonames;
    }

    public void setPhotonames(List<String> photonames) {
        this.photonames = photonames;
    }
    
    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public int getPhotolist_size() {
        return photolist_size;
    }

    public void setPhotolist_size(int photolist_size) {
        this.photolist_size = photolist_size;
    }

    public void setItemid_string(String itemid_string) {
        this.itemid_string = itemid_string;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrently(double currently) {
        this.currently = currently;
    }

    public void setBuyprice(Double buyprice) {
        this.buyprice = buyprice;
    }

    public void setFirstbid(double firstbid) {
        this.firstbid = firstbid;
    }

    public void setNumberofbids(int numberofbids) {
        this.numberofbids = numberofbids;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
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

    public String update(){    
        
       Date now = new Date();
       AuctionDAO dao = new AuctionDAO();
       Auction auction = new Auction();
       auction = dao.select(item_id);
       Date starts = auction.getStarted();
       
       if(getStarted().before(now)){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERROR: Η ημερομηνια και ώρα εναρξης της δημοπρασίας δεν μπορει να είναι προγενέστερη της τρέχουσας ωρας και ημερομηνίας !"));
            return null;
        }   
        
       if(starts.after(now)){
           if(getStarted().before(now)){
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("ERROR: Αν η δημοπρασία δεν έχει ξεκινήσει τότε η ημερομηνια και ώρα της δημοπρασίας δεν μπορει να αλλάξει σε προγενεστερη ημερομηνια της τωρινης ημερομηνιας!"));
                return null; 
           }
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
       
       auction.setName(getName());
       auction.setBuyPrice(getBuyprice());
       auction.setFirstBid(getFirstbid());
       auction.setStarted(getStarted());
       auction.setEnds(getEnds());
       auction.setCountry(getCountry());
       auction.setCity(getCity());
       auction.setDescription(getDescription());
       
       List<Category> categorylist = new ArrayList<Category>();
       int i =0;
       while(getSelectedcategories().length>i){
            Category category = new Category();
            category.setName(getSelectedcategories()[i]);       
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
       
       String message = dao.update(auction);
       FacesContext context = FacesContext.getCurrentInstance();
       context.addMessage(null, new FacesMessage(message));          
       return null;
    }
    
    public String deletephoto(){
        Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String photoId_string = requestMap.get("photoId");
        int photo_id = Integer.parseInt(photoId_string);
        PhotoDAO phdao = new PhotoDAO();
        String message = phdao.remove(photo_id); 
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
        return null;
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
        photos = photodao.getItemPhotosde(auction);
        
        CategoryDAO cadao = new CategoryDAO();  
        categories = cadao.getCategoriesname(); 
        this.selectedcategories = new String[categories.size()];       
        
        Collection<Category> itemcategories = auction.getCategoryCollection();
        List<Category> calist = new ArrayList<>();
        calist.addAll(itemcategories);
        int j;        

        for(j=0; j<itemcategories.size(); j++){
            Category category = calist.get(j);
            selectedcategories[j] = category.getName();
        }
    }
}