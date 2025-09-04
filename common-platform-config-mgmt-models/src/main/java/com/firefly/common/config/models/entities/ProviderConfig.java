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
 * Entity representing configuration settings for a provider
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provider_configs")
public class ProviderConfig {

    @Id
    private UUID id;

    @Column("provider_id")
    private UUID providerId;

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
