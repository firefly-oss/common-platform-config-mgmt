package com.catalis.common.config.core.mappers;

import com.catalis.common.config.interfaces.dtos.ProviderProcessDTO;
import com.catalis.common.config.models.entities.ProviderProcess;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderProcess entity and DTO
 */
@Mapper(componentModel = "spring", uses = {ProviderMapper.class})
public interface ProviderProcessMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderProcess entity
     * @return ProviderProcessDTO
     */
    ProviderProcessDTO toDTO(ProviderProcess entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderProcessDTO
     * @return ProviderProcess entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderProcess toEntity(ProviderProcessDTO dto);
}
