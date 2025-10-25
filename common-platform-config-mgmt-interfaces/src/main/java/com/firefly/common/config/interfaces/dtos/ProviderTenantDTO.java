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
import com.firefly.core.utils.annotations.ValidDate;
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
 * DTO for Provider-Tenant relationship entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider-Tenant relationship information")
public class ProviderTenantDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider-Tenant relationship ID")
    private UUID id;
    
    @NotNull(message = "Provider ID is required")
    @FilterableId
    @Schema(description = "ID of the provider")
    private UUID providerId;
    
    @Schema(description = "Provider information")
    private ProviderDTO provider;
    
    @NotNull(message = "Tenant ID is required")
    @FilterableId
    @Schema(description = "ID of the tenant")
    private UUID tenantId;
    
    @Schema(description = "Tenant information")
    private TenantDTO tenant;
    
    @Schema(description = "Whether this is the primary provider for the tenant", defaultValue = "false")
    private Boolean isPrimary;
    
    @Schema(description = "Priority of this provider for the tenant (higher number means higher priority)", example = "10", defaultValue = "0")
    private Integer priority;
    
    @Schema(description = "Configuration override in JSON format for this specific tenant-provider relationship", example = "{\"api_key\":\"custom_key\"}")
    private String configurationOverride;
    
    @Schema(description = "Whether this provider is enabled for the tenant", defaultValue = "true")
    private Boolean enabled;
    
    @ValidDate
    @Schema(description = "Start date of the provider-tenant relationship")
    private LocalDateTime startDate;
    
    @ValidDate
    @Schema(description = "End date of the provider-tenant relationship")
    private LocalDateTime endDate;
    
    @Schema(description = "Notes about this provider-tenant relationship", example = "Special configuration for US market")
    private String notes;
    
    @Schema(description = "Additional metadata in JSON format", example = "{\"custom_field\":\"value\"}")
    private String metadata;
    
    @Schema(description = "Whether the relationship is active", defaultValue = "true")
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

