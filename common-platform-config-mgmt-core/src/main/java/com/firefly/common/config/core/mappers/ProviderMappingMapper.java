package com.firefly.common.config.core.mappers;

import com.firefly.common.config.interfaces.dtos.ProviderMappingDTO;
import com.firefly.common.config.models.entities.ProviderMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderMapping entity and DTO
 */
@Mapper(componentModel = "spring", uses = {ProviderMapTypeMapper.class, ProviderMapper.class})
public interface ProviderMappingMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderMapping entity
     * @return ProviderMappingDTO
     */
    ProviderMappingDTO toDTO(ProviderMapping entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderMappingDTO
     * @return ProviderMapping entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderMapping toEntity(ProviderMappingDTO dto);
}