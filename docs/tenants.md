# Tenant Management

**Complete guide to managing isolated banking instances in Firefly**

---

## Table of Contents

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

- **Complete Data Isolation**: Customer data never crosses tenant boundaries
- **Independent Configuration**: Business rules, limits, fees per tenant
- **Custom Branding**: Unique visual identity and user experience
- **Specific Providers**: Dedicated or shared external service providers
- **Separate Compliance**: Region-specific regulatory requirements
- **Isolated Operations**: Independent monitoring, logging, and alerting

---

## What is a Tenant?

### Conceptual Model

Think of a tenant as a **virtual bank** within the Firefly ecosystem:

```
┌─────────────────────────────────────────────────────────┐
│                    Firefly Platform                     │
│                                                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐   │
│  │  Tenant A    │  │  Tenant B    │  │  Tenant C    │   │
│  │  (Spain)     │  │  (France)    │  │  (Germany)   │   │
│  │              │  │              │  │              │   │
│  │ • Customers  │  │ • Customers  │  │ • Customers  │   │
│  │ • Accounts   │  │ • Accounts   │  │ • Accounts   │   │
│  │ • Config     │  │ • Config     │  │ • Config     │   │
│  │ • Branding   │  │ • Branding   │  │ • Branding   │   │
│  │ • Providers  │  │ • Providers  │  │ • Providers  │   │
│  └──────────────┘  └──────────────┘  └──────────────┘   │
│                                                         │
│  Shared Infrastructure: Compute, Storage, Network       │
└─────────────────────────────────────────────────────────┘
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
│ DELETED  │  Deleted (permanently removed)
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
| `code` | String | Yes | Unique tenant identifier (2-50 chars) |
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
| `subscriptionTier` | String | No | Subscription tier level (e.g., ENTERPRISE) |
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

**Get Single Tenant**: `GET /api/v1/tenants/{id}`

**Filter Tenants**: `POST /api/v1/tenants/filter`

Use the filter endpoint with a `FilterRequest` body to search tenants with pagination and criteria.

### Deleting a Tenant

**Endpoint**: `DELETE /api/v1/tenants/{id}`

**Note**: This deletes the tenant. Returns HTTP 204 (No Content) on success.

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

### Isolation Best Practices

#### DO:

1. **Always Filter by Tenant ID**
   ```java
   // Correct
   accountRepository.findByIdAndTenantId(accountId, tenantId);

   // Wrong - could expose other tenants' data
   accountRepository.findById(accountId);
   ```

2. **Validate Tenant Context in All Operations**
   ```java
   public Mono<Account> createAccount(Account account) {
       return getCurrentTenantId()
           .flatMap(tenantId -> {
               account.setTenantId(tenantId);
               return accountRepository.save(account);
           });
   }
   ```

3. **Use Tenant-Aware Logging**
   ```java
   log.info("Creating account for tenant: {}, account: {}",
       tenantId, accountId);
   ```

4. **Implement Tenant-Specific Rate Limiting**
   ```java
   @RateLimiter(
       name = "tenant-api",
       keyResolver = TenantKeyResolver.class
   )
   public Mono<Response> processRequest(Request request) {
       // Rate limited per tenant
   }
   ```

#### DON'T:

1. **Never Query Across Tenants**
   ```java
   // Wrong - exposes all tenants' data
   SELECT * FROM accounts;

   // Correct - filtered by tenant
   SELECT * FROM accounts WHERE tenant_id = :tenantId;
   ```

2. **Don't Share Sensitive Data**
   - Never expose one tenant's data to another
   - Implement strict access controls
   - Audit all cross-tenant access attempts

3. **Don't Hardcode Tenant Logic**
   - Use configuration, not code
   - Keep business logic generic and configurable

---

## Tenant Statuses

### Available Statuses

| Status | Code | Description | Allowed Operations |
|--------|------|-------------|-------------------|
| **TRIAL** | `TRIAL` | Evaluation period with limited features | All operations, with feature limits |
| **ACTIVE** | `ACTIVE` | Fully operational, serving customers | All operations |
| **SUSPENDED** | `SUSPENDED` | Temporarily disabled (payment issues, compliance) | Read-only, no transactions |
| **MAINTENANCE** | `MAINTENANCE` | Undergoing maintenance or upgrades | Read-only, no new customers |
| **INACTIVE** | `INACTIVE` | Not currently in use, data retained | Read-only |
| **DELETED** | `DELETED` | Deleted (permanently removed) | No operations |

### Status Management

**Get All Statuses**: `GET /api/v1/tenant-statuses`

**Response**:
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "code": "ACTIVE",
    "name": "Active",
    "description": "Tenant is fully operational"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "code": "SUSPENDED",
    "name": "Suspended",
    "description": "Tenant is temporarily suspended"
  }
]
```

### Changing Tenant Status

To change a tenant's status, update the tenant's `tenantStatusId` field using the standard update endpoint.

**Endpoint**: `PUT /api/v1/tenants/{id}`

**Request Body**:
```json
{
  "tenantStatusId": "550e8400-e29b-41d4-a716-446655440002"
}
```

**Note**: There is no dedicated status change endpoint. Use the standard tenant update endpoint and set the `tenantStatusId` field to the desired status.
```

---

## API Reference

### Tenant Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/tenants/{id}` | Get tenant by ID |
| `POST` | `/api/v1/tenants/filter` | Filter tenants with pagination and criteria |
| `POST` | `/api/v1/tenants` | Create new tenant |
| `PUT` | `/api/v1/tenants/{id}` | Update tenant |
| `DELETE` | `/api/v1/tenants/{id}` | Delete tenant |
| `POST` | `/api/v1/tenants/{tenantId}/providers/filter` | Filter tenant's provider relationships |
| `POST` | `/api/v1/tenants/{tenantId}/providers` | Associate provider with tenant |
| `GET` | `/api/v1/tenants/{tenantId}/providers/{id}` | Get tenant-provider relationship |
| `PUT` | `/api/v1/tenants/{tenantId}/providers/{id}` | Update tenant-provider relationship |
| `DELETE` | `/api/v1/tenants/{tenantId}/providers/{id}` | Remove provider from tenant |

### Tenant Status Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/tenant-statuses/{id}` | Get status by ID |
| `POST` | `/api/v1/tenant-statuses/filter` | Filter tenant statuses |
| `POST` | `/api/v1/tenant-statuses` | Create tenant status |
| `PUT` | `/api/v1/tenant-statuses/{id}` | Update tenant status |
| `DELETE` | `/api/v1/tenant-statuses/{id}` | Delete tenant status |

### Tenant Branding Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/tenant-brandings/{id}` | Get branding by ID |
| `GET` | `/api/v1/tenant-brandings/tenant/{tenantId}` | Get branding for tenant |
| `POST` | `/api/v1/tenant-brandings/filter` | Filter tenant brandings |
| `POST` | `/api/v1/tenant-brandings` | Create tenant branding |
| `PUT` | `/api/v1/tenant-brandings/{id}` | Update tenant branding |
| `DELETE` | `/api/v1/tenant-brandings/{id}` | Delete tenant branding |

---

## Best Practices

### Tenant Creation

#### DO:

1. **Validate Unique Tenant Code**
   ```java
   public Mono<Tenant> createTenant(TenantDTO dto) {
       return tenantRepository.findByCode(dto.getCode())
           .flatMap(existing ->
               Mono.error(new TenantCodeAlreadyExistsException())
           )
           .switchIfEmpty(
               Mono.defer(() -> {
                   Tenant tenant = mapper.toEntity(dto);
                   return tenantRepository.save(tenant);
               })
           );
   }
   ```

2. **Initialize Default Configuration**
   ```java
   public Mono<Tenant> createTenant(TenantDTO dto) {
       return tenantRepository.save(mapper.toEntity(dto))
           .flatMap(tenant ->
               initializeDefaultConfiguration(tenant)
                   .thenReturn(tenant)
           );
   }

   private Mono<Void> initializeDefaultConfiguration(Tenant tenant) {
       return Mono.when(
           createDefaultBranding(tenant),
           createDefaultParameters(tenant),
           assignDefaultProviders(tenant)
       );
   }
   ```

3. **Set Up Monitoring and Alerting**
   ```java
   public Mono<Tenant> createTenant(TenantDTO dto) {
       return tenantRepository.save(mapper.toEntity(dto))
           .flatMap(tenant ->
               setupMonitoring(tenant)
                   .thenReturn(tenant)
           );
   }
   ```

4. **Create Audit Trail**
   ```java
   public Mono<Tenant> createTenant(TenantDTO dto) {
       return tenantRepository.save(mapper.toEntity(dto))
           .doOnSuccess(tenant ->
               auditLog.record(
                   action: "TENANT_CREATED",
                   tenantId: tenant.getId(),
                   createdBy: getCurrentUser(),
                   details: dto
               )
           );
   }
   ```

#### DON'T:

1. **Don't Create Tenants Without Validation**
   - Always validate tenant code uniqueness
   - Validate country, timezone, currency, language
   - Validate contact information

2. **Don't Skip Default Configuration**
   - Always initialize default branding
   - Set up default parameters
   - Assign default providers

3. **Don't Forget Audit Trails**
   - Log all tenant creation events
   - Track who created the tenant and when
   - Store initial configuration

### Tenant Updates

#### DO:

1. **Validate Changes Before Applying**
   ```java
   public Mono<Tenant> updateTenant(UUID id, TenantDTO dto) {
       return tenantRepository.findById(id)
           .flatMap(existing ->
               validateChanges(existing, dto)
                   .then(applyChanges(existing, dto))
           );
   }
   ```

2. **Track Configuration Changes**
   ```java
   public Mono<Tenant> updateTenant(UUID id, TenantDTO dto) {
       return tenantRepository.findById(id)
           .flatMap(existing -> {
               Map<String, Object> changes = detectChanges(existing, dto);
               return tenantRepository.save(applyChanges(existing, dto))
                   .doOnSuccess(updated ->
                       auditLog.record(
                           action: "TENANT_UPDATED",
                           tenantId: id,
                           changes: changes
                       )
                   );
           });
   }
   ```

3. **Notify Affected Systems**
   ```java
   public Mono<Tenant> updateTenant(UUID id, TenantDTO dto) {
       return tenantRepository.save(applyChanges(existing, dto))
           .flatMap(updated ->
               notifyConfigurationChange(updated)
                   .thenReturn(updated)
           );
   }
   ```

#### DON'T:

1. **Don't Allow Changing Immutable Fields**
   - Tenant ID cannot be changed
   - Tenant code should not be changed (or require special approval)
   - Creation timestamp cannot be changed

2. **Don't Update Without Validation**
   - Validate all field changes
   - Check for conflicts
   - Verify user permissions

### Tenant Deletion

#### DO:

1. **Handle Deletion Carefully**
   ```java
   // The DELETE endpoint permanently removes the tenant and associated resources.
   // Consider updating the tenant status to INACTIVE first for a grace period,
   // then permanently delete via the DELETE endpoint.
   public Mono<Void> deleteTenant(UUID id) {
       return tenantService.delete(id);
   }
   ```

2. **Check for Active Customers**
   ```java
   public Mono<Void> deleteTenant(UUID id) {
       return customerRepository.countByTenantId(id)
           .flatMap(count -> {
               if (count > 0) {
                   return Mono.error(
                       new TenantHasActiveCustomersException(count)
                   );
               }
               return deleteTenant(id);
           });
   }
   ```

3. **Archive Data Before Deletion**
   ```java
   public Mono<Void> deleteTenant(UUID id) {
       return archiveTenantData(id)
           .then(deleteTenant(id));
   }
   ```

4. **Notify Stakeholders**
   ```java
   public Mono<Void> deleteTenant(UUID id) {
       return tenantRepository.findById(id)
           .flatMap(tenant ->
               notifyDeletion(tenant)
                   .then(deleteTenant(id))
           );
   }
   ```

#### DON'T:

1. **Don't Delete Without Precautions**
   - Archive data before permanent deletion
   - Retain data for compliance period if required
   - Consider setting status to INACTIVE before permanently deleting

2. **Don't Delete Without Checks**
   - Check for active customers
   - Check for pending transactions
   - Check for active subscriptions

---

## Use Cases

### Use Case 1: Multi-Brand Bank

**Scenario**: A bank operates three brands targeting different customer segments.

**Implementation**:

```yaml
Tenant 1: "PremiumBank"
├── Target: High-net-worth individuals
├── Branding: Luxury, sophisticated
├── Products: Premium accounts, wealth management
└── Providers: Enhanced KYC, international payments

Tenant 2: "StandardBank"
├── Target: General population
├── Branding: Professional, trustworthy
├── Products: Standard accounts, loans
└── Providers: Standard KYC, domestic payments

Tenant 3: "YouthBank"
├── Target: Students and young professionals
├── Branding: Modern, vibrant
├── Products: No-fee accounts, budgeting tools
└── Providers: Fast KYC, instant payments
```

**Benefits**:
- Separate branding per segment
- Tailored products and pricing
- Shared infrastructure (60% cost reduction)
- Independent operations and reporting

### Use Case 2: Geographic Expansion

**Scenario**: A neobank expands from Spain to France and Germany.

**Implementation**:

```yaml
Tenant: "SpainBranch"
├── Country: Spain
├── Currency: EUR
├── Language: Spanish
├── Providers: Iberpay, Spanish KYC
└── Compliance: Bank of Spain

Tenant: "FranceBranch"
├── Country: France
├── Currency: EUR
├── Language: French
├── Providers: SEPA, French KYC
└── Compliance: ACPR

Tenant: "GermanyBranch"
├── Country: Germany
├── Currency: EUR
├── Language: German
├── Providers: SEPA, German KYC
└── Compliance: BaFin
```

**Benefits**:
- Local compliance per country
- Localized user experience
- Region-specific providers
- Centralized management

### Use Case 3: White-Label Banking

**Scenario**: A retail company offers banking to customers under its own brand.

**Implementation**:

```yaml
Tenant: "RetailBankingBrand"
├── Branding: Retail company's brand
├── Providers: BaaS provider (Treezor)
├── Products: Checking, savings, co-branded cards
├── Integration: Retail loyalty program
└── Revenue: Interchange fees, interest income
```

**Benefits**:
- Launch in 3 months
- No banking infrastructure needed
- Complete brand control
- New revenue stream

---

## Summary

Tenants are the foundation of Firefly's multi-tenant architecture, enabling:

- **Complete Isolation**: Data, configuration, and operations
- **Flexible Deployment**: Multi-brand, geographic, white-label
- **Cost Efficiency**: Shared infrastructure, independent customization
- **Rapid Launch**: New tenants in days, not months
- **Compliance**: Region-specific regulatory requirements

For more information, see:
- [Provider Management](./providers.md)
- [Parameter Configuration](./parameters.md)
- [Main Documentation](./README.md)

---

**[Back to Top](#tenant-management)**

