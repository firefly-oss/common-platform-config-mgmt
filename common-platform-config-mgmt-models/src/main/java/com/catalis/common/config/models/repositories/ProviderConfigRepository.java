package com.catalis.common.config.models.repositories;

import com.catalis.common.config.models.entities.ProviderConfig;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderConfigRepository extends BaseRepository<ProviderConfig, Long> {

    Flux<ProviderConfig> findByProviderId(Long providerId);

    Flux<ProviderConfig> findByProviderIdAndActiveTrue(Long providerId);

    Mono<ProviderConfig> findByProviderIdAndKey(Long providerId, String key);

    Flux<ProviderConfig> findByProviderIdAndEnvironment(Long providerId, String environment);

    Flux<ProviderConfig> findByProviderIdAndConfigGroup(Long providerId, String configGroup);

    Flux<ProviderConfig> findByProviderIdAndIsSecret(Long providerId, Boolean isSecret);

    Flux<ProviderConfig> findByProviderIdAndIsRequired(Long providerId, Boolean isRequired);
}
