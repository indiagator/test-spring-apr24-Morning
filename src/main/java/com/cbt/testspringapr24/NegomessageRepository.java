package com.cbt.testspringapr24;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NegomessageRepository extends JpaRepository<Negomessage, String> {
}