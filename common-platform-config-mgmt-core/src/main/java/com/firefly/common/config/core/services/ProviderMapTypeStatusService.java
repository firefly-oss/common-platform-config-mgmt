package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderMapTypeStatusDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider map type statuses
 */
public interface ProviderMapTypeStatusService {

    /**
     * Get a provider map type status by ID
     * @param id Provider map type status ID
     * @return Provider map type status DTO
     */
    Mono<ProviderMapTypeStatusDTO> getById(Long id);

    /**
     * Filter provider map type statuses based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider map type statuses
     */
    Mono<PaginationResponse<ProviderMapTypeStatusDTO>> filter(FilterRequest<ProviderMapTypeStatusDTO> filterRequest);

    /**
     * Create a new provider map type status
     * @param providerMapTypeStatusDTO Provider map type status DTO
     * @return Created provider map type status DTO
     */
    Mono<ProviderMapTypeStatusDTO> create(ProviderMapTypeStatusDTO providerMapTypeStatusDTO);

    /**
     * Update an existing provider map type status
     * @param id Provider map type status ID
     * @param providerMapTypeStatusDTO Provider map type status DTO
     * @return Updated provider map type status DTO
     */
    Mono<ProviderMapTypeStatusDTO> update(Long id, ProviderMapTypeStatusDTO providerMapTypeStatusDTO);

    /**
     * Delete a provider map type status
     * @param id Provider map type status ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}