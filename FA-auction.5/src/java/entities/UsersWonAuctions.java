/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lumberjack
 */
@Entity
@Table(name = "users_won_auctions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersWonAuctions.findAll", query = "SELECT u FROM UsersWonAuctions u"),
    @NamedQuery(name = "UsersWonAuctions.findByBuyerUsername", query = "SELECT u FROM UsersWonAuctions u WHERE u.usersWonAuctionsPK.buyerUsername = :buyerUsername"),
    @NamedQuery(name = "UsersWonAuctions.findByAuctionsItemId", query = "SELECT u FROM UsersWonAuctions u WHERE u.usersWonAuctionsPK.auctionsItemId = :auctionsItemId"),
    @NamedQuery(name = "UsersWonAuctions.findByRating", query = "SELECT u FROM UsersWonAuctions u WHERE u.rating = :rating")})
public class UsersWonAuctions implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsersWonAuctionsPK usersWonAuctionsPK;
    @Column(name = "rating")
    private Integer rating;
    @JoinColumn(name = "auctions_item_id", referencedColumnName = "item_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Auction auction;
    @JoinColumn(name = "buyer_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public UsersWonAuctions() {
    }

    public UsersWonAuctions(UsersWonAuctionsPK usersWonAuctionsPK) {
        this.usersWonAuctionsPK = usersWonAuctionsPK;
    }

    public UsersWonAuctions(String buyerUsername, int auctionsItemId) {
        this.usersWonAuctionsPK = new UsersWonAuctionsPK(buyerUsername, auctionsItemId);
    }

    public UsersWonAuctionsPK getUsersWonAuctionsPK() {
        return usersWonAuctionsPK;
    }

    public void setUsersWonAuctionsPK(UsersWonAuctionsPK usersWonAuctionsPK) {
        this.usersWonAuctionsPK = usersWonAuctionsPK;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
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
        hash += (usersWonAuctionsPK != null ? usersWonAuctionsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersWonAuctions)) {
            return false;
        }
        UsersWonAuctions other = (UsersWonAuctions) object;
        if ((this.usersWonAuctionsPK == null && other.usersWonAuctionsPK != null) || (this.usersWonAuctionsPK != null && !this.usersWonAuctionsPK.equals(other.usersWonAuctionsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UsersWonAuctions[ usersWonAuctionsPK=" + usersWonAuctionsPK + " ]";
    }
    
}
