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

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private UUID id;
    
    @NotNull(message = "Internal contract ID is required")
    @Schema(description = "ID of the internal contract", example = "12345")
    private UUID internalContractId;
    
    @NotNull(message = "Provider contract ID is required")
    @FilterableId
    @Schema(description = "ID of the provider contract")
    private UUID providerContractId;
    
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