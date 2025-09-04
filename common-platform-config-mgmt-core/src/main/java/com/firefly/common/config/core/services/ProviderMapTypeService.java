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

import com.firefly.common.config.interfaces.dtos.ProviderMapTypeDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider map types
 */
public interface ProviderMapTypeService {

    /**
     * Get a provider map type by ID
     * @param id Provider map type ID
     * @return Provider map type DTO
     */
    Mono<ProviderMapTypeDTO> getById(UUID id);

    /**
     * Filter provider map types based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider map types
     */
    Mono<PaginationResponse<ProviderMapTypeDTO>> filter(FilterRequest<ProviderMapTypeDTO> filterRequest);

    /**
     * Create a new provider map type
     * @param providerMapTypeDTO Provider map type DTO
     * @return Created provider map type DTO
     */
    Mono<ProviderMapTypeDTO> create(ProviderMapTypeDTO providerMapTypeDTO);

    /**
     * Update an existing provider map type
     * @param id Provider map type ID
     * @param providerMapTypeDTO Provider map type DTO
     * @return Updated provider map type DTO
     */
    Mono<ProviderMapTypeDTO> update(UUID id, ProviderMapTypeDTO providerMapTypeDTO);

    /**
     * Delete a provider map type
     * @param id Provider map type ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}