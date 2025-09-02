package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderProcess;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderProcessRepository extends BaseRepository<ProviderProcess, UUID> {

    Flux<ProviderProcess> findByActiveTrue();

    Mono<ProviderProcess> findByCode(String code);

    Flux<ProviderProcess> findByProviderId(UUID providerId);

    Flux<ProviderProcess> findByProviderIdAndActiveTrue(UUID providerId);

    Flux<ProviderProcess> findByIsCommonTrue();

    Flux<ProviderProcess> findByProcessType(String processType);

    Flux<ProviderProcess> findByProcessCategory(String processCategory);

    Flux<ProviderProcess> findByProviderIdAndProcessType(UUID providerId, String processType);
}
