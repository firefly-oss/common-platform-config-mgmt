-- Provider Contracts Statuses Table
-- Defines the status of provider contracts (Active, Expired, Pending, etc.)
-- Uses UUID primary key following the established pattern
CREATE TABLE provider_contracts_statuses (
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Contracts Table
-- Stores contract information for providers
-- Uses UUID primary key and foreign key references following the established pattern
CREATE TABLE provider_contracts (
    id UUID PRIMARY KEY,
    contract_id UUID NOT NULL,
    contract_type_id UUID NOT NULL,
    provider_id UUID NOT NULL REFERENCES providers(id),
    provider_contract_status_id UUID NOT NULL REFERENCES provider_contracts_statuses(id),
    description TEXT,
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Contracts Mapping Table
-- Maps internal contracts to provider contracts
-- Uses UUID primary key for consistency with other entities
CREATE TABLE provider_contracts_mapping (
    id UUID PRIMARY KEY,
    internal_contract_id UUID NOT NULL,
    provider_contract_id UUID NOT NULL REFERENCES provider_contracts(id),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    UNIQUE(internal_contract_id, provider_contract_id)
);

-- Insert initial data for provider contract statuses
-- Using UUID generation following the established pattern
INSERT INTO provider_contracts_statuses (id, code, name, description, active)
VALUES
(uuid_generate_v4(), 'ACTIVE', 'Active', 'Contract is active and in effect', true),
(uuid_generate_v4(), 'EXPIRED', 'Expired', 'Contract has expired', true),
(uuid_generate_v4(), 'PENDING', 'Pending', 'Contract is pending approval or activation', true),
(uuid_generate_v4(), 'SUSPENDED', 'Suspended', 'Contract is temporarily suspended', true),
(uuid_generate_v4(), 'TERMINATED', 'Terminated', 'Contract has been terminated', true);

-- Create indexes for better performance
CREATE INDEX idx_provider_contract_status_id ON provider_contracts(provider_contract_status_id);
CREATE INDEX idx_provider_contracts_provider_id ON provider_contracts(provider_id);
CREATE INDEX idx_provider_contracts_contract_id ON provider_contracts(contract_id);
CREATE INDEX idx_provider_contracts_contract_type_id ON provider_contracts(contract_type_id);
CREATE INDEX idx_provider_contracts_mapping_internal_contract_id ON provider_contracts_mapping(internal_contract_id);
CREATE INDEX idx_provider_contracts_mapping_provider_contract_id ON provider_contracts_mapping(provider_contract_id);