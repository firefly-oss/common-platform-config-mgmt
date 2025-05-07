package com.catalis.common.config.models.repositories;

import com.catalis.common.config.models.entities.ProviderProcessStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderProcessStatusRepository extends BaseRepository<ProviderProcessStatus, Long> {

    Flux<ProviderProcessStatus> findByActiveTrue();

    Mono<ProviderProcessStatus> findByCode(String code);
}
