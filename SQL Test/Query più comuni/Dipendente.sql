--TUTTI I DIPENDENTI
SELECT *
FROM Dipendente;
--ORDER BY Valutazione/Salario/DataNascita
-------------------------------------------

--DIPENDENTI BY NOME/COGNOME/SESSO/ETA'
SELECT *
FROM Dipendente
WHERE Dipendente.Nome = 'Andrea';
-------------------------------------
SELECT *
FROM Dipendente
WHERE Dipendente.Cognome = 'Lemmo';
------------------------------------
SELECT *
FROM Dipendente
WHERE Dipednente.Sesso = 'M';
-----------------------------------
SELECT *
FROM Dipendente
WHERE EXTRACT (YEAR FROM AGE(Dipendente.DataNascita)) = 25 --Età cercata
ORDER BY Dipendente.DataNascita;
---------------------------------------------------------------------------------------------------------------------------------------------------------------

--PROGETTI CREATI DA UN DIPENDENTE (DI CUI E' PROJECT MANAGER)
SELECT p.NomeProgetto
FROM Progetto AS p JOIN Partecipazione AS par ON (par.Progetto = p.CodProgetto)
WHERE par.RuoloDip = 'Project Manager' AND par.Dipendente IN (
	SELECT Dipendente.CF
	FROM Dipendente
	WHERE Dipendente.Nome = 'Andrea' AND Dipendente.Cognome = 'Lemmo'
);
--------------------------------------------------------------------------------

--PROGETTI IN CUI I DIPENDENTI DI NOME E COGNOME X Y PARTECIPANO
SELECT p.NomeProgetto, 'Andrea Lemmo' AS Dipendente, par.RuoloDip
FROM Progetto AS p JOIN Partecipazione AS par ON (par.Progetto = p.CodProgetto)
WHERE par.RuoloDip <> 'Project Manager' AND par.Dipendente IN( --Senza la condizione "ruolo diverso da Project Manager" se si vogliono includere quelli creati
	SELECT Dipendente.CF
	FROM Dipendente
	WHERE Dipendente.Nome = 'Andrea' AND Dipendente.Cognome = 'Lemmo'
);
---------------------------------------------------------------------------------------------------------------------------------------------------------------

--DIPENDENTI BY LUOGO DI NASCITA
SELECT d.CF, d.Nome, d.Cognome, l.NomeComune, l.NomeProvincia
FROM Dipendente AS d JOIN LuogoNascita AS l ON (d.LuogoNascita = l.CodComune)
WHERE l.NomeProvincia = 'Napoli'; --Oppure l.NomeComune = X
------------------------------------------------------------------------------

--DIPENDENTI BY SKILL
SELECT d.Nome, d.Cognome
FROM Dipendente AS d JOIN Abilità AS a ON (a.Dipendente = d.CF)
WHERE a.Skill IN (
	SELECT Skill.IDSkill
	FROM Skill
	WHERE NomeSkill = 'Programmazione Java'
);
-----------------------------------------------------------------

--SKILL DI UN DIPENDENTE
SELECT s.NomeSKill
FROM Skill AS s JOIN Abilità AS a ON (a.Skill = s.IDSkill)
WHERE a.Dipendente IN(
	SELECT d.CF
	FROM Dipendente AS d
	WHERE d.Nome = 'Andrea' AND d.Cognome = 'Lemmo'
);
-----------------------------------------------------------

--DIPENDENTI CON VALUTAZIONE/SALARIO SUPERIORE A X
SELECT *
FROM Dipendente
WHERE Dipendente.Valutazione >= 8;
--------------------------------------------------
SELECT *
FROM Dipendente
WHERE Dipendente.Salario >= 100;
--------------------------------------------------

--MEETING ORGANIZZATI DA UN DIPENDENTE
SELECT *
FROM Dipendente AS d JOIN Meeting AS m ON (m.Organizzatore = d.CF)
WHERE d.Nome = 'Andrea'AND d.Cognome = 'Lemmo';
-------------------------------------------------------------------

--MEETING A CUI E' INVITATO UN DIPENDENTE (DI CUI NON E' ORGANIZZATORE)
SELECT *
FROM (Dipendente AS d JOIN Presenza AS p ON (p.Dipendente = d.CF)) JOIN Meeting AS m ON (p.Meeting = m.IDMeeting)
WHERE d.Nome = 'Andrea' AND d.Cognome = 'Lemmo' AND m.Organizzatore NOT IN(
	SELECT d.CF
	FROM Dipendente AS d
	WHERE d.Nome = 'Andrea' AND d.Cognome = 'Lemmo'
);
------------------------------------------------------------------------------------------------------------------

--RUOLI ASSUNTI DA UN DIPENDENTE IN TUTTI I SUOI PROGETTI
SELECT par.RuoloDip
FROM Partecipazione AS par JOIN Dipendente AS d ON (d.CF = par.Dipendente)
WHERE d.CF IN(
	SELECT d.CF
	FROM Dipendente AS d
	WHERE d.Nome = 'Andrea' AND d.Cognome = 'Lemmo'
);
--------------------------------------------------------------------------