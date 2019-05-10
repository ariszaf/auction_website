/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import db.JPAResourceBean;
import entities.User;
import entities.UsersHasAuctions;

import entities.UsersWonAuctions;
import entities.UsersWonAuctionsPK;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;



/**
 *
 * @author lumberjack
 */
public class UsersWonAuctionsDAO {
    
    
    
    public UsersWonAuctionsDAO() {
    }
    
    
    
    public List<UsersWonAuctions> getUsersWonAuctions() {
        List<UsersWonAuctions> uwo = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("UsersWonAuctions.findAll");
        uwo = q.getResultList();

        tx.commit();
        em.close();
        return uwo;
    }
    
    public String remove(String buyer_username ,Integer auctions_item_id ) {   
        UsersWonAuctions uwa;
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        
        
        
        Query q = em.createQuery("SELECT u FROM UsersWonAuctions u "
                + "WHERE u.usersWonAuctionsPK.buyerUsername = :buyer_username AND u.usersWonAuctionsPK.auctionsItemId = :auctions_item_id");
        q.setParameter("buyer_username",buyer_username );
        q.setParameter("auctions_item_id", auctions_item_id);
        uwa = (UsersWonAuctions) q.getSingleResult();
        
        try{
            em.remove(uwa);
        }
        catch(IllegalArgumentException e){
           em.close();
           return "UsersWonAuctions "+buyer_username +" does not exist";
        }
               
        tx.commit();
        em.close();
        return "UsersWonAuctions "+buyer_username+" removed";      
    }
    
    public  UsersWonAuctions select(String buyer_username ,Integer auctions_item_id ) {
        
        UsersWonAuctions uwa;
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //Query q = em.createNativeQuery("Select * from user u where u.username = \"" + username +"\"",User.class);
        Query q = em.createQuery("SELECT u FROM UsersWonAuctions u "
                + "WHERE u.usersWonAuctionsPK.buyerUsername = :buyer_username AND u.usersWonAuctionsPK.auctionsItemId = :auctions_item_id");
        
        q.setParameter("buyer_username",buyer_username );
        q.setParameter("auctions_item_id", auctions_item_id);
        
        try{
            uwa = (UsersWonAuctions) q.getSingleResult();
        }
        catch(NoResultException e){
            uwa = null;
        }
        tx.commit();
        em.close();
        return uwa;      
    }
    
    public UsersWonAuctions selectBuyer(int item_id) {
        
        UsersWonAuctions uwa = new UsersWonAuctions();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createNamedQuery("UsersWonAuctions.findByAuctionsItemId").setParameter("auctionsItemId", item_id);
        try{
            uwa = (UsersWonAuctions)q.getSingleResult();
        }
        catch(NoResultException e){
            uwa = null;
        }
        tx.commit();
        em.close();
        return uwa;      
    } 
    
    public String insert(UsersWonAuctions uwa){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(uwa);
        tx.commit();
        em.close();
        return "User inserted";
    }
    
    public List<UsersWonAuctions> getMyAuctionsRating_b(String username) {  
        List<UsersWonAuctions> ratings_b = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("SELECT u FROM UsersWonAuctions  u where u.rating > 0 AND u.usersWonAuctionsPK.buyerUsername = :buyerUsername").setParameter("buyerUsername", username);
        ratings_b = q.getResultList();
        
        tx.commit();
        em.close();
        return ratings_b;
    }
        
    public Ratings getPendingBuyierRating(String username){
        
        User user = new User();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = null;
        
        try{
            q = em.createQuery("SELECT uwa.rating, uha.auction.itemId FROM User u, UsersWonAuctions uwa, UsersHasAuctions uha WHERE u.username = :username AND uha MEMBER OF u.usersHasAuctionsCollection AND uha.auction.itemId = uwa.auction.itemId AND uwa.rating=null").setParameter("username", username);
        }
        catch(IllegalArgumentException el){
           String message = el.getMessage();
           message="";
        } 
        
        List<Integer> seller = new ArrayList<Integer>();
        List<Integer> item_id= new ArrayList<Integer>();
        List<Object[]> rat = q.getResultList();
        for(Object[] obj :rat){
           seller.add((Integer)obj[0]);
           item_id.add((Integer)obj[1]);
        }
        
        Ratings rate = new Ratings(seller,item_id);
             
        tx.commit();
        em.close();
        
        return rate;
    }

    public Ratings getSubBuyierRating(String  username){
        
        User user = new User();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = null;
        
        try{
            q = em.createQuery("SELECT uwa.rating, uha.auction.itemId FROM User u, UsersWonAuctions uwa, UsersHasAuctions uha WHERE u.username = :username AND uha MEMBER OF u.usersHasAuctionsCollection AND uha.auction.itemId = uwa.auction.itemId AND uwa.rating>0").setParameter("username", username);
        }
        catch(IllegalArgumentException el){
           String message = el.getMessage();
           message="";
        } 
        
        List<Integer> seller = new ArrayList<Integer>();
        List<Integer> item_id= new ArrayList<Integer>();
        List<Object[]> rat = q.getResultList();
        for(Object[] obj :rat){
           seller.add((Integer)obj[0]);
           item_id.add((Integer)obj[1]);
        }
        
        Ratings rate = new Ratings(seller,item_id);
             
        tx.commit();
        em.close();
        
        return rate;
    }
    
    public String update(UsersWonAuctions userswonauctions) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();        
        em.merge(userswonauctions);
        tx.commit();
        em.close();
        
        return "Auction rated";      
    }
    
    public List<Integer> getMyRatingBuyer(String username) {  
        List<Integer> myratings = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("SELECT u.rating FROM UsersWonAuctions u WHERE u.usersWonAuctionsPK.buyerUsername = :buyerUsername AND u.rating!=null");
        q.setParameter("buyerUsername", username);
        myratings = q.getResultList();
        
        tx.commit();
        em.close();
        return myratings;
    }
    
    public List<SellerBuyerItem> updateWonAuction() {
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNativeQuery("Select b.bidder_username, a.item_id from auction a JOIN bids b on a.item_id = b.item_id where a.item_id NOT IN(select auctions_item_id from users_won_auctions) AND a.item_id IN (select item_id from bids) AND a.item_id NOT IN (select item_id from auction where auction.ends > now()) AND a.currently=b.bid_amount");
        
        List<Object[]> uwalist = q.getResultList();
        
        if(uwalist==null || uwalist.isEmpty()) return null;
        
        List<Integer> items = new ArrayList<>();
        List<SellerBuyerItem> sbiL = new ArrayList();
        
        int i =0;
        while(i < uwalist.size()){
            String bidder =(String) uwalist.get(i)[0];
            int itemid = (int) uwalist.get(i)[1];
            items.add(itemid);
            SellerBuyerItem sbItem = new SellerBuyerItem(bidder,itemid); 
            sbiL.add(sbItem);
            
            
            UsersWonAuctions uwa = new UsersWonAuctions();
            UsersWonAuctionsPK uwapk = new UsersWonAuctionsPK ();
            uwapk.setAuctionsItemId(itemid);
            uwapk.setBuyerUsername(bidder);
            uwa.setUsersWonAuctionsPK(uwapk);
            em.persist(uwa);
            i++;
        }
        
 
        Query q1 = em.createQuery("SELECT u.user.username , u.auction.itemId FROM UsersHasAuctions u WHERE u.usersHasAuctionsPK.auctionsItemId IN :items");
        q1.setParameter("items",items);
        List<Object[]> uha = q1.getResultList();
        
        for(Object[] obj: uha ){ 
           int temp1 = (int)obj[1];
           String temp2 =(String)obj[0];
           
           for(SellerBuyerItem sbItem : sbiL){
               if(sbItem.getItem() == temp1){
                   sbItem.setSeller(temp2);
                   break;
               }
           } 
        }
        tx.commit();
        em.close();
        return sbiL;
    }
    
    public class SellerBuyerItem {
        private String seller ;
        private String buyer ;
        private int item ;        

        public SellerBuyerItem() {
        }

        public SellerBuyerItem(String buyer, int item) {
            this.buyer = buyer;
            this.item = item;
        }

        
        public String getSeller() {
            return seller;
        }

        public void setSeller(String seller) {
            this.seller = seller;
        }

        public String getBuyer() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer = buyer;
        }

        public int getItem() {
            return item;
        }

        public void setItem(int item) {
            this.item = item;
        }
    }
    
}