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
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing audit trail for configuration changes.
 * Tracks all changes to configuration entities with before/after values for rollback capability.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("configuration_audits")
public class ConfigurationAudit {

    @Id
    private UUID id;

    @Column("tenant_id")
    private UUID tenantId;

    @Column("entity_type")
    private String entityType;

    @Column("entity_id")
    private UUID entityId;

    @Column("action")
    private String action;

    @Column("field_name")
    private String fieldName;

    @Column("old_value")
    private String oldValue;

    @Column("new_value")
    private String newValue;

    @Column("changed_by_user_id")
    private UUID changedByUserId;

    @Column("changed_by_username")
    private String changedByUsername;

    @Column("change_reason")
    private String changeReason;

    @Column("ip_address")
    private String ipAddress;

    @Column("user_agent")
    private String userAgent;

    @Column("rollback_available")
    private Boolean rollbackAvailable;

    @Column("metadata")
    private String metadata;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
}

