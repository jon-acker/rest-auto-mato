# AGENTS

## Skills
Each skill below has its own `SKILL.md`; read the relevant file whenever the associated name is invoked or the task clearly matches its description.

### Available skills
- `skill-creator`: Guide for creating effective skills. Use when users want to create or update a skill that extends Codex with specialized knowledge, workflows, or tool integrations. (path `~/Library/Caches/JetBrains/IntelliJIdea2025.3/aia/codex/skills/.system/skill-creator/SKILL.md`)
- `skill-installer`: Install Codex skills into `$CODEX_HOME/skills` from curated lists or GitHub repos. Use when a user asks to list installable skills, install a curated skill, or install from another repo (path `~/Library/Caches/JetBrains/IntelliJIdea2025.3/aia/codex/skills/.system/skill-installer/SKILL.md`).
- `run-cucumber-tests`: Run this repo's Cucumber integration suites and local fake API variants via Maven. Use when you need to execute or debug Cucumber/Gherkin feature tests, including the local fake API option described in README.md. (path `/Users/jonathanacker/Library/Caches/JetBrains/IntelliJIdea2025.3/aia/codex/skills/run-cucumber-tests/SKILL.md`)

## How to use skills
1. Discovery: The list above covers the skills available in this session. The skill bodies live in the listed paths.
2. Trigger rules: If a user names a skill (with `$SkillName` or plain text) or the task matches a skill description, use that skill for the turn. If multiple skills apply, use all of them. Do not carry skills across turns unless re-mentioned.
3. Missing or blocked skills: If a named skill is missing or unreadable, call that out briefly and proceed with the next-best approach.
4. Skill usage workflow:
   - Open the relevant `SKILL.md` and read only what you need to follow its workflow.
   - Resolve relative paths against the skill directory listed above.
   - If the skill references extra folders (e.g., `references/`), load only the files you need; avoid bulk-loading everything.
   - Prefer running or patching scripts provided by the skill instead of retyping large code blocks.
   - Reuse assets or templates supplied by the skill when possible.
5. Coordination: If multiple skills apply, choose the minimal set that covers the request and state the order you’ll use them. Announce which skill(s) you’re using and why. If you skip an obvious skill, explain why.
6. Context hygiene:
   - Keep context small; summarize long sections instead of pasting them.
   - Avoid deep reference-chasing; prefer opening only files directly linked from `SKILL.md` unless blocked.
   - When variants exist (frameworks, providers, domains), pick the relevant reference file(s) only and note that choice.
7. Safety and fallback: If a skill can’t be applied cleanly (missing files, unclear instructions), explain the issue and proceed with the next-best approach.

## Running tests
- Refer to `README.md` for the authoritative instructions.
- Remote API: `mvn clean test`.
- Local fake API: enable the `SpringBootTest` on line 13 of `README.md` and run `mvn clean test -Dapi.baseUrl=http://localhost`.

## Coding standards
- Aim for Domain-Driven Design clarity: align code structure with the current ubiquitous language and keep aggregates, bounded contexts, and services legible for collaborators.
- Feature files and scenarios are authored and reviewed by humans before implementing code. They follow Gherkin syntax and the ubiquitous language derived from the business domain.
- Use fluent api design where it makes sense
- Use Java streams unless it results in more complex or much slower code
