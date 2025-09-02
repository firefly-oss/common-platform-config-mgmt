package com.firefly.common.config.web.controllers;

import com.firefly.common.config.core.services.ProviderContractStatusService;
import com.firefly.common.config.interfaces.dtos.ProviderContractStatusDTO;
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
 * REST controller for managing provider contract statuses
 */
@RestController
@RequestMapping("/api/v1/provider-contract-statuses")
@RequiredArgsConstructor
@Tag(name = "Provider Contract Statuses", description = "API for managing provider contract statuses")
public class ProviderContractStatusController {

    private final ProviderContractStatusService providerContractStatusService;

    /**
     * GET /api/v1/provider-contract-statuses/:id : Get a provider contract status by ID
     *
     * @param id the ID of the provider contract status to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider contract status in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderContractStatusById",
            summary = "Get a provider contract status by ID",
            description = "Returns a provider contract status based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderContractStatusDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider contract status not found")
            }
    )
    public ResponseEntity<Mono<ProviderContractStatusDTO>> getById(
            @Parameter(description = "ID of the provider contract status to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(providerContractStatusService.getById(id));
    }

    /**
     * POST /api/v1/provider-contract-statuses/filter : Filter provider contract statuses
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider contract statuses in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviderContractStatuses",
            summary = "Filter provider contract statuses",
            description = "Returns a filtered list of provider contract statuses based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderContractStatusDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderContractStatusDTO> filterRequest) {
        return ResponseEntity.ok(providerContractStatusService.filter(filterRequest));
    }

    /**
     * POST /api/v1/provider-contract-statuses : Create a new provider contract status
     *
     * @param providerContractStatusDTO the provider contract status to create
     * @return the ResponseEntity with status 201 (Created) and the new provider contract status in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProviderContractStatus",
            summary = "Create a new provider contract status",
            description = "Creates a new provider contract status and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider contract status created", content = @Content(schema = @Schema(implementation = ProviderContractStatusDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderContractStatusDTO>> create(
            @Parameter(description = "Provider contract status to create", required = true)
            @Valid @RequestBody ProviderContractStatusDTO providerContractStatusDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerContractStatusService.create(providerContractStatusDTO));
    }

    /**
     * PUT /api/v1/provider-contract-statuses/:id : Update an existing provider contract status
     *
     * @param id the ID of the provider contract status to update
     * @param providerContractStatusDTO the provider contract status to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider contract status in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProviderContractStatus",
            summary = "Update an existing provider contract status",
            description = "Updates an existing provider contract status and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider contract status updated", content = @Content(schema = @Schema(implementation = ProviderContractStatusDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider contract status not found")
            }
    )
    public ResponseEntity<Mono<ProviderContractStatusDTO>> update(
            @Parameter(description = "ID of the provider contract status to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Provider contract status to update", required = true)
            @Valid @RequestBody ProviderContractStatusDTO providerContractStatusDTO) {
        return ResponseEntity.ok(providerContractStatusService.update(id, providerContractStatusDTO));
    }

    /**
     * DELETE /api/v1/provider-contract-statuses/:id : Delete a provider contract status
     *
     * @param id the ID of the provider contract status to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderContractStatus",
            summary = "Delete a provider contract status",
            description = "Deletes a provider contract status",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider contract status deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider contract status not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider contract status to delete", required = true)
            @PathVariable UUID id) {
        return providerContractStatusService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}