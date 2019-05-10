package db;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean(name="jpaResourceBean", eager=true)
@ApplicationScoped
public class JPAResourceBean {

    protected static EntityManagerFactory emf;
    
    public static synchronized EntityManagerFactory getEMF (){
        if (emf == null){
            emf = Persistence.createEntityManagerFactory("FA-auctionPU");
        }
        return emf;
    }
}