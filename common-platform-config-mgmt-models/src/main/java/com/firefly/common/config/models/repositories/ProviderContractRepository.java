package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderContract;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderContractRepository extends BaseRepository<ProviderContract, UUID> {

    Flux<ProviderContract> findByActiveTrue();

    Flux<ProviderContract> findByProviderId(UUID providerId);

    Flux<ProviderContract> findByProviderContractStatusId(UUID providerContractStatusId);

    Flux<ProviderContract> findByContractTypeId(UUID contractTypeId);

    Mono<ProviderContract> findByContractId(UUID contractId);
}