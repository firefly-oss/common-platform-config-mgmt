package com.catalis.common.config.interfaces.dtos;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO for Provider Contract Mapping entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider Contract Mapping information")
public class ProviderContractMappingDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider contract mapping ID")
    private Long id;
    
    @NotNull(message = "Internal contract ID is required")
    @Schema(description = "ID of the internal contract", example = "12345")
    private Long internalContractId;
    
    @NotNull(message = "Provider contract ID is required")
    @FilterableId
    @Schema(description = "ID of the provider contract")
    private Long providerContractId;
    
    @Schema(description = "Provider contract information")
    private ProviderContractDTO providerContract;
    
    @Schema(description = "Whether the provider contract mapping is active", defaultValue = "true")
    private Boolean active;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Version for optimistic locking")
    private Long version;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}