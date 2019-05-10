/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
    
/**
 *
 * @author lumberjack
 */
@XmlRootElement(name = "Items")
public class AuctionWrapper  implements Serializable {
    private Collection<AuctionModel> item = new ArrayList<>();

    @XmlElement(name="Item")
    public Collection<AuctionModel> getItem() {
        return item;
    }

    public void setItem(Collection<AuctionModel> items) {
        this.item = items;
    }
}
