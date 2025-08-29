package com.firefly.common.config.core.services.impl;

import com.firefly.common.config.core.mappers.ProviderMapper;
import com.firefly.common.config.core.services.ProviderService;
import com.firefly.common.config.interfaces.dtos.ProviderDTO;
import com.firefly.common.config.models.entities.Provider;
import com.firefly.common.config.models.repositories.ProviderRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderService interface
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderRepository repository;

    @Autowired
    private ProviderMapper mapper;

    @Override
    public Mono<ProviderDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderDTO>> filter(FilterRequest<ProviderDTO> filterRequest) {
        return FilterUtils.createFilter(
                Provider.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderDTO> create(ProviderDTO providerDTO) {
        // Set ID to null to ensure a new entity is created
        providerDTO.setId(null);

        // Convert DTO to entity
        Provider entity = mapper.toEntity(providerDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderDTO> update(Long id, ProviderDTO providerDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerDTO.setId(id);

                    // Convert DTO to entity
                    Provider updatedEntity = mapper.toEntity(providerDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}
