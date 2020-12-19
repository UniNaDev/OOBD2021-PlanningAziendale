--TUTTI I PROGETTI (ORDINATI PER SCADENZA/CREAZIONE/TERMINAZIONE)
SELECT *
FROM Progetto;
-------------------

--PROGETTO BY NOME
SELECT *
FROM Progetto
WHERE Progetto.NomeProgetto = 'Test Normale';
---------------------------------------------

--PROGETTI CHE SCADONO OGGI/IN UNA CERTA DATA
SELECT *
FROM Progetto
WHERE Progetto.Scadenza = CURRENT_DATE;
---------------------------------------------
SELECT *
FROM Progetto
WHERE Progetto.Scadenza = '16/09/1995';
----------------------------------------------

--PROGETTI TERMINATI/ANCORA IN CORSO
SELECT *
FROM Progetto
WHERE Progetto.DataTerminazione IS NOT NULL
ORDER BY Progetto.DataTerminazione;
----------------------------------------------
SELECT *
FROM Progetto
WHERE Progetto.DataTerminazione IS NULL;
--ORDER BY secondo le necessità
----------------------------------------------

--PROGETTI CON SCADENZA
SELECT *
FROM Progetto
WHERE Progetto.Scadenza IS NOT NULL
ORDER BY Progetto.Scadenza;
-------------------------------------

--PROGETTI DI UNO SPECIFICO TIPO
SELECT *
FROM Progetto
WHERE Progetto.TipoProgetto = 'Ricerca base'
--ORDER BY a seconda delle necessità
--------------------------------------------

--PROGETTI DI UN CERTO AMBITO
SELECT p.NomeProgetto, p.TipoProgetto, p.DescrizioneProgetto, p.DataCreazione, p.Scadenza, p.DataTerminazione
FROM (Progetto AS p JOIN AmbitoProgettoLink AS l ON (l.Progetto = p.CodProgetto)) JOIN AmbitoProgetto AS a ON (l.Ambito = a.IDAmbito)
WHERE a.NomeAmbito = 'Economia' OR a.NomeAmbito = 'Militare'; --OR/AND a seconda dei casi
--ORDER BY a seconda delle necessità
--------------------------------------------------------------------------------------------------------------------------------------

--AMBITI DI UN PROGETTO
SELECT a.NomeAmbito
FROM (AmbitoProgetto AS a JOIN AmbitoProgettoLink AS l ON (l.Ambito = a.IDAmbito))
WHERE l.Progetto IN(
	SELECT p.CodProgetto
	FROM Progetto AS p
	WHERE p.NomeProgetto = 'Test Normale'
	); --SELECT aggiuntiva se si vuole cercare il progetto per nome, altrimenti solo la prima con CodProgetto
--------------------------------------------------------------------------------------------------------------

--DIPENDENTI PARTECIPANTI AD UN PROGETTO
SELECT d.Nome, d.Cognome, par.RuoloDip
FROM Dipendente AS d JOIN Partecipazione AS par ON (par.Dipendente = d.CF)
WHERE par.Progetto IN(
	SELECT p.CodProgetto
	FROM Progetto AS p
	WHERE p.NomeProgetto = 'Test Normale'
);
---------------------------------------------------------------------------

--TUTTI I RUOLI ASSUNTI IN UN PROGETTO
SELECT par.RuoloDip
FROM Partecipazione AS par JOIN Progetto AS p ON (p.CodProgetto = par.Progetto)
WHERE p.NomeProgetto = 'Test Normale';
-------------------------------------------------------------------------------