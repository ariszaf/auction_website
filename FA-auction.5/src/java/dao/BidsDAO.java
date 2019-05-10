/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.JPAResourceBean;

import entities.Bids;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author lumberjack
 */
public class BidsDAO {
    
    public List<Bids> getAuctions() {
        List<Bids> bids = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("Bids.findAll");
       bids = q.getResultList();

        tx.commit();
        em.close();
        return bids;
    }
    public Bids select_username(String bidderUsername) {
        
        Bids bid = new Bids();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createNamedQuery("Bids.findByBidderUsername").setParameter("bidderUsername", bidderUsername);
        try{
            bid = (Bids)q.getSingleResult();
        }
        catch(NoResultException e){
            bid = null;
        }
        tx.commit();
        em.close();
        return bid;      
    }
    
    public String insert(Bids bid){ 
        try {
            EntityManager em = JPAResourceBean.getEMF().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(bid);
            tx.commit();
            em.close();
            return "Your auction inserted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public List<Bids> getMyBids(String username) {  
        List<Bids> bids = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createNamedQuery("Bids.findByBidderUsername").setParameter("bidderUsername", username);
        bids = q.getResultList();
        
        tx.commit();
        em.close();
        return bids;
    }
}
