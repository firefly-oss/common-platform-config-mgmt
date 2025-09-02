package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderConfig;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderConfigRepository extends BaseRepository<ProviderConfig, UUID> {

    Flux<ProviderConfig> findByProviderId(UUID providerId);

    Flux<ProviderConfig> findByProviderIdAndActiveTrue(UUID providerId);

    Mono<ProviderConfig> findByProviderIdAndKey(UUID providerId, String key);

    Flux<ProviderConfig> findByProviderIdAndEnvironment(UUID providerId, String environment);

    Flux<ProviderConfig> findByProviderIdAndConfigGroup(UUID providerId, String configGroup);

    Flux<ProviderConfig> findByProviderIdAndIsSecret(UUID providerId, Boolean isSecret);

    Flux<ProviderConfig> findByProviderIdAndIsRequired(UUID providerId, Boolean isRequired);
}
