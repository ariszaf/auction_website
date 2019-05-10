package dao;

import db.JPAResourceBean;
import entities.User;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@ManagedBean(name = "userDAO")
@RequestScoped
public class UserDAO {
    
    
    public List<User> getUsers() {
        List<User> users = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("User.findAll");
        users = q.getResultList();

        tx.commit();
        em.close();
        return users;
    }
    
      public List<User> getValidatedUsers() {
        List<User> users = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("User.findAllValidated");
        users = q.getResultList();

        tx.commit();
        em.close();
        return users;
    }
    
    public User select(String username) {
        
        User user = new User();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //Query q = em.createNativeQuery("Select * from user u where u.username = \"" + username +"\"",User.class);
        Query q = em.createNamedQuery("User.findByUsername").setParameter("username", username);
        try{
            user = (User) q.getSingleResult();
        }
        catch(NoResultException e){
            user = null;
        } 
        tx.commit();
        em.close();
        return user;      
    }

    public String remove(String username) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        User user = em.find(User.class,username);
        
        try{
             em.remove(user);
        }
        catch(IllegalArgumentException e){
           em.close();
           return "User does not exist";
        }
               
        tx.commit();
        em.close();
        return "User "+username+" removed";      
    }
    
    public Double selectSellerRating(String seller_username){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        
        Query q = em.createQuery("SELECT avg(u.rating) FROM UsersHasAuctions u "
                + "WHERE u.usersHasAuctionsPK.sellerUsername = :seller_username AND u.rating IS NOT NULL");
        q.setParameter("seller_username",seller_username);
        
        Double rating;
        try{
            rating = (Double)q.getSingleResult();
        }
        catch(NoResultException e){
            System.err.print("Something whent wrong in getSingleResults");
            rating = -1.0;
        } 
        tx.commit();
        em.close();
        return rating;
    }
    
    public void updateSellerRating(String seller_username){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        
        Query q = em.createQuery("SELECT avg(u.rating) FROM UsersHasAuctions u "
                + "WHERE u.usersHasAuctionsPK.sellerUsername = :seller_username AND u.rating IS NOT NULL");
        q.setParameter("seller_username",seller_username);
        
        Query q1 = em.createNamedQuery("User.findByUsername").setParameter("username",seller_username);
        
        Double rating;
        User user;
        try{
            user =(User)q1.getSingleResult();
            rating = (Double)q.getSingleResult();
            user.setBidRating(rating);
            em.merge(user); 
        }
        catch(NoResultException e){
            System.err.print("Something whent wrong in getSingleResults");
            rating = -1.0;
        } 
        tx.commit();
        em.close();
    }
    
    public void updateBuyerRating(String buyer_username){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        
        Query q = em.createQuery("SELECT avg(u.rating) FROM UsersWonAuctions u "
                + "WHERE u.usersWonAuctionsPK.buyerUsername = :buyer_username AND u.rating IS NOT NULL");
        q.setParameter("buyer_username",buyer_username);
        
        Query q1 = em.createNamedQuery("User.findByUsername").setParameter("username",buyer_username);
        
        
        Double rating;
        User user;
        try{
            user =(User)q1.getSingleResult();
            rating = (Double)q.getSingleResult();
            user.setBidRating(rating);
            em.merge(user);  
        }
        catch(NoResultException e){
            System.err.print("Something whent wrong in getSingleResults");
            rating= 0.0;
        } 
        tx.commit();
        em.close();
        
    } 
    
    public Double selectBuyerRating(String buyer_username){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        
        Query q = em.createQuery("SELECT avg(u.rating) FROM UsersWonAuctions u "
                + "WHERE u.usersWonAuctionsPK.buyerUsername = :buyer_username AND u.rating IS NOT NULL");
        q.setParameter("buyer_username",buyer_username);
        
        Double rating;
        try{
            rating = (Double)q.getSingleResult();
        }
        catch(NoResultException e){
            System.err.print("Something whent wrong in getSingleResults");
            rating= 0.0;
        } 
        tx.commit();
        em.close();
        return rating;
    }
    
    

    
    public String update(User user) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();        
        em.merge(user);
        tx.commit();
        em.close();
        
        return "User "+user.getUsername()+" updated";      
    } 


    public User selectEmail(String email) {
        
        User user = new User();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createNamedQuery("User.findByEmail").setParameter("email", email);
        try{
            user = (User) q.getSingleResult();
        }
        catch(NoResultException e){
            user = null;
        } 
        tx.commit();
        em.close();
        return user;      
    }
 
    public String insert(User user){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            em.persist(user);
        }
        catch (Exception e) {
            return e.getMessage();
        }
        tx.commit();
        em.close();
        return "Thank you " + user.getUsername()+ " for registering your new account! Your account is currently pending approval by the site administrator.";
    }
    
    public String getSellerRating(String  username){
        
        User user = new User();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = null;
        
        try{
            q = em.createQuery("SELECT uwa.rating, uha.rating FROM User u , UsersWonAuctions uwa , UsersHasAuctions uha WHERE u.username = :username AND uwa MEMBER OF u.usersWonAuctionsCollection AND uwa.auction.itemId = uha.auction.itemId").setParameter("username", username);
        }
        catch(IllegalArgumentException el){
           String message = el.getMessage();
           message="";
        } 
        
        List<Double> seller = new ArrayList<Double>();
        List<Double> buyier = new ArrayList<Double>();
        
        List<Object[]> rat = q.getResultList();
        for(Object[] obj :rat){
           seller.add((Double)obj[0]);
           buyier.add((Double)obj[1]);
        }
        
        
        
        tx.commit();
        em.close();
        return null; 
    } 
    
    
   public class Bla  {
       Double sellerRating;
       Double buyeirRating;

        public Bla(Double sellerRating, Double buyeirRating) {
            this.sellerRating = sellerRating;
            this.buyeirRating = buyeirRating;
        }
       
       
   }

    
//    
//    private User setUser(User us){
//        User user = new User();
//        user.setAddress(us.getAddress());
//        user.setCity(us.getCity());
//        user.setCountry(us.getCountry());
//        user.setEmail(us.getEmail());
//        user.setLatitude(us.getLatitude());
//        user.setLongitude(us.getLongitude());
//        user.setName(us.getName());
//        user.setSurname(us.getSurname());
//        user.setPassword(us.getPassword());
//       
//            
//    }
//    

//    public String insertUser(User user) {
//        String retMessage = "";
//        EntityManager em = jpaResourceBean.getEMF().createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        try {
//            em.persist(user);
//            tx.commit();
//            retMessage = "ok";
//            return retMessage;
//        } catch (PersistenceException e) {
//            if (tx.isActive()) {
//                tx.rollback();
//            }
//            retMessage = e.getMessage();
//            return retMessage;
//        } finally {
//            em.close();
//        }
//    }

    // update
    // delete
   
    
    
}
