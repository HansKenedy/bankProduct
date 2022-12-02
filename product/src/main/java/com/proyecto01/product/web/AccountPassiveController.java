package com.proyecto01.product.web;

import com.proyecto01.product.domain.AccountPassive;
import com.proyecto01.product.domain.Action;
import com.proyecto01.product.domain.Client;
import com.proyecto01.product.service.AccountPassiveService;
import com.proyecto01.product.service.ClientService;
import com.proyecto01.product.web.mapper.AccountPassiveMapper;
import com.proyecto01.product.web.model.AccountPassiveModel;
import com.proyecto01.product.web.model.DepositModel;
import com.proyecto01.product.web.model.WithdrawModel;
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
@RequestMapping("/v1/AccountPassive")
public class AccountPassiveController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private AccountPassiveService accountPassiveService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountPassiveMapper accountPassiveMapper;


    @GetMapping()
    public Mono<ResponseEntity<Flux<AccountPassiveModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(accountPassiveService.findAll()
                        .map(customer -> accountPassiveMapper.entityToModel(customer))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountPassiveModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<AccountPassive> response = accountPassiveService.findById(id);
        return response
                .map(customer -> accountPassiveMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/documentCard/{accountNumber}")
    public Mono<ResponseEntity<AccountPassiveModel>> getByIdAccountNumber(@PathVariable String accountNumber){
        log.info("getById executed {}", accountNumber);
        Mono<AccountPassive> response = accountPassiveService.findByAccountNumber(accountNumber);
        return response
                .map(customer -> accountPassiveMapper.entityToModel(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/documentDNI/{identityNumber}")
    public Mono<ResponseEntity<Client>> getByIdIdentityNumber(@PathVariable String identityNumber){
        log.info("getById executed {}", identityNumber);
        Mono<Client> response = clientService.getClient(identityNumber);
        return response
                .map(customer -> accountPassiveMapper
                        .entityToModelClient(customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<ResponseEntity<AccountPassiveModel>> create(@Valid @RequestBody AccountPassiveModel request){
        log.info("create executed {}", request);
        return accountPassiveService.create(accountPassiveMapper.modelToEntity(request))
                .map(accountPassive -> accountPassiveMapper.entityToModel(accountPassive))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "accountPassive", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AccountPassiveModel>> updateById(@PathVariable String id, @Valid @RequestBody AccountPassiveModel request){
        log.info("updateById executed {}:{}", id, request);
        return accountPassiveService.update(id, accountPassiveMapper.modelToEntity(request))
                .map(accountPassive -> accountPassiveMapper.entityToModel(accountPassive))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "accountPassive", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return accountPassiveService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/withdraw")
    public Mono<ResponseEntity<AccountPassiveModel>> withdraw(@Valid @RequestBody WithdrawModel withdrawModel) {
        log.info("withdraw executed {}", withdrawModel);
        return accountPassiveService.updateAccountBalance(withdrawModel.getAccountNumber(), withdrawModel.getAmount(),Action.WITHDRAW)
                .map(accountPassive -> accountPassiveMapper.entityToModel(accountPassive))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "accountPassive", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping(value = "/deposit")
    public Mono<ResponseEntity<AccountPassiveModel>> deposit(@Valid @RequestBody DepositModel depositModel) {
        log.info("deposit executed {}", depositModel);
        return accountPassiveService.updateAccountBalance(depositModel.getAccountNumber(), depositModel.getAmount(),Action.DEPOSIT)
                .map(accountPassive -> accountPassiveMapper.entityToModel(accountPassive))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "accountPassive", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
