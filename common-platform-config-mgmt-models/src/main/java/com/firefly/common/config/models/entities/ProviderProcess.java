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
 * Entity representing a BPMN process specific to a provider
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provider_processes")
public class ProviderProcess {

    @Id
    private Long id;

    @Column("code")
    private String code;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("provider_id")
    private Long providerId;

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
