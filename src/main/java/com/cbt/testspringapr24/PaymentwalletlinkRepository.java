package com.cbt.testspringapr24;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentwalletlinkRepository extends JpaRepository<Paymentwalletlink, String> {
    Paymentwalletlink findByPaymentrefid(String paymentrefid);
}