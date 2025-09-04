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
 * Entity representing a BPMN process specific to a provider
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provider_processes")
public class ProviderProcess {

    @Id
    private UUID id;

    @Column("code")
    private String code;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("provider_id")
    private UUID providerId;

    @Column("process_type")
    private String processType;

    @Column("process_category")
    private String processCategory;

    @Column("is_common")
    private Boolean isCommon;

    @Column("priority")
    private Integer priority;

    @Column("estimated_duration_seconds")
    private Integer estimatedDurationSeconds;

    @Column("tags")
    private String tags;

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
