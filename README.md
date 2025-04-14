# mtms

## Traccia
Si vuole implementare un sistema Client-Server, con Server concorrente, che simuli un servizio postale, dove i clienti possono effettuare queste operazioni:
1. Invio pacco: il cliente può inviare un pacco, specificando il destinatario, l’indirizzo e il peso.
2. Traccia spedizione: il cliente, inserendo un numero di tracciamento, può controllare lo stato della spedizione.
3. Calcolo tariffa: il cliente può inserire il peso e le dimensioni del pacco per ottenere una stima della tariffa di spedizione.
4. Visualizza lo storico delle spedizioni: il cliente può visualizzare un elenco delle spedizioni effettuate in precedenza.

Il server deve essere in grado di gestire più richieste contemporaneamente. La concorrenza sarà implementata con il Threading. 
Ogni volta che un client si connette, il server crea un nuovo thread per gestire quella connessione.
