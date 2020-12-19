--TUTTI I MEETING
SELECT *
FROM Meeting
ORDER BY Meeting.DataInizio, Meeting.OrarioInizio;
--------------------------------------------------

--MEETING TELEMATICI/FISICI
SELECT *
FROM Meeting
WHERE Meeting.Modalità = 'Telematico'
ORDER BY Meeting.DataInizio, Meeting.OrarioInizio;
---------------------------------------------------
SELECT *
FROM Meeting
WHERE Meeting.Modalità = 'Fisico'
ORDER BY Meeting.DataInizio, Meeting.OrarioInizio;
---------------------------------------------------

--SALA DI UN MEETING FISICO
SELECT sala.CodSala
FROM (SalaRiunione AS sala JOIN Riunione AS r ON (r.Sala = sala.CodSala)) JOIN Meeting AS m ON (m.IDMeeting = r.Meeting)
WHERE m.IDMeeting = 1;
------------------------------------------------------------------------------------------------------------------------

--MEETING BY INIZIO/FINE
SELECT *
FROM Meeting
WHERE Meeting.DataInizio = CURRENT_DATE
ORDER BY Meeting.OrarioInizio;
----------------------------------------
SELECT *
FROM Meeting
WHERE Meeting.DataFine = CURRENT_DATE
ORDER BY Meeting.DataFine;
---------------------------------------

--MEETING NEI PROSSIMI 7 GIORNI
SELECT *
FROM Meeting
WHERE Meeting.DataInizio BETWEEN CURRENT_DATE AND CURRENT_DATE+7;
------------------------------------------------------------------------------

--SALA CON CAPIENZA SUPERIORE A X
SELECT *
FROM SalaRiunione
WHERE SalaRiunione.Capienza >= 100;
------------------------------------

--TUTTI I MEETING DI UN PROGETTO
SELECT *
FROM Meeting AS m JOIN Discussione AS d ON (d.Meeting = m.IDMeeting)
WHERE  d.Progetto IN (
	SELECT p.CodProgetto
	FROM Progetto AS p
	WHERE p.NomeProgetto = 'Test Normale'
)
ORDER BY m.DataInizio, m.OrarioInizio;
---------------------------------------------------------------------

--PROGETTI CON UN MEETING OGGI/IN UNA DATA SPECIFICA
SELECT p.NomeProgetto
FROM (Progetto AS p JOIN Discussione AS d ON (p.CodProgetto = d.Progetto)) JOIN Meeting AS m ON (m.IDMeeting = d.Meeting)
WHERE m.DataInizio = CURRENT_DATE;
-------------------------------------------------------------------------------------------------------------------------