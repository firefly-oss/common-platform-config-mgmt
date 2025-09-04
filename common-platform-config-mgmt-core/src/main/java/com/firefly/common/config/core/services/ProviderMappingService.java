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

import com.firefly.common.config.interfaces.dtos.ProviderMappingDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider mappings
 */
public interface ProviderMappingService {

    /**
     * Get a provider mapping by ID
     * @param id Provider mapping ID
     * @return Provider mapping DTO
     */
    Mono<ProviderMappingDTO> getById(UUID id);

    /**
     * Filter provider mappings based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider mappings
     */
    Mono<PaginationResponse<ProviderMappingDTO>> filter(FilterRequest<ProviderMappingDTO> filterRequest);

    /**
     * Create a new provider mapping
     * @param providerMappingDTO Provider mapping DTO
     * @return Created provider mapping DTO
     */
    Mono<ProviderMappingDTO> create(ProviderMappingDTO providerMappingDTO);

    /**
     * Update an existing provider mapping
     * @param id Provider mapping ID
     * @param providerMappingDTO Provider mapping DTO
     * @return Updated provider mapping DTO
     */
    Mono<ProviderMappingDTO> update(UUID id, ProviderMappingDTO providerMappingDTO);

    /**
     * Delete a provider mapping
     * @param id Provider mapping ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}