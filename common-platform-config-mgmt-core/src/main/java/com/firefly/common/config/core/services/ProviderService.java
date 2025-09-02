package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing providers
 */
public interface ProviderService {

    /**
     * Get a provider by ID
     * @param id Provider ID
     * @return Provider DTO
     */
    Mono<ProviderDTO> getById(UUID id);

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
    Mono<ProviderDTO> update(UUID id, ProviderDTO providerDTO);

    /**
     * Delete a provider
     * @param id Provider ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}
