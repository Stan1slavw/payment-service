# Repository Guidelines

This repository contains a small Spring Boot microservices setup for a course project. It is organized into three service modules plus shared infrastructure for local development.

## Project Structure & Module Organization

- `order-service/`, `payment-service/`, `user-service/`: Independent Spring Boot services with their own `pom.xml` and Maven wrapper.
- `infra/`: Local dependencies and observability tooling, including `docker-compose.yml`, `prometheus.yml`, and `nginx.conf`.
- Typical layout inside each service:
- `src/main/java/...` for production code (package prefix `com.stanislav.<service>`).
- `src/main/resources/` for configuration such as `application.yaml` and Flyway migrations under `db/migration/`.
- `src/test/java/...` for tests.

## Build, Test, and Development Commands

Run commands from a service directory, e.g., `payment-service/`.

- `.\mvnw clean package` builds the service JAR.
- `.\mvnw spring-boot:run` runs the service locally.
- `.\mvnw test` runs unit/integration tests.
- `docker compose up -d` in `infra/` starts Kafka, Postgres, Prometheus, and Grafana for local dev.
- `docker compose down` in `infra/` stops the stack.

## Coding Style & Naming Conventions

- Java 17, Spring Boot conventions, 4-space indentation.
- Classes in `UpperCamelCase`, methods/fields in `lowerCamelCase`.
- Configuration keys live in `application.yaml`; prefer env overrides such as `PAYMENT_DB_URL` or `KAFKA_BOOTSTRAP_SERVERS`.
- Flyway migrations should follow `V1__description.sql` naming.

## Testing Guidelines

- Use Spring Boot test starters (JUnit 5 default).
- Place tests under `src/test/java/...` mirroring production packages.
- Name tests `*Test` or `*IT` to distinguish unit vs integration tests.
- Run all tests with `.\mvnw test` before opening a PR.

## Commit & Pull Request Guidelines

- No Git history is present in this workspace, so use short, descriptive commit subjects (e.g., “Add payment repository”).
- PRs should include:
- A brief summary of changes.
- Testing performed (or a note if not run).
- Any config or environment variables added/changed.

## Security & Configuration Tips

- Default DB/Kafka settings are defined in each service `application.yaml`, but should be overridden via environment variables for real deployments.
- Avoid committing secrets; use local `.env` or IDE run configs instead.
