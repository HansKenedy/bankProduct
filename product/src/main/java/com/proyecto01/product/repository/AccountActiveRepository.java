package com.proyecto01.product.repository;

import com.proyecto01.product.domain.AccountActive;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountActiveRepository extends ReactiveMongoRepository<AccountActive, String> {
}
