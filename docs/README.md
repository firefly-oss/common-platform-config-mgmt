# Firefly Configuration Management Service

**Enterprise-grade multi-tenant configuration management for modern core banking**

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen.svg)](https://spring.io/projects/spring-boot)

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [What is Firefly?](#-what-is-firefly)
- [Core Concepts](#-core-concepts)
- [Architecture Overview](#-architecture-overview)
- [Technology Stack](#-technology-stack)
- [Data Model](#-data-model)
- [Quick Start Guide](#-quick-start-guide)
- [Use Cases](#-use-cases)
- [Best Practices](#-best-practices)
- [Documentation](#-documentation)
- [Support](#-support)

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
- **MAINTENANCE**: Undergoing maintenance or upgrades
- **INACTIVE**: Not currently in use but data retained
- **DELETED**: Soft-deleted, pending permanent removal

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


