package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderTypeDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider types
 */
public interface ProviderTypeService {

    /**
     * Get a provider type by ID
     * @param id Provider type ID
     * @return Provider type DTO
     */
    Mono<ProviderTypeDTO> getById(UUID id);

    /**
     * Filter provider types based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider types
     */
    Mono<PaginationResponse<ProviderTypeDTO>> filter(FilterRequest<ProviderTypeDTO> filterRequest);

    /**
     * Create a new provider type
     * @param providerTypeDTO Provider type DTO
     * @return Created provider type DTO
     */
    Mono<ProviderTypeDTO> create(ProviderTypeDTO providerTypeDTO);

    /**
     * Update an existing provider type
     * @param id Provider type ID
     * @param providerTypeDTO Provider type DTO
     * @return Updated provider type DTO
     */
    Mono<ProviderTypeDTO> update(UUID id, ProviderTypeDTO providerTypeDTO);

    /**
     * Delete a provider type
     * @param id Provider type ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}
