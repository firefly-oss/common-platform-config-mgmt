# Tests for common-platform-config-mgmt-interfaces

This directory contains unit tests for the interfaces module, specifically for the validation logic.

## Test Files

### SecretConfigurationValidatorTest.java

Comprehensive JUnit 5 test suite for the `@ValidSecretConfiguration` annotation and `SecretConfigurationValidator` class.

**Test Coverage:**
- ✅ Valid secret parameters (isSecret=true with credentialVaultId)
- ✅ Valid non-secret parameters (isSecret=false with parameterValue)
- ✅ Invalid secret parameters (missing credentialVaultId)
- ✅ Invalid secret parameters (has both credentialVaultId and parameterValue)
- ✅ Invalid non-secret parameters (missing parameterValue)
- ✅ Invalid non-secret parameters (has credentialVaultId when shouldn't)
- ✅ Edge cases (null isSecret, blank values, UUID formats)
- ✅ Both ProviderParameterDTO and EnvironmentConfigDTO

**Total Test Cases:** 20+

## Running Tests

### Option 1: Using Maven (Recommended)

```bash
# Run all tests in the interfaces module
mvn test -pl common-platform-config-mgmt-interfaces

# Run only the validator tests
mvn test -Dtest=SecretConfigurationValidatorTest -pl common-platform-config-mgmt-interfaces

# Run tests with verbose output
mvn test -Dtest=SecretConfigurationValidatorTest -pl common-platform-config-mgmt-interfaces -X
```

### Option 2: Using IDE

1. Open the project in IntelliJ IDEA or Eclipse
2. Navigate to `SecretConfigurationValidatorTest.java`
3. Right-click on the class or individual test methods
4. Select "Run" or "Debug"

### Option 3: Manual Validation Test

If you encounter dependency issues with Maven, you can run the manual validation test:

```bash
# Compile the project first
mvn clean compile -DskipTests

# Run the manual test (see docs/examples/ManualValidationTest.java)
# This requires setting up the classpath manually
```

## Test Structure

The tests are organized using JUnit 5's nested test classes:

```
SecretConfigurationValidatorTest
├── ProviderParameterDTOTests
│   ├── Valid scenarios (2 tests)
│   ├── Invalid scenarios (6 tests)
│   └── Edge cases (2 tests)
├── EnvironmentConfigDTOTests
│   ├── Valid scenarios (2 tests)
│   ├── Invalid scenarios (4 tests)
│   └── Special scenarios (1 test)
└── EdgeCaseTests
    ├── UUID format handling (1 test)
    ├── Multiple DTOs validation (1 test)
    └── Long values handling (1 test)
```

## Dependencies

The tests require the following dependencies (already configured in `pom.xml`):

- **JUnit 5** (`junit-jupiter`) - Testing framework
- **Jakarta Validation API** (`jakarta.validation-api`) - Validation API
- **Hibernate Validator** (`hibernate-validator`) - Validation implementation
- **Lombok** - For DTO builders

## Validation Rules Tested

### For Secret Parameters/Configs (isSecret=true):

1. ✅ `credentialVaultId` must NOT be null
2. ✅ `credentialVaultId` must NOT be blank
3. ✅ `parameterValue`/`configValue` MUST be null

### For Non-Secret Parameters/Configs (isSecret=false):

1. ✅ `parameterValue`/`configValue` must NOT be null
2. ✅ `parameterValue`/`configValue` must NOT be blank
3. ✅ `credentialVaultId` MUST be null

## Example Test Output

```
SecretConfigurationValidator Tests
├─ ProviderParameterDTO Validation Tests
│  ├─ ✅ Should pass validation when isSecret=true with credentialVaultId and null parameterValue
│  ├─ ✅ Should pass validation when isSecret=false with parameterValue and null credentialVaultId
│  ├─ ✅ Should fail validation when isSecret=true but credentialVaultId is null
│  ├─ ✅ Should fail validation when isSecret=true but credentialVaultId is blank
│  ├─ ✅ Should fail validation when isSecret=true but parameterValue is not null
│  ├─ ✅ Should fail validation when isSecret=false but parameterValue is null
│  ├─ ✅ Should fail validation when isSecret=false but parameterValue is blank
│  ├─ ✅ Should fail validation when isSecret=false but credentialVaultId is not null
│  └─ ✅ Should pass validation when isSecret is null (treated as false)
├─ EnvironmentConfigDTO Validation Tests
│  ├─ ✅ Should pass validation when isSecret=true with credentialVaultId and null configValue
│  ├─ ✅ Should pass validation when isSecret=false with configValue and null credentialVaultId
│  ├─ ✅ Should fail validation when isSecret=true but credentialVaultId is null
│  ├─ ✅ Should fail validation when isSecret=true but configValue is not null
│  ├─ ✅ Should fail validation when isSecret=false but configValue is null
│  ├─ ✅ Should fail validation when isSecret=false but credentialVaultId is not null
│  └─ ✅ Should pass validation for encryption key stored in vault
└─ Edge Cases and Special Scenarios
   ├─ ✅ Should handle UUID format in credentialVaultId
   ├─ ✅ Should validate multiple DTOs independently
   └─ ✅ Should handle very long credentialVaultId values

Test run finished after 1234 ms
[        20 tests successful      ]
[         0 tests failed          ]
```

## Troubleshooting

### Issue: "Could not resolve dependencies"

If you encounter dependency resolution issues:

1. Check your Maven settings (`~/.m2/settings.xml`)
2. Ensure you have access to the required repositories
3. Try running with `-U` flag to force update: `mvn clean test -U`

### Issue: "Test not found"

If the test class is not found:

1. Ensure the test directory structure is correct
2. Verify the test class is in the correct package
3. Run `mvn clean compile test-compile` to recompile

### Issue: "Validation not working"

If validation doesn't seem to work:

1. Ensure Hibernate Validator is on the classpath
2. Check that the `@ValidSecretConfiguration` annotation is present on the DTO
3. Verify the validator factory is initialized correctly

## Contributing

When adding new tests:

1. Follow the existing naming convention: `test[Scenario][Condition]`
2. Use descriptive `@DisplayName` annotations
3. Include both positive and negative test cases
4. Add comments explaining complex scenarios
5. Ensure tests are independent and can run in any order

## Related Documentation

- [Security Vault Integration Guide](../../../docs/SECURITY_VAULT_INTEGRATION.md)
- [Secure Configuration Examples](../../../docs/examples/SecureConfigurationExample.java)
- [Validation Example](../../../docs/examples/ValidationExample.java)
- [Manual Validation Test](../../../docs/examples/ManualValidationTest.java)

