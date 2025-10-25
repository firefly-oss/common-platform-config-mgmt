/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.common.config.web.controllers;

import com.firefly.common.config.core.services.WebhookConfigService;
import com.firefly.common.config.interfaces.dtos.WebhookConfigDTO;
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

@RestController
@RequestMapping("/api/v1/webhook-configs")
@RequiredArgsConstructor
@Tag(name = "Webhook Configurations", description = "API for managing webhook configurations with authentication, retry logic, and event filtering")
public class WebhookConfigController {

    private final WebhookConfigService webhookConfigService;

    @GetMapping("/{id}")
    @Operation(
            operationId = "getWebhookConfigById",
            summary = "Get webhook configuration by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = WebhookConfigDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Webhook configuration not found")
            }
    )
    public ResponseEntity<Mono<WebhookConfigDTO>> getById(
            @Parameter(description = "ID of the webhook configuration to retrieve", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(webhookConfigService.getById(id));
    }

    @PostMapping("/filter")
    @Operation(
            operationId = "filterWebhookConfigs",
            summary = "Filter webhook configurations",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            }
    )
    public ResponseEntity<Mono<PaginationResponse<WebhookConfigDTO>>> filter(
            @ParameterObject @ModelAttribute FilterRequest<WebhookConfigDTO> filterRequest) {
        return ResponseEntity.ok(webhookConfigService.filter(filterRequest));
    }

    @PostMapping
    @Operation(
            operationId = "createWebhookConfig",
            summary = "Create a new webhook configuration",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Webhook configuration created", content = @Content(schema = @Schema(implementation = WebhookConfigDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<WebhookConfigDTO>> create(
            @Parameter(description = "Webhook configuration to create", required = true)
            @Valid @RequestBody WebhookConfigDTO webhookConfigDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(webhookConfigService.create(webhookConfigDTO));
    }

    @PutMapping("/{id}")
    @Operation(
            operationId = "updateWebhookConfig",
            summary = "Update an existing webhook configuration",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Webhook configuration updated", content = @Content(schema = @Schema(implementation = WebhookConfigDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Webhook configuration not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<Mono<WebhookConfigDTO>> update(
            @Parameter(description = "ID of the webhook configuration to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Updated webhook configuration data", required = true)
            @Valid @RequestBody WebhookConfigDTO webhookConfigDTO) {
        return ResponseEntity.ok(webhookConfigService.update(id, webhookConfigDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            operationId = "deleteWebhookConfig",
            summary = "Delete a webhook configuration",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Webhook configuration deleted"),
                    @ApiResponse(responseCode = "404", description = "Webhook configuration not found")
            }
    )
    public ResponseEntity<Mono<Void>> delete(
            @Parameter(description = "ID of the webhook configuration to delete", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(webhookConfigService.delete(id));
    }
}

