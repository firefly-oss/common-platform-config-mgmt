-- Provider Types Table
CREATE TABLE provider_types (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Statuses Table
CREATE TABLE provider_statuses (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Providers Table
CREATE TABLE providers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    provider_type_id BIGINT NOT NULL REFERENCES provider_types(id),
    provider_status_id BIGINT NOT NULL REFERENCES provider_statuses(id),
    api_base_url VARCHAR(255),
    webhook_url VARCHAR(255),
    callback_url VARCHAR(255),
    logo_url VARCHAR(255),
    documentation_url VARCHAR(255),
    support_url VARCHAR(255),
    contact_name VARCHAR(100),
    contact_email VARCHAR(255),
    contact_phone VARCHAR(50),
    technical_contact_name VARCHAR(100),
    technical_contact_email VARCHAR(255),
    technical_contact_phone VARCHAR(50),
    country_code VARCHAR(10),
    region VARCHAR(100),
    timezone VARCHAR(50),
    currency_code VARCHAR(10),
    max_requests_per_second INTEGER,
    max_concurrent_requests INTEGER,
    requires_authentication BOOLEAN DEFAULT TRUE,
    authentication_type VARCHAR(50),
    supports_webhooks BOOLEAN DEFAULT FALSE,
    supports_callbacks BOOLEAN DEFAULT FALSE,
    supports_polling BOOLEAN DEFAULT FALSE,
    polling_interval_seconds INTEGER,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Configs Table
CREATE TABLE provider_configs (
    id BIGSERIAL PRIMARY KEY,
    provider_id BIGINT NOT NULL REFERENCES providers(id),
    config_group VARCHAR(100),
    key VARCHAR(100) NOT NULL,
    value TEXT NOT NULL,
    value_type VARCHAR(50) DEFAULT 'string',
    description TEXT,
    is_secret BOOLEAN NOT NULL DEFAULT FALSE,
    is_required BOOLEAN NOT NULL DEFAULT FALSE,
    is_editable BOOLEAN NOT NULL DEFAULT TRUE,
    validation_regex VARCHAR(255),
    default_value TEXT,
    environment VARCHAR(50) NOT NULL DEFAULT 'default',
    expiration_date TIMESTAMP WITH TIME ZONE,
    tags VARCHAR(255),
    metadata TEXT,
    order_index INTEGER DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    UNIQUE(provider_id, key, environment)
);

-- Provider Process Statuses Table
CREATE TABLE provider_process_statuses (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Processes Table
CREATE TABLE provider_processes (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    provider_id BIGINT NOT NULL REFERENCES providers(id),
    process_type VARCHAR(100) NOT NULL,
    process_category VARCHAR(100),
    is_common BOOLEAN NOT NULL DEFAULT FALSE,
    priority INTEGER DEFAULT 0,
    estimated_duration_seconds INTEGER,
    tags VARCHAR(255),
    metadata TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Process Versions Table
CREATE TABLE provider_process_versions (
    id BIGSERIAL PRIMARY KEY,
    provider_process_id BIGINT NOT NULL REFERENCES provider_processes(id),
    version VARCHAR(20) NOT NULL,
    bpmn_xml TEXT NOT NULL,
    bpmn_diagram_xml TEXT,
    provider_process_status_id BIGINT NOT NULL REFERENCES provider_process_statuses(id),
    notes TEXT,
    changelog TEXT,
    deployed_by VARCHAR(100),
    deployed_at TIMESTAMP WITH TIME ZONE,
    is_current BOOLEAN NOT NULL DEFAULT FALSE,
    is_deployed BOOLEAN NOT NULL DEFAULT FALSE,
    deployment_id VARCHAR(100),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version_number BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    UNIQUE(provider_process_id, version)
);

-- Create indexes for better performance
CREATE INDEX idx_provider_type_id ON providers(provider_type_id);
CREATE INDEX idx_provider_status_id ON providers(provider_status_id);
CREATE INDEX idx_provider_id ON provider_configs(provider_id);
CREATE INDEX idx_provider_config_group ON provider_configs(config_group);
CREATE INDEX idx_provider_config_key ON provider_configs(key);
CREATE INDEX idx_provider_config_environment ON provider_configs(environment);
CREATE INDEX idx_provider_country_code ON providers(country_code);
CREATE INDEX idx_provider_region ON providers(region);
CREATE INDEX idx_provider_id_process ON provider_processes(provider_id);
CREATE INDEX idx_provider_process_type ON provider_processes(process_type);
CREATE INDEX idx_provider_process_category ON provider_processes(process_category);
CREATE INDEX idx_provider_process_id ON provider_process_versions(provider_process_id);
CREATE INDEX idx_provider_process_status_id ON provider_process_versions(provider_process_status_id);
CREATE INDEX idx_provider_process_version_is_current ON provider_process_versions(is_current);
CREATE INDEX idx_provider_process_version_is_deployed ON provider_process_versions(is_deployed);
CREATE INDEX idx_provider_process_version_deployment_id ON provider_process_versions(deployment_id);
