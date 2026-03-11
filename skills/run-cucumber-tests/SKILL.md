---
name: run-cucumber-tests
description: Run this repo's Cucumber integration tests via Maven. Use when you need to execute or troubleshoot Cucumber/Gherkin feature tests, including local fake API runs with api.baseUrl.
---

# Run Cucumber tests
- Read `README.md` for the authoritative commands and any local fake API setup notes.
- Make sure the app is running before running tests (`mvn spring-boot:start`)
- Remote API (default): run `mvn clean test` from the repo root.
- Local fake API: enable `SpringBootTest` as described in `README.md`, then run `mvn clean test -Dapi.baseUrl=http://localhost`.
- Prefer the bundled script for repeatable execution.

# Bundled script
- Use `scripts/run_cucumber_tests.sh` from any directory; it runs from the repo root.
- Flags: `--local` runs with `-Dapi.baseUrl=http://localhost` (ensure the local fake API is enabled first). `--base-url <url>` runs with the provided base URL. `--` passes additional arguments directly to Maven.
- Examples: `scripts/run_cucumber_tests.sh`; `scripts/run_cucumber_tests.sh --local`; `scripts/run_cucumber_tests.sh --base-url https://example.com -- -DskipTests=false`.

# Troubleshooting
- If Mockito inline mock maker fails on JDK 21, configure the Mockito agent or switch to a supported mock maker in test config.
- If Maven cannot write to `~/.m2/repository`, fix permissions or set `-Dmaven.repo.local=<path>`.
