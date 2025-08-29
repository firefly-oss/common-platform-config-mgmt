package com.firefly.common.config.core.mappers;

import com.firefly.common.config.interfaces.dtos.ProviderContractStatusDTO;
import com.firefly.common.config.models.entities.ProviderContractStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderContractStatus entity and DTO
 */
@Mapper(componentModel = "spring")
public interface ProviderContractStatusMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderContractStatus entity
     * @return ProviderContractStatusDTO
     */
    ProviderContractStatusDTO toDTO(ProviderContractStatus entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderContractStatusDTO
     * @return ProviderContractStatus entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderContractStatus toEntity(ProviderContractStatusDTO dto);
}