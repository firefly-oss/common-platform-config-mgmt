package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.mappers.ProviderConfigMapper;
import com.catalis.common.config.core.services.ProviderConfigService;
import com.catalis.common.config.interfaces.dtos.ProviderConfigDTO;
import com.catalis.common.config.models.entities.ProviderConfig;
import com.catalis.common.config.models.repositories.ProviderConfigRepository;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderConfigService interface
 */
@Service
public class ProviderConfigServiceImpl implements ProviderConfigService {

    @Autowired
    private ProviderConfigRepository repository;

    @Autowired
    private ProviderConfigMapper mapper;

    @Override
    public Mono<ProviderConfigDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider config not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderConfigDTO>> filter(FilterRequest<ProviderConfigDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderConfig.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderConfigDTO> create(ProviderConfigDTO providerConfigDTO) {
        // Set ID to null to ensure a new entity is created
        providerConfigDTO.setId(null);

        // Convert DTO to entity
        ProviderConfig entity = mapper.toEntity(providerConfigDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderConfigDTO> update(Long id, ProviderConfigDTO providerConfigDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider config not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerConfigDTO.setId(id);

                    // Convert DTO to entity
                    ProviderConfig updatedEntity = mapper.toEntity(providerConfigDTO);

                    // Preserve created date
                    updatedEntity.setCreatedAt(existingEntity.getCreatedAt());

                    // Save updated entity
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider config not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}
