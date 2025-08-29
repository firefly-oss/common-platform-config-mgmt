package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderProcessStatusDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider process statuses
 */
public interface ProviderProcessStatusService {

    /**
     * Get a provider process status by ID
     * @param id Provider process status ID
     * @return Provider process status DTO
     */
    Mono<ProviderProcessStatusDTO> getById(Long id);

    /**
     * Filter provider process statuses based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider process statuses
     */
    Mono<PaginationResponse<ProviderProcessStatusDTO>> filter(FilterRequest<ProviderProcessStatusDTO> filterRequest);

    /**
     * Create a new provider process status
     * @param providerProcessStatusDTO Provider process status DTO
     * @return Created provider process status DTO
     */
    Mono<ProviderProcessStatusDTO> create(ProviderProcessStatusDTO providerProcessStatusDTO);

    /**
     * Update an existing provider process status
     * @param id Provider process status ID
     * @param providerProcessStatusDTO Provider process status DTO
     * @return Updated provider process status DTO
     */
    Mono<ProviderProcessStatusDTO> update(Long id, ProviderProcessStatusDTO providerProcessStatusDTO);

    /**
     * Delete a provider process status
     * @param id Provider process status ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}
