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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lumberjack
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findByMessageId", query = "SELECT m FROM Message m WHERE m.messageId = :messageId"),
    @NamedQuery(name = "Message.findByText", query = "SELECT m FROM Message m WHERE m.text = :text"),
    @NamedQuery(name = "Message.findBySubject", query = "SELECT m FROM Message m WHERE m.subject = :subject"),
    @NamedQuery(name = "Message.findByDateSent", query = "SELECT m FROM Message m WHERE m.dateSent = :dateSent"),
    @NamedQuery(name = "Message.findByDateCreated", query = "SELECT m FROM Message m WHERE m.dateCreated = :dateCreated"),
    @NamedQuery(name = "Message.findByOpened", query = "SELECT m FROM Message m WHERE m.opened = :opened"),
    @NamedQuery(name = "Message.findByDeletedSender", query = "SELECT m FROM Message m WHERE m.deletedSender = :deletedSender"),
    @NamedQuery(name = "Message.findByDeletedRecipient", query = "SELECT m FROM Message m WHERE m.deletedRecipient = :deletedRecipient")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "message_id")
    private Integer messageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "text")
    private String text;
    @Size(max = 45)
    @Column(name = "subject")
    private String subject;
    @Column(name = "date_sent")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "opened")
    private boolean opened;
    @Basic(optional = false)
    @NotNull
    @Column(name = "deleted_sender")
    private boolean deletedSender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "deleted_recipient")
    private boolean deletedRecipient;
    @JoinColumn(name = "sender_username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User senderUsername;
    @JoinColumn(name = "recipient_username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User recipientUsername;

    public Message() {
    }

    public Message(Integer messageId) {
        this.messageId = messageId;
    }

    public Message(Integer messageId, String text, Date dateCreated, boolean opened, boolean deletedSender, boolean deletedRecipient) {
        this.messageId = messageId;
        this.text = text;
        this.dateCreated = dateCreated;
        this.opened = opened;
        this.deletedSender = deletedSender;
        this.deletedRecipient = deletedRecipient;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean getOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean getDeletedSender() {
        return deletedSender;
    }

    public void setDeletedSender(boolean deletedSender) {
        this.deletedSender = deletedSender;
    }

    public boolean getDeletedRecipient() {
        return deletedRecipient;
    }

    public void setDeletedRecipient(boolean deletedRecipient) {
        this.deletedRecipient = deletedRecipient;
    }

    public User getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(User senderUsername) {
        this.senderUsername = senderUsername;
    }

    public User getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(User recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Message[ messageId=" + messageId + " ]";
    }
    
}
