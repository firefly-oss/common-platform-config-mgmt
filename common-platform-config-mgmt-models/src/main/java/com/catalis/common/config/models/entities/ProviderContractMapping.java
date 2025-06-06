package com.catalis.common.config.models.entities;

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
 * Entity representing a mapping between provider contracts and internal contracts
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provider_contracts_mapping")
public class ProviderContractMapping {

    @Id
    private Long id;

    @Column("internal_contract_id")
    private Long internalContractId;

    @Column("provider_contract_id")
    private Long providerContractId;

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