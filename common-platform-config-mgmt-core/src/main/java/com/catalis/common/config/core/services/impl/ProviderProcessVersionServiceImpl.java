package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.services.ProviderProcessVersionService;
import com.catalis.common.config.interfaces.dtos.ProviderProcessVersionDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderProcessVersionService interface
 */
@Service
public class ProviderProcessVersionServiceImpl implements ProviderProcessVersionService {

    @Override
    public Mono<ProviderProcessVersionDTO> getById(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<PaginationResponse<ProviderProcessVersionDTO>> filter(FilterRequest<ProviderProcessVersionDTO> filterRequest) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderProcessVersionDTO> create(ProviderProcessVersionDTO providerProcessVersionDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderProcessVersionDTO> update(Long id, ProviderProcessVersionDTO providerProcessVersionDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }
}
