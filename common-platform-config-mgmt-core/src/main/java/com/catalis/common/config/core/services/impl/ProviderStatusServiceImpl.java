package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.mappers.ProviderStatusMapper;
import com.catalis.common.config.core.services.ProviderStatusService;
import com.catalis.common.config.interfaces.dtos.ProviderStatusDTO;
import com.catalis.common.config.models.entities.ProviderStatus;
import com.catalis.common.config.models.repositories.ProviderStatusRepository;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderStatusService interface
 */
@Service
public class ProviderStatusServiceImpl implements ProviderStatusService {

    @Autowired
    private ProviderStatusRepository repository;

    @Autowired
    private ProviderStatusMapper mapper;

    @Override
    public Mono<ProviderStatusDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider status not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderStatusDTO>> filter(FilterRequest<ProviderStatusDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderStatus.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderStatusDTO> create(ProviderStatusDTO providerStatusDTO) {
        // Set ID to null to ensure a new entity is created
        providerStatusDTO.setId(null);

        // Convert DTO to entity
        ProviderStatus entity = mapper.toEntity(providerStatusDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderStatusDTO> update(Long id, ProviderStatusDTO providerStatusDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider status not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerStatusDTO.setId(id);

                    // Convert DTO to entity
                    ProviderStatus updatedEntity = mapper.toEntity(providerStatusDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider status not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}
