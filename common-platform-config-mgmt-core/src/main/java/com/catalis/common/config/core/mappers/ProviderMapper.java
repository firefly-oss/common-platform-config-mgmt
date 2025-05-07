package com.catalis.common.config.core.mappers;

import com.catalis.common.config.interfaces.dtos.ProviderDTO;
import com.catalis.common.config.models.entities.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for Provider entity and DTO
 */
@Mapper(componentModel = "spring", uses = {ProviderTypeMapper.class, ProviderStatusMapper.class})
public interface ProviderMapper {

    /**
     * Convert entity to DTO
     * @param entity Provider entity
     * @return ProviderDTO
     */
    ProviderDTO toDTO(Provider entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderDTO
     * @return Provider entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Provider toEntity(ProviderDTO dto);
}
