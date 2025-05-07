package com.catalis.common.config.core.mappers;

import com.catalis.common.config.interfaces.dtos.ProviderProcessStatusDTO;
import com.catalis.common.config.models.entities.ProviderProcessStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderProcessStatus entity and DTO
 */
@Mapper(componentModel = "spring")
public interface ProviderProcessStatusMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderProcessStatus entity
     * @return ProviderProcessStatusDTO
     */
    ProviderProcessStatusDTO toDTO(ProviderProcessStatus entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderProcessStatusDTO
     * @return ProviderProcessStatus entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderProcessStatus toEntity(ProviderProcessStatusDTO dto);
}
