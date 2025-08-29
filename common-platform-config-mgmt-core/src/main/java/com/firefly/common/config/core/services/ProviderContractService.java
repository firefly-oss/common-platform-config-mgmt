package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderContractDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider contracts
 */
public interface ProviderContractService {

    /**
     * Get a provider contract by ID
     * @param id Provider contract ID
     * @return Provider contract DTO
     */
    Mono<ProviderContractDTO> getById(Long id);

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
    Mono<ProviderContractDTO> update(Long id, ProviderContractDTO providerContractDTO);

    /**
     * Delete a provider contract
     * @param id Provider contract ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}