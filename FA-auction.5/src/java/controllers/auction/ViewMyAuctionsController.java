package controllers.auction;

import controllers.sesion.LoginLogout;
import dao.UsersHasAuctionsDAO;
import entities.Auction;
import entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ViewMyAuctionsController implements Serializable{
    
    @ManagedProperty(value="#{loginLogout}")
    private LoginLogout loginLogout;
    private List<Auction> active_auctions = new ArrayList<Auction>();
    private List<Auction> inactive_auctions = new ArrayList<Auction>();
    private User user;
    private String username;
    private List<Auction> auctions;

    public List<Auction> getInactive_auctions() {
        return inactive_auctions;
    }

    public void setInactive_auctions(List<Auction> inactive_auctions) {
        this.inactive_auctions = inactive_auctions;
    }
    
    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LoginLogout getLoginLogout() {
        return loginLogout;
    }

    public void setLoginLogout(LoginLogout loginLogout) {
        this.loginLogout = loginLogout;
    }

    public List<Auction> getActive_auctions() {
        return active_auctions;
    }

    public void setActive_auctions(List<Auction> active_auctions) {
        this.active_auctions = active_auctions;
    }

   @PostConstruct
    public void viewMyAuctions() {
        Date now = new Date();
        UsersHasAuctionsDAO dao = new UsersHasAuctionsDAO();
        user = loginLogout.getUser();
        username = user.getUsername();
        auctions = dao.getMyAuctions(username);
        
        Auction active_auction = null;
        Auction inactive_auction =null;
        Date ends;
        Date started;
        for(int i=0; i<auctions.size(); i++){
            active_auction = auctions.get(i);
            ends = active_auction.getEnds();
            started = active_auction.getStarted();
            if(ends.after(now) && started.before(now)){
               active_auctions.add(active_auction);
            }
         }
        
        for(int i=0; i<auctions.size(); i++){
            inactive_auction = auctions.get(i);
            ends = inactive_auction.getEnds();
            started = inactive_auction.getStarted();
            if(ends.before(now) || started.after(now)){
               inactive_auctions.add(inactive_auction);
            }
        }
    }
}