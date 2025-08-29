package com.firefly.common.config.core.services.impl;

import com.firefly.common.config.core.mappers.ProviderContractStatusMapper;
import com.firefly.common.config.core.services.ProviderContractStatusService;
import com.firefly.common.config.interfaces.dtos.ProviderContractStatusDTO;
import com.firefly.common.config.models.entities.ProviderContractStatus;
import com.firefly.common.config.models.repositories.ProviderContractStatusRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ProviderContractStatusService interface
 */
@Service
public class ProviderContractStatusServiceImpl implements ProviderContractStatusService {

    @Autowired
    private ProviderContractStatusRepository repository;

    @Autowired
    private ProviderContractStatusMapper mapper;

    @Override
    public Mono<ProviderContractStatusDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider contract status not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderContractStatusDTO>> filter(FilterRequest<ProviderContractStatusDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderContractStatus.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderContractStatusDTO> create(ProviderContractStatusDTO providerContractStatusDTO) {
        // Set ID to null to ensure a new entity is created
        providerContractStatusDTO.setId(null);

        // Convert DTO to entity
        ProviderContractStatus entity = mapper.toEntity(providerContractStatusDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderContractStatusDTO> update(Long id, ProviderContractStatusDTO providerContractStatusDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider contract status not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerContractStatusDTO.setId(id);

                    // Convert DTO to entity
                    ProviderContractStatus updatedEntity = mapper.toEntity(providerContractStatusDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider contract status not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}