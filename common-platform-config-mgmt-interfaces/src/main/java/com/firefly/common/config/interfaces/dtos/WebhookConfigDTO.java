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


package com.firefly.common.config.interfaces.dtos;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for WebhookConfig entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Webhook configuration for tenants and providers")
public class WebhookConfigDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Webhook config ID")
    private UUID id;

    @FilterableId
    @Schema(description = "Tenant ID")
    private UUID tenantId;

    @FilterableId
    @Schema(description = "Provider ID")
    private UUID providerId;

    @NotBlank(message = "Webhook name is required")
    @Size(min = 2, max = 100, message = "Webhook name must be between 2 and 100 characters")
    @Schema(description = "Webhook name", example = "Payment Notification Webhook")
    private String webhookName;

    @NotBlank(message = "Webhook URL is required")
    @Pattern(regexp = "^https?://.*", message = "Webhook URL must be a valid HTTP(S) URL")
    @Schema(description = "Webhook URL", example = "https://api.example.com/webhooks/payments")
    private String webhookUrl;

    @Schema(description = "Webhook description")
    private String description;

    @Schema(description = "Event types (JSON array)", example = "[\"payment.created\", \"payment.completed\"]")
    private String eventTypes;

    @Schema(description = "HTTP method", example = "POST")
    private String httpMethod;

    @Schema(description = "Authentication type (NONE, BASIC, BEARER, API_KEY, HMAC)", example = "BEARER")
    private String authType;

    @Schema(description = "Authentication header name", example = "Authorization")
    private String authHeaderName;

    @Schema(description = "Authentication header value", example = "Bearer token123")
    private String authHeaderValue;

    @Schema(description = "Secret key for HMAC signature")
    private String secretKey;

    @Schema(description = "Custom headers (JSON)", example = "{\"X-Custom-Header\": \"value\"}")
    private String customHeaders;

    @Min(value = 1, message = "Timeout must be at least 1 second")
    @Schema(description = "Timeout in seconds", example = "30")
    private Integer timeoutSeconds;

    @Schema(description = "Whether retry is enabled", example = "true")
    private Boolean retryEnabled;

    @Min(value = 0, message = "Max retry attempts must be non-negative")
    @Schema(description = "Maximum retry attempts", example = "3")
    private Integer maxRetryAttempts;

    @Min(value = 1, message = "Retry delay must be at least 1 second")
    @Schema(description = "Retry delay in seconds", example = "60")
    private Integer retryDelaySeconds;

    @Min(value = 1, message = "Backoff multiplier must be at least 1")
    @Schema(description = "Retry backoff multiplier", example = "2.0")
    private Double retryBackoffMultiplier;

    @Schema(description = "Whether the webhook is enabled", example = "true")
    private Boolean enabled;

    @Schema(description = "Additional metadata (JSON)")
    private String metadata;

    @Schema(description = "Whether the webhook config is active", example = "true")
    private Boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Version for optimistic locking")
    private Long version;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}

