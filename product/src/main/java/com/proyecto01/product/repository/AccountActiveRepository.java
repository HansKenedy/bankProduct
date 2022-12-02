package com.proyecto01.product.repository;

import com.proyecto01.product.domain.AccountActive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountActiveRepository extends ReactiveMongoRepository<AccountActive, String> {
    Mono<AccountActive> findByAccountNumber(String accountNumber);
}
