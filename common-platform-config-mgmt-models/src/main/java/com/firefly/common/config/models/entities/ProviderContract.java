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
 * Entity representing a provider contract in the system
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provider_contracts")
public class ProviderContract {

    @Id
    private Long id;

    @Column("contract_id")
    private Long contractId;

    @Column("contract_type_id")
    private Long contractTypeId;

    @Column("provider_id")
    private Long providerId;

    @Column("provider_contract_status_id")
    private Long providerContractStatusId;

    @Column("description")
    private String description;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

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