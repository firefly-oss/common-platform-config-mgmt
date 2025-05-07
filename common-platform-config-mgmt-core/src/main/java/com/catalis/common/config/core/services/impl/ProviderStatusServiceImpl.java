package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.services.ProviderStatusService;
import com.catalis.common.config.interfaces.dtos.ProviderStatusDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderStatusService interface
 */
@Service
public class ProviderStatusServiceImpl implements ProviderStatusService {

    @Override
    public Mono<ProviderStatusDTO> getById(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<PaginationResponse<ProviderStatusDTO>> filter(FilterRequest<ProviderStatusDTO> filterRequest) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderStatusDTO> create(ProviderStatusDTO providerStatusDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderStatusDTO> update(Long id, ProviderStatusDTO providerStatusDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }
}
