package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.mappers.ProviderMapTypeMapper;
import com.catalis.common.config.core.services.ProviderMapTypeService;
import com.catalis.common.config.interfaces.dtos.ProviderMapTypeDTO;
import com.catalis.common.config.models.entities.ProviderMapType;
import com.catalis.common.config.models.repositories.ProviderMapTypeRepository;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderMapTypeService interface
 */
@Service
public class ProviderMapTypeServiceImpl implements ProviderMapTypeService {

    @Autowired
    private ProviderMapTypeRepository repository;

    @Autowired
    private ProviderMapTypeMapper mapper;

    @Override
    public Mono<ProviderMapTypeDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider map type not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderMapTypeDTO>> filter(FilterRequest<ProviderMapTypeDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderMapType.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderMapTypeDTO> create(ProviderMapTypeDTO providerMapTypeDTO) {
        // Set ID to null to ensure a new entity is created
        providerMapTypeDTO.setId(null);

        // Convert DTO to entity
        ProviderMapType entity = mapper.toEntity(providerMapTypeDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderMapTypeDTO> update(Long id, ProviderMapTypeDTO providerMapTypeDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider map type not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerMapTypeDTO.setId(id);

                    // Convert DTO to entity
                    ProviderMapType updatedEntity = mapper.toEntity(providerMapTypeDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider map type not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}