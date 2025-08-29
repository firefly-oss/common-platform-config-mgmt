package com.firefly.common.config.core.mappers;

import com.firefly.common.config.interfaces.dtos.ProviderTypeDTO;
import com.firefly.common.config.models.entities.ProviderType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderType entity and DTO
 */
@Mapper(componentModel = "spring")
public interface ProviderTypeMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderType entity
     * @return ProviderTypeDTO
     */
    ProviderTypeDTO toDTO(ProviderType entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderTypeDTO
     * @return ProviderType entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderType toEntity(ProviderTypeDTO dto);
}
