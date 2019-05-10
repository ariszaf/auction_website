package dao;

import db.JPAResourceBean;
import entities.Category;
import entities.User;
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
public class CategoryDAO {
    public String insert(Category category) {
        try {
            EntityManager em = JPAResourceBean.getEMF().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(category);
            tx.commit();
            em.close();
            return "OK";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
   
    public String remove(String name) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        Category category = em.find(Category.class,name);
        
        try{
             em.remove(category);
        }
        catch(IllegalArgumentException e){
           em.close();
           return "Category does not exist";
        }
               
        tx.commit();
        em.close();
        return "Category" + name + "removed";      
    }

    public List<Category> getCategories() {
        List<Category> categories = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("Category.findAll");
        categories = q.getResultList();

        tx.commit();
        em.close();
        return categories;
    }
    
     // 
    
    public List<Category> getInterestingCategories(String username) {
        List<Category> categories = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("Category.findInterestingCategories").setParameter("username", username);
        categories = q.getResultList();

        tx.commit();
        em.close();
        return categories;
    }
    

    public List<Category> getCategoriesname() {
        List<Category> categories = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createQuery("SELECT c.name FROM Category c");
        categories = q.getResultList();

        tx.commit();
        em.close();
        return categories;
    }
    
    public Category select(String name) {
       
        Category category = new Category();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createNamedQuery("Category.findByName").setParameter("name", name);
        try{
            category = (Category)q.getSingleResult();
        }
        catch(NoResultException e){
            category = null;
        }
        tx.commit();
        em.close();
        return category;      
    } 
} 

