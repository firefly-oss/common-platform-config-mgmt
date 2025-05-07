package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.services.ProviderConfigService;
import com.catalis.common.config.interfaces.dtos.ProviderConfigDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderConfigService interface
 */
@Service
public class ProviderConfigServiceImpl implements ProviderConfigService {

    @Override
    public Mono<ProviderConfigDTO> getById(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<PaginationResponse<ProviderConfigDTO>> filter(FilterRequest<ProviderConfigDTO> filterRequest) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderConfigDTO> create(ProviderConfigDTO providerConfigDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<ProviderConfigDTO> update(Long id, ProviderConfigDTO providerConfigDTO) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new UnsupportedOperationException("Not implemented yet"));
    }
}
