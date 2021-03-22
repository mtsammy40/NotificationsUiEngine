package com.vsms.portal.data.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "message_trailer")
public class MessageTrailer {
    private String inTrash;
    private int id;
    private Timestamp dateCreated;
    private String trailer;

    @Basic
    @Column(name = "in_trash", nullable = true, length = 255)
    public String getInTrash() {
        return inTrash;
    }

    public void setInTrash(String inTrash) {
        this.inTrash = inTrash;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_created", nullable = false)
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "trailer", nullable = true, length = -1)
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageTrailer that = (MessageTrailer) o;
        return id == that.id &&
                Objects.equals(inTrash, that.inTrash) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(trailer, that.trailer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inTrash, id, dateCreated, trailer);
    }
}
