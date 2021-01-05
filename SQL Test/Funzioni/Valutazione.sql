/*FUNCTION PER VALUTAZIONE DIPENDENTE
**La funzione calcola la valutazione del dipendente in decimi
**basandosi sulla sua partecipazione ai progetti e al suo numero
**di presenze ai meeting.
*****************************************************************/

--FUNCTION PER VALUTAZIONE MEETING
CREATE OR REPLACE FUNCTION ValutazioneMeeting(dip Dipendente.CF%TYPE) RETURNS FLOAT
LANGUAGE PLPGSQL
AS $$
DECLARE
ValutazioneMeeting FLOAT;
TotaleMeeting FLOAT;
Presenze FLOAT;
BEGIN
--Conta tutti i meeting a cui doveva partecipare
SELECT COUNT(*) INTO TotaleMeeting
FROM Presenza AS p
WHERE p.CF = dip AND p.IDMeeting IN(
	SELECT m.IDMeeting
	FROM Meeting AS m
	WHERE m.DataFine<CURRENT_DATE OR (m.DataFine=CURRENT_DATE AND m.OrarioFine<CURRENT_TIME));
--Se non ci sono meeting a cui doveva presenziare di default la sua valutazione a riguardo è 0
IF (TotaleMeeting = 0) THEN
	RETURN 0;
END IF;
--Conta le sue presenze per ciascuno di essi
SELECT COUNT(*) INTO Presenze
FROM Presenza AS p
WHERE p.CF=dip AND p.Presente=TRUE AND p.IDMeeting IN(
	SELECT m.IDMeeting
	FROM Meeting AS m
	WHERE m.DataFine<CURRENT_DATE OR (m.DataFine=CURRENT_DATE AND m.OrarioFine<CURRENT_TIME));
--Fai il rapporto e restituisci il valore in decimi
ValutazioneMeeting := (Presenze*10)/TotaleMeeting;
RETURN ValutazioneMeeting;
END;
$$;
----------------------------------------------------------------------------------------

--FUNCTION PER PROGETTI
CREATE OR REPLACE FUNCTION ValutazioneProgetti(dip Dipendente.CF%TYPE) RETURNS FLOAT
LANGUAGE PLPGSQL
AS $$
DECLARE
TotaleProgetti FLOAT;
Partecipazioni FLOAT;
ValutazioneProgetti FLOAT;
BEGIN
--Calcola il numero totale di progetti dell'azienda
SELECT COUNT(*) INTO TotaleProgetti
FROM Progetto;
--Calcola il numero di progetti a cui il dipendente partecipa
SELECT COUNT(*) INTO Partecipazioni
FROM Partecipazione AS p
WHERE p.CF = dip;
--Fai il rapporto e restituisci il risultato
ValutazioneProgetti := (Partecipazioni*10)/TotaleProgetti;
RETURN ValutazioneProgetti;
END;
$$;
-----------------------------------------------------------------------------------------

--FUNCTION PRINCIPALE
CREATE OR REPLACE FUNCTION Valutazione(dip Dipendente.CF%TYPE) RETURNS FLOAT
LANGUAGE PLPGSQL
AS $$
DECLARE
ValMeeting FLOAT;
ValProgetti FLOAT;
ValutazioneFinale FLOAT;
BEGIN
--Calcola valutazione meeting
ValMeeting := ValutazioneMeeting(dip);
--Calcola valutazione progetti
ValProgetti := ValutazioneProgetti(dip);
--Fai la media e restituisci il risultato in decimi
ValutazioneFinale := (ValMeeting+ValProgetti)/2;
RETURN ValutazioneFinale;
END;
$$;
--------------------------------------------------------------------------------------------------------------------------