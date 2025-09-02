package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderProcessVersionDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider process versions
 */
public interface ProviderProcessVersionService {

    /**
     * Get a provider process version by ID
     * @param id Provider process version ID
     * @return Provider process version DTO
     */
    Mono<ProviderProcessVersionDTO> getById(UUID id);

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
    Mono<ProviderProcessVersionDTO> update(UUID id, ProviderProcessVersionDTO providerProcessVersionDTO);

    /**
     * Delete a provider process version
     * @param id Provider process version ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}
