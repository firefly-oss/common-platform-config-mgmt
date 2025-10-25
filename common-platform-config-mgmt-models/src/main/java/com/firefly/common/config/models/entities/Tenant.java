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
 * Entity representing a Tenant in the Firefly core banking system.
 * A Tenant represents an isolated instance of Firefly with its own configuration,
 * providers, and operational settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tenants")
public class Tenant {

    @Id
    private UUID id;

    @Column("code")
    private String code;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("country_id")
    private UUID countryId;

    @Column("region")
    private String region;

    @Column("timezone")
    private String timezone;

    @Column("currency_code")
    private String currencyCode;

    @Column("language_code")
    private String languageCode;

    @Column("logo_url")
    private String logoUrl;

    @Column("primary_color")
    private String primaryColor;

    @Column("secondary_color")
    private String secondaryColor;

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

    @Column("max_users")
    private Integer maxUsers;

    @Column("max_accounts")
    private Integer maxAccounts;

    @Column("max_transactions_per_day")
    private Integer maxTransactionsPerDay;

    @Column("subscription_tier")
    private String subscriptionTier;

    @Column("subscription_start_date")
    private LocalDateTime subscriptionStartDate;

    @Column("subscription_end_date")
    private LocalDateTime subscriptionEndDate;

    @Column("trial_mode")
    private Boolean trialMode;

    @Column("trial_end_date")
    private LocalDateTime trialEndDate;

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

