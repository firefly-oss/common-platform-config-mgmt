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
 * Entity representing a provider in the system
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("providers")
public class Provider {

    @Id
    private UUID id;

    @Column("code")
    private String code;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("provider_type_id")
    private UUID providerTypeId;

    @Column("provider_status_id")
    private UUID providerStatusId;

    @Column("api_base_url")
    private String apiBaseUrl;

    @Column("webhook_url")
    private String webhookUrl;

    @Column("callback_url")
    private String callbackUrl;

    @Column("logo_url")
    private String logoUrl;

    @Column("documentation_url")
    private String documentationUrl;

    @Column("support_url")
    private String supportUrl;

    @Column("contact_name")
    private String contactName;

    @Column("contact_email")
    private String contactEmail;

    @Column("contact_phone")
    private String contactPhone;

    @Column("technical_contact_name")
    private String technicalContactName;

    @Column("technical_contact_email")
    private String technicalContactEmail;

    @Column("technical_contact_phone")
    private String technicalContactPhone;

    @Column("country_id")
    private UUID countryId;

    @Column("region")
    private String region;

    @Column("timezone")
    private String timezone;

    @Column("currency_code")
    private String currencyCode;

    @Column("max_requests_per_second")
    private Integer maxRequestsPerSecond;

    @Column("max_concurrent_requests")
    private Integer maxConcurrentRequests;

    @Column("requires_authentication")
    private Boolean requiresAuthentication;

    @Column("authentication_type")
    private String authenticationType;

    @Column("supports_webhooks")
    private Boolean supportsWebhooks;

    @Column("supports_callbacks")
    private Boolean supportsCallbacks;

    @Column("supports_polling")
    private Boolean supportsPolling;

    @Column("polling_interval_seconds")
    private Integer pollingIntervalSeconds;

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
