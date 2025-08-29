package com.firefly.common.config.web.controllers;

import com.firefly.common.config.core.services.ProviderMapTypeService;
import com.firefly.common.config.interfaces.dtos.ProviderMapTypeDTO;
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

/**
 * REST controller for managing provider map types
 */
@RestController
@RequestMapping("/api/v1/provider-map-types")
@RequiredArgsConstructor
@Tag(name = "Provider Map Types", description = "API for managing provider map types")
public class ProviderMapTypeController {

    private final ProviderMapTypeService providerMapTypeService;

    /**
     * GET /api/v1/provider-map-types/:id : Get a provider map type by ID
     *
     * @param id the ID of the provider map type to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider map type in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderMapTypeById",
            summary = "Get a provider map type by ID",
            description = "Returns a provider map type based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderMapTypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider map type not found")
            }
    )
    public ResponseEntity<Mono<ProviderMapTypeDTO>> getById(
            @Parameter(description = "ID of the provider map type to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(providerMapTypeService.getById(id));
    }

    /**
     * POST /api/v1/provider-map-types/filter : Filter provider map types
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider map types in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviderMapTypes",
            summary = "Filter provider map types",
            description = "Returns a filtered list of provider map types based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderMapTypeDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderMapTypeDTO> filterRequest) {
        return ResponseEntity.ok(providerMapTypeService.filter(filterRequest));
    }

    /**
     * POST /api/v1/provider-map-types : Create a new provider map type
     *
     * @param providerMapTypeDTO the provider map type to create
     * @return the ResponseEntity with status 201 (Created) and the new provider map type in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProviderMapType",
            summary = "Create a new provider map type",
            description = "Creates a new provider map type and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider map type created", content = @Content(schema = @Schema(implementation = ProviderMapTypeDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderMapTypeDTO>> create(
            @Parameter(description = "Provider map type to create", required = true)
            @Valid @RequestBody ProviderMapTypeDTO providerMapTypeDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerMapTypeService.create(providerMapTypeDTO));
    }

    /**
     * PUT /api/v1/provider-map-types/:id : Update an existing provider map type
     *
     * @param id the ID of the provider map type to update
     * @param providerMapTypeDTO the provider map type to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider map type in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProviderMapType",
            summary = "Update an existing provider map type",
            description = "Updates an existing provider map type and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider map type updated", content = @Content(schema = @Schema(implementation = ProviderMapTypeDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider map type not found")
            }
    )
    public ResponseEntity<Mono<ProviderMapTypeDTO>> update(
            @Parameter(description = "ID of the provider map type to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Provider map type to update", required = true)
            @Valid @RequestBody ProviderMapTypeDTO providerMapTypeDTO) {
        return ResponseEntity.ok(providerMapTypeService.update(id, providerMapTypeDTO));
    }

    /**
     * DELETE /api/v1/provider-map-types/:id : Delete a provider map type
     *
     * @param id the ID of the provider map type to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderMapType",
            summary = "Delete a provider map type",
            description = "Deletes a provider map type",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider map type deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider map type not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider map type to delete", required = true)
            @PathVariable Long id) {
        return providerMapTypeService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}