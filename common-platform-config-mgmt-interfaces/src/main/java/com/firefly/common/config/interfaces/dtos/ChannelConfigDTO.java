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

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for ChannelConfig entity.
 * Contains only essential fields. Additional dynamic configuration is managed via ChannelConfigParameterDTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Channel configuration for a tenant")
public class ChannelConfigDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Channel config ID")
    private UUID id;

    @FilterableId
    @NotNull(message = "Tenant ID is required")
    @Schema(description = "Tenant ID", required = true)
    private UUID tenantId;

    @NotBlank(message = "Channel code is required")
    @Size(max = 50, message = "Channel code must not exceed 50 characters")
    @Schema(description = "Channel code (WEB_BANKING, MOBILE_BANKING, ATM, BRANCH, CALL_CENTER, API, OPEN_BANKING)", required = true, example = "WEB_BANKING")
    private String channelCode;

    @NotBlank(message = "Channel name is required")
    @Size(max = 100, message = "Channel name must not exceed 100 characters")
    @Schema(description = "Channel name", required = true, example = "Web Banking")
    private String channelName;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Channel description", example = "Primary web banking channel for desktop users")
    private String description;

    @Schema(description = "Whether the channel is enabled", example = "true")
    private Boolean enabled;

    @Min(value = 1, message = "Priority must be at least 1")
    @Schema(description = "Channel priority for failover (1 = highest)", example = "1")
    private Integer priority;

    @Schema(description = "Failover channel ID (backup channel if this one fails)")
    private UUID failoverChannelId;

    @Schema(description = "Additional metadata in JSON format")
    private String metadata;

    @Schema(description = "Whether the channel config is active", example = "true")
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

