package com.catalis.common.config.core.services.impl;

import com.catalis.common.config.core.mappers.ProviderContractMappingMapper;
import com.catalis.common.config.core.services.ProviderContractMappingService;
import com.catalis.common.config.interfaces.dtos.ProviderContractMappingDTO;
import com.catalis.common.config.models.entities.ProviderContractMapping;
import com.catalis.common.config.models.repositories.ProviderContractMappingRepository;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderContractMappingService interface
 */
@Service
public class ProviderContractMappingServiceImpl implements ProviderContractMappingService {

    @Autowired
    private ProviderContractMappingRepository repository;

    @Autowired
    private ProviderContractMappingMapper mapper;

    @Override
    public Mono<ProviderContractMappingDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider contract mapping not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderContractMappingDTO>> filter(FilterRequest<ProviderContractMappingDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderContractMapping.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderContractMappingDTO> create(ProviderContractMappingDTO providerContractMappingDTO) {
        // Set ID to null to ensure a new entity is created
        providerContractMappingDTO.setId(null);

        // Convert DTO to entity
        ProviderContractMapping entity = mapper.toEntity(providerContractMappingDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderContractMappingDTO> update(Long id, ProviderContractMappingDTO providerContractMappingDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider contract mapping not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerContractMappingDTO.setId(id);

                    // Convert DTO to entity
                    ProviderContractMapping updatedEntity = mapper.toEntity(providerContractMappingDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider contract mapping not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}