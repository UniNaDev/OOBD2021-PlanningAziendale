--INSERT IN DIPENDENTE
-------------------------------------------INSERT NORMALE----------------------------------
INSERT INTO Dipendente
VALUES ('LMMNDR95P16F839I','Andrea','Lemmo','M',DATE('16-09-1995'),'via Rodolfo Falvo,2 80127 NA','andrealemmo@gmail.com','0815603859','3407924680',120,0,'password','F839');

------------------------------------------INSERT CF SBAGLIATO-------------------------------
INSERT INTO Dipendente
VALUES ('lmmndr95916f839i','Andrea','Lemmo','M',DATE('16-09-1995'),'via Rodolfo Falvo,2 80127 NA','andrealemmo@gmail.com','0815603859','3407924680',120,0,'password','F839');

-----------------------------------------INSERT SESSO SBAGLIATO-----------------------------
INSERT INTO Dipendente
VALUES ('LMMNDR95P16F839I','Andrea','Lemmo','G',DATE('16-09-1995'),'via Rodolfo Falvo,2 80127 NA','andrealemmo@gmail.com','0815603859','3407924680',120,0,'password','F839');

----------------------------------------INSERT EMAIL SBAGLIATA-------------------------------
INSERT INTO Dipendente
VALUES ('LMMNDR95P16F839I','Andrea','Lemmo','M',DATE('16-09-1995'),'via Rodolfo Falvo,2 80127 NA','a.lemmo@@.com','0815603859','3407924680',120,0,'password','F839');

----------------------------------------INSERT SALARIO NEGATIVO---------------------------
INSERT INTO Dipendente
VALUES ('LMMNDR95P16F839I','Andrea','Lemmo','M',DATE('16-09-1995'),'via Rodolfo Falvo,2 80127 NA','andrealemmo@gmail.com','0815603859','3407924680',-120,0,'password','F839');

---------------------------------------INSERT VALUTAZIONE NEGATIVA---------------------------
INSERT INTO Dipendente
VALUES ('LMMNDR95P16F839I','Andrea','Lemmo','M',DATE('16-09-1995'),'via Rodolfo Falvo,2 80127 NA','andrealemmo@gmail.com','0815603859','3407924680',120,-5,'password','F839');