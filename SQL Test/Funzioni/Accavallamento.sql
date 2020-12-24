--FUNCTION PER ACCAVALLAMENTO
--Funzione che calcola l'eventuale accavallamento di due eventi definiti a partire dalle loro
--date di inizio/fine e i loro orari di inizio/fine.
--Resistituisce un boolean: true=accavallamento, false=no accavallamento.
-----------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION accavallamento(DataInizioOLD DATE, DataFineOLD DATE, DataInizioNEW DATE, DataFineNEW DATE, OraInizioOLD TIME, OraFineOLD TIME, OraInizioNEW TIME, OraFineNEW TIME )
RETURNS BOOLEAN
LANGUAGE PLPGSQL
AS $$
DECLARE
InizioNEW TIMESTAMP;
InizioOLD TIMESTAMP;
FineNEW TIMESTAMP;
FineOLD TIMESTAMP;
BEGIN
InizioNEW := DataInizioNEW + OraInizioNEW;
InizioOLD := DataInizioOLD + OraInizioOLD;
FineNEW := DataFineNEW + OraFineNEW;
FineOLD := DataFineOLD + OraFineOLD;
--Controlla accavallamento
IF ((InizioNEW,FineNEW) OVERLAPS (InizioOLD,FineOLD)) THEN
	RETURN TRUE;	--accavallamento avvenuto
END IF;
RETURN FALSE;	--accavallamento non avvenuto
END;
$$;