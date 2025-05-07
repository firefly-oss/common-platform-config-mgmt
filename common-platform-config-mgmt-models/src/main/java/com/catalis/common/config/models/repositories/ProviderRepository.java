package com.catalis.common.config.models.repositories;

import com.catalis.common.config.models.entities.Provider;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderRepository extends BaseRepository<Provider, Long> {

    Flux<Provider> findByActiveTrue();

    Mono<Provider> findByCode(String code);

    Flux<Provider> findByProviderTypeId(Long providerTypeId);

    Flux<Provider> findByProviderStatusId(Long providerStatusId);

    Flux<Provider> findByCountryCode(String countryCode);

    Flux<Provider> findByRegion(String region);
}
