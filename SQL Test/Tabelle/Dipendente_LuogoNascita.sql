--TABELLA LUOGONASCITA
-------------------------------------------
CREATE TABLE LuogoNascita(
	CodComune CHAR(4) PRIMARY KEY,
	NomeComune VARCHAR(50) NOT NULL,
	NomeProvincia VARCHAR(50) NOT NULL
);
-------------------------------------------

--TABELLA DIPENDENTE
---------------------------------------------------------------------------------------
CREATE TABLE Dipendente(
	CF CHAR(16) PRIMARY KEY,
	Nome VARCHAR(30) NOT NULL,
	Cognome VARCHAR(50) NOT NULL,
	Sesso CHAR(1) NOT NULL,
	DataNascita DATE NOT NULL,
	Indirizzo VARCHAR(100) NOT NULL,
	Email VARCHAR(50) UNIQUE,
	TelefonoCasa CHAR(10) DEFAULT NULL,
	Cellulare CHAR(10) DEFAULT NULL,
	Salario FLOAT NOT NULL DEFAULT 0,
	Valutazione FLOAT NOT NULL DEFAULT 0,
	Password VARCHAR(50) NOT NULL,
	LuogoNascita CHAR(4) NOT NULL,
	CONSTRAINT cf_valido CHECK (CF ~ '^([A-Za-z]{6}[0-9lmnpqrstuvLMNPQRSTUV]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9lmnpqrstuvLMNPQRSTUV]{2}[A-Za-z]{1}[0-9lmnpqrstuvLMNPQRSTUV]{3}[A-Za-z]{1})|([0-9]{11})$'),
	CONSTRAINT sesso_valido CHECK (Sesso='F' OR Sesso='M'),
	CONSTRAINT email_valida CHECK (Email ~ '^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$'),
	CONSTRAINT salario_positivo CHECK (Salario>=0),
	CONSTRAINT valutazione_positiva CHECK (valutazione>=0 AND Valutazione<=10),
	CONSTRAINT fk_luogonascita FOREIGN KEY (LuogoNascita) REFERENCES LuogoNascita(CodComune)
);
-----------------------------------------------------------------------------------------