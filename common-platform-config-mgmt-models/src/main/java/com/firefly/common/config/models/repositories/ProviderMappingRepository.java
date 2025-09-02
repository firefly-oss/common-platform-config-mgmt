package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderMapping;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Repository
public interface ProviderMappingRepository extends BaseRepository<ProviderMapping, UUID> {

    Flux<ProviderMapping> findByActiveTrue();

    Flux<ProviderMapping> findByProviderMapTypeId(UUID providerMapTypeId);

    Flux<ProviderMapping> findByProviderId(UUID providerId);

    Flux<ProviderMapping> findByInternalProviderId(UUID internalProviderId);

    Flux<ProviderMapping> findByProviderIdAndInternalProviderId(UUID providerId, UUID internalProviderId);

    Flux<ProviderMapping> findByProviderMapTypeIdAndProviderId(UUID providerMapTypeId, UUID providerId);

    Flux<ProviderMapping> findByProviderMapTypeIdAndInternalProviderId(UUID providerMapTypeId, UUID internalProviderId);
}