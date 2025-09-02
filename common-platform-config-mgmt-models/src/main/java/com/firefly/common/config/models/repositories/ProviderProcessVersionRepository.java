package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderProcessVersion;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderProcessVersionRepository extends BaseRepository<ProviderProcessVersion, UUID> {

    Flux<ProviderProcessVersion> findByProviderProcessId(UUID providerProcessId);

    Flux<ProviderProcessVersion> findByProviderProcessIdOrderByCreatedAtDesc(UUID providerProcessId);

    Mono<ProviderProcessVersion> findByProviderProcessIdAndVersion(UUID providerProcessId, String version);

    Mono<ProviderProcessVersion> findByProviderProcessIdAndIsCurrentTrue(UUID providerProcessId);

    Flux<ProviderProcessVersion> findByProviderProcessStatusId(UUID providerProcessStatusId);

    Flux<ProviderProcessVersion> findByIsDeployedTrue();

    Mono<ProviderProcessVersion> findByDeploymentId(String deploymentId);
}
