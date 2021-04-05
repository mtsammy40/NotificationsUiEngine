package com.vsms.portal.data.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ch_clients")
public class ChClients {
    private Long id;
    private String churchName;
    private String county;
    private String address;
    private String msisdn;
    private String email;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "church_name", nullable = true, length = -1)
    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    @Basic
    @Column(name = "county", nullable = true, length = -1)
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Basic
    @Column(name = "address", nullable = true, length = -1)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "msisdn", nullable = true, length = -1)
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Basic
    @Column(name = "email", nullable = true, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChClients chClients = (ChClients) o;
        return id == chClients.id &&
                Objects.equals(churchName, chClients.churchName) &&
                Objects.equals(county, chClients.county) &&
                Objects.equals(address, chClients.address) &&
                Objects.equals(msisdn, chClients.msisdn) &&
                Objects.equals(email, chClients.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, churchName, county, address, msisdn, email);
    }
}
