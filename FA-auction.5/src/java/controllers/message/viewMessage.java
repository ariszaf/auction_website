/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.message;

import controllers.admin.SearchUsers;
import controllers.sesion.LoginLogout;

import dao.MessageDAO;
import dao.UserDAO;
import java.util.List;

import entities.Message;
import entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;


/**
 *
 * @author lumberjack
 */
@ManagedBean
@ViewScoped
public class viewMessage implements Serializable{
    
    
    private List<Message> incomingMail;
    private List<Message> filteredincomingMail;
    
    private List<Message> outgoingMail;
    private List<Message> filteredoutgoingMail;
    
    private Message currmess; 
    
    private List<SearchUsers.ColumnModel> columns;
    private String columnTemplate = "messages" ;
    
    private  int controlview = 1;
    
    private Message [] deleteOutGoingmessage;
    private Message [] deleteInComingmessage;
    
    private String recipient;
    private String subject;
    private String text;
    
    @ManagedProperty("#{user}")
    LoginLogout user;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    

    public Message[] getDeleteInComingmessage() {
        return deleteInComingmessage;
    }

    public void setDeleteInComingmessage(Message[] deleteInComingmessage) {
        this.deleteInComingmessage = deleteInComingmessage;
    }

    public Message[] getDeleteOutGoingmessage() {
        return deleteOutGoingmessage;
    }

    public void setDeleteOutGoingmessage(Message[] deleteOutGoingmessage) {
        this.deleteOutGoingmessage = deleteOutGoingmessage;
    }

    public Message getCurrmess() {
        return currmess;
    }

    public void setCurrmess(Message currmess) {
        this.currmess = currmess;    
    }
    
    public LoginLogout getUser() {
        return user;
    }
    
    public List<Message> getOutgoingMail() {
        return outgoingMail;
    }

    public void setOutgoingMail(List<Message> outgoingMail) {
        this.outgoingMail = outgoingMail;
    }

    public List<Message> getFilteredoutgoingMail() {
        return filteredoutgoingMail;
    }

    public void setFilteredoutgoingMail(List<Message> filteredoutgoingMail) {
        this.filteredoutgoingMail = filteredoutgoingMail;
    }
    
    
    public List<Message> getFilteredincomingMail() {
        return filteredincomingMail;
    }

    public void setFilteredincomingMail(List<Message> filteredincomingMail) {
        this.filteredincomingMail = filteredincomingMail;
    }
    
    public List<Message> getIncomingMail() {
        return incomingMail;
    }

    public void setIncomingMail(List<Message> incomingMail) {
        this.incomingMail = incomingMail;
    }

    public void setUser(LoginLogout user) {
        this.user = user;
    }
   
    public int getControlview() {
        return controlview;
    }

    public void setControlview(int controlview) {
        this.controlview = controlview;
    }

    public List<SearchUsers.ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<SearchUsers.ColumnModel> columns) {
        this.columns = columns;
    }

    public String getColumnTemplate() {
        return columnTemplate;
    }

    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }

    
   
   
    @PostConstruct
    public void init() {
        
        //HttpSession session = SessionUtils.getSession();
        //LoginLogout user = (LoginLogout) session.getAttribute("user");
        
        MessageDAO messagedao = new MessageDAO();
        String use = user.getUsername();
        incomingMail = messagedao.incomingMail(use);
        this.user.setUnreadMessages(messagedao.newMail(use));
        outgoingMail = messagedao.outgoingMail(use);
        createDynamicColumns();
        
    }
    
    public void deleteInComingMessages(){
        
        MessageDAO messagedao = new MessageDAO();
        
        for(Message mess :this.deleteInComingmessage){
            this.incomingMail.remove(mess);
            
            if(!mess.getOpened()){
                this.user.setUnreadMessages(this.user.getUnreadMessages()-1);
            }
            
            mess.setDeletedRecipient(true);
            if(messagedao.update(mess).compareTo("OK")!=0 ){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail to delete Message",  null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Messages Deleted",  null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            
        }
        this.controlview=1;
    }
    
    public void deleteOutGoingMessages(){
        
        MessageDAO messagedao = new MessageDAO();
       
        for(Message mess :this.deleteOutGoingmessage){
            this.outgoingMail.remove(mess);
            mess.setDeletedSender(true);
            if(messagedao.update(mess).compareTo("OK")!=0 ){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail to delete Message",  null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Messages Deleted",  null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            
        }
        this.controlview=2;
        
    }
    
    public void CountViewMess(){
        MessageDAO messdao =new MessageDAO();
        Long count = messdao.newMail(this.user.getUsername());
        this.user.setUnreadMessages(count);
    }
    
    public void setRead(Message mess){
        incomingMail.get(incomingMail.indexOf(mess)).setOpened(true);
        MessageDAO messDao = new MessageDAO();
        messDao.update(mess);
        this.currmess=mess;
        Long un =this.user.getUnreadMessages();
        this.user.setUnreadMessages(un-1);
    }
    
    
    public void sendMessage(){
        Message mess = new Message();
        MessageDAO messdao = new MessageDAO(); 
        UserDAO userdao = new UserDAO();
        User user = userdao.select(recipient);
        
        if(user==null){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Username not found",  null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        mess.setRecipientUsername(user);
        mess.setSenderUsername(this.user.getUser());
        mess.setDateCreated( new Date());
        mess.setDateSent(new Date());
        mess.setOpened(false);
        mess.setDeletedRecipient(false);
        mess.setDeletedSender(false);
        if(subject==null){
            subject=new String("No Subject");
        }
        mess.setSubject(subject);
        mess.setText(text);
        
        this.outgoingMail.add(mess);
        
        if(messdao.insert(mess)!=null){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Message Sent",  null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Message failed",  null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        this.resetCompose();
    }
    
    public void resetCompose(){
        this.subject=null;
        this.recipient=null;
        this.text=null;
    }
    
    
    private void createDynamicColumns() {
        
        String[] columnKeys = columnTemplate.split(" ");
        columns = new ArrayList<SearchUsers.ColumnModel>();   
           
         
        for(String columnKey : columnKeys) {
            String key = columnKey.trim();
             
            
            SearchUsers.ColumnModel cm = new SearchUsers.ColumnModel(columnKey.toUpperCase(), "subject");
           
            columns.add(cm);
           
            
            
        }
    } 
   
   
   
    
    
}
