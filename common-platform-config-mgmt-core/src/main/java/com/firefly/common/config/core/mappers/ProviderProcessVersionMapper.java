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
