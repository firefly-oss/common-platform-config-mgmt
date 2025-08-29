package com.firefly.common.config.core.services.impl;

import com.firefly.common.config.core.mappers.ProviderMapTypeStatusMapper;
import com.firefly.common.config.core.services.ProviderMapTypeStatusService;
import com.firefly.common.config.interfaces.dtos.ProviderMapTypeStatusDTO;
import com.firefly.common.config.models.entities.ProviderMapTypeStatus;
import com.firefly.common.config.models.repositories.ProviderMapTypeStatusRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderMapTypeStatusService interface
 */
@Service
public class ProviderMapTypeStatusServiceImpl implements ProviderMapTypeStatusService {

    @Autowired
    private ProviderMapTypeStatusRepository repository;

    @Autowired
    private ProviderMapTypeStatusMapper mapper;

    @Override
    public Mono<ProviderMapTypeStatusDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider map type status not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderMapTypeStatusDTO>> filter(FilterRequest<ProviderMapTypeStatusDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderMapTypeStatus.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderMapTypeStatusDTO> create(ProviderMapTypeStatusDTO providerMapTypeStatusDTO) {
        // Set ID to null to ensure a new entity is created
        providerMapTypeStatusDTO.setId(null);

        // Convert DTO to entity
        ProviderMapTypeStatus entity = mapper.toEntity(providerMapTypeStatusDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderMapTypeStatusDTO> update(Long id, ProviderMapTypeStatusDTO providerMapTypeStatusDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider map type status not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerMapTypeStatusDTO.setId(id);

                    // Convert DTO to entity
                    ProviderMapTypeStatus updatedEntity = mapper.toEntity(providerMapTypeStatusDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider map type status not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}