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


package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderProcessVersionDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider process versions
 */
public interface ProviderProcessVersionService {

    /**
     * Get a provider process version by ID
     * @param id Provider process version ID
     * @return Provider process version DTO
     */
    Mono<ProviderProcessVersionDTO> getById(UUID id);

    /**
     * Filter provider process versions based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider process versions
     */
    Mono<PaginationResponse<ProviderProcessVersionDTO>> filter(FilterRequest<ProviderProcessVersionDTO> filterRequest);

    /**
     * Create a new provider process version
     * @param providerProcessVersionDTO Provider process version DTO
     * @return Created provider process version DTO
     */
    Mono<ProviderProcessVersionDTO> create(ProviderProcessVersionDTO providerProcessVersionDTO);

    /**
     * Update an existing provider process version
     * @param id Provider process version ID
     * @param providerProcessVersionDTO Provider process version DTO
     * @return Updated provider process version DTO
     */
    Mono<ProviderProcessVersionDTO> update(UUID id, ProviderProcessVersionDTO providerProcessVersionDTO);

    /**
     * Delete a provider process version
     * @param id Provider process version ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}
