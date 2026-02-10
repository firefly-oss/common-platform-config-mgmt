# Security Vault Integration Guide

**Integration with common-platform-security-vault for secure credential management**

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Configuring Secret Parameters](#configuring-secret-parameters)
- [Configuring Secret Environment Configs](#configuring-secret-environment-configs)
- [Usage Examples](#usage-examples)
- [Migrating Existing Data](#migrating-existing-data)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)

---

## Overview

This configuration management service **DOES NOT store credentials directly**. Instead, it stores **references** (credential IDs) to credentials that are securely managed by the `common-platform-security-vault` microservice.

### Why This Architecture?

**Separation of Concerns**: Config management vs. Secret management  
**Enhanced Security**: Credentials are encrypted with AES-256-GCM in the vault  
**Credential Rotation**: Change credentials without modifying configurations  
**Centralized Auditing**: All credential access is logged in the vault  
**Regulatory Compliance**: Facilitates compliance with PCI-DSS, SOC2, ISO27001  
**Access Control**: Fine-grained access control with IP, service, and environment restrictions

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    Consumer Microservice                         │
│  (e.g., Payment Service, KYC Service, Card Issuing Service)     │
└────────────────┬────────────────────────────────────────────────┘
                 │
                 │ 1. Get configuration
                 ↓
┌─────────────────────────────────────────────────────────────────┐
│         common-platform-config-mgmt (THIS SERVICE)              │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ ProviderParameter / EnvironmentConfig                    │  │
│  │                                                           │  │
│  │  - parameterName: "api_key"                              │  │
│  │  - isSecret: true                                        │  │
│  │  - credentialVaultId: "credential-uuid-from-vault"       │  │
│  │  - parameterValue: null  ← NOT USED FOR SECRETS          │  │
│  └──────────────────────────────────────────────────────────┘  │
└────────────────┬────────────────────────────────────────────────┘
                 │
                 │ 2. Returns credentialVaultId (UUID)
                 ↓
┌─────────────────────────────────────────────────────────────────┐
│                    Consumer Microservice                         │
│                                                                  │
│  if (parameter.isSecret) {                                      │
│    String decryptedValue = vaultClient.decryptCredential(       │
│      UUID.fromString(parameter.credentialVaultId),              │
│      accessRequest                                              │
│    );                                                            │
│  }                                                               │
└────────────────┬────────────────────────────────────────────────┘
                 │
                 │ 3. Decrypt credential using UUID
                 │    POST /api/v1/credentials/{id}/decrypt
                 ↓
┌─────────────────────────────────────────────────────────────────┐
│         common-platform-security-vault                           │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ Credential                                                │  │
│  │                                                           │  │
│  │  - id: UUID                                              │  │
│  │  - code: "STRIPE_API_KEY_PROD"                           │  │
│  │  - encryptedValue: "..." (AES-256-GCM encrypted)         │  │
│  │  - credentialTypeId: UUID (API_KEY, PASSWORD, etc.)      │  │
│  │  - environmentTypeId: UUID (PRODUCTION, STAGING, etc.)   │  │
│  │  - allowedServices: "payment-service,billing-service"    │  │
│  │  - allowedIps: "10.0.0.0/8"                              │  │
│  │  - auditAllAccess: true                                  │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

---

## Configuring Secret Parameters

### Relevant Fields

| Field | Type | Description | Usage |
|-------|------|-------------|-------|
| `parameterName` | String | Parameter name | Always required |
| `isSecret` | Boolean | Indicates if it's a secret | `true` for credentials |
| `parameterValue` | String | Direct value | Only when `isSecret=false` |
| `credentialVaultId` | String | Vault credential UUID | Only when `isSecret=true` |

### Example: Non-Secret Parameter

```json
{
  "providerId": "uuid-stripe-provider",
  "tenantId": "uuid-acme-bank",
  "parameterName": "api_timeout_ms",
  "parameterValue": "30000",
  "parameterType": "INTEGER",
  "isSecret": false,
  "credentialVaultId": null,
  "description": "API call timeout in milliseconds"
}
```

### Example: Secret Parameter (API Key)

```json
{
  "providerId": "uuid-stripe-provider",
  "tenantId": "uuid-acme-bank",
  "parameterName": "api_key",
  "parameterValue": null,
  "parameterType": "STRING",
  "isSecret": true,
  "credentialVaultId": "550e8400-e29b-41d4-a716-446655440000",
  "description": "Stripe API Key for production"
}
```

**Note**: The `credentialVaultId` is the UUID of the credential stored in the security-vault, NOT a custom string identifier.

---

## Configuring Secret Environment Configs

### Example: Database Password

```json
{
  "tenantId": "uuid-acme-bank",
  "environmentName": "production",
  "configKey": "database.password",
  "configValue": null,
  "configType": "STRING",
  "isSecret": true,
  "credentialVaultId": "660e8400-e29b-41d4-a716-446655440001",
  "category": "database",
  "description": "Database password for production"
}
```

### Example: Encryption Key

```json
{
  "tenantId": "uuid-acme-bank",
  "environmentName": "production",
  "configKey": "encryption.master.key",
  "configValue": null,
  "configType": "STRING",
  "isSecret": true,
  "credentialVaultId": "770e8400-e29b-41d4-a716-446655440002",
  "category": "security",
  "description": "Master encryption key for sensitive data"
}
```

---

## Usage Examples

### Example 1: Creating a Credential in the Vault

First, you need to create the credential in the security-vault:

```java
// 1. Create credential in security-vault
CredentialDTO credential = CredentialDTO.builder()
    .code("STRIPE_API_KEY_PROD")
    .name("Stripe Production API Key")
    .description("API key for Stripe payment gateway in production")
    .credentialTypeId(apiKeyTypeId)  // UUID of API_KEY type
    .credentialStatusId(activeStatusId)  // UUID of ACTIVE status
    .environmentTypeId(productionEnvId)  // UUID of PRODUCTION environment
    .tenantId(tenantId)
    .encryptedValue("sk_live_abc123xyz789...")  // Will be encrypted by vault
    .allowedServices("payment-service,billing-service")
    .allowedIps("10.0.0.0/8")
    .auditAllAccess(true)
    .active(true)
    .build();

CredentialDTO created = vaultClient.createCredential(credential).block();
UUID credentialId = created.getId();  // This is what you store in config-mgmt
```

### Example 2: Storing the Reference in Config Management

```java
// 2. Create parameter in config-mgmt with reference to vault credential
ProviderParameterDTO parameter = ProviderParameterDTO.builder()
    .providerId(stripeProviderId)
    .tenantId(tenantId)
    .parameterName("api_key")
    .isSecret(true)
    .credentialVaultId(credentialId.toString())  // UUID from vault
    .parameterValue(null)  // Must be null for secrets
    .parameterType("STRING")
    .description("Stripe API Key")
    .build();

configClient.createProviderParameter(parameter).block();
```

### Example 3: Consuming a Secret Parameter

```java
@Service
@RequiredArgsConstructor
public class StripePaymentService {
    
    private final ConfigManagementClient configClient;
    private final SecurityVaultClient vaultClient;
    
    public Mono<String> getStripeApiKey(UUID tenantId) {
        // 1. Get parameter configuration
        return configClient.getProviderParameter(
            STRIPE_PROVIDER_ID,
            tenantId,
            "api_key"
        )
        .flatMap(parameter -> {
            // 2. Check if it's a secret
            if (Boolean.TRUE.equals(parameter.getIsSecret())) {
                // 3. Decrypt credential from vault
                UUID credentialId = UUID.fromString(parameter.getCredentialVaultId());
                
                // Build access request for audit trail
                AccessRequest accessRequest = AccessRequest.builder()
                    .userId(getCurrentUserId())
                    .serviceName("payment-service")
                    .ipAddress(getCurrentIpAddress())
                    .environment("production")
                    .reason("Processing payment transaction")
                    .build();
                
                return vaultClient.decryptCredential(credentialId, accessRequest);
            } else {
                // 4. Use direct value if not secret
                return Mono.just(parameter.getParameterValue());
            }
        });
    }
}
```

### Example 4: Generic Helper Method

```java
@Component
@RequiredArgsConstructor
public class SecureConfigHelper {
    
    private final SecurityVaultClient vaultClient;
    
    /**
     * Gets the value of a parameter, automatically handling secrets
     */
    public Mono<String> getParameterValue(
            ProviderParameterDTO parameter,
            AccessRequest accessRequest) {
        
        if (Boolean.TRUE.equals(parameter.getIsSecret())) {
            // Validate
            if (parameter.getCredentialVaultId() == null) {
                return Mono.error(new IllegalStateException(
                    "Parameter is marked as secret but credentialVaultId is null"
                ));
            }
            
            // Decrypt from vault
            UUID credentialId = UUID.fromString(parameter.getCredentialVaultId());
            return vaultClient.decryptCredential(credentialId, accessRequest);
        } else {
            // Use direct value
            return Mono.justOrEmpty(parameter.getParameterValue());
        }
    }
}
```

---

## Migrating Existing Data

If you already have parameters with credentials stored directly in `parameterValue`, follow these steps:

### Step 1: Identify Secret Parameters

```sql
-- Find all parameters marked as secrets with direct values
SELECT id, provider_id, tenant_id, parameter_name, parameter_value
FROM provider_parameters
WHERE is_secret = true
  AND parameter_value IS NOT NULL;
```

### Step 2: Create Credentials in Vault

For each credential found:

```java
CredentialDTO credential = CredentialDTO.builder()
    .code(generateCode(param))  // e.g., "STRIPE_API_KEY_ACME_PROD"
    .name(generateName(param))
    .description(param.getDescription())
    .credentialTypeId(determineTypeId(param))
    .credentialStatusId(activeStatusId)
    .environmentTypeId(determineEnvId(param))
    .tenantId(param.getTenantId())
    .encryptedValue(param.getParameterValue())  // Existing value
    .active(true)
    .build();

CredentialDTO created = vaultClient.createCredential(credential).block();
```

### Step 3: Update Parameter with Vault Reference

```java
param.setCredentialVaultId(created.getId().toString());
param.setParameterValue(null);  // Clear the direct value

configClient.updateProviderParameter(param.getId(), param).block();
```

---

## Best Practices

### 1. Credential ID Format

The `credentialVaultId` field stores the **UUID** of the credential in the vault:

```
CORRECT: "550e8400-e29b-41d4-a716-446655440000"
WRONG:   "vault-cred-stripe-api-key-prod"
```

### 2. Never Log Credentials

```java
// BAD
log.info("API Key: {}", apiKey);

// GOOD
log.info("API Key retrieved successfully");
```

### 3. Always Provide Access Context

```java
AccessRequest accessRequest = AccessRequest.builder()
    .userId(userId)
    .serviceName(serviceName)
    .ipAddress(ipAddress)
    .environment(environment)
    .reason("Specific reason for access")
    .build();
```

### 4. Handle Errors Gracefully

```java
return vaultClient.decryptCredential(credentialId, accessRequest)
    .onErrorResume(SecurityException.class, e -> {
        log.error("Access denied to credential: {}", credentialId, e);
        return Mono.error(new UnauthorizedException("Access denied"));
    })
    .onErrorResume(NotFoundException.class, e -> {
        log.error("Credential not found: {}", credentialId, e);
        return Mono.error(new ConfigurationException("Invalid credential reference"));
    });
```

---

## Troubleshooting

### Error: "credentialVaultId is null but isSecret is true"

**Cause**: Parameter marked as secret but no vault reference

**Solution**: Create credential in vault first, then set the UUID

### Error: "Credential not found in vault"

**Cause**: The UUID in `credentialVaultId` doesn't exist in the vault

**Solution**:
1. Verify the credential exists: `GET /api/v1/credentials/{id}`
2. Check if it was deleted or deactivated
3. Verify tenant access permissions

### Error: "Access denied"

**Cause**: Access control restrictions in the vault

**Solution**:
1. Check `allowedServices` - ensure your service is listed
2. Check `allowedIps` - ensure your IP is in the allowed range
3. Check `allowedEnvironments` - ensure you're in the correct environment
4. Verify `requireApprovalForAccess` is not enabled without approval

---

## API Reference

### Security Vault Endpoints

- `POST /api/v1/credentials` - Create a new credential
- `GET /api/v1/credentials/{id}` - Get credential metadata (not decrypted value)
- `POST /api/v1/credentials/{id}/decrypt` - Decrypt and retrieve credential value
- `PUT /api/v1/credentials/{id}` - Update credential
- `DELETE /api/v1/credentials/{id}` - Delete credential

### Config Management Endpoints

- `POST /api/v1/provider-parameters` - Create provider parameter
- `GET /api/v1/provider-parameters/{id}` - Get provider parameter
- `POST /api/v1/provider-parameters/filter` - Filter/search provider parameters
- `PUT /api/v1/provider-parameters/{id}` - Update provider parameter
- `DELETE /api/v1/provider-parameters/{id}` - Delete provider parameter

---

## Additional Resources

- [Security Vault README](../../common-platform-security-vault/README.md)
- [Security Vault API Documentation](http://localhost:8081/swagger-ui.html)
- [Config Management API Documentation](http://localhost:8080/swagger-ui.html)

