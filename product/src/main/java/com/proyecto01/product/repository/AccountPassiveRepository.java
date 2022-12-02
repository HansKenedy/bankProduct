package com.proyecto01.product.repository;

import com.proyecto01.product.domain.AccountPassive;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountPassiveRepository extends ReactiveMongoRepository<AccountPassive, String> {
    Mono<AccountPassive> findByAccountNumber(String accountNumber);
    Mono<AccountPassive> findByIdentityNumber(String identityNumber);
}
