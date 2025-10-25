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
│  ┌──────────────────┐                                      │
│  │  KYC Provider    │ ──────> Onfido API                   │
│  │  (Onfido)        │ <────── Identity Verified            │
│  └──────────────────┘                                      │
│                                                             │
│  Payment Request                                            │
│         │                                                   │
│         ▼                                                   │
│  ┌──────────────────┐                                      │
│  │ Payment Provider │ ──────> Stripe API                   │
│  │  (Stripe)        │ <────── Payment Processed            │
│  └──────────────────┘                                      │
│                                                             │
│  Card Issuance Request                                      │
│         │                                                   │
│         ▼                                                   │
│  ┌──────────────────┐                                      │
│  │  Card Provider   │ ──────> Marqeta API                  │
│  │  (Marqeta)       │ <────── Card Issued                  │
│  └──────────────────┘                                      │
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


