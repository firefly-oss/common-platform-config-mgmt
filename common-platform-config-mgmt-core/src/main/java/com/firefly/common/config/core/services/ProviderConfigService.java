package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderConfigDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider configurations
 */
public interface ProviderConfigService {

    /**
     * Get a provider configuration by ID
     * @param id Provider configuration ID
     * @return Provider configuration DTO
     */
    Mono<ProviderConfigDTO> getById(UUID id);

    /**
     * Filter provider configurations based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider configurations
     */
    Mono<PaginationResponse<ProviderConfigDTO>> filter(FilterRequest<ProviderConfigDTO> filterRequest);

    /**
     * Create a new provider configuration
     * @param providerConfigDTO Provider configuration DTO
     * @return Created provider configuration DTO
     */
    Mono<ProviderConfigDTO> create(ProviderConfigDTO providerConfigDTO);

    /**
     * Update an existing provider configuration
     * @param id Provider configuration ID
     * @param providerConfigDTO Provider configuration DTO
     * @return Updated provider configuration DTO
     */
    Mono<ProviderConfigDTO> update(UUID id, ProviderConfigDTO providerConfigDTO);

    /**
     * Delete a provider configuration
     * @param id Provider configuration ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}
