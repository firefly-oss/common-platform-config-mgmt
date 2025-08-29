package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderTypeRepository extends BaseRepository<ProviderType, Long> {

    Flux<ProviderType> findByActiveTrue();

    Mono<ProviderType> findByCode(String code);
}
