package com.vsms.portal.data.repositories;

import com.vsms.portal.data.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
