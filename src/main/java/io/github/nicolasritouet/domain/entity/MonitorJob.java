package io.github.nicolasritouet.domain.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MonitorJob implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String url;

    @Lob
    private String body;

    private String element;

    private Date lastCheck;

    private Date lastChange;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public Date getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(Date lastCheck) {
        this.lastCheck = lastCheck;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    @Override
    public String toString() {
        return "MonitorJob{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", body='" + body + '\'' +
                ", element='" + element + '\'' +
                ", lastCheck=" + lastCheck +
                ", lastChange=" + lastChange +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MonitorJob that = (MonitorJob) o;

        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (element != null ? !element.equals(that.element) : that.element != null) return false;
        if (!id.equals(that.id)) return false;
        if (lastChange != null ? !lastChange.equals(that.lastChange) : that.lastChange != null) return false;
        if (lastCheck != null ? !lastCheck.equals(that.lastCheck) : that.lastCheck != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (element != null ? element.hashCode() : 0);
        result = 31 * result + (lastCheck != null ? lastCheck.hashCode() : 0);
        result = 31 * result + (lastChange != null ? lastChange.hashCode() : 0);
        return result;
    }
}
