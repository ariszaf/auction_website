/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.JPAResourceBean;
import entities.Administrator;

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

/**
 *
 * @author lumberjack
 */
@ManagedBean
@RequestScoped
public class AdministratorDAO {
    
    
    public List<Administrator> getAdmin() {
        List<Administrator> admins = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("Administrator.findAll");
        admins = q.getResultList();

        tx.commit();
        em.close();
        return admins;
    }
    
    public String remove(String username) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        Administrator admin = em.find(Administrator.class, username);
        //Query q = em.createNativeQuery("select * from administrator a where a.username = \"" + username+ "\"");
        
        try{
            em.remove(admin);
        }
        catch(IllegalArgumentException e){
           em.close();
           return "Admin "+username+" does not exist";
        }
               
        tx.commit();
        em.close();
        return "Admin "+username+" removed";      
    }
    
    public Administrator select(String username) {
        
        Administrator admin = new Administrator();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //Query q = em.createNativeQuery("Select * from user u where u.username = \"" + username +"\"",User.class);
        Query q = em.createNamedQuery("Administrator.findByUsername").setParameter("username", username);
        try{
            admin = (Administrator) q.getSingleResult();
        }
        catch(NoResultException e){
            admin = null;
        }
        tx.commit();
        em.close();
        return admin;      
    }
    
    public String insert(Administrator admin){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(admin);
        tx.commit();
        em.close();
        return "User "+admin.getUsername()+" inserted";
    }

    public AdministratorDAO() {
    }
    
    
    
    
    
}
