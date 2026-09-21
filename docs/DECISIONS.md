# Architecture decisions

Architecture-only scaffold. Decisions will be recorded here before implementation.

- One Git repository and one evolving source tree.
- Modular monolith as the initial backend shape.
- Web, mobile, API, shared contracts, infrastructure, database, documentation and automation remain separate top-level areas.
- Core release precedes the AI release; AI is added only after the Core release gate.

