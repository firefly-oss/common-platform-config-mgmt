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

import com.firefly.common.config.interfaces.dtos.ProviderContractDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider contracts
 */
public interface ProviderContractService {

    /**
     * Get a provider contract by ID
     * @param id Provider contract ID
     * @return Provider contract DTO
     */
    Mono<ProviderContractDTO> getById(UUID id);

    /**
     * Filter provider contracts based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider contracts
     */
    Mono<PaginationResponse<ProviderContractDTO>> filter(FilterRequest<ProviderContractDTO> filterRequest);

    /**
     * Create a new provider contract
     * @param providerContractDTO Provider contract DTO
     * @return Created provider contract DTO
     */
    Mono<ProviderContractDTO> create(ProviderContractDTO providerContractDTO);

    /**
     * Update an existing provider contract
     * @param id Provider contract ID
     * @param providerContractDTO Provider contract DTO
     * @return Updated provider contract DTO
     */
    Mono<ProviderContractDTO> update(UUID id, ProviderContractDTO providerContractDTO);

    /**
     * Delete a provider contract
     * @param id Provider contract ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}