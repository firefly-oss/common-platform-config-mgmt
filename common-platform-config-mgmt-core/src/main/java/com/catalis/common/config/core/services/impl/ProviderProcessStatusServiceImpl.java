package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.services.ProviderProcessStatusService;
import com.catalis.common.config.interfaces.dtos.ProviderProcessStatusDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderProcessStatusService interface
 */
@Service
public class ProviderProcessStatusServiceImpl implements ProviderProcessStatusService {

    @Override
    public Mono<ProviderProcessStatusDTO> getById(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<PaginationResponse<ProviderProcessStatusDTO>> filter(FilterRequest<ProviderProcessStatusDTO> filterRequest) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderProcessStatusDTO> create(ProviderProcessStatusDTO providerProcessStatusDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderProcessStatusDTO> update(Long id, ProviderProcessStatusDTO providerProcessStatusDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }
}
