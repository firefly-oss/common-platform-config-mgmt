package com.catalis.common.config.models.repositories;

import com.catalis.common.config.models.entities.ProviderContractMapping;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderContractMappingRepository extends BaseRepository<ProviderContractMapping, Long> {

    Flux<ProviderContractMapping> findByActiveTrue();

    Flux<ProviderContractMapping> findByProviderContractId(Long providerContractId);

    Flux<ProviderContractMapping> findByInternalContractId(Long internalContractId);

    Mono<ProviderContractMapping> findByProviderContractIdAndInternalContractId(Long providerContractId, Long internalContractId);
}