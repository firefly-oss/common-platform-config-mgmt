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
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for TenantSettings entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Operational settings for a tenant")
public class TenantSettingsDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Settings ID")
    private UUID id;

    @NotNull(message = "Tenant ID is required")
    @FilterableId
    @Schema(description = "Tenant ID")
    private UUID tenantId;

    // Rate Limiting
    @Min(value = 0, message = "Rate limit must be non-negative")
    @Schema(description = "API rate limit per minute", example = "100")
    private Integer apiRateLimitPerMinute;

    @Min(value = 0, message = "Rate limit must be non-negative")
    @Schema(description = "API rate limit per hour", example = "5000")
    private Integer apiRateLimitPerHour;

    @Min(value = 0, message = "Rate limit must be non-negative")
    @Schema(description = "API rate limit per day", example = "100000")
    private Integer apiRateLimitPerDay;

    // Security Policies
    @Min(value = 6, message = "Password minimum length must be at least 6")
    @Schema(description = "Minimum password length", example = "12")
    private Integer passwordMinLength;

    @Schema(description = "Require uppercase letters in password", example = "true")
    private Boolean passwordRequireUppercase;

    @Schema(description = "Require lowercase letters in password", example = "true")
    private Boolean passwordRequireLowercase;

    @Schema(description = "Require numbers in password", example = "true")
    private Boolean passwordRequireNumbers;

    @Schema(description = "Require special characters in password", example = "true")
    private Boolean passwordRequireSpecialChars;

    @Min(value = 0, message = "Password expiry days must be non-negative")
    @Schema(description = "Password expiry in days (0 = never)", example = "90")
    private Integer passwordExpiryDays;

    @Schema(description = "Whether MFA is enabled", example = "true")
    private Boolean mfaEnabled;

    @Schema(description = "Whether MFA is required", example = "false")
    private Boolean mfaRequired;

    @Min(value = 1, message = "Session timeout must be at least 1 minute")
    @Schema(description = "Session timeout in minutes", example = "30")
    private Integer sessionTimeoutMinutes;

    @Min(value = 1, message = "Max login attempts must be at least 1")
    @Schema(description = "Maximum login attempts before lockout", example = "5")
    private Integer maxLoginAttempts;

    @Min(value = 0, message = "Lockout duration must be non-negative")
    @Schema(description = "Account lockout duration in minutes", example = "30")
    private Integer accountLockoutDurationMinutes;

    // Circuit Breaker Configuration
    @Schema(description = "Whether circuit breaker is enabled", example = "true")
    private Boolean circuitBreakerEnabled;

    @Min(value = 1, message = "Failure threshold must be at least 1")
    @Schema(description = "Circuit breaker failure threshold", example = "5")
    private Integer circuitBreakerFailureThreshold;

    @Min(value = 1, message = "Timeout must be at least 1 second")
    @Schema(description = "Circuit breaker timeout in seconds", example = "30")
    private Integer circuitBreakerTimeoutSeconds;

    @Min(value = 1, message = "Reset timeout must be at least 1 second")
    @Schema(description = "Circuit breaker reset timeout in seconds", example = "60")
    private Integer circuitBreakerResetTimeoutSeconds;

    // Maintenance Windows
    @Schema(description = "Whether maintenance mode is enabled", example = "false")
    private Boolean maintenanceModeEnabled;

    @Schema(description = "Maintenance start time")
    private LocalDateTime maintenanceStartTime;

    @Schema(description = "Maintenance end time")
    private LocalDateTime maintenanceEndTime;

    @Schema(description = "Maintenance message to display", example = "System under maintenance")
    private String maintenanceMessage;

    // Audit and Logging
    @Schema(description = "Whether audit is enabled", example = "true")
    private Boolean auditEnabled;

    @Min(value = 1, message = "Retention days must be at least 1")
    @Schema(description = "Audit retention in days", example = "365")
    private Integer auditRetentionDays;

    @Schema(description = "Log level (DEBUG, INFO, WARN, ERROR)", example = "INFO")
    private String logLevel;

    @Schema(description = "Whether sensitive data masking is enabled", example = "true")
    private Boolean sensitiveDataMaskingEnabled;

    // Data Retention
    @Min(value = 1, message = "Retention days must be at least 1")
    @Schema(description = "Transaction retention in days", example = "2555")
    private Integer transactionRetentionDays;

    @Min(value = 1, message = "Retention days must be at least 1")
    @Schema(description = "Document retention in days", example = "2555")
    private Integer documentRetentionDays;

    @Schema(description = "Whether backup is enabled", example = "true")
    private Boolean backupEnabled;

    @Min(value = 1, message = "Backup frequency must be at least 1 hour")
    @Schema(description = "Backup frequency in hours", example = "24")
    private Integer backupFrequencyHours;

    // Notification Settings
    @Schema(description = "Whether email notifications are enabled", example = "true")
    private Boolean emailNotificationsEnabled;

    @Schema(description = "Whether SMS notifications are enabled", example = "true")
    private Boolean smsNotificationsEnabled;

    @Schema(description = "Whether push notifications are enabled", example = "true")
    private Boolean pushNotificationsEnabled;

    @Schema(description = "Whether webhook notifications are enabled", example = "true")
    private Boolean webhookNotificationsEnabled;

    // Compliance
    @Schema(description = "Whether GDPR is enabled", example = "true")
    private Boolean gdprEnabled;

    @Schema(description = "Whether PCI-DSS is enabled", example = "true")
    private Boolean pciDssEnabled;

    @Schema(description = "Data residency country code", example = "US")
    private String dataResidencyCountry;

    @Schema(description = "Additional metadata (JSON)")
    private String metadata;

    @Schema(description = "Whether the settings are active", example = "true")
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

