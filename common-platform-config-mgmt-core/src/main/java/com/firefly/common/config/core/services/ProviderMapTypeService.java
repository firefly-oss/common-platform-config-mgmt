package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderMapTypeDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing provider map types
 */
public interface ProviderMapTypeService {

    /**
     * Get a provider map type by ID
     * @param id Provider map type ID
     * @return Provider map type DTO
     */
    Mono<ProviderMapTypeDTO> getById(Long id);

    /**
     * Filter provider map types based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider map types
     */
    Mono<PaginationResponse<ProviderMapTypeDTO>> filter(FilterRequest<ProviderMapTypeDTO> filterRequest);

    /**
     * Create a new provider map type
     * @param providerMapTypeDTO Provider map type DTO
     * @return Created provider map type DTO
     */
    Mono<ProviderMapTypeDTO> create(ProviderMapTypeDTO providerMapTypeDTO);

    /**
     * Update an existing provider map type
     * @param id Provider map type ID
     * @param providerMapTypeDTO Provider map type DTO
     * @return Updated provider map type DTO
     */
    Mono<ProviderMapTypeDTO> update(Long id, ProviderMapTypeDTO providerMapTypeDTO);

    /**
     * Delete a provider map type
     * @param id Provider map type ID
     * @return Void
     */
    Mono<Void> delete(Long id);
}