/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.common.config.core.services.impl;

import com.firefly.common.config.core.mappers.ProviderMappingStatusMapper;
import com.firefly.common.config.core.services.ProviderMappingStatusService;
import com.firefly.common.config.interfaces.dtos.ProviderMappingStatusDTO;
import com.firefly.common.config.models.entities.ProviderMappingStatus;
import com.firefly.common.config.models.repositories.ProviderMappingStatusRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the ProviderMappingStatusService interface
 */
@Service
public class ProviderMappingStatusServiceImpl implements ProviderMappingStatusService {

    @Autowired
    private ProviderMappingStatusRepository repository;

    @Autowired
    private ProviderMappingStatusMapper mapper;

    @Override
    public Mono<ProviderMappingStatusDTO> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping status not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderMappingStatusDTO>> filter(FilterRequest<ProviderMappingStatusDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderMappingStatus.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderMappingStatusDTO> create(ProviderMappingStatusDTO providerMappingStatusDTO) {
        // Set ID to null to ensure a new entity is created
        providerMappingStatusDTO.setId(null);

        // Convert DTO to entity
        ProviderMappingStatus entity = mapper.toEntity(providerMappingStatusDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderMappingStatusDTO> update(UUID id, ProviderMappingStatusDTO providerMappingStatusDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping status not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerMappingStatusDTO.setId(id);

                    // Convert DTO to entity
                    ProviderMappingStatus updatedEntity = mapper.toEntity(providerMappingStatusDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping status not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}