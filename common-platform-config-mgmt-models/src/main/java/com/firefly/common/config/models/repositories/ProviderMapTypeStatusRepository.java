package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderMapTypeStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderMapTypeStatusRepository extends BaseRepository<ProviderMapTypeStatus, UUID> {

    Flux<ProviderMapTypeStatus> findByActiveTrue();

    Mono<ProviderMapTypeStatus> findByCode(String code);
}