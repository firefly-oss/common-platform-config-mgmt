package com.catalis.common.config.models.repositories;

import com.catalis.common.config.models.entities.ProviderProcessVersion;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderProcessVersionRepository extends BaseRepository<ProviderProcessVersion, Long> {

    Flux<ProviderProcessVersion> findByProviderProcessId(Long providerProcessId);

    Flux<ProviderProcessVersion> findByProviderProcessIdOrderByCreatedAtDesc(Long providerProcessId);

    Mono<ProviderProcessVersion> findByProviderProcessIdAndVersion(Long providerProcessId, String version);

    Mono<ProviderProcessVersion> findByProviderProcessIdAndIsCurrentTrue(Long providerProcessId);

    Flux<ProviderProcessVersion> findByProviderProcessStatusId(Long providerProcessStatusId);

    Flux<ProviderProcessVersion> findByIsDeployedTrue();

    Mono<ProviderProcessVersion> findByDeploymentId(String deploymentId);
}
