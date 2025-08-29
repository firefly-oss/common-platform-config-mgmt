package com.firefly.common.config.web.controllers;

import com.firefly.common.config.core.services.ProviderMappingStatusService;
import com.firefly.common.config.interfaces.dtos.ProviderMappingStatusDTO;
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
 * REST controller for managing provider mapping statuses
 */
@RestController
@RequestMapping("/api/v1/provider-mapping-statuses")
@RequiredArgsConstructor
@Tag(name = "Provider Mapping Statuses", description = "API for managing provider mapping statuses")
public class ProviderMappingStatusController {

    private final ProviderMappingStatusService providerMappingStatusService;

    /**
     * GET /api/v1/provider-mapping-statuses/:id : Get a provider mapping status by ID
     *
     * @param id the ID of the provider mapping status to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider mapping status in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderMappingStatusById",
            summary = "Get a provider mapping status by ID",
            description = "Returns a provider mapping status based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderMappingStatusDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider mapping status not found")
            }
    )
    public ResponseEntity<Mono<ProviderMappingStatusDTO>> getById(
            @Parameter(description = "ID of the provider mapping status to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(providerMappingStatusService.getById(id));
    }

    /**
     * POST /api/v1/provider-mapping-statuses/filter : Filter provider mapping statuses
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider mapping statuses in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviderMappingStatuses",
            summary = "Filter provider mapping statuses",
            description = "Returns a filtered list of provider mapping statuses based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderMappingStatusDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderMappingStatusDTO> filterRequest) {
        return ResponseEntity.ok(providerMappingStatusService.filter(filterRequest));
    }

    /**
     * POST /api/v1/provider-mapping-statuses : Create a new provider mapping status
     *
     * @param providerMappingStatusDTO the provider mapping status to create
     * @return the ResponseEntity with status 201 (Created) and the new provider mapping status in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProviderMappingStatus",
            summary = "Create a new provider mapping status",
            description = "Creates a new provider mapping status and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider mapping status created", content = @Content(schema = @Schema(implementation = ProviderMappingStatusDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderMappingStatusDTO>> create(
            @Parameter(description = "Provider mapping status to create", required = true)
            @Valid @RequestBody ProviderMappingStatusDTO providerMappingStatusDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerMappingStatusService.create(providerMappingStatusDTO));
    }

    /**
     * PUT /api/v1/provider-mapping-statuses/:id : Update an existing provider mapping status
     *
     * @param id the ID of the provider mapping status to update
     * @param providerMappingStatusDTO the provider mapping status to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider mapping status in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProviderMappingStatus",
            summary = "Update an existing provider mapping status",
            description = "Updates an existing provider mapping status and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider mapping status updated", content = @Content(schema = @Schema(implementation = ProviderMappingStatusDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider mapping status not found")
            }
    )
    public ResponseEntity<Mono<ProviderMappingStatusDTO>> update(
            @Parameter(description = "ID of the provider mapping status to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Provider mapping status to update", required = true)
            @Valid @RequestBody ProviderMappingStatusDTO providerMappingStatusDTO) {
        return ResponseEntity.ok(providerMappingStatusService.update(id, providerMappingStatusDTO));
    }

    /**
     * DELETE /api/v1/provider-mapping-statuses/:id : Delete a provider mapping status
     *
     * @param id the ID of the provider mapping status to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderMappingStatus",
            summary = "Delete a provider mapping status",
            description = "Deletes a provider mapping status",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider mapping status deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider mapping status not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider mapping status to delete", required = true)
            @PathVariable Long id) {
        return providerMappingStatusService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
