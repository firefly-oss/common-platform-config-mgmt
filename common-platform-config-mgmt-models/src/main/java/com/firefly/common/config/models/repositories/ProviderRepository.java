package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.Provider;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderRepository extends BaseRepository<Provider, UUID> {

    Flux<Provider> findByActiveTrue();

    Mono<Provider> findByCode(String code);

    Flux<Provider> findByProviderTypeId(UUID providerTypeId);

    Flux<Provider> findByProviderStatusId(UUID providerStatusId);

    Flux<Provider> findByCountryId(UUID countryId);

    Flux<Provider> findByRegion(String region);
}
