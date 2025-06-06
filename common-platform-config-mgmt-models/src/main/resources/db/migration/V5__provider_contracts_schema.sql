-- Provider Contracts Statuses Table
CREATE TABLE provider_contracts_statuses (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Contracts Table
CREATE TABLE provider_contracts (
    id BIGSERIAL PRIMARY KEY,
    contract_id BIGINT NOT NULL,
    contract_type_id BIGINT NOT NULL,
    provider_id BIGINT NOT NULL REFERENCES providers(id),
    provider_contract_status_id BIGINT NOT NULL REFERENCES provider_contracts_statuses(id),
    description TEXT,
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Contracts Mapping Table
CREATE TABLE provider_contracts_mapping (
    id BIGSERIAL PRIMARY KEY,
    internal_contract_id BIGINT NOT NULL,
    provider_contract_id BIGINT NOT NULL REFERENCES provider_contracts(id),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    UNIQUE(internal_contract_id, provider_contract_id)
);

-- Create indexes for better performance
CREATE INDEX idx_provider_contract_status_id ON provider_contracts(provider_contract_status_id);
CREATE INDEX idx_provider_contracts_provider_id ON provider_contracts(provider_id);
CREATE INDEX idx_provider_contracts_contract_id ON provider_contracts(contract_id);
CREATE INDEX idx_provider_contracts_contract_type_id ON provider_contracts(contract_type_id);
CREATE INDEX idx_provider_contracts_mapping_internal_contract_id ON provider_contracts_mapping(internal_contract_id);
CREATE INDEX idx_provider_contracts_mapping_provider_contract_id ON provider_contracts_mapping(provider_contract_id);