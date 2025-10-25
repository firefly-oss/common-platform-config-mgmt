/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
import java.util.UUID;

/**
 * DTO for Provider Parameter entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider parameter/configuration information")
public class ProviderParameterDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider parameter ID")
    private UUID id;
    
    @NotNull(message = "Provider ID is required")
    @FilterableId
    @Schema(description = "ID of the provider")
    private UUID providerId;
    
    @FilterableId
    @Schema(description = "ID of the tenant (null for provider-level parameters)")
    private UUID tenantId;
    
    @NotBlank(message = "Parameter name is required")
    @Size(min = 1, max = 100, message = "Parameter name must be between 1 and 100 characters")
    @Schema(description = "Name of the parameter", example = "api_key")
    private String parameterName;
    
    @Schema(description = "Value of the parameter", example = "sk_test_123456")
    private String parameterValue;
    
    @Schema(description = "Type of the parameter", example = "STRING")
    private String parameterType;
    
    @Schema(description = "Description of the parameter", example = "API key for authentication")
    private String description;
    
    @Schema(description = "Whether the parameter is secret", defaultValue = "false")
    private Boolean isSecret;
    
    @Schema(description = "Whether the parameter is required", defaultValue = "false")
    private Boolean isRequired;
    
    @Schema(description = "Whether the parameter is editable", defaultValue = "true")
    private Boolean isEditable;
    
    @Schema(description = "Validation regex for the parameter value", example = "^sk_[a-zA-Z0-9]+$")
    private String validationRegex;
    
    @Schema(description = "Default value for the parameter")
    private String defaultValue;
    
    @Schema(description = "Environment for the parameter", example = "production")
    private String environment;
    
    @Schema(description = "Category of the parameter", example = "authentication")
    private String category;
    
    @Schema(description = "Display order for UI", example = "1")
    private Integer displayOrder;
    
    @Schema(description = "Additional metadata in JSON format", example = "{\"ui_hint\":\"password\"}")
    private String metadata;
    
    @Schema(description = "Whether the parameter is active", defaultValue = "true")
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

