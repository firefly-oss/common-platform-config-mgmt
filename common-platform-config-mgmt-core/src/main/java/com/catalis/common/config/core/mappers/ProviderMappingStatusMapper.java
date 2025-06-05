package com.catalis.common.config.core.mappers;

import com.catalis.common.config.interfaces.dtos.ProviderMappingStatusDTO;
import com.catalis.common.config.models.entities.ProviderMappingStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderMappingStatus entity and DTO
 */
@Mapper(componentModel = "spring")
public interface ProviderMappingStatusMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderMappingStatus entity
     * @return ProviderMappingStatusDTO
     */
    ProviderMappingStatusDTO toDTO(ProviderMappingStatus entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderMappingStatusDTO
     * @return ProviderMappingStatus entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderMappingStatus toEntity(ProviderMappingStatusDTO dto);
}