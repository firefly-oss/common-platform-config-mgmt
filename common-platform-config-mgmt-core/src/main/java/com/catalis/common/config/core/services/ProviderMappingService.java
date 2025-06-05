package com.catalis.common.config.core.services;

import com.catalis.common.config.interfaces.dtos.ProviderMappingDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider mappings
 */
public interface ProviderMappingService {

    /**
     * Get a provider mapping by ID
     * @param id Provider mapping ID
     * @return Provider mapping DTO
     */
    Mono<ProviderMappingDTO> getById(Long id);

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
    Mono<ProviderMappingDTO> update(Long id, ProviderMappingDTO providerMappingDTO);

    /**
     * Delete a provider mapping
     * @param id Provider mapping ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}