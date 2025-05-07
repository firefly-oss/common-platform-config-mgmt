package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.services.ProviderProcessService;
import com.catalis.common.config.interfaces.dtos.ProviderProcessDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderProcessService interface
 */
@Service
public class ProviderProcessServiceImpl implements ProviderProcessService {

    @Override
    public Mono<ProviderProcessDTO> getById(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<PaginationResponse<ProviderProcessDTO>> filter(FilterRequest<ProviderProcessDTO> filterRequest) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderProcessDTO> create(ProviderProcessDTO providerProcessDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderProcessDTO> update(Long id, ProviderProcessDTO providerProcessDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }
}
