package com.firefly.common.config.core.services.impl;

import com.firefly.common.config.core.mappers.ProviderProcessStatusMapper;
import com.firefly.common.config.core.services.ProviderProcessStatusService;
import com.firefly.common.config.interfaces.dtos.ProviderProcessStatusDTO;
import com.firefly.common.config.models.entities.ProviderProcessStatus;
import com.firefly.common.config.models.repositories.ProviderProcessStatusRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderProcessStatusService interface
 */
@Service
public class ProviderProcessStatusServiceImpl implements ProviderProcessStatusService {

    @Autowired
    private ProviderProcessStatusRepository repository;

    @Autowired
    private ProviderProcessStatusMapper mapper;

    @Override
    public Mono<ProviderProcessStatusDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider process status not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderProcessStatusDTO>> filter(FilterRequest<ProviderProcessStatusDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderProcessStatus.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderProcessStatusDTO> create(ProviderProcessStatusDTO providerProcessStatusDTO) {
        // Set ID to null to ensure a new entity is created
        providerProcessStatusDTO.setId(null);

        // Convert DTO to entity
        ProviderProcessStatus entity = mapper.toEntity(providerProcessStatusDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderProcessStatusDTO> update(Long id, ProviderProcessStatusDTO providerProcessStatusDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider process status not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerProcessStatusDTO.setId(id);

                    // Convert DTO to entity
                    ProviderProcessStatus updatedEntity = mapper.toEntity(providerProcessStatusDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider process status not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}
