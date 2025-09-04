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

import com.firefly.common.config.core.mappers.ProviderProcessMapper;
import com.firefly.common.config.core.services.ProviderProcessService;
import com.firefly.common.config.interfaces.dtos.ProviderProcessDTO;
import com.firefly.common.config.models.entities.ProviderProcess;
import com.firefly.common.config.models.repositories.ProviderProcessRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the ProviderProcessService interface
 */
@Service
public class ProviderProcessServiceImpl implements ProviderProcessService {

    @Autowired
    private ProviderProcessRepository repository;

    @Autowired
    private ProviderProcessMapper mapper;

    @Override
    public Mono<ProviderProcessDTO> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider process not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderProcessDTO>> filter(FilterRequest<ProviderProcessDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderProcess.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderProcessDTO> create(ProviderProcessDTO providerProcessDTO) {
        // Set ID to null to ensure a new entity is created
        providerProcessDTO.setId(null);

        // Convert DTO to entity
        ProviderProcess entity = mapper.toEntity(providerProcessDTO);

        // Save entity and return mapped DTO
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderProcessDTO> update(UUID id, ProviderProcessDTO providerProcessDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider process not found with id: " + id)))
                .flatMap(existingEntity -> {
                    // Set the ID from the path parameter
                    providerProcessDTO.setId(id);

                    // Convert DTO to entity
                    ProviderProcess updatedEntity = mapper.toEntity(providerProcessDTO);

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
                .switchIfEmpty(Mono.error(new RuntimeException("Provider process not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}
