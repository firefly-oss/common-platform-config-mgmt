package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderMapTypeStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderMapTypeStatusRepository extends BaseRepository<ProviderMapTypeStatus, Long> {

    Flux<ProviderMapTypeStatus> findByActiveTrue();

    Mono<ProviderMapTypeStatus> findByCode(String code);
}