#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
repo_root="$(cd "${script_dir}/../../.." && pwd)"

app_started=false

if ! command -v mvn >/dev/null 2>&1; then
  echo "mvn not found in PATH" >&2
  exit 1
fi

if [[ ! -f "${repo_root}/pom.xml" ]]; then
  echo "pom.xml not found at repo root: ${repo_root}" >&2
  exit 1
fi

base_url=""
extra_args=()

while [[ $# -gt 0 ]]; do
  case "$1" in
    --base-url)
      base_url="$2"
      shift 2
      ;;
    --local)
      base_url="http://localhost"
      shift
      ;;
    --)
      shift
      extra_args+=("$@")
      break
      ;;
    *)
      extra_args+=("$1")
      shift
      ;;
  esac
done

cd "${repo_root}"

function stop_app {
  if [[ "${app_started}" == "true" ]]; then
    mvn spring-boot:stop || true
  fi
}

trap stop_app EXIT

echo "Starting Spring Boot application..."
mvn spring-boot:start
app_started=true
echo "Spring Boot started; running Cucumber tests."

if [[ -n "${base_url}" ]]; then
  mvn clean test "-Dapi.baseUrl=${base_url}" "${extra_args[@]}"
else
  mvn clean test "${extra_args[@]}"
fi
