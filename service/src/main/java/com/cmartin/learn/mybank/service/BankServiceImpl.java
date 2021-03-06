package com.cmartin.learn.mybank.service;

import com.cmartin.learn.domain.Account;
import com.cmartin.learn.mybank.AccountRepository;
import com.cmartin.learn.mybank.api.*;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cmartin.learn.mybank.service.SimpleMapper.mapRepositoryToService;
import static com.cmartin.learn.mybank.service.SimpleMapper.mapServiceToRepository;

/**
 * Created by cmartin on 10/07/16.
 */
@Service
public class BankServiceImpl implements BankService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRepository bankRepository;

    public BankServiceImpl(final AccountRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Try<OperationOutputDto> operation(final OperationInputDto operationInputDto) {
        return Try.success(new OperationOutputDto("dummyProperty"));
    }

    @Override
    public List<ContractDto> getContracts(final ContractFilter filter) {
        return null;
    }

    @Override
    public List<ContractDto> getUserContracts(final UUID userId) {
        return null;
    }

    @Override
    public List<AccountDto> getAccounts(final AccountFilter filter) {
        // dummy impl
        //return newAccountList();

        //TODO validate filter and build criteria

        final Iterable<Account> accounts = this.bankRepository.findAll();

        List<AccountDto> dtos = SimpleMapper.mapRepositoryToService(accounts);
        return dtos;
    }

    @Override
    public List<AccountDto> getUserAccounts(final UUID userId) {
        return null;
    }

    @Override
    public Optional<AccountDto> getAccount(final UUID accountId) {

        final Optional<Account> accountOption = this.bankRepository.findOne(accountId);

        //TODO pattern matching with vavr

        return accountOption.isPresent() ? Optional.of(mapRepositoryToService(accountOption.get())) :
                Optional.empty();
    }

    @Override
    public Try<UUID> findAccountById(final UUID id) {
        return Try.success(id);
    }

    @Override
    public Try<AccountDto> createAccount(final AccountDto request) {
        this.logger.debug("input: {}", request);

        //TODO validate dto

        //TODO mapstruct mapper service => repository
        final Optional<Account> accountOption = this.bankRepository.save(mapServiceToRepository(request));

        //TODO pattern matching with vavr
        return accountOption.isPresent() ? Try.success(mapRepositoryToService(accountOption.get())) :
                ServiceHelper.buildError("Error while storing the entity");
    }

    @Override
    public Try<AccountDto> updateAccount(final AccountDto accountDto) {
        return Try.success(accountDto);
    }

    @Override
    public Try<UUID> deleteAccount(final UUID accountId) {
        return Try.success(accountId);
    }

    @Override
    public List<AccountTransactionDto> getAccountTransactions(final UUID accountId) {
        return new ArrayList<>();
    }

    @Override
    public Optional<AccountTransactionDto> getAccountTransaction(final UUID accountTransactionId) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> getUser(final UUID userId) {
        return Optional.empty();
    }

    @Override
    public List<UserDto> getUsers(final UserFilter filter) {
        return new ArrayList<>();
    }

    @Override
    public List<UserDto> getAccountUsers(final UUID accountId) {
        return new ArrayList<>();
    }

}
