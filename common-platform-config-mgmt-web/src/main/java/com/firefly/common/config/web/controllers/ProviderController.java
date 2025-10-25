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

import com.firefly.common.config.core.services.ProviderService;
import com.firefly.common.config.interfaces.dtos.ProviderDTO;
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
 * REST controller for managing providers and their nested resources
 */
@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
@Tag(name = "Providers", description = "API for managing providers")
public class ProviderController {

    private final ProviderService providerService;

    /**
     * GET /api/v1/providers/:id : Get a provider by ID
     *
     * @param id the ID of the provider to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderById",
            summary = "Get a provider by ID",
            description = "Returns a provider based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    public ResponseEntity<Mono<ProviderDTO>> getById(
            @Parameter(description = "ID of the provider to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(providerService.getById(id));
    }

    /**
     * POST /api/v1/providers/filter : Filter providers
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of providers in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviders",
            summary = "Filter providers",
            description = "Returns a filtered list of providers based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderDTO> filterRequest) {
        return ResponseEntity.ok(providerService.filter(filterRequest));
    }

    /**
     * POST /api/v1/providers : Create a new provider
     *
     * @param providerDTO the provider to create
     * @return the ResponseEntity with status 201 (Created) and the new provider in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProvider",
            summary = "Create a new provider",
            description = "Creates a new provider and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider created", content = @Content(schema = @Schema(implementation = ProviderDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderDTO>> create(
            @Parameter(description = "Provider to create", required = true)
            @Valid @RequestBody ProviderDTO providerDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerService.create(providerDTO));
    }

    /**
     * PUT /api/v1/providers/:id : Update an existing provider
     *
     * @param id the ID of the provider to update
     * @param providerDTO the provider to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProvider",
            summary = "Update an existing provider",
            description = "Updates an existing provider and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider updated", content = @Content(schema = @Schema(implementation = ProviderDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    public ResponseEntity<Mono<ProviderDTO>> update(
            @Parameter(description = "ID of the provider to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Provider to update", required = true)
            @Valid @RequestBody ProviderDTO providerDTO) {
        return ResponseEntity.ok(providerService.update(id, providerDTO));
    }

    /**
     * DELETE /api/v1/providers/:id : Delete a provider
     *
     * @param id the ID of the provider to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProvider",
            summary = "Delete a provider",
            description = "Deletes a provider",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider to delete", required = true)
            @PathVariable UUID id) {
        return providerService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
