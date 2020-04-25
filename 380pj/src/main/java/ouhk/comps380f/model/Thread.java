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
@Table(name = "threads")
public class Thread implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String content;
    private String category;

    @ManyToOne
    @JoinColumn(name = "username")
    private TicketUser user;

    @OneToMany(mappedBy = "thread", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<ThreadAttachment> attachments = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "thread", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Reply> reply = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "timestamptz")
    @DateTimeFormat(pattern = "YYYY-mm-dd HH:mm")
    private Date created_at;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TicketUser getUser() {
        return user;
    }

    public void setUser(TicketUser user) {
        this.user = user;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ThreadAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ThreadAttachment> attachments) {
        this.attachments = attachments;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
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

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }

    public void deleteAttachment(ThreadAttachment attachment) {
        attachment.setThread(null);
        this.attachments.remove(attachment);
    }
}
