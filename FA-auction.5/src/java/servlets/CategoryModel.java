/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author lumberjack
 */
public class CategoryModel implements Serializable {
    private String cat;

    @XmlElement(name="Category")
    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
    
    
}
