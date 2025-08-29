package com.firefly.common.config.core.services.impl;

import com.firefly.common.config.core.mappers.ProviderProcessVersionMapper;
import com.firefly.common.config.core.services.ProviderProcessVersionService;
import com.firefly.common.config.interfaces.dtos.ProviderProcessVersionDTO;
import com.firefly.common.config.models.entities.ProviderProcessVersion;
import com.firefly.common.config.models.repositories.ProviderProcessVersionRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderProcessVersionService interface
 */
@Service
public class ProviderProcessVersionServiceImpl implements ProviderProcessVersionService {

    @Autowired
    private ProviderProcessVersionRepository repository;

    @Autowired
    private ProviderProcessVersionMapper mapper;

    @Override
    public Mono<ProviderProcessVersionDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider process version not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderProcessVersionDTO>> filter(FilterRequest<ProviderProcessVersionDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderProcessVersion.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderProcessVersionDTO> create(ProviderProcessVersionDTO providerProcessVersionDTO) {
        // Set ID to null to ensure a new entity is created
        providerProcessVersionDTO.setId(null);

        // Convert DTO to entity
        ProviderProcessVersion entity = mapper.toEntity(providerProcessVersionDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderProcessVersionDTO> update(Long id, ProviderProcessVersionDTO providerProcessVersionDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider process version not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerProcessVersionDTO.setId(id);

                    // Convert DTO to entity
                    ProviderProcessVersion updatedEntity = mapper.toEntity(providerProcessVersionDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider process version not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}
