package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderMappingStatusDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider mapping statuses
 */
public interface ProviderMappingStatusService {

    /**
     * Get a provider mapping status by ID
     * @param id Provider mapping status ID
     * @return Provider mapping status DTO
     */
    Mono<ProviderMappingStatusDTO> getById(Long id);

    /**
     * Filter provider mapping statuses based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider mapping statuses
     */
    Mono<PaginationResponse<ProviderMappingStatusDTO>> filter(FilterRequest<ProviderMappingStatusDTO> filterRequest);

    /**
     * Create a new provider mapping status
     * @param providerMappingStatusDTO Provider mapping status DTO
     * @return Created provider mapping status DTO
     */
    Mono<ProviderMappingStatusDTO> create(ProviderMappingStatusDTO providerMappingStatusDTO);

    /**
     * Update an existing provider mapping status
     * @param id Provider mapping status ID
     * @param providerMappingStatusDTO Provider mapping status DTO
     * @return Updated provider mapping status DTO
     */
    Mono<ProviderMappingStatusDTO> update(Long id, ProviderMappingStatusDTO providerMappingStatusDTO);

    /**
     * Delete a provider mapping status
     * @param id Provider mapping status ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}