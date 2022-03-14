#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER usr_vac WITH PASSWORD 'Uv@cc1n@t10n..';
    CREATE DATABASE vaccination;
    GRANT ALL PRIVILEGES ON DATABASE vaccination TO usr_vac;
EOSQL