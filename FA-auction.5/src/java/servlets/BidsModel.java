/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author lumberjack
 */
public class BidsModel implements Serializable{
    
    private BitterModel bitter;
    private Date time;
    private Double bit_amount;

    @XmlElement(name="Amount")
    public Double getBit_amount() {
        return bit_amount;
    }

    public void setBit_amount(Double bit_amount) {
        this.bit_amount = bit_amount;
    }

    @XmlElement(name="Time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    @XmlElement(name="Bitter")
    public BitterModel getBitter() {
        return bitter;
    }

    public void setBitter(BitterModel bitter) {
        this.bitter = bitter;
    }
    
}
