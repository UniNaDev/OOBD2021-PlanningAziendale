# OOBD2021-PlanningAziendale

## Traccia: Sistema di planning per gestione progetti
Si sviluppi un sistema informativo, composto da una base di dati relazionale e un applicativo Java dotato di GUI (Swing o JavaFX), per la gestione di progetti in un’azienda. Si tenga traccia dei partecipanti al progetto, identificando i ruoli per ognuno di essi (per ogni progetto ci sarà solo un project manager). Ad ogni progetto è associato una tipologia (“Ricerca di base”, “Ricerca Industriale”, “Ricerca sperimentale”, “Sviluppo Sperimentale”, ...) ed uno o più ambiti (Economia, Medicina, …). Il sistema dovrà permettere anche l'organizzazione di meeting fisicamente, in sale riunioni, o telematicamente su una piattaforma di videoconferenza. Si dovrà tenere traccia delle partecipazioni ai progetti ed ai meeting, ai fini della valutazione del singolo partecipante. In fase di creazione di un nuovo progetto, i partecipanti dovranno essere selezionati in base a criteri di ricerca che includono anche il salario medio e la valutazione aziendale del partecipante, oltre alla tipologia di progetti cui ha preso parte. Per il gruppo da 3: ad ogni partecipante sarà associata una lista di skill. Inoltre, in fase di creazione di un nuovo progetto, i partecipanti potranno essere scelti in funzione anche delle loro skill. In fase di registrazione di un partecipante, inserire le skill e se non presente nel DB, crearne una nuova.

## Funzionalità
###### Dipendente
[] Sistema di credenziali con accesso e creazione account
  [] Generazione del codice fiscale
[] Visualizzare e modificare le informazioni del proprio account
[] Vedere e modificare le proprie skill lavorative

###### Progetto
[] Creare un progetto
  [] Assegnare dipendenti e ruoli
    [] Filtrare dipendenti per skill, stipendio e numero di progetti già in corso
[] Visualizzare i propri progetti
  [] Filtrare la lista per ambiti, tipologia, scadenza e terminazione
[] Modificare un progetto se se ne è il creatore/project manager
  [] Terminare un progetto

###### Meeting
[] Organizzare un meeting
  [] Controllo delle prenotazioni delle sale
[] Modificare meeting se se ne è gli organizzatori
  [] Assegnare presenze degli invitati
    [] Aggiornare valutazione dei dipendenti
[] Visualizzare tutti i meeting a cui si è invitati
  [] Filtrare la lista per date di inizio e per progetti discussi
