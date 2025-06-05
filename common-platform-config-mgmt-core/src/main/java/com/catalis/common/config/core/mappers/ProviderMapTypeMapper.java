package com.catalis.common.config.core.mappers;

import com.catalis.common.config.interfaces.dtos.ProviderMapTypeDTO;
import com.catalis.common.config.models.entities.ProviderMapType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderMapType entity and DTO
 */
@Mapper(componentModel = "spring", uses = {ProviderMappingStatusMapper.class})
public interface ProviderMapTypeMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderMapType entity
     * @return ProviderMapTypeDTO
     */
    ProviderMapTypeDTO toDTO(ProviderMapType entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderMapTypeDTO
     * @return ProviderMapType entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderMapType toEntity(ProviderMapTypeDTO dto);
}
