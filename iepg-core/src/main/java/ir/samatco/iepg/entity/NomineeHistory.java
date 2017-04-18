package ir.samatco.iepg.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Saeed
 *         Date: 4/17/2017
 */
@Entity
@Table(name="nomineeHistory")
public class NomineeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JoinColumn(name = "nomineeId")
    Nominee nominee;
    Integer price;
    @Temporal(TemporalType.DATE)
    Date reportTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nominee getNominee() {
        return nominee;
    }

    public void setNominee(Nominee nominee) {
        this.nominee = nominee;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}
