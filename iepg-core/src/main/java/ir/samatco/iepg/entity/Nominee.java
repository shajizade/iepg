package ir.samatco.iepg.entity;

import javax.persistence.*;

/**
 * @author Saeed
 *         Date: 4/17/2017
 */
@Entity
@Table(name = "nominee")
public class Nominee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    @Transient
    Integer buyPrice;
    @Transient
    Integer sellPrice;
    Long number;
    Boolean valid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nominee)) return false;

        Nominee nominee = (Nominee) o;

        return id.equals(nominee.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
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

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Long getEffectiveNumber() {
        return number + 100;
    }
}
