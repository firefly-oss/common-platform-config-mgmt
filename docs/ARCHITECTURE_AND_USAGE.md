i # Architecture and Usage Guide - Firefly Config Management

**Version:** 1.0.0  
**Date:** 2025-10-25  
**Author:** Firefly Development Team

---

## Table of Contents

1. [Introduction](#introduction)
2. [System Architecture](#system-architecture)
3. [Detailed Data Model](#detailed-data-model)
4. [EAV Pattern - Dynamic Configuration](#eav-pattern---dynamic-configuration)
5. [Real-World Usage Examples](#real-world-usage-examples)
6. [Workflows](#workflows)
7. [Best Practices](#best-practices)

---

## Introduction

Firefly Config Management is the **central configuration system** for the Firefly Core Banking Platform. It manages:

- **Multi-tenancy**: Complete isolation per tenant
- **External Providers**: KYC, Payments, Cards, BaaS, etc.
- **Banking Channels**: Web, Mobile, ATM, Branch, Call Center, API
- **Feature Flags**: Gradual feature rollout
- **Environment Configuration**: DEV, QA, STAGING, PROD
- **Complete Audit Trail**: Traceability of all changes
- **Webhooks**: Event notifications

---

## System Architecture

### Design Principles

1. **Reactive Programming**: Entire system is non-blocking (Spring WebFlux + R2DBC)
2. **Multi-tenancy**: Complete data isolation per tenant
3. **EAV Pattern**: Dynamic configuration without schema changes
4. **Delete Operations**: Standard delete operations (some entities support soft delete via `active` flag, others use hard deletes)
5. **Optimistic Locking**: Concurrency control with `version` field
6. **Audit Trail**: Complete change log for compliance

### Application Layers

```
┌─────────────────────────────────────────────────────────────┐
│                    REST API Layer                           │
│  Controllers (16) - OpenAPI Documentation - Swagger UI      │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                   Business Logic Layer                      │
│  Services (16) - Validation - Business Rules - Mappers      │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                   Data Access Layer                         │
│  Repositories (16) - R2DBC - Reactive Queries               │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                   PostgreSQL Database                       │
│  16 Tables - Indexes - Foreign Keys - Constraints           │
└─────────────────────────────────────────────────────────────┘
```

---

## Detailed Data Model

### 1. Tenant Management (4 entities)

#### 1.1 Tenant
**Purpose**: Represents an isolated Firefly instance (a bank, fintech, etc.)

**Key fields:**
- `id` (UUID): Unique identifier
- `code` (String): Unique tenant code (e.g., "ACME_BANK_US")
- `tenant_status_id` (UUID): Current status (ACTIVE, SUSPENDED, etc.)
- `country_id` (UUID): Country of operation
- `subscription_tier` (String): Subscription level (FREE, BASIC, ENTERPRISE)
- `is_trial` (Boolean): Whether in trial period
- `metadata` (JSON): Additional custom data

**Relationships:**
- 1 Tenant → 1 TenantStatus
- 1 Tenant → 1 TenantBranding
- 1 Tenant → 1 TenantSettings
- 1 Tenant → N ChannelConfigs
- 1 Tenant → N FeatureFlags
- 1 Tenant → N ProviderTenants

#### 1.2 TenantStatus
**Purpose**: Tenant lifecycle states

**Predefined states:**
- `ACTIVE`: Operational and active
- `SUSPENDED`: Temporarily suspended
- `INACTIVE`: Inactive
- `TRIAL`: In trial period
- `EXPIRED`: Subscription expired

#### 1.3 TenantBranding
**Purpose**: Visual customization per tenant

**Key fields:**
- `logo_url`: Main logo URL
- `logo_dark_url`: Dark mode logo URL
- `primary_color`: Primary color (#HEX)
- `secondary_color`: Secondary color
- `custom_css`: Custom CSS

#### 1.4 TenantSettings
**Purpose**: Tenant operational configuration (44 fields)

**Configuration categories:**
- **Security**: MFA, timeouts, password policies
- **Limits**: Transactions, amounts, attempts
- **Notifications**: Email, SMS, push
- **Compliance**: KYC, AML, reports
- **Operational**: Schedules, maintenance, backups

---

### 2. Provider Management (6 entities)

#### 2.1 Provider
**Purpose**: Integrated external providers (Stripe, Plaid, Onfido, etc.)

**Key fields:**
- `code`: Unique code (e.g., "STRIPE_PAYMENTS")
- `provider_type_id`: Provider type
- `provider_status_id`: Current status
- `base_url`: API base URL
- `api_version`: API version
- `rate_limit_per_minute`: Request limit

**Provider types:**
- `KYC`: Know Your Customer (Onfido, Jumio)
- `PAYMENT`: Payment processors (Stripe, Adyen)
- `CARD`: Card issuance (Marqeta, Galileo)
- `BAAS`: Banking as a Service (Synapse, Unit)
- `ACCOUNT`: Account management
- `TRANSACTION`: Transaction processing
- `COMPLIANCE`: Regulatory compliance
- `NOTIFICATION`: Notification delivery
- `DOCUMENT`: Document management
- `ANALYTICS`: Analytics and reporting

#### 2.2 ProviderParameter (EAV Pattern)
**Purpose**: Dynamic provider configuration

**Key fields:**
- `provider_id`: Provider it belongs to
- `tenant_id`: Specific tenant (null = global)
- `parameter_name`: Parameter name
- `parameter_value`: Value (null if `is_secret=true`, use `credential_vault_id` instead)
- `parameter_type`: STRING, INTEGER, DECIMAL, BOOLEAN, JSON
- `is_secret`: Whether the parameter is a secret stored in the vault
- `credential_vault_id`: Reference to credential in security-vault (for secrets)

**Parameter examples:**
```json
{
  "providerId": "uuid-stripe",
  "tenantId": null,
  "parameterName": "api_version",
  "parameterValue": "2023-10-16",
  "parameterType": "STRING",
  "isSecret": false
}
```

#### 2.3 ProviderValueMapping
**Purpose**: Value mapping between Firefly and external providers

**Key fields:**
- `provider_id`: Provider it belongs to
- `tenant_id`: Specific tenant (null = global)
- `mapping_type`: Type of mapping (e.g., "TRANSACTION_STATUS")
- `firefly_value`: Internal Firefly value
- `provider_value`: External provider value
- `direction`: Direction of mapping (e.g., "BIDIRECTIONAL")

**Example:**
```json
{
  "providerId": "uuid-stripe",
  "tenantId": null,
  "mappingType": "TRANSACTION_STATUS",
  "fireflyValue": "COMPLETED",
  "providerValue": "succeeded",
  "direction": "BIDIRECTIONAL"
}
```

#### 2.4 ProviderTenant
**Purpose**: Many-to-many relationship between providers and tenants

**Key fields:**
- `provider_id`: Provider
- `tenant_id`: Tenant
- `priority`: Priority (higher = preferred)
- `is_primary`: Whether it's the primary provider
- `enabled`: Whether it's enabled
- `auto_failover_enabled`: Whether automatic failover is enabled
- `fallback_provider_id`: Reference to fallback provider

---

### 3. Channel Management (2 entities)

#### 3.1 ChannelConfig
**Purpose**: Banking channel configuration

**Supported channels:**
- `WEB_BANKING`: Web banking
- `MOBILE_BANKING`: Mobile app
- `ATM`: Automated teller machines
- `BRANCH`: Physical branches
- `CALL_CENTER`: Call center
- `API`: Public API
- `OPEN_BANKING`: Open Banking APIs

**Key fields:**
- `channel_code`: Channel code
- `enabled`: Whether it's enabled
- `priority`: Priority for failover
- `failover_channel_id`: Backup channel

#### 3.2 ChannelConfigParameter (EAV Pattern)
**Purpose**: Dynamic parameters per channel

**Categories:**
- `SECURITY`: Security configuration
- `LIMITS`: Transaction limits
- `FEATURES`: Enabled features
- `AVAILABILITY`: Schedules and availability
- `MONITORING`: Metrics and monitoring

**Parameter examples:**
```json
[
  {
    "parameter_key": "max_transaction_amount",
    "parameter_value": "50000.00",
    "parameter_type": "DECIMAL",
    "category": "LIMITS",
    "is_required": true
  },
  {
    "parameter_key": "session_timeout_minutes",
    "parameter_value": "15",
    "parameter_type": "INTEGER",
    "category": "SECURITY",
    "is_required": true
  },
  {
    "parameter_key": "requires_mfa",
    "parameter_value": "true",
    "parameter_type": "BOOLEAN",
    "category": "SECURITY",
    "is_required": true
  },
  {
    "parameter_key": "mobile_app_version_min",
    "parameter_value": "3.0.0",
    "parameter_type": "STRING",
    "category": "FEATURES",
    "is_required": false
  }
]
```

---

### 4. Feature Management (1 entity)

#### 4.1 FeatureFlag
**Purpose**: Feature toggles for gradual feature rollout

**Key fields:**
- `feature_key`: Unique feature identifier
- `feature_name`: Human-readable feature name
- `enabled`: Whether it's enabled
- `rollout_percentage`: User percentage (0-100)
- `target_user_segments`: User segments (JSON)
- `start_date`: Start date
- `end_date`: End date
- `environment`: Environment (dev, staging, prod, all)

**Example:**
```json
{
  "tenantId": "uuid-acme-bank",
  "featureKey": "new_dashboard_ui",
  "featureName": "New Dashboard UI",
  "enabled": true,
  "rolloutPercentage": 25,
  "targetUserSegments": "[\"beta_testers\", \"premium_users\"]",
  "startDate": "2025-10-01T00:00:00",
  "endDate": "2025-11-01T00:00:00"
}
```

---

### 5. Configuration Management (2 entities)

#### 5.1 EnvironmentConfig
**Purpose**: Environment-specific configuration

**Environments:**
- `DEVELOPMENT`: Local development
- `QA`: Quality Assurance
- `STAGING`: Pre-production
- `PRODUCTION`: Production

**Example:**
```json
{
  "tenantId": "uuid-acme-bank",
  "environmentName": "PRODUCTION",
  "configKey": "database_pool_size",
  "configValue": "50",
  "configType": "INTEGER",
  "isSecret": false
}
```

#### 5.2 ConfigurationAudit
**Purpose**: Complete change audit trail

**Key fields:**
- `entity_type`: Modified entity type
- `entity_id`: Entity ID
- `action`: CREATE, UPDATE, DELETE, ACTIVATE, DEACTIVATE
- `field_name`: Modified field
- `old_value`: Previous value
- `new_value`: New value
- `changed_by_user_id`: User who made the change
- `changed_by_username`: Username or email of user
- `change_reason`: Business justification
- `ip_address`: User's IP
- `change_category`: CONFIGURATION, SECURITY, COMPLIANCE, OPERATIONAL, EMERGENCY
- `change_severity`: LOW, MEDIUM, HIGH, CRITICAL
- `rollback_available`: Whether it can be reverted

---

### 6. Webhook Management (1 entity)

#### 6.1 WebhookConfig
**Purpose**: Centralized webhook configuration

**Key fields:**
- `webhook_name`: Descriptive name for the webhook
- `webhook_url`: Destination URL
- `event_types`: Event types (comma-separated)
- `http_method`: GET, POST, PUT, PATCH
- `auth_type`: NONE, BASIC, BEARER, API_KEY, HMAC, OAUTH2
- `retry_enabled`: Whether retry is enabled
- `max_retry_attempts`: Maximum retry attempts
- `retry_delay_seconds`: Delay between retries
- `retry_backoff_multiplier`: Exponential backoff multiplier
- `timeout_seconds`: Request timeout

**Example:**
```json
{
  "tenantId": "uuid-acme-bank",
  "webhookName": "Transaction Notifications",
  "webhookUrl": "https://api.acmebank.com/webhooks/transactions",
  "eventTypes": "transaction.created,transaction.completed",
  "httpMethod": "POST",
  "authType": "BEARER",
  "authHeaderValue": "eyJhbGciOiJIUzI1NiIs...",
  "retryEnabled": true,
  "maxRetryAttempts": 3,
  "retryDelaySeconds": 60,
  "retryBackoffMultiplier": 2.0,
  "timeoutSeconds": 30
}
```

---

## EAV Pattern - Dynamic Configuration

### Why EAV?

The **Entity-Attribute-Value** pattern allows:

1. **Flexibility**: Add new parameters without schema changes
2. **Extensibility**: Each tenant can have unique parameters
3. **Versioning**: Easy configuration migration
4. **Multi-tenancy**: Global vs tenant-specific configuration

### EAV Implementations in the System

#### 1. ProviderParameter

```
┌─────────────────────────────────────────────────────────────┐
│                      Provider (Entity)                      │
│  id, code, name, base_url, api_version, ...                 │
└─────────────────────────────────────────────────────────────┘
                            ↓ 1:N
┌─────────────────────────────────────────────────────────────┐
│              ProviderParameter (Attribute-Value)            │
│  provider_id, parameter_name, parameter_value, ...          │
└─────────────────────────────────────────────────────────────┘
```

**Advantages:**
- Add new parameters without ALTER TABLE
- Global vs per-tenant configuration
- Encrypted sensitive values

#### 2. ChannelConfigParameter

```
┌─────────────────────────────────────────────────────────────┐
│                   ChannelConfig (Entity)                    │
│  id, tenant_id, channel_code, enabled, priority, ...        │
└─────────────────────────────────────────────────────────────┘
                            ↓ 1:N
┌─────────────────────────────────────────────────────────────┐
│          ChannelConfigParameter (Attribute-Value)           │
│  channel_config_id, parameter_key, parameter_value, ...     │
└─────────────────────────────────────────────────────────────┘
```

**Advantages:**
- Channel-specific configuration
- Parameter categorization
- Regex validation
- Default values

---

## Real-World Usage Examples

### Example 1: Configure a New Tenant

**Step 1: Create Tenant**
```bash
POST /api/v1/tenants
Content-Type: application/json

{
  "code": "ACME_BANK_US",
  "name": "Acme Bank USA",
  "description": "Main US operations for Acme Bank",
  "tenantStatusId": "uuid-active-status",
  "countryId": "uuid-usa",
  "timezone": "America/New_York",
  "defaultCurrencyCode": "USD",
  "defaultLanguageCode": "en-US",
  "subscriptionTier": "ENTERPRISE",
  "isTrial": false
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "code": "ACME_BANK_US",
  "name": "Acme Bank USA",
  "active": true,
  "version": 0,
  "createdAt": "2025-10-25T20:00:00",
  "updatedAt": "2025-10-25T20:00:00"
}
```

**Step 2: Configure Branding**
```bash
POST /api/v1/tenant-brandings
Content-Type: application/json

{
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "logoUrl": "https://cdn.acmebank.com/logo.png",
  "primaryColor": "#0066CC",
  "secondaryColor": "#FF6600",
  "fontFamily": "Roboto, sans-serif"
}
```

**Step 3: Configure Settings**
```bash
POST /api/v1/tenant-settings
Content-Type: application/json

{
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "mfaEnabled": true,
  "sessionTimeoutMinutes": 15,
  "maxLoginAttempts": 3,
  "passwordMinLength": 12,
  "maxTransactionAmount": 100000.00,
  "dailyTransactionLimit": 500000.00
}
```

---

### Example 2: Configure Web Banking Channel

**Step 1: Create ChannelConfig**
```bash
POST /api/v1/channel-configs
Content-Type: application/json

{
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "channelCode": "WEB_BANKING",
  "channelName": "Web Banking Portal",
  "description": "Primary web banking channel",
  "enabled": true,
  "priority": 1
}
```

**Response:**
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "channelCode": "WEB_BANKING",
  "channelName": "Web Banking Portal",
  "enabled": true,
  "priority": 1,
  "active": true,
  "version": 0
}
```

**Step 2: Add Dynamic Parameters**
```bash
POST /api/v1/channel-config-parameters
Content-Type: application/json

[
  {
    "channelConfigId": "660e8400-e29b-41d4-a716-446655440001",
    "parameterKey": "max_transaction_amount",
    "parameterValue": "50000.00",
    "parameterType": "DECIMAL",
    "category": "LIMITS",
    "isRequired": true,
    "description": "Maximum transaction amount for web banking"
  },
  {
    "channelConfigId": "660e8400-e29b-41d4-a716-446655440001",
    "parameterKey": "session_timeout_minutes",
    "parameterValue": "15",
    "parameterType": "INTEGER",
    "category": "SECURITY",
    "isRequired": true
  },
  {
    "channelConfigId": "660e8400-e29b-41d4-a716-446655440001",
    "parameterKey": "requires_mfa",
    "parameterValue": "true",
    "parameterType": "BOOLEAN",
    "category": "SECURITY",
    "isRequired": true
  }
]
```

---

## Workflows

### Workflow 1: New Tenant Onboarding

```
1. Create Tenant → 2. Create TenantBranding → 3. Create TenantSettings
                                                        ↓
4. Create ChannelConfigs (Web, Mobile, ATM) → 5. Add ChannelConfigParameters
                                                        ↓
6. Associate Providers (KYC, Payment, Card) → 7. Configure ProviderParameters
                                                        ↓
8. Create FeatureFlags → 9. Configure EnvironmentConfigs → 10. Tenant Ready
```

### Workflow 2: Activate New Feature

```
1. Create FeatureFlag (enabled=false, rollout_percentage=0)
                    ↓
2. Enable for beta testers (target_segments=["beta_testers"])
                    ↓
3. Gradually increase rollout (10% → 25% → 50% → 100%)
                    ↓
4. Monitor metrics and errors
                    ↓
5. Rollback if issues (enabled=false) or 100% if OK
```

---

## Best Practices

### 1. Security
- Always mark sensitive parameters with `isSecret=true` and use `credentialVaultId` to reference credentials stored in the security-vault
- Do not store credential values directly in `parameterValue`
- Use HTTPS for all communications
- Implement rate limiting per tenant

### 2. Performance
- Use indexes on frequently searched fields
- Implement caching for static configurations
- Paginate large query results
- Use projections to reduce transferred data

### 3. Audit
- Log all changes in ConfigurationAudit
- Include user information and timestamp
- Keep previous values for rollback
- Implement log retention per compliance

### 4. Multi-tenancy
- Always validate tenant_id in requests
- Implement data isolation at query level
- Use tenant_id in all composite indexes
- Validate permissions per tenant

### 5. Dynamic Configuration (EAV)
- Use ProviderParameter for provider configuration
- Use ChannelConfigParameter for channel configuration
- Define `parameter_type` correctly (STRING, INTEGER, DECIMAL, BOOLEAN, JSON)
- Use `validation_regex` to validate values
- Define `default_value` for optional parameters

### 6. Feature Flags
- Start with low rollout_percentage (5-10%)
- Gradually increase based on metrics
- Use target_segments for specific groups
- Define start_date and end_date for temporary features
- Have rollback plan ready

---

**Document generated on 2025-10-25** 
**For more information, see complete documentation in `/docs`**

