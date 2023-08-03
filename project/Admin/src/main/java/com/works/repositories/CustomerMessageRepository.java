package com.works.repositories;

import com.works.entities.CustomerMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerMessageRepository extends JpaRepository<CustomerMessage, Long> {
}