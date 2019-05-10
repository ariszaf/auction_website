package controllers.ratings;

import controllers.sesion.LoginLogout;
import dao.UsersHasAuctionsDAO;
import dao.Ratings;
import dao.UserDAO;
import entities.User;
import entities.UsersHasAuctions;
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
public class RateSellerController implements Serializable{
    @ManagedProperty(value="#{loginLogout}")
    private LoginLogout loginLogout;
    private Ratings ratings_p;
    private Ratings ratings_s;
    private List<Integer> buyier_rating_p;
    private List<Integer> item_id_p;    
    private List<Integer> buyier_rating_s;
    private List<Integer> item_id_s;
    private Integer rate;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public List<Integer> getBuyier_rating_s() {
        return buyier_rating_s;
    }

    public void setBuyier_rating_s(List<Integer> buyier_rating_s) {
        this.buyier_rating_s = buyier_rating_s;
    }

    public List<Integer> getItem_id_s() {
        return item_id_s;
    }

    public void setItem_id_s(List<Integer> item_id_s) {
        this.item_id_s = item_id_s;
    }
    
    public List<Integer> getBuyier_rating_p() {
        return buyier_rating_p;
    }

    public void setBuyier_rating_p(List<Integer> buyier_rating_p) {
        this.buyier_rating_p = buyier_rating_p;
    }

    public List<Integer> getItem_id_p() {
        return item_id_p;
    }

    public void setItem_id_p(List<Integer> item_id_p) {
        this.item_id_p = item_id_p;
    }

    public LoginLogout getLoginLogout() {
        return loginLogout;
    }

    public void setLoginLogout(LoginLogout loginLogout) {
        this.loginLogout = loginLogout;
    }

    public String rateseller(){
        Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String itemid_string = requestMap.get("itemId");
        Integer item_id = Integer.parseInt(itemid_string);
        UsersHasAuctionsDAO dao = new UsersHasAuctionsDAO();
        UsersHasAuctions usershasauctions = new UsersHasAuctions();
        usershasauctions = dao.selectSeller(item_id);
        usershasauctions.setRating(getRate());    
        String message = dao.update(usershasauctions);
        
        List<Integer> myratings = dao.getMyRatingSellers(usershasauctions.getUser().getUsername());
        Integer mrsize = myratings.size();
        Integer sum=0;
        int i, rating=0;
        for(i=0; i<mrsize; i++){
            rating=myratings.get(i);
            sum+=rating;
        }        
        double avg_myrating = (double)sum/(double)mrsize;
        User seller = usershasauctions.getUser();
        UserDAO udao = new UserDAO();
        seller.setSellRating(avg_myrating);
        udao.update(seller);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
        return null;
    }
    
    @PostConstruct
    public void init() {
        UsersHasAuctionsDAO dao = new UsersHasAuctionsDAO();
        user = loginLogout.getUser();
        String username = user.getUsername();
        ratings_p = dao.getPendingSellerRating(username);
        buyier_rating_p = ratings_p.getBuyierRating();
        item_id_p = ratings_p.getItem_list();
        ratings_s = dao.getSubSellerRating(username);
        buyier_rating_s = ratings_s.getBuyierRating();
        item_id_s = ratings_s.getItem_list();    
    }
}
