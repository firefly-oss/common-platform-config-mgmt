package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.mappers.ProviderTypeMapper;
import com.catalis.common.config.core.services.ProviderTypeService;
import com.catalis.common.config.interfaces.dtos.ProviderTypeDTO;
import com.catalis.common.config.models.entities.ProviderType;
import com.catalis.common.config.models.repositories.ProviderTypeRepository;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderTypeService interface
 */
@Service
public class ProviderTypeServiceImpl implements ProviderTypeService {

    @Autowired
    private ProviderTypeRepository repository;

    @Autowired
    private ProviderTypeMapper mapper;

    @Override
    public Mono<ProviderTypeDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider type not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderTypeDTO>> filter(FilterRequest<ProviderTypeDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderType.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderTypeDTO> create(ProviderTypeDTO providerTypeDTO) {
        // Set ID to null to ensure a new entity is created
        providerTypeDTO.setId(null);

        // Convert DTO to entity
        ProviderType entity = mapper.toEntity(providerTypeDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderTypeDTO> update(Long id, ProviderTypeDTO providerTypeDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider type not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerTypeDTO.setId(id);

                    // Convert DTO to entity
                    ProviderType updatedEntity = mapper.toEntity(providerTypeDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider type not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}
