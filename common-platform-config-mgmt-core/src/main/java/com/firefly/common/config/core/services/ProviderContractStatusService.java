package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderContractStatusDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider contract statuses
 */
public interface ProviderContractStatusService {

    /**
     * Get a provider contract status by ID
     * @param id Provider contract status ID
     * @return Provider contract status DTO
     */
    Mono<ProviderContractStatusDTO> getById(Long id);

    /**
     * Filter provider contract statuses based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider contract statuses
     */
    Mono<PaginationResponse<ProviderContractStatusDTO>> filter(FilterRequest<ProviderContractStatusDTO> filterRequest);

    /**
     * Create a new provider contract status
     * @param providerContractStatusDTO Provider contract status DTO
     * @return Created provider contract status DTO
     */
    Mono<ProviderContractStatusDTO> create(ProviderContractStatusDTO providerContractStatusDTO);

    /**
     * Update an existing provider contract status
     * @param id Provider contract status ID
     * @param providerContractStatusDTO Provider contract status DTO
     * @return Updated provider contract status DTO
     */
    Mono<ProviderContractStatusDTO> update(Long id, ProviderContractStatusDTO providerContractStatusDTO);

    /**
     * Delete a provider contract status
     * @param id Provider contract status ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}