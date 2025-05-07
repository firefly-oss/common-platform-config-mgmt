package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.services.ProviderService;
import com.catalis.common.config.interfaces.dtos.ProviderDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderService interface
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    @Override
    public Mono<ProviderDTO> getById(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<PaginationResponse<ProviderDTO>> filter(FilterRequest<ProviderDTO> filterRequest) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderDTO> create(ProviderDTO providerDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderDTO> update(Long id, ProviderDTO providerDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }
}
