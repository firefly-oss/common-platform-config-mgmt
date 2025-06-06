package com.catalis.common.config.web.controllers;

import com.catalis.common.config.core.services.ProviderContractService;
import com.catalis.common.config.interfaces.dtos.ProviderContractDTO;
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
 * REST controller for managing provider contracts
 */
@RestController
@RequestMapping("/api/v1/provider-contracts")
@RequiredArgsConstructor
@Tag(name = "Provider Contracts", description = "API for managing provider contracts")
public class ProviderContractController {

    private final ProviderContractService providerContractService;

    /**
     * GET /api/v1/provider-contracts/:id : Get a provider contract by ID
     *
     * @param id the ID of the provider contract to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider contract in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderContractById",
            summary = "Get a provider contract by ID",
            description = "Returns a provider contract based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderContractDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider contract not found")
            }
    )
    public ResponseEntity<Mono<ProviderContractDTO>> getById(
            @Parameter(description = "ID of the provider contract to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(providerContractService.getById(id));
    }

    /**
     * POST /api/v1/provider-contracts/filter : Filter provider contracts
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of provider contracts in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviderContracts",
            summary = "Filter provider contracts",
            description = "Returns a filtered list of provider contracts based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderContractDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderContractDTO> filterRequest) {
        return ResponseEntity.ok(providerContractService.filter(filterRequest));
    }

    /**
     * POST /api/v1/provider-contracts : Create a new provider contract
     *
     * @param providerContractDTO the provider contract to create
     * @return the ResponseEntity with status 201 (Created) and the new provider contract in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProviderContract",
            summary = "Create a new provider contract",
            description = "Creates a new provider contract and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider contract created", content = @Content(schema = @Schema(implementation = ProviderContractDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderContractDTO>> create(
            @Parameter(description = "Provider contract to create", required = true)
            @Valid @RequestBody ProviderContractDTO providerContractDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerContractService.create(providerContractDTO));
    }

    /**
     * PUT /api/v1/provider-contracts/:id : Update an existing provider contract
     *
     * @param id the ID of the provider contract to update
     * @param providerContractDTO the provider contract to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider contract in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProviderContract",
            summary = "Update an existing provider contract",
            description = "Updates an existing provider contract and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider contract updated", content = @Content(schema = @Schema(implementation = ProviderContractDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider contract not found")
            }
    )
    public ResponseEntity<Mono<ProviderContractDTO>> update(
            @Parameter(description = "ID of the provider contract to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Provider contract to update", required = true)
            @Valid @RequestBody ProviderContractDTO providerContractDTO) {
        return ResponseEntity.ok(providerContractService.update(id, providerContractDTO));
    }

    /**
     * DELETE /api/v1/provider-contracts/:id : Delete a provider contract
     *
     * @param id the ID of the provider contract to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderContract",
            summary = "Delete a provider contract",
            description = "Deletes a provider contract",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider contract deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider contract not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider contract to delete", required = true)
            @PathVariable Long id) {
        return providerContractService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}