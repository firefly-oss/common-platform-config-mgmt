package com.firefly.common.config.interfaces.dtos;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for ProviderMapping entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider Mapping information")
public class ProviderMappingDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider Mapping ID")
    private UUID id;
    
    @NotNull(message = "Provider map type ID is required")
    @FilterableId
    @Schema(description = "ID of the provider map type")
    private UUID providerMapTypeId;
    
    @Schema(description = "Provider map type information")
    private ProviderMapTypeDTO providerMapType;
    
    @NotNull(message = "Provider ID is required")
    @FilterableId
    @Schema(description = "ID of the provider")
    private UUID providerId;
    
    @Schema(description = "Provider information")
    private ProviderDTO provider;
    
    @NotNull(message = "Internal provider ID is required")
    @FilterableId
    @Schema(description = "ID of the internal provider")
    private UUID internalProviderId;
    
    @Schema(description = "Internal provider information")
    private ProviderDTO internalProvider;
    
    @Schema(description = "Whether the provider mapping is active", defaultValue = "true")
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