package dao;

import db.JPAResourceBean;
import entities.Administrator;
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
public class SellerDAO {
    
    public String insert(Seller seller){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(seller);
        tx.commit();
        em.close();
        return null;
    }
    public Seller select(String username){
        
        Seller seller = new Seller();
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createNamedQuery("Seller.findByUsername").setParameter("username", username);
        try{
            seller = (Seller) q.getSingleResult();
        }
        catch(NoResultException e){
            seller = null;
        } 
        tx.commit();
        em.close();
        return seller;
    }
    
    
    public String remove(String username) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        Seller seller = em.find(Seller.class, username);
        //Query q = em.createNativeQuery("select * from administrator a where a.username = \"" + username+ "\"");
        
        try{
            em.remove(seller);
        }
        catch(IllegalArgumentException e){
           em.close();
           return "Seller "+username+" does not exist";
        }
               
        tx.commit();
        em.close();
        return "Seller "+username+" removed";      
    }
}
