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

import com.firefly.common.config.interfaces.dtos.ProviderMappingStatusDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider mapping statuses
 */
public interface ProviderMappingStatusService {

    /**
     * Get a provider mapping status by ID
     * @param id Provider mapping status ID
     * @return Provider mapping status DTO
     */
    Mono<ProviderMappingStatusDTO> getById(UUID id);

    /**
     * Filter provider mapping statuses based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider mapping statuses
     */
    Mono<PaginationResponse<ProviderMappingStatusDTO>> filter(FilterRequest<ProviderMappingStatusDTO> filterRequest);

    /**
     * Create a new provider mapping status
     * @param providerMappingStatusDTO Provider mapping status DTO
     * @return Created provider mapping status DTO
     */
    Mono<ProviderMappingStatusDTO> create(ProviderMappingStatusDTO providerMappingStatusDTO);

    /**
     * Update an existing provider mapping status
     * @param id Provider mapping status ID
     * @param providerMappingStatusDTO Provider mapping status DTO
     * @return Updated provider mapping status DTO
     */
    Mono<ProviderMappingStatusDTO> update(UUID id, ProviderMappingStatusDTO providerMappingStatusDTO);

    /**
     * Delete a provider mapping status
     * @param id Provider mapping status ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}