package com.catalis.common.config.models.repositories;

import com.catalis.common.config.models.entities.ProviderStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderStatusRepository extends BaseRepository<ProviderStatus, Long> {

    Flux<ProviderStatus> findByActiveTrue();

    Mono<ProviderStatus> findByCode(String code);
}
