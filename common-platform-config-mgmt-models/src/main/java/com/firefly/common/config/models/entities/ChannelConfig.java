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
 * Entity representing channel configuration for a tenant.
 * Manages configuration for different banking channels (Web, Mobile, ATM, Branch, etc.)
 * 
 * This entity contains only essential fields. Additional dynamic configuration is stored in ChannelConfigParameter.
 * This design allows for flexible, extensible configuration without schema changes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("channel_configs")
public class ChannelConfig {

    @Id
    private UUID id;

    @Column("tenant_id")
    private UUID tenantId;

    @Column("channel_code")
    private String channelCode; // WEB_BANKING, MOBILE_BANKING, ATM, BRANCH, CALL_CENTER, API, OPEN_BANKING

    @Column("channel_name")
    private String channelName;

    @Column("description")
    private String description;

    @Column("enabled")
    private Boolean enabled;

    @Column("priority")
    private Integer priority; // Channel priority for failover (1 = highest)

    @Column("failover_channel_id")
    private UUID failoverChannelId; // Backup channel if this one fails

    @Column("metadata")
    private String metadata; // Additional metadata in JSON format

    @Column("active")
    private Boolean active;

    @Version
    private Long version;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
}

