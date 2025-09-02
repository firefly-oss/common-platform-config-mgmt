package com.firefly.common.config.core.services;

import com.firefly.common.config.interfaces.dtos.ProviderContractMappingDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing provider contract mappings
 */
public interface ProviderContractMappingService {

    /**
     * Get a provider contract mapping by ID
     * @param id Provider contract mapping ID
     * @return Provider contract mapping DTO
     */
    Mono<ProviderContractMappingDTO> getById(UUID id);

    /**
     * Filter provider contract mappings based on criteria
     * @param filterRequest Filter criteria
     * @return Paginated list of provider contract mappings
     */
    Mono<PaginationResponse<ProviderContractMappingDTO>> filter(FilterRequest<ProviderContractMappingDTO> filterRequest);

    /**
     * Create a new provider contract mapping
     * @param providerContractMappingDTO Provider contract mapping DTO
     * @return Created provider contract mapping DTO
     */
    Mono<ProviderContractMappingDTO> create(ProviderContractMappingDTO providerContractMappingDTO);

    /**
     * Update an existing provider contract mapping
     * @param id Provider contract mapping ID
     * @param providerContractMappingDTO Provider contract mapping DTO
     * @return Updated provider contract mapping DTO
     */
    Mono<ProviderContractMappingDTO> update(UUID id, ProviderContractMappingDTO providerContractMappingDTO);

    /**
     * Delete a provider contract mapping
     * @param id Provider contract mapping ID
     * @return Void
     */
    Mono<Void> delete(UUID id);
}