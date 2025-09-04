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

import com.firefly.common.config.core.services.ProviderContractMappingService;
import com.firefly.common.config.interfaces.dtos.ProviderContractMappingDTO;
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
 * REST controller for managing provider contract mappings
 */
@RestController
@RequestMapping("/api/v1/provider-contract-mappings")
@RequiredArgsConstructor
@Tag(name = "Provider Contract Mappings", description = "API for managing provider contract mappings")
public class ProviderContractMappingController {

    private final ProviderContractMappingService providerContractMappingService;

    /**
     * GET /api/v1/provider-contract-mappings/:id : Get a provider contract mapping by ID
     *
     * @param id the ID of the provider contract mapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider contract mapping in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderContractMappingById",
            summary = "Get a provider contract mapping by ID",
            description = "Returns a provider contract mapping based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderContractMappingDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider contract mapping not found")
            }
    )
    public ResponseEntity<Mono<ProviderContractMappingDTO>> getById(
            @Parameter(description = "ID of the provider contract mapping to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(providerContractMappingService.getById(id));
    }

    /**
     * POST /api/v1/provider-contract-mappings/filter : Filter provider contract mappings
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider contract mappings in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviderContractMappings",
            summary = "Filter provider contract mappings",
            description = "Returns a filtered list of provider contract mappings based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderContractMappingDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderContractMappingDTO> filterRequest) {
        return ResponseEntity.ok(providerContractMappingService.filter(filterRequest));
    }

    /**
     * POST /api/v1/provider-contract-mappings : Create a new provider contract mapping
     *
     * @param providerContractMappingDTO the provider contract mapping to create
     * @return the ResponseEntity with status 201 (Created) and the new provider contract mapping in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProviderContractMapping",
            summary = "Create a new provider contract mapping",
            description = "Creates a new provider contract mapping and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider contract mapping created", content = @Content(schema = @Schema(implementation = ProviderContractMappingDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderContractMappingDTO>> create(
            @Parameter(description = "Provider contract mapping to create", required = true)
            @Valid @RequestBody ProviderContractMappingDTO providerContractMappingDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerContractMappingService.create(providerContractMappingDTO));
    }

    /**
     * PUT /api/v1/provider-contract-mappings/:id : Update an existing provider contract mapping
     *
     * @param id the ID of the provider contract mapping to update
     * @param providerContractMappingDTO the provider contract mapping to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider contract mapping in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProviderContractMapping",
            summary = "Update an existing provider contract mapping",
            description = "Updates an existing provider contract mapping and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider contract mapping updated", content = @Content(schema = @Schema(implementation = ProviderContractMappingDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider contract mapping not found")
            }
    )
    public ResponseEntity<Mono<ProviderContractMappingDTO>> update(
            @Parameter(description = "ID of the provider contract mapping to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Provider contract mapping to update", required = true)
            @Valid @RequestBody ProviderContractMappingDTO providerContractMappingDTO) {
        return ResponseEntity.ok(providerContractMappingService.update(id, providerContractMappingDTO));
    }

    /**
     * DELETE /api/v1/provider-contract-mappings/:id : Delete a provider contract mapping
     *
     * @param id the ID of the provider contract mapping to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderContractMapping",
            summary = "Delete a provider contract mapping",
            description = "Deletes a provider contract mapping",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider contract mapping deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider contract mapping not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider contract mapping to delete", required = true)
            @PathVariable UUID id) {
        return providerContractMappingService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}