package com.vsms.portal.data.model;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
    private Long id;
    private String businessNumber;
    private String location;
    private String name;
    private String phoneNumber;
    private String status;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "business_number", nullable = false)
    public String getBusinessNumber() {
        return businessNumber;
    }
    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    @Column(name = "location", nullable = false)
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "phone_number", nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
