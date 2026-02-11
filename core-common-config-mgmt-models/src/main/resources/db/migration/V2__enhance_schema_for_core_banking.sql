-- =====================================================
-- Firefly Core Banking - Schema Enhancement Migration
-- =====================================================
-- This migration enhances the configuration management schema
-- with additional core banking attributes, improved naming,
-- and better standardization for enterprise-grade systems.
-- =====================================================

-- =====================================================
-- ENHANCE TENANTS TABLE
-- =====================================================
-- Add missing core banking attributes for tenants
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS legal_entity_name VARCHAR(255);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS tax_identification_number VARCHAR(50);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS registration_number VARCHAR(50);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS incorporation_date DATE;
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS regulatory_license_number VARCHAR(100);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS regulatory_authority VARCHAR(100);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS parent_tenant_id UUID;
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS tenant_type VARCHAR(50) DEFAULT 'STANDARD';
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS industry_sector VARCHAR(100);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS employee_count_range VARCHAR(50);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS annual_revenue_range VARCHAR(50);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS risk_rating VARCHAR(20);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS compliance_tier VARCHAR(50);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS data_classification VARCHAR(50) DEFAULT 'CONFIDENTIAL';
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS sla_tier VARCHAR(50);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS billing_currency_code VARCHAR(3);
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS billing_cycle VARCHAR(20) DEFAULT 'MONTHLY';
ALTER TABLE tenants ADD COLUMN IF NOT EXISTS payment_terms_days INTEGER DEFAULT 30;

-- Add foreign key for parent tenant (hierarchical tenants)
ALTER TABLE tenants ADD CONSTRAINT fk_tenant_parent 
    FOREIGN KEY (parent_tenant_id) REFERENCES tenants(id) ON DELETE SET NULL;

-- Add indexes for new fields
CREATE INDEX IF NOT EXISTS idx_tenants_parent ON tenants(parent_tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenants_type ON tenants(tenant_type);
CREATE INDEX IF NOT EXISTS idx_tenants_risk_rating ON tenants(risk_rating);
CREATE INDEX IF NOT EXISTS idx_tenants_compliance_tier ON tenants(compliance_tier);
CREATE INDEX IF NOT EXISTS idx_tenants_legal_entity ON tenants(legal_entity_name);

-- Add comments for better documentation
COMMENT ON COLUMN tenants.legal_entity_name IS 'Official legal name of the tenant organization';
COMMENT ON COLUMN tenants.tax_identification_number IS 'Tax ID or EIN for the tenant';
COMMENT ON COLUMN tenants.registration_number IS 'Business registration number';
COMMENT ON COLUMN tenants.regulatory_license_number IS 'Banking or financial services license number';
COMMENT ON COLUMN tenants.parent_tenant_id IS 'Reference to parent tenant for hierarchical structures';
COMMENT ON COLUMN tenants.tenant_type IS 'Type of tenant: STANDARD, ENTERPRISE, PARTNER, SUBSIDIARY';
COMMENT ON COLUMN tenants.risk_rating IS 'Risk assessment rating: LOW, MEDIUM, HIGH, CRITICAL';
COMMENT ON COLUMN tenants.compliance_tier IS 'Compliance requirements tier';
COMMENT ON COLUMN tenants.data_classification IS 'Data classification level: PUBLIC, INTERNAL, CONFIDENTIAL, RESTRICTED';

-- =====================================================
-- ENHANCE PROVIDERS TABLE
-- =====================================================
-- Add missing core banking attributes for providers
ALTER TABLE providers ADD COLUMN IF NOT EXISTS provider_category VARCHAR(100);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS certification_level VARCHAR(50);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS compliance_certifications TEXT;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS sla_uptime_percentage DECIMAL(5,2) DEFAULT 99.9;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS max_response_time_ms INTEGER;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS rate_limit_per_second INTEGER;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS rate_limit_per_minute INTEGER;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS rate_limit_per_hour INTEGER;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS supports_sandbox BOOLEAN DEFAULT TRUE;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS sandbox_url VARCHAR(500);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS production_url VARCHAR(500);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS webhook_support BOOLEAN DEFAULT FALSE;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS batch_processing_support BOOLEAN DEFAULT FALSE;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS real_time_processing_support BOOLEAN DEFAULT TRUE;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS supported_countries TEXT;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS supported_currencies TEXT;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS supported_languages TEXT;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS pricing_model VARCHAR(50);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS contract_start_date DATE;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS contract_end_date DATE;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS auto_renewal BOOLEAN DEFAULT FALSE;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS notice_period_days INTEGER DEFAULT 30;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS primary_contact_name VARCHAR(255);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS primary_contact_email VARCHAR(255);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS primary_contact_phone VARCHAR(50);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS escalation_contact_name VARCHAR(255);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS escalation_contact_email VARCHAR(255);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS escalation_contact_phone VARCHAR(50);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS last_health_check_at TIMESTAMP;
ALTER TABLE providers ADD COLUMN IF NOT EXISTS health_check_status VARCHAR(20);
ALTER TABLE providers ADD COLUMN IF NOT EXISTS health_check_url VARCHAR(500);

-- Add indexes for new provider fields
CREATE INDEX IF NOT EXISTS idx_providers_category ON providers(provider_category);
CREATE INDEX IF NOT EXISTS idx_providers_certification ON providers(certification_level);
CREATE INDEX IF NOT EXISTS idx_providers_health_status ON providers(health_check_status);
CREATE INDEX IF NOT EXISTS idx_providers_contract_end ON providers(contract_end_date);

-- Add comments for provider fields
COMMENT ON COLUMN providers.provider_category IS 'Business category: PAYMENT, COMPLIANCE, INFRASTRUCTURE, etc.';
COMMENT ON COLUMN providers.certification_level IS 'Certification level: PCI_DSS, ISO27001, SOC2, etc.';
COMMENT ON COLUMN providers.sla_uptime_percentage IS 'Guaranteed uptime percentage per SLA';
COMMENT ON COLUMN providers.pricing_model IS 'Pricing model: PAY_PER_USE, SUBSCRIPTION, TIERED, CUSTOM';
COMMENT ON COLUMN providers.health_check_status IS 'Current health status: HEALTHY, DEGRADED, DOWN, UNKNOWN';

-- =====================================================
-- ENHANCE PROVIDER_TENANTS TABLE
-- =====================================================
-- Add missing attributes for provider-tenant relationships
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS relationship_type VARCHAR(50) DEFAULT 'STANDARD';
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS billing_model VARCHAR(50);
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS monthly_transaction_limit INTEGER;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS monthly_volume_limit DECIMAL(15,2);
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS cost_per_transaction DECIMAL(10,4);
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS monthly_fee DECIMAL(10,2);
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS setup_fee DECIMAL(10,2);
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS currency_code VARCHAR(3) DEFAULT 'USD';
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS auto_failover_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS fallback_provider_id UUID;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS health_check_interval_seconds INTEGER DEFAULT 300;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS circuit_breaker_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS circuit_breaker_threshold INTEGER DEFAULT 5;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS circuit_breaker_timeout_seconds INTEGER DEFAULT 60;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS last_used_at TIMESTAMP;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS total_requests_count BIGINT DEFAULT 0;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS total_failures_count BIGINT DEFAULT 0;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS average_response_time_ms INTEGER;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS approved_by_user_id UUID;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS approved_at TIMESTAMP;
ALTER TABLE provider_tenants ADD COLUMN IF NOT EXISTS approval_notes TEXT;

-- Add foreign key for fallback provider
ALTER TABLE provider_tenants ADD CONSTRAINT fk_provider_tenant_fallback 
    FOREIGN KEY (fallback_provider_id) REFERENCES providers(id) ON DELETE SET NULL;

-- Add indexes
CREATE INDEX IF NOT EXISTS idx_provider_tenants_relationship_type ON provider_tenants(relationship_type);
CREATE INDEX IF NOT EXISTS idx_provider_tenants_fallback ON provider_tenants(fallback_provider_id);
CREATE INDEX IF NOT EXISTS idx_provider_tenants_last_used ON provider_tenants(last_used_at);

-- Add comments
COMMENT ON COLUMN provider_tenants.relationship_type IS 'Type: STANDARD, PREMIUM, TRIAL, CUSTOM';
COMMENT ON COLUMN provider_tenants.billing_model IS 'Billing model: TRANSACTION_BASED, VOLUME_BASED, FLAT_FEE, HYBRID';
COMMENT ON COLUMN provider_tenants.fallback_provider_id IS 'Backup provider if primary fails';
COMMENT ON COLUMN provider_tenants.circuit_breaker_enabled IS 'Enable circuit breaker pattern for resilience';

-- =====================================================
-- ENHANCE TENANT_SETTINGS TABLE
-- =====================================================
-- Add missing operational and compliance settings
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS ip_whitelist TEXT;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS ip_blacklist TEXT;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS geo_blocking_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS allowed_countries TEXT;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS blocked_countries TEXT;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS fraud_detection_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS fraud_score_threshold INTEGER DEFAULT 75;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS transaction_monitoring_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS aml_screening_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS sanctions_screening_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS kyc_verification_required BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS kyc_refresh_interval_days INTEGER DEFAULT 365;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS document_verification_required BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS biometric_verification_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS two_factor_auth_methods TEXT;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS password_history_count INTEGER DEFAULT 5;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS concurrent_sessions_limit INTEGER DEFAULT 3;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS session_idle_timeout_minutes INTEGER DEFAULT 15;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS force_password_change_on_first_login BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS api_key_rotation_days INTEGER DEFAULT 90;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS encryption_algorithm VARCHAR(50) DEFAULT 'AES-256-GCM';
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS data_retention_policy VARCHAR(50) DEFAULT 'STANDARD';
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS auto_archive_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS archive_after_days INTEGER DEFAULT 2555;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS auto_delete_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS delete_after_days INTEGER DEFAULT 3650;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS disaster_recovery_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS backup_retention_days INTEGER DEFAULT 90;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS point_in_time_recovery_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS cross_region_replication_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS replication_regions TEXT;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS regulatory_reporting_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS regulatory_reporting_frequency VARCHAR(20) DEFAULT 'MONTHLY';
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS sox_compliance_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS hipaa_compliance_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE tenant_settings ADD COLUMN IF NOT EXISTS iso27001_compliance_enabled BOOLEAN DEFAULT FALSE;

-- Add comments for tenant settings
COMMENT ON COLUMN tenant_settings.fraud_score_threshold IS 'Fraud score threshold (0-100) above which transactions are flagged';
COMMENT ON COLUMN tenant_settings.aml_screening_enabled IS 'Enable Anti-Money Laundering screening';
COMMENT ON COLUMN tenant_settings.kyc_refresh_interval_days IS 'Days between KYC verification refreshes';
COMMENT ON COLUMN tenant_settings.encryption_algorithm IS 'Encryption algorithm for sensitive data';
COMMENT ON COLUMN tenant_settings.data_retention_policy IS 'Data retention policy: MINIMAL, STANDARD, EXTENDED, PERMANENT';

-- =====================================================
-- ENHANCE CHANNEL_CONFIGS TABLE
-- =====================================================
-- Add missing channel configuration attributes
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS channel_type VARCHAR(50);
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS requires_authentication BOOLEAN DEFAULT TRUE;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS supports_biometric_auth BOOLEAN DEFAULT FALSE;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS max_concurrent_sessions INTEGER DEFAULT 1;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS session_timeout_minutes INTEGER DEFAULT 15;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS idle_timeout_minutes INTEGER DEFAULT 5;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS rate_limit_per_minute INTEGER DEFAULT 100;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS rate_limit_per_hour INTEGER DEFAULT 5000;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS max_transaction_amount DECIMAL(15,2);
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS daily_transaction_limit DECIMAL(15,2);
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS monthly_transaction_limit DECIMAL(15,2);
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS min_app_version VARCHAR(20);
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS supported_platforms TEXT;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS maintenance_window_start TIME;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS maintenance_window_end TIME;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS availability_schedule TEXT;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS geo_restrictions_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS allowed_countries TEXT;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS blocked_countries TEXT;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS ip_whitelist TEXT;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS features_enabled TEXT;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS features_disabled TEXT;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS custom_branding_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS analytics_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE channel_configs ADD COLUMN IF NOT EXISTS logging_level VARCHAR(20) DEFAULT 'INFO';

-- Add indexes for channel configs
CREATE INDEX IF NOT EXISTS idx_channel_configs_type ON channel_configs(channel_type);
CREATE INDEX IF NOT EXISTS idx_channel_configs_auth_required ON channel_configs(requires_authentication);

-- Add comments
COMMENT ON COLUMN channel_configs.channel_type IS 'Channel type: DIGITAL, PHYSICAL, API, PARTNER';
COMMENT ON COLUMN channel_configs.max_transaction_amount IS 'Maximum transaction amount allowed through this channel';
COMMENT ON COLUMN channel_configs.availability_schedule IS 'JSON schedule defining when channel is available';

-- =====================================================
-- ENHANCE WEBHOOK_CONFIGS TABLE
-- =====================================================
-- Add missing webhook configuration attributes
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS webhook_version VARCHAR(20) DEFAULT '1.0';
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS content_type VARCHAR(50) DEFAULT 'application/json';
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS signature_algorithm VARCHAR(50) DEFAULT 'HMAC-SHA256';
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS verify_ssl BOOLEAN DEFAULT TRUE;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS follow_redirects BOOLEAN DEFAULT FALSE;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS batch_events BOOLEAN DEFAULT FALSE;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS batch_size INTEGER DEFAULT 1;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS batch_timeout_seconds INTEGER DEFAULT 60;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS priority INTEGER DEFAULT 5;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS async_processing BOOLEAN DEFAULT TRUE;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS dead_letter_queue_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS max_dead_letter_retries INTEGER DEFAULT 10;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS circuit_breaker_enabled BOOLEAN DEFAULT TRUE;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS circuit_breaker_threshold INTEGER DEFAULT 5;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS circuit_breaker_timeout_seconds INTEGER DEFAULT 300;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS last_triggered_at TIMESTAMP;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS last_success_at TIMESTAMP;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS last_failure_at TIMESTAMP;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS total_triggers_count BIGINT DEFAULT 0;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS total_success_count BIGINT DEFAULT 0;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS total_failure_count BIGINT DEFAULT 0;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS average_response_time_ms INTEGER;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS health_check_enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE webhook_configs ADD COLUMN IF NOT EXISTS health_check_interval_seconds INTEGER DEFAULT 300;

-- Add indexes for webhooks
CREATE INDEX IF NOT EXISTS idx_webhook_configs_priority ON webhook_configs(priority);
CREATE INDEX IF NOT EXISTS idx_webhook_configs_last_triggered ON webhook_configs(last_triggered_at);

-- Add comments
COMMENT ON COLUMN webhook_configs.signature_algorithm IS 'Algorithm for webhook signature: HMAC-SHA256, HMAC-SHA512, RSA';
COMMENT ON COLUMN webhook_configs.dead_letter_queue_enabled IS 'Enable DLQ for failed webhook deliveries';
COMMENT ON COLUMN webhook_configs.circuit_breaker_enabled IS 'Enable circuit breaker to prevent cascading failures';

-- =====================================================
-- ENHANCE CONFIGURATION_AUDITS TABLE
-- =====================================================
-- Add missing audit trail attributes
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS session_id VARCHAR(100);
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS request_id VARCHAR(100);
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS correlation_id VARCHAR(100);
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS source_system VARCHAR(100);
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS change_category VARCHAR(50);
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS change_severity VARCHAR(20);
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS requires_approval BOOLEAN DEFAULT FALSE;
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS approved_by_user_id UUID;
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS approved_at TIMESTAMP;
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS approval_notes TEXT;
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS rolled_back BOOLEAN DEFAULT FALSE;
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS rolled_back_by_user_id UUID;
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS rolled_back_at TIMESTAMP;
ALTER TABLE configuration_audits ADD COLUMN IF NOT EXISTS rollback_reason TEXT;

-- Add indexes for audits
CREATE INDEX IF NOT EXISTS idx_configuration_audits_session ON configuration_audits(session_id);
CREATE INDEX IF NOT EXISTS idx_configuration_audits_request ON configuration_audits(request_id);
CREATE INDEX IF NOT EXISTS idx_configuration_audits_correlation ON configuration_audits(correlation_id);
CREATE INDEX IF NOT EXISTS idx_configuration_audits_category ON configuration_audits(change_category);
CREATE INDEX IF NOT EXISTS idx_configuration_audits_severity ON configuration_audits(change_severity);
CREATE INDEX IF NOT EXISTS idx_configuration_audits_rolled_back ON configuration_audits(rolled_back);

-- Add comments
COMMENT ON COLUMN configuration_audits.change_category IS 'Category: CONFIGURATION, SECURITY, COMPLIANCE, OPERATIONAL';
COMMENT ON COLUMN configuration_audits.change_severity IS 'Severity: LOW, MEDIUM, HIGH, CRITICAL';
COMMENT ON COLUMN configuration_audits.correlation_id IS 'Correlation ID for tracking related changes across systems';

