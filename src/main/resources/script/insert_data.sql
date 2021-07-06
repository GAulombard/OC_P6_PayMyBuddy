INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city,user_role)
    VALUES ('Geoffrey','Aulombard','g.aulomb@jetmail.fr','$2y$10$d1CWMBxRNJcbznet9mzO8.dJuBRUMJmQYctlswcorLNDlw/CGlSCy',0674558612,'2 rue de ville',45000,'Orlyans','ROLE_ADMIN');
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUES ('Jean','Bombeurre','j.bombeurre@jetmail.fr','$2y$10$d1CWMBxRNJcbznet9mzO8.dJuBRUMJmQYctlswcorLNDlw/CGlSCy',0679458612,'4 rue des snacks',75000,'Poris');
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUES ('Laura','Jamais','laura.jamais@jetmail.fr','$2y$10$d1CWMBxRNJcbznet9mzO8.dJuBRUMJmQYctlswcorLNDlw/CGlSCy',0679458612,'12 rue de l\'espoire',23000,'Limouge');
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUES ('Mark','Nofler','m.nofler@jetmail.fr','$2y$10$d1CWMBxRNJcbznet9mzO8.dJuBRUMJmQYctlswcorLNDlw/CGlSCy',0679458612,'8 rue des musicos',75000,'Poris');
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUES ('Long','Tram','tram.long@takatoukite.fr','$2y$10$d1CWMBxRNJcbznet9mzO8.dJuBRUMJmQYctlswcorLNDlw/CGlSCy',0679458612,'16 bis avenue du soleil levant',75000,'Poris');
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUES ('Zinezine','Zizane','z.z@jetmail.fr','$2y$10$d1CWMBxRNJcbznet9mzO8.dJuBRUMJmQYctlswcorLNDlw/CGlSCy',0679458612,'15 rue de la balle',13000,'Marsèye');
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUES ('Ana','Pu','ana.pu@yahoo.fr','$2y$10$d1CWMBxRNJcbznet9mzO8.dJuBRUMJmQYctlswcorLNDlw/CGlSCy',0679458612,'126 avenue de rien',75000,'Poris');

INSERT INTO bank_account (iban,bank_establishment,bic,balance,user_id)
    VALUES ('FR123456789123456B789123456','POSTAL_BANK','AABBPPDLERD',4200,1);
INSERT INTO bank_account (iban,bank_establishment,bic,balance,user_id)
    VALUES ('FR12345421V123456B789123456','POSTAL_BANK','APPBPPDLERD',400,2);
INSERT INTO bank_account (iban,bank_establishment,bic,balance,user_id)
    VALUES ('FR1234B6789127856B789123456','POSTAL_BANK','AWXBPPDLERD',7000,5);
INSERT INTO bank_account (iban,bank_establishment,bic,balance,user_id)
    VALUES ('FR12345848912A456B789123456','POSTAL_BANK','AAGHJPDLERD',0,6);

INSERT INTO contact (user_id,contact_user_id)
    VALUES (1,5);

INSERT INTO transaction (amount,message,date,creditor,debtor)
    VALUES (200,'Tiens, voilà des cacahuètes !!','2021-07-01 11:00:00','FR123456789123456B789123456','FR1234B6789127856B789123456');

INSERT INTO fee(iban_account,reference_transaction,date,amount,rate100)
    VALUES ('FR1234B6789127856B789123456',1,'2021-07-01 11:00:00',4,2);