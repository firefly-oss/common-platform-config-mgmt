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


package com.firefly.common.config.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing operational settings for a tenant.
 * Includes rate limits, security policies, maintenance windows, circuit breakers, etc.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tenant_settings")
public class TenantSettings {

    @Id
    private UUID id;

    @Column("tenant_id")
    private UUID tenantId;

    // Rate Limiting
    @Column("api_rate_limit_per_minute")
    private Integer apiRateLimitPerMinute;

    @Column("api_rate_limit_per_hour")
    private Integer apiRateLimitPerHour;

    @Column("api_rate_limit_per_day")
    private Integer apiRateLimitPerDay;

    // Security Policies
    @Column("password_min_length")
    private Integer passwordMinLength;

    @Column("password_require_uppercase")
    private Boolean passwordRequireUppercase;

    @Column("password_require_lowercase")
    private Boolean passwordRequireLowercase;

    @Column("password_require_numbers")
    private Boolean passwordRequireNumbers;

    @Column("password_require_special_chars")
    private Boolean passwordRequireSpecialChars;

    @Column("password_expiry_days")
    private Integer passwordExpiryDays;

    @Column("mfa_enabled")
    private Boolean mfaEnabled;

    @Column("mfa_required")
    private Boolean mfaRequired;

    @Column("session_timeout_minutes")
    private Integer sessionTimeoutMinutes;

    @Column("max_login_attempts")
    private Integer maxLoginAttempts;

    @Column("account_lockout_duration_minutes")
    private Integer accountLockoutDurationMinutes;

    // Circuit Breaker Configuration
    @Column("circuit_breaker_enabled")
    private Boolean circuitBreakerEnabled;

    @Column("circuit_breaker_failure_threshold")
    private Integer circuitBreakerFailureThreshold;

    @Column("circuit_breaker_timeout_seconds")
    private Integer circuitBreakerTimeoutSeconds;

    @Column("circuit_breaker_reset_timeout_seconds")
    private Integer circuitBreakerResetTimeoutSeconds;

    // Maintenance Windows
    @Column("maintenance_mode_enabled")
    private Boolean maintenanceModeEnabled;

    @Column("maintenance_start_time")
    private LocalDateTime maintenanceStartTime;

    @Column("maintenance_end_time")
    private LocalDateTime maintenanceEndTime;

    @Column("maintenance_message")
    private String maintenanceMessage;

    // Audit and Logging
    @Column("audit_enabled")
    private Boolean auditEnabled;

    @Column("audit_retention_days")
    private Integer auditRetentionDays;

    @Column("log_level")
    private String logLevel;

    @Column("sensitive_data_masking_enabled")
    private Boolean sensitiveDataMaskingEnabled;

    // Data Retention
    @Column("transaction_retention_days")
    private Integer transactionRetentionDays;

    @Column("document_retention_days")
    private Integer documentRetentionDays;

    @Column("backup_enabled")
    private Boolean backupEnabled;

    @Column("backup_frequency_hours")
    private Integer backupFrequencyHours;

    // Notification Settings
    @Column("email_notifications_enabled")
    private Boolean emailNotificationsEnabled;

    @Column("sms_notifications_enabled")
    private Boolean smsNotificationsEnabled;

    @Column("push_notifications_enabled")
    private Boolean pushNotificationsEnabled;

    @Column("webhook_notifications_enabled")
    private Boolean webhookNotificationsEnabled;

    // Compliance
    @Column("gdpr_enabled")
    private Boolean gdprEnabled;

    @Column("pci_dss_enabled")
    private Boolean pciDssEnabled;

    @Column("data_residency_country")
    private String dataResidencyCountry;

    @Column("metadata")
    private String metadata;

    @Column("active")
    private Boolean active;

    @Version
    @Column("version")
    private Long version;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
}

