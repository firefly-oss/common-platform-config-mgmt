package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderMapping;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProviderMappingRepository extends BaseRepository<ProviderMapping, Long> {

    Flux<ProviderMapping> findByActiveTrue();

    Flux<ProviderMapping> findByProviderMapTypeId(Long providerMapTypeId);

    Flux<ProviderMapping> findByProviderId(Long providerId);

    Flux<ProviderMapping> findByInternalProviderId(Long internalProviderId);

    Flux<ProviderMapping> findByProviderIdAndInternalProviderId(Long providerId, Long internalProviderId);

    Flux<ProviderMapping> findByProviderMapTypeIdAndProviderId(Long providerMapTypeId, Long providerId);

    Flux<ProviderMapping> findByProviderMapTypeIdAndInternalProviderId(Long providerMapTypeId, Long internalProviderId);
}