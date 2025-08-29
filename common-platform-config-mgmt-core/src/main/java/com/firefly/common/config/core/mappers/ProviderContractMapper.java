package com.firefly.common.config.core.mappers;

import com.firefly.common.config.interfaces.dtos.ProviderContractDTO;
import com.firefly.common.config.models.entities.ProviderContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderContract entity and DTO
 */
@Mapper(componentModel = "spring", uses = {ProviderMapper.class, ProviderContractStatusMapper.class})
public interface ProviderContractMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderContract entity
     * @return ProviderContractDTO
     */
    ProviderContractDTO toDTO(ProviderContract entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderContractDTO
     * @return ProviderContract entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderContract toEntity(ProviderContractDTO dto);
}