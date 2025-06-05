-- Provider Mapping Statuses Table
CREATE TABLE provider_mapping_statuses (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);


-- Provider Map Type Table
CREATE TABLE provider_map_type (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Provider Mapping Table
CREATE TABLE provider_mapping (
    id BIGSERIAL PRIMARY KEY,
    provider_map_type_id BIGINT NOT NULL REFERENCES provider_map_type(id),
    provider_mapping_status_id BIGINT NOT NULL REFERENCES provider_mapping_statuses(id),
    provider_id BIGINT NOT NULL REFERENCES providers(id),
    internal_provider_id BIGINT NOT NULL REFERENCES providers(id),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    UNIQUE(provider_map_type_id, provider_id, internal_provider_id)
);

-- Alter providers table to change country_code to countryId
-- First create a temporary table to store country codes and their IDs
CREATE TEMPORARY TABLE temp_country_codes (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE
);

-- Insert distinct country codes from providers table
INSERT INTO temp_country_codes (code)
SELECT DISTINCT country_code FROM providers WHERE country_code IS NOT NULL;

-- Add the new countryId column as BIGSERIAL
ALTER TABLE providers ADD COLUMN countryId BIGSERIAL;

-- Update providers with the corresponding country IDs
UPDATE providers
SET countryId = temp_country_codes.id
FROM temp_country_codes
WHERE providers.country_code = temp_country_codes.code;

-- Drop the old country_code column
ALTER TABLE providers DROP COLUMN country_code;

-- Create indexes for better performance
CREATE INDEX idx_provider_map_type_status_id ON provider_mapping(provider_mapping_status_id);
CREATE INDEX idx_provider_mapping_provider_map_type_id ON provider_mapping(provider_map_type_id);
CREATE INDEX idx_provider_mapping_provider_id ON provider_mapping(provider_id);
CREATE INDEX idx_provider_mapping_internal_provider_id ON provider_mapping(internal_provider_id);
CREATE INDEX idx_provider_countryId ON providers(countryId);
