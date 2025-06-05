package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.mappers.ProviderMappingMapper;
import com.catalis.common.config.core.services.ProviderMappingService;
import com.catalis.common.config.interfaces.dtos.ProviderMappingDTO;
import com.catalis.common.config.models.entities.ProviderMapping;
import com.catalis.common.config.models.repositories.ProviderMappingRepository;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderMappingService interface
 */
@Service
public class ProviderMappingServiceImpl implements ProviderMappingService {

    @Autowired
    private ProviderMappingRepository repository;

    @Autowired
    private ProviderMappingMapper mapper;

    @Override
    public Mono<ProviderMappingDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderMappingDTO>> filter(FilterRequest<ProviderMappingDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderMapping.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderMappingDTO> create(ProviderMappingDTO providerMappingDTO) {
        // Set ID to null to ensure a new entity is created
        providerMappingDTO.setId(null);

        // Convert DTO to entity
        ProviderMapping entity = mapper.toEntity(providerMappingDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderMappingDTO> update(Long id, ProviderMappingDTO providerMappingDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerMappingDTO.setId(id);

                    // Convert DTO to entity
                    ProviderMapping updatedEntity = mapper.toEntity(providerMappingDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}