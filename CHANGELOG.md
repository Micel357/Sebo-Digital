# Changelog

Notable changes are recorded here. Git tags and GitHub Releases should point to validated commits.

## Unreleased — v1.1.0

### Added
- A Portuguese Cucumber scenario for the existing customer registration flow.
- A focused purchase workflow test suite.

### Changed
- Migrated automated tests from JUnit 4 to JUnit 5.
- Added Mockito isolation for the employee dependency in book service tests.
- Replaced scenarios that only simulated unimplemented features with assertions against existing service behavior.
- Updated the Maven configuration and README for the current test stack and application state.

### Removed
- Placeholder scenarios for persistence, security, backups, load testing, external payment gateways, and notifications that had no corresponding implementation.

## v1.0.0 — academic baseline

- Preserved the academic Sebo Digital snapshot at commit 4373a4cf45b355706a6090a8abf877e299d39771.
- The baseline includes in-memory services and the original JUnit 4 test suite. Its archived Surefire report records 1 failure and 2 errors.
