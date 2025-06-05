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
 * Entity representing a provider mapping in the system
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provider_mapping")
public class ProviderMapping {

    @Id
    private Long id;

    @Column("provider_map_type_id")
    private Long providerMapTypeId;

    @Column("provider_id")
    private Long providerId;

    @Column("internal_provider_id")
    private Long internalProviderId;

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