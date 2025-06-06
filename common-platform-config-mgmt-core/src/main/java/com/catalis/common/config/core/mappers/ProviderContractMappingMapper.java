package com.catalis.common.config.core.mappers;

import com.catalis.common.config.interfaces.dtos.ProviderContractMappingDTO;
import com.catalis.common.config.models.entities.ProviderContractMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for ProviderContractMapping entity and DTO
 */
@Mapper(componentModel = "spring", uses = {ProviderContractMapper.class})
public interface ProviderContractMappingMapper {

    /**
     * Convert entity to DTO
     * @param entity ProviderContractMapping entity
     * @return ProviderContractMappingDTO
     */
    ProviderContractMappingDTO toDTO(ProviderContractMapping entity);

    /**
     * Convert DTO to entity
     * @param dto ProviderContractMappingDTO
     * @return ProviderContractMapping entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProviderContractMapping toEntity(ProviderContractMappingDTO dto);
}