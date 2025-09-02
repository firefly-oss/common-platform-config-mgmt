package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderStatusRepository extends BaseRepository<ProviderStatus, UUID> {

    Flux<ProviderStatus> findByActiveTrue();

    Mono<ProviderStatus> findByCode(String code);
}
