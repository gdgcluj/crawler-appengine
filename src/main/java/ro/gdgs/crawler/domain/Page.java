package ro.gdgs.crawler.domain;

import javax.persistence.*;

/**
 * @author hasna
 * @since 1.1
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "pages")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(unique = true, nullable = false)
    private Long id;
    @Column
    private String url;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private boolean crawled;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCrawled() {
        return crawled;
    }

    public void setCrawled(boolean crawled) {
        this.crawled = crawled;
    }
}
