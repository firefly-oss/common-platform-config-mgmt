package com.firefly.common.config.interfaces.dtos;

import com.firefly.annotations.ValidDate;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Provider Contract entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider Contract information")
public class ProviderContractDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider contract ID")
    private UUID id;

    @NotNull(message = "Contract ID is required")
    @Schema(description = "ID of the contract", example = "12345")
    private UUID contractId;

    @NotNull(message = "Contract type ID is required")
    @Schema(description = "ID of the contract type", example = "1")
    private UUID contractTypeId;

    @NotNull(message = "Provider ID is required")
    @FilterableId
    @Schema(description = "ID of the provider")
    private UUID providerId;

    @Schema(description = "Provider information")
    private ProviderDTO provider;

    @NotNull(message = "Provider contract status ID is required")
    @FilterableId
    @Schema(description = "ID of the provider contract status")
    private UUID providerContractStatusId;

    @Schema(description = "Provider contract status information")
    private ProviderContractStatusDTO providerContractStatus;

    @Schema(description = "Description of the provider contract", example = "Contract for payment processing services")
    private String description;

    @Schema(description = "Start date of the contract")

    @ValidDate
    private LocalDateTime startDate;

    @Schema(description = "End date of the contract")

    @ValidDate
    private LocalDateTime endDate;

    @Schema(description = "Whether the provider contract is active", defaultValue = "true")
    private Boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Version for optimistic locking")
    private Long version;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}
