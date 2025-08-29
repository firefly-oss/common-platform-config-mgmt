package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderStatusDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider statuses
 */
public interface ProviderStatusService {

    /**
     * Get a provider status by ID
     * @param id Provider status ID
     * @return Provider status DTO
     */
    Mono<ProviderStatusDTO> getById(Long id);

    /**
     * Filter provider statuses based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider statuses
     */
    Mono<PaginationResponse<ProviderStatusDTO>> filter(FilterRequest<ProviderStatusDTO> filterRequest);

    /**
     * Create a new provider status
     * @param providerStatusDTO Provider status DTO
     * @return Created provider status DTO
     */
    Mono<ProviderStatusDTO> create(ProviderStatusDTO providerStatusDTO);

    /**
     * Update an existing provider status
     * @param id Provider status ID
     * @param providerStatusDTO Provider status DTO
     * @return Updated provider status DTO
     */
    Mono<ProviderStatusDTO> update(Long id, ProviderStatusDTO providerStatusDTO);

    /**
     * Delete a provider status
     * @param id Provider status ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}
