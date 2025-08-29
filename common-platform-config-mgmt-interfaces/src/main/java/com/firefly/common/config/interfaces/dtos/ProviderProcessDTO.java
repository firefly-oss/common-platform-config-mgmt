package com.firefly.common.config.interfaces.dtos;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO for Provider Process entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider Process information")
public class ProviderProcessDTO {
    
    @Schema(description = "Provider process ID")
    private Long id;
    
    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 50, message = "Code must be between 2 and 50 characters")
    @Schema(description = "Unique code for the process", example = "PAYMENT_PROCESS")
    private String code;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Name of the process", example = "Payment Processing")
    private String name;
    
    @Schema(description = "Description of the process", example = "Process for handling payment transactions")
    private String description;
    
    @NotNull(message = "Provider ID is required")
    @FilterableId
    @Schema(description = "ID of the provider")
    private Long providerId;
    
    @Schema(description = "Provider information")
    private ProviderDTO provider;
    
    @NotBlank(message = "Process type is required")
    @Schema(description = "Type of the process", example = "PAYMENT")
    private String processType;
    
    @Schema(description = "Category of the process", example = "TRANSACTION")
    private String processCategory;
    
    @Schema(description = "Whether the process is common across providers", example = "false", defaultValue = "false")
    private Boolean isCommon;
    
    @Schema(description = "Priority of the process (higher number means higher priority)", example = "10", defaultValue = "0")
    private Integer priority;
    
    @Schema(description = "Estimated duration of the process in seconds", example = "60")
    private Integer estimatedDurationSeconds;
    
    @Schema(description = "Tags for categorizing the process", example = "payment,transaction")
    private String tags;
    
    @Schema(description = "Additional metadata for the process", example = "{\"version\":\"1.0\",\"author\":\"John Doe\"}")
    private String metadata;
    
    @Schema(description = "Whether the process is active", defaultValue = "true")
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
