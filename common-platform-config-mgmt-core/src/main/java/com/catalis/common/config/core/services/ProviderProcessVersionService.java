package com.catalis.common.config.core.services;

import com.catalis.common.config.interfaces.dtos.ProviderProcessVersionDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider process versions
 */
public interface ProviderProcessVersionService {

    /**
     * Get a provider process version by ID
     * @param id Provider process version ID
     * @return Provider process version DTO
     */
    Mono<ProviderProcessVersionDTO> getById(Long id);

    /**
     * Filter provider process versions based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider process versions
     */
    Mono<PaginationResponse<ProviderProcessVersionDTO>> filter(FilterRequest<ProviderProcessVersionDTO> filterRequest);

    /**
     * Create a new provider process version
     * @param providerProcessVersionDTO Provider process version DTO
     * @return Created provider process version DTO
     */
    Mono<ProviderProcessVersionDTO> create(ProviderProcessVersionDTO providerProcessVersionDTO);

    /**
     * Update an existing provider process version
     * @param id Provider process version ID
     * @param providerProcessVersionDTO Provider process version DTO
     * @return Updated provider process version DTO
     */
    Mono<ProviderProcessVersionDTO> update(Long id, ProviderProcessVersionDTO providerProcessVersionDTO);

    /**
     * Delete a provider process version
     * @param id Provider process version ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}
