/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.collaboration;
import controllers.admin.SearchUsers;
import controllers.sesion.LoginLogout;
import controllers.sesion.SessionUtils;
import dao.MessageDAO;
import dao.UserDAO;
import entities.Auction;
import java.util.List;
import javax.inject.Named;
import entities.Message;
import entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author lumberjack
 */
@ManagedBean
@ViewScoped
public class Collaboration implements Serializable {

    @ManagedProperty("#{user}")
    LoginLogout user;

    public LoginLogout getUser() {
        return user;
    }

    public void setUser(LoginLogout user) {
        this.user = user;
    }
    
    private List<Auction> recommendedAuctions;

    public List<Auction> getRecommendedAuctions() {
        return recommendedAuctions;
    }

    public void setRecommendedAuctions(List<Auction> recommendedAuctions) {
        this.recommendedAuctions = recommendedAuctions;
    }
    
    
    @PostConstruct
    public void init() {
        this.recommendedAuctions= this.user.getRecomendedAuction(); 
    }

    
}
