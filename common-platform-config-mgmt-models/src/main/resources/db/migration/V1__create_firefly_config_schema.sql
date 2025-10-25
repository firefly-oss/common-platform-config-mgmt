-- =====================================================
-- Firefly Core Banking - Configuration Management Schema
-- =====================================================
-- This schema manages tenants, providers, and their configurations
-- for the Firefly open-source core banking platform.
-- =====================================================

-- =====================================================
-- TENANT STATUS TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS tenant_statuses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tenant_statuses_code ON tenant_statuses(code);
CREATE INDEX idx_tenant_statuses_active ON tenant_statuses(active);

-- Insert default tenant statuses
INSERT INTO tenant_statuses (code, name, description, active) VALUES
    ('ACTIVE', 'Active', 'Tenant is active and operational', true),
    ('SUSPENDED', 'Suspended', 'Tenant is temporarily suspended', true),
    ('INACTIVE', 'Inactive', 'Tenant is inactive', true),
    ('TRIAL', 'Trial', 'Tenant is in trial period', true),
    ('EXPIRED', 'Expired', 'Tenant subscription has expired', true);

-- =====================================================
-- TENANTS TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    tenant_status_id UUID NOT NULL,
    country_code VARCHAR(3),
    timezone VARCHAR(50),
    default_currency_code VARCHAR(3),
    default_language_code VARCHAR(10),
    business_contact_name VARCHAR(255),
    business_contact_email VARCHAR(255),
    business_contact_phone VARCHAR(50),
    technical_contact_name VARCHAR(255),
    technical_contact_email VARCHAR(255),
    technical_contact_phone VARCHAR(50),
    subscription_tier VARCHAR(50),
    subscription_start_date TIMESTAMP,
    subscription_end_date TIMESTAMP,
    is_trial BOOLEAN DEFAULT FALSE,
    trial_end_date TIMESTAMP,
    metadata TEXT,
    active BOOLEAN DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tenant_status FOREIGN KEY (tenant_status_id) REFERENCES tenant_statuses(id)
);

CREATE INDEX idx_tenants_code ON tenants(code);
CREATE INDEX idx_tenants_status ON tenants(tenant_status_id);
CREATE INDEX idx_tenants_country ON tenants(country_code);
CREATE INDEX idx_tenants_active ON tenants(active);

-- =====================================================
-- TENANT BRANDING TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS tenant_brandings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL UNIQUE,
    logo_url TEXT,
    logo_dark_url TEXT,
    favicon_url TEXT,
    primary_color VARCHAR(7),
    secondary_color VARCHAR(7),
    accent_color VARCHAR(7),
    background_color VARCHAR(7),
    text_color VARCHAR(7),
    font_family VARCHAR(100),
    custom_css TEXT,
    email_header_url TEXT,
    email_footer_text TEXT,
    metadata TEXT,
    active BOOLEAN DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tenant_branding_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id) ON DELETE CASCADE
);

CREATE INDEX idx_tenant_brandings_tenant ON tenant_brandings(tenant_id);
CREATE INDEX idx_tenant_brandings_active ON tenant_brandings(active);

-- =====================================================
-- PROVIDER TYPE TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS provider_types (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    active BOOLEAN DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_provider_types_code ON provider_types(code);
CREATE INDEX idx_provider_types_category ON provider_types(category);
CREATE INDEX idx_provider_types_active ON provider_types(active);

-- Insert default provider types
INSERT INTO provider_types (code, name, description, category, active) VALUES
    ('PAYMENT_GATEWAY', 'Payment Gateway', 'Payment processing provider', 'PAYMENT', true),
    ('KYC', 'KYC Provider', 'Know Your Customer verification provider', 'COMPLIANCE', true),
    ('CARD_ISSUING', 'Card Issuing', 'Card issuing and management provider', 'PAYMENT', true),
    ('BANKING_CORE', 'Banking Core', 'Core banking system provider', 'BANKING', true),
    ('FRAUD_DETECTION', 'Fraud Detection', 'Fraud detection and prevention provider', 'SECURITY', true),
    ('NOTIFICATION', 'Notification', 'Email/SMS notification provider', 'COMMUNICATION', true),
    ('DOCUMENT_STORAGE', 'Document Storage', 'Document storage and management provider', 'STORAGE', true),
    ('CREDIT_SCORING', 'Credit Scoring', 'Credit scoring and risk assessment provider', 'COMPLIANCE', true),
    ('ACCOUNTING', 'Accounting', 'Accounting and ledger provider', 'BANKING', true),
    ('IDENTITY_VERIFICATION', 'Identity Verification', 'Identity verification provider', 'COMPLIANCE', true);

-- =====================================================
-- PROVIDER STATUS TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS provider_statuses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_provider_statuses_code ON provider_statuses(code);
CREATE INDEX idx_provider_statuses_active ON provider_statuses(active);

-- Insert default provider statuses
INSERT INTO provider_statuses (code, name, description, active) VALUES
    ('ACTIVE', 'Active', 'Provider is active and operational', true),
    ('INACTIVE', 'Inactive', 'Provider is inactive', true),
    ('MAINTENANCE', 'Maintenance', 'Provider is under maintenance', true),
    ('DEPRECATED', 'Deprecated', 'Provider is deprecated', true);

-- =====================================================
-- PROVIDERS TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS providers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    provider_type_id UUID NOT NULL,
    provider_status_id UUID NOT NULL,
    base_url VARCHAR(500),
    api_version VARCHAR(20),
    documentation_url TEXT,
    support_email VARCHAR(255),
    support_phone VARCHAR(50),
    metadata TEXT,
    active BOOLEAN DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_provider_type FOREIGN KEY (provider_type_id) REFERENCES provider_types(id),
    CONSTRAINT fk_provider_status FOREIGN KEY (provider_status_id) REFERENCES provider_statuses(id)
);

CREATE INDEX idx_providers_code ON providers(code);
CREATE INDEX idx_providers_type ON providers(provider_type_id);
CREATE INDEX idx_providers_status ON providers(provider_status_id);
CREATE INDEX idx_providers_active ON providers(active);

-- =====================================================
-- PROVIDER PARAMETERS TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS provider_parameters (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    provider_id UUID NOT NULL,
    tenant_id UUID,
    parameter_name VARCHAR(100) NOT NULL,
    parameter_value TEXT,
    parameter_type VARCHAR(50),
    description TEXT,
    is_secret BOOLEAN DEFAULT FALSE,
    is_required BOOLEAN DEFAULT FALSE,
    is_editable BOOLEAN DEFAULT TRUE,
    validation_regex VARCHAR(500),
    default_value TEXT,
    environment VARCHAR(50),
    category VARCHAR(100),
    display_order INTEGER,
    metadata TEXT,
    active BOOLEAN DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_provider_parameter_provider FOREIGN KEY (provider_id) REFERENCES providers(id) ON DELETE CASCADE,
    CONSTRAINT fk_provider_parameter_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id) ON DELETE CASCADE
);

CREATE INDEX idx_provider_parameters_provider ON provider_parameters(provider_id);
CREATE INDEX idx_provider_parameters_tenant ON provider_parameters(tenant_id);
CREATE INDEX idx_provider_parameters_name ON provider_parameters(parameter_name);
CREATE INDEX idx_provider_parameters_category ON provider_parameters(category);
CREATE INDEX idx_provider_parameters_environment ON provider_parameters(environment);
CREATE INDEX idx_provider_parameters_active ON provider_parameters(active);
CREATE UNIQUE INDEX idx_provider_parameters_unique_tenant ON provider_parameters(provider_id, tenant_id, parameter_name) WHERE tenant_id IS NOT NULL;
CREATE UNIQUE INDEX idx_provider_parameters_unique_global ON provider_parameters(provider_id, parameter_name) WHERE tenant_id IS NULL;

-- =====================================================
-- PROVIDER VALUE MAPPINGS TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS provider_value_mappings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    provider_id UUID NOT NULL,
    tenant_id UUID,
    mapping_type VARCHAR(100) NOT NULL,
    firefly_value VARCHAR(255) NOT NULL,
    provider_value VARCHAR(255) NOT NULL,
    direction VARCHAR(20),
    description TEXT,
    priority INTEGER DEFAULT 0,
    metadata TEXT,
    active BOOLEAN DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_provider_value_mapping_provider FOREIGN KEY (provider_id) REFERENCES providers(id) ON DELETE CASCADE,
    CONSTRAINT fk_provider_value_mapping_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id) ON DELETE CASCADE
);

CREATE INDEX idx_provider_value_mappings_provider ON provider_value_mappings(provider_id);
CREATE INDEX idx_provider_value_mappings_tenant ON provider_value_mappings(tenant_id);
CREATE INDEX idx_provider_value_mappings_type ON provider_value_mappings(mapping_type);
CREATE INDEX idx_provider_value_mappings_firefly_value ON provider_value_mappings(firefly_value);
CREATE INDEX idx_provider_value_mappings_provider_value ON provider_value_mappings(provider_value);
CREATE INDEX idx_provider_value_mappings_direction ON provider_value_mappings(direction);
CREATE INDEX idx_provider_value_mappings_active ON provider_value_mappings(active);

-- =====================================================
-- PROVIDER TENANTS TABLE (Relationship)
-- =====================================================
CREATE TABLE IF NOT EXISTS provider_tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    provider_id UUID NOT NULL,
    tenant_id UUID NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    priority INTEGER DEFAULT 0,
    configuration_override TEXT,
    enabled BOOLEAN DEFAULT TRUE,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    notes TEXT,
    metadata TEXT,
    active BOOLEAN DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_provider_tenant_provider FOREIGN KEY (provider_id) REFERENCES providers(id) ON DELETE CASCADE,
    CONSTRAINT fk_provider_tenant_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id) ON DELETE CASCADE,
    CONSTRAINT uq_provider_tenant UNIQUE (provider_id, tenant_id)
);

CREATE INDEX idx_provider_tenants_provider ON provider_tenants(provider_id);
CREATE INDEX idx_provider_tenants_tenant ON provider_tenants(tenant_id);
CREATE INDEX idx_provider_tenants_primary ON provider_tenants(is_primary);
CREATE INDEX idx_provider_tenants_enabled ON provider_tenants(enabled);
CREATE INDEX idx_provider_tenants_active ON provider_tenants(active);

