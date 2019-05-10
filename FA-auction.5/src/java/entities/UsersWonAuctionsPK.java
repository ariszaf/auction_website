/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author lumberjack
 */
@Embeddable
public class UsersWonAuctionsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 34)
    @Column(name = "buyer_username")
    private String buyerUsername;
    @Basic(optional = false)
    @NotNull
    @Column(name = "auctions_item_id")
    private int auctionsItemId;

    public UsersWonAuctionsPK() {
    }

    public UsersWonAuctionsPK(String buyerUsername, int auctionsItemId) {
        this.buyerUsername = buyerUsername;
        this.auctionsItemId = auctionsItemId;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public int getAuctionsItemId() {
        return auctionsItemId;
    }

    public void setAuctionsItemId(int auctionsItemId) {
        this.auctionsItemId = auctionsItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buyerUsername != null ? buyerUsername.hashCode() : 0);
        hash += (int) auctionsItemId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersWonAuctionsPK)) {
            return false;
        }
        UsersWonAuctionsPK other = (UsersWonAuctionsPK) object;
        if ((this.buyerUsername == null && other.buyerUsername != null) || (this.buyerUsername != null && !this.buyerUsername.equals(other.buyerUsername))) {
            return false;
        }
        if (this.auctionsItemId != other.auctionsItemId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UsersWonAuctionsPK[ buyerUsername=" + buyerUsername + ", auctionsItemId=" + auctionsItemId + " ]";
    }
    
}
