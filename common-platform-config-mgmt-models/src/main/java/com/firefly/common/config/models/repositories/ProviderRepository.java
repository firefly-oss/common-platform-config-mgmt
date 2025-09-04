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

import com.firefly.common.config.models.entities.Provider;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProviderRepository extends BaseRepository<Provider, UUID> {

    Flux<Provider> findByActiveTrue();

    Mono<Provider> findByCode(String code);

    Flux<Provider> findByProviderTypeId(UUID providerTypeId);

    Flux<Provider> findByProviderStatusId(UUID providerStatusId);

    Flux<Provider> findByCountryId(UUID countryId);

    Flux<Provider> findByRegion(String region);
}
