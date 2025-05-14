package com.catalis.common.config.web.controllers;

import com.catalis.common.config.core.services.ProviderProcessService;
import com.catalis.common.config.core.services.ProviderProcessVersionService;
import com.catalis.common.config.interfaces.dtos.ProviderProcessDTO;
import com.catalis.common.config.interfaces.dtos.ProviderProcessVersionDTO;
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
 * REST controller for managing provider processes and their versions
 */
@RestController
@RequestMapping("/api/v1/processes")
@RequiredArgsConstructor
@Tag(name = "Provider Processes", description = "API for managing provider processes and their versions")
public class ProviderProcessController {

    private final ProviderProcessService providerProcessService;
    private final ProviderProcessVersionService providerProcessVersionService;

    /**
     * GET /api/v1/processes/:id : Get a provider process by ID
     *
     * @param id the ID of the provider process to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider process in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderProcessById",
            summary = "Get a provider process by ID",
            description = "Returns a provider process based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderProcessDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider process not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessDTO>> getById(
            @Parameter(description = "ID of the provider process to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(providerProcessService.getById(id));
    }

    /**
     * POST /api/v1/processes/filter : Filter provider processes
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider processes in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviderProcesses",
            summary = "Filter provider processes",
            description = "Returns a filtered list of provider processes based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderProcessDTO>>> filter(
            @Parameter(description = "Filter criteria", required = true)
            @RequestBody FilterRequest<ProviderProcessDTO> filterRequest) {
        return ResponseEntity.ok(providerProcessService.filter(filterRequest));
    }

    /**
     * POST /api/v1/processes : Create a new provider process
     *
     * @param providerProcessDTO the provider process to create
     * @return the ResponseEntity with status 201 (Created) and the new provider process in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProviderProcess",
            summary = "Create a new provider process",
            description = "Creates a new provider process and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider process created", content = @Content(schema = @Schema(implementation = ProviderProcessDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderProcessDTO>> create(
            @Parameter(description = "Provider process to create", required = true)
            @Valid @RequestBody ProviderProcessDTO providerProcessDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerProcessService.create(providerProcessDTO));
    }

    /**
     * PUT /api/v1/processes/:id : Update an existing provider process
     *
     * @param id the ID of the provider process to update
     * @param providerProcessDTO the provider process to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider process in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProviderProcess",
            summary = "Update an existing provider process",
            description = "Updates an existing provider process and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider process updated", content = @Content(schema = @Schema(implementation = ProviderProcessDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider process not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessDTO>> update(
            @Parameter(description = "ID of the provider process to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Provider process to update", required = true)
            @Valid @RequestBody ProviderProcessDTO providerProcessDTO) {
        return ResponseEntity.ok(providerProcessService.update(id, providerProcessDTO));
    }

    /**
     * DELETE /api/v1/processes/:id : Delete a provider process
     *
     * @param id the ID of the provider process to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderProcess",
            summary = "Delete a provider process",
            description = "Deletes a provider process",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider process deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider process not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider process to delete", required = true)
            @PathVariable Long id) {
        return providerProcessService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    // Nested resource: Process Versions

    /**
     * POST /api/v1/processes/:processId/versions/filter : Filter versions for a process
     *
     * @param processId the ID of the process
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of versions in the body
     */
    @PostMapping("/{processId}/versions/filter")
    @Operation(
            operationId = "filterProviderProcessVersions",
            summary = "Filter versions for a process",
            description = "Returns a filtered list of versions for a specific process",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderProcessVersionDTO>>> filterVersions(
            @Parameter(description = "ID of the process", required = true)
            @PathVariable Long processId,
            @ParameterObject @ModelAttribute FilterRequest<ProviderProcessVersionDTO> filterRequest) {

        filterRequest.getFilters().setProviderProcessId(processId);
        return ResponseEntity.ok(providerProcessVersionService.filter(filterRequest));
    }

    /**
     * POST /api/v1/processes/:processId/versions : Create a new version for a process
     *
     * @param processId the ID of the process
     * @param versionDTO the version to create
     * @return the ResponseEntity with status 201 (Created) and the new version in the body
     */
    @PostMapping("/{processId}/versions")
    @Operation(
            operationId = "createProviderProcessVersion",
            summary = "Create a new version for a process",
            description = "Creates a new version for a specific process and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Version created", content = @Content(schema = @Schema(implementation = ProviderProcessVersionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Process not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessVersionDTO>> createVersion(
            @Parameter(description = "ID of the process", required = true)
            @PathVariable Long processId,
            @Parameter(description = "Version to create", required = true)
            @Valid @RequestBody ProviderProcessVersionDTO versionDTO) {
        versionDTO.setProviderProcessId(processId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerProcessVersionService.create(versionDTO));
    }

    /**
     * GET /api/v1/processes/:processId/versions/:id : Get a version by ID for a process
     *
     * @param processId the ID of the process
     * @param id the ID of the version to retrieve
     * @return the ResponseEntity with status 200 (OK) and the version in the body, or status 404 (Not Found)
     */
    @GetMapping("/{processId}/versions/{id}")
    @Operation(
            operationId = "getProviderProcessVersionById",
            summary = "Get a version by ID for a process",
            description = "Returns a version for a specific process based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderProcessVersionDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Version not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessVersionDTO>> getVersionById(
            @Parameter(description = "ID of the process", required = true)
            @PathVariable Long processId,
            @Parameter(description = "ID of the version to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(providerProcessVersionService.getById(id)
                .filter(version -> version.getProviderProcessId().equals(processId)));
    }

    /**
     * PUT /api/v1/processes/:processId/versions/:id : Update a version for a process
     *
     * @param processId the ID of the process
     * @param id the ID of the version to update
     * @param versionDTO the version to update
     * @return the ResponseEntity with status 200 (OK) and the updated version in the body, or status 404 (Not Found)
     */
    @PutMapping("/{processId}/versions/{id}")
    @Operation(
            operationId = "updateProviderProcessVersion",
            summary = "Update a version for a process",
            description = "Updates a version for a specific process and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Version updated", content = @Content(schema = @Schema(implementation = ProviderProcessVersionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Version not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessVersionDTO>> updateVersion(
            @Parameter(description = "ID of the process", required = true)
            @PathVariable Long processId,
            @Parameter(description = "ID of the version to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Version to update", required = true)
            @Valid @RequestBody ProviderProcessVersionDTO versionDTO) {
        versionDTO.setProviderProcessId(processId);
        return ResponseEntity.ok(providerProcessVersionService.update(id, versionDTO));
    }

    /**
     * DELETE /api/v1/processes/:processId/versions/:id : Delete a version for a process
     *
     * @param processId the ID of the process
     * @param id the ID of the version to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{processId}/versions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderProcessVersion",
            summary = "Delete a version for a process",
            description = "Deletes a version for a specific process",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Version deleted"),
                    @ApiResponse(responseCode = "404", description = "Version not found")
            }
    )
    public Mono<ResponseEntity<Void>> deleteVersion(
            @Parameter(description = "ID of the process", required = true)
            @PathVariable Long processId,
            @Parameter(description = "ID of the version to delete", required = true)
            @PathVariable Long id) {
        return providerProcessVersionService.getById(id)
                .filter(version -> version.getProviderProcessId().equals(processId))
                .flatMap(version -> providerProcessVersionService.delete(id))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
