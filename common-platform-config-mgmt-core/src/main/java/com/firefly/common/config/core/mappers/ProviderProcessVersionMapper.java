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


package com.firefly.common.config.core.mappers;

import com.firefly.common.config.interfaces.dtos.ProviderProcessVersionDTO;
import com.firefly.common.config.models.entities.ProviderProcessVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderProcessVersion entity and DTO
 */
@Mapper(componentModel = "spring", uses = {ProviderProcessMapper.class, ProviderProcessStatusMapper.class})
public interface ProviderProcessVersionMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderProcessVersion entity
     * @return ProviderProcessVersionDTO
     */
    @Mapping(source = "versionNumber", target = "versionNumber")
    ProviderProcessVersionDTO toDTO(ProviderProcessVersion entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderProcessVersionDTO
     * @return ProviderProcessVersion entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "versionNumber", target = "versionNumber")
    ProviderProcessVersion toEntity(ProviderProcessVersionDTO dto);
}
