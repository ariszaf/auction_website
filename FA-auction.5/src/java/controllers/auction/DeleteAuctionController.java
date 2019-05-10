package controllers.auction;

import dao.AuctionDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class DeleteAuctionController implements Serializable {
 
    private String itemid_string;
    private int item_id;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
    
    public String delete() {        

        Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        itemid_string = requestMap.get("itemId");
        item_id = Integer.parseInt(itemid_string);
        AuctionDAO dao = new AuctionDAO();
        String message = dao.remove(item_id);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
        return null;
    }
}