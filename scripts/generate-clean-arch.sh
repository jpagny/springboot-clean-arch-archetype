#!/usr/bin/env bash
set -euo pipefail

# --- Defaults (override via CLI args or env) ---
ARCHETYPE_GROUP_DEFAULT="com.jpagny"
ARCHETYPE_ARTIFACT_DEFAULT="springboot-clean-arch-archetype"
ARCHETYPE_VERSION_DEFAULT="1.0.0-SNAPSHOT"

GROUP_ID_DEFAULT="com.mycompany"
ARTIFACT_ID_DEFAULT="my-clean-arch-app"
VERSION_DEFAULT="0.0.1-SNAPSHOT"
PACKAGE_DEFAULT="$GROUP_ID_DEFAULT"
OUTPUT_DIR_DEFAULT="$HOME/Documents/Projects/devs/archetype"

MAVEN_BIN_DEFAULT="/app/extra/plugins/maven/lib/maven3/bin/mvn"

usage() {
  cat <<EOF
Usage:
  $(basename "$0") [options]

Options:
  -o, --output-dir    Output directory (default: $OUTPUT_DIR_DEFAULT)
  -g, --group-id      groupId (default: $GROUP_ID_DEFAULT)
  -a, --artifact-id   artifactId (default: $ARTIFACT_ID_DEFAULT)
  -p, --package       base package (default: same as groupId)
  -v, --version       project version (default: $VERSION_DEFAULT)

  --archetype-group   archetype groupId (default: $ARCHETYPE_GROUP_DEFAULT)
  --archetype-artifact archetype artifactId (default: $ARCHETYPE_ARTIFACT_DEFAULT)
  --archetype-version archetype version (default: $ARCHETYPE_VERSION_DEFAULT)

  --mvn               path to Maven binary (default: $MAVEN_BIN_DEFAULT)

Examples:
  $(basename "$0") -g com.acme -a order-service -p com.acme.orders -o /tmp/GEN
  $(basename "$0") -g com.acme -a order-service              # package defaults to groupId
EOF
}

# --- Parse args ---
OUTPUT_DIR="$OUTPUT_DIR_DEFAULT"
GROUP_ID="$GROUP_ID_DEFAULT"
ARTIFACT_ID="$ARTIFACT_ID_DEFAULT"
VERSION="$VERSION_DEFAULT"
PACKAGE=""

ARCHETYPE_GROUP="$ARCHETYPE_GROUP_DEFAULT"
ARCHETYPE_ARTIFACT="$ARCHETYPE_ARTIFACT_DEFAULT"
ARCHETYPE_VERSION="$ARCHETYPE_VERSION_DEFAULT"

MAVEN_BIN="$MAVEN_BIN_DEFAULT"

while [[ $# -gt 0 ]]; do
  case "$1" in
    -o|--output-dir) OUTPUT_DIR="$2"; shift 2;;
    -g|--group-id) GROUP_ID="$2"; shift 2;;
    -a|--artifact-id) ARTIFACT_ID="$2"; shift 2;;
    -p|--package) PACKAGE="$2"; shift 2;;
    -v|--version) VERSION="$2"; shift 2;;

    --archetype-group) ARCHETYPE_GROUP="$2"; shift 2;;
    --archetype-artifact) ARCHETYPE_ARTIFACT="$2"; shift 2;;
    --archetype-version) ARCHETYPE_VERSION="$2"; shift 2;;

    --mvn) MAVEN_BIN="$2"; shift 2;;

    -h|--help) usage; exit 0;;
    *) echo "Unknown option: $1"; usage; exit 1;;
  esac
done

if [[ -z "$PACKAGE" ]]; then
  PACKAGE="$GROUP_ID"
fi

# --- Safety checks ---
if [[ ! -x "$MAVEN_BIN" ]]; then
  echo "ERROR: Maven binary not found/executable: $MAVEN_BIN"
  echo "Tip: pass --mvn /path/to/mvn"
  exit 1
fi

mkdir -p "$OUTPUT_DIR"
cd "$OUTPUT_DIR"

# Remove previous generated project if it exists
rm -rf "$OUTPUT_DIR/$ARTIFACT_ID"

"$MAVEN_BIN" -B org.apache.maven.plugins:maven-archetype-plugin:3.4.1:generate \
  -DarchetypeCatalog=local \
  -DarchetypeGroupId="$ARCHETYPE_GROUP" \
  -DarchetypeArtifactId="$ARCHETYPE_ARTIFACT" \
  -DarchetypeVersion="$ARCHETYPE_VERSION" \
  -DgroupId="$GROUP_ID" \
  -DartifactId="$ARTIFACT_ID" \
  -Dversion="$VERSION" \
  -Dpackage="$PACKAGE" \
  -DinteractiveMode=false \
  -DoutputDirectory="$OUTPUT_DIR"

echo "✅ Generated: $OUTPUT_DIR/$ARTIFACT_ID"
