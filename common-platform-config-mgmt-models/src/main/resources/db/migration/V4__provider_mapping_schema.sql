-- Provider Mapping Statuses Table
-- Defines the status of provider mappings (Active, Inactive, etc.)
-- Uses UUID primary key following the established pattern
CREATE TABLE provider_mapping_statuses (
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Map Type Table
-- Defines the types of provider mappings available in the system
-- Uses UUID primary key for consistency with other entities
CREATE TABLE provider_map_type (
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Mapping Table
-- Follows the established UUID pattern for primary keys and foreign key relationships
CREATE TABLE provider_mapping (
    id UUID PRIMARY KEY,
    provider_map_type_id UUID NOT NULL REFERENCES provider_map_type(id),
    provider_mapping_status_id UUID NOT NULL REFERENCES provider_mapping_statuses(id),
    provider_id UUID NOT NULL REFERENCES providers(id),
    internal_provider_id UUID NOT NULL REFERENCES providers(id),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    UNIQUE(provider_map_type_id, provider_id, internal_provider_id)
);

-- Alter providers table to change country_code to countryId
-- Note: This migration converts country_code (VARCHAR) to countryId (BIGSERIAL)
-- The countryId will be auto-generated sequential integers, not UUIDs
-- This is intentional as country IDs are meant to be simple sequential identifiers

-- First create a temporary table to store country codes and their sequential IDs
CREATE TEMPORARY TABLE temp_country_codes (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE
);

-- Insert distinct country codes from providers table (IDs will be auto-generated)
INSERT INTO temp_country_codes (code)
SELECT DISTINCT country_code FROM providers WHERE country_code IS NOT NULL;

-- Add the new countryId column as BIGINT (will be populated with sequential IDs)
ALTER TABLE providers ADD COLUMN countryId BIGINT;

-- Update providers with the corresponding sequential country IDs
UPDATE providers
SET countryId = temp_country_codes.id
FROM temp_country_codes
WHERE providers.country_code = temp_country_codes.code;

-- Drop the old country_code column
ALTER TABLE providers DROP COLUMN country_code;

-- Insert initial data for provider mapping statuses
-- Using UUID generation following the established pattern
INSERT INTO provider_mapping_statuses (id, code, name, description, active)
VALUES
(uuid_generate_v4(), 'ACTIVE', 'Active', 'Provider mapping is active and operational', true),
(uuid_generate_v4(), 'INACTIVE', 'Inactive', 'Provider mapping is inactive', true),
(uuid_generate_v4(), 'PENDING', 'Pending', 'Provider mapping is pending approval', true);

-- Insert initial data for provider map types
-- These define the different types of mappings available
INSERT INTO provider_map_type (id, code, name, description, active)
VALUES
(uuid_generate_v4(), 'DIRECT', 'Direct Mapping', 'Direct one-to-one provider mapping', true),
(uuid_generate_v4(), 'PROXY', 'Proxy Mapping', 'Provider mapping through a proxy', true),
(uuid_generate_v4(), 'FALLBACK', 'Fallback Mapping', 'Fallback provider mapping for redundancy', true);

-- Create indexes for better performance
CREATE INDEX idx_provider_map_type_status_id ON provider_mapping(provider_mapping_status_id);
CREATE INDEX idx_provider_mapping_provider_map_type_id ON provider_mapping(provider_map_type_id);
CREATE INDEX idx_provider_mapping_provider_id ON provider_mapping(provider_id);
CREATE INDEX idx_provider_mapping_internal_provider_id ON provider_mapping(internal_provider_id);
CREATE INDEX idx_provider_countryId ON providers(countryId);
