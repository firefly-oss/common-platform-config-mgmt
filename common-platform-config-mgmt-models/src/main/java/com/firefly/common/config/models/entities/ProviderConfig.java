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

/**
 * Entity representing configuration settings for a provider
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provider_configs")
public class ProviderConfig {

    @Id
    private Long id;

    @Column("provider_id")
    private Long providerId;

    @Column("config_group")
    private String configGroup;

    @Column("key")
    private String key;

    @Column("value")
    private String value;

    @Column("value_type")
    private String valueType;

    @Column("description")
    private String description;

    @Column("is_secret")
    private Boolean isSecret;

    @Column("is_required")
    private Boolean isRequired;

    @Column("is_editable")
    private Boolean isEditable;

    @Column("validation_regex")
    private String validationRegex;

    @Column("default_value")
    private String defaultValue;

    @Column("environment")
    private String environment;

    @Column("expiration_date")
    private LocalDateTime expirationDate;

    @Column("tags")
    private String tags;

    @Column("metadata")
    private String metadata;

    @Column("order_index")
    private Integer orderIndex;

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
