CREATE DATABASE paymybuddy_test;



USE paymybuddy_test;




CREATE TABLE user (
                user_id INT AUTO_INCREMENT NOT NULL,
                first_name VARCHAR(50) NOT NULL,
                last_name VARCHAR(50) NOT NULL,
                email VARCHAR(50) NOT NULL,
                password VARCHAR(100) NOT NULL,
                phone INT,
                address VARCHAR(50) NOT NULL,
                zip INT NOT NULL,
                city VARCHAR(100) NOT NULL,
                user_role VARCHAR(50) DEFAULT 'ROLE_USER' NOT NULL,
                deleted BIT(1) DEFAULT FALSE NOT NULL,
                PRIMARY KEY (user_id)
);


CREATE UNIQUE INDEX user_idx
 ON user
 ( email );

CREATE TABLE contact (
                id INT AUTO_INCREMENT NOT NULL,
                contact_user_id INT NOT NULL,
                user_id INT NOT NULL,
                PRIMARY KEY (id)
);


CREATE TABLE bank_account (
                iban VARCHAR(27) NOT NULL,
                bank_establishment VARCHAR(50) NOT NULL,
                bic VARCHAR(11) NOT NULL,
                balance DOUBLE PRECISION NOT NULL,
                deleted BIT(1) DEFAULT FALSE NOT NULL,
                PRIMARY KEY (iban)
);


CREATE TABLE transaction (
                reference INT AUTO_INCREMENT NOT NULL,
                amount DOUBLE PRECISION NOT NULL,
                message VARCHAR(200) NOT NULL,
                date DATETIME NOT NULL,
                creditor VARCHAR(27) NOT NULL,
                debtor VARCHAR(27) NOT NULL,
                PRIMARY KEY (reference)
);


CREATE TABLE fee (
                fee_id INT AUTO_INCREMENT NOT NULL,
                iban_account VARCHAR(27) NOT NULL,
                reference_transaction INT NOT NULL,
                date DATETIME NOT NULL,
                amount DOUBLE PRECISION NOT NULL,
                rate100 DOUBLE PRECISION NOT NULL,
                PRIMARY KEY (fee_id, iban_account, reference_transaction)
);


ALTER TABLE bank_account ADD CONSTRAINT user_bank_account_fk
FOREIGN KEY (user_id)
REFERENCES user (user_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE contact ADD CONSTRAINT user_contact_fk
FOREIGN KEY (user_id)
REFERENCES user (user_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE contact ADD CONSTRAINT user_contact_fk1
FOREIGN KEY (contact_user_id)
REFERENCES user (user_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE transaction ADD CONSTRAINT bank_account_transaction_fk
FOREIGN KEY (creditor)
REFERENCES bank_account (iban)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE transaction ADD CONSTRAINT bank_account_transaction_fk1
FOREIGN KEY (debtor)
REFERENCES bank_account (iban)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE fee ADD CONSTRAINT bank_account_fee_fk
FOREIGN KEY (iban_account)
REFERENCES bank_account (iban)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE fee ADD CONSTRAINT transaction_fee_fk
FOREIGN KEY (reference_transaction)
REFERENCES transaction (reference)
ON DELETE NO ACTION
ON UPDATE NO ACTION;