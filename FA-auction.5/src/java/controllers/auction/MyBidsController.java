package controllers;

import controllers.sesion.LoginLogout;
import dao.BidsDAO;
import entities.Bids;
import entities.User;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class MyBidsController implements Serializable{
    @ManagedProperty(value="#{loginLogout}")
    private LoginLogout loginLogout;
    private List<Bids> mybids;

    public List<Bids> getMybids() {
        return mybids;
    }

    public void setMybids(List<Bids> mybids) {
        this.mybids = mybids;
    }
    
    public LoginLogout getLoginLogout() {
        return loginLogout;
    }

    public void setLoginLogout(LoginLogout loginLogout) {
        this.loginLogout = loginLogout;
    }


    @PostConstruct
    public void init() {
        User user = loginLogout.getUser();
        String username = user.getUsername();
        BidsDAO dao = new BidsDAO();     
        mybids = dao.getMyBids(username);
    }
}
