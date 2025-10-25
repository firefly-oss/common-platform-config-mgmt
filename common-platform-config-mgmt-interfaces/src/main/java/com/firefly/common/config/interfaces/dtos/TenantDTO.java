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
    
    @Schema(description = "Tenant status information")
    private TenantStatusDTO tenantStatus;
    
    @FilterableId
    @Schema(description = "ID of the country")
    private UUID countryId;
    
    @Schema(description = "Region of the tenant", example = "North America")
    private String region;
    
    @Schema(description = "Timezone of the tenant", example = "America/New_York")
    private String timezone;
    
    @Schema(description = "Currency code", example = "USD")
    private String currencyCode;
    
    @Schema(description = "Language code", example = "en")
    private String languageCode;
    
    @Schema(description = "Logo URL", example = "https://example.com/logo.png")
    private String logoUrl;
    
    @Schema(description = "Primary brand color", example = "#FF5733")
    private String primaryColor;
    
    @Schema(description = "Secondary brand color", example = "#33FF57")
    private String secondaryColor;
    
    @Schema(description = "Contact name", example = "John Doe")
    private String contactName;
    
    @Email(message = "Contact email must be valid")
    @Schema(description = "Contact email", example = "contact@acmebank.com")
    private String contactEmail;
    
    @Schema(description = "Contact phone", example = "+1-555-0100")
    private String contactPhone;
    
    @Schema(description = "Technical contact name", example = "Jane Smith")
    private String technicalContactName;
    
    @Email(message = "Technical contact email must be valid")
    @Schema(description = "Technical contact email", example = "tech@acmebank.com")
    private String technicalContactEmail;
    
    @Schema(description = "Technical contact phone", example = "+1-555-0101")
    private String technicalContactPhone;
    
    @Schema(description = "Maximum number of users allowed", example = "1000")
    private Integer maxUsers;
    
    @Schema(description = "Maximum number of accounts allowed", example = "10000")
    private Integer maxAccounts;
    
    @Schema(description = "Maximum transactions per day allowed", example = "50000")
    private Integer maxTransactionsPerDay;
    
    @Schema(description = "Subscription tier", example = "ENTERPRISE")
    private String subscriptionTier;
    
    @ValidDate
    @Schema(description = "Subscription start date")
    private LocalDateTime subscriptionStartDate;
    
    @ValidDate
    @Schema(description = "Subscription end date")
    private LocalDateTime subscriptionEndDate;
    
    @Schema(description = "Whether the tenant is in trial mode", defaultValue = "false")
    private Boolean trialMode;
    
    @ValidDate
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

