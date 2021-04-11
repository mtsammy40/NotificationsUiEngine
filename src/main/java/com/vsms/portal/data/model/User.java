package com.vsms.portal.data.model;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsms.portal.data.exceptions.ValidationException;
import com.vsms.portal.data.repositories.UserRepository;
import com.vsms.portal.utils.enums.Roles;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "users")
public class User {
    public enum Status {
        ACTIVE, DISABLED, CREATION_PENDING, CREATION_ONGOING, UPDATE_ONGOING;
    }

    public enum Action {
        CREATION, UPDATE, DELETE;
    }

    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String password;
    private String status;
    private Role role;
    private Client clientId;

    private String token;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic(optional = false)
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic(optional = false)
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore()
    @Basic(optional = false)
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne()
    @JoinColumn(referencedColumnName = "id", name = "role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne()
    @JoinColumn(referencedColumnName = "id", name = "client_id")
    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client client) {
        this.clientId = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Transient
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    

    public void validate(UserRepository repository, Action action) throws Exception {
        // Validate phone number format
        boolean phoneIsValid = Pattern.compile("^(254)([0-9]){9}$").matcher(phone).matches();
        if (StringUtils.isBlank(phone)) {
            throw new ValidationException("Phone is required!");
        }
        if (!phoneIsValid) {
            throw new ValidationException("Phone format is invalid!");
        }
        // Check for duplicate email and phone number
        List<User> userList = repository.findByEmailOrPhoneLike(email, phone);
        List<User> duplicateUserList = userList.stream().filter((user) -> !user.getId().equals(this.id))
                .collect(Collectors.toList());
        if (duplicateUserList.size() > 0) {
            throw new ValidationException("Email or Phone is already registered to another user!");
        }
        if (action.equals(Action.CREATION)) {
            this.setStatus(Status.CREATION_ONGOING.name());
        } else if (action.equals(Action.UPDATE)) {
            this.setStatus(Status.UPDATE_ONGOING.name());
        }
    }

    public boolean hasAdminRole() {
        return this.role != null && this.role.getTitle().equalsIgnoreCase(Roles.ADMIN.name());
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return super.toString();
        }
    }


}
