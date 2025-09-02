-- Common Provider
INSERT INTO providers (
    id,
    code,
    name,
    description,
    provider_type_id,
    provider_status_id,
    country_code,
    currency_code,
    requires_authentication,
    active
)
VALUES (
    uuid_generate_v4(),
    'COMMON',
    'Common',
    'Common provider for shared processes',
    (SELECT id FROM provider_types WHERE code = 'BAAS'),
    (SELECT id FROM provider_statuses WHERE code = 'ACTIVE'),
    'EU',
    'EUR',
    true,
    true
);

-- Provider Processes for TREEZOR - create-beneficiary-process
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
VALUES (
    uuid_generate_v4(),
    'create-beneficiary-process',
    'Create Beneficiary Process',
    'Process to create a new beneficiary in Treezor',
    (SELECT id FROM providers WHERE code = 'TREEZOR'),
    'BENEFICIARY',
    'ONBOARDING',
    false,
    true
);

-- Provider Processes for COMMON
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
    'send-verification-sms',
    'Send Verification SMS Process',
    'Process to send verification SMS',
    (SELECT id FROM providers WHERE code = 'COMMON'),
    'VERIFICATION',
    'SECURITY',
    true,
    true
),
(
    uuid_generate_v4(),
    'send-verification-email',
    'Send Verification Email Process',
    'Process to send verification email',
    (SELECT id FROM providers WHERE code = 'COMMON'),
    'VERIFICATION',
    'SECURITY',
    true,
    true
),
(
    uuid_generate_v4(),
    'validate-verification-code',
    'Validate Verification Code Process',
    'Process to validate verification code',
    (SELECT id FROM providers WHERE code = 'COMMON'),
    'VERIFICATION',
    'SECURITY',
    true,
    true
);

-- Provider Process Versions for create-beneficiary-process
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
    (SELECT id FROM provider_processes WHERE code = 'create-beneficiary-process'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_0v7z9dk" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.12.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="create-beneficiary-process" name="Create Beneficiary Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="Beneficiary Creation Requested">
      <bpmn:outgoing>Flow_0y2nh1g</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_0y2nh1g" sourceRef="StartEvent_1" targetRef="Activity_CreateBeneficiary" />
    <bpmn:serviceTask id="Activity_CreateBeneficiary" name="Create Beneficiary">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="baas-create-beneficiary" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_0y2nh1g</bpmn:incoming>
      <bpmn:outgoing>Flow_194i1gr</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_194i1gr" sourceRef="Activity_CreateBeneficiary" targetRef="Event_18d3ozs" />
    <bpmn:endEvent id="Event_18d3ozs" name="Beneficiary Created">
      <bpmn:incoming>Flow_194i1gr</bpmn:incoming>
    </bpmn:endEvent>
  </bpmn:process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="create-beneficiary-process">
      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">
        <dc:Bounds x="179" y="99" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="159" y="142" width="77" height="27" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_1kv9xkk_di" bpmnElement="Activity_CreateBeneficiary">
        <dc:Bounds x="270" y="77" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Event_18d3ozs_di" bpmnElement="Event_18d3ozs">
        <dc:Bounds x="432" y="99" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="410" y="142" width="80" height="14" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNEdge id="Flow_0y2nh1g_di" bpmnElement="Flow_0y2nh1g">
        <di:waypoint x="215" y="117" />
        <di:waypoint x="270" y="117" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_194i1gr_di" bpmnElement="Flow_194i1gr">
        <di:waypoint x="370" y="117" />
        <di:waypoint x="432" y="117" />
      </bpmndi:BPMNEdge>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);

-- Provider Process Versions for send-verification-sms
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
    (SELECT id FROM provider_processes WHERE code = 'send-verification-sms'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_1a2b3c4d" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.34.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="send-verification-sms" name="Send Verification SMS Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="SMS Verification Requested">
      <bpmn:outgoing>Flow_1</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_1" sourceRef="StartEvent_1" targetRef="Activity_CreateSCAOperation" />
    <bpmn:serviceTask id="Activity_CreateSCAOperation" name="Create SCA Operation">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="create-sca-operation-task" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_1</bpmn:incoming>
      <bpmn:outgoing>Flow_2</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_2" sourceRef="Activity_CreateSCAOperation" targetRef="Activity_SendSMS" />
    <bpmn:serviceTask id="Activity_SendSMS" name="Send Verification SMS">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="send-verification-sms-task" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_2</bpmn:incoming>
      <bpmn:outgoing>Flow_3</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_3" sourceRef="Activity_SendSMS" targetRef="Activity_CreateSCAChallenge" />
    <bpmn:serviceTask id="Activity_CreateSCAChallenge" name="Create SCA Challenge">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="create-sca-challenge-task" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_3</bpmn:incoming>
      <bpmn:outgoing>Flow_4</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:endEvent id="EndEvent_1" name="SMS Verification Completed">
      <bpmn:incoming>Flow_4</bpmn:incoming>
    </bpmn:endEvent>
    <bpmn:sequenceFlow id="Flow_4" sourceRef="Activity_CreateSCAChallenge" targetRef="EndEvent_1" />
  </bpmn:process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="send-verification-sms">
      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_1" bpmnElement="StartEvent_1">
        <dc:Bounds x="179" y="99" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="159" y="142" width="77" height="27" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_CreateSCAOperation_di" bpmnElement="Activity_CreateSCAOperation">
        <dc:Bounds x="270" y="77" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_SendSMS_di" bpmnElement="Activity_SendSMS">
        <dc:Bounds x="430" y="77" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_CreateSCAChallenge_di" bpmnElement="Activity_CreateSCAChallenge">
        <dc:Bounds x="590" y="77" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="EndEvent_1_di" bpmnElement="EndEvent_1">
        <dc:Bounds x="752" y="99" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="730" y="142" width="80" height="27" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNEdge id="Flow_1_di" bpmnElement="Flow_1">
        <di:waypoint x="215" y="117" />
        <di:waypoint x="270" y="117" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_2_di" bpmnElement="Flow_2">
        <di:waypoint x="370" y="117" />
        <di:waypoint x="430" y="117" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_3_di" bpmnElement="Flow_3">
        <di:waypoint x="530" y="117" />
        <di:waypoint x="590" y="117" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_4_di" bpmnElement="Flow_4">
        <di:waypoint x="690" y="117" />
        <di:waypoint x="752" y="117" />
      </bpmndi:BPMNEdge>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);

-- Provider Process Versions for send-verification-email
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
    (SELECT id FROM provider_processes WHERE code = 'send-verification-email'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_1a2b3c4d" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.34.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="send-verification-email" name="Send Verification Email Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="Email Verification Requested">
      <bpmn:outgoing>Flow_1</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_1" sourceRef="StartEvent_1" targetRef="Activity_CreateSCAOperation" />
    <bpmn:serviceTask id="Activity_CreateSCAOperation" name="Create SCA Operation">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="create-sca-operation-task" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_1</bpmn:incoming>
      <bpmn:outgoing>Flow_2</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_2" sourceRef="Activity_CreateSCAOperation" targetRef="Activity_SendEmail" />
    <bpmn:serviceTask id="Activity_SendEmail" name="Send Verification Email">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="send-verification-email-task" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_2</bpmn:incoming>
      <bpmn:outgoing>Flow_3</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:sequenceFlow id="Flow_3" sourceRef="Activity_SendEmail" targetRef="Activity_CreateSCAChallenge" />
    <bpmn:serviceTask id="Activity_CreateSCAChallenge" name="Create SCA Challenge">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="create-sca-challenge-task" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_3</bpmn:incoming>
      <bpmn:outgoing>Flow_4</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:endEvent id="EndEvent_1" name="Email Verification Completed">
      <bpmn:incoming>Flow_4</bpmn:incoming>
    </bpmn:endEvent>
    <bpmn:sequenceFlow id="Flow_4" sourceRef="Activity_CreateSCAChallenge" targetRef="EndEvent_1" />
  </bpmn:process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="send-verification-email">
      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_1" bpmnElement="StartEvent_1">
        <dc:Bounds x="179" y="99" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="159" y="142" width="77" height="27" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_CreateSCAOperation_di" bpmnElement="Activity_CreateSCAOperation">
        <dc:Bounds x="270" y="77" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_SendEmail_di" bpmnElement="Activity_SendEmail">
        <dc:Bounds x="430" y="77" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_CreateSCAChallenge_di" bpmnElement="Activity_CreateSCAChallenge">
        <dc:Bounds x="590" y="77" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="EndEvent_1_di" bpmnElement="EndEvent_1">
        <dc:Bounds x="752" y="99" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="730" y="142" width="80" height="27" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNEdge id="Flow_1_di" bpmnElement="Flow_1">
        <di:waypoint x="215" y="117" />
        <di:waypoint x="270" y="117" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_2_di" bpmnElement="Flow_2">
        <di:waypoint x="370" y="117" />
        <di:waypoint x="430" y="117" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_3_di" bpmnElement="Flow_3">
        <di:waypoint x="530" y="117" />
        <di:waypoint x="590" y="117" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_4_di" bpmnElement="Flow_4">
        <di:waypoint x="690" y="117" />
        <di:waypoint x="752" y="117" />
      </bpmndi:BPMNEdge>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);

-- Provider Process Versions for validate-verification-code
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
    (SELECT id FROM provider_processes WHERE code = 'validate-verification-code'),
    '1.0.0',
    '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:zeebe="http://camunda.org/schema/zeebe/1.0" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:modeler="http://camunda.org/schema/modeler/1.0" id="Definitions_2a3b4c5d" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="5.34.0" modeler:executionPlatform="Camunda Cloud" modeler:executionPlatformVersion="8.2.0">
  <bpmn:process id="validate-verification-code" name="Validate Verification Email Process" isExecutable="true">
    <bpmn:startEvent id="StartEvent_1" name="Email Verification Code Validation Requested">
      <bpmn:outgoing>Flow_1</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_1" sourceRef="StartEvent_1" targetRef="Activity_ValidateSCA" />
    <bpmn:serviceTask id="Activity_ValidateSCA" name="Validate SCA Challenge">
      <bpmn:extensionElements>
        <zeebe:taskDefinition type="validate-sca-challenge-task" />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_1</bpmn:incoming>
      <bpmn:outgoing>Flow_2</bpmn:outgoing>
    </bpmn:serviceTask>
    <bpmn:endEvent id="EndEvent_1" name="Email Verification Code Validation Completed">
      <bpmn:incoming>Flow_2</bpmn:incoming>
    </bpmn:endEvent>
    <bpmn:sequenceFlow id="Flow_2" sourceRef="Activity_ValidateSCA" targetRef="EndEvent_1" />
  </bpmn:process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="validate-verification-code">
      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_1" bpmnElement="StartEvent_1">
        <dc:Bounds x="179" y="99" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="153" y="142" width="90" height="40" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_ValidateSCA_di" bpmnElement="Activity_ValidateSCA">
        <dc:Bounds x="270" y="77" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="EndEvent_1_di" bpmnElement="EndEvent_1">
        <dc:Bounds x="432" y="99" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="405" y="142" width="90" height="40" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNEdge id="Flow_1_di" bpmnElement="Flow_1">
        <di:waypoint x="215" y="117" />
        <di:waypoint x="270" y="117" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_2_di" bpmnElement="Flow_2">
        <di:waypoint x="370" y="117" />
        <di:waypoint x="432" y="117" />
      </bpmndi:BPMNEdge>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn:definitions>',
    (SELECT id FROM provider_process_statuses WHERE code = 'ACTIVE'),
    true,
    true,
    true
);