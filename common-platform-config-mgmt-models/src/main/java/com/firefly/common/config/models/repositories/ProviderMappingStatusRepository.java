package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderMappingStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderMappingStatusRepository extends BaseRepository<ProviderMappingStatus, UUID> {

    Flux<ProviderMappingStatus> findByActiveTrue();

    Mono<ProviderMappingStatus> findByCode(String code);
}