package com.firefly.common.config.web.controllers;

import com.firefly.common.config.core.services.ProviderProcessStatusService;
import com.firefly.common.config.interfaces.dtos.ProviderProcessStatusDTO;
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
 * REST controller for managing provider process statuses
 */
@RestController
@RequestMapping("/api/v1/provider-process-statuses")
@RequiredArgsConstructor
@Tag(name = "Provider Process Statuses", description = "API for managing provider process statuses")
public class ProviderProcessStatusController {

    private final ProviderProcessStatusService providerProcessStatusService;

    /**
     * GET /api/v1/provider-process-statuses/:id : Get a provider process status by ID
     *
     * @param id the ID of the provider process status to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider process status in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderProcessStatusById",
            summary = "Get a provider process status by ID",
            description = "Returns a provider process status based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderProcessStatusDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider process status not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessStatusDTO>> getById(
            @Parameter(description = "ID of the provider process status to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(providerProcessStatusService.getById(id));
    }

    /**
     * POST /api/v1/provider-process-statuses/filter : Filter provider process statuses
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider process statuses in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviderProcessStatuses",
            summary = "Filter provider process statuses",
            description = "Returns a filtered list of provider process statuses based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderProcessStatusDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderProcessStatusDTO> filterRequest) {
        return ResponseEntity.ok(providerProcessStatusService.filter(filterRequest));
    }

    /**
     * POST /api/v1/provider-process-statuses : Create a new provider process status
     *
     * @param providerProcessStatusDTO the provider process status to create
     * @return the ResponseEntity with status 201 (Created) and the new provider process status in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProviderProcessStatus",
            summary = "Create a new provider process status",
            description = "Creates a new provider process status and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider process status created", content = @Content(schema = @Schema(implementation = ProviderProcessStatusDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderProcessStatusDTO>> create(
            @Parameter(description = "Provider process status to create", required = true)
            @Valid @RequestBody ProviderProcessStatusDTO providerProcessStatusDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerProcessStatusService.create(providerProcessStatusDTO));
    }

    /**
     * PUT /api/v1/provider-process-statuses/:id : Update an existing provider process status
     *
     * @param id the ID of the provider process status to update
     * @param providerProcessStatusDTO the provider process status to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider process status in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing provider process status",
            description = "Updates an existing provider process status and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider process status updated", content = @Content(schema = @Schema(implementation = ProviderProcessStatusDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider process status not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessStatusDTO>> update(
            @Parameter(description = "ID of the provider process status to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Provider process status to update", required = true)
            @Valid @RequestBody ProviderProcessStatusDTO providerProcessStatusDTO) {
        return ResponseEntity.ok(providerProcessStatusService.update(id, providerProcessStatusDTO));
    }

    /**
     * DELETE /api/v1/provider-process-statuses/:id : Delete a provider process status
     *
     * @param id the ID of the provider process status to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a provider process status",
            description = "Deletes a provider process status",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider process status deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider process status not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider process status to delete", required = true)
            @PathVariable UUID id) {
        return providerProcessStatusService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
