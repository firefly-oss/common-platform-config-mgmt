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
 * Entity representing webhook configuration for tenants and providers.
 * Manages webhook endpoints, events, authentication, and retry policies.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("webhook_configs")
public class WebhookConfig {

    @Id
    private UUID id;

    @Column("tenant_id")
    private UUID tenantId;

    @Column("provider_id")
    private UUID providerId;

    @Column("webhook_name")
    private String webhookName;

    @Column("webhook_url")
    private String webhookUrl;

    @Column("description")
    private String description;

    @Column("event_types")
    private String eventTypes;

    @Column("http_method")
    private String httpMethod;

    @Column("auth_type")
    private String authType;

    @Column("auth_header_name")
    private String authHeaderName;

    @Column("auth_header_value")
    private String authHeaderValue;

    @Column("secret_key")
    private String secretKey;

    @Column("custom_headers")
    private String customHeaders;

    @Column("timeout_seconds")
    private Integer timeoutSeconds;

    @Column("retry_enabled")
    private Boolean retryEnabled;

    @Column("max_retry_attempts")
    private Integer maxRetryAttempts;

    @Column("retry_delay_seconds")
    private Integer retryDelaySeconds;

    @Column("retry_backoff_multiplier")
    private Double retryBackoffMultiplier;

    @Column("enabled")
    private Boolean enabled;

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

