#!/usr/bin/env bash
set -e

OUTPUT_DIR="//home/orishi/Documents/Projects/devs/archetype"
ARCHETYPE_GROUP="com.jpagny"
ARCHETYPE_ARTIFACT="springboot-clean-arch-archetype"
ARCHETYPE_VERSION="1.0.0-SNAPSHOT"

mkdir -p "$OUTPUT_DIR"

cd "$OUTPUT_DIR"

/app/extra/plugins/maven/lib/maven3/bin/mvn -B org.apache.maven.plugins:maven-archetype-plugin:3.4.1:generate \
  -DarchetypeCatalog=local \
  -DarchetypeGroupId="$ARCHETYPE_GROUP" \
  -DarchetypeArtifactId="$ARCHETYPE_ARTIFACT" \
  -DarchetypeVersion="$ARCHETYPE_VERSION" \
  -DgroupId=com.mycompany \
  -DartifactId=my-clean-arch-app \
  -Dversion=0.0.1-SNAPSHOT \
  -Dpackage=com.mycompany \
  -DinteractiveMode=false \
  -DoutputDirectory="$OUTPUT_DIR"
