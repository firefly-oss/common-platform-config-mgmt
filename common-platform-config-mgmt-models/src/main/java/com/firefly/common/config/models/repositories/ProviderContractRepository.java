package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderContract;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderContractRepository extends BaseRepository<ProviderContract, Long> {

    Flux<ProviderContract> findByActiveTrue();

    Flux<ProviderContract> findByProviderId(Long providerId);

    Flux<ProviderContract> findByProviderContractStatusId(Long providerContractStatusId);

    Flux<ProviderContract> findByContractTypeId(Long contractTypeId);

    Mono<ProviderContract> findByContractId(Long contractId);
}