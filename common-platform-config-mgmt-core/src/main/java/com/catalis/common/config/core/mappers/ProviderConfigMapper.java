package com.catalis.common.config.core.mappers;

import com.catalis.common.config.interfaces.dtos.ProviderConfigDTO;
import com.catalis.common.config.models.entities.ProviderConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderConfig entity and DTO
 */
@Mapper(componentModel = "spring", uses = {ProviderMapper.class})
public interface ProviderConfigMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderConfig entity
     * @return ProviderConfigDTO
     */
    ProviderConfigDTO toDTO(ProviderConfig entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderConfigDTO
     * @return ProviderConfig entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderConfig toEntity(ProviderConfigDTO dto);
}
