package com.catalis.common.config.web.controllers;

import com.catalis.common.config.core.services.ProviderMappingService;
import com.catalis.common.config.interfaces.dtos.ProviderMappingDTO;
import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
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

/**
 * REST controller for managing provider mappings
 */
@RestController
@RequestMapping("/api/v1/provider-mappings")
@RequiredArgsConstructor
@Tag(name = "Provider Mappings", description = "API for managing provider mappings")
public class ProviderMappingController {

    private final ProviderMappingService providerMappingService;

    /**
     * GET /api/v1/provider-mappings/:id : Get a provider mapping by ID
     *
     * @param id the ID of the provider mapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider mapping in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderMappingById",
            summary = "Get a provider mapping by ID",
            description = "Returns a provider mapping based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderMappingDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider mapping not found")
            }
    )
    public ResponseEntity<Mono<ProviderMappingDTO>> getById(
            @Parameter(description = "ID of the provider mapping to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(providerMappingService.getById(id));
    }

    /**
     * POST /api/v1/provider-mappings/filter : Filter provider mappings
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider mappings in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviderMappings",
            summary = "Filter provider mappings",
            description = "Returns a filtered list of provider mappings based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderMappingDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderMappingDTO> filterRequest) {
        return ResponseEntity.ok(providerMappingService.filter(filterRequest));
    }

    /**
     * POST /api/v1/provider-mappings : Create a new provider mapping
     *
     * @param providerMappingDTO the provider mapping to create
     * @return the ResponseEntity with status 201 (Created) and the new provider mapping in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProviderMapping",
            summary = "Create a new provider mapping",
            description = "Creates a new provider mapping and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider mapping created", content = @Content(schema = @Schema(implementation = ProviderMappingDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderMappingDTO>> create(
            @Parameter(description = "Provider mapping to create", required = true)
            @Valid @RequestBody ProviderMappingDTO providerMappingDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerMappingService.create(providerMappingDTO));
    }

    /**
     * PUT /api/v1/provider-mappings/:id : Update an existing provider mapping
     *
     * @param id the ID of the provider mapping to update
     * @param providerMappingDTO the provider mapping to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider mapping in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProviderMapping",
            summary = "Update an existing provider mapping",
            description = "Updates an existing provider mapping and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider mapping updated", content = @Content(schema = @Schema(implementation = ProviderMappingDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider mapping not found")
            }
    )
    public ResponseEntity<Mono<ProviderMappingDTO>> update(
            @Parameter(description = "ID of the provider mapping to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Provider mapping to update", required = true)
            @Valid @RequestBody ProviderMappingDTO providerMappingDTO) {
        return ResponseEntity.ok(providerMappingService.update(id, providerMappingDTO));
    }

    /**
     * DELETE /api/v1/provider-mappings/:id : Delete a provider mapping
     *
     * @param id the ID of the provider mapping to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderMapping",
            summary = "Delete a provider mapping",
            description = "Deletes a provider mapping",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider mapping deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider mapping not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider mapping to delete", required = true)
            @PathVariable Long id) {
        return providerMappingService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}