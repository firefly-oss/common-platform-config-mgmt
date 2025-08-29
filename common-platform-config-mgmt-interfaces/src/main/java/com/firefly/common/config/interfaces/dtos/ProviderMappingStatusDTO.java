package com.firefly.common.config.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO for ProviderMappingStatus entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider Mapping Status information")
public class ProviderMappingStatusDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider Mapping Status ID")
    private Long id;
    
    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 50, message = "Code must be between 2 and 50 characters")
    @Schema(description = "Unique code for the provider mapping status", example = "ACTIVE")
    private String code;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Name of the provider mapping status", example = "Active")
    private String name;
    
    @Schema(description = "Description of the provider mapping status", example = "Provider mapping status is active and operational")
    private String description;
    
    @Schema(description = "Whether the provider mapping status is active", defaultValue = "true")
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