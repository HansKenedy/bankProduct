package com.proyecto01.product.service;

import com.proyecto01.product.domain.AccountPassive;
import com.proyecto01.product.domain.Action;
import com.proyecto01.product.domain.Client;
import com.proyecto01.product.domain.TypeAccountPassive;
import com.proyecto01.product.repository.AccountPassiveRepository;
import com.proyecto01.product.web.mapper.AccountPassiveMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountPassiveService {
    @Autowired
    private AccountPassiveRepository accountPassiveRepository;

    @Autowired
    private AccountPassiveMapper accountPassiveMapper;

    @Autowired
    private ClientService clientService;


    public Flux<AccountPassive> findAll(){
        log.debug("findAll executed");
        return accountPassiveRepository.findAll();
    }


    public Mono<AccountPassive> findById(String accountActiveId){
        log.debug("findById executed {}", accountActiveId);
        return accountPassiveRepository.findById(accountActiveId);
    }

    public Mono<AccountPassive> findByAccountNumber(String accountNumber){
        log.debug("findByAccountNumber executed {}", accountNumber);
        return accountPassiveRepository.findByAccountNumber(accountNumber);
    }
    public Flux<AccountPassive> findByIdentityNumber(String accountNumber){
        log.debug("findByAccountNumber executed {}", accountNumber);
        return accountPassiveRepository.findByIdentityNumber(accountNumber);
    }
    public Mono<AccountPassive> create(AccountPassive accountPassive) {
        log.info("create executed {}", accountPassive);
        Mono<Client> clientMono = clientService.getClient(accountPassive.getIdentityNumber());
        return accountPassiveRepository.save(accountPassive);
    }
    public Mono<AccountPassive> update(String accountActiveId, AccountPassive accountPassive){
        log.debug("update executed {}:{}", accountActiveId, accountPassive);
        return accountPassiveRepository.findById(accountActiveId)
                .flatMap(dbAccountPassive -> {
                    accountPassiveMapper.update(dbAccountPassive, accountPassive);
                    return accountPassiveRepository.save(dbAccountPassive);
                });
    }


    public Mono<AccountPassive> delete(String accountActiveId){
        log.debug("delete executed {}", accountActiveId);
        return accountPassiveRepository.findById(accountActiveId)
                .flatMap(existingAccountPassiveId -> accountPassiveRepository.delete(existingAccountPassiveId)
                        .then(Mono.just(existingAccountPassiveId)));
    }

    public Mono<AccountPassive>  updateAccountBalance(String accountNumber, double amount, Action action) {
        log.info("updateAccountBalance executed {}", amount);
        return accountPassiveRepository.findByAccountNumber(accountNumber)
                .flatMap(dbAccountPassive -> {
                    if(isAmountAvailable(amount, dbAccountPassive.getBalance())){

                        if (action == Action.WITHDRAW) {
                            dbAccountPassive.setBalance((dbAccountPassive.getBalance() - amount));
                        } else if (action == Action.DEPOSIT) {
                            dbAccountPassive.setBalance((dbAccountPassive.getBalance() + amount));
                        }
                    }
                    return accountPassiveRepository.save(dbAccountPassive);

                });
    }


    public boolean isAmountAvailable(double amount, double accountBalance) {

        return (accountBalance - amount) > 0;
    }
}
