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
 * Entity representing a version of a provider process
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provider_process_versions")
public class ProviderProcessVersion {

    @Id
    private Long id;

    @Column("provider_process_id")
    private Long providerProcessId;

    @Column("version")
    private String version;

    @Column("bpmn_xml")
    private String bpmnXml;

    @Column("bpmn_diagram_xml")
    private String bpmnDiagramXml;

    @Column("provider_process_status_id")
    private Long providerProcessStatusId;

    @Column("notes")
    private String notes;

    @Column("changelog")
    private String changelog;

    @Column("deployed_by")
    private String deployedBy;

    @Column("deployed_at")
    private LocalDateTime deployedAt;

    @Column("is_current")
    private Boolean isCurrent;

    @Column("is_deployed")
    private Boolean isDeployed;

    @Column("deployment_id")
    private String deploymentId;

    @Column("active")
    private Boolean active;

    @Version
    @Column("version_number")
    private Long versionNumber;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
