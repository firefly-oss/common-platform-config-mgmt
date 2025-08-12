package com.catalis.common.config.interfaces.dtos;

import com.catalis.annotations.ValidDate;
import com.catalis.core.utils.annotations.FilterableId;
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
 * DTO for Provider Config entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider configuration information")
public class ProviderConfigDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider config ID")
    private Long id;

    @NotNull(message = "Provider ID is required")
    @FilterableId
    @Schema(description = "ID of the provider")
    private Long providerId;

    @Schema(description = "Provider information")
    private ProviderDTO provider;

    @Schema(description = "Group for organizing configurations", example = "API_CREDENTIALS")
    private String configGroup;

    @NotBlank(message = "Key is required")
    @Size(min = 1, max = 100, message = "Key must be between 1 and 100 characters")
    @Schema(description = "Configuration key", example = "API_KEY")
    private String key;

    @NotBlank(message = "Value is required")
    @Schema(description = "Configuration value", example = "abcdef123456")
    private String value;

    @Schema(description = "Type of the value", example = "string", defaultValue = "string")
    private String valueType;

    @Schema(description = "Description of the configuration", example = "API key for authentication")
    private String description;

    @Schema(description = "Whether the value is a secret that should be masked", example = "true", defaultValue = "false")
    private Boolean isSecret;

    @Schema(description = "Whether the configuration is required", example = "true", defaultValue = "false")
    private Boolean isRequired;

    @Schema(description = "Whether the configuration can be edited", example = "true", defaultValue = "true")
    private Boolean isEditable;

    @Schema(description = "Regular expression for validating the value", example = "^[a-zA-Z0-9]{10,}$")
    private String validationRegex;

    @Schema(description = "Default value for the configuration", example = "default_value")
    private String defaultValue;

    @Schema(description = "Environment for the configuration", example = "production", defaultValue = "default")
    private String environment;

    @Schema(description = "Expiration date for the configuration")

    @ValidDate
    private LocalDateTime expirationDate;

    @Schema(description = "Tags for categorizing the configuration", example = "auth,security")
    private String tags;

    @Schema(description = "Additional metadata for the configuration", example = "{\"source\":\"manual\",\"version\":\"1.0\"}")
    private String metadata;

    @Schema(description = "Order index for displaying configurations", example = "10", defaultValue = "0")
    private Integer orderIndex;

    @Schema(description = "Whether the configuration is active", defaultValue = "true")
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
