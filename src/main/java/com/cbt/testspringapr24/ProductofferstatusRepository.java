package com.cbt.testspringapr24;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductofferstatusRepository extends JpaRepository<Productofferstatus, String> {
  List<Productofferstatus> findByStatus(String status);
}