package dao;

import db.JPAResourceBean;
import entities.Auction;
import entities.Photo;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@ManagedBean
@SessionScoped
public class PhotoDAO {
    public String insert(Photo photo) {
        try {
            EntityManager em = JPAResourceBean.getEMF().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(photo);
            tx.commit();
            em.close();
            return "OK";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String remove(int photo_id) {   
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();        
        tx.begin();
        Photo photo = em.find(Photo.class,photo_id);
        
        try{
            em.remove(photo);
        }
        
        catch(IllegalArgumentException e){
           em.close();
           return "Photo does not exist";
        }
               
        tx.commit();
        em.close();
        return "Photo" + photo_id + "removed";      
    }

    public List<Photo> getPhotos() {
        List<Photo> photos = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createNamedQuery("Photo.findAll");
        photos = q.getResultList();

        tx.commit();
        em.close();
        return photos;
    }
    
    public Photo select(int photo_id) {
        
        Photo photo = new Photo();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createNamedQuery("Photo.findByPhotoId");
        try{
            photo = (Photo)q.getSingleResult();
        }
        catch(NoResultException e){
            photo = null;
        }
        tx.commit();
        em.close();
        return photo;      
    }
    
    public Photo selectbyitem(String name, int item_id) {
        
        Photo photo = new Photo();
        
        EntityManager em = JPAResourceBean.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("SELECT p.photoId FROM Photo p WHERE p.name = :name AND p.itemId = :itemId").setParameter("name", name).setParameter("itemId", item_id);
        try{
            photo = (Photo)q.getSingleResult();
        }
        catch(NoResultException e){
            photo = null;
        }
        tx.commit();
        em.close();
        return photo;      
    }
    
    public List<Photo> getItemPhotos(Auction item_id) {
        List<Photo> photos = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("SELECT p.name FROM Photo p WHERE p.itemId = :itemId").setParameter("itemId", item_id);
        photos = q.getResultList();
  
        tx.commit();
        em.close();
        return photos;
    }
    
    public List<Photo> getItemPhotosde(Auction item_id) {
        List<Photo> photos = null;
        EntityManagerFactory factory = JPAResourceBean.getEMF();
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q = em.createQuery("SELECT p FROM Photo p WHERE p.itemId = :itemId").setParameter("itemId", item_id);
        photos = q.getResultList();
  
        tx.commit();
        em.close();
        return photos;
    }
    
}

