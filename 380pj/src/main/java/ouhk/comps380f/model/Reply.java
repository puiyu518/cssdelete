package ouhk.comps380f.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "replies")
public class Reply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "rid")
    private Thread thread;

    @ManyToOne
    @JoinColumn(name = "username")
    private TicketUser user;

    @OneToMany(mappedBy = "reply", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<ReplyAttachment> attachments = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "timestamptz")
    @DateTimeFormat(pattern = "YYYY-mm-dd HH:mm")
    private Date created_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public TicketUser getUser() {
        return user;
    }

    public void setUser(TicketUser user) {
        this.user = user;
    }

    public List<ReplyAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ReplyAttachment> attachments) {
        this.attachments = attachments;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    
    
     public void deleteAttachment(ReplyAttachment attachment) {
        attachment.setReply(null);
        this.attachments.remove(attachment);
    }
      public String getDateBetween() {
        Date now = new Date();
        long difference = now.getTime() - this.created_at.getTime();
        if (TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS) < 60) {
            return Long.toString(TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS))  + " minutes ago";
        } else if (TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS) < 24) {
            return Long.toString(TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS)) + " hours ago";
        } else {
            return Long.toString(TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS))  + " days ago";

        }

    }
}
