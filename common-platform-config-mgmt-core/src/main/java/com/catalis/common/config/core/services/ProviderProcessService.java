package com.catalis.common.config.core.services;

import com.catalis.common.config.interfaces.dtos.ProviderProcessDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider processes
 */
public interface ProviderProcessService {

    /**
     * Get a provider process by ID
     * @param id Provider process ID
     * @return Provider process DTO
     */
    Mono<ProviderProcessDTO> getById(Long id);

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
    Mono<ProviderProcessDTO> update(Long id, ProviderProcessDTO providerProcessDTO);

    /**
     * Delete a provider process
     * @param id Provider process ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}
