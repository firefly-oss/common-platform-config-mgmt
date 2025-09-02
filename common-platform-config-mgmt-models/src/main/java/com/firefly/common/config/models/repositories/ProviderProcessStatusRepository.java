package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderProcessStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderProcessStatusRepository extends BaseRepository<ProviderProcessStatus, UUID> {

    Flux<ProviderProcessStatus> findByActiveTrue();

    Mono<ProviderProcessStatus> findByCode(String code);
}
