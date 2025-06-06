# Firefly Common Platform Config Management

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.0-brightgreen.svg)
![R2DBC](https://img.shields.io/badge/R2DBC-PostgreSQL-blue.svg)
![Camunda](https://img.shields.io/badge/Camunda-8-orange.svg)

A microservice component of the Firefly Platform for managing configuration and process definitions for a core banking platform, supporting both standalone operation and Banking as a Service (BaaS) modes. This service provides a centralized configuration management system that enables the Firefly orchestration microservice to determine whether to operate standalone through payment gateways or use a Banking as a Service provider.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Data Model](#data-model)
- [API Documentation](#api-documentation)
  - [API Examples](#api-examples)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [Step-by-Step Guide](#step-by-step-guide)
  - [Setting Up Provider Types](#setting-up-provider-types)
  - [Creating Provider Statuses](#creating-provider-statuses)
  - [Configuring a Provider](#configuring-a-provider)
  - [Managing Provider Configurations](#managing-provider-configurations)
  - [Creating Process Statuses](#creating-process-statuses)
  - [Defining Provider Processes](#defining-provider-processes)
  - [Managing Process Versions](#managing-process-versions)
  - [Validating and Deploying Processes](#validating-and-deploying-processes)
- [Development](#development)
  - [Project Structure](#project-structure)
  - [Building](#building)
  - [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

## Overview

The Firefly Common Platform Config Management microservice provides a centralized configuration management platform for the Firefly core banking system. It manages provider types, providers, configurations, and processes to support different operational modes (standalone or Banking as a Service).

This microservice enables the Firefly orchestration microservice to determine whether to operate standalone through payment gateways (such as Iberpay in Spain) or use a Banking as a Service provider (like Treezor). The processes that the orchestrator must run differ completely between these scenarios, but this platform also allows for common processes between providers.

## Features

- **Provider Management**: Create and manage different types of providers (BaaS, Payment Gateway, CRM, etc.)
- **Configuration Management**: Store and retrieve provider-specific configurations
- **Process Definition**: Define and version BPMN processes for different providers
- **Reactive API**: Built with Spring WebFlux for non-blocking reactive operations using Mono types
- **Database Independence**: Uses R2DBC for reactive database access
- **Versioning**: Supports versioning of process definitions
- **Hierarchical API**: RESTful API with nested resources

## Architecture

The application follows a clean architecture approach with the following layers:

- **Web Layer**: REST controllers for handling HTTP requests
- **Service Layer**: Business logic and service implementations
- **Repository Layer**: Data access using R2DBC repositories
- **Model Layer**: Domain entities and DTOs

The application is built using:

- **Spring Boot**: Application framework
- **Spring WebFlux**: Reactive web framework
- **Spring Data R2DBC**: Reactive database access
- **PostgreSQL**: Database
- **MapStruct**: Object mapping
- **Lombok**: Boilerplate code reduction

## Data Model

The core entities in the system are:

- **ProviderType**: Types of providers (BaaS, Payment Gateway, CRM)
- **ProviderStatus**: Status of providers (Active, Inactive, etc.)
- **Provider**: Provider details and configuration
- **ProviderConfig**: Configuration settings for providers
- **ProviderProcessStatus**: Status of processes (Draft, Published, etc.)
- **ProviderProcess**: Process definitions for providers
- **ProviderProcessVersion**: Versioned BPMN process definitions
- **ProviderMappingStatus**: Status of provider mappings (Active, Inactive, etc.)
- **ProviderMapType**: Types of provider mappings
- **ProviderMapping**: Mappings between providers
- **ProviderContractsStatus**: Status of provider contracts (Active, Expired, etc.)
- **ProviderContract**: Contracts associated with providers
- **ProviderContractMapping**: Mappings between internal contracts and provider contracts

### Entity Relationship Diagram

```mermaid
erDiagram
    ProviderType ||--o{ Provider : "has many"
    ProviderStatus ||--o{ Provider : "has many"
    Provider ||--o{ ProviderConfig : "has many"
    Provider ||--o{ ProviderProcess : "has many"
    ProviderProcessStatus ||--o{ ProviderProcessVersion : "has many"
    ProviderProcess ||--o{ ProviderProcessVersion : "has many"
    Provider ||--o{ ProviderMapping : "has many"
    Provider ||--o{ ProviderMapping : "has many as internal"
    ProviderMapType ||--o{ ProviderMapping : "has many"
    ProviderMappingStatus ||--o{ ProviderMapping : "has many"
    ProviderContractsStatus ||--o{ ProviderContract : "has many"
    Provider ||--o{ ProviderContract : "has many"
    ProviderContract ||--o{ ProviderContractMapping : "has many"

    ProviderType {
        Long id PK
        String code
        String name
        String description
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderStatus {
        Long id PK
        String code
        String name
        String description
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    Provider {
        Long id PK
        String code
        String name
        String description
        Long providerTypeId FK
        Long providerStatusId FK
        String apiBaseUrl
        String webhookUrl
        String callbackUrl
        String logoUrl
        String documentationUrl
        String supportUrl
        String contactName
        String contactEmail
        String contactPhone
        String technicalContactName
        String technicalContactEmail
        String technicalContactPhone
        Long countryId
        String region
        String timezone
        String currencyCode
        Integer maxRequestsPerSecond
        Integer maxConcurrentRequests
        Boolean requiresAuthentication
        String authenticationType
        Boolean supportsWebhooks
        Boolean supportsCallbacks
        Boolean supportsPolling
        Integer pollingIntervalSeconds
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderConfig {
        Long id PK
        Long providerId FK
        String configGroup
        String key
        String value
        String valueType
        String description
        Boolean isSecret
        Boolean isRequired
        Boolean isEditable
        String validationRegex
        String defaultValue
        String environment
        LocalDateTime expirationDate
        String tags
        String metadata
        Integer orderIndex
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderProcessStatus {
        Long id PK
        String code
        String name
        String description
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderProcess {
        Long id PK
        String code
        String name
        String description
        Long providerId FK
        String processType
        String processCategory
        Boolean isCommon
        Integer priority
        Integer estimatedDurationSeconds
        String tags
        String metadata
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderProcessVersion {
        Long id PK
        Long providerProcessId FK
        String version
        String bpmnXml
        String bpmnDiagramXml
        Long providerProcessStatusId FK
        String notes
        String changelog
        String deployedBy
        LocalDateTime deployedAt
        Boolean isCurrent
        Boolean isDeployed
        String deploymentId
        Boolean active
        Long versionNumber
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderMappingStatus {
        Long id PK
        String code
        String name
        String description
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderMapType {
        Long id PK
        String code
        String name
        String description
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderMapping {
        Long id PK
        Long providerMapTypeId FK
        Long providerMappingStatusId FK
        Long providerId FK
        Long internalProviderId FK
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderContractsStatus {
        Long id PK
        String code
        String name
        String description
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderContract {
        Long id PK
        Long contractId
        Long contractTypeId
        Long providerId FK
        Long providerContractStatusId FK
        String description
        LocalDateTime startDate
        LocalDateTime endDate
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    ProviderContractMapping {
        Long id PK
        Long internalContractId
        Long providerContractId FK
        Boolean active
        Long version
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
```

## API Documentation

The API follows RESTful principles with nested resources. All endpoints return reactive `Mono` types, including `Mono<PaginationResponse<T>>` for paginated results.

Base URL: `/api/v1`

### Main Resources

- `/api/v1/provider-types`: Provider types management
- `/api/v1/provider-statuses`: Provider statuses management
- `/api/v1/providers`: Provider management
- `/api/v1/provider-process-statuses`: Process statuses management
- `/api/v1/processes`: Process management

### Nested Resources

- `/api/v1/providers/{providerId}/configs`: Provider configurations
- `/api/v1/providers/{providerId}/processes`: Provider processes
- `/api/v1/processes/{processId}/versions`: Process versions

For detailed API documentation, see the Swagger UI at `/swagger-ui.html` when the application is running.

### API Examples

#### Creating a Provider Type

```bash
curl -X POST http://localhost:8080/api/v1/provider-types \
  -H "Content-Type: application/json" \
  -d '{
    "code": "BAAS",
    "name": "Banking as a Service",
    "description": "Banking as a Service providers",
    "active": true
  }'
```

#### Creating a Provider

```bash
curl -X POST http://localhost:8080/api/v1/providers \
  -H "Content-Type: application/json" \
  -d '{
    "code": "TREEZOR",
    "name": "Treezor",
    "description": "Treezor BaaS provider",
    "providerTypeId": 1,
    "providerStatusId": 1,
    "apiBaseUrl": "https://api.treezor.com",
    "webhookUrl": "https://webhooks.mycompany.com/treezor",
    "callbackUrl": "https://callbacks.mycompany.com/treezor",
    "contactName": "John Doe",
    "contactEmail": "john.doe@treezor.com",
    "countryCode": "FR",
    "region": "Europe",
    "currencyCode": "EUR",
    "requiresAuthentication": true,
    "authenticationType": "OAUTH2",
    "supportsWebhooks": true,
    "active": true
  }'
```

#### Adding a Configuration to a Provider

```bash
curl -X POST http://localhost:8080/api/v1/providers/1/configs \
  -H "Content-Type: application/json" \
  -d '{
    "configGroup": "API_CREDENTIALS",
    "key": "API_KEY",
    "value": "your-api-key-here",
    "valueType": "string",
    "description": "API key for authentication",
    "isSecret": true,
    "isRequired": true,
    "isEditable": true,
    "active": true
  }'
```

#### Creating a Process for a Provider

```bash
curl -X POST http://localhost:8080/api/v1/processes \
  -H "Content-Type: application/json" \
  -d '{
    "code": "PAYMENT_PROCESS",
    "name": "Payment Processing",
    "description": "Process for handling payments",
    "providerId": 1,
    "processType": "PAYMENT",
    "processCategory": "CORE",
    "isCommon": false,
    "priority": 10,
    "estimatedDurationSeconds": 60,
    "tags": "payment,core",
    "active": true
  }'
```

#### Adding a Version to a Process

```bash
curl -X POST http://localhost:8080/api/v1/processes/1/versions \
  -H "Content-Type: application/json" \
  -d '{
    "version": "1.0.0",
    "bpmnXml": "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn:definitions xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" id=\"Definitions_1\" targetNamespace=\"http://bpmn.io/schema/bpmn\">\n  <bpmn:process id=\"payment-process\" name=\"Payment Process\" isExecutable=\"true\">\n    <!-- BPMN content here -->\n  </bpmn:process>\n</bpmn:definitions>",
    "providerProcessStatusId": 1,
    "notes": "Initial version",
    "isCurrent": true,
    "active": true
  }'
```

#### Filtering Providers

```bash
curl -X POST http://localhost:8080/api/v1/providers/filter \
  -H "Content-Type: application/json" \
  -d '{
    "page": 0,
    "size": 10,
    "filters": [
      {
        "field": "providerTypeId",
        "operator": "EQUALS",
        "value": 1
      },
      {
        "field": "active",
        "operator": "EQUALS",
        "value": true
      }
    ],
    "sorts": [
      {
        "field": "name",
        "direction": "ASC"
      }
    ]
  }'
```

The filter request structure supports the following operators:

- `EQUALS`: Exact match
- `NOT_EQUALS`: Not equal to
- `GREATER_THAN`: Greater than
- `LESS_THAN`: Less than
- `GREATER_THAN_OR_EQUAL`: Greater than or equal to
- `LESS_THAN_OR_EQUAL`: Less than or equal to
- `CONTAINS`: String contains
- `STARTS_WITH`: String starts with
- `ENDS_WITH`: String ends with
- `IN`: Value in a list
- `NOT_IN`: Value not in a list
- `IS_NULL`: Field is null
- `IS_NOT_NULL`: Field is not null

Example with multiple filter types:

```bash
curl -X POST http://localhost:8080/api/v1/providers/filter \
  -H "Content-Type: application/json" \
  -d '{
    "page": 0,
    "size": 20,
    "filters": [
      {
        "field": "name",
        "operator": "CONTAINS",
        "value": "Bank"
      },
      {
        "field": "region",
        "operator": "IN",
        "value": ["Europe", "North America"]
      },
      {
        "field": "createdAt",
        "operator": "GREATER_THAN",
        "value": "2023-01-01T00:00:00Z"
      }
    ],
    "sorts": [
      {
        "field": "createdAt",
        "direction": "DESC"
      }
    ]
  }'
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.8 or higher
- PostgreSQL 13 or higher
- Docker (optional, for containerized deployment)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/firefly-oss/common-platform-config-mgmt.git
   cd common-platform-config-mgmt
   ```

2. Build the application:
   ```bash
   mvn clean install
   ```

### Configuration

Configuration is managed through application properties files:

- `application.yml`: Common configuration
- `application-dev.yml`: Development environment configuration
- `application-prod.yml`: Production environment configuration

Key configuration properties:

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/config_mgmt
    username: postgres
    password: postgres
  flyway:
    url: jdbc:postgresql://localhost:5432/config_mgmt
    user: postgres
    password: postgres
```

### Running the Application

#### Local Development

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Using Docker

```bash
docker build -t common-platform-config-mgmt .
docker run -p 8080:8080 common-platform-config-mgmt
```

#### Using Docker Compose

```bash
docker-compose up
```

## Step-by-Step Guide

This section provides a comprehensive guide on how to use the Config Management microservice to set up providers and their processes.

### Setting Up Provider Types

Provider types categorize different kinds of providers (e.g., BaaS, Payment Gateway, CRM).

1. **Create a Provider Type**:

```bash
curl -X POST http://localhost:8080/api/v1/provider-types \
  -H "Content-Type: application/json" \
  -d '{
    "code": "BAAS",
    "name": "Banking as a Service",
    "description": "Banking as a Service providers",
    "active": true
  }'
```

2. **Verify Provider Type Creation**:

```bash
curl -X GET http://localhost:8080/api/v1/provider-types/1
```

### Creating Provider Statuses

Provider statuses define the operational state of providers (e.g., Active, Inactive, Maintenance).

1. **Create Provider Statuses**:

```bash
# Create Active status
curl -X POST http://localhost:8080/api/v1/provider-statuses \
  -H "Content-Type: application/json" \
  -d '{
    "code": "ACTIVE",
    "name": "Active",
    "description": "Provider is active and operational",
    "active": true
  }'

# Create Maintenance status
curl -X POST http://localhost:8080/api/v1/provider-statuses \
  -H "Content-Type: application/json" \
  -d '{
    "code": "MAINTENANCE",
    "name": "Maintenance",
    "description": "Provider is under maintenance",
    "active": true
  }'
```

### Configuring a Provider

Providers represent external services like BaaS providers or payment gateways.

1. **Create a Provider**:

```bash
curl -X POST http://localhost:8080/api/v1/providers \
  -H "Content-Type: application/json" \
  -d '{
    "code": "TREEZOR",
    "name": "Treezor",
    "description": "Treezor BaaS provider",
    "providerTypeId": 1,
    "providerStatusId": 1,
    "apiBaseUrl": "https://api.treezor.com",
    "webhookUrl": "https://webhooks.mycompany.com/treezor",
    "callbackUrl": "https://callbacks.mycompany.com/treezor",
    "contactName": "John Doe",
    "contactEmail": "john.doe@treezor.com",
    "countryCode": "FR",
    "region": "Europe",
    "currencyCode": "EUR",
    "requiresAuthentication": true,
    "authenticationType": "OAUTH2",
    "supportsWebhooks": true,
    "active": true
  }'
```

2. **Verify Provider Creation**:

```bash
curl -X GET http://localhost:8080/api/v1/providers/1
```

### Managing Provider Configurations

Provider configurations store settings and credentials for each provider.

1. **Add API Credentials Configuration**:

```bash
curl -X POST http://localhost:8080/api/v1/providers/1/configs \
  -H "Content-Type: application/json" \
  -d '{
    "configGroup": "API_CREDENTIALS",
    "key": "API_KEY",
    "value": "your-api-key-here",
    "valueType": "string",
    "description": "API key for authentication",
    "isSecret": true,
    "isRequired": true,
    "isEditable": true,
    "active": true
  }'
```

2. **Add API Secret Configuration**:

```bash
curl -X POST http://localhost:8080/api/v1/providers/1/configs \
  -H "Content-Type: application/json" \
  -d '{
    "configGroup": "API_CREDENTIALS",
    "key": "API_SECRET",
    "value": "your-api-secret-here",
    "valueType": "string",
    "description": "API secret for authentication",
    "isSecret": true,
    "isRequired": true,
    "isEditable": true,
    "active": true
  }'
```

3. **Add Environment Configuration**:

```bash
curl -X POST http://localhost:8080/api/v1/providers/1/configs \
  -H "Content-Type: application/json" \
  -d '{
    "configGroup": "ENVIRONMENT",
    "key": "ENVIRONMENT",
    "value": "sandbox",
    "valueType": "string",
    "description": "Environment (sandbox or production)",
    "isSecret": false,
    "isRequired": true,
    "isEditable": true,
    "active": true
  }'
```

4. **List Provider Configurations**:

```bash
curl -X GET http://localhost:8080/api/v1/providers/1/configs
```

### Creating Process Statuses

Process statuses define the lifecycle state of BPMN processes (e.g., Draft, Published, Deprecated).

1. **Create Process Statuses**:

```bash
# Create Draft status
curl -X POST http://localhost:8080/api/v1/provider-process-statuses \
  -H "Content-Type: application/json" \
  -d '{
    "code": "DRAFT",
    "name": "Draft",
    "description": "Process is in draft state",
    "active": true
  }'

# Create Published status
curl -X POST http://localhost:8080/api/v1/provider-process-statuses \
  -H "Content-Type: application/json" \
  -d '{
    "code": "PUBLISHED",
    "name": "Published",
    "description": "Process is published and ready for production",
    "active": true
  }'
```

### Defining Provider Processes

Provider processes define the business processes that can be executed for a provider.

1. **Create a Process**:

```bash
curl -X POST http://localhost:8080/api/v1/processes \
  -H "Content-Type: application/json" \
  -d '{
    "code": "PAYMENT_PROCESS",
    "name": "Payment Processing",
    "description": "Process for handling payments",
    "providerId": 1,
    "processType": "PAYMENT",
    "processCategory": "CORE",
    "isCommon": false,
    "priority": 10,
    "estimatedDurationSeconds": 60,
    "tags": "payment,core",
    "active": true
  }'
```

2. **Create a Common Process** (shared across providers):

```bash
curl -X POST http://localhost:8080/api/v1/processes \
  -H "Content-Type: application/json" \
  -d '{
    "code": "CUSTOMER_ONBOARDING",
    "name": "Customer Onboarding",
    "description": "Common process for customer onboarding",
    "providerId": 1,
    "processType": "ONBOARDING",
    "processCategory": "CUSTOMER",
    "isCommon": true,
    "priority": 5,
    "estimatedDurationSeconds": 300,
    "tags": "onboarding,customer",
    "active": true
  }'
```

### Managing Process Versions

Process versions store the actual BPMN XML definitions for each process.

1. **Create a Process Version**:

```bash
curl -X POST http://localhost:8080/api/v1/processes/1/versions \
  -H "Content-Type: application/json" \
  -d '{
    "version": "1.0.0",
    "bpmnXml": "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn:definitions xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" id=\"Definitions_1\" targetNamespace=\"http://bpmn.io/schema/bpmn\">\n  <bpmn:process id=\"payment-process\" name=\"Payment Process\" isExecutable=\"true\">\n    <!-- BPMN content here -->\n  </bpmn:process>\n</bpmn:definitions>",
    "providerProcessStatusId": 1,
    "notes": "Initial version",
    "isCurrent": true,
    "active": true
  }'
```

2. **Update a Process Version Status** (e.g., from Draft to Published):

```bash
curl -X PUT http://localhost:8080/api/v1/processes/1/versions/1 \
  -H "Content-Type: application/json" \
  -d '{
    "version": "1.0.0",
    "bpmnXml": "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn:definitions xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" id=\"Definitions_1\" targetNamespace=\"http://bpmn.io/schema/bpmn\">\n  <bpmn:process id=\"payment-process\" name=\"Payment Process\" isExecutable=\"true\">\n    <!-- BPMN content here -->\n  </bpmn:process>\n</bpmn:definitions>",
    "providerProcessStatusId": 2,
    "notes": "Initial version",
    "changelog": "Published for production use",
    "isCurrent": true,
    "active": true
  }'
```

### Validating and Deploying Processes

The microservice integrates with Camunda 8 for BPMN process validation and deployment.

1. **Validate a BPMN Process**:

The validation happens automatically when creating or updating a process version. The service checks for:

- Valid BPMN 2.0 XML syntax
- Process executable flag is set to true
- Process ID matches the expected format
- Required elements are present

2. **Deploy a Process to Camunda**:

```bash
curl -X PUT http://localhost:8080/api/v1/processes/1/versions/1/deploy \
  -H "Content-Type: application/json"
```

This endpoint will:

- Validate the BPMN process
- Deploy it to the configured Camunda engine
- Update the process version with deployment information
- Set the `isDeployed` flag to true
- Store the Camunda deployment ID

3. **Check Deployment Status**:

```bash
curl -X GET http://localhost:8080/api/v1/processes/1/versions/1
```

The response will include deployment information if the process has been deployed.

## Development

### Project Structure

```
common-platform-config-mgmt/
├── common-platform-config-mgmt-models/       # Domain models and repositories
├── common-platform-config-mgmt-core/         # Business logic and services
├── common-platform-config-mgmt-interfaces/   # DTOs and interfaces
├── common-platform-config-mgmt-web/          # Web controllers and configuration
├── docs/                                     # Documentation
└── scripts/                                  # Utility scripts
```

### Building

```bash
mvn clean install
```

### Testing

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=ProviderServiceTest
```

## Deployment

### Kubernetes

Kubernetes deployment files are available in the `k8s` directory.

```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

### Cloud Deployment

For cloud deployment, follow the specific guidelines for your cloud provider.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.
