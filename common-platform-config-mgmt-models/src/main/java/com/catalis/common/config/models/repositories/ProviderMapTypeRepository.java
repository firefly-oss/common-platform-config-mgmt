package com.catalis.common.config.models.repositories;

import com.catalis.common.config.models.entities.ProviderMapType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderMapTypeRepository extends BaseRepository<ProviderMapType, Long> {

    Flux<ProviderMapType> findByActiveTrue();

    Mono<ProviderMapType> findByCode(String code);

    Flux<ProviderMapType> findByProviderMappingStatusId(Long providerMappingStatusId);
}
