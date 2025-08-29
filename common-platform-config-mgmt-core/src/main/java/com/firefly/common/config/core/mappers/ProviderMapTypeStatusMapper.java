package com.firefly.common.config.core.mappers;

import com.firefly.common.config.interfaces.dtos.ProviderMapTypeStatusDTO;
import com.firefly.common.config.models.entities.ProviderMapTypeStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderMapTypeStatus entity and DTO
 */
@Mapper(componentModel = "spring")
public interface ProviderMapTypeStatusMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderMapTypeStatus entity
     * @return ProviderMapTypeStatusDTO
     */
    ProviderMapTypeStatusDTO toDTO(ProviderMapTypeStatus entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderMapTypeStatusDTO
     * @return ProviderMapTypeStatus entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderMapTypeStatus toEntity(ProviderMapTypeStatusDTO dto);
}
