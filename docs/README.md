# Firefly Configuration Management Service

**Enterprise-grade multi-tenant configuration management for modern core banking**

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen.svg)](https://spring.io/projects/spring-boot)

---

## ✅ System Status

**Current Version:** 1.0.0
**Build Status:** ✅ Passing
**Application Status:** ✅ Running on http://localhost:8080
**Database:** ✅ PostgreSQL 17.6 with 16 tables
**Entities:** ✅ 16 entities with complete stack (Entity, DTO, Repository, Mapper, Service, Controller)
**API Endpoints:** ✅ 80+ REST endpoints with OpenAPI documentation
**Swagger UI:** ✅ Available at http://localhost:8080/swagger-ui.html

---

## 📋 Table of Contents

- [System Status](#-system-status)
- [Documentation Index](#-documentation-index)
- [Introduction](#-introduction)
- [What is Firefly?](#-what-is-firefly)
- [Core Concepts](#-core-concepts)
- [Architecture Overview](#-architecture-overview)
- [Technology Stack](#-technology-stack)
- [Data Model](#-data-model)
- [Quick Start Guide](#-quick-start-guide)
- [Use Cases](#-use-cases)
- [Best Practices](#-best-practices)
- [Support](#-support)

---

## 📚 Documentation Index

This project has comprehensive documentation covering all aspects of the system:

### Quick Reference
- **[SYSTEM_VERIFICATION.md](./SYSTEM_VERIFICATION.md)** - Complete verification report with all components validated
- **[COMPLETE_SYSTEM_AUDIT.md](./COMPLETE_SYSTEM_AUDIT.md)** - Full system audit with validation matrices and status
- **[ARCHITECTURE_AND_USAGE.md](./ARCHITECTURE_AND_USAGE.md)** - Detailed architecture, data model, and real-world usage examples

### Detailed Guides
- **[README.md](./README.md)** (this file) - Comprehensive introduction and key concepts
- **[tenants.md](./tenants.md)** - Tenant management and lifecycle
- **[providers.md](./providers.md)** - Provider integration and configuration
- **[parameters.md](./parameters.md)** - Dynamic parameter management with EAV pattern

### Project Root
- **[../README.md](../README.md)** - Main project README with quick start guide

---

## 🎯 Introduction

The **Firefly Configuration Management Service** is a mission-critical microservice within the Firefly open-source core banking ecosystem. It serves as the **central nervous system** for managing multi-tenant configurations, orchestrating external provider integrations, and enabling comprehensive customization across the entire banking platform.

### Purpose and Scope

This service addresses the fundamental challenge of operating a multi-tenant banking platform at scale by providing:

#### 🎛️ Centralized Configuration
Single source of truth for all platform configurations, eliminating configuration drift and ensuring consistency across environments.

#### 🏢 Tenant Isolation
Complete separation of data, settings, and integrations per tenant, ensuring security, compliance, and operational independence.

#### 🔌 Provider Orchestration
Dynamic management of external service integrations with support for multiple providers per capability, failover, and load balancing.

#### 🎨 Flexible Customization
Per-tenant branding, parameters, business rules, and workflows without code changes or redeployment.

#### 📊 Operational Excellence
Comprehensive audit trails, versioning, change management, and rollback capabilities for enterprise-grade operations.

### Why Configuration Management Matters

In modern banking platforms, configuration management is critical because:

1. **Regulatory Compliance**: Different regions have different regulatory requirements (GDPR, PSD2, PCI-DSS, etc.)
2. **Business Agility**: Rapid deployment of new brands, products, or markets without platform changes
3. **Operational Efficiency**: Centralized management reduces complexity, errors, and operational overhead
4. **Customer Experience**: Tailored experiences per brand, region, or customer segment
5. **Risk Management**: Controlled rollout of changes with comprehensive audit trails and rollback capabilities
6. **Cost Optimization**: Shared infrastructure with tenant-specific customization reduces total cost of ownership

---

## 🏦 What is Firefly?

**Firefly** is an open-source, cloud-native core banking platform designed for the next generation of financial services. It enables banks, fintech companies, and financial service providers to build, deploy, and scale digital banking experiences with unprecedented speed and flexibility.

### Key Capabilities

#### 💳 Complete Banking Infrastructure
- **Account Management**: Current accounts, savings accounts, deposits, and specialized accounts
- **Payment Processing**: Domestic and international payments, SEPA, SWIFT, instant payments
- **Card Issuing**: Debit cards, credit cards, virtual cards, and card management
- **Loan Origination**: Personal loans, mortgages, business loans, and servicing
- **Investment Management**: Wealth management, robo-advisory, and portfolio management

#### 🌍 Multi-Tenant Architecture
- **Isolated Environments**: Complete data and configuration isolation per tenant
- **Shared Infrastructure**: Optimized resource utilization with tenant-specific customization
- **Horizontal Scalability**: Scale individual tenants independently based on demand
- **Resource Optimization**: Efficient use of compute, storage, and network resources

#### 🔌 Extensive Integration Ecosystem
- **10+ Provider Types**: KYC, payments, cards, BaaS, compliance, notifications, and more
- **Plug-and-Play**: Standardized integration patterns for rapid provider onboarding
- **Redundancy Support**: Primary and backup providers for critical services
- **Health Monitoring**: Real-time provider health checks and automatic failover

#### 🎨 Complete Customization
- **White-Label Branding**: Full visual customization per tenant
- **Configurable Business Rules**: Fees, limits, workflows without code changes
- **Flexible Product Catalog**: Tailored banking products per tenant
- **Custom Workflows**: Tenant-specific approval processes and automation

#### 🔒 Enterprise-Grade Security
- **End-to-End Encryption**: Data encryption at rest and in transit
- **Role-Based Access Control**: Granular permissions and access management
- **Audit Logging**: Comprehensive audit trails for compliance
- **Compliance**: PCI-DSS, GDPR, PSD2, and regional regulatory compliance

---

## 🔑 Core Concepts

### Tenant

A **Tenant** represents a completely isolated instance of the Firefly banking platform. Think of it as a **virtual bank** within the larger Firefly ecosystem.

#### What is a Tenant?

Each tenant operates independently with its own:

- **Configuration Settings**: Business rules, limits, fees, operational parameters
- **Customer Base**: Completely isolated customer data, accounts, and transactions
- **Branding**: Unique visual identity (logos, colors, fonts, custom CSS)
- **Provider Integrations**: Specific external service providers and configurations
- **Product Catalog**: Tailored banking products and services
- **Compliance Rules**: Region-specific regulatory requirements and reporting
- **Business Logic**: Custom workflows, approval processes, and automation

#### Tenant Lifecycle

```
┌──────────┐    ┌──────────┐    ┌──────────┐    ┌───────────┐    ┌──────────┐
│  TRIAL   │ -> │  ACTIVE  │ -> │SUSPENDED │ -> │ INACTIVE  │ -> │ DELETED  │
└──────────┘    └──────────┘    └──────────┘    └───────────┘    └──────────┘
     │               │                │                │
     └───────────────┴────────────────┴────────────────┘
                     │
                ┌────────────┐
                │MAINTENANCE │
                └────────────┘
```

**Status Descriptions:**
- **TRIAL**: Evaluation period with limited features or time
- **ACTIVE**: Fully operational and serving customers
- **SUSPENDED**: Temporarily disabled (e.g., payment issues, compliance review)
- **INACTIVE**: Not currently in use but data retained
- **EXPIRED**: Subscription has expired

#### Real-World Examples

**Example 1: Multi-Brand Bank**
```yaml
Tenant: "PremiumBank"
  Target Audience: High-net-worth individuals
  Branding: Luxury, sophisticated design with gold accents
  Products:
    - Premium checking accounts (no fees, concierge service)
    - Wealth management and investment advisory
    - Exclusive credit cards with premium rewards
  Providers:
    - KYC: Enhanced due diligence provider
    - Payments: International wire transfer specialist
    - Cards: Premium card issuer with metal cards
  Business Rules:
    - Minimum balance: $100,000
    - No transaction fees
    - Dedicated relationship manager
```

**Example 2: Digital-First Neobank**
```yaml
Tenant: "YouthBank"
  Target Audience: Students and young professionals (18-35)
  Branding: Modern, vibrant, mobile-first design
  Products:
    - No-fee checking accounts
    - Budgeting and savings tools
    - Instant peer-to-peer payments
  Providers:
    - KYC: Fast, mobile-optimized identity verification
    - Payments: Instant payment provider
    - Notifications: Push notifications and in-app messaging
  Business Rules:
    - No minimum balance
    - No monthly fees
    - Gamified savings features
```

**Example 3: Regional Branch**
```yaml
Tenant: "SpainBranch"
  Target Audience: Spanish market
  Branding: Corporate identity with local cultural elements
  Products:
    - SEPA-compliant accounts
    - Spanish mortgage products
    - Local payment methods (Bizum)
  Providers:
    - Payments: Iberpay (Spanish payment system)
    - KYC: Spanish identity verification (DNI/NIE)
    - Compliance: Spanish regulatory reporting
  Business Rules:
    - Currency: EUR
    - Language: Spanish
    - Timezone: Europe/Madrid
    - Compliance: Bank of Spain regulations
```

---

### Provider

A **Provider** is an external service that Firefly integrates with to deliver specific banking functionality. Providers are the **building blocks** that enable Firefly to offer comprehensive banking services without building everything from scratch.

#### Why Providers?

Modern banking requires integration with numerous specialized services:
- **Specialization**: Best-of-breed providers for each capability
- **Compliance**: Certified providers for regulated activities
- **Speed to Market**: Leverage existing solutions instead of building from scratch
- **Flexibility**: Switch providers without changing core platform
- **Redundancy**: Multiple providers for critical services

#### Provider Types

Firefly supports 10 distinct provider types, each serving a specific purpose:

| Type | Purpose | Examples | Use Cases |
|------|---------|----------|-----------|
| **KYC** | Identity verification and customer onboarding | Onfido, Jumio, Veriff, Trulioo | Customer registration, identity verification, AML checks |
| **PAYMENT** | Payment processing and money movement | Stripe, Adyen, PayPal, Wise | Transfers, bill payments, international remittances |
| **CARD** | Card issuing, management, and processing | Marqeta, Galileo, GPS, i2c | Debit/credit card issuance, virtual cards, card controls |
| **BAAS** | Complete banking infrastructure | Treezor, Railsbank, Solarisbank | Account creation, ledger, regulatory compliance |
| **ACCOUNT** | Account management and ledger | Mambu, Thought Machine, Temenos | Core banking ledger, account lifecycle |
| **TRANSACTION** | Transaction processing and clearing | FIS, Fiserv, ACI Worldwide | Transaction routing, clearing, settlement |
| **COMPLIANCE** | AML, fraud detection, regulatory compliance | ComplyAdvantage, Chainalysis | Transaction monitoring, sanctions screening |
| **NOTIFICATION** | Customer communications | Twilio, SendGrid, OneSignal, Firebase | SMS, email, push notifications |
| **DOCUMENT** | Document storage and management | DocuSign, Box, Dropbox, AWS S3 | Document storage, e-signatures, archival |
| **ANALYTICS** | Data analytics and business intelligence | Mixpanel, Amplitude, Segment | Customer analytics, business reporting |

#### Provider Configuration

Each provider requires specific configuration to function properly:

```yaml
Provider: Treezor (BaaS)
├── Basic Information
│   ├── Code: TREEZOR
│   ├── Name: Treezor
│   ├── Type: BAAS
│   └── Status: ACTIVE
├── Connection Details
│   ├── Base URL: https://api.treezor.com
│   ├── API Version: v1
│   ├── Documentation: https://docs.treezor.com
│   └── Timeout: 30000ms
├── Authentication
│   ├── Client ID: (encrypted)
│   ├── Client Secret: (encrypted)
│   └── Auth Type: OAuth2
├── Support
│   ├── Support Email: support@treezor.com
│   ├── Support Phone: +33 1 23 45 67 89
│   └── SLA: 99.9% uptime
└── Metadata
    ├── Region: EU
    ├── Certifications: [PCI-DSS, ISO27001]
    └── Data Residency: France
```

#### Provider-Tenant Relationship

Providers can be associated with multiple tenants, and tenants can use multiple providers. This **many-to-many** relationship enables:

- **Shared Providers**: Multiple tenants using the same provider (cost optimization)
- **Tenant-Specific Providers**: Dedicated providers for specific tenants (compliance, performance)
- **Provider Redundancy**: Multiple providers for the same capability (failover, load balancing)

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│  Tenant A   │ ──────> │  Provider 1  │ <────── │  Tenant B   │
│  (Spain)    │         │  (Treezor)   │         │  (France)   │
└─────────────┘         └──────────────┘         └─────────────┘
      │                                                  │
      │                 ┌──────────────┐                │
      └──────────────> │  Provider 2  │ <──────────────┘
                        │  (Onfido)    │
                        └──────────────┘
```

---

### Provider Parameters

**Provider Parameters** are dynamic, configurable values that control how Firefly interacts with external providers. They enable **flexible, environment-specific, and tenant-specific configurations** without code changes.

#### Why Parameters Matter

Traditional hardcoded configurations are inflexible and risky. Parameters solve this by providing:

- **Flexibility**: Change configurations without redeploying code
- **Environment Management**: Different settings for dev, staging, production
- **Tenant Customization**: Override defaults for specific tenants
- **Security**: Encrypted storage of sensitive credentials
- **Auditability**: Track all configuration changes with timestamps and user attribution
- **Validation**: Type-safe parameters with validation rules

#### Parameter Hierarchy

Parameters follow a **resolution hierarchy** to determine the effective value:

```
┌─────────────────────────────────────────────────────────┐
│  1. Tenant-Specific Parameter (Highest Priority)        │
│     tenantId: spain-tenant-uuid                         │
│     parameterName: api_timeout                          │
│     parameterValue: 45000                               │
│     Reason: Spain tenant needs longer timeout           │
└─────────────────────────────────────────────────────────┘
                         ↓ (if not found)
┌─────────────────────────────────────────────────────────┐
│  2. Global Parameter (Default)                          │
│     tenantId: null                                      │
│     parameterName: api_timeout                          │
│     parameterValue: 30000                               │
│     Reason: Default timeout for all tenants             │
└─────────────────────────────────────────────────────────┘
                         ↓ (if not found)
┌─────────────────────────────────────────────────────────┐
│  3. Default Value (Fallback)                            │
│     defaultValue: 30000                                 │
│     Reason: Hardcoded fallback in parameter definition  │
└─────────────────────────────────────────────────────────┘
                         ↓ (if not found and required)
┌─────────────────────────────────────────────────────────┐
│  4. Error (Required parameter missing)                  │
│     Throws: ConfigurationException                      │
└─────────────────────────────────────────────────────────┘
```

**Resolution Algorithm:**
1. Check for tenant-specific parameter
2. If not found, check for global parameter
3. If not found, use default value
4. If no default and parameter is required, throw error

#### Parameter Types

Parameters support multiple data types for **type-safe configuration**:

| Type | Description | Example | Validation |
|------|-------------|---------|------------|
| **STRING** | Text values | `"sk_live_abc123xyz789"` | Max length, regex pattern |
| **INTEGER** | Numeric values | `30000` (milliseconds) | Min/max range |
| **BOOLEAN** | True/false flags | `true` | Must be true or false |
| **JSON** | Complex structured data | `{"url":"...", "events":[...]}` | Valid JSON syntax |
| **URL** | Web addresses | `"https://api.example.com"` | Valid URL format |
| **EMAIL** | Email addresses | `"alerts@mybank.com"` | Valid email format |

#### Parameter Categories

Parameters are organized into logical categories for better management:

- **connection**: API endpoints, timeouts, retry policies, connection pooling
- **authentication**: Credentials, tokens, auth methods, OAuth configuration
- **webhook**: Webhook URLs, secrets, event subscriptions, retry policies
- **features**: Feature flags, capabilities, limits, experimental features
- **business**: Business rules, fees, limits, thresholds
- **compliance**: Regulatory settings, reporting, data retention

---

### Provider Value Mappings

**Provider Value Mappings** solve the critical problem of **data format inconsistencies** between Firefly's internal representation and external provider formats.

#### The Problem

Different providers use different formats for the same concept, creating integration complexity:

```
Concept: Account Status

Firefly Internal:  "ACTIVE"
Provider A:        "active"
Provider B:        "1"
Provider C:        "ENABLED"
Provider D:        "A"
Provider E:        "true"
```

Without mappings, you'd need custom code for each provider. With mappings, it's **pure configuration**.

#### How Mappings Work

Value mappings provide **bidirectional translation** between Firefly and provider formats:

```
┌──────────────────────────────────────────────────────────┐
│  Firefly sends: status = "ACTIVE"                        │
└──────────────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────────────┐
│  Value Mapping Lookup                                    │
│  providerId: treezor-uuid                                │
│  mappingType: ACCOUNT_STATUS                             │
│  fireflyValue: "ACTIVE"                                  │
│  providerValue: "1"                                      │
│  direction: BIDIRECTIONAL                                │
└──────────────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────────────┐
│  Provider receives: status = "1"                         │
└──────────────────────────────────────────────────────────┘
```

#### Bidirectional Mapping

Mappings work in both directions for seamless integration:

**Outbound (Firefly → Provider)**
```
Firefly: "PENDING_APPROVAL" → Provider: "2"
```

**Inbound (Provider → Firefly)**
```
Provider: "2" → Firefly: "PENDING_APPROVAL"
```

#### Common Mapping Types

- **ACCOUNT_STATUS**: Account state mappings (ACTIVE, SUSPENDED, CLOSED)
- **TRANSACTION_TYPE**: Transaction category mappings (TRANSFER, PAYMENT, WITHDRAWAL)
- **CURRENCY_CODE**: Currency format mappings (EUR, USD, GBP)
- **COUNTRY_CODE**: Country identifier mappings (ES, FR, DE)
- **DOCUMENT_TYPE**: Document category mappings (PASSPORT, ID_CARD, DRIVER_LICENSE)
- **ERROR_CODE**: Error code translations for consistent error handling
- **PAYMENT_METHOD**: Payment method mappings (CARD, BANK_TRANSFER, DIRECT_DEBIT)

---

### Tenant Branding

**Tenant Branding** enables **complete visual customization** of the banking experience per tenant, allowing each tenant to maintain its unique brand identity while sharing the underlying platform infrastructure.

#### Why Branding Matters

In multi-tenant banking platforms, branding is critical for:

1. **Brand Identity**: Each tenant maintains its unique visual identity
2. **Customer Trust**: Consistent branding builds customer confidence
3. **Market Differentiation**: Stand out in competitive markets
4. **White-Label**: Enable partners to offer banking under their own brand
5. **Regional Adaptation**: Adapt visual elements for different markets

#### Branding Components

##### Visual Identity
- **Logos**: Primary logo, dark mode logo, favicon, email header
- **Colors**: Primary, secondary, accent, background, text, success, warning, error
- **Typography**: Font families for headings, body text, monospace, button text
- **Custom CSS**: Advanced styling overrides for complete control

##### Email Templates
- **Header Images**: Branded email headers with logo
- **Footer Text**: Legal disclaimers, contact information, unsubscribe links
- **Color Schemes**: Consistent with web/mobile branding
- **Templates**: Welcome, transaction alerts, statements, marketing

#### Branding Architecture

```yaml
┌─────────────────────────────────────────────────────────┐
│  Tenant: PremiumBank                                    │
├─────────────────────────────────────────────────────────┤
│  Logos                                                  │
│  ├── Light Mode: /assets/premium-logo-light.svg        │
│  ├── Dark Mode:  /assets/premium-logo-dark.svg         │
│  ├── Favicon:    /assets/premium-favicon.ico           │
│  └── Email:      /assets/premium-email-header.png      │
├─────────────────────────────────────────────────────────┤
│  Colors                                                 │
│  ├── Primary:    #1A237E (Deep Blue)                   │
│  ├── Secondary:  #FFD700 (Gold)                        │
│  ├── Accent:     #00BCD4 (Cyan)                        │
│  ├── Background: #FFFFFF (White)                       │
│  ├── Text:       #212121 (Dark Gray)                   │
│  ├── Success:    #4CAF50 (Green)                       │
│  ├── Warning:    #FF9800 (Orange)                      │
│  └── Error:      #F44336 (Red)                         │
├─────────────────────────────────────────────────────────┤
│  Typography                                             │
│  ├── Headings:   'Playfair Display', serif             │
│  ├── Body:       'Roboto', sans-serif                  │
│  ├── Monospace:  'Roboto Mono', monospace              │
│  └── Buttons:    'Roboto', sans-serif, 600 weight      │
├─────────────────────────────────────────────────────────┤
│  Custom CSS                                             │
│  └── .premium-card {                                   │
│        box-shadow: 0 4px 6px rgba(0,0,0,0.1);          │
│        border-radius: 8px;                             │
│        background: linear-gradient(135deg, ...);       │
│      }                                                  │
└─────────────────────────────────────────────────────────┘
```

#### Use Cases

**White-Label Banking**: Retail companies offering banking services under their own brand
**Multi-Brand Strategy**: Single institution operating multiple brands for different segments
**Regional Customization**: Adapting branding for different markets and cultures
**A/B Testing**: Testing different visual identities to optimize conversion
**Partner Programs**: Enabling partners to offer co-branded banking services

---

### Feature Flags

**Feature Flags** (also known as feature toggles) enable **controlled rollout of new features** and **runtime configuration** without code deployments, providing unprecedented flexibility and risk mitigation in production environments.

#### Why Feature Flags Matter

In enterprise banking platforms, feature flags are critical for:

1. **Progressive Rollout**: Deploy features to a subset of users before full release
2. **A/B Testing**: Test different feature variations to optimize user experience
3. **Kill Switch**: Instantly disable problematic features without redeployment
4. **Tenant-Specific Features**: Enable features for specific tenants based on their subscription
5. **Environment Control**: Different feature sets for dev, staging, and production
6. **Risk Mitigation**: Reduce deployment risk by decoupling deployment from release

#### Feature Flag Configuration

```yaml
Feature Flag: Advanced Analytics Dashboard
├── Basic Information
│   ├── Feature Key: advanced_analytics_dashboard
│   ├── Feature Name: Advanced Analytics Dashboard
│   ├── Enabled: true
│   └── Environment: production
├── Rollout Strategy
│   ├── Rollout Percentage: 25%
│   ├── Start Date: 2025-01-15
│   ├── End Date: 2025-02-15
│   └── Target User Segments: ["premium", "enterprise"]
└── Tenant Association
    ├── Tenant ID: spain-tenant-uuid (optional)
    └── Global: false (tenant-specific)
```

#### Rollout Strategies

- **Percentage Rollout**: Gradually increase from 0% to 100% based on user hash
- **Tenant-Specific**: Enable for specific tenants only
- **Time-Based**: Automatically enable/disable based on date range
- **User Segment**: Target specific user segments (premium, trial, enterprise)
- **Environment-Based**: Different flags for dev, staging, production

---

### Tenant Settings

**Tenant Settings** provide **comprehensive operational configuration** for each tenant, including rate limiting, security policies, circuit breakers, maintenance windows, and compliance settings.

#### Why Tenant Settings Matter

Enterprise banking requires fine-grained control over:

1. **Rate Limiting**: Prevent abuse and ensure fair resource usage
2. **Security Policies**: Password requirements, MFA, session management
3. **Circuit Breakers**: Automatic failure detection and recovery
4. **Maintenance Windows**: Scheduled maintenance with minimal disruption
5. **Compliance**: GDPR, PCI-DSS, SOX compliance flags
6. **Data Retention**: Automated data lifecycle management

#### Settings Categories

##### Rate Limiting
```yaml
Rate Limiting Configuration:
├── Per Minute: 100 requests
├── Per Hour: 5,000 requests
└── Per Day: 100,000 requests
```

##### Security Policies
```yaml
Security Configuration:
├── Password
│   ├── Min Length: 12 characters
│   ├── Require Uppercase: true
│   ├── Require Lowercase: true
│   ├── Require Numbers: true
│   └── Require Special Chars: true
├── Authentication
│   ├── MFA Enabled: true
│   ├── Session Timeout: 30 minutes
│   └── Max Login Attempts: 5
└── Account Lockout
    ├── Lockout Duration: 30 minutes
    └── Lockout After: 5 failed attempts
```

##### Circuit Breaker
```yaml
Circuit Breaker Configuration:
├── Enabled: true
├── Failure Threshold: 50% (failures/total)
├── Timeout: 60 seconds
└── Half-Open Retry: 30 seconds
```

##### Maintenance Windows
```yaml
Maintenance Configuration:
├── Maintenance Mode: false
├── Maintenance Message: "Scheduled maintenance in progress"
├── Start Time: 2025-01-20 02:00:00 UTC
└── End Time: 2025-01-20 04:00:00 UTC
```

##### Compliance Flags
```yaml
Compliance Configuration:
├── GDPR Compliant: true
├── PCI-DSS Compliant: true
└── SOX Compliant: false
```

##### Data Retention
```yaml
Data Retention Configuration:
├── Audit Retention Days: 2555 (7 years)
├── Data Retention Days: 365 (1 year)
└── Auto Delete Expired Data: true
```

##### Notifications
```yaml
Notification Configuration:
├── Email Notifications: true
├── SMS Notifications: true
└── Push Notifications: true
```

---

### Webhook Configurations

**Webhook Configurations** enable **centralized management of webhook integrations** with external systems, providing authentication, retry logic, and event filtering capabilities.

#### Why Webhook Configuration Matters

Modern banking platforms need to integrate with numerous external systems:

1. **Event-Driven Architecture**: Real-time notifications to external systems
2. **Centralized Management**: Single source of truth for all webhooks
3. **Security**: Encrypted credentials and signature verification
4. **Reliability**: Automatic retry with exponential backoff
5. **Flexibility**: Per-tenant and per-provider webhook configurations
6. **Observability**: Track webhook delivery success/failure rates

#### Webhook Configuration

```yaml
Webhook: Transaction Notifications
├── Basic Information
│   ├── Webhook Name: Transaction Notifications
│   ├── Webhook URL: https://partner.example.com/webhooks/transactions
│   ├── HTTP Method: POST
│   ├── Enabled: true
│   └── Timeout: 30 seconds
├── Event Filtering
│   ├── Event Types: ["transaction.created", "transaction.completed", "transaction.failed"]
│   └── Event Filter: {"amount": {"gt": 1000}}
├── Authentication
│   ├── Auth Type: BEARER_TOKEN
│   ├── Auth Header Name: Authorization
│   ├── Auth Header Value: Bearer sk_live_abc123xyz789 (encrypted)
│   └── Secret Key: whsec_abc123xyz789 (for signature verification)
├── Retry Configuration
│   ├── Retry Enabled: true
│   ├── Max Retry Attempts: 5
│   ├── Retry Delay: 60 seconds
│   └── Backoff Multiplier: 2.0 (exponential backoff)
└── Custom Headers
    ├── X-Tenant-ID: spain-tenant-uuid
    ├── X-Environment: production
    └── X-API-Version: v1
```

#### Authentication Types

- **BEARER_TOKEN**: OAuth 2.0 bearer token authentication
- **API_KEY**: API key in header or query parameter
- **BASIC_AUTH**: HTTP Basic Authentication
- **HMAC**: HMAC signature verification
- **CUSTOM**: Custom authentication headers

#### Retry Strategy

Webhooks use **exponential backoff** for reliability:

```
Attempt 1: Immediate
Attempt 2: 60 seconds later
Attempt 3: 120 seconds later (60 * 2.0)
Attempt 4: 240 seconds later (120 * 2.0)
Attempt 5: 480 seconds later (240 * 2.0)
```

---

### Configuration Audit

**Configuration Audit** provides **complete audit trail** of all configuration changes with rollback capabilities, ensuring compliance, accountability, and operational safety.

#### Why Configuration Audit Matters

Enterprise banking requires comprehensive audit trails for:

1. **Regulatory Compliance**: SOX, GDPR, PCI-DSS audit requirements
2. **Change Tracking**: Who changed what, when, and why
3. **Rollback Capability**: Restore previous configurations when needed
4. **Security**: Detect unauthorized changes and security breaches
5. **Troubleshooting**: Understand what changed before an incident
6. **Accountability**: Clear attribution of all configuration changes

#### Audit Entry Structure

```yaml
Audit Entry: Provider Configuration Change
├── Change Information
│   ├── Entity Type: Provider
│   ├── Entity ID: treezor-provider-uuid
│   ├── Action: UPDATE
│   ├── Field Name: baseUrl
│   ├── Old Value: https://api.treezor.com/v1
│   └── New Value: https://api.treezor.com/v2
├── User Attribution
│   ├── Changed By User ID: admin-user-uuid
│   ├── Changed By Username: admin@mybank.com
│   └── Change Reason: "Migrating to API v2 for better performance"
├── Security Context
│   ├── IP Address: 203.0.113.42
│   ├── User Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)
│   └── Timestamp: 2025-01-15 14:30:00 UTC
└── Rollback
    ├── Rollback Available: true
    └── Rollback Script: UPDATE providers SET base_url = 'https://api.treezor.com/v1' WHERE id = 'treezor-provider-uuid'
```

#### Audited Actions

- **CREATE**: New entity creation
- **UPDATE**: Entity modification
- **DELETE**: Entity deletion (soft or hard)
- **ACTIVATE**: Entity activation
- **DEACTIVATE**: Entity deactivation
- **ROLLBACK**: Configuration rollback

#### Audit Immutability

**Audit entries are immutable** - they cannot be modified or deleted once created. This ensures:
- **Tamper-proof**: No one can alter the audit trail
- **Compliance**: Meets regulatory requirements for audit logs
- **Trust**: Complete confidence in audit data integrity

---

### Environment Configurations

**Environment Configurations** enable **environment-specific settings** (dev, staging, production) with support for secrets management and configuration inheritance.

#### Why Environment Configuration Matters

Modern banking platforms operate across multiple environments:

1. **Environment Isolation**: Different settings for dev, staging, production
2. **Secrets Management**: Secure storage of environment-specific secrets
3. **Configuration Inheritance**: Override global defaults per environment
4. **Deployment Safety**: Prevent production credentials in non-production environments
5. **Testing**: Realistic testing with environment-specific configurations
6. **Compliance**: Separate production data from non-production environments

#### Environment Configuration Structure

```yaml
Environment Config: Production Database
├── Basic Information
│   ├── Environment Name: production
│   ├── Config Key: database.connection.url
│   ├── Config Value: postgresql://prod-db.example.com:5432/firefly
│   ├── Config Type: STRING
│   └── Is Secret: false
├── Organization
│   ├── Category: database
│   └── Description: Production database connection URL
└── Tenant Association
    ├── Tenant ID: spain-tenant-uuid (optional)
    └── Global: false (tenant-specific)
```

#### Configuration Types

- **STRING**: Text values (URLs, names, descriptions)
- **INTEGER**: Numeric values (ports, timeouts, limits)
- **BOOLEAN**: True/false flags (feature toggles, enabled/disabled)
- **JSON**: Complex structured data (arrays, objects)
- **SECRET**: Encrypted sensitive values (passwords, API keys, tokens)

#### Environment Hierarchy

```
┌─────────────────────────────────────────────────────────┐
│  1. Tenant-Specific Environment Config (Highest)        │
│     tenantId: spain-tenant-uuid                         │
│     environment: production                             │
│     configKey: api.timeout                              │
│     configValue: 45000                                  │
└─────────────────────────────────────────────────────────┘
                         ↓ (if not found)
┌─────────────────────────────────────────────────────────┐
│  2. Global Environment Config                           │
│     tenantId: null                                      │
│     environment: production                             │
│     configKey: api.timeout                              │
│     configValue: 30000                                  │
└─────────────────────────────────────────────────────────┘
                         ↓ (if not found)
┌─────────────────────────────────────────────────────────┐
│  3. Default Environment Config                          │
│     environment: default                                │
│     configKey: api.timeout                              │
│     configValue: 20000                                  │
└─────────────────────────────────────────────────────────┘
```

#### Secrets Management

Sensitive configuration values are **encrypted at rest**:
- **Encryption**: AES-256 encryption for secret values
- **Access Control**: Only authorized services can decrypt secrets
- **Audit Trail**: All secret access is logged
- **Rotation**: Support for secret rotation without downtime

---

### Channel Configurations

**Channel Configurations** enable **centralized management of banking channels** (Web Banking, Mobile, ATM, Branch, etc.) with comprehensive control over features, limits, security, and availability per tenant.

#### Why Channel Configuration Matters

Modern banking platforms operate across multiple channels, each with unique requirements:

1. **Channel Enablement**: Enable/disable specific channels per tenant
2. **Feature Control**: Configure which features are available per channel
3. **Transaction Limits**: Set channel-specific transaction limits and restrictions
4. **Security Policies**: Define authentication and security requirements per channel
5. **Availability Management**: Configure operating hours and maintenance windows
6. **Monitoring & Alerts**: Track channel performance and detect anomalies
7. **Compliance**: Ensure regulatory compliance per channel (geo-restrictions, audit trails)

#### Channel Configuration Structure

```yaml
Channel Config: Mobile Banking
├── Basic Information
│   ├── Channel Code: MOBILE_BANKING
│   ├── Channel Name: Mobile Banking
│   ├── Description: iOS and Android mobile banking app
│   └── Enabled: true
├── Availability
│   ├── Available 24/7: true
│   ├── Operating Hours: N/A (24/7)
│   ├── Operating Days: All days
│   └── Timezone: America/New_York
├── Transaction Limits
│   ├── Max Transaction Amount: $10,000
│   ├── Min Transaction Amount: $0.01
│   ├── Daily Transaction Limit: $50,000
│   ├── Max Transactions/Day: 100
│   └── Max Transactions/Hour: 20
├── Supported Features
│   ├── Transfers: ✅ Enabled
│   ├── Payments: ✅ Enabled
│   ├── Deposits: ❌ Disabled (mobile check deposit coming soon)
│   ├── Withdrawals: ❌ Disabled (ATM only)
│   ├── Account Opening: ✅ Enabled
│   ├── Loan Applications: ✅ Enabled
│   ├── Card Management: ✅ Enabled
│   └── Bill Payments: ✅ Enabled
├── Security Configuration
│   ├── Requires MFA: true
│   ├── Requires Biometric: true (Face ID/Touch ID)
│   ├── Session Timeout: 15 minutes
│   ├── Max Concurrent Sessions: 3
│   ├── IP Whitelist: Disabled
│   └── Geo Restriction: Enabled (US, CA, MX only)
├── Rate Limiting
│   ├── Rate Limit Enabled: true
│   ├── Requests/Minute: 60
│   └── Requests/Hour: 1,000
├── Channel-Specific Config
│   ├── Mobile App Version Min: 2.5.0
│   ├── Mobile Deep Link: mybank://home
│   └── API Version: v2
└── Monitoring & Compliance
    ├── Monitoring Enabled: true
    ├── Alert on High Volume: true
    ├── Alert Threshold: 1,000 transactions
    ├── Audit All Transactions: true
    └── Require Approval: false
```

#### Supported Channel Types

1. **WEB_BANKING**: Desktop/laptop web browser access
2. **MOBILE_BANKING**: iOS and Android mobile apps
3. **ATM**: Automated Teller Machine network
4. **BRANCH**: In-person branch/teller transactions
5. **CALL_CENTER**: Phone banking and customer service
6. **API**: Direct API access for integrations
7. **OPEN_BANKING**: Open Banking API (PSD2, UK Open Banking, etc.)

#### Channel Priority & Failover

Channels can be configured with **priority levels** and **failover channels**:

```
Primary Channel: WEB_BANKING (Priority: 1)
    ↓ (if unavailable)
Failover Channel: MOBILE_BANKING (Priority: 2)
    ↓ (if unavailable)
Fallback: CALL_CENTER (Priority: 3)
```

This ensures **high availability** and **business continuity** even when primary channels are down.

#### Maintenance Mode

Each channel can be put into **maintenance mode** independently:

```yaml
Maintenance Mode:
├── Enabled: true
├── Message: "Mobile Banking is undergoing scheduled maintenance"
├── Start Time: 2025-10-26 02:00:00 UTC
└── End Time: 2025-10-26 04:00:00 UTC
```

During maintenance, users see a friendly message and can use alternative channels.

#### Real-World Example: Multi-Channel Configuration

**Scenario**: A bank wants to configure different limits for different channels

```yaml
Web Banking:
  - Max Transaction: $50,000
  - Daily Limit: $200,000
  - Requires MFA: true
  - Available: 24/7

Mobile Banking:
  - Max Transaction: $10,000
  - Daily Limit: $50,000
  - Requires MFA: true
  - Requires Biometric: true
  - Available: 24/7

ATM:
  - Max Transaction: $500 (withdrawal)
  - Daily Limit: $1,000
  - Requires PIN: true
  - Available: 24/7

Branch:
  - Max Transaction: $100,000
  - Daily Limit: Unlimited
  - Requires ID Verification: true
  - Operating Hours: 9:00 AM - 5:00 PM (Mon-Fri)
```

This allows the bank to **balance convenience with security** across different channels.

---

## 🏗️ Architecture Overview

### System Architecture

The Configuration Management Service follows a **layered architecture** with clear separation of concerns:

```
┌───────────────────────────────────────────────────────────────────┐
│                         Client Applications                        │
│  (Web App, Mobile App, Admin Portal, Partner Integrations)        │
└───────────────────────────────────────────────────────────────────┘
                                 ↓ HTTPS/REST
┌───────────────────────────────────────────────────────────────────┐
│                          API Gateway                               │
│  (Authentication, Rate Limiting, Request Routing, Load Balancing) │
└───────────────────────────────────────────────────────────────────┘
                                 ↓
┌───────────────────────────────────────────────────────────────────┐
│              Configuration Management Service (This Service)       │
│                                                                    │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │                    Web Layer (REST API)                      │ │
│  │  • Controllers  • Request Validation  • OpenAPI/Swagger      │ │
│  │  • Exception Handling  • Response Formatting                 │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                                 ↓                                  │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │                   Service Layer (Business Logic)             │ │
│  │  • Services  • Mappers  • Validation  • Business Rules      │ │
│  │  • Transaction Management  • Event Publishing                │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                                 ↓                                  │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │                   Data Layer (Persistence)                   │ │
│  │  • Repositories  • Entities  • R2DBC  • Flyway              │ │
│  │  • Query Optimization  • Connection Pooling                  │ │
│  └─────────────────────────────────────────────────────────────┘ │
└───────────────────────────────────────────────────────────────────┘
                                 ↓ R2DBC (Reactive)
┌───────────────────────────────────────────────────────────────────┐
│                        PostgreSQL Database                         │
│  (Tenants, Providers, Parameters, Mappings, Branding)             │
│  • ACID Transactions  • JSON Support  • Full-Text Search          │
└───────────────────────────────────────────────────────────────────┘
```

### Module Structure

The service follows a **multi-module Maven project** structure for clear separation of concerns:

```
common-platform-config-mgmt/
│
├── common-platform-config-mgmt-models/
│   ├── Entities (JPA/R2DBC entities)
│   ├── Repositories (Data access layer)
│   ├── Migrations (Flyway SQL scripts)
│   └── Purpose: Data model and persistence
│
├── common-platform-config-mgmt-interfaces/
│   ├── DTOs (Data Transfer Objects)
│   ├── API Contracts (Request/Response models)
│   └── Purpose: API contracts and data transfer
│
├── common-platform-config-mgmt-core/
│   ├── Services (Business logic)
│   ├── Mappers (Entity ↔ DTO conversion)
│   ├── Validators (Business rule validation)
│   └── Purpose: Business logic and orchestration
│
├── common-platform-config-mgmt-web/
│   ├── Controllers (REST endpoints)
│   ├── Configuration (Spring configuration)
│   ├── Exception Handlers (Error handling)
│   └── Purpose: HTTP API and web layer
│
└── common-platform-config-mgmt-sdk/
    ├── Generated Client (Auto-generated from OpenAPI)
    └── Purpose: Client library for consumers
```

### Design Principles

1. **Separation of Concerns**: Each layer has a single, well-defined responsibility
2. **Dependency Inversion**: Depend on abstractions, not concrete implementations
3. **Reactive Programming**: Non-blocking I/O for high throughput and scalability
4. **Immutability**: Immutable DTOs and value objects for thread safety
5. **Fail-Fast**: Validate early and fail fast with clear error messages
6. **Observability**: Comprehensive logging, metrics, and tracing

---

## 💻 Technology Stack

### Core Technologies

#### Java 21 (LTS)
Latest Long-Term Support version with modern language features:
- **Virtual Threads**: Lightweight concurrency for high throughput (Project Loom)
- **Pattern Matching**: Cleaner, more expressive code with pattern matching for switch
- **Records**: Immutable data carriers with automatic equals/hashCode/toString
- **Sealed Classes**: Controlled inheritance hierarchies for better type safety
- **Text Blocks**: Multi-line string literals for better readability

#### Spring Boot 3.2.2
Enterprise-grade application framework with:
- **Auto-configuration**: Rapid application setup with sensible defaults
- **Production-ready**: Built-in metrics, health checks, and monitoring
- **Extensive Ecosystem**: Integration with countless libraries and frameworks
- **Enterprise Support**: Battle-tested in production environments worldwide
- **Spring Native**: GraalVM native image support for faster startup and lower memory

#### Spring WebFlux
Reactive, non-blocking web framework:
- **Reactive Programming**: Non-blocking I/O for scalability and efficiency
- **Backpressure**: Automatic flow control to prevent overwhelming consumers
- **Event-Driven**: Efficient resource utilization with event-driven architecture
- **High Throughput**: Handle thousands of concurrent requests with minimal resources
- **Functional Endpoints**: Functional programming style for route definitions

#### R2DBC (Reactive Relational Database Connectivity)
Reactive database access:
- **Fully Reactive**: End-to-end non-blocking database access
- **PostgreSQL Support**: Native reactive PostgreSQL driver
- **Connection Pooling**: Efficient connection management with R2DBC Pool
- **Transaction Management**: ACID guarantees with reactive streams
- **Performance**: Lower latency and higher throughput than JDBC

### Supporting Technologies

| Technology | Purpose | Benefits |
|------------|---------|----------|
| **PostgreSQL 14+** | Primary database | ACID compliance, JSON support, full-text search, scalability |
| **Flyway** | Database migrations | Version control, repeatable migrations, rollback support |
| **MapStruct** | Object mapping | Type-safe, compile-time generation, high performance |
| **Lombok** | Boilerplate reduction | Less code, better readability, maintainability |
| **OpenAPI 3.0** | API documentation | Interactive docs, client generation, contract-first |
| **Maven** | Build tool | Dependency management, multi-module builds, plugins |
| **SLF4J + Logback** | Logging | Structured logging, log levels, multiple appenders |
| **JUnit 5** | Testing framework | Modern testing, parameterized tests, nested tests |
| **Mockito** | Mocking framework | Unit test isolation, behavior verification |
| **Testcontainers** | Integration testing | Real database testing with Docker containers |

---

## 📊 Data Model

### Entity Relationship Diagram

```
┌─────────────────┐
│  TenantStatus   │
│  ─────────────  │
│  • id (PK)      │
│  • code         │
│  • name         │
│  • description  │
└────────┬────────┘
         │ 1
         │
         │ N
┌────────┴────────┐         ┌──────────────────┐         ┌──────────────────┐
│     Tenant      │ 1 ─── 1 │ TenantBranding   │         │ TenantSettings   │
│  ─────────────  │         │  ──────────────  │         │  ──────────────  │
│  • id (PK)      │         │  • id (PK)       │         │  • id (PK)       │
│  • code         │         │  • tenantId (FK) │         │  • tenantId (FK) │
│  • name         │         │  • logoUrl       │         │  • rateLimitPM   │
│  • countryId    │         │  • primaryColor  │         │  • mfaEnabled    │
│  • statusId(FK) │         │  • customCss     │         │  • gdprCompliant │
│  • metadata     │         │  • createdAt     │         │  • ... (40+ cfg) │
└────────┬────────┘         └──────────────────┘         └──────────────────┘
         │ N                                                      ↑ 1
         │                                                        │
         │ M (via ProviderTenant)                                │ 1
         │                                                        │
┌────────┴────────┐                                      ┌────────┴────────┐
│  ProviderTenant │                                      │  FeatureFlag    │
│  ─────────────  │                                      │  ─────────────  │
│  • id (PK)      │                                      │  • id (PK)      │
│  • tenantId(FK) │                                      │  • tenantId(FK) │
│  • providerId   │                                      │  • featureKey   │
│  • isPrimary    │                                      │  • enabled      │
│  • priority     │                                      │  • rollout%     │
└────────┬────────┘                                      └─────────────────┘
         │ N                                                      ↑ 1
         │                                                        │
         │ 1                                                      │ N
         │                                              ┌─────────┴─────────┐
         │                                              │  ChannelConfig    │
         │                                              │  ───────────────  │
         │                                              │  • id (PK)        │
         │                                              │  • tenantId (FK)  │
         │                                              │  • channelCode    │
         │                                              │  • channelName    │
         │                                              │  • enabled        │
         │                                              │  • maxTxAmount    │
         │                                              │  • requiresMfa    │
         │                                              │  • ... (50+ cfg)  │
         │                                              └───────────────────┘
         │ N
         │
         │ 1
┌────────┴────────┐         ┌──────────────────┐         ┌──────────────────┐
│    Provider     │ 1 ─── N │ProviderParameter │         │ WebhookConfig    │
│  ─────────────  │         │  ──────────────  │         │  ──────────────  │
│  • id (PK)      │         │  • id (PK)       │         │  • id (PK)       │
│  • code         │         │  • providerId(FK)│         │  • tenantId (FK) │
│  • name         │         │  • tenantId (FK) │         │  • providerId    │
│  • typeId (FK)  │         │  • parameterName │         │  • webhookUrl    │
│  • statusId(FK) │         │  • parameterValue│         │  • authType      │
│  • baseUrl      │         │  • parameterType │         │  • retryEnabled  │
│  • metadata     │         │  • isSecret      │         │  • eventTypes    │
└────────┬────────┘         └──────────────────┘         └──────────────────┘
         │ 1                                                      ↑ N
         │                                                        │
         │ N                                                      │ 1
┌────────┴─────────────┐                              ┌───────────┴──────────┐
│ProviderValueMapping  │                              │EnvironmentConfig     │
│  ──────────────────  │                              │  ──────────────────  │
│  • id (PK)           │                              │  • id (PK)           │
│  • providerId (FK)   │                              │  • tenantId (FK)     │
│  • mappingType       │                              │  • environmentName   │
│  • fireflyValue      │                              │  • configKey         │
│  • providerValue     │                              │  • configValue       │
│  • description       │                              │  • isSecret          │
└──────────────────────┘                              └──────────────────────┘

                    ┌──────────────────────┐
                    │ ConfigurationAudit   │
                    │  ──────────────────  │
                    │  • id (PK)           │
                    │  • entityType        │
                    │  • entityId          │
                    │  • action            │
                    │  • fieldName         │
                    │  • oldValue          │
                    │  • newValue          │
                    │  • changedByUserId   │
                    │  • changeReason      │
                    │  • rollbackAvailable │
                    │  • ipAddress         │
                    │  • createdAt         │
                    └──────────────────────┘
```

### Key Tables

#### Tenants (3 tables)
- **tenant_statuses**: TRIAL, ACTIVE, SUSPENDED, MAINTENANCE, INACTIVE, DELETED
- **tenants**: Core tenant configuration and settings
- **tenant_brandings**: Visual customization and branding per tenant

#### Providers (7 tables)
- **provider_types**: KYC, PAYMENT, CARD, BAAS, ACCOUNT, TRANSACTION, COMPLIANCE, NOTIFICATION, DOCUMENT, ANALYTICS
- **provider_statuses**: ACTIVE, INACTIVE, MAINTENANCE, DEPRECATED
- **providers**: Provider configurations and connection details
- **provider_tenants**: Many-to-many tenant-provider associations with priority
- **provider_parameters**: Dynamic configuration values (global and tenant-specific)
- **provider_value_mappings**: Value translation rules for provider integration
- **webhook_configs**: Centralized webhook management with authentication and retry logic

#### Enterprise Features (6 tables)
- **feature_flags**: Feature toggles with rollout percentage and targeting
- **tenant_settings**: Comprehensive operational settings (rate limiting, security, circuit breakers, compliance)
- **configuration_audits**: Complete audit trail of all configuration changes with rollback capability
- **environment_configs**: Environment-specific configurations (dev, staging, production) with secrets management
- **channel_configs**: Banking channel configurations (Web, Mobile, ATM, Branch, etc.) with features, limits, and security

### Database Features

- **UUID Primary Keys**: Globally unique identifiers for distributed systems
- **JSON Columns**: Flexible metadata storage with JSON support
- **Timestamps**: Created/updated timestamps for audit trails
- **Soft Deletes**: Logical deletion with retention for compliance
- **Indexes**: Optimized indexes for common query patterns
- **Constraints**: Foreign keys, unique constraints, check constraints

---

## 🚀 Quick Start Guide

### Step 1: Prerequisites

Ensure you have the following installed:

```bash
# Check Java version (must be 21+)
java -version
# Expected: openjdk version "21.0.x"

# Check Maven version (must be 3.8+)
mvn -version
# Expected: Apache Maven 3.8.x or higher

# Check PostgreSQL version (must be 14+)
psql --version
# Expected: psql (PostgreSQL) 14.x or higher
```

### Step 2: Database Setup

```sql
-- Create database
CREATE DATABASE firefly_config;

-- Create user (if needed)
CREATE USER firefly_user WITH PASSWORD 'secure_password_here';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE firefly_config TO firefly_user;

-- Connect to database
\c firefly_config

-- Grant schema privileges
GRANT ALL ON SCHEMA public TO firefly_user;
```

### Step 3: Configuration

Create or edit `common-platform-config-mgmt-web/src/main/resources/application.yml`:

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/firefly_config
    username: firefly_user
    password: secure_password_here
    pool:
      initial-size: 10
      max-size: 50
      max-idle-time: 30m
      max-acquire-time: 3s
      max-create-connection-time: 5s

  flyway:
    url: jdbc:postgresql://localhost:5432/firefly_config
    user: firefly_user
    password: secure_password_here
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true

server:
  port: 8080
  compression:
    enabled: true
  http2:
    enabled: true

logging:
  level:
    com.firefly: DEBUG
    org.springframework.r2dbc: DEBUG
    org.springframework.web: INFO
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
```

### Step 4: Build and Run

```bash
# Clone repository
git clone https://github.com/firefly-oss/common-platform-config-mgmt.git
cd common-platform-config-mgmt

# Build project (skip tests for faster build)
mvn clean install -DskipTests

# Run database migrations (automatic on startup)
# Migrations are in: common-platform-config-mgmt-models/src/main/resources/db/migration

# Run service
cd common-platform-config-mgmt-web
mvn spring-boot:run

# Or run the JAR directly
java -jar target/common-platform-config-mgmt-web-1.0.0-SNAPSHOT.jar
```

### Step 5: Verify Installation

```bash
# Check health endpoint
curl http://localhost:8080/actuator/health

# Expected response:
# {"status":"UP","components":{"db":{"status":"UP"}}}

# Access Swagger UI
open http://localhost:8080/swagger-ui.html

# Or use curl to test API
curl http://localhost:8080/api/v1/tenants
```

---

## 🎯 Use Cases

### Use Case 1: Multi-Brand Banking Institution

**Scenario**: A traditional bank wants to launch a digital-only brand targeting millennials while maintaining its premium brand for high-net-worth individuals.

**Challenge**:
- Different target audiences with different needs
- Separate branding and user experiences
- Shared infrastructure for cost efficiency
- Independent product offerings and pricing

**Solution with Firefly**:

```yaml
Tenant 1: "PremiumBank"
├── Target Audience: High-net-worth individuals (>$1M assets)
├── Branding:
│   ├── Colors: Deep blue (#1A237E) and gold (#FFD700)
│   ├── Typography: Serif fonts for elegance
│   └── Logo: Sophisticated, traditional design
├── Products:
│   ├── Premium checking (no fees, concierge service)
│   ├── Wealth management and investment advisory
│   ├── Exclusive credit cards (metal cards, premium rewards)
│   └── Private banking services
├── Providers:
│   ├── KYC: Enhanced due diligence provider
│   ├── Payments: International wire transfer specialist
│   ├── Cards: Premium card issuer (metal cards)
│   └── Compliance: Enhanced AML/KYC monitoring
├── Business Rules:
│   ├── Minimum balance: $100,000
│   ├── No transaction fees
│   ├── Dedicated relationship manager
│   └── Priority customer support
└── Revenue Model: Asset-based fees, premium services

Tenant 2: "DigitalBank"
├── Target Audience: Millennials and Gen Z (18-35 years)
├── Branding:
│   ├── Colors: Vibrant purple (#9C27B0) and cyan (#00BCD4)
│   ├── Typography: Modern sans-serif fonts
│   └── Logo: Minimalist, tech-forward design
├── Products:
│   ├── No-fee checking accounts
│   ├── Budgeting and savings tools
│   ├── Instant peer-to-peer payments
│   └── Cryptocurrency integration
├── Providers:
│   ├── KYC: Fast, mobile-optimized verification
│   ├── Payments: Instant payment provider
│   ├── Notifications: Push notifications, in-app messaging
│   └── Analytics: User behavior analytics
├── Business Rules:
│   ├── No minimum balance
│   ├── No monthly fees
│   ├── Gamified savings features
│   └── 24/7 chatbot support
└── Revenue Model: Interchange fees, premium features
```

**Benefits**:
- ✅ Single platform, multiple brands
- ✅ Shared infrastructure reduces costs by 60%
- ✅ Independent branding and user experiences
- ✅ Rapid launch of new brands (weeks vs. months)

---

### Use Case 2: Geographic Expansion

**Scenario**: A successful neobank in Spain wants to expand to France and Germany while complying with local regulations.

**Challenge**:
- Different regulatory requirements per country
- Local payment systems and providers
- Currency and language localization
- Regional compliance and reporting

**Solution with Firefly**:

```yaml
Tenant: "SpainBranch"
├── Country: Spain (ES)
├── Currency: EUR
├── Language: Spanish (es-ES)
├── Timezone: Europe/Madrid
├── Providers:
│   ├── Payments: Iberpay (Spanish payment system)
│   ├── KYC: Spanish identity verification (DNI/NIE)
│   ├── Compliance: Bank of Spain regulatory reporting
│   └── Notifications: Spanish SMS provider
├── Compliance:
│   ├── Regulator: Bank of Spain (Banco de España)
│   ├── Reporting: Spanish regulatory reports
│   └── Data Residency: Spain
└── Products: Spanish-specific products (Bizum, Spanish mortgages)

Tenant: "FranceBranch"
├── Country: France (FR)
├── Currency: EUR
├── Language: French (fr-FR)
├── Timezone: Europe/Paris
├── Providers:
│   ├── Payments: SEPA, French payment systems
│   ├── KYC: French identity verification (CNI, passport)
│   ├── Compliance: ACPR regulatory reporting
│   └── Notifications: French SMS provider
├── Compliance:
│   ├── Regulator: ACPR (Autorité de Contrôle Prudentiel)
│   ├── Reporting: French regulatory reports
│   └── Data Residency: France
└── Products: French-specific products (Livret A equivalent)

Tenant: "GermanyBranch"
├── Country: Germany (DE)
├── Currency: EUR
├── Language: German (de-DE)
├── Timezone: Europe/Berlin
├── Providers:
│   ├── Payments: SEPA, German payment systems
│   ├── KYC: German identity verification (Personalausweis)
│   ├── Compliance: BaFin regulatory reporting
│   └── Notifications: German SMS provider
├── Compliance:
│   ├── Regulator: BaFin (Bundesanstalt für Finanzdienstleistungsaufsicht)
│   ├── Reporting: German regulatory reports
│   └── Data Residency: Germany
└── Products: German-specific products (Girokonto)
```

**Benefits**:
- ✅ Rapid geographic expansion (3 countries in 6 months)
- ✅ Local compliance without platform changes
- ✅ Shared core platform, localized experiences
- ✅ Centralized management, distributed operations

---

### Use Case 3: White-Label Banking for Retail Company

**Scenario**: A large retail company wants to offer banking services to its customers under its own brand to increase customer loyalty and generate additional revenue.

**Challenge**:
- No banking infrastructure or expertise
- Need to maintain retail brand identity
- Regulatory compliance requirements
- Integration with existing retail systems

**Solution with Firefly**:

```yaml
Tenant: "RetailBankingBrand"
├── Business Model: White-label banking for retail customers
├── Branding:
│   ├── Colors: Retail company's brand colors
│   ├── Logo: Retail company's logo
│   └── Design: Consistent with retail website/app
├── Providers:
│   ├── BaaS: Treezor (complete banking infrastructure)
│   ├── KYC: Onfido (fast, mobile-optimized)
│   ├── Cards: Marqeta (co-branded debit cards)
│   ├── Compliance: ComplyAdvantage (AML/KYC)
│   └── Notifications: SendGrid (email), Twilio (SMS)
├── Products:
│   ├── Checking account (linked to retail loyalty program)
│   ├── Savings account (bonus interest for retail purchases)
│   ├── Co-branded debit card (cashback on retail purchases)
│   └── Buy-now-pay-later (BNPL) for retail purchases
├── Integration:
│   ├── Retail loyalty program integration
│   ├── E-commerce platform integration
│   ├── Point-of-sale (POS) integration
│   └── Customer data synchronization
└── Revenue Model:
    ├── Interchange fees from card transactions
    ├── Interest income from deposits
    ├── BNPL fees
    └── Increased retail sales from loyalty integration
```

**Benefits**:
- ✅ Launch banking services in 3 months
- ✅ No banking infrastructure investment
- ✅ Maintain complete brand control
- ✅ Increase customer loyalty and lifetime value
- ✅ New revenue stream from banking services

---

### Use Case 4: Provider Redundancy and Failover

**Scenario**: A bank needs high availability for critical services like payments and KYC, requiring multiple providers with automatic failover.

**Challenge**:
- Single provider failure can disrupt operations
- Need automatic failover without manual intervention
- Different providers have different APIs and data formats
- Cost optimization by using cheaper providers when available

**Solution with Firefly**:

```yaml
Payment Providers (Priority-based failover):
├── Primary Provider: Stripe
│   ├── Priority: 1 (highest)
│   ├── Use Case: Normal operations
│   ├── Cost: Higher fees, better reliability
│   └── Health Check: Every 30 seconds
├── Secondary Provider: Adyen
│   ├── Priority: 2
│   ├── Use Case: Failover when Stripe is down
│   ├── Cost: Medium fees, good reliability
│   └── Health Check: Every 30 seconds
└── Tertiary Provider: PayPal
    ├── Priority: 3 (lowest)
    ├── Use Case: Last resort failover
    ├── Cost: Lower fees, acceptable reliability
    └── Health Check: Every 30 seconds

Failover Logic:
1. Attempt payment with Primary Provider (Stripe)
2. If fails (timeout, error, health check failed):
   → Attempt with Secondary Provider (Adyen)
3. If fails:
   → Attempt with Tertiary Provider (PayPal)
4. If all fail:
   → Queue for retry, alert operations team

Value Mappings (handle different provider formats):
├── Stripe: status = "succeeded"
├── Adyen: status = "Authorised"
└── PayPal: status = "COMPLETED"
    → All map to Firefly internal: "SUCCESS"
```

**Benefits**:
- ✅ 99.99% uptime for critical services
- ✅ Automatic failover without manual intervention
- ✅ Cost optimization by using cheaper providers
- ✅ No code changes when switching providers
- ✅ Unified interface despite different provider APIs

---

### Use Case 5: Progressive Feature Rollout with Feature Flags

**Scenario**: A bank wants to launch a new "Advanced Analytics Dashboard" feature but needs to test it with a small percentage of users before full rollout.

**Challenge**:
- Risk of bugs affecting all users
- Need to test with real users in production
- Ability to instantly disable if problems occur
- Different rollout strategies for different tenants

**Solution with Firefly**:

```yaml
Feature Flag: Advanced Analytics Dashboard
├── Phase 1: Internal Testing (Week 1)
│   ├── Enabled: true
│   ├── Rollout Percentage: 0%
│   ├── Target User Segments: ["internal_employees"]
│   └── Environment: production
├── Phase 2: Beta Testing (Week 2-3)
│   ├── Enabled: true
│   ├── Rollout Percentage: 5%
│   ├── Target User Segments: ["premium", "early_adopters"]
│   └── Monitoring: Error rates, performance metrics
├── Phase 3: Gradual Rollout (Week 4-6)
│   ├── Week 4: 25% rollout
│   ├── Week 5: 50% rollout
│   ├── Week 6: 75% rollout
│   └── Monitoring: User engagement, error rates
└── Phase 4: Full Rollout (Week 7)
    ├── Enabled: true
    ├── Rollout Percentage: 100%
    └── Kill Switch: Available for instant disable

Tenant-Specific Overrides:
├── Premium Tenant: 100% rollout (early access)
├── Trial Tenant: 0% rollout (not available)
└── Enterprise Tenant: Custom rollout schedule
```

**Benefits**:
- ✅ Zero-downtime feature deployment
- ✅ Instant rollback if issues detected
- ✅ A/B testing capabilities
- ✅ Tenant-specific feature access
- ✅ Reduced deployment risk

---

### Use Case 6: Compliance and Audit Trail

**Scenario**: A bank needs to comply with SOX regulations requiring complete audit trail of all configuration changes with ability to rollback.

**Challenge**:
- Regulatory requirement for audit logs
- Need to track who changed what, when, and why
- Ability to rollback unauthorized changes
- Tamper-proof audit trail

**Solution with Firefly**:

```yaml
Audit Scenario: Unauthorized Provider Configuration Change
├── Original Configuration:
│   ├── Provider: Treezor
│   ├── Base URL: https://api.treezor.com/v1
│   ├── Timeout: 30000ms
│   └── Status: ACTIVE
├── Unauthorized Change (Detected):
│   ├── Changed By: contractor@external.com
│   ├── IP Address: 203.0.113.42 (suspicious location)
│   ├── Timestamp: 2025-01-15 03:00:00 UTC (off-hours)
│   ├── Field Changed: baseUrl
│   ├── Old Value: https://api.treezor.com/v1
│   ├── New Value: https://malicious-site.com/api
│   └── Change Reason: "API upgrade" (suspicious)
├── Detection:
│   ├── Security Alert: Unauthorized configuration change
│   ├── Anomaly: Change made during off-hours
│   ├── Risk Level: CRITICAL
│   └── Action: Automatic rollback triggered
└── Rollback:
    ├── Rollback Initiated: 2025-01-15 03:05:00 UTC
    ├── Rollback By: security-system@mybank.com
    ├── Configuration Restored: baseUrl = https://api.treezor.com/v1
    ├── Audit Entry Created: Rollback action logged
    └── Security Team Notified: Incident report generated

Compliance Benefits:
├── SOX Compliance: Complete audit trail
├── GDPR Compliance: Data change tracking
├── PCI-DSS Compliance: Security event logging
└── Internal Audit: Quarterly audit reports
```

**Benefits**:
- ✅ Complete regulatory compliance
- ✅ Tamper-proof audit trail
- ✅ Instant rollback capability
- ✅ Security breach detection
- ✅ Accountability and attribution

---

### Use Case 7: Multi-Environment Configuration Management

**Scenario**: A bank operates across dev, staging, and production environments with different configurations and secrets for each.

**Challenge**:
- Different database URLs per environment
- Separate API keys and secrets
- Environment-specific timeouts and limits
- Prevent production secrets in non-production

**Solution with Firefly**:

```yaml
Configuration: Database Connection
├── Development Environment:
│   ├── Environment Name: development
│   ├── Config Key: database.url
│   ├── Config Value: postgresql://dev-db.internal:5432/firefly_dev
│   ├── Config Type: STRING
│   ├── Is Secret: false
│   └── Category: database
├── Staging Environment:
│   ├── Environment Name: staging
│   ├── Config Key: database.url
│   ├── Config Value: postgresql://staging-db.internal:5432/firefly_staging
│   ├── Config Type: STRING
│   ├── Is Secret: false
│   └── Category: database
└── Production Environment:
    ├── Environment Name: production
    ├── Config Key: database.url
    ├── Config Value: postgresql://prod-db.example.com:5432/firefly_prod
    ├── Config Type: STRING
    ├── Is Secret: false
    └── Category: database

Configuration: API Keys (Secrets)
├── Development:
│   ├── Config Key: stripe.api_key
│   ├── Config Value: sk_test_abc123xyz789 (encrypted)
│   ├── Is Secret: true
│   └── Access: Development team only
├── Staging:
│   ├── Config Key: stripe.api_key
│   ├── Config Value: sk_test_staging_xyz789 (encrypted)
│   ├── Is Secret: true
│   └── Access: QA team only
└── Production:
    ├── Config Key: stripe.api_key
    ├── Config Value: sk_live_prod_xyz789 (encrypted)
    ├── Is Secret: true
    └── Access: Operations team only (restricted)

Configuration Inheritance:
├── Global Default: timeout = 20000ms
├── Production Override: timeout = 30000ms
└── Tenant-Specific Production: timeout = 45000ms (Spain tenant)
```

**Benefits**:
- ✅ Environment isolation
- ✅ Secrets management with encryption
- ✅ Configuration inheritance
- ✅ Access control per environment
- ✅ Prevent production credential leaks

---

### Use Case 8: Rate Limiting and Security Policies

**Scenario**: A bank needs to implement different rate limiting and security policies per tenant based on their subscription tier.

**Challenge**:
- Different rate limits for trial vs. premium tenants
- Tenant-specific security requirements
- DDoS protection and abuse prevention
- Compliance with security standards

**Solution with Firefly**:

```yaml
Trial Tenant Settings:
├── Rate Limiting:
│   ├── Per Minute: 10 requests
│   ├── Per Hour: 500 requests
│   ├── Per Day: 5,000 requests
│   └── Burst Allowance: 20 requests
├── Security Policies:
│   ├── Password Min Length: 8 characters
│   ├── MFA Enabled: false (optional)
│   ├── Session Timeout: 15 minutes
│   ├── Max Login Attempts: 3
│   └── Account Lockout: 15 minutes
├── Circuit Breaker:
│   ├── Enabled: true
│   ├── Failure Threshold: 30%
│   └── Timeout: 30 seconds
└── Compliance:
    ├── GDPR Compliant: true
    ├── PCI-DSS Compliant: false
    └── Data Retention: 30 days

Premium Tenant Settings:
├── Rate Limiting:
│   ├── Per Minute: 1,000 requests
│   ├── Per Hour: 50,000 requests
│   ├── Per Day: 1,000,000 requests
│   └── Burst Allowance: 2,000 requests
├── Security Policies:
│   ├── Password Min Length: 12 characters
│   ├── MFA Enabled: true (required)
│   ├── Session Timeout: 60 minutes
│   ├── Max Login Attempts: 5
│   └── Account Lockout: 30 minutes
├── Circuit Breaker:
│   ├── Enabled: true
│   ├── Failure Threshold: 50%
│   └── Timeout: 60 seconds
└── Compliance:
    ├── GDPR Compliant: true
    ├── PCI-DSS Compliant: true
    └── Data Retention: 2555 days (7 years)

Enterprise Tenant Settings:
├── Rate Limiting:
│   ├── Per Minute: 10,000 requests
│   ├── Per Hour: 500,000 requests
│   ├── Per Day: 10,000,000 requests
│   └── Burst Allowance: 20,000 requests
├── Security Policies:
│   ├── Password Min Length: 16 characters
│   ├── MFA Enabled: true (required, hardware tokens)
│   ├── Session Timeout: 120 minutes
│   ├── Max Login Attempts: 10
│   └── Account Lockout: 60 minutes
├── Circuit Breaker:
│   ├── Enabled: true
│   ├── Failure Threshold: 70%
│   └── Timeout: 120 seconds
└── Compliance:
    ├── GDPR Compliant: true
    ├── PCI-DSS Compliant: true
    ├── SOX Compliant: true
    └── Data Retention: 3650 days (10 years)
```

**Benefits**:
- ✅ Tiered service offerings
- ✅ DDoS protection
- ✅ Compliance per tenant
- ✅ Flexible security policies
- ✅ Resource optimization

---

## ✅ Best Practices

### Tenant Management

#### ✅ DO:

1. **Implement Complete Data Isolation**
   ```sql
   -- Use tenant-aware queries
   SELECT * FROM accounts WHERE tenant_id = :tenantId;

   -- Never query across tenants
   -- ❌ SELECT * FROM accounts; -- WRONG!
   ```

2. **Validate Tenant Context in All API Requests**
   ```java
   @PreAuthorize("hasPermission(#tenantId, 'TENANT', 'READ')")
   public Mono<TenantDTO> getTenant(UUID tenantId) {
       // Tenant context validated before execution
   }
   ```

3. **Use Tenant-Aware Logging**
   ```java
   log.info("Processing payment for tenant: {}", tenantId);
   // Include tenantId in all log statements
   ```

4. **Implement Tenant-Specific Rate Limiting**
   ```yaml
   rate-limits:
     premium-tier: 10000 requests/hour
     standard-tier: 1000 requests/hour
     trial-tier: 100 requests/hour
   ```

5. **Maintain Comprehensive Audit Trails**
   ```java
   auditLog.record(
       tenantId: tenantId,
       action: "UPDATE_TENANT_CONFIG",
       user: currentUser,
       changes: configChanges,
       timestamp: Instant.now()
   );
   ```

#### ❌ DON'T:

1. **Share Sensitive Data Between Tenants**
   - Never expose one tenant's data to another
   - Implement strict access controls

2. **Allow Cross-Tenant Queries**
   - Always filter by tenant ID
   - Use database row-level security if available

3. **Hardcode Tenant-Specific Logic**
   - Use configuration, not code
   - Keep business logic generic

4. **Skip Tenant Validation**
   - Always validate tenant exists and is active
   - Check user has permission for tenant

---

### Provider Configuration

#### ✅ DO:

1. **Encrypt All Sensitive Credentials**
   ```java
   @Column(name = "parameter_value")
   @Convert(converter = EncryptedStringConverter.class)
   private String parameterValue; // Encrypted at rest
   ```

2. **Use Environment-Specific Configurations**
   ```yaml
   providers:
     stripe:
       sandbox:
         api-key: sk_test_...
         base-url: https://api.stripe.com/test
       production:
         api-key: sk_live_...
         base-url: https://api.stripe.com
   ```

3. **Implement Circuit Breakers**
   ```java
   @CircuitBreaker(name = "stripe", fallbackMethod = "fallbackPayment")
   public Mono<PaymentResponse> processPayment(PaymentRequest request) {
       return stripeClient.charge(request);
   }
   ```

4. **Monitor Provider Health and Performance**
   ```java
   @Scheduled(fixedRate = 30000) // Every 30 seconds
   public void checkProviderHealth() {
       providers.forEach(provider -> {
           healthCheck(provider)
               .subscribe(
                   health -> updateProviderStatus(provider, health),
                   error -> markProviderDown(provider, error)
               );
       });
   }
   ```

5. **Have Fallback Providers for Critical Services**
   ```java
   public Mono<PaymentResponse> processPayment(PaymentRequest request) {
       return primaryProvider.charge(request)
           .onErrorResume(error -> secondaryProvider.charge(request))
           .onErrorResume(error -> tertiaryProvider.charge(request));
   }
   ```

6. **Version All Provider Configurations**
   ```sql
   CREATE TABLE provider_config_history (
       id UUID PRIMARY KEY,
       provider_id UUID,
       config_version INTEGER,
       config_data JSONB,
       changed_by UUID,
       changed_at TIMESTAMP
   );
   ```

#### ❌ DON'T:

1. **Store Credentials in Plain Text**
   - Always encrypt sensitive data
   - Use secret management systems (Vault, AWS Secrets Manager)

2. **Use Production Credentials in Development**
   - Use sandbox/test credentials in non-production
   - Separate environments completely

3. **Skip Error Handling for Provider Calls**
   - Always handle errors gracefully
   - Implement retries with exponential backoff

4. **Ignore Provider Rate Limits**
   - Respect provider rate limits
   - Implement request throttling

5. **Deploy Without Testing Provider Integrations**
   - Test all provider integrations before deployment
   - Use integration tests with real providers (sandbox)

---

### Parameter Management

#### ✅ DO:

1. **Document All Parameters**
   ```java
   @ProviderParameter(
       name = "api_timeout",
       description = "API request timeout in milliseconds",
       type = ParameterType.INTEGER,
       defaultValue = "30000",
       validationRegex = "^[0-9]+$",
       category = "connection"
   )
   ```

2. **Validate Parameter Values Before Saving**
   ```java
   public Mono<ProviderParameter> saveParameter(ProviderParameter param) {
       return validateParameter(param)
           .flatMap(repository::save);
   }

   private Mono<ProviderParameter> validateParameter(ProviderParameter param) {
       if (param.getValidationRegex() != null) {
           if (!param.getParameterValue().matches(param.getValidationRegex())) {
               return Mono.error(new ValidationException("Invalid parameter value"));
           }
       }
       return Mono.just(param);
   }
   ```

3. **Use Global Parameters for Defaults**
   ```java
   // Global parameter (applies to all tenants)
   ProviderParameter.builder()
       .providerId(providerId)
       .tenantId(null) // null = global
       .parameterName("api_timeout")
       .parameterValue("30000")
       .build();
   ```

4. **Override with Tenant-Specific Parameters Only When Necessary**
   ```java
   // Tenant-specific override (only for Spain tenant)
   ProviderParameter.builder()
       .providerId(providerId)
       .tenantId(spainTenantId) // specific tenant
       .parameterName("api_timeout")
       .parameterValue("45000") // longer timeout for Spain
       .build();
   ```

5. **Version Parameter Changes**
   ```java
   public Mono<ProviderParameter> updateParameter(UUID id, String newValue) {
       return repository.findById(id)
           .flatMap(param -> {
               // Save to history
               saveToHistory(param);
               // Update parameter
               param.setParameterValue(newValue);
               param.setUpdatedAt(Instant.now());
               return repository.save(param);
           });
   }
   ```

6. **Implement Parameter Change Approval Workflows**
   ```java
   public Mono<ParameterChangeRequest> requestParameterChange(
       UUID parameterId,
       String newValue
   ) {
       return ParameterChangeRequest.builder()
           .parameterId(parameterId)
           .currentValue(getCurrentValue(parameterId))
           .proposedValue(newValue)
           .requestedBy(currentUser)
           .status(ChangeRequestStatus.PENDING_APPROVAL)
           .build()
           .save()
           .flatMap(this::notifyApprovers);
   }
   ```

#### ❌ DON'T:

1. **Create Duplicate Parameters**
   - Use unique constraints on (providerId, tenantId, parameterName)
   - Check for existing parameters before creating

2. **Skip Validation**
   - Always validate parameter values
   - Use type-safe validation

3. **Use Parameters for Secrets**
   - Use dedicated secret management (Vault, AWS Secrets Manager)
   - Mark parameters as `isSecret=true` for encryption

4. **Change Parameters Without Testing**
   - Test parameter changes in non-production first
   - Have rollback plan

5. **Delete Parameters Without Checking Dependencies**
   - Check if parameter is required
   - Verify no active tenants depend on it

---

## 📚 Documentation

### Detailed Guides

| Guide | Description | Topics Covered |
|-------|-------------|----------------|
| **[Tenant Management](./tenants.md)** | Complete guide to tenant lifecycle, configuration, and isolation | Tenant creation, statuses, configuration, isolation, best practices |
| **[Provider Management](./providers.md)** | Configuring providers, types, and integrations | Provider types, lifecycle, configuration, tenant associations |
| **[Parameter Configuration](./parameters.md)** | Dynamic parameter management and hierarchy | Parameter types, hierarchy, categories, security, validation |

### API Documentation

Access the **interactive API documentation** at:
```
http://localhost:8080/swagger-ui.html
```

Features:
- **Try It Out**: Test API endpoints directly from the browser
- **Request/Response Examples**: See example requests and responses
- **Schema Documentation**: Detailed schema documentation for all DTOs
- **Authentication**: Test with authentication tokens
- **Code Generation**: Generate client code in multiple languages

---

## 📞 Support

### Community Resources

- 📚 **Documentation**: [docs/](.)
- 🐛 **Issues**: [GitHub Issues](https://github.com/firefly-oss/common-platform-config-mgmt/issues)
- 💬 **Community**: [Firefly Community](https://community.firefly-banking.org)
- 🌐 **Website**: [firefly-banking.org](https://firefly-banking.org)
- 📧 **Email**: support@firefly-banking.org

### Getting Help

1. **Check Documentation**: Start with the detailed guides above
2. **Search Issues**: Someone may have already solved your problem
3. **Ask the Community**: Post in the community forum
4. **Report Bugs**: Create a detailed GitHub issue with:
   - Steps to reproduce
   - Expected behavior
   - Actual behavior
   - Environment details (Java version, OS, etc.)
   - Logs and stack traces

### Contributing

We welcome contributions! See [CONTRIBUTING.md](../CONTRIBUTING.md) for guidelines.

**Ways to contribute:**
- 🐛 Report bugs
- 💡 Suggest features
- 📝 Improve documentation
- 🔧 Submit pull requests
- ⭐ Star the repository
- 📢 Spread the word

---

## 📄 License

This project is licensed under the **Apache License 2.0**.

See the [LICENSE](../LICENSE) file for complete details.

```
Copyright 2025 Firefly Open Source Community

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

---

## 🙏 Acknowledgments

Built with ❤️ by the **Firefly Open Source Community**

Special thanks to all contributors who have helped make Firefly better!

---

**[⬆ Back to Top](#firefly-configuration-management-service)**

