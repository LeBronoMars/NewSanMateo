# shortcut for running flavour-independent, debug-variant-only, important development checks of the current build
set -xe

PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

"$PROJECT_DIR"/gradlew clean checkstyle findbugs pmd lintDebug testDebugUnitTestCoverage sonarqube -PwithDexcount
