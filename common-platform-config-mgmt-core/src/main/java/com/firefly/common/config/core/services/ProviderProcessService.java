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

import com.firefly.common.config.interfaces.dtos.ProviderProcessDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider processes
 */
public interface ProviderProcessService {

    /**
     * Get a provider process by ID
     * @param id Provider process ID
     * @return Provider process DTO
     */
    Mono<ProviderProcessDTO> getById(UUID id);

    /**
     * Filter provider processes based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider processes
     */
    Mono<PaginationResponse<ProviderProcessDTO>> filter(FilterRequest<ProviderProcessDTO> filterRequest);

    /**
     * Create a new provider process
     * @param providerProcessDTO Provider process DTO
     * @return Created provider process DTO
     */
    Mono<ProviderProcessDTO> create(ProviderProcessDTO providerProcessDTO);

    /**
     * Update an existing provider process
     * @param id Provider process ID
     * @param providerProcessDTO Provider process DTO
     * @return Updated provider process DTO
     */
    Mono<ProviderProcessDTO> update(UUID id, ProviderProcessDTO providerProcessDTO);

    /**
     * Delete a provider process
     * @param id Provider process ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}
