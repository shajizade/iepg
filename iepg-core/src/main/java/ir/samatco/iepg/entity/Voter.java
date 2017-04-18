package ir.samatco.iepg.entity;

import javax.persistence.*;

/**
 * @author Saeed
 *         Date: 4/17/2017
 */

@Entity
@Table(name = "voter")
public class Voter {
    @Id
    @GeneratedValue
    Long id;
    String username;
    Integer points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
