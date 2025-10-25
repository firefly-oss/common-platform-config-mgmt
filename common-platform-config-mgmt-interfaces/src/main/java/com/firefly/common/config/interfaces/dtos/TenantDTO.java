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

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Tenant entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Tenant information for Firefly core banking system")
public class TenantDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Tenant ID")
    private UUID id;
    
    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 50, message = "Code must be between 2 and 50 characters")
    @Schema(description = "Unique code for the tenant", example = "TENANT_US_001")
    private String code;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Name of the tenant", example = "Acme Bank USA")
    private String name;
    
    @Schema(description = "Description of the tenant", example = "Main US operations for Acme Bank")
    private String description;
    
    @NotNull(message = "Tenant status ID is required")
    @FilterableId
    @Schema(description = "ID of the tenant status")
    private UUID tenantStatusId;

    @FilterableId
    @Schema(description = "Country ID (UUID reference to country entity)")
    private UUID countryId;

    @Schema(description = "Timezone of the tenant", example = "America/New_York")
    private String timezone;

    @Size(max = 3, message = "Currency code must be 3 characters")
    @Schema(description = "Default currency code (ISO 4217)", example = "USD")
    private String defaultCurrencyCode;

    @Size(max = 10, message = "Language code must not exceed 10 characters")
    @Schema(description = "Default language code (ISO 639-1)", example = "en-US")
    private String defaultLanguageCode;

    @Schema(description = "Business contact name", example = "John Doe")
    private String businessContactName;

    @Email(message = "Business contact email must be valid")
    @Schema(description = "Business contact email", example = "business@acmebank.com")
    private String businessContactEmail;
    
    @Schema(description = "Business contact phone", example = "+1-555-0100")
    private String businessContactPhone;

    @Schema(description = "Technical contact name", example = "Jane Smith")
    private String technicalContactName;

    @Email(message = "Technical contact email must be valid")
    @Schema(description = "Technical contact email", example = "tech@acmebank.com")
    private String technicalContactEmail;

    @Schema(description = "Technical contact phone", example = "+1-555-0101")
    private String technicalContactPhone;

    @Schema(description = "Subscription tier", example = "ENTERPRISE")
    private String subscriptionTier;

    @Schema(description = "Subscription start date")
    private LocalDateTime subscriptionStartDate;

    @Schema(description = "Subscription end date")
    private LocalDateTime subscriptionEndDate;

    @Schema(description = "Whether the tenant is in trial mode", defaultValue = "false")
    private Boolean isTrial;

    @Schema(description = "Trial end date")
    private LocalDateTime trialEndDate;
    
    @Schema(description = "Additional metadata in JSON format", example = "{\"custom_field\":\"value\"}")
    private String metadata;
    
    @Schema(description = "Whether the tenant is active", defaultValue = "true")
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

