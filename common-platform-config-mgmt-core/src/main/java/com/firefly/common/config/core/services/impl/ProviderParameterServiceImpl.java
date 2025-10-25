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

import com.firefly.common.config.core.mappers.ProviderParameterMapper;
import com.firefly.common.config.core.services.ProviderParameterService;
import com.firefly.common.config.interfaces.dtos.ProviderParameterDTO;
import com.firefly.common.config.models.entities.ProviderParameter;
import com.firefly.common.config.models.repositories.ProviderParameterRepository;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
public class ProviderParameterServiceImpl implements ProviderParameterService {

    @Autowired
    private ProviderParameterRepository repository;

    @Autowired
    private ProviderParameterMapper mapper;

    @Override
    public Mono<ProviderParameterDTO> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider parameter not found with id: " + id)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaginationResponse<ProviderParameterDTO>> filter(FilterRequest<ProviderParameterDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProviderParameter.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProviderParameterDTO> create(ProviderParameterDTO providerParameterDTO) {
        providerParameterDTO.setId(null);
        ProviderParameter entity = mapper.toEntity(providerParameterDTO);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProviderParameterDTO> update(UUID id, ProviderParameterDTO providerParameterDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider parameter not found with id: " + id)))
                .flatMap(existingEntity -> {
                    providerParameterDTO.setId(id);
                    ProviderParameter updatedEntity = mapper.toEntity(providerParameterDTO);
                    updatedEntity.setCreatedAt(existingEntity.getCreatedAt());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Provider parameter not found with id: " + id)))
                .flatMap(entity -> repository.delete(entity));
    }
}

