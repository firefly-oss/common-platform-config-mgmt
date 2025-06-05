package com.catalis.common.config.models.entities;

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
    private Long id;

    @Column("code")
    private String code;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("provider_type_id")
    private Long providerTypeId;

    @Column("provider_status_id")
    private Long providerStatusId;

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
    private Long countryId;

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
