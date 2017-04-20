package ir.samatco.iepg.entity;

import javax.persistence.*;

/**
 * @author Saeed
 *         Date: 4/17/2017
 */
@Entity
@Table(name = "userVote")
public class UserVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JoinColumn(name = "voterId")
    Voter voter;
    @JoinColumn(name = "nomineeId")
    Nominee nominee;
    Integer number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    public Nominee getNominee() {
        return nominee;
    }

    public void setNominee(Nominee nominee) {
        this.nominee = nominee;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
