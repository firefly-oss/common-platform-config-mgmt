-- Enable UUID extension for generating UUIDs
-- This extension provides functions for generating UUIDs (Universally Unique Identifiers)
-- uuid_generate_v4() generates random UUIDs based on random or pseudo-random numbers
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Provider Types
-- All tables use UUID as primary keys for better distribution and to avoid ID conflicts
-- when merging data from different environments or systems
INSERT INTO provider_types (id, code, name, description, active)
VALUES (uuid_generate_v4(), 'BAAS', 'Banking as a Service', 'Providers that offer banking services through APIs', true);

-- Provider Statuses
-- Each status record gets a unique UUID to ensure referential integrity
INSERT INTO provider_statuses (id, code, name, description, active)
VALUES (uuid_generate_v4(), 'ACTIVE', 'Active', 'Provider is active and operational', true);

-- Treezor Provider
-- Foreign key references use SELECT statements to find the correct UUID values
-- This ensures data consistency even when UUIDs are generated dynamically
INSERT INTO providers (
    id,
    code,
    name,
    description,
    provider_type_id,
    provider_status_id,
    api_base_url,
    country_code,
    currency_code,
    requires_authentication,
    active
)
VALUES (
    uuid_generate_v4(),
    'TREEZOR',
    'Treezor',
    'Treezor Banking as a Service provider',
    (SELECT id FROM provider_types WHERE code = 'BAAS'),
    (SELECT id FROM provider_statuses WHERE code = 'ACTIVE'),
    'https://api.treezor.com',
    'FR',
    'EUR',
    true,
    true
);

-- Provider Process Statuses
INSERT INTO provider_process_statuses (id, code, name, description, active)
VALUES
(uuid_generate_v4(), 'DRAFT', 'Draft', 'Process is in draft state', true),
(uuid_generate_v4(), 'PUBLISHED', 'Published', 'Process is published and ready for use', true),
(uuid_generate_v4(), 'ACTIVE', 'Active', 'Process is active and in use', true),
(uuid_generate_v4(), 'DEPRECATED', 'Deprecated', 'Process is deprecated but still supported', true),
(uuid_generate_v4(), 'RETIRED', 'Retired', 'Process is no longer supported', true);

-- Provider Processes for Treezor
INSERT INTO provider_processes (
    id,
    code,
    name,
    description,
    provider_id,
    process_type,
    process_category,
    is_common,
    active
)
VALUES
(
    uuid_generate_v4(),
    'create-account-process',
    'Create Account Process',
    'Process to create a new account in Treezor',
    (SELECT id FROM providers WHERE code = 'TREEZOR'),
    'ACCOUNT',
    'ONBOARDING',
    false,
    true
),
(
    uuid_generate_v4(),
    'create-document-process',
    'Create Document Process',
    'Process to create a new document in Treezor',
    (SELECT id FROM providers WHERE code = 'TREEZOR'),
    'DOCUMENT',
    'ONBOARDING',
    false,
    true
),
(
    uuid_generate_v4(),
    'create-legal-person-process',
    'Create Legal Person Process',
    'Process to create a new legal person in Treezor',
    (SELECT id FROM providers WHERE code = 'TREEZOR'),
    'LEGAL_PERSON',
    'ONBOARDING',
    false,
    true
),
(
    uuid_generate_v4(),
    'create-natural-person-process',
    'Create Natural Person Process',
    'Process to create a new natural person in Treezor',
    (SELECT id FROM providers WHERE code = 'TREEZOR'),
    'NATURAL_PERSON',
    'ONBOARDING',
    false,
    true
),
(
    uuid_generate_v4(),
    'create-tax-residence-process',
    'Create Tax Residence Process',
    'Process to create a new tax residence in Treezor',
    (SELECT id FROM providers WHERE code = 'TREEZOR'),
    'TAX_RESIDENCE',
    'ONBOARDING',
    false,
    true
),
(
    uuid_generate_v4(),
    'user-kyb-review-process',
    'User KYB Review Process',
    'Process for KYB (Know Your Business) review in Treezor',
    (SELECT id FROM providers WHERE code = 'TREEZOR'),
    'KYB',
    'COMPLIANCE',
    false,
    true
),
(
    uuid_generate_v4(),
    'user-kyc-review-process',
    'User KYC Review Process',
    'Process for KYC (Know Your Customer) review in Treezor',
    (SELECT id FROM providers WHERE code = 'TREEZOR'),
    'KYC',
    'COMPLIANCE',
    false,
    true
);

-- Provider Process Versions
-- For create-account-process
INSERT INTO provider_process_versions (
    id,
    provider_process_id,
    version,
    bpmn_xml,
    provider_process_status_id,
    is_current,
    is_deployed,
    active
)
VALUES (
    uuid_generate_v4(),
    (SELECT id FROM provider_processes WHERE code = 'create-account-process'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_0v7z9dk" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.12.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="create-account-process" name="Create Account Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="Account Creation Requested">
      <bpmn:outgoing>Flow_0y2nh1g</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_0y2nh1g" sourceRef="StartEvent_1" targetRef="Activity_CreateAccount" />
    <bpmn:serviceTask id="Activity_CreateAccount" name="Create Account">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="baas-create-account" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_0y2nh1g</bpmn:incoming>
      <bpmn:outgoing>Flow_194i1gr</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_194i1gr" sourceRef="Activity_CreateAccount" targetRef="Event_18d3ozs" />
    <bpmn:endEvent id="Event_18d3ozs" name="Account Created">
      <bpmn:incoming>Flow_194i1gr</bpmn:incoming>
    </bpmn:endEvent>
  </bpmn:process>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);

-- For create-document-process
INSERT INTO provider_process_versions (
    id,
    provider_process_id,
    version,
    bpmn_xml,
    provider_process_status_id,
    is_current,
    is_deployed,
    active
)
VALUES (
    uuid_generate_v4(),
    (SELECT id FROM provider_processes WHERE code = 'create-document-process'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_0v7z9dk" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.12.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="create-document-process" name="Create Document Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="Document Creation Requested">
      <bpmn:outgoing>Flow_0y2nh1g</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_0y2nh1g" sourceRef="StartEvent_1" targetRef="Activity_CreateDocument" />
    <bpmn:serviceTask id="Activity_CreateDocument" name="Create Document">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="baas-create-document" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_0y2nh1g</bpmn:incoming>
      <bpmn:outgoing>Flow_194i1gr</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_194i1gr" sourceRef="Activity_CreateDocument" targetRef="Event_18d3ozs" />
    <bpmn:endEvent id="Event_18d3ozs" name="Document Created">
      <bpmn:incoming>Flow_194i1gr</bpmn:incoming>
    </bpmn:endEvent>
  </bpmn:process>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);

-- For create-legal-person-process
INSERT INTO provider_process_versions (
    id,
    provider_process_id,
    version,
    bpmn_xml,
    provider_process_status_id,
    is_current,
    is_deployed,
    active
)
VALUES (
    uuid_generate_v4(),
    (SELECT id FROM provider_processes WHERE code = 'create-legal-person-process'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_0v7z9dk" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.12.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="create-legal-person-process" name="Create Legal Person Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="Legal Person Creation Requested">
      <bpmn:outgoing>Flow_0y2nh1g</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_0y2nh1g" sourceRef="StartEvent_1" targetRef="Activity_CreateLegalPerson" />
    <bpmn:serviceTask id="Activity_CreateLegalPerson" name="Create Legal Person">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="baas-create-legal-person" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_0y2nh1g</bpmn:incoming>
      <bpmn:outgoing>Flow_194i1gr</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_194i1gr" sourceRef="Activity_CreateLegalPerson" targetRef="Event_18d3ozs" />
    <bpmn:endEvent id="Event_18d3ozs" name="Legal Person Created">
      <bpmn:incoming>Flow_194i1gr</bpmn:incoming>
    </bpmn:endEvent>
  </bpmn:process>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);

-- For create-natural-person-process
INSERT INTO provider_process_versions (
    id,
    provider_process_id,
    version,
    bpmn_xml,
    provider_process_status_id,
    is_current,
    is_deployed,
    active
)
VALUES (
    uuid_generate_v4(),
    (SELECT id FROM provider_processes WHERE code = 'create-natural-person-process'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_0v7z9dk" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.12.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="create-natural-person-process" name="Create Natural Person Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="Natural Person Creation Requested">
      <bpmn:outgoing>Flow_0y2nh1g</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_0y2nh1g" sourceRef="StartEvent_1" targetRef="Activity_CreateNaturalPerson" />
    <bpmn:serviceTask id="Activity_CreateNaturalPerson" name="Create Natural Person">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="baas-create-natural-person" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_0y2nh1g</bpmn:incoming>
      <bpmn:outgoing>Flow_194i1gr</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_194i1gr" sourceRef="Activity_CreateNaturalPerson" targetRef="Event_18d3ozs" />
    <bpmn:endEvent id="Event_18d3ozs" name="Natural Person Created">
      <bpmn:incoming>Flow_194i1gr</bpmn:incoming>
    </bpmn:endEvent>
  </bpmn:process>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);

-- For create-tax-residence-process
INSERT INTO provider_process_versions (
    id,
    provider_process_id,
    version,
    bpmn_xml,
    provider_process_status_id,
    is_current,
    is_deployed,
    active
)
VALUES (
    uuid_generate_v4(),
    (SELECT id FROM provider_processes WHERE code = 'create-tax-residence-process'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_0v7z9dk" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.12.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="create-tax-residence-process" name="Create Tax Residence Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="Tax Residence Creation Requested">
      <bpmn:outgoing>Flow_0y2nh1g</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_0y2nh1g" sourceRef="StartEvent_1" targetRef="Activity_CreateTaxResidence" />
    <bpmn:serviceTask id="Activity_CreateTaxResidence" name="Create Tax Residence">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="baas-create-tax-residence" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_0y2nh1g</bpmn:incoming>
      <bpmn:outgoing>Flow_194i1gr</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_194i1gr" sourceRef="Activity_CreateTaxResidence" targetRef="Event_18d3ozs" />
    <bpmn:endEvent id="Event_18d3ozs" name="Tax Residence Created">
      <bpmn:incoming>Flow_194i1gr</bpmn:incoming>
    </bpmn:endEvent>
  </bpmn:process>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);

-- For user-kyb-review-process
INSERT INTO provider_process_versions (
    id,
    provider_process_id,
    version,
    bpmn_xml,
    provider_process_status_id,
    is_current,
    is_deployed,
    active
)
VALUES (
    uuid_generate_v4(),
    (SELECT id FROM provider_processes WHERE code = 'user-kyb-review-process'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_0v7z9dk" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.12.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="user-kyb-review-process" name="User KYB Review Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="KYB Review Requested">
      <bpmn:outgoing>Flow_0y2nh1g</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_0y2nh1g" sourceRef="StartEvent_1" targetRef="Activity_KYBReview" />
    <bpmn:serviceTask id="Activity_KYBReview" name="Perform KYB Review">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="baas-kyb-review" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_0y2nh1g</bpmn:incoming>
      <bpmn:outgoing>Flow_194i1gr</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_194i1gr" sourceRef="Activity_KYBReview" targetRef="Event_18d3ozs" />
    <bpmn:endEvent id="Event_18d3ozs" name="KYB Review Completed">
      <bpmn:incoming>Flow_194i1gr</bpmn:incoming>
    </bpmn:endEvent>
  </bpmn:process>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);

-- For user-kyc-review-process
INSERT INTO provider_process_versions (
    id,
    provider_process_id,
    version,
    bpmn_xml,
    provider_process_status_id,
    is_current,
    is_deployed,
    active
)
VALUES (
    uuid_generate_v4(),
    (SELECT id FROM provider_processes WHERE code = 'user-kyc-review-process'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_0v7z9dk" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.12.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="user-kyc-review-process" name="User KYC Review Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="KYC Review Requested">
      <bpmn:outgoing>Flow_0y2nh1g</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_0y2nh1g" sourceRef="StartEvent_1" targetRef="Activity_KYCReview" />
    <bpmn:serviceTask id="Activity_KYCReview" name="Perform KYC Review">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="baas-kyc-review" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_0y2nh1g</bpmn:incoming>
      <bpmn:outgoing>Flow_194i1gr</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_194i1gr" sourceRef="Activity_KYCReview" targetRef="Event_18d3ozs" />
    <bpmn:endEvent id="Event_18d3ozs" name="KYC Review Completed">
      <bpmn:incoming>Flow_194i1gr</bpmn:incoming>
    </bpmn:endEvent>
  </bpmn:process>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);
