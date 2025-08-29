package com.firefly.common.config.interfaces.dtos;

import com.firefly.annotations.ValidCurrencyCode;
import com.firefly.annotations.ValidPhoneNumber;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO for Provider entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Provider information")
public class ProviderDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Provider ID")
    private Long id;

    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 50, message = "Code must be between 2 and 50 characters")
    @Schema(description = "Unique code for the provider", example = "TREEZOR")
    private String code;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Name of the provider", example = "Treezor")
    private String name;

    @Schema(description = "Description of the provider", example = "Treezor is a Banking as a Service provider")
    private String description;

    @NotNull(message = "Provider type ID is required")
    @FilterableId
    @Schema(description = "ID of the provider type")
    private Long providerTypeId;

    @Schema(description = "Provider type information")
    private ProviderTypeDTO providerType;

    @NotNull(message = "Provider status ID is required")
    @FilterableId
    @Schema(description = "ID of the provider status")
    private Long providerStatusId;

    @Schema(description = "Provider status information")
    private ProviderStatusDTO providerStatus;

    @Schema(description = "Base URL for the provider's API", example = "https://api.treezor.com")
    private String apiBaseUrl;

    @Schema(description = "URL for receiving webhooks from the provider", example = "https://webhooks.mycompany.com/treezor")
    private String webhookUrl;

    @Schema(description = "URL for receiving callbacks from the provider", example = "https://callbacks.mycompany.com/treezor")
    private String callbackUrl;

    @Schema(description = "URL for the provider's logo", example = "https://assets.mycompany.com/logos/treezor.png")
    private String logoUrl;

    @Schema(description = "URL for the provider's documentation", example = "https://docs.treezor.com")
    private String documentationUrl;

    @Schema(description = "URL for the provider's support", example = "https://support.treezor.com")
    private String supportUrl;

    @Schema(description = "Name of the primary contact person", example = "John Doe")
    private String contactName;

    @Email(message = "Contact email must be valid")
    @Schema(description = "Email of the primary contact person", example = "john.doe@treezor.com")
    private String contactEmail;

    @Schema(description = "Phone number of the primary contact person", example = "+33123456789")

    @ValidPhoneNumber
    private String contactPhone;

    @Schema(description = "Name of the technical contact person", example = "Jane Smith")
    private String technicalContactName;

    @Email(message = "Technical contact email must be valid")
    @Schema(description = "Email of the technical contact person", example = "jane.smith@treezor.com")
    private String technicalContactEmail;

    @Schema(description = "Phone number of the technical contact person", example = "+33987654321")
    @ValidPhoneNumber
    private String technicalContactPhone;

    @Schema(description = "Country ID", example = "FR")
    private Long countryId;

    @Schema(description = "Region where the provider operates", example = "Europe")
    private String region;

    @Schema(description = "Timezone of the provider", example = "Europe/Paris")
    private String timezone;

    @Schema(description = "ISO currency code used by the provider", example = "EUR")

    @ValidCurrencyCode
    private String currencyCode;

    @Schema(description = "Maximum number of requests per second allowed by the provider", example = "100")
    private Integer maxRequestsPerSecond;

    @Schema(description = "Maximum number of concurrent requests allowed by the provider", example = "50")
    private Integer maxConcurrentRequests;

    @Schema(description = "Whether the provider requires authentication", example = "true")
    private Boolean requiresAuthentication;

    @Schema(description = "Type of authentication used by the provider", example = "OAUTH2")
    private String authenticationType;

    @Schema(description = "Whether the provider supports webhooks", example = "true")
    private Boolean supportsWebhooks;

    @Schema(description = "Whether the provider supports callbacks", example = "true")
    private Boolean supportsCallbacks;

    @Schema(description = "Whether the provider supports polling", example = "false")
    private Boolean supportsPolling;

    @Schema(description = "Interval in seconds for polling the provider", example = "60")
    private Integer pollingIntervalSeconds;

    @Schema(description = "Whether the provider is active", defaultValue = "true")
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
