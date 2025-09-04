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

import com.firefly.common.config.core.mappers.ProviderMappingMapper;
import com.firefly.common.config.core.services.ProviderMappingService;
import com.firefly.common.config.interfaces.dtos.ProviderMappingDTO;
import com.firefly.common.config.models.entities.ProviderMapping;
import com.firefly.common.config.models.repositories.ProviderMappingRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

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
    public Mono<ProviderMappingDTO> getById(UUID id) {
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
    public Mono<ProviderMappingDTO> update(UUID id, ProviderMappingDTO providerMappingDTO) {
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
    public Mono<Void> delete(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider mapping not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}