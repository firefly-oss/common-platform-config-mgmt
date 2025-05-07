package com.catalis.common.config.web.controllers;

import com.catalis.common.config.core.services.ProviderStatusService;
import com.catalis.common.config.interfaces.dtos.ProviderStatusDTO;
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
 * REST controller for managing provider statuses
 */
@RestController
@RequestMapping("/api/v1/provider-statuses")
@RequiredArgsConstructor
@Tag(name = "Provider Statuses", description = "API for managing provider statuses")
public class ProviderStatusController {

    private final ProviderStatusService providerStatusService;

    /**
     * GET /api/v1/provider-statuses/:id : Get a provider status by ID
     *
     * @param id the ID of the provider status to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider status in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a provider status by ID",
            description = "Returns a provider status based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderStatusDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider status not found")
            }
    )
    public ResponseEntity<Mono<ProviderStatusDTO>> getById(
            @Parameter(description = "ID of the provider status to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(providerStatusService.getById(id));
    }

    /**
     * POST /api/v1/provider-statuses/filter : Filter provider statuses
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider statuses in the body
     */
    @PostMapping("/filter")
    @Operation(
            summary = "Filter provider statuses",
            description = "Returns a filtered list of provider statuses based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderStatusDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderStatusDTO> filterRequest) {
        return ResponseEntity.ok(providerStatusService.filter(filterRequest));
    }

    /**
     * POST /api/v1/provider-statuses : Create a new provider status
     *
     * @param providerStatusDTO the provider status to create
     * @return the ResponseEntity with status 201 (Created) and the new provider status in the body
     */
    @PostMapping
    @Operation(
            summary = "Create a new provider status",
            description = "Creates a new provider status and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider status created", content = @Content(schema = @Schema(implementation = ProviderStatusDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderStatusDTO>> create(
            @Parameter(description = "Provider status to create", required = true)
            @Valid @RequestBody ProviderStatusDTO providerStatusDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerStatusService.create(providerStatusDTO));
    }

    /**
     * PUT /api/v1/provider-statuses/:id : Update an existing provider status
     *
     * @param id the ID of the provider status to update
     * @param providerStatusDTO the provider status to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider status in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing provider status",
            description = "Updates an existing provider status and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider status updated", content = @Content(schema = @Schema(implementation = ProviderStatusDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider status not found")
            }
    )
    public ResponseEntity<Mono<ProviderStatusDTO>> update(
            @Parameter(description = "ID of the provider status to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Provider status to update", required = true)
            @Valid @RequestBody ProviderStatusDTO providerStatusDTO) {
        return ResponseEntity.ok(providerStatusService.update(id, providerStatusDTO));
    }

    /**
     * DELETE /api/v1/provider-statuses/:id : Delete a provider status
     *
     * @param id the ID of the provider status to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a provider status",
            description = "Deletes a provider status",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider status deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider status not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider status to delete", required = true)
            @PathVariable Long id) {
        return providerStatusService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
