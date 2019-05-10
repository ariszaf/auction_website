package dao;

import db.JPAResourceBean;
import entities.Auction;
import entities.Category;
import entities.UsersHasAuctions;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@ManagedBean
@SessionScoped
public class AuctionDAO {
    public String insert(Auction auction) {
        try {
            EntityManager em = JPAResourceBean.getEMF().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(auction);
            tx.commit();
            em.close();
            return "Your auction inserted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
        
    public String update(Auction auction) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();        
        em.merge(auction);
        tx.commit();
        em.close();
        
        return "Auction "+auction.getName()+" updated";      
    } 
    
    public Auction select(int item_id) {
        
        Auction auction = new Auction();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createNamedQuery("Auction.findByItemId").setParameter("itemId", item_id);
        try{
            auction = (Auction)q.getSingleResult();
        }
        catch(NoResultException e){
            auction = null;
        }
        tx.commit();
        em.close();
        return auction;      
    }
    
    public String remove(int item_id) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();        
        tx.begin();
        Auction auction = em.find(Auction.class,item_id);
        
        try{
            em.remove(auction);
        }
        
        catch(IllegalArgumentException e){
           em.close();
           return "Auction does not exist";
        }          
        tx.commit();
        em.close();
        return "Auction " + item_id + " removed successfully!";      
    }

    public List<Auction> getAuctions() {
        List<Auction> auctions = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("Auction.findAll");
        auctions = q.getResultList();

        tx.commit();
        em.close();
        return auctions;
    }
       
    public Auction selectUserAuction(int item_id) {
        
        Auction auction = new Auction();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("Auction.findByItemId").setParameter("itemId", item_id);
        try{
            auction = (Auction)q.getSingleResult();
        }
        catch(NoResultException e){
            auction = null;
        }
        tx.commit();
        em.close();
        return auction;      
    }
    
    public List<Auction> getAllActiveAuctions(Date now) {  
        List<Auction> auctions = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("SELECT a FROM Auction a WHERE a.ends > :ends AND a.started < :started");
        q.setParameter("started", now);
        q.setParameter("ends", now);
        auctions = q.getResultList();
        
        tx.commit();
        em.close();
        return auctions;
    }  
    
    public List<Auction> getActiveAuctionsc1(Date now, String country, String keyword_selected) {  
        List<Auction> auctions = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("SELECT a FROM Auction a WHERE a.ends > :ends AND a.started < :started AND a.description like :description AND a.country like :country");
        q.setParameter("started", now);
        q.setParameter("ends", now);
        q.setParameter("description", "%" + keyword_selected + "%");
        q.setParameter("country", "%" + country + "%");
        auctions = q.getResultList();
        
        tx.commit();
        em.close();
        return auctions;
    }
    
    public List<Auction> getActiveAuctionsc2(Date now, String country, String keyword_selected, Integer max_price) {  
        List<Auction> auctions = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("SELECT a FROM Auction a WHERE a.ends > :ends AND a.started < :started AND a.description like :description AND a.country like :country AND a.buyPrice <= :buyPrice");
        q.setParameter("started", now);
        q.setParameter("ends", now);
        q.setParameter("description", "%" + keyword_selected + "%");
        q.setParameter("country", "%" + country + "%");
        q.setParameter("buyPrice", max_price);
        auctions = q.getResultList();
        
        tx.commit();
        em.close();
        return auctions;
    }
    
    
    public List<Auction> getActiveAuctionsc3(Date now, String country, String keyword_selected, String category){
        List<Auction> auctions = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("SELECT a FROM Auction a JOIN a.categoryCollection c WHERE c.name= :category AND a.ends > :ends AND a.started < :started AND a.description like :description AND a.country like :country");
        q.setParameter("started", now);
        q.setParameter("ends", now);
        q.setParameter("description", "%" + keyword_selected + "%");
        q.setParameter("country", "%" + country + "%");
        q.setParameter("category", category);
        auctions= q.getResultList();

        tx.commit();
        em.close();
        
        return auctions;
    }

    public List<Auction> getActiveAuctionsc4(Date now, String country, String keyword_selected, String category, Integer max_price){
        List<Auction> auctions = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("SELECT a FROM Auction a JOIN a.categoryCollection c WHERE c.name= :category AND a.ends > :ends AND a.started < :started AND a.description like :description AND a.country like :country AND a.buyPrice <= :buyPrice");
        q.setParameter("started", now);
        q.setParameter("ends", now);
        q.setParameter("description", "%" + keyword_selected + "%");
        q.setParameter("country", "%" + country + "%");
        q.setParameter("category", category);
        q.setParameter("buyPrice", max_price);
        auctions = q.getResultList();
        
        tx.commit();
        em.close();
        return auctions;
    }
}