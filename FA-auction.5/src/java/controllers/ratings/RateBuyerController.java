package controllers.ratings;

import controllers.sesion.LoginLogout;
import dao.Ratings;
import dao.UserDAO;
import dao.UsersWonAuctionsDAO;
import entities.User;
import entities.UsersWonAuctions;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class RateBuyerController implements Serializable{
    @ManagedProperty(value="#{loginLogout}")
    private LoginLogout loginLogout;
    private Ratings ratings_p;
    private Ratings ratings_s;
    private List<Integer> seller_rating_p;
    private List<Integer> item_id_p;    
    private List<Integer> seller_rating_s;
    private List<Integer> item_id_s;
    private Integer rate;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public List<Integer> getItem_id_s() {
        return item_id_s;
    }

    public void setItem_id_s(List<Integer> item_id_s) {
        this.item_id_s = item_id_s;
    }

    public List<Integer> getItem_id_p() {
        return item_id_p;
    }

    public void setItem_id_p(List<Integer> item_id_p) {
        this.item_id_p = item_id_p;
    }

    public List<Integer> getSeller_rating_p() {
        return seller_rating_p;
    }

    public void setSeller_rating_p(List<Integer> seller_rating_p) {
        this.seller_rating_p = seller_rating_p;
    }

    public List<Integer> getSeller_rating_s() {
        return seller_rating_s;
    }

    public void setSeller_rating_s(List<Integer> seller_rating_s) {
        this.seller_rating_s = seller_rating_s;
    }

    
    
    public LoginLogout getLoginLogout() {
        return loginLogout;
    }

    public void setLoginLogout(LoginLogout loginLogout) {
        this.loginLogout = loginLogout;
    }

    public String ratebuyer(){
        Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String itemid_string = requestMap.get("itemId");
        Integer item_id = Integer.parseInt(itemid_string);
        UsersWonAuctionsDAO dao = new UsersWonAuctionsDAO();
        UsersWonAuctions userswonauctions = new UsersWonAuctions();
        userswonauctions = dao.selectBuyer(item_id);
        userswonauctions.setRating(getRate());
        String message = dao.update(userswonauctions);
        
        List<Integer> myratings = dao.getMyRatingBuyer(userswonauctions.getUser().getUsername());
        Integer mrsize = myratings.size();
        Integer sum=0;
        int i, rating=0;
        for(i=0; i<mrsize; i++){
            rating=myratings.get(i);
            sum+=rating;
        }
        double avg_myrating = (double)sum/(double)mrsize;
        User buyer = userswonauctions.getUser();
        UserDAO udao = new UserDAO();
        buyer.setBidRating(avg_myrating);
        udao.update(buyer);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
        return null;
    }
    
    @PostConstruct
    public void init() {
        UsersWonAuctionsDAO dao = new UsersWonAuctionsDAO();
        User user = loginLogout.getUser();
        String username = user.getUsername();
        ratings_p = dao.getPendingBuyierRating(username);
        seller_rating_p = ratings_p.getSellerRating();
        item_id_p = ratings_p.getItem_list();
        ratings_s = dao.getSubBuyierRating(username);
        seller_rating_s = ratings_s.getBuyierRating();
        item_id_s = ratings_s.getItem_list();    
    }
}
