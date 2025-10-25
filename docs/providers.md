# Provider Management

**Complete guide to managing external service integrations in Firefly**

---

## 📋 Table of Contents

- [Overview](#overview)
- [What is a Provider?](#what-is-a-provider)
- [Provider Types](#provider-types)
- [Provider Lifecycle](#provider-lifecycle)
- [Provider Configuration](#provider-configuration)
- [Provider-Tenant Associations](#provider-tenant-associations)
- [Provider Statuses](#provider-statuses)
- [API Reference](#api-reference)
- [Best Practices](#best-practices)
- [Use Cases](#use-cases)

---

## Overview

**Providers** are external services that Firefly integrates with to deliver specific banking functionalities. They are the **building blocks** that enable Firefly to offer comprehensive banking services without building everything from scratch.

### Purpose

Providers enable Firefly to:

- **Leverage Specialization**: Use best-of-breed providers for each capability
- **Ensure Compliance**: Integrate with certified providers for regulated activities
- **Accelerate Time-to-Market**: Leverage existing solutions instead of building from scratch
- **Maintain Flexibility**: Switch providers without changing core platform
- **Enable Redundancy**: Use multiple providers for critical services (failover, load balancing)
- **Optimize Costs**: Choose cost-effective providers per tenant or region

### Key Characteristics

Each provider has:

- ✅ **Type Classification**: Categorized by capability (KYC, PAYMENT, CARD, etc.)
- ✅ **Connection Details**: Base URL, API version, authentication
- ✅ **Status Management**: ACTIVE, INACTIVE, MAINTENANCE, DEPRECATED
- ✅ **Dynamic Parameters**: Configurable settings per provider and tenant
- ✅ **Value Mappings**: Translation between Firefly and provider formats
- ✅ **Multi-Tenant Support**: Shared or dedicated per tenant

---

## What is a Provider?

### Conceptual Model

Think of a provider as a **specialized service** that Firefly calls to perform specific banking operations:

```
┌─────────────────────────────────────────────────────────────┐
│                    Firefly Platform                         │
│                                                             │
│  Customer Registration Request                              │
│         │                                                   │
│         ▼                                                   │
│  ┌──────────────────┐                                       │
│  │  KYC Provider    │ ──────> Onfido API                    │
│  │  (Onfido)        │ <────── Identity Verified             │
│  └──────────────────┘                                       │
│                                                             │
│  Payment Request                                            │
│         │                                                   │
│         ▼                                                   │
│  ┌──────────────────┐                                       │
│  │ Payment Provider │ ──────> Stripe API                    │
│  │  (Stripe)        │ <────── Payment Processed             │
│  └──────────────────┘                                       │
│                                                             │
│  Card Issuance Request                                      │
│         │                                                   │
│         ▼                                                   │
│  ┌──────────────────┐                                       │
│  │  Card Provider   │ ──────> Marqeta API                   │
│  │  (Marqeta)       │ <────── Card Issued                   │
│  └──────────────────┘                                       │
└─────────────────────────────────────────────────────────────┘
```

### Provider Components

#### 1. Core Configuration

```yaml
Provider: "Treezor"
├── Identification
│   ├── ID: 550e8400-e29b-41d4-a716-446655440400
│   ├── Code: TREEZOR (unique identifier)
│   └── Name: Treezor
├── Classification
│   ├── Type: BAAS (Banking-as-a-Service)
│   └── Status: ACTIVE
├── Connection Details
│   ├── Base URL: https://api.treezor.com
│   ├── API Version: v1
│   ├── Documentation: https://docs.treezor.com
│   └── Timeout: 30000ms
├── Support Information
│   ├── Support Email: support@treezor.com
│   ├── Support Phone: +33 1 23 45 67 89
│   └── SLA: 99.9% uptime
└── Metadata
    ├── Region: EU
    ├── Certifications: [PCI-DSS, ISO27001]
    └── Data Residency: France
```

#### 2. Parameters (Dynamic Configuration)

```yaml
Parameters:
├── Global (applies to all tenants)
│   ├── api_timeout: 30000
│   ├── retry_attempts: 3
│   └── environment: production
└── Tenant-Specific (overrides for specific tenants)
    ├── Tenant A
    │   ├── client_id: tenant_a_client_id
    │   ├── client_secret: (encrypted)
    │   └── webhook_url: https://tenant-a.com/webhooks
    └── Tenant B
        ├── client_id: tenant_b_client_id
        ├── client_secret: (encrypted)
        └── webhook_url: https://tenant-b.com/webhooks
```

#### 3. Value Mappings (Data Translation)

```yaml
Value Mappings:
├── Account Status
│   ├── Firefly: "ACTIVE" → Treezor: "1"
│   ├── Firefly: "SUSPENDED" → Treezor: "2"
│   └── Firefly: "CLOSED" → Treezor: "3"
├── Transaction Type
│   ├── Firefly: "TRANSFER" → Treezor: "transfer"
│   ├── Firefly: "PAYMENT" → Treezor: "payment"
│   └── Firefly: "WITHDRAWAL" → Treezor: "withdrawal"
└── Currency Code
    ├── Firefly: "EUR" → Treezor: "978"
    ├── Firefly: "USD" → Treezor: "840"
    └── Firefly: "GBP" → Treezor: "826"
```

#### 4. Tenant Associations

```yaml
Tenant Associations:
├── Tenant A (Spain)
│   ├── Is Primary: true
│   ├── Priority: 1
│   └── Active: true
├── Tenant B (France)
│   ├── Is Primary: true
│   ├── Priority: 1
│   └── Active: true
└── Tenant C (Germany)
    ├── Is Primary: false (backup provider)
    ├── Priority: 2
    └── Active: true
```

---

## Provider Types

Firefly supports **10 distinct provider types**, each serving a specific purpose in the banking ecosystem:

### 1. KYC (Know Your Customer)

**Purpose**: Identity verification and customer onboarding

**Capabilities**:
- Identity document verification (passport, ID card, driver's license)
- Facial recognition and liveness detection
- Address verification
- AML (Anti-Money Laundering) checks
- PEP (Politically Exposed Person) screening
- Sanctions list screening

**Examples**: Onfido, Jumio, Veriff, Trulioo, Sumsub

**Use Cases**:
- Customer registration and onboarding
- Enhanced due diligence for high-risk customers
- Ongoing customer monitoring
- Regulatory compliance (KYC/AML)

---

### 2. PAYMENT

**Purpose**: Payment processing and money movement

**Capabilities**:
- Domestic and international transfers
- SEPA payments (Europe)
- SWIFT payments (international)
- Instant payments
- Recurring payments
- Payment status tracking

**Examples**: Stripe, Adyen, PayPal, Wise, Treezor

**Use Cases**:
- Customer-to-customer transfers
- Bill payments
- International remittances
- Merchant payments
- Salary disbursements

---

### 3. CARD

**Purpose**: Card issuing, management, and processing

**Capabilities**:
- Debit card issuance
- Credit card issuance
- Virtual card creation
- Card activation/deactivation
- Card limits and controls
- Transaction authorization
- Chargeback management

**Examples**: Marqeta, Galileo, GPS, i2c, Paysafe

**Use Cases**:
- Debit card programs
- Credit card programs
- Virtual cards for online shopping
- Corporate card programs
- Prepaid card programs

---

### 4. BAAS (Banking-as-a-Service)

**Purpose**: Complete banking infrastructure

**Capabilities**:
- Account creation and management
- Ledger and transaction processing
- Regulatory compliance
- Payment processing
- Card issuing
- KYC/AML services
- Reporting and analytics

**Examples**: Treezor, Railsbank, Solarisbank, Synapse, Unit

**Use Cases**:
- Complete banking infrastructure outsourcing
- Rapid market entry without banking license
- White-label banking solutions
- Embedded finance

---

### 5. ACCOUNT

**Purpose**: Account management and ledger

**Capabilities**:
- Account lifecycle management
- Balance tracking
- Transaction history
- Interest calculation
- Fee management
- Account statements

**Examples**: Mambu, Thought Machine, Temenos, Finacle

**Use Cases**:
- Core banking ledger
- Account management
- Interest calculation
- Fee processing

---

### 6. TRANSACTION

**Purpose**: Transaction processing and clearing

**Capabilities**:
- Transaction routing
- Transaction clearing
- Settlement processing
- Reconciliation
- Transaction monitoring
- Fraud detection

**Examples**: FIS, Fiserv, ACI Worldwide, Temenos

**Use Cases**:
- Transaction processing
- Clearing and settlement
- Reconciliation
- Transaction monitoring

---

### 7. COMPLIANCE

**Purpose**: AML, fraud detection, regulatory compliance

**Capabilities**:
- Transaction monitoring
- Sanctions screening
- PEP screening
- Adverse media screening
- Case management
- Regulatory reporting

**Examples**: ComplyAdvantage, Chainalysis, Elliptic, Refinitiv

**Use Cases**:
- AML/KYC compliance
- Fraud detection
- Sanctions screening
- Regulatory reporting

---

### 8. NOTIFICATION

**Purpose**: Customer communications

**Capabilities**:
- SMS notifications
- Email notifications
- Push notifications
- In-app messaging
- Transactional emails
- Marketing campaigns

**Examples**: Twilio, SendGrid, OneSignal, Firebase, Mailgun

**Use Cases**:
- Transaction alerts
- Account notifications
- Marketing campaigns
- Customer support
- Two-factor authentication

---

### 9. DOCUMENT

**Purpose**: Document storage and management

**Capabilities**:
- Document upload and storage
- Document retrieval
- E-signatures
- Document archival
- Document versioning
- Access control

**Examples**: DocuSign, Box, Dropbox, AWS S3, Google Cloud Storage

**Use Cases**:
- Customer document storage
- Contract management
- E-signatures
- Regulatory document retention

---

### 10. ANALYTICS

**Purpose**: Data analytics and business intelligence

**Capabilities**:
- Customer analytics
- Transaction analytics
- Business reporting
- Dashboards and visualizations
- Predictive analytics
- Data export

**Examples**: Mixpanel, Amplitude, Segment, Tableau, Looker

**Use Cases**:
- Customer behavior analysis
- Business performance monitoring
- Fraud detection
- Marketing optimization
- Product analytics

---

## Provider Lifecycle

### Lifecycle States

```
┌──────────┐
│  ACTIVE  │  Provider is operational and available
└────┬─────┘
     │
     ├─────────────┐
     │             │ maintenance()
     │             ▼
     │        ┌────────────┐
     │        │MAINTENANCE │  Scheduled maintenance
     │        └────┬───────┘
     │             │ complete()
     │             │
     │ ◄───────────┘
     │
     │ deactivate()
     ▼
┌───────────┐
│ INACTIVE  │  Provider temporarily disabled
└────┬──────┘
     │ reactivate()
     │
     ▼
┌──────────┐
│  ACTIVE  │
└────┬─────┘
     │ deprecate()
     ▼
┌────────────┐
│ DEPRECATED │  Provider being phased out
└────────────┘
```

### State Transitions

| From | To | Trigger | Description |
|------|----|---------| ------------|
| ACTIVE | MAINTENANCE | Scheduled maintenance | Provider undergoing maintenance |
| MAINTENANCE | ACTIVE | Maintenance completed | Return to normal operations |
| ACTIVE | INACTIVE | Provider issues, contract termination | Temporary deactivation |
| INACTIVE | ACTIVE | Issues resolved | Reactivation |
| ACTIVE | DEPRECATED | New provider available | Phase out old provider |

---

## Provider Configuration

### Creating a Provider

**Endpoint**: `POST /api/v1/providers`

**Request Body**:
```json
{
  "code": "TREEZOR",
  "name": "Treezor",
  "description": "Banking-as-a-Service provider for payment processing",
  "providerTypeId": "550e8400-e29b-41d4-a716-446655440200",
  "providerStatusId": "550e8400-e29b-41d4-a716-446655440300",
  "baseUrl": "https://api.treezor.com",
  "apiVersion": "v1",
  "documentationUrl": "https://docs.treezor.com",
  "supportEmail": "support@treezor.com",
  "supportPhone": "+33123456789",
  "metadata": "{\"region\":\"EU\",\"certifications\":[\"PCI-DSS\",\"ISO27001\"]}"
}
```

**Response**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440400",
  "code": "TREEZOR",
  "name": "Treezor",
  "description": "Banking-as-a-Service provider for payment processing",
  "providerTypeId": "550e8400-e29b-41d4-a716-446655440200",
  "providerStatusId": "550e8400-e29b-41d4-a716-446655440300",
  "baseUrl": "https://api.treezor.com",
  "apiVersion": "v1",
  "documentationUrl": "https://docs.treezor.com",
  "supportEmail": "support@treezor.com",
  "supportPhone": "+33123456789",
  "metadata": "{\"region\":\"EU\",\"certifications\":[\"PCI-DSS\",\"ISO27001\"]}",
  "createdAt": "2025-01-01T10:00:00Z",
  "updatedAt": "2025-01-01T10:00:00Z"
}
```

### Field Descriptions

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `code` | String | Yes | Unique provider identifier (2-20 chars, uppercase) |
| `name` | String | Yes | Human-readable provider name |
| `description` | Text | No | Detailed description of the provider |
| `providerTypeId` | UUID | Yes | Reference to provider type (KYC, PAYMENT, etc.) |
| `providerStatusId` | UUID | Yes | Reference to provider status (ACTIVE, INACTIVE, etc.) |
| `baseUrl` | URL | Yes | Provider API base URL |
| `apiVersion` | String | No | API version (e.g., "v1", "2.0") |
| `documentationUrl` | URL | No | Link to provider API documentation |
| `supportEmail` | Email | No | Provider support email |
| `supportPhone` | String | No | Provider support phone number |
| `metadata` | JSON | No | Additional metadata (certifications, region, etc.) |

### Updating a Provider

**Endpoint**: `PUT /api/v1/providers/{id}`

**Request Body**: Same as creation, all fields optional

**Example - Update Support Information**:
```json
{
  "supportEmail": "new-support@treezor.com",
  "supportPhone": "+33123456790"
}
```

### Retrieving Providers

**Get All Providers**: `GET /api/v1/providers`

**Query Parameters**:
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort field and direction (e.g., "name,asc")
- `type`: Filter by provider type ID
- `status`: Filter by provider status ID
- `code`: Filter by provider code

**Get Single Provider**: `GET /api/v1/providers/{id}`

**Get Provider by Code**: `GET /api/v1/providers/code/{code}`

**Get Providers by Type**: `GET /api/v1/providers/type/{typeId}`

### Deleting a Provider

**Endpoint**: `DELETE /api/v1/providers/{id}`

**Note**: Providers with active tenant associations cannot be deleted. Remove all tenant associations first.

---

## Provider-Tenant Associations

### Associating a Provider with a Tenant

**Endpoint**: `POST /api/v1/provider-tenants`

**Request Body**:
```json
{
  "providerId": "550e8400-e29b-41d4-a716-446655440400",
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "isPrimary": true,
  "priority": 1,
  "isActive": true,
  "metadata": "{\"contract_id\":\"CONTRACT-12345\",\"start_date\":\"2025-01-01\"}"
}
```

**Field Descriptions**:

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `providerId` | UUID | Yes | Provider to associate |
| `tenantId` | UUID | Yes | Tenant to associate with |
| `isPrimary` | Boolean | No | Whether this is the primary provider for this capability |
| `priority` | Integer | No | Priority for failover (1 = highest priority) |
| `isActive` | Boolean | No | Whether association is active |
| `metadata` | JSON | No | Additional metadata (contract details, etc.) |

### Provider Priority and Failover

When multiple providers of the same type are associated with a tenant, Firefly uses **priority-based failover**:

```yaml
Payment Providers for Tenant A:
├── Provider 1: Stripe
│   ├── isPrimary: true
│   ├── priority: 1 (highest)
│   └── Use: Normal operations
├── Provider 2: Adyen
│   ├── isPrimary: false
│   ├── priority: 2
│   └── Use: Failover when Stripe is down
└── Provider 3: PayPal
    ├── isPrimary: false
    ├── priority: 3 (lowest)
    └── Use: Last resort failover
```

**Failover Logic**:
1. Attempt operation with primary provider (priority 1)
2. If fails (timeout, error, health check failed):
   → Attempt with next priority provider (priority 2)
3. If fails:
   → Attempt with next priority provider (priority 3)
4. If all fail:
   → Queue for retry, alert operations team

### Getting Provider-Tenant Associations

**Get All Associations**: `GET /api/v1/provider-tenants`

**Get Associations for Tenant**: `GET /api/v1/provider-tenants/tenant/{tenantId}`

**Get Associations for Provider**: `GET /api/v1/provider-tenants/provider/{providerId}`

**Get Primary Provider for Tenant and Type**:
```
GET /api/v1/provider-tenants/tenant/{tenantId}/type/{typeId}/primary
```

### Updating an Association

**Endpoint**: `PUT /api/v1/provider-tenants/{id}`

**Example - Change Priority**:
```json
{
  "priority": 2,
  "isPrimary": false
}
```

### Removing an Association

**Endpoint**: `DELETE /api/v1/provider-tenants/{id}`

**Note**: Ensure tenant has alternative provider before removing primary provider.

---

## Provider Statuses

### Available Statuses

| Status | Code | Description | Allowed Operations |
|--------|------|-------------|-------------------|
| **ACTIVE** | `ACTIVE` | Provider is operational and available | All operations |
| **INACTIVE** | `INACTIVE` | Provider temporarily disabled | No operations |
| **MAINTENANCE** | `MAINTENANCE` | Scheduled maintenance | Read-only operations |
| **DEPRECATED** | `DEPRECATED` | Provider being phased out | Existing operations only, no new tenants |

### Status Management

**Get All Statuses**: `GET /api/v1/provider-statuses`

**Response**:
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440300",
    "code": "ACTIVE",
    "name": "Active",
    "description": "Provider is operational"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440301",
    "code": "INACTIVE",
    "name": "Inactive",
    "description": "Provider is temporarily disabled"
  }
]
```

### Changing Provider Status

**Endpoint**: `PUT /api/v1/providers/{id}/status`

**Request Body**:
```json
{
  "statusId": "550e8400-e29b-41d4-a716-446655440301",
  "reason": "Provider API experiencing issues",
  "effectiveDate": "2025-01-15T00:00:00Z"
}
```

---

## API Reference

### Provider Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/providers` | List all providers (paginated) |
| `GET` | `/api/v1/providers/{id}` | Get provider by ID |
| `GET` | `/api/v1/providers/code/{code}` | Get provider by code |
| `GET` | `/api/v1/providers/type/{typeId}` | Get providers by type |
| `POST` | `/api/v1/providers` | Create new provider |
| `PUT` | `/api/v1/providers/{id}` | Update provider |
| `DELETE` | `/api/v1/providers/{id}` | Delete provider |
| `PUT` | `/api/v1/providers/{id}/status` | Change provider status |

### Provider Type Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/provider-types` | List all provider types |
| `GET` | `/api/v1/provider-types/{id}` | Get type by ID |
| `GET` | `/api/v1/provider-types/code/{code}` | Get type by code |

### Provider Status Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/provider-statuses` | List all provider statuses |
| `GET` | `/api/v1/provider-statuses/{id}` | Get status by ID |
| `GET` | `/api/v1/provider-statuses/code/{code}` | Get status by code |

### Provider-Tenant Association Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/provider-tenants` | List all associations |
| `GET` | `/api/v1/provider-tenants/{id}` | Get association by ID |
| `GET` | `/api/v1/provider-tenants/tenant/{tenantId}` | Get associations for tenant |
| `GET` | `/api/v1/provider-tenants/provider/{providerId}` | Get associations for provider |
| `GET` | `/api/v1/provider-tenants/tenant/{tenantId}/type/{typeId}/primary` | Get primary provider |
| `POST` | `/api/v1/provider-tenants` | Create association |
| `PUT` | `/api/v1/provider-tenants/{id}` | Update association |
| `DELETE` | `/api/v1/provider-tenants/{id}` | Delete association |

---

## Best Practices

### Provider Selection

#### ✅ DO:

1. **Evaluate Multiple Providers**
   - Compare features, pricing, SLAs
   - Test integration complexity
   - Check certifications and compliance
   - Review customer support quality

2. **Consider Geographic Coverage**
   ```yaml
   Europe:
     - KYC: Onfido (EU-based, GDPR compliant)
     - Payment: Treezor (EU banking license)

   US:
     - KYC: Jumio (US-based)
     - Payment: Stripe (US-focused)
   ```

3. **Plan for Redundancy**
   ```yaml
   Critical Services (Payment):
     - Primary: Stripe (priority 1)
     - Secondary: Adyen (priority 2)
     - Tertiary: PayPal (priority 3)

   Non-Critical Services (Analytics):
     - Single provider: Mixpanel
   ```

4. **Test in Sandbox First**
   ```java
   // Use sandbox environment for testing
   ProviderParameter.builder()
       .parameterName("environment")
       .parameterValue("sandbox")
       .build();
   ```

#### ❌ DON'T:

1. **Don't Use Single Provider for Critical Services**
   - Always have backup providers
   - Implement automatic failover

2. **Don't Skip Compliance Checks**
   - Verify provider certifications
   - Check regulatory compliance
   - Review data residency requirements

3. **Don't Ignore Provider SLAs**
   - Monitor provider uptime
   - Track response times
   - Set up alerts for SLA violations

### Provider Integration

#### ✅ DO:

1. **Implement Circuit Breakers**
   ```java
   @CircuitBreaker(
       name = "stripe",
       fallbackMethod = "fallbackPayment"
   )
   public Mono<PaymentResponse> processPayment(PaymentRequest request) {
       return stripeClient.charge(request);
   }

   public Mono<PaymentResponse> fallbackPayment(
       PaymentRequest request,
       Exception ex
   ) {
       // Try secondary provider
       return adyenClient.charge(request);
   }
   ```

2. **Monitor Provider Health**
   ```java
   @Scheduled(fixedRate = 30000) // Every 30 seconds
   public void checkProviderHealth() {
       providers.forEach(provider -> {
           healthCheck(provider)
               .subscribe(
                   health -> {
                       if (health.isHealthy()) {
                           updateProviderStatus(provider, ACTIVE);
                       } else {
                           updateProviderStatus(provider, INACTIVE);
                           alertOps(provider, health.getError());
                       }
                   },
                   error -> {
                       markProviderDown(provider, error);
                       alertOps(provider, error);
                   }
               );
       });
   }
   ```

3. **Implement Retry Logic**
   ```java
   public Mono<PaymentResponse> processPayment(PaymentRequest request) {
       return stripeClient.charge(request)
           .retryWhen(
               Retry.backoff(3, Duration.ofSeconds(1))
                   .filter(throwable -> isRetryable(throwable))
           );
   }
   ```

4. **Use Value Mappings**
   ```java
   public Mono<String> mapToProviderValue(
       UUID providerId,
       String mappingType,
       String fireflyValue
   ) {
       return valueMappingRepository
           .findByProviderIdAndMappingTypeAndFireflyValue(
               providerId, mappingType, fireflyValue
           )
           .map(ProviderValueMapping::getProviderValue)
           .switchIfEmpty(Mono.just(fireflyValue)); // Use as-is if no mapping
   }
   ```

5. **Log All Provider Interactions**
   ```java
   public Mono<PaymentResponse> processPayment(PaymentRequest request) {
       return Mono.just(request)
           .doOnNext(req -> log.info(
               "Calling provider: {}, request: {}",
               providerId, req
           ))
           .flatMap(stripeClient::charge)
           .doOnSuccess(response -> log.info(
               "Provider response: {}, response: {}",
               providerId, response
           ))
           .doOnError(error -> log.error(
               "Provider error: {}, error: {}",
               providerId, error
           ));
   }
   ```

#### ❌ DON'T:

1. **Don't Hardcode Provider Logic**
   - Use configuration and parameters
   - Keep integration code generic

2. **Don't Skip Error Handling**
   - Handle all error scenarios
   - Implement proper fallbacks
   - Log errors for debugging

3. **Don't Ignore Rate Limits**
   - Respect provider rate limits
   - Implement request throttling
   - Queue requests if needed

### Provider Security

#### ✅ DO:

1. **Encrypt Sensitive Parameters**
   ```java
   @Column(name = "parameter_value")
   @Convert(converter = EncryptedStringConverter.class)
   private String parameterValue; // Encrypted at rest
   ```

2. **Use Environment-Specific Credentials**
   ```yaml
   environments:
     development:
       stripe:
         api_key: sk_test_...
     production:
       stripe:
         api_key: sk_live_...
   ```

3. **Rotate Credentials Regularly**
   ```java
   @Scheduled(cron = "0 0 0 1 * *") // First day of month
   public void rotateProviderCredentials() {
       providers.forEach(provider -> {
           if (shouldRotateCredentials(provider)) {
               rotateCredentials(provider);
               notifyOps(provider, "Credentials rotated");
           }
       });
   }
   ```

4. **Validate Webhook Signatures**
   ```java
   public Mono<Void> handleWebhook(
       String signature,
       String payload
   ) {
       return validateSignature(signature, payload)
           .flatMap(valid -> {
               if (!valid) {
                   return Mono.error(
                       new InvalidWebhookSignatureException()
                   );
               }
               return processWebhook(payload);
           });
   }
   ```

#### ❌ DON'T:

1. **Don't Store Credentials in Plain Text**
   - Always encrypt sensitive data
   - Use secret management systems

2. **Don't Log Sensitive Data**
   - Mask API keys in logs
   - Redact sensitive fields

3. **Don't Share Credentials Across Environments**
   - Use separate credentials per environment
   - Never use production credentials in development

---

## Use Cases

### Use Case 1: Multi-Provider Failover

**Scenario**: A bank needs 99.99% uptime for payment processing.

**Implementation**:

```yaml
Payment Providers:
├── Primary: Stripe
│   ├── Priority: 1
│   ├── SLA: 99.9%
│   └── Cost: Higher
├── Secondary: Adyen
│   ├── Priority: 2
│   ├── SLA: 99.9%
│   └── Cost: Medium
└── Tertiary: PayPal
    ├── Priority: 3
    ├── SLA: 99.5%
    └── Cost: Lower

Failover Logic:
1. Try Stripe (primary)
2. If fails → Try Adyen (secondary)
3. If fails → Try PayPal (tertiary)
4. If all fail → Queue and alert

Result: 99.9999% uptime (six nines)
```

### Use Case 2: Geographic Provider Selection

**Scenario**: A bank operates in multiple regions with different providers per region.

**Implementation**:

```yaml
Europe (SEPA):
├── Payment: Treezor (EU banking license)
├── KYC: Onfido (GDPR compliant)
└── Card: GPS (EU-based)

US:
├── Payment: Stripe (US-focused)
├── KYC: Jumio (US-based)
└── Card: Marqeta (US-based)

Asia:
├── Payment: Adyen (global coverage)
├── KYC: Trulioo (global coverage)
└── Card: i2c (global coverage)
```

### Use Case 3: Cost Optimization

**Scenario**: A bank wants to optimize costs by using cheaper providers when possible.

**Implementation**:

```yaml
KYC Providers:
├── Basic KYC: Cheaper provider
│   ├── Use for: Low-risk customers
│   ├── Cost: $1 per verification
│   └── Features: Basic ID verification
└── Enhanced KYC: Premium provider
    ├── Use for: High-risk customers
    ├── Cost: $5 per verification
    └── Features: Enhanced due diligence, PEP screening

Logic:
if (customer.riskLevel == LOW) {
    use basicKYCProvider;
} else {
    use enhancedKYCProvider;
}

Result: 70% cost reduction on KYC
```

---

## Summary

Providers are the foundation of Firefly's integration ecosystem, enabling:

- ✅ **Best-of-Breed**: Use specialized providers for each capability
- ✅ **Flexibility**: Switch providers without platform changes
- ✅ **Redundancy**: Multiple providers for high availability
- ✅ **Cost Optimization**: Choose cost-effective providers
- ✅ **Compliance**: Certified providers for regulated activities

For more information, see:
- [Parameter Configuration](./parameters.md)
- [Tenant Management](./tenants.md)
- [Main Documentation](./README.md)

---

**[⬆ Back to Top](#provider-management)**

