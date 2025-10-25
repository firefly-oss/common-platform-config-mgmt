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

import com.firefly.annotations.ValidCurrencyCode;
import com.firefly.annotations.ValidPhoneNumber;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Provider entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider information")
public class ProviderDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider ID")
    private UUID id;

    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 50, message = "Code must be between 2 and 50 characters")
    @Schema(description = "Unique code for the provider", example = "TREEZOR")
    private String code;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Name of the provider", example = "Treezor")
    private String name;

    @Schema(description = "Description of the provider", example = "Treezor is a Banking as a Service provider")
    private String description;

    @NotNull(message = "Provider type ID is required")
    @FilterableId
    @Schema(description = "ID of the provider type")
    private UUID providerTypeId;

    @NotNull(message = "Provider status ID is required")
    @FilterableId
    @Schema(description = "ID of the provider status")
    private UUID providerStatusId;

    @Schema(description = "Base URL for the provider's API", example = "https://api.treezor.com")
    private String baseUrl;

    @Schema(description = "API version", example = "v1")
    private String apiVersion;

    @Schema(description = "URL for the provider's documentation", example = "https://docs.treezor.com")
    private String documentationUrl;

    @Email(message = "Support email must be valid")
    @Schema(description = "Support email", example = "support@treezor.com")
    private String supportEmail;

    @Schema(description = "Support phone number", example = "+33123456789")
    private String supportPhone;

    @Schema(description = "Additional metadata in JSON format")
    private String metadata;

    @Schema(description = "Whether the provider is active", defaultValue = "true")
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
