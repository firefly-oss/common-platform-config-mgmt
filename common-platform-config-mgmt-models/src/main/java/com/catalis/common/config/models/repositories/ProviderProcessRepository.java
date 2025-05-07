package com.catalis.common.config.models.repositories;

import com.catalis.common.config.models.entities.ProviderProcess;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderProcessRepository extends BaseRepository<ProviderProcess, Long> {

    Flux<ProviderProcess> findByActiveTrue();

    Mono<ProviderProcess> findByCode(String code);

    Flux<ProviderProcess> findByProviderId(Long providerId);

    Flux<ProviderProcess> findByProviderIdAndActiveTrue(Long providerId);

    Flux<ProviderProcess> findByIsCommonTrue();

    Flux<ProviderProcess> findByProcessType(String processType);

    Flux<ProviderProcess> findByProcessCategory(String processCategory);

    Flux<ProviderProcess> findByProviderIdAndProcessType(Long providerId, String processType);
}
