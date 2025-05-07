package com.catalis.common.config.core.services;

import com.catalis.common.config.interfaces.dtos.ProviderDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing providers
 */
public interface ProviderService {

    /**
     * Get a provider by ID
     * @param id Provider ID
     * @return Provider DTO
     */
    Mono<ProviderDTO> getById(Long id);

    /**
     * Filter providers based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of providers
     */
    Mono<PaginationResponse<ProviderDTO>> filter(FilterRequest<ProviderDTO> filterRequest);

    /**
     * Create a new provider
     * @param providerDTO Provider DTO
     * @return Created provider DTO
     */
    Mono<ProviderDTO> create(ProviderDTO providerDTO);

    /**
     * Update an existing provider
     * @param id Provider ID
     * @param providerDTO Provider DTO
     * @return Updated provider DTO
     */
    Mono<ProviderDTO> update(Long id, ProviderDTO providerDTO);

    /**
     * Delete a provider
     * @param id Provider ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}
