package com.example.repository;

import com.example.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT * FROM clients OFFSET :offset LIMIT :size", nativeQuery = true)
    List<Client> getClientByPage(@Param("offset") int offset, @Param("size") int size);
}
