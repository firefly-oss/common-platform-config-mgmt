package com.firefly.common.config.core.services.impl;

import com.firefly.common.config.core.mappers.ProviderMappingStatusMapper;
import com.firefly.common.config.core.services.ProviderMappingStatusService;
import com.firefly.common.config.interfaces.dtos.ProviderMappingStatusDTO;
import com.firefly.common.config.models.entities.ProviderMappingStatus;
import com.firefly.common.config.models.repositories.ProviderMappingStatusRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderMappingStatusService interface
 */
@Service
public class ProviderMappingStatusServiceImpl implements ProviderMappingStatusService {

    @Autowired
    private ProviderMappingStatusRepository repository;

    @Autowired
    private ProviderMappingStatusMapper mapper;

    @Override
    public Mono<ProviderMappingStatusDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping status not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderMappingStatusDTO>> filter(FilterRequest<ProviderMappingStatusDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderMappingStatus.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderMappingStatusDTO> create(ProviderMappingStatusDTO providerMappingStatusDTO) {
        // Set ID to null to ensure a new entity is created
        providerMappingStatusDTO.setId(null);

        // Convert DTO to entity
        ProviderMappingStatus entity = mapper.toEntity(providerMappingStatusDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderMappingStatusDTO> update(Long id, ProviderMappingStatusDTO providerMappingStatusDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping status not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerMappingStatusDTO.setId(id);

                    // Convert DTO to entity
                    ProviderMappingStatus updatedEntity = mapper.toEntity(providerMappingStatusDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping status not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}