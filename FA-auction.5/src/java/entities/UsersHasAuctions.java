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
@Table(name = "users_has_auctions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersHasAuctions.findAll", query = "SELECT u FROM UsersHasAuctions u"),
    @NamedQuery(name = "UsersHasAuctions.findBySellerUsername", query = "SELECT u FROM UsersHasAuctions u WHERE u.usersHasAuctionsPK.sellerUsername = :sellerUsername"),
    @NamedQuery(name = "UsersHasAuctions.findByAuctionsItemId", query = "SELECT u FROM UsersHasAuctions u WHERE u.usersHasAuctionsPK.auctionsItemId = :auctionsItemId"),
    @NamedQuery(name = "UsersHasAuctions.findByRating", query = "SELECT u FROM UsersHasAuctions u WHERE u.rating = :rating")})
public class UsersHasAuctions implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsersHasAuctionsPK usersHasAuctionsPK;
    @Column(name = "rating")
    private Integer rating;
    @JoinColumn(name = "auctions_item_id", referencedColumnName = "item_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Auction auction;
    @JoinColumn(name = "seller_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public UsersHasAuctions() {
    }

    public UsersHasAuctions(UsersHasAuctionsPK usersHasAuctionsPK) {
        this.usersHasAuctionsPK = usersHasAuctionsPK;
    }

    public UsersHasAuctions(String sellerUsername, int auctionsItemId) {
        this.usersHasAuctionsPK = new UsersHasAuctionsPK(sellerUsername, auctionsItemId);
    }

    public UsersHasAuctionsPK getUsersHasAuctionsPK() {
        return usersHasAuctionsPK;
    }

    public void setUsersHasAuctionsPK(UsersHasAuctionsPK usersHasAuctionsPK) {
        this.usersHasAuctionsPK = usersHasAuctionsPK;
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
        hash += (usersHasAuctionsPK != null ? usersHasAuctionsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersHasAuctions)) {
            return false;
        }
        UsersHasAuctions other = (UsersHasAuctions) object;
        if ((this.usersHasAuctionsPK == null && other.usersHasAuctionsPK != null) || (this.usersHasAuctionsPK != null && !this.usersHasAuctionsPK.equals(other.usersHasAuctionsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UsersHasAuctions[ usersHasAuctionsPK=" + usersHasAuctionsPK + " ]";
    }
    
}
