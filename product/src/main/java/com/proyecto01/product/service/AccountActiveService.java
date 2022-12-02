package com.proyecto01.product.service;


import com.proyecto01.product.domain.AccountActive;
import com.proyecto01.product.domain.AccountPassive;
import com.proyecto01.product.domain.Action;
import com.proyecto01.product.repository.AccountActiveRepository;
import com.proyecto01.product.web.mapper.AccountActiveMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountActiveService {
    @Autowired
    private AccountActiveRepository accountActiveRepository;

    @Autowired
    private AccountActiveMapper accountActiveMapper;


    public Flux<AccountActive> findAll(){
        log.debug("findAll executed");
        return accountActiveRepository.findAll();
    }


    public Mono<AccountActive> findById(String accountActiveId){
        log.debug("findById executed {}", accountActiveId);
        return accountActiveRepository.findById(accountActiveId);
    }
    public Mono<AccountActive> findByAccountNumber(String accountNumber){
        log.debug("findByAccountNumber executed {}", accountNumber);
        return accountActiveRepository.findByAccountNumber(accountNumber);
    }
    public Mono<AccountActive> create(AccountActive accountActive){
        log.debug("create executed {}", accountActive);
        return accountActiveRepository.save(accountActive);
    }


    public Mono<AccountActive> update(String accountActiveId, AccountActive accountActive){
        log.debug("update executed {}:{}", accountActiveId, accountActive);
        return accountActiveRepository.findById(accountActiveId)
                .flatMap(dbAccountActive -> {
                    accountActiveMapper.update(dbAccountActive, accountActive);
                    return accountActiveRepository.save(dbAccountActive);
                });
    }


    public Mono<AccountActive> delete(String accountActiveId){
        log.debug("delete executed {}", accountActiveId);
        return accountActiveRepository.findById(accountActiveId)
                .flatMap(existingAccountPassiveId -> accountActiveRepository.delete(existingAccountPassiveId)
                        .then(Mono.just(existingAccountPassiveId)));
    }

    public Mono<AccountActive>  updateAccountBalance(String accountNumber, double amount) {
        log.info("updateAccountBalance executed {}", amount);
        return accountActiveRepository.findByAccountNumber(accountNumber)
                .flatMap(dbAccountActive -> {
                    if(isAmountAvailable(amount, dbAccountActive.getAmount())){

                        dbAccountActive.setAmountPaid(dbAccountActive.getAmountPaid() + amount);
                        dbAccountActive.setAmount(dbAccountActive.getAmount() - amount);
                        return accountActiveRepository.save(dbAccountActive);

                    }else {
                        return Mono.error(new Exception("No tiene Saldo" + dbAccountActive.getAccountNumber()));
                    }


                });
    }


    public boolean isAmountAvailable(double amount, double accountBalance) {

        return (accountBalance - amount) > 0;
    }

}
