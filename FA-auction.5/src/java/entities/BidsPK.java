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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author lumberjack
 */
@Embeddable
public class BidsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 34)
    @Column(name = "bidder_username")
    private String bidderUsername;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public BidsPK() {
    }

    public BidsPK(String bidderUsername, Date time) {
        this.bidderUsername = bidderUsername;
        this.time = time;
    }

    public String getBidderUsername() {
        return bidderUsername;
    }

    public void setBidderUsername(String bidderUsername) {
        this.bidderUsername = bidderUsername;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidderUsername != null ? bidderUsername.hashCode() : 0);
        hash += (time != null ? time.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BidsPK)) {
            return false;
        }
        BidsPK other = (BidsPK) object;
        if ((this.bidderUsername == null && other.bidderUsername != null) || (this.bidderUsername != null && !this.bidderUsername.equals(other.bidderUsername))) {
            return false;
        }
        if ((this.time == null && other.time != null) || (this.time != null && !this.time.equals(other.time))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BidsPK[ bidderUsername=" + bidderUsername + ", time=" + time + " ]";
    }
    
}
