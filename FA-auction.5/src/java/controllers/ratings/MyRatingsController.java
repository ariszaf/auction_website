package controllers.ratings;

import controllers.sesion.LoginLogout;
import dao.UsersHasAuctionsDAO;
import dao.UsersWonAuctionsDAO;
import entities.User;
import entities.UsersHasAuctions;
import entities.UsersWonAuctions;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class MyRatingsController implements Serializable{
    @ManagedProperty(value="#{loginLogout}")
    private LoginLogout loginLogout;
    private List<UsersHasAuctions> ratings_s;
    private List<UsersWonAuctions> ratings_b;
    private Double avg_rating_s;
    private Double avg_rating_b;

    public Double getAvg_rating_s() {
        return avg_rating_s;
    }

    public void setAvg_rating_s(Double avg_rating_s) {
        this.avg_rating_s = avg_rating_s;
    }

    public Double getAvg_rating_b() {
        return avg_rating_b;
    }

    public void setAvg_rating_b(Double avg_rating_b) {
        this.avg_rating_b = avg_rating_b;
    }
    
    public LoginLogout getLoginLogout() {
        return loginLogout;
    }

    public void setLoginLogout(LoginLogout loginLogout) {
        this.loginLogout = loginLogout;
    }

    
    public List<UsersHasAuctions> getRatings_s() {
        return ratings_s;
    }

    public void setRatings_s(List<UsersHasAuctions> ratings_s) {
        this.ratings_s = ratings_s;
    }

    public List<UsersWonAuctions> getRatings_b() {
        return ratings_b;
    }

    public void setRatings_b(List<UsersWonAuctions> ratings_b) {
        this.ratings_b = ratings_b;
    }

    @PostConstruct
    public void init() {
        UsersHasAuctionsDAO uhadao = new UsersHasAuctionsDAO();
        UsersWonAuctionsDAO uwadao = new UsersWonAuctionsDAO();
        User user = loginLogout.getUser();
        String username = user.getUsername();
        ratings_s = uhadao.getMyAuctionsRating_s(username);
        avg_rating_s = user.getSellRating();
        ratings_b = uwadao.getMyAuctionsRating_b(username);
        avg_rating_b = user.getBidRating();
    }
}
