# Provider Parameter Configuration

**Complete guide to dynamic configuration management in Firefly**

---

## 📋 Table of Contents

- [Overview](#overview)
- [What are Provider Parameters?](#what-are-provider-parameters)
- [Parameter Hierarchy](#parameter-hierarchy)
- [Parameter Types](#parameter-types)
- [Parameter Categories](#parameter-categories)
- [Parameter Configuration](#parameter-configuration)
- [Security and Encryption](#security-and-encryption)
- [Validation and Constraints](#validation-and-constraints)
- [API Reference](#api-reference)
- [Best Practices](#best-practices)
- [Use Cases](#use-cases)

---

## Overview

**Provider Parameters** are dynamic, configurable values that control how Firefly interacts with external providers. They enable **flexible, environment-specific, and tenant-specific configurations** without code changes or redeployment.

### Purpose

Parameters solve critical configuration challenges:

- **Flexibility**: Change configurations without redeploying code
- **Environment Management**: Different settings for dev, staging, production
- **Tenant Customization**: Override defaults for specific tenants
- **Security**: Encrypted storage of sensitive credentials
- **Auditability**: Track all configuration changes with timestamps
- **Validation**: Type-safe parameters with validation rules
- **Versioning**: Track parameter changes over time

### Key Characteristics

Each parameter has:

- ✅ **Scope**: Global (all tenants) or tenant-specific
- ✅ **Type Safety**: Strongly typed (STRING, INTEGER, BOOLEAN, JSON, URL, EMAIL)
- ✅ **Validation**: Regex patterns, min/max values, required/optional
- ✅ **Security**: Encryption for sensitive values (API keys, secrets)
- ✅ **Categorization**: Organized by category (connection, authentication, etc.)
- ✅ **Metadata**: Additional context and documentation

---

## What are Provider Parameters?

### Conceptual Model

Think of parameters as **configuration knobs** that control provider behavior:

```
┌─────────────────────────────────────────────────────────────┐
│                    Provider: Stripe                         │
│                                                             │
│  Global Parameters (Default for all tenants)                │
│  ┌───────────────────────────────────────────────────────┐ │
│  │ api_timeout: 30000 (milliseconds)                     │ │
│  │ retry_attempts: 3                                     │ │
│  │ environment: production                               │ │
│  │ webhook_url: https://api.firefly.com/webhooks/stripe │ │
│  └───────────────────────────────────────────────────────┘ │
│                                                             │
│  Tenant-Specific Parameters (Override for Tenant A)         │
│  ┌───────────────────────────────────────────────────────┐ │
│  │ api_key: sk_live_tenant_a_key (encrypted)            │ │
│  │ api_timeout: 45000 (longer timeout for Tenant A)     │ │
│  │ webhook_url: https://tenant-a.com/webhooks/stripe    │ │
│  └───────────────────────────────────────────────────────┘ │
│                                                             │
│  Tenant-Specific Parameters (Override for Tenant B)         │
│  ┌───────────────────────────────────────────────────────┐ │
│  │ api_key: sk_live_tenant_b_key (encrypted)            │ │
│  │ environment: sandbox (testing for Tenant B)          │ │
│  └───────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### Parameter Components

#### 1. Core Configuration

```yaml
Parameter: "api_timeout"
├── Identification
│   ├── ID: 550e8400-e29b-41d4-a716-446655440500
│   ├── Provider: Stripe (UUID)
│   ├── Tenant: null (global) or specific tenant UUID
│   └── Name: api_timeout
├── Value
│   ├── Value: "30000"
│   ├── Type: INTEGER
│   └── Default: "30000"
├── Metadata
│   ├── Description: "API request timeout in milliseconds"
│   ├── Category: connection
│   ├── Display Order: 1
│   └── Environment: production
├── Security
│   ├── Is Secret: false
│   ├── Is Required: true
│   └── Is Editable: true
└── Validation
    ├── Validation Regex: "^[0-9]+$"
    ├── Min Value: 1000
    └── Max Value: 60000
```

#### 2. Scope Levels

**Global Parameters** (tenantId = null):
- Apply to all tenants by default
- Provide sensible defaults
- Can be overridden by tenant-specific parameters

**Tenant-Specific Parameters** (tenantId = specific UUID):
- Override global parameters for a specific tenant
- Enable tenant-specific customization
- Take precedence over global parameters

---

## Parameter Hierarchy

### Resolution Order

Parameters follow a **hierarchical resolution** to determine the effective value:

```
┌─────────────────────────────────────────────────────────────┐
│  Request: Get parameter "api_timeout" for Tenant A          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 1: Check Tenant-Specific Parameter                    │
│  Query: providerId = X, tenantId = A, name = "api_timeout" │
│  Result: Found → "45000"                                    │
│  ✅ RETURN "45000"                                          │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  Request: Get parameter "api_timeout" for Tenant B          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 1: Check Tenant-Specific Parameter                    │
│  Query: providerId = X, tenantId = B, name = "api_timeout" │
│  Result: Not Found                                          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 2: Check Global Parameter                             │
│  Query: providerId = X, tenantId = null, name = "api_timeout"│
│  Result: Found → "30000"                                    │
│  ✅ RETURN "30000"                                          │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  Request: Get parameter "new_param" for Tenant C            │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 1: Check Tenant-Specific Parameter                    │
│  Result: Not Found                                          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 2: Check Global Parameter                             │
│  Result: Not Found                                          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 3: Check Default Value                                │
│  Result: Found → "30000" (from parameter definition)        │
│  ✅ RETURN "30000"                                          │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  Request: Get required parameter "api_key" for Tenant D     │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 1: Check Tenant-Specific Parameter                    │
│  Result: Not Found                                          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 2: Check Global Parameter                             │
│  Result: Not Found                                          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 3: Check Default Value                                │
│  Result: Not Found                                          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│  Step 4: Parameter is Required                              │
│  ❌ THROW ConfigurationException                            │
│  "Required parameter 'api_key' not found for tenant D"      │
└─────────────────────────────────────────────────────────────┘
```

### Resolution Algorithm

```java
public Mono<String> getParameterValue(
    UUID providerId,
    UUID tenantId,
    String parameterName
) {
    // Step 1: Check tenant-specific parameter
    return parameterRepository
        .findByProviderIdAndTenantIdAndParameterName(
            providerId, tenantId, parameterName
        )
        .map(ProviderParameter::getParameterValue)
        // Step 2: Check global parameter
        .switchIfEmpty(
            parameterRepository
                .findByProviderIdAndTenantIdAndParameterName(
                    providerId, null, parameterName
                )
                .map(ProviderParameter::getParameterValue)
        )
        // Step 3: Check default value
        .switchIfEmpty(
            parameterRepository
                .findByProviderIdAndParameterName(providerId, parameterName)
                .map(ProviderParameter::getDefaultValue)
                .filter(Objects::nonNull)
        )
        // Step 4: Error if required and not found
        .switchIfEmpty(
            parameterRepository
                .findByProviderIdAndParameterName(providerId, parameterName)
                .flatMap(param -> {
                    if (param.getIsRequired()) {
                        return Mono.error(new ConfigurationException(
                            "Required parameter '" + parameterName + 
                            "' not found for tenant " + tenantId
                        ));
                    }
                    return Mono.empty();
                })
        );
}
```

---

## Parameter Types

Firefly supports **6 parameter types** for type-safe configuration:

### 1. STRING

**Purpose**: Text values (API keys, URLs, names, etc.)

**Example**:
```json
{
  "parameterName": "api_key",
  "parameterValue": "sk_live_abc123xyz789",
  "parameterType": "STRING"
}
```

**Validation**:
- Max length: 1000 characters
- Regex pattern (optional)
- Allowed values (optional)

**Use Cases**:
- API keys and secrets
- Endpoint URLs
- Provider names
- Custom identifiers

---

### 2. INTEGER

**Purpose**: Numeric values (timeouts, limits, counts, etc.)

**Example**:
```json
{
  "parameterName": "api_timeout",
  "parameterValue": "30000",
  "parameterType": "INTEGER",
  "metadata": "{\"unit\":\"milliseconds\",\"min\":1000,\"max\":60000\"}"
}
```

**Validation**:
- Min value (optional)
- Max value (optional)
- Must be valid integer

**Use Cases**:
- Timeouts (milliseconds)
- Retry attempts
- Rate limits
- Page sizes
- Connection pool sizes

---

### 3. BOOLEAN

**Purpose**: True/false flags (feature flags, toggles, etc.)

**Example**:
```json
{
  "parameterName": "enable_webhooks",
  "parameterValue": "true",
  "parameterType": "BOOLEAN"
}
```

**Validation**:
- Must be "true" or "false" (case-insensitive)

**Use Cases**:
- Feature flags
- Enable/disable functionality
- Debug mode
- Sandbox mode
- Retry enabled

---

### 4. JSON

**Purpose**: Complex structured data (objects, arrays, etc.)

**Example**:
```json
{
  "parameterName": "webhook_config",
  "parameterValue": "{\"url\":\"https://api.example.com/webhooks\",\"events\":[\"payment.success\",\"payment.failed\"],\"secret\":\"whsec_abc123\"}",
  "parameterType": "JSON"
}
```

**Validation**:
- Must be valid JSON syntax
- Schema validation (optional)

**Use Cases**:
- Webhook configurations
- Complex settings
- Feature configurations
- Custom metadata
- Provider-specific settings

---

### 5. URL

**Purpose**: Web addresses (API endpoints, webhooks, etc.)

**Example**:
```json
{
  "parameterName": "base_url",
  "parameterValue": "https://api.stripe.com",
  "parameterType": "URL"
}
```

**Validation**:
- Must be valid URL format
- Protocol validation (http/https)
- Domain validation

**Use Cases**:
- API base URLs
- Webhook URLs
- Redirect URLs
- Documentation URLs
- Support URLs

---

### 6. EMAIL

**Purpose**: Email addresses (support, notifications, etc.)

**Example**:
```json
{
  "parameterName": "support_email",
  "parameterValue": "support@example.com",
  "parameterType": "EMAIL"
}
```

**Validation**:
- Must be valid email format
- Domain validation (optional)

**Use Cases**:
- Support emails
- Notification emails
- Alert emails
- Contact emails

---

## Parameter Categories

Parameters are organized into **logical categories** for better management:

### 1. connection

**Purpose**: API connection settings

**Common Parameters**:
- `base_url`: API base URL
- `api_timeout`: Request timeout (milliseconds)
- `retry_attempts`: Number of retry attempts
- `retry_delay`: Delay between retries (milliseconds)
- `connection_pool_size`: Connection pool size
- `keep_alive_timeout`: Keep-alive timeout (seconds)

**Example**:
```json
{
  "parameterName": "api_timeout",
  "parameterValue": "30000",
  "parameterType": "INTEGER",
  "category": "connection"
}
```

---

### 2. authentication

**Purpose**: Authentication and authorization settings

**Common Parameters**:
- `api_key`: API key
- `api_secret`: API secret
- `client_id`: OAuth client ID
- `client_secret`: OAuth client secret
- `auth_type`: Authentication type (API_KEY, OAUTH2, BASIC)
- `token_url`: OAuth token URL
- `scope`: OAuth scope

**Example**:
```json
{
  "parameterName": "api_key",
  "parameterValue": "sk_live_abc123xyz789",
  "parameterType": "STRING",
  "category": "authentication",
  "isSecret": true
}
```

---

### 3. webhook

**Purpose**: Webhook configuration

**Common Parameters**:
- `webhook_url`: Webhook endpoint URL
- `webhook_secret`: Webhook signature secret
- `webhook_events`: Subscribed events (JSON array)
- `webhook_retry_attempts`: Retry attempts for failed webhooks
- `webhook_timeout`: Webhook request timeout

**Example**:
```json
{
  "parameterName": "webhook_config",
  "parameterValue": "{\"url\":\"https://api.example.com/webhooks\",\"events\":[\"payment.success\",\"payment.failed\"]}",
  "parameterType": "JSON",
  "category": "webhook"
}
```

---

### 4. features

**Purpose**: Feature flags and capabilities

**Common Parameters**:
- `enable_webhooks`: Enable webhook notifications
- `enable_retry`: Enable automatic retry
- `enable_sandbox`: Use sandbox environment
- `enable_debug`: Enable debug logging
- `supported_currencies`: Supported currencies (JSON array)
- `supported_countries`: Supported countries (JSON array)

**Example**:
```json
{
  "parameterName": "enable_webhooks",
  "parameterValue": "true",
  "parameterType": "BOOLEAN",
  "category": "features"
}
```

---

### 5. business

**Purpose**: Business rules and limits

**Common Parameters**:
- `min_transaction_amount`: Minimum transaction amount
- `max_transaction_amount`: Maximum transaction amount
- `daily_limit`: Daily transaction limit
- `monthly_limit`: Monthly transaction limit
- `fee_percentage`: Fee percentage
- `fee_fixed`: Fixed fee amount

**Example**:
```json
{
  "parameterName": "max_transaction_amount",
  "parameterValue": "10000",
  "parameterType": "INTEGER",
  "category": "business",
  "metadata": "{\"currency\":\"EUR\"}"
}
```

---

### 6. compliance

**Purpose**: Regulatory and compliance settings

**Common Parameters**:
- `data_residency`: Data residency region
- `retention_period`: Data retention period (days)
- `enable_audit_log`: Enable audit logging
- `enable_encryption`: Enable data encryption
- `compliance_level`: Compliance level (STANDARD, ENHANCED)
- `reporting_frequency`: Reporting frequency (DAILY, WEEKLY, MONTHLY)

**Example**:
```json
{
  "parameterName": "data_residency",
  "parameterValue": "EU",
  "parameterType": "STRING",
  "category": "compliance"
}
```

---

## Parameter Configuration

### Creating a Parameter

**Endpoint**: `POST /api/v1/provider-parameters`

**Request Body** (Global Parameter):
```json
{
  "providerId": "550e8400-e29b-41d4-a716-446655440400",
  "tenantId": null,
  "parameterName": "api_timeout",
  "parameterValue": "30000",
  "parameterType": "INTEGER",
  "description": "API request timeout in milliseconds",
  "isSecret": false,
  "isRequired": true,
  "isEditable": true,
  "validationRegex": "^[0-9]+$",
  "defaultValue": "30000",
  "environment": "production",
  "category": "connection",
  "displayOrder": 1,
  "metadata": "{\"unit\":\"milliseconds\",\"min\":1000,\"max\":60000\"}"
}
```

**Request Body** (Tenant-Specific Parameter):
```json
{
  "providerId": "550e8400-e29b-41d4-a716-446655440400",
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "parameterName": "api_key",
  "parameterValue": "sk_live_tenant_a_key",
  "parameterType": "STRING",
  "description": "Stripe API key for Tenant A",
  "isSecret": true,
  "isRequired": true,
  "isEditable": true,
  "environment": "production",
  "category": "authentication",
  "displayOrder": 1
}
```


