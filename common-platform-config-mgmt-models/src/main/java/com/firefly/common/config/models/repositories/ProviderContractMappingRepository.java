package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderContractMapping;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderContractMappingRepository extends BaseRepository<ProviderContractMapping, UUID> {

    Flux<ProviderContractMapping> findByActiveTrue();

    Flux<ProviderContractMapping> findByProviderContractId(UUID providerContractId);

    Flux<ProviderContractMapping> findByInternalContractId(UUID internalContractId);

    Mono<ProviderContractMapping> findByProviderContractIdAndInternalContractId(UUID providerContractId, UUID internalContractId);
}