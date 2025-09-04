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
 * DTO for ProviderMapType entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider Map Type information")
public class ProviderMapTypeDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider Map Type ID")
    private UUID id;

    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 50, message = "Code must be between 2 and 50 characters")
    @Schema(description = "Unique code for the provider map type", example = "INTERNAL_EXTERNAL")
    private String code;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Name of the provider map type", example = "Internal to External Mapping")
    private String name;

    @Schema(description = "Description of the provider map type", example = "Maps internal providers to external providers")
    private String description;

    @NotNull(message = "Provider mapping status ID is required")
    @FilterableId
    @Schema(description = "ID of the provider mapping status")
    private UUID providerMappingStatusId;

    @Schema(description = "Provider mapping status information")
    private ProviderMappingStatusDTO providerMappingStatus;

    @Schema(description = "Whether the provider map type is active", defaultValue = "true")
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
