/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
