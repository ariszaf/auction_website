/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import controllers.sesion.LoginLogout;
import dao.UserDAO;
import entities.User;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.Dependent; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

 
@ManagedBean
@ViewScoped
public class SearchUsers implements Serializable {
     
    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("username", "name", "surname", "email",
            "phone","address","city","country","zip_code","vat","sellRating","bidRating","validation","date_registration");
     
    private String columnTemplate = "username name surname email validation" ;

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
     
    private List<ColumnModel> columns;
     
    private List<User> users;
     
    private List<User> filteredUsers;
     
    @ManagedProperty("#{loginLogout}")
    private LoginLogout loginbean;

    public LoginLogout getLoginbean() {
        return loginbean;
    }

    public void setLoginbean(LoginLogout loginbean) {
        this.loginbean = loginbean;
    }
    
    
 
    @PostConstruct
    public void init() {
        UserDAO userdao = new UserDAO();
        users = userdao.getUsers();
        createDynamicColumns();
    }
     
    public List<User> getUsers() {
        return users;
    }
 
    public List<User> getFilteredUsers() {
        return filteredUsers;
    }
 
    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }
 
    
 
    public String getColumnTemplate() {
        return columnTemplate;
    }
 
    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }
 
    public List<ColumnModel> getColumns() {
        return columns;
    }
 
    private void createDynamicColumns() {
        
        String[] columnKeys = columnTemplate.split(" ");
        columns = new ArrayList<ColumnModel>();   
         
        for(String columnKey : columnKeys) {
            String key = columnKey.trim();
             
            if(VALID_COLUMN_KEYS.contains(key)) {
                ColumnModel cm = new ColumnModel(columnKey.toUpperCase(), columnKey);
                columns.add(cm);
            }
        }
    }
    
  
     
    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:Users");
        table.setValueExpression("sortBy", null);
         
        //update columns
        createDynamicColumns();
    }
     
   
    public String exportXML(){
        try { 
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FA-auction/ExportXML");
        } catch (IOException ex) {
            Logger.getLogger(SearchUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    static public class ColumnModel implements Serializable {
 
        private String header;
        private String property;
 
        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }
 
        public String getHeader() {
            return header;
        }
 
        public String getProperty() {
            return property;
        }
    }
    
    
   
}