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
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for ConfigurationAudit entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Audit trail for configuration changes")
public class ConfigurationAuditDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Audit ID")
    private UUID id;

    @FilterableId
    @Schema(description = "Tenant ID")
    private UUID tenantId;

    @NotBlank(message = "Entity type is required")
    @Schema(description = "Entity type", example = "Tenant")
    private String entityType;

    @NotNull(message = "Entity ID is required")
    @Schema(description = "Entity ID")
    private UUID entityId;

    @NotBlank(message = "Action is required")
    @Schema(description = "Action (CREATE, UPDATE, DELETE)", example = "UPDATE")
    private String action;

    @Schema(description = "Field name that was changed", example = "apiRateLimitPerHour")
    private String fieldName;

    @Schema(description = "Old value before change", example = "1000")
    private String oldValue;

    @Schema(description = "New value after change", example = "5000")
    private String newValue;

    @Schema(description = "User ID who made the change")
    private UUID changedByUserId;

    @Schema(description = "Username who made the change", example = "admin@firefly.com")
    private String changedByUsername;

    @Schema(description = "Reason for the change", example = "Increased rate limit for premium tier")
    private String changeReason;

    @Schema(description = "IP address of the user", example = "192.168.1.1")
    private String ipAddress;

    @Schema(description = "User agent", example = "Mozilla/5.0...")
    private String userAgent;

    @Schema(description = "Whether rollback is available", example = "true")
    private Boolean rollbackAvailable;

    @Schema(description = "Additional metadata (JSON)")
    private String metadata;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
}

