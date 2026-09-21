# Wealth-e implementation plan

Architecture-only scaffold. Application implementation is intentionally not included in this commit.

## Ordered phases

- [ ] P0 Discovery: architecture, ERD, ADRs, UX sitemap, threat model, assumptions.
- [ ] P1 Foundation: monorepo foundations, Compose, API, database, web, mobile, contracts, CI.
- [ ] P2 Auth and demo fixtures.
- [ ] P3 Ledger, accounts, transfers and reconciliation.
- [ ] P4 Instruments, investments, prices, FX and performance.
- [ ] P5 Property, liabilities and budgeting.
- [ ] P6 Analytics, snapshots and scenarios.
- [ ] P7 Web completion and accessibility.
- [ ] P8 Mobile completion and parity checks.
- [ ] P9 Core hardening and `v1.0.0-core` release gate.
- [ ] P10 AI foundation after the Core gate.
- [ ] P11 AI tools and narratives.
- [ ] P12 RAG, anomaly detection and safety.
- [ ] P13 AI hardening and `v2.0.0-ai` release gate.

## Release checkout commands

```text
git switch --detach v1.0.0-core
git switch --detach v2.0.0-ai
git switch main
```

Tags are not created by this architecture scaffold. They require verified release gates and explicit authorization.

