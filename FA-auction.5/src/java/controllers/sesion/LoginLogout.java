/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.sesion;


import controllers.collaboration.RecommendByCollaboration;
import dao.AdministratorDAO;
import dao.BuyerDAO;
import dao.MessageDAO;
import dao.SellerDAO;
import dao.UserDAO;
import dao.UsersWonAuctionsDAO;
import dao.UsersWonAuctionsDAO.SellerBuyerItem;
import entities.Auction;
import entities.Message;
import entities.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


/**
 *
 * @author lumberjack
 */
@ManagedBean
@SessionScoped
public class LoginLogout implements Serializable {

    String username;
    String password;
    private List<Auction> recomendedAuction;

    User user;
    
    Long unreadMessages ;

    public Long getUnreadMessages() {
        MessageDAO messdao =new MessageDAO();
        Long count = messdao.newMail(this.username);
        this.setUnreadMessages(count);
        return unreadMessages;
    }

    public void setUnreadMessages(Long unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public List<Auction> getRecomendedAuction() {
        return recomendedAuction;
    }

    public void setRecomendedAuction(List<Auction> recomendedAuction) {
        this.recomendedAuction = recomendedAuction;
    }
    
    
    
    private Integer accountype;

   
    
    public Integer getAccountype() {
        return accountype;
    }

    public void setAccountype(Integer accountype) {
        this.accountype = accountype;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    
    public boolean isLoggedIn() {
        return user != null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    
    public LoginLogout() {
        UsersWonAuctionsDAO dao = new UsersWonAuctionsDAO();
        List<SellerBuyerItem> sbiL = dao.updateWonAuction();
        if(sbiL!=null){
            for(SellerBuyerItem sbi: sbiL){
                createMessage(sbi);
            }
        }
    }
    
    private void createMessage(SellerBuyerItem sbi){
        String sellerMessage = new String("Congratulation you just sold item " + ((Integer)sbi.getItem()).toString() + " to buyer " + sbi.getBuyer() +
                ". Thank you for choosing FA-Auctions for your sale and we wish you many more sales to come. Please take the time to rate your sale. Thank you." );
        String buyerMessage = new String("Congratulation you just bought item " + ((Integer)sbi.getItem()).toString() + " from seller " + sbi.getSeller() +
                ". Thank you for choosing FA-Auctions for your purchaces . Please take the time to rate your purchace. Thank you." );
        
        MessageDAO messdao = new MessageDAO();
        
        UserDAO userdao = new UserDAO();
        User seller = userdao.select(sbi.getSeller());
        User buyer = userdao.select(sbi.getBuyer());
        User admin = userdao.select("aa");
        
        if(seller== null || buyer==null) return;
        
        Message mess = new Message();
        
        mess.setRecipientUsername(seller);
        mess.setSenderUsername(admin);
        mess.setDateCreated( new Date());
        mess.setDateSent(new Date());
        mess.setOpened(false);
        mess.setDeletedRecipient(false);
        mess.setDeletedSender(true);
        mess.setSubject("You made a Sale");
        mess.setText(sellerMessage);
        
        messdao.insert(mess);
        
        mess = new Message();
        
        mess.setRecipientUsername(buyer);
        mess.setSenderUsername(admin);
        mess.setDateCreated( new Date());
        mess.setDateSent(new Date());
        mess.setOpened(false);
        mess.setDeletedRecipient(false);
        mess.setDeletedSender(true);
        mess.setSubject("You made a Purchace");
        mess.setText(buyerMessage);
        
        messdao.insert(mess);
        return; 
    }
    
    public String login(){
        UserDAO userdao = new UserDAO();
        boolean valid;
        user = userdao.select(username);
        if(user==null){
            valid = false;
        }
        else {
            valid = (user.getPassword().compareTo(password)==0);
        }
        
        if(valid){
   
            this.set_acount_type(username);
            
            MessageDAO messdao= new MessageDAO();
            this.unreadMessages =messdao.newMail(username);
            
            if(user.getValidation()){
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("user", this);
                RecommendByCollaboration rbc = new RecommendByCollaboration();
                if(user.getAccount()==1){
                    return "restricted/admin/Search-Users.xhtml";
                }
                else{
                    
                    this.recomendedAuction = rbc.findRecommendations(5, username);
                    return "restricted/user/homepage.xhtml";
                }
            }
            else{
                HttpSession session = SessionUtils.getSession();
                session.invalidate();
                return "visitor/pending-signup.xhtml" ;
            }
        }
        else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error Login",null);  
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error login"));
            return "/index.xhtml";
        }    
     
    }
    
    public String logout() {
	HttpSession session = SessionUtils.getSession();
	session.invalidate();
	return "/index?faces-redirect=true";
    }
    
    public boolean set_acount_type(String username){
        AdministratorDAO  adminDao = new AdministratorDAO();
        if(adminDao.select(username)==null){
            BuyerDAO buyer = new BuyerDAO();
            if(buyer.select(username)==null){
                SellerDAO seller = new SellerDAO();
                if(seller.select(username)==null){
                    return false;
                }
                else{
                    this.setAccountype(2);
                }
            }
            else{
                this.setAccountype(3);
            }
        }
        else{
            this.setAccountype(1);
        }
        return true;
    }
    
}
