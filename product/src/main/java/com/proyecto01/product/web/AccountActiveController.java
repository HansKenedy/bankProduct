package com.proyecto01.product.web;

import com.proyecto01.product.domain.AccountActive;
import com.proyecto01.product.domain.AccountPassive;
import com.proyecto01.product.domain.Action;
import com.proyecto01.product.domain.TypeAccountActive;
import com.proyecto01.product.service.AccountActiveService;
import com.proyecto01.product.service.AccountPassiveService;
import com.proyecto01.product.web.mapper.AccountActiveMapper;

import com.proyecto01.product.web.model.AccountActiveModel;
import com.proyecto01.product.web.model.AccountPassiveModel;
import com.proyecto01.product.web.model.DepositModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/AccountActive")
public class AccountActiveController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private AccountActiveService accountActiveService;


    @Autowired
    private AccountActiveMapper accountActiveMapper;


    @GetMapping()
    public Mono<ResponseEntity<Flux<AccountActiveModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(accountActiveService.findAll()
                        .map(customer -> accountActiveMapper.entityToModel(customer))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountActiveModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<AccountActive> response = accountActiveService.findById(id);
        return response
                .map(accountActive -> accountActiveMapper.entityToModel(accountActive))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/documentCard/{accountNumber}")
    public Mono<ResponseEntity<AccountActiveModel>> getByIdAccountNumber(@PathVariable String accountNumber){
        log.info("getById executed {}", accountNumber);
        Mono<AccountActive> response = accountActiveService.findByAccountNumber(accountNumber);
        return response
                .map(customer -> accountActiveMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<AccountActiveModel>> create(@Valid @RequestBody AccountActiveModel request){
        log.info("create executed {}", request);
        return accountActiveService.create(accountActiveMapper.modelToEntity(request))
                .map(accountActive -> accountActiveMapper.entityToModel(accountActive))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "accountActive", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AccountActiveModel>> updateById(@PathVariable String id, @Valid @RequestBody AccountActiveModel request){
        log.info("updateById executed {}:{}", id, request);
        return accountActiveService.update(id, accountActiveMapper.modelToEntity(request))
                .map(accountActive -> accountActiveMapper.entityToModel(accountActive))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "accountActive", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return accountActiveService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/depositCredit")
    public Mono<ResponseEntity<AccountActiveModel>> depositCredit(@Valid @RequestBody DepositModel depositModel) {
        log.info("deposit executed {}", depositModel);
        return accountActiveService.updateAccountBalance(depositModel.getAccountNumber(), depositModel.getAmount())
                .map(accountActive -> accountActiveMapper.entityToModel(accountActive))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "accountActive", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
