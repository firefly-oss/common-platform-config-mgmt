package com.catalis.common.config.core.mappers;

import com.catalis.common.config.interfaces.dtos.ProviderStatusDTO;
import com.catalis.common.config.models.entities.ProviderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderStatus entity and DTO
 */
@Mapper(componentModel = "spring")
public interface ProviderStatusMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderStatus entity
     * @return ProviderStatusDTO
     */
    ProviderStatusDTO toDTO(ProviderStatus entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderStatusDTO
     * @return ProviderStatus entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderStatus toEntity(ProviderStatusDTO dto);
}
