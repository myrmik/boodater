package boodater.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="CURRENT_EPISODE")
public class CurrentEpisode {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @Column
    private int episode;

    @Column
    private Date date;

    @Override
    public String toString() {
        return getName() + " - " + getEpisodeString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEpisode() {
        return episode;
    }

    public String getEpisodeString() {
        if (episode < 10)
            return "0" + Integer.toString(episode);
        return Integer.toString(episode);
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
