
# --- !Ups
DROP TABLE IF EXISTS tbl_bank_account;

CREATE TABLE IF NOT EXISTS tbl_bank_account (
    no varchar(11) NOT NULL,
    open_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    close_date timestamp DEFAULT NULL,
    balance numeric(10,2) NOT NULL DEFAULT 0,
    PRIMARY KEY (no),
    UNIQUE (no)
);

/* Valid records */
INSERT INTO tbl_bank_account ( no, open_date, balance ) VALUES( '00000000001', CURRENT_TIMESTAMP, 100000.50 );
INSERT INTO tbl_bank_account ( no, open_date, balance ) VALUES( '00000000002', CURRENT_TIMESTAMP, 2650000.00 );
INSERT INTO tbl_bank_account ( no, open_date, balance ) VALUES( '00000000003', CURRENT_TIMESTAMP, 500.11 );
INSERT INTO tbl_bank_account ( no, open_date, balance ) VALUES( '00000000004', CURRENT_TIMESTAMP, 8000000.00 );
INSERT INTO tbl_bank_account ( no, open_date, balance ) VALUES( '00000000005', CURRENT_TIMESTAMP, 7630000.01 );
INSERT INTO tbl_bank_account ( no, open_date, balance ) VALUES( '00000000006', CURRENT_TIMESTAMP, 21000000.99 );

# --- !Downs