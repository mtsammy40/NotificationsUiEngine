package com.vsms.portal.data.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "church_members")
public class ChurchMembers {
    private int id;
    private String balances;
    private String churchId;
    private Timestamp dateCreated;
    private String memberName;
    private String pledge;
    private String memberPhoneNumber;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "balances", nullable = true, length = 255)
    public String getBalances() {
        return balances;
    }

    public void setBalances(String balances) {
        this.balances = balances;
    }

    @Basic
    @Column(name = "church_id", nullable = true, length = 255)
    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    @Basic
    @Column(name = "date_created", nullable = true)
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "member_name", nullable = true, length = 255)
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Basic
    @Column(name = "pledge", nullable = true, length = 255)
    public String getPledge() {
        return pledge;
    }

    public void setPledge(String pledge) {
        this.pledge = pledge;
    }

    @Basic
    @Column(name = "member_phone_number", nullable = true, length = 255)
    public String getMemberPhoneNumber() {
        return memberPhoneNumber;
    }

    public void setMemberPhoneNumber(String memberPhoneNumber) {
        this.memberPhoneNumber = memberPhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChurchMembers that = (ChurchMembers) o;
        return id == that.id &&
                Objects.equals(balances, that.balances) &&
                Objects.equals(churchId, that.churchId) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(memberName, that.memberName) &&
                Objects.equals(pledge, that.pledge) &&
                Objects.equals(memberPhoneNumber, that.memberPhoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balances, churchId, dateCreated, memberName, pledge, memberPhoneNumber);
    }
}
