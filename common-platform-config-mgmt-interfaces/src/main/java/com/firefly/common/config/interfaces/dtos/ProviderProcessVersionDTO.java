package com.firefly.common.config.interfaces.dtos;

import com.firefly.annotations.ValidDate;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO for Provider Process Version entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider Process Version information")
public class ProviderProcessVersionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider process version ID")
    private Long id;

    @NotNull(message = "Provider process ID is required")
    @FilterableId
    @Schema(description = "ID of the provider process")
    private Long providerProcessId;

    @Schema(description = "Provider process information")
    private ProviderProcessDTO providerProcess;

    @NotBlank(message = "Version is required")
    @Size(min = 1, max = 20, message = "Version must be between 1 and 20 characters")
    @Schema(description = "Version of the process", example = "1.0.0")
    private String version;

    @NotBlank(message = "BPMN XML is required")
    @Schema(description = "BPMN XML definition of the process")
    private String bpmnXml;

    @Schema(description = "BPMN diagram XML for visualization")
    private String bpmnDiagramXml;

    @NotNull(message = "Provider process status ID is required")
    @FilterableId
    @Schema(description = "ID of the provider process status")
    private Long providerProcessStatusId;

    @Schema(description = "Provider process status information")
    private ProviderProcessStatusDTO providerProcessStatus;

    @Schema(description = "Notes about the process version", example = "Initial version of the payment process")
    private String notes;

    @Schema(description = "Changelog describing changes from previous version", example = "Added support for international payments")
    private String changelog;

    @Schema(description = "User who deployed the process", example = "john.doe@example.com")
    private String deployedBy;

    @Schema(description = "Date and time when the process was deployed")

    @ValidDate
    private LocalDateTime deployedAt;

    @Schema(description = "Whether this is the current version of the process", example = "true", defaultValue = "true")
    private Boolean isCurrent;

    @Schema(description = "Whether this version is deployed to the execution engine", example = "false", defaultValue = "false")
    private Boolean isDeployed;

    @Schema(description = "Deployment ID in the execution engine", example = "deployment-123456")
    private String deploymentId;

    @Schema(description = "Whether the process version is active", defaultValue = "true")
    private Boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Version number for optimistic locking")
    private Long versionNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}
