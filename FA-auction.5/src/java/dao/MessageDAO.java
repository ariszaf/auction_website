/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.JPAResourceBean;

import entities.Message;
import java.util.ArrayList;

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
public class MessageDAO {

    public MessageDAO() {
    }
    
    
    
    public List<Message> getMessages() {
        List<Message> mess = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("Message.findAll");
        mess = q.getResultList();

        tx.commit();
        em.close();
        return mess;
    }
    
    public String remove(String messageId) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        Message mess = em.find(Message.class,Integer.parseInt(messageId));
        //Query q = em.createNativeQuery("select * from administrator a where a.username = \"" + username+ "\"");
        
        try{
            em.remove(mess);
        }
        catch(IllegalArgumentException e){
           em.close();
           return "Message "+messageId+" does not exist";
        }
               
        tx.commit();
        em.close();
        return "Admin "+messageId+" removed";      
    }
    
    public Message select(String messageId) {
        
        Message mess = new Message();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //Query q = em.createNativeQuery("Select * from user u where u.username = \"" + username +"\"",User.class);
        Query q = em.createNamedQuery("Message.findByMessageId").setParameter("messageId", messageId);
        try{
            mess = (Message) q.getSingleResult();
        }
        catch(NoResultException e){
            mess = null;
        }
        tx.commit();
        em.close();
        return mess;      
    }
    
    
    public List<Message> incomingMail(String username){
        List<Message> mess = new ArrayList();
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        Query q = em.createQuery("SELECT m from Message m where m.recipientUsername.username = :username and m.deletedRecipient = false ");
        q.setParameter("username", username);
        mess = q.getResultList();
        tx.commit();
        em.close();
        return mess;
        
        
    }
    
    
    public List<Message> outgoingMail(String username){
        List<Message> mess = new ArrayList();
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        Query q = em.createQuery("SELECT m from Message m where m.senderUsername.username = :username and m.deletedSender = false ");
        q.setParameter("username", username);
        mess = q.getResultList();
        tx.commit();
        em.close();
        return mess;
        
    }
    
    public Long newMail(String username){
        Message mess = new Message();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("Select count(m.messageId) from Message m where m.recipientUsername.username= :username AND m.opened = false AND m.deletedRecipient = false ");
        q.setParameter("username", username);
        
        Long countNewMessages = (Long) q.getSingleResult();
        
        tx.commit();
        em.close();
        return countNewMessages;
        
    }
    
    public String insert(Message mess){
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(mess);
        tx.commit();
        em.close();
        return "User "+mess.getMessageId()+" inserted";
    }
    
    public String update(Message mess) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();        
        em.merge(mess);
        tx.commit();
        em.close();
        
        return "OK";      
    } 
    
}
