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
public class UsersHasAuctionsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 34)
    @Column(name = "seller_username")
    private String sellerUsername;
    @Basic(optional = false)
    @NotNull
    @Column(name = "auctions_item_id")
    private int auctionsItemId;

    public UsersHasAuctionsPK() {
    }

    public UsersHasAuctionsPK(String sellerUsername, int auctionsItemId) {
        this.sellerUsername = sellerUsername;
        this.auctionsItemId = auctionsItemId;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
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
        hash += (sellerUsername != null ? sellerUsername.hashCode() : 0);
        hash += (int) auctionsItemId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersHasAuctionsPK)) {
            return false;
        }
        UsersHasAuctionsPK other = (UsersHasAuctionsPK) object;
        if ((this.sellerUsername == null && other.sellerUsername != null) || (this.sellerUsername != null && !this.sellerUsername.equals(other.sellerUsername))) {
            return false;
        }
        if (this.auctionsItemId != other.auctionsItemId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UsersHasAuctionsPK[ sellerUsername=" + sellerUsername + ", auctionsItemId=" + auctionsItemId + " ]";
    }
    
}
