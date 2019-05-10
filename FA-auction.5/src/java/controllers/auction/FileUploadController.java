package controllers.auction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
  
@ManagedBean
@ViewScoped

public class FileUploadController implements Serializable{
    private String destination="/home/lumberjack/NetBeansProjects/FA-auction.5/web/resources/images/";
    private List<String> photonames = new ArrayList<String>();
    
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public List<String> getPhotonames() {
        return photonames;
    }

    public void setPhotonames(List<String> photonames) {
        this.photonames = photonames;
    }
    
    public void upload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("The file was uploaded succesfully!");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        photonames.add(event.getFile().getFileName());
  
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFile(String fileName, InputStream in) {
           try { 
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(destination + fileName));               
                int read = 0;
                byte[] bytes = new byte[1024];                                    
 
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }      
                in.close();
                out.flush();
                out.close();
              
                System.out.println("New file created!");
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
    }
}