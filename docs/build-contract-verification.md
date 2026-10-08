# Architecture test compilation follow-up

Base: fetched canonical main c8a67dafffdf30194a7bc33e88162d090bdbecba.
Network routing probe compiles/packages unchanged production source, then fails
compileTestKotlin in LayerRulesTest because scope assertions use APIs intended
for declaration lists. No local EARS/state helper or AGENTS file was found.
Use this requirement/task/evidence record and the existing portable EARS helper.

Requirement: WHEN architecture verification runs THE SYSTEM SHALL select actual
production files and evaluate the documented import boundaries without vacuous
empty scopes or exemptions for pre-existing violations.

SPEAR spec/prove: observed test compilation failure on Konsist 0.17.3. Repair
the test API usage and expose real rule failures; do not change gameplay,
configuration, persistence or weaken assertions to claim a passing build.

Source audit already finds application imports from infrastructure config and
the plugin class. This PR must remain draft if repaired tests expose those
violations. Correct runtime-layer separation is a separate implementation
decision requiring compatibility design; it is not silently folded into a
network repository-routing change.

Verification: test compilation now passes on Java 21/Konsist 0.17.3. All three
architecture tests execute: domain and infrastructure pass; application fails
on VoteCommand, VotePartyService, VoteService and VoteSitesCommand importing
infrastructure. Wildcard imports are treated as their package names; no empty
scope, blanket exception or existing-violation allowance is added. Production
source, config, permissions, reward/storage semantics and API signatures are
unchanged. This is a draft test repair, not a passing Votes release.

The canonical clean/test/shadowJar attempt failed at test assertions (not
compilation); unchanged production compiled in the network probe. Network
repository-routing initialization was supplied for the check without changing
this source repository's dependency declarations. Existing EARS requirements
and whitespace validation pass. Review and safe runtime boundary repair remain
pending. No upload, merge, activation or live acceptance.
