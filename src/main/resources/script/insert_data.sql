INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city,is_admin)
            VALUE ("Geoffrey","Aulombard","admin@pmb.fr","123456789",0679458612,"2 rue de ville",45000,"Orlyens",TRUE);
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUE ("Jean","Bombeurre","j.bombeurre@jetmail.fr","123456789",0679458612,"4 rue des snacks",75000,"Poris");
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUE ("Laura","Jamais","laura.jamais@jetmail.fr","123456789",0679458612,"12 rue de l'espoire",23000,"Limouge");
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUE ("Mark","Nofler","m.nofler@jetmail.fr","123456789",0679458612,"8 rue des musicos",75000,"Poris");
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUE ("Long","Tram","tram.long@takatoukite.fr","123456789",0679458612,"16 bis avenue du soleil levant",75000,"Poris");
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUE ("Zinezine","Zizane","z.z@jetmail.fr","123456789",0679458612,"15 rue de la balle",13000,"Marsèye");
INSERT INTO user (first_name,last_name,email,password,phone,address,zip,city)
    VALUE ("Ana","Pu","ana.pu@yahoo.fr","123456789",0679458612,"126 avenue de rien",75000,"Poris");

INSERT INTO bank_account (iban,bank_establishment,bic,balance,user_id)
    VALUE ("FR123456789123456B789123456","POSTAL_BANK","AABBPPDLERD",4200,1);
INSERT INTO bank_account (iban,bank_establishment,bic,balance,user_id)
    VALUE ("FR12345421V123456B789123456","POSTAL_BANK","APPBPPDLERD",400,2);
INSERT INTO bank_account (iban,bank_establishment,bic,balance,user_id)
    VALUE ("FR1234B6789127856B789123456","POSTAL_BANK","AWXBPPDLERD",7000,5);
INSERT INTO bank_account (iban,bank_establishment,bic,balance,user_id)
    VALUE ("FR12345848912A456B789123456","POSTAL_BANK","AAGHJPDLERD",0,6);

INSERT INTO contact (user_id,contact_user_id)
    VALUE (1,5);

INSERT INTO transaction (amount,message,date,fee,creditor,debtor)
    VALUE (200,"Tiens, voilà des cacahuètes !!",'2021-07-01 11:00:00',2,"FR123456789123456B789123456","FR1234B6789127856B789123456");