#!/usr/bin/env bash
# Run the FLang interpreter without Gradle noise.
# Rebuilds only if sources changed, then launches the plain launcher.
# Usage: ./run.sh [-v] [path]
#   -v, --verbose  print the parsed AST and, for files, the optimized AST
set -euo pipefail
cd "$(dirname "$0")"

# Rebuild quietly if any .java file is newer than the installed launcher.
LAUNCHER="app/build/install/app/bin/app"
if [[ ! -x "$LAUNCHER" ]] || [[ -n "$(find app/src -name '*.java' -newer "$LAUNCHER" 2>/dev/null)" ]]; then
  ./gradlew installDist -q
fi

exec "$LAUNCHER" "$@"
