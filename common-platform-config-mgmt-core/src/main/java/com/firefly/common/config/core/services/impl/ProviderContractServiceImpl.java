package com.firefly.common.config.core.services.impl;

import com.firefly.common.config.core.mappers.ProviderContractMapper;
import com.firefly.common.config.core.services.ProviderContractService;
import com.firefly.common.config.interfaces.dtos.ProviderContractDTO;
import com.firefly.common.config.models.entities.ProviderContract;
import com.firefly.common.config.models.repositories.ProviderContractRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the ProviderContractService interface
 */
@Service
public class ProviderContractServiceImpl implements ProviderContractService {

    @Autowired
    private ProviderContractRepository repository;

    @Autowired
    private ProviderContractMapper mapper;

    @Override
    public Mono<ProviderContractDTO> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider contract not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderContractDTO>> filter(FilterRequest<ProviderContractDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderContract.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderContractDTO> create(ProviderContractDTO providerContractDTO) {
        // Set ID to null to ensure a new entity is created
        providerContractDTO.setId(null);

        // Convert DTO to entity
        ProviderContract entity = mapper.toEntity(providerContractDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderContractDTO> update(UUID id, ProviderContractDTO providerContractDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider contract not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerContractDTO.setId(id);

                    // Convert DTO to entity
                    ProviderContract updatedEntity = mapper.toEntity(providerContractDTO);

                    // Preserve created date
                    updatedEntity.setCreatedAt(existingEntity.getCreatedAt());

                    // Save updated entity
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider contract not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}