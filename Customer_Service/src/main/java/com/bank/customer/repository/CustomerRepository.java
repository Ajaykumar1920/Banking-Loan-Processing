package com.bank.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.customer.entity.CustomerModel;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
}
