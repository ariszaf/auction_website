/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author lumberjack
 */
public class BidsWrapper implements Serializable {
    
    private Collection<BidsModel> bids = new ArrayList();
    
    @XmlElement(name="Bid")
    public Collection<BidsModel> getItem() {
        return bids;
    }

    public void setItem(Collection<BidsModel> bids) {
        this.bids = bids;
    }
    
}
