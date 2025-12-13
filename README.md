# springboot-clean-arch-archetype
Spring Boot Clean Architecture Archetype

## Build & install the archetype

Run the following command at the project root to build and install the archetype into your local Maven repository (`~/.m2/repository`):

```
mvn -B -U -DskipTests clean install
```

Quick notes:
- `clean install` builds and installs the archetype locally
- `-DskipTests` skips test execution (faster)
- `-U` forces snapshot updates if needed
- `-B` enables batch mode (useful in CI)

## Generate a new project from the archetype

Once the archetype is installed in your local Maven repository, generate a new project with:

```
mvn archetype:generate \
  -DarchetypeCatalog=local \
  -DarchetypeArtifactId=springboot-clean-arch-archetype \
  -DarchetypeGroupId=com.jpagny \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.mycompany \
  -DartifactId=my-clean-arch-app \
  -Dversion=0.0.1-SNAPSHOT \
  -Dpackage=com.mycompany \
  -DinteractiveMode=false \
  -DoutputDirectory=<path> \
  -B
```

One-liner command:

```
mvn -B archetype:generate -DarchetypeCatalog=local -DarchetypeArtifactId=springboot-clean-arch-archetype -DarchetypeGroupId=com.jpagny -DarchetypeVersion=1.0.0-SNAPSHOT -DgroupId=com.mycompany -DartifactId=my-clean-arch-app -Dversion=0.0.1-SNAPSHOT -Dpackage=com.mycompany -DinteractiveMode=false -DoutputDirectory=<path>
```


Notes:
- You can run the generation in the directory where you want to create the project.
- `-DarchetypeCatalog=local` forces Maven to use your local archetype catalog (useful right after a local install).
- `-DoutputDirectory <path>` lets you choose where the new project will be generated; replace `<path>` with your target folder.
- `-B` enables batch mode (non-interactive), convenient for scripts/CI.
