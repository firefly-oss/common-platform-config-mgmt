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


package com.firefly.common.config.web.controllers;

import com.firefly.common.config.core.services.ProviderTenantService;
import com.firefly.common.config.core.services.TenantService;
import com.firefly.common.config.interfaces.dtos.ProviderTenantDTO;
import com.firefly.common.config.interfaces.dtos.TenantDTO;
import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

/**
 * REST controller for managing tenants and their nested resources
 */
@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
@Tag(name = "Tenants", description = "API for managing Firefly tenants and their provider configurations")
public class TenantController {

    private final TenantService tenantService;
    private final ProviderTenantService providerTenantService;

    @GetMapping("/{id}")
    @Operation(
            operationId = "getTenantById",
            summary = "Get a tenant by ID",
            description = "Returns a tenant based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = TenantDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Tenant not found")
            }
    )
    public ResponseEntity<Mono<TenantDTO>> getById(
            @Parameter(description = "ID of the tenant to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(tenantService.getById(id));
    }

    @PostMapping("/filter")
    @Operation(
            operationId = "filterTenants",
            summary = "Filter tenants",
            description = "Returns a filtered list of tenants based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<TenantDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<TenantDTO> filterRequest) {
        return ResponseEntity.ok(tenantService.filter(filterRequest));
    }

    @PostMapping
    @Operation(
            operationId = "createTenant",
            summary = "Create a new tenant",
            description = "Creates a new tenant and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tenant created", content = @Content(schema = @Schema(implementation = TenantDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<TenantDTO>> create(
            @Parameter(description = "Tenant to create", required = true)
            @Valid @RequestBody TenantDTO tenantDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tenantService.create(tenantDTO));
    }

    @PutMapping("/{id}")
    @Operation(
            operationId = "updateTenant",
            summary = "Update an existing tenant",
            description = "Updates an existing tenant and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tenant updated", content = @Content(schema = @Schema(implementation = TenantDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Tenant not found")
            }
    )
    public ResponseEntity<Mono<TenantDTO>> update(
            @Parameter(description = "ID of the tenant to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Tenant to update", required = true)
            @Valid @RequestBody TenantDTO tenantDTO) {
        return ResponseEntity.ok(tenantService.update(id, tenantDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteTenant",
            summary = "Delete a tenant",
            description = "Deletes a tenant",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tenant deleted"),
                    @ApiResponse(responseCode = "404", description = "Tenant not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the tenant to delete", required = true)
            @PathVariable UUID id) {
        return tenantService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    // Nested resource: Tenant Providers

    @PostMapping("/{tenantId}/providers/filter")
    @Operation(
            operationId = "filterTenantProviders",
            summary = "Filter providers for a tenant",
            description = "Returns a filtered list of provider-tenant relationships for a specific tenant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderTenantDTO>>> filterProviders(
            @Parameter(description = "ID of the tenant", required = true)
            @PathVariable UUID tenantId,
            @RequestBody FilterRequest<ProviderTenantDTO> filterRequest) {

        filterRequest.getFilters().setTenantId(tenantId);
        return ResponseEntity.ok(providerTenantService.filter(filterRequest));
    }

    @PostMapping("/{tenantId}/providers")
    @Operation(
            operationId = "addProviderToTenant",
            summary = "Add a provider to a tenant",
            description = "Creates a new provider-tenant relationship and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider added to tenant", content = @Content(schema = @Schema(implementation = ProviderTenantDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Tenant not found")
            }
    )
    public ResponseEntity<Mono<ProviderTenantDTO>> addProvider(
            @Parameter(description = "ID of the tenant", required = true)
            @PathVariable UUID tenantId,
            @Parameter(description = "Provider-tenant relationship to create", required = true)
            @Valid @RequestBody ProviderTenantDTO providerTenantDTO) {
        providerTenantDTO.setTenantId(tenantId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerTenantService.create(providerTenantDTO));
    }

    @GetMapping("/{tenantId}/providers/{id}")
    @Operation(
            operationId = "getTenantProviderById",
            summary = "Get a provider-tenant relationship by ID",
            description = "Returns a provider-tenant relationship for a specific tenant based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderTenantDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider-tenant relationship not found")
            }
    )
    public ResponseEntity<Mono<ProviderTenantDTO>> getProviderById(
            @Parameter(description = "ID of the tenant", required = true)
            @PathVariable UUID tenantId,
            @Parameter(description = "ID of the provider-tenant relationship to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(providerTenantService.getById(id)
                .filter(pt -> pt.getTenantId().equals(tenantId)));
    }

    @PutMapping("/{tenantId}/providers/{id}")
    @Operation(
            operationId = "updateTenantProvider",
            summary = "Update a provider-tenant relationship",
            description = "Updates a provider-tenant relationship for a specific tenant and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider-tenant relationship updated", content = @Content(schema = @Schema(implementation = ProviderTenantDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider-tenant relationship not found")
            }
    )
    public ResponseEntity<Mono<ProviderTenantDTO>> updateProvider(
            @Parameter(description = "ID of the tenant", required = true)
            @PathVariable UUID tenantId,
            @Parameter(description = "ID of the provider-tenant relationship to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Provider-tenant relationship to update", required = true)
            @Valid @RequestBody ProviderTenantDTO providerTenantDTO) {
        providerTenantDTO.setTenantId(tenantId);
        return ResponseEntity.ok(providerTenantService.update(id, providerTenantDTO));
    }

    @DeleteMapping("/{tenantId}/providers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "removeTenantProvider",
            summary = "Remove a provider from a tenant",
            description = "Deletes a provider-tenant relationship for a specific tenant",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider removed from tenant"),
                    @ApiResponse(responseCode = "404", description = "Provider-tenant relationship not found")
            }
    )
    public Mono<ResponseEntity<Void>> removeProvider(
            @Parameter(description = "ID of the tenant", required = true)
            @PathVariable UUID tenantId,
            @Parameter(description = "ID of the provider-tenant relationship to delete", required = true)
            @PathVariable UUID id) {
        return providerTenantService.getById(id)
                .filter(pt -> pt.getTenantId().equals(tenantId))
                .flatMap(pt -> providerTenantService.delete(id))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

