package com.firefly.common.config.web.controllers;

import com.firefly.common.config.core.services.ProviderConfigService;
import com.firefly.common.config.core.services.ProviderProcessService;
import com.firefly.common.config.core.services.ProviderService;
import com.firefly.common.config.interfaces.dtos.ProviderConfigDTO;
import com.firefly.common.config.interfaces.dtos.ProviderDTO;
import com.firefly.common.config.interfaces.dtos.ProviderProcessDTO;
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
 * REST controller for managing providers and their nested resources
 */
@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
@Tag(name = "Providers", description = "API for managing providers and their configurations and processes")
public class ProviderController {

    private final ProviderService providerService;
    private final ProviderConfigService providerConfigService;
    private final ProviderProcessService providerProcessService;

    /**
     * GET /api/v1/providers/:id : Get a provider by ID
     *
     * @param id the ID of the provider to retrieve
     * @return the ResponseEntity with status 200 (OK) and the provider in the body, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Operation(
            operationId = "getProviderById",
            summary = "Get a provider by ID",
            description = "Returns a provider based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    public ResponseEntity<Mono<ProviderDTO>> getById(
            @Parameter(description = "ID of the provider to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(providerService.getById(id));
    }

    /**
     * POST /api/v1/providers/filter : Filter providers
     *
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of providers in the body
     */
    @PostMapping("/filter")
    @Operation(
            operationId = "filterProviders",
            summary = "Filter providers",
            description = "Returns a filtered list of providers based on criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<ProviderDTO> filterRequest) {
        return ResponseEntity.ok(providerService.filter(filterRequest));
    }

    /**
     * POST /api/v1/providers : Create a new provider
     *
     * @param providerDTO the provider to create
     * @return the ResponseEntity with status 201 (Created) and the new provider in the body
     */
    @PostMapping
    @Operation(
            operationId = "createProvider",
            summary = "Create a new provider",
            description = "Creates a new provider and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider created", content = @Content(schema = @Schema(implementation = ProviderDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<ProviderDTO>> create(
            @Parameter(description = "Provider to create", required = true)
            @Valid @RequestBody ProviderDTO providerDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerService.create(providerDTO));
    }

    /**
     * PUT /api/v1/providers/:id : Update an existing provider
     *
     * @param id the ID of the provider to update
     * @param providerDTO the provider to update
     * @return the ResponseEntity with status 200 (OK) and the updated provider in the body, or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Operation(
            operationId = "updateProvider",
            summary = "Update an existing provider",
            description = "Updates an existing provider and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider updated", content = @Content(schema = @Schema(implementation = ProviderDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    public ResponseEntity<Mono<ProviderDTO>> update(
            @Parameter(description = "ID of the provider to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Provider to update", required = true)
            @Valid @RequestBody ProviderDTO providerDTO) {
        return ResponseEntity.ok(providerService.update(id, providerDTO));
    }

    /**
     * DELETE /api/v1/providers/:id : Delete a provider
     *
     * @param id the ID of the provider to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProvider",
            summary = "Delete a provider",
            description = "Deletes a provider",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Provider deleted"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the provider to delete", required = true)
            @PathVariable UUID id) {
        return providerService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    // Nested resource: Provider Configs

    /**
     * GET /api/v1/providers/:providerId/configs : Get all configurations for a provider
     *
     * @param providerId the ID of the provider
     * @return the ResponseEntity with status 200 (OK) and the list of configurations in the body
     */
    @PostMapping("/{providerId}/configs/filter")
    @Operation(
            operationId = "filterProviderConfigs",
            summary = "Filter configurations for a provider",
            description = "Returns a filtered list of configurations for a specific provider",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderConfigDTO>>> filterConfigs(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @ParameterObject @ModelAttribute FilterRequest<ProviderConfigDTO> filterRequest) {

        filterRequest.getFilters().setProviderId(providerId);
        return ResponseEntity.ok(providerConfigService.filter(filterRequest));
    }

    /**
     * POST /api/v1/providers/:providerId/configs : Create a new configuration for a provider
     *
     * @param providerId the ID of the provider
     * @param providerConfigDTO the configuration to create
     * @return the ResponseEntity with status 201 (Created) and the new configuration in the body
     */
    @PostMapping("/{providerId}/configs")
    @Operation(
            operationId = "createProviderConfig",
            summary = "Create a new configuration for a provider",
            description = "Creates a new configuration for a specific provider and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Configuration created", content = @Content(schema = @Schema(implementation = ProviderConfigDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    public ResponseEntity<Mono<ProviderConfigDTO>> createConfig(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @Parameter(description = "Configuration to create", required = true)
            @Valid @RequestBody ProviderConfigDTO providerConfigDTO) {
        providerConfigDTO.setProviderId(providerId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerConfigService.create(providerConfigDTO));
    }

    /**
     * GET /api/v1/providers/:providerId/configs/:id : Get a configuration by ID for a provider
     *
     * @param providerId the ID of the provider
     * @param id the ID of the configuration to retrieve
     * @return the ResponseEntity with status 200 (OK) and the configuration in the body, or status 404 (Not Found)
     */
    @GetMapping("/{providerId}/configs/{id}")
    @Operation(
            operationId = "getProviderConfigById",
            summary = "Get a configuration by ID for a provider",
            description = "Returns a configuration for a specific provider based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderConfigDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Configuration not found")
            }
    )
    public ResponseEntity<Mono<ProviderConfigDTO>> getConfigById(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @Parameter(description = "ID of the configuration to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(providerConfigService.getById(id)
                .filter(config -> config.getProviderId().equals(providerId)));
    }

    /**
     * PUT /api/v1/providers/:providerId/configs/:id : Update a configuration for a provider
     *
     * @param providerId the ID of the provider
     * @param id the ID of the configuration to update
     * @param providerConfigDTO the configuration to update
     * @return the ResponseEntity with status 200 (OK) and the updated configuration in the body, or status 404 (Not Found)
     */
    @PutMapping("/{providerId}/configs/{id}")
    @Operation(
            operationId = "updateProviderConfig",
            summary = "Update a configuration for a provider",
            description = "Updates a configuration for a specific provider and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Configuration updated", content = @Content(schema = @Schema(implementation = ProviderConfigDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Configuration not found")
            }
    )
    public ResponseEntity<Mono<ProviderConfigDTO>> updateConfig(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @Parameter(description = "ID of the configuration to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Configuration to update", required = true)
            @Valid @RequestBody ProviderConfigDTO providerConfigDTO) {
        providerConfigDTO.setProviderId(providerId);
        return ResponseEntity.ok(providerConfigService.update(id, providerConfigDTO));
    }

    /**
     * DELETE /api/v1/providers/:providerId/configs/:id : Delete a configuration for a provider
     *
     * @param providerId the ID of the provider
     * @param id the ID of the configuration to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{providerId}/configs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderConfig",
            summary = "Delete a configuration for a provider",
            description = "Deletes a configuration for a specific provider",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Configuration deleted"),
                    @ApiResponse(responseCode = "404", description = "Configuration not found")
            }
    )
    public Mono<ResponseEntity<Void>> deleteConfig(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @Parameter(description = "ID of the configuration to delete", required = true)
            @PathVariable UUID id) {
        return providerConfigService.getById(id)
                .filter(config -> config.getProviderId().equals(providerId))
                .flatMap(config -> providerConfigService.delete(id))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Nested resource: Provider Processes

    /**
     * POST /api/v1/providers/:providerId/processes/filter : Filter processes for a provider
     *
     * @param providerId the ID of the provider
     * @param filterRequest the filter criteria
     * @return the ResponseEntity with status 200 (OK) and the list of processes in the body
     */
    @PostMapping("/{providerId}/processes/filter")
    @Operation(
            operationId = "filterProviderProcesses",
            summary = "Filter processes for a provider",
            description = "Returns a filtered list of processes for a specific provider",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<ProviderProcessDTO>>> filterProcesses(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @RequestBody FilterRequest<ProviderProcessDTO> filterRequest) {

        filterRequest.getFilters().setProviderId(providerId);
        return ResponseEntity.ok(providerProcessService.filter(filterRequest));
    }

    /**
     * POST /api/v1/providers/:providerId/processes : Create a new process for a provider
     *
     * @param providerId the ID of the provider
     * @param providerProcessDTO the process to create
     * @return the ResponseEntity with status 201 (Created) and the new process in the body
     */
    @PostMapping("/{providerId}/processes")
    @Operation(
            operationId = "createProviderProcess",
            summary = "Create a new process for a provider",
            description = "Creates a new process for a specific provider and returns it",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Process created", content = @Content(schema = @Schema(implementation = ProviderProcessDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessDTO>> createProcess(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @Parameter(description = "Process to create", required = true)
            @Valid @RequestBody ProviderProcessDTO providerProcessDTO) {
        providerProcessDTO.setProviderId(providerId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerProcessService.create(providerProcessDTO));
    }

    /**
     * GET /api/v1/providers/:providerId/processes/:id : Get a process by ID for a provider
     *
     * @param providerId the ID of the provider
     * @param id the ID of the process to retrieve
     * @return the ResponseEntity with status 200 (OK) and the process in the body, or status 404 (Not Found)
     */
    @GetMapping("/{providerId}/processes/{id}")
    @Operation(
            operationId = "getProviderProcessById",
            summary = "Get a process by ID for a provider",
            description = "Returns a process for a specific provider based on the ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProviderProcessDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Process not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessDTO>> getProcessById(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @Parameter(description = "ID of the process to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(providerProcessService.getById(id)
                .filter(process -> process.getProviderId().equals(providerId)));
    }

    /**
     * PUT /api/v1/providers/:providerId/processes/:id : Update a process for a provider
     *
     * @param providerId the ID of the provider
     * @param id the ID of the process to update
     * @param providerProcessDTO the process to update
     * @return the ResponseEntity with status 200 (OK) and the updated process in the body, or status 404 (Not Found)
     */
    @PutMapping("/{providerId}/processes/{id}")
    @Operation(
            operationId = "updateProviderProcess",
            summary = "Update a process for a provider",
            description = "Updates a process for a specific provider and returns it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Process updated", content = @Content(schema = @Schema(implementation = ProviderProcessDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Process not found")
            }
    )
    public ResponseEntity<Mono<ProviderProcessDTO>> updateProcess(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @Parameter(description = "ID of the process to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Process to update", required = true)
            @Valid @RequestBody ProviderProcessDTO providerProcessDTO) {
        providerProcessDTO.setProviderId(providerId);
        return ResponseEntity.ok(providerProcessService.update(id, providerProcessDTO));
    }

    /**
     * DELETE /api/v1/providers/:providerId/processes/:id : Delete a process for a provider
     *
     * @param providerId the ID of the provider
     * @param id the ID of the process to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{providerId}/processes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            operationId = "deleteProviderProcess",
            summary = "Delete a process for a provider",
            description = "Deletes a process for a specific provider",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Process deleted"),
                    @ApiResponse(responseCode = "404", description = "Process not found")
            }
    )
    public Mono<ResponseEntity<Void>> deleteProcess(
            @Parameter(description = "ID of the provider", required = true)
            @PathVariable UUID providerId,
            @Parameter(description = "ID of the process to delete", required = true)
            @PathVariable UUID id) {
        return providerProcessService.getById(id)
                .filter(process -> process.getProviderId().equals(providerId))
                .flatMap(process -> providerProcessService.delete(id))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
