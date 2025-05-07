package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.services.ProviderTypeService;
import com.catalis.common.config.interfaces.dtos.ProviderTypeDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderTypeService interface
 */
@Service
public class ProviderTypeServiceImpl implements ProviderTypeService {

    @Override
    public Mono<ProviderTypeDTO> getById(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<PaginationResponse<ProviderTypeDTO>> filter(FilterRequest<ProviderTypeDTO> filterRequest) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderTypeDTO> create(ProviderTypeDTO providerTypeDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderTypeDTO> update(Long id, ProviderTypeDTO providerTypeDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }
}
