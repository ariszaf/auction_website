package dao;

import db.JPAResourceBean;
import entities.Administrator;
import entities.Buyer;
import entities.Seller;

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

@ManagedBean
@RequestScoped
public class BuyerDAO {

    public String insert(Buyer buyer){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(buyer);
        tx.commit();
        em.close();
        return null;
    }
    
    public Buyer select(String username){
        
        Buyer buyer = new Buyer();
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createNamedQuery("Buyer.findByUsername").setParameter("username", username);
        try{
            buyer = (Buyer) q.getSingleResult();
        }
        catch(NoResultException e){
            buyer = null;
        } 
        tx.commit();
        em.close();
        return buyer;
    }
    
    public String remove(String username) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        Buyer buyer = em.find(Buyer.class, username);
        //Query q = em.createNativeQuery("select * from administrator a where a.username = \"" + username+ "\"");
        
        try{
            em.remove(buyer);
        }
        catch(IllegalArgumentException e){
           em.close();
           return "Buyer "+username+" does not exist";
        }
               
        tx.commit();
        em.close();
        return "Buyer "+username+" removed";      
    }
}
