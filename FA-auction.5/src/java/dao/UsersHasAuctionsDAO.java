package dao;

import db.JPAResourceBean;
import entities.Auction;
import entities.User;
import entities.UsersHasAuctions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UsersHasAuctionsDAO {
    
    public Collection<UsersHasAuctions> getUsersHasAuctions() {
        Collection<UsersHasAuctions> uho = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("UsersHasAuctions.findAll");
        uho = q.getResultList();

        tx.commit();
        em.close();
        return uho;
    }
    
     public  UsersHasAuctions select(String seller_username ,Integer auctions_item_id ) {
        
        UsersHasAuctions uha;
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //Query q = em.createNativeQuery("Select * from user u where u.username = \"" + username +"\"",User.class);
        Query q = em.createQuery("SELECT u FROM UsersHasAuctions u "
                + "WHERE u.usersHasAuctionsPK.sellerUsername = :seller_username AND u.usersHasAuctionsPK.auctionsItemId = :auctions_item_id");
        
        q.setParameter("seller_username",seller_username );
        q.setParameter("auctions_item_id", auctions_item_id);
        
        try{
            uha = (UsersHasAuctions) q.getSingleResult();
        }
        catch(NoResultException e){
            uha = null;
        }
        tx.commit();
        em.close();
        return uha;      
    }
    
    public String insert(UsersHasAuctions uha){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(uha);

        tx.commit();
        em.close();
        return "User inserted";
    }

    public UsersHasAuctions selectSeller(int item_id) {
        
        UsersHasAuctions uha = new UsersHasAuctions();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createNamedQuery("UsersHasAuctions.findByAuctionsItemId").setParameter("auctionsItemId", item_id);
        try{
            uha = (UsersHasAuctions)q.getSingleResult();
        }
        catch(NoResultException e){
            uha = null;
        }
        tx.commit();
        em.close();
        return uha;      
    }

    public List<Auction> getMyAuctions(String username) {  
        List<Auction> auctions = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("SELECT u.auction FROM UsersHasAuctions u where u.usersHasAuctionsPK.sellerUsername = :sellerUsername").setParameter("sellerUsername", username);
        auctions = q.getResultList();
        
        tx.commit();
        em.close();
        return auctions;
    }
    
    public List<Auction> getMyAuctionsAsBuyer(String username) {  
        List<Auction> auctions = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("SELECT u.auction FROM bids u where u.usersHasAuctionsPK.bidderUsername = :buyerUsername").setParameter("buyerUsername", username);
        auctions = q.getResultList();
        
        tx.commit();
        em.close();
        return auctions;
    }
    
    public List<UsersHasAuctions> getMyAuctionsRating_s(String username) {  
        List<UsersHasAuctions> ratings_s = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("SELECT u FROM UsersHasAuctions  u where u.rating > 0 AND u.usersHasAuctionsPK.sellerUsername = :sellerUsername").setParameter("sellerUsername", username);
        ratings_s = q.getResultList();
        
        tx.commit();
        em.close();
        return ratings_s;
    }
    
    public Ratings getPendingSellerRating(String username){
        
        User user = new User();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = null;
        
        try{
            q = em.createQuery("SELECT uha.rating, uha.auction.itemId FROM User u , UsersWonAuctions uwa , UsersHasAuctions uha WHERE u.username = :username AND uwa MEMBER OF u.usersWonAuctionsCollection AND uwa.auction.itemId = uha.auction.itemId AND uha.rating=null").setParameter("username", username);
        }
        catch(IllegalArgumentException el){
           String message = el.getMessage();
           message="";
        } 
        
        List<Integer> buyier = new ArrayList<Integer>();
        List<Integer> item_id= new ArrayList<Integer>();
        List<Object[]> rat = q.getResultList();
        for(Object[] obj :rat){
           buyier.add((Integer)obj[0]);
           item_id.add((Integer)obj[1]);
        }
        
        Ratings rate = new Ratings(buyier,item_id);
             
        tx.commit();
        em.close();
        
        return rate;
    }

    public Ratings getSubSellerRating(String  username){
        
        User user = new User();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = null;
        
        try{
            q = em.createQuery("SELECT uha.rating, uha.auction.itemId FROM User u , UsersWonAuctions uwa , UsersHasAuctions uha WHERE u.username = :username AND uwa MEMBER OF u.usersWonAuctionsCollection AND uwa.auction.itemId = uha.auction.itemId AND uha.rating>0").setParameter("username", username);
        }
        catch(IllegalArgumentException el){
           String message = el.getMessage();
           message="";
        } 
        
        List<Integer> buyier = new ArrayList<Integer>();
        List<Integer> item_id= new ArrayList<Integer>();
        List<Object[]> rat = q.getResultList();
        for(Object[] obj :rat){
           buyier.add((Integer)obj[0]);
           item_id.add((Integer)obj[1]);
        }
        
        Ratings rate = new Ratings(buyier,item_id);
             
        tx.commit();
        em.close();
        
        return rate;
    }

    
    public Ratings getAllRatings(String  username){
        
        User user = new User();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = null;
        
        try{
            q = em.createQuery("SELECT uwa.rating, uha.rating, uha.auction.itemId FROM User u , UsersWonAuctions uwa , UsersHasAuctions uha WHERE u.username = :username AND uwa MEMBER OF u.usersWonAuctionsCollection AND uwa.auction.itemId = uha.auction.itemId").setParameter("username", username);
        }
        catch(IllegalArgumentException el){
           String message = el.getMessage();
           message="";
        } 
        
        List<Integer> seller = new ArrayList<Integer>();
        List<Integer> buyier = new ArrayList<Integer>();
        List<Integer> item_id= new ArrayList<Integer>();
        List<Object[]> rat = q.getResultList();
        for(Object[] obj :rat){
           seller.add((Integer)obj[0]);
           buyier.add((Integer)obj[1]);
           item_id.add((Integer)obj[2]);
        }
        
        Ratings rate = new Ratings(seller,buyier,username,item_id); 
             
        tx.commit();
        em.close();
        
        return rate;
    }
      
    public String update(UsersHasAuctions usershasauctions) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();        
        em.merge(usershasauctions);
        tx.commit();
        em.close();
        
        return "Auction rated";      
    } 
    
    public List<Integer> getMyRatingSellers(String username) {  
        List<Integer> myratings = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("SELECT u.rating FROM UsersHasAuctions u WHERE u.usersHasAuctionsPK.sellerUsername = :sellerUsername AND u.rating!=null");
        q.setParameter("sellerUsername", username);
        myratings = q.getResultList();
        
        tx.commit();
        em.close();
        return myratings;
    }
}