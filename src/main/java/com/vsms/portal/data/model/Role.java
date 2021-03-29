package com.vsms.portal.data.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Role {
    private int id;
    private String title;
    private String status;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 20)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id &&
                Objects.equals(title, role.title) &&
                Objects.equals(status, role.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status);
    }
}
