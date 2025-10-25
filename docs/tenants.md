# Tenant Management

**Complete guide to managing isolated banking instances in Firefly**

---

## 📋 Table of Contents

- [Overview](#overview)
- [What is a Tenant?](#what-is-a-tenant)
- [Tenant Lifecycle](#tenant-lifecycle)
- [Tenant Configuration](#tenant-configuration)
- [Tenant Isolation](#tenant-isolation)
- [Tenant Statuses](#tenant-statuses)
- [API Reference](#api-reference)
- [Best Practices](#best-practices)
- [Use Cases](#use-cases)

---

## Overview

A **Tenant** in Firefly represents a **complete, isolated instance** of the core banking platform. Each tenant operates independently with its own configuration, customer base, branding, and provider integrations, while sharing the underlying infrastructure for cost efficiency and operational simplicity.

### Purpose

Tenants enable Firefly to support multiple banking operations from a single platform installation:

- **Multi-Brand Banking**: Operate multiple brands from one platform
- **Geographic Expansion**: Launch in new regions with local configurations
- **White-Label Banking**: Enable partners to offer banking under their own brand
- **Business Segmentation**: Separate retail, business, and premium banking
- **Regulatory Compliance**: Isolate operations by regulatory jurisdiction

### Key Characteristics

Each tenant has:

- ✅ **Complete Data Isolation**: Customer data never crosses tenant boundaries
- ✅ **Independent Configuration**: Business rules, limits, fees per tenant
- ✅ **Custom Branding**: Unique visual identity and user experience
- ✅ **Specific Providers**: Dedicated or shared external service providers
- ✅ **Separate Compliance**: Region-specific regulatory requirements
- ✅ **Isolated Operations**: Independent monitoring, logging, and alerting

---

## What is a Tenant?

### Conceptual Model

Think of a tenant as a **virtual bank** within the Firefly ecosystem:

```
┌─────────────────────────────────────────────────────────────┐
│                    Firefly Platform                         │
│                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │  Tenant A    │  │  Tenant B    │  │  Tenant C    │     │
│  │  (Spain)     │  │  (France)    │  │  (Germany)   │     │
│  │              │  │              │  │              │     │
│  │ • Customers  │  │ • Customers  │  │ • Customers  │     │
│  │ • Accounts   │  │ • Accounts   │  │ • Accounts   │     │
│  │ • Config     │  │ • Config     │  │ • Config     │     │
│  │ • Branding   │  │ • Branding   │  │ • Branding   │     │
│  │ • Providers  │  │ • Providers  │  │ • Providers  │     │
│  └──────────────┘  └──────────────┘  └──────────────┘     │
│                                                             │
│  Shared Infrastructure: Compute, Storage, Network           │
└─────────────────────────────────────────────────────────────┘
```

### Tenant Components

#### 1. Core Configuration

```yaml
Tenant: "DigitalBankSpain"
├── Identification
│   ├── ID: 550e8400-e29b-41d4-a716-446655440000
│   ├── Code: DBSP (unique identifier)
│   └── Name: Digital Bank Spain
├── Geographic Settings
│   ├── Country: Spain (UUID reference)
│   ├── Timezone: Europe/Madrid
│   ├── Currency: EUR
│   └── Language: es-ES
├── Operational Settings
│   ├── Status: ACTIVE
│   ├── Subscription Tier: ENTERPRISE
│   ├── Trial: false
│   └── Subscription Period: 2025-01-01 to 2026-01-01
└── Metadata
    ├── Industry: retail_banking
    ├── Region: EMEA
    └── Regulatory Jurisdiction: Spain
```

#### 2. Contact Information

Each tenant has designated contacts for business and technical matters:

```yaml
Business Contact:
├── Name: Maria Garcia
├── Email: maria.garcia@digitalbank.es
├── Phone: +34 912 345 678
└── Role: Business Owner

Technical Contact:
├── Name: Carlos Rodriguez
├── Email: carlos.rodriguez@digitalbank.es
├── Phone: +34 912 345 679
└── Role: Technical Lead
```

#### 3. Branding (Separate Entity)

Visual customization is managed through the **TenantBranding** entity:

```yaml
Branding:
├── Logos
│   ├── Primary: /assets/dbsp-logo.svg
│   ├── Dark Mode: /assets/dbsp-logo-dark.svg
│   └── Favicon: /assets/dbsp-favicon.ico
├── Colors
│   ├── Primary: #1976D2 (Blue)
│   ├── Secondary: #FFC107 (Amber)
│   ├── Accent: #00BCD4 (Cyan)
│   ├── Background: #FFFFFF
│   └── Text: #212121
├── Typography
│   ├── Headings: 'Roboto', sans-serif
│   ├── Body: 'Open Sans', sans-serif
│   └── Monospace: 'Roboto Mono', monospace
└── Custom CSS
    └── Advanced styling overrides
```

#### 4. Provider Associations

Tenants are associated with specific providers for various capabilities:

```yaml
Providers:
├── KYC: Onfido (identity verification)
├── Payment: Treezor (payment processing)
├── Card: Marqeta (card issuing)
├── Compliance: ComplyAdvantage (AML/KYC)
└── Notification: Twilio (SMS), SendGrid (email)
```

---

## Tenant Lifecycle

### Lifecycle States

```
┌──────────┐
│  TRIAL   │  Evaluation period with limited features
└────┬─────┘
     │ activate()
     ▼
┌──────────┐
│  ACTIVE  │  Fully operational, serving customers
└────┬─────┘
     │
     ├─────────────┐
     │             │ suspend()
     │             ▼
     │        ┌──────────┐
     │        │SUSPENDED │  Temporarily disabled
     │        └────┬─────┘
     │             │ reactivate()
     │             │
     │ ◄───────────┘
     │
     │ maintenance()
     ▼
┌────────────┐
│MAINTENANCE │  Undergoing upgrades/maintenance
└────┬───────┘
     │ complete()
     │
     ▼
┌──────────┐
│  ACTIVE  │
└────┬─────┘
     │ deactivate()
     ▼
┌───────────┐
│ INACTIVE  │  Not in use, data retained
└────┬──────┘
     │ delete()
     ▼
┌──────────┐
│ DELETED  │  Soft-deleted, pending permanent removal
└──────────┘
```

### State Transitions

| From | To | Trigger | Description |
|------|----|---------| ------------|
| TRIAL | ACTIVE | Subscription activated | Trial period completed successfully |
| ACTIVE | SUSPENDED | Payment failure, compliance issue | Temporary suspension |
| SUSPENDED | ACTIVE | Issue resolved | Reactivation after suspension |
| ACTIVE | MAINTENANCE | Scheduled maintenance | Planned maintenance window |
| MAINTENANCE | ACTIVE | Maintenance completed | Return to normal operations |
| ACTIVE | INACTIVE | Business decision | Tenant no longer in use |
| INACTIVE | DELETED | Retention period expired | Permanent deletion initiated |

---

## Tenant Configuration

### Creating a Tenant

**Endpoint**: `POST /api/v1/tenants`

**Request Body**:
```json
{
  "code": "DBSP",
  "name": "Digital Bank Spain",
  "description": "Digital banking platform for the Spanish market",
  "tenantStatusId": "550e8400-e29b-41d4-a716-446655440001",
  "countryId": "550e8400-e29b-41d4-a716-446655440100",
  "timezone": "Europe/Madrid",
  "defaultCurrencyCode": "EUR",
  "defaultLanguageCode": "es-ES",
  "businessContactName": "Maria Garcia",
  "businessContactEmail": "maria.garcia@digitalbank.es",
  "businessContactPhone": "+34912345678",
  "technicalContactName": "Carlos Rodriguez",
  "technicalContactEmail": "carlos.rodriguez@digitalbank.es",
  "technicalContactPhone": "+34912345679",
  "subscriptionTier": "ENTERPRISE",
  "subscriptionStartDate": "2025-01-01T00:00:00Z",
  "subscriptionEndDate": "2026-01-01T00:00:00Z",
  "isTrial": false,
  "metadata": "{\"industry\":\"retail_banking\",\"region\":\"EMEA\"}"
}
```

**Response**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "code": "DBSP",
  "name": "Digital Bank Spain",
  "description": "Digital banking platform for the Spanish market",
  "tenantStatusId": "550e8400-e29b-41d4-a716-446655440001",
  "countryId": "550e8400-e29b-41d4-a716-446655440100",
  "timezone": "Europe/Madrid",
  "defaultCurrencyCode": "EUR",
  "defaultLanguageCode": "es-ES",
  "businessContactName": "Maria Garcia",
  "businessContactEmail": "maria.garcia@digitalbank.es",
  "businessContactPhone": "+34912345678",
  "technicalContactName": "Carlos Rodriguez",
  "technicalContactEmail": "carlos.rodriguez@digitalbank.es",
  "technicalContactPhone": "+34912345679",
  "subscriptionTier": "ENTERPRISE",
  "subscriptionStartDate": "2025-01-01T00:00:00Z",
  "subscriptionEndDate": "2026-01-01T00:00:00Z",
  "isTrial": false,
  "metadata": "{\"industry\":\"retail_banking\",\"region\":\"EMEA\"}",
  "createdAt": "2025-01-01T10:00:00Z",
  "updatedAt": "2025-01-01T10:00:00Z"
}
```

### Field Descriptions

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `code` | String | Yes | Unique tenant identifier (2-10 chars, uppercase) |
| `name` | String | Yes | Human-readable tenant name |
| `description` | Text | No | Detailed description of the tenant |
| `tenantStatusId` | UUID | Yes | Reference to tenant status (TRIAL, ACTIVE, etc.) |
| `countryId` | UUID | Yes | Reference to country where tenant operates |
| `timezone` | String | Yes | IANA timezone (e.g., "Europe/Madrid") |
| `defaultCurrencyCode` | String | Yes | ISO 4217 currency code (e.g., "EUR") |
| `defaultLanguageCode` | String | Yes | ISO 639-1 language code (e.g., "es-ES") |
| `businessContactName` | String | Yes | Business owner name |
| `businessContactEmail` | Email | Yes | Business owner email |
| `businessContactPhone` | String | No | Business owner phone |
| `technicalContactName` | String | Yes | Technical lead name |
| `technicalContactEmail` | Email | Yes | Technical lead email |
| `technicalContactPhone` | String | No | Technical lead phone |
| `subscriptionTier` | Enum | Yes | TRIAL, BASIC, STANDARD, PREMIUM, ENTERPRISE |
| `subscriptionStartDate` | DateTime | Yes | Subscription start date |
| `subscriptionEndDate` | DateTime | No | Subscription end date (null = no expiration) |
| `isTrial` | Boolean | No | Whether tenant is in trial period |
| `metadata` | JSON | No | Additional metadata (flexible key-value pairs) |

### Updating a Tenant

**Endpoint**: `PUT /api/v1/tenants/{id}`

**Request Body**: Same as creation, all fields optional

**Example - Update Contact Information**:
```json
{
  "businessContactName": "Ana Martinez",
  "businessContactEmail": "ana.martinez@digitalbank.es",
  "businessContactPhone": "+34912345680"
}
```

### Retrieving Tenants

**Get All Tenants**: `GET /api/v1/tenants`

**Query Parameters**:
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort field and direction (e.g., "name,asc")
- `status`: Filter by status code (e.g., "ACTIVE")
- `country`: Filter by country ID
- `subscriptionTier`: Filter by subscription tier

**Get Single Tenant**: `GET /api/v1/tenants/{id}`

**Get Tenant by Code**: `GET /api/v1/tenants/code/{code}`

### Deleting a Tenant

**Endpoint**: `DELETE /api/v1/tenants/{id}`

**Note**: This is a **soft delete**. The tenant is marked as DELETED but data is retained for compliance and audit purposes.

**Hard Delete**: Requires manual database operation after retention period expires.

---

## Tenant Isolation

### Data Isolation

Firefly ensures **complete data isolation** between tenants through multiple mechanisms:

#### 1. Database-Level Isolation

**Row-Level Security**:
```sql
-- All queries automatically filtered by tenant_id
CREATE POLICY tenant_isolation ON accounts
    USING (tenant_id = current_setting('app.current_tenant')::uuid);
```

**Tenant-Aware Queries**:
```java
// Always include tenant_id in queries
@Query("SELECT * FROM accounts WHERE tenant_id = :tenantId")
Flux<Account> findByTenantId(UUID tenantId);
```

#### 2. Application-Level Isolation

**Tenant Context**:
```java
// Tenant context stored in reactive context
public Mono<Account> getAccount(UUID accountId) {
    return ReactiveSecurityContextHolder.getContext()
        .map(ctx -> ctx.getAuthentication())
        .map(auth -> (TenantPrincipal) auth.getPrincipal())
        .flatMap(principal -> 
            accountRepository.findByIdAndTenantId(
                accountId, 
                principal.getTenantId()
            )
        );
}
```

**Request Validation**:
```java
@PreAuthorize("hasPermission(#tenantId, 'TENANT', 'READ')")
public Mono<TenantDTO> getTenant(UUID tenantId) {
    // Tenant access validated before execution
}
```

#### 3. API-Level Isolation

**Tenant Header**:
```http
GET /api/v1/accounts HTTP/1.1
Host: api.firefly-banking.org
Authorization: Bearer <token>
X-Tenant-ID: 550e8400-e29b-41d4-a716-446655440000
```

**Tenant Validation Middleware**:
```java
@Component
public class TenantValidationFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String tenantId = exchange.getRequest()
            .getHeaders()
            .getFirst("X-Tenant-ID");
        
        return validateTenant(tenantId)
            .flatMap(tenant -> {
                exchange.getAttributes().put("tenant", tenant);
                return chain.filter(exchange);
            });
    }
}
```


