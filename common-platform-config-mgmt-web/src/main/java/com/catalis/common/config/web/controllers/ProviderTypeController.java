package com.catalis.common.config.web.controllers;

import com.catalis.common.config.core.services.ProviderTypeService;
import com.catalis.common.config.interfaces.dtos.ProviderTypeDTO;
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
 * REST controller for managing provider types
 */
@RestController
@RequestMapping("/api/v1/provider-types")
@RequiredArgsConstructor
@Tag(name = "Provider Types", description = "API for managing provider types")
public class ProviderTypeController {

    private final ProviderTypeService providerTypeService;

    /**
     * GET /api/v1/provider-types/:id : Get a provider type by ID
     *
     * @param id the ID of the provider type to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider type in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderTypeById",
            summary = "Get a provider type by ID",
            description = "Returns a provider type based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderTypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider type not found")
            }
    )
    public ResponseEntity<Mono<ProviderTypeDTO>> getById(
            @Parameter(description = "ID of the provider type to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(providerTypeService.getById(id));
    }

    /**
     * POST /api/v1/provider-types/filter : Filter provider types
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider types in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviderTypes",
            summary = "Filter provider types",
            description = "Returns a filtered list of provider types based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderTypeDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderTypeDTO> filterRequest) {
        return ResponseEntity.ok(providerTypeService.filter(filterRequest));
    }

    /**
     * POST /api/v1/provider-types : Create a new provider type
     *
     * @param providerTypeDTO the provider type to create
     * @return the ResponseEntity with status 201 (Created) and the new provider type in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProviderType",
            summary = "Create a new provider type",
            description = "Creates a new provider type and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider type created", content = @Content(schema = @Schema(implementation = ProviderTypeDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderTypeDTO>> create(
            @Parameter(description = "Provider type to create", required = true)
            @Valid @RequestBody ProviderTypeDTO providerTypeDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerTypeService.create(providerTypeDTO));
    }

    /**
     * PUT /api/v1/provider-types/:id : Update an existing provider type
     *
     * @param id the ID of the provider type to update
     * @param providerTypeDTO the provider type to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider type in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProviderType",
            summary = "Update an existing provider type",
            description = "Updates an existing provider type and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider type updated", content = @Content(schema = @Schema(implementation = ProviderTypeDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider type not found")
            }
    )
    public ResponseEntity<Mono<ProviderTypeDTO>> update(
            @Parameter(description = "ID of the provider type to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Provider type to update", required = true)
            @Valid @RequestBody ProviderTypeDTO providerTypeDTO) {
        return ResponseEntity.ok(providerTypeService.update(id, providerTypeDTO));
    }

    /**
     * DELETE /api/v1/provider-types/:id : Delete a provider type
     *
     * @param id the ID of the provider type to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderType",
            summary = "Delete a provider type",
            description = "Deletes a provider type",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider type deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider type not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider type to delete", required = true)
            @PathVariable Long id) {
        return providerTypeService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
