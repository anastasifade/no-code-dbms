CREATE SCHEMA IF NOT EXISTS user_data;
CREATE SCHEMA IF NOT EXISTS metadata;

CREATE TABLE IF NOT EXISTS metadata.tables (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(63) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS metadata.columns (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(63) NOT NULL,
    type VARCHAR(64) NOT NULL,
    table_id BIGINT NOT NULL REFERENCES metadata.tables (id) ON DELETE CASCADE,
    CONSTRAINT uq_name_per_table UNIQUE (name, table_id)
);

CREATE TABLE IF NOT EXISTS metadata.column_constraints (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(32) NOT NULL,
    column_id BIGINT NOT NULL REFERENCES metadata.columns (id) ON DELETE CASCADE,
    default_value TEXT,
    length INT,
    precision INT,
    scale INT,
    CONSTRAINT chk_def_val_constr CHECK
        ((default_value IS NOT NULL AND type = 'DEFAULT' AND length IS NULL AND precision IS NULL AND scale IS NULL)
        OR (type <> 'DEFAULT')),
    CONSTRAINT chk_not_null_constr CHECK ((type = 'NOT NULL' AND default_value IS NULL AND length IS NULL AND
        precision IS NULL AND scale IS NULL) OR (type <> 'NOT NULL')),
    CONSTRAINT chk_length_restriction CHECK ((type = 'LENGTH' AND length IS NOT NULL AND length > 0 AND
        default_value IS NULL AND precision IS NULL AND scale IS NULL) OR (type <> 'LENGTH')),
    CONSTRAINT chk_precision_scale_restriction CHECK
        ((type = 'PRECISION AND SCALE' AND precision IS NOT NULL AND precision > 0 AND
        (scale > 0 OR scale IS NULL) AND length IS NULL AND default_value IS NULL) OR
        (type <> 'PRECISION AND SCALE')),
    CONSTRAINT correct_col_constr_type CHECK (type IN ('NOT NULL', 'DEFAULT', 'LENGTH', 'PRECISION AND SCALE')),
    CONSTRAINT uq_type_per_column UNIQUE (column_id, type)
);

CREATE TABLE IF NOT EXISTS metadata.table_constraints (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(63) UNIQUE,
    type VARCHAR(16) NOT NULL,
    table_id BIGINT NOT NULL REFERENCES metadata.tables (id) ON DELETE CASCADE,
    check_expression TEXT,
    ref_table BIGINT REFERENCES metadata.tables (id) ON DELETE CASCADE,
    CONSTRAINT chk_check_expr_constr CHECK
        ((check_expression IS NOT NULL AND type = 'CHECK' AND ref_table IS NULL) OR
        (type <> 'CHECK' AND check_expression IS NULL)),
    CONSTRAINT chk_fk_constr CHECK
        ((ref_table IS NOT NULL AND type = 'REFERENCES' AND check_expression IS NULL) OR
        (type <> 'REFERENCES' AND ref_table IS NULL)),
    CONSTRAINT correct_table_constr_type CHECK (type IN ('UNIQUE', 'PRIMARY KEY', 'CHECK', 'REFERENCES'))
);

-- represents referenced columns in foreign key constraints
CREATE TABLE IF NOT EXISTS metadata.table_constraints_ref_columns (
    table_constraint_id BIGINT REFERENCES metadata.table_constraints (id) ON DELETE CASCADE,
    ref_column_id BIGINT REFERENCES metadata.columns (id) ON DELETE CASCADE,
    CONSTRAINT table_constraints_ref_columns_pk PRIMARY KEY (table_constraint_id, ref_column_id)
);

-- represents columns to which a table-level constraint applies
CREATE TABLE IF NOT EXISTS metadata.table_constraint_columns (
    table_constraint_id BIGINT REFERENCES metadata.table_constraints (id) ON DELETE CASCADE,
    column_id BIGINT REFERENCES metadata.columns (id) ON DELETE CASCADE,
    CONSTRAINT table_constraints_columns_pk PRIMARY KEY (table_constraint_id, column_id)
);
