package controllers.auction;

import dao.AuctionDAO;
import dao.CategoryDAO;
import entities.Auction;
import entities.Category;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ViewAuctionsController implements Serializable {

    private List<Auction> auctions;
    private String keyword_selected;
    private String category_selected;
    private String country;
    private Integer max_price;
    private List<Category> categories;

    public Integer getMax_price() {
        return max_price;
    }

    public void setMax_price(Integer max_price) {
        this.max_price = max_price;
    }
    
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getCategory_selected() {
        return category_selected;
    }

    public void setCategory_selected(String category_selected) {
        this.category_selected = category_selected;
    }

    public String getKeyword_selected() {
        return keyword_selected;
    }

    public void setKeyword_selected(String keyword_selected) {
        this.keyword_selected = keyword_selected;
    }
    
    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
    
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }       

    @PostConstruct
    public void init() {
        CategoryDAO dao = new CategoryDAO();  
        categories = dao.getCategoriesname();
        Date now = new Date();
        AuctionDAO adao = new AuctionDAO(); 
        auctions = adao.getAllActiveAuctions(now);
    } 

    public String viewSearchResult() {
        Date now = new Date();
        AuctionDAO dao = new AuctionDAO(); 

        if(getMax_price()==0){
            max_price = null;
        }
        
        if(max_price == null && getCategory_selected() == ""){
            auctions = dao.getActiveAuctionsc1(now, getCountry(), getKeyword_selected());
            return null;
        }
        else if(getCategory_selected() == "" && max_price != null){
            auctions = dao.getActiveAuctionsc2(now, getCountry(), getKeyword_selected(), getMax_price());
            return null;
        }
        else if(getCategory_selected() != "" && max_price == null){
            auctions = dao.getActiveAuctionsc3(now, getCountry(), getKeyword_selected(), getCategory_selected());
            return null;
        }
        else if(getCategory_selected() != "" && max_price != null){
            auctions = dao.getActiveAuctionsc4(now, getCountry(), getKeyword_selected(), getCategory_selected(), getMax_price());
            return null;
        }  
        return null;
    } 
}