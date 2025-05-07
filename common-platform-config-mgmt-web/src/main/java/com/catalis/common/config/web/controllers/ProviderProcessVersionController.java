package com.catalis.common.config.web.controllers;

import com.catalis.common.config.core.services.ProviderProcessVersionService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

/**
 * REST controller for managing provider process versions
 */
@RestController
@RequestMapping("/api/v1/process-versions")
@RequiredArgsConstructor
@Tag(name = "Provider Process Versions", description = "API for managing provider process versions")
public class ProviderProcessVersionController {

    private final ProviderProcessVersionService providerProcessVersionService;

    /**
     * GET /api/v1/process-versions/:id : Get a provider process version by ID
     *
     * @param id the ID of the provider process version to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider process version in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a provider process version by ID",
            description = "Returns a provider process version based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderProcessVersionDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider process version not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessVersionDTO>> getById(
            @Parameter(description = "ID of the provider process version to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(providerProcessVersionService.getById(id));
    }

    /**
     * POST /api/v1/process-versions/filter : Filter provider process versions
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider process versions in the body
     */
    @PostMapping("/filter")
    @Operation(
            summary = "Filter provider process versions",
            description = "Returns a filtered list of provider process versions based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderProcessVersionDTO>>> filter(
            @Parameter(description = "Filter criteria", required = true)
            @RequestBody FilterRequest<ProviderProcessVersionDTO> filterRequest) {
        return ResponseEntity.ok(providerProcessVersionService.filter(filterRequest));
    }

    /**
     * POST /api/v1/process-versions : Create a new provider process version
     *
     * @param providerProcessVersionDTO the provider process version to create
     * @return the ResponseEntity with status 201 (Created) and the new provider process version in the body
     */
    @PostMapping
    @Operation(
            summary = "Create a new provider process version",
            description = "Creates a new provider process version and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider process version created", content = @Content(schema = @Schema(implementation = ProviderProcessVersionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderProcessVersionDTO>> create(
            @Parameter(description = "Provider process version to create", required = true)
            @Valid @RequestBody ProviderProcessVersionDTO providerProcessVersionDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerProcessVersionService.create(providerProcessVersionDTO));
    }

    /**
     * PUT /api/v1/process-versions/:id : Update an existing provider process version
     *
     * @param id the ID of the provider process version to update
     * @param providerProcessVersionDTO the provider process version to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider process version in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing provider process version",
            description = "Updates an existing provider process version and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider process version updated", content = @Content(schema = @Schema(implementation = ProviderProcessVersionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider process version not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessVersionDTO>> update(
            @Parameter(description = "ID of the provider process version to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Provider process version to update", required = true)
            @Valid @RequestBody ProviderProcessVersionDTO providerProcessVersionDTO) {
        return ResponseEntity.ok(providerProcessVersionService.update(id, providerProcessVersionDTO));
    }

    /**
     * DELETE /api/v1/process-versions/:id : Delete a provider process version
     *
     * @param id the ID of the provider process version to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a provider process version",
            description = "Deletes a provider process version",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider process version deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider process version not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider process version to delete", required = true)
            @PathVariable Long id) {
        return providerProcessVersionService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
