package com.catalis.common.config.models.repositories;

import com.catalis.common.config.models.entities.ProviderMappingStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderMappingStatusRepository extends BaseRepository<ProviderMappingStatus, Long> {

    Flux<ProviderMappingStatus> findByActiveTrue();

    Mono<ProviderMappingStatus> findByCode(String code);
}