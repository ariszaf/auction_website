/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lumberjack
 */
@Entity
@Table(name = "bids")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bids.findAll", query = "SELECT b FROM Bids b"),
    @NamedQuery(name = "Bids.findByBidderUsername", query = "SELECT b FROM Bids b WHERE b.bidsPK.bidderUsername = :bidderUsername"),
    @NamedQuery(name = "Bids.findByBidAmount", query = "SELECT b FROM Bids b WHERE b.bidAmount = :bidAmount"),
    @NamedQuery(name = "Bids.findByTime", query = "SELECT b FROM Bids b WHERE b.bidsPK.time = :time")})
public class Bids implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BidsPK bidsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bid_amount")
    private double bidAmount;
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne(optional = false)
    private Auction itemId;
    @JoinColumn(name = "bidder_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Bids() {
    }

    public Bids(BidsPK bidsPK) {
        this.bidsPK = bidsPK;
    }

    public Bids(BidsPK bidsPK, double bidAmount) {
        this.bidsPK = bidsPK;
        this.bidAmount = bidAmount;
    }

    public Bids(String bidderUsername, Date time) {
        this.bidsPK = new BidsPK(bidderUsername, time);
    }

    public BidsPK getBidsPK() {
        return bidsPK;
    }

    public void setBidsPK(BidsPK bidsPK) {
        this.bidsPK = bidsPK;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Auction getItemId() {
        return itemId;
    }

    public void setItemId(Auction itemId) {
        this.itemId = itemId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidsPK != null ? bidsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bids)) {
            return false;
        }
        Bids other = (Bids) object;
        if ((this.bidsPK == null && other.bidsPK != null) || (this.bidsPK != null && !this.bidsPK.equals(other.bidsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bids[ bidsPK=" + bidsPK + " ]";
    }
    
}
