# Battle Royale
Un gioco di carte multiplayer fatto in Java, ispirato da Hearthstone e Clash Royale.

# Lista delle classe
## Giocatore.java
La persona che sta giocando.
- Nome utente
- Carte sbloccate []
- Deck []
- Soldi

## Carta.java
Una carta che può essere usata durante la partita per attaccare le carte dell'avversario.
- Costo mana
- Nome carta
- Effetto
- HP (anche se non siamo sponsorizzati dalla HP...)
- Attacco
- TipoCarta (Magia / Personaggio)
- Rarita (comune, rara, epica, leggendaria)
- Classe carta
- Abilita

## Collezione carte.java
Classe dove si trovano tutte le carte del gioco.
- Carta []

## Partita.java
Classe dove viene gestita la partita in corso.


## Effetti/Trigger
- Evoca una personaggio dal mazzo          *Grido
- Silenzia tutti i personaggi                  *Grido
- Silenzia un servitore a scelta                 *Grido
- Se è presete un servitore, allora aumenta delle caratteristiche               *Grido
- Prendi il controllo di un servitore nemico casuale                      *Grido
- Quando peschi una carta, aggiungine una carta nel tuo mazzo                *trigger
- Diminuisci le statistiche di ogni personaggio di -1/-1                      *Grido
- Quando peschi hai il 50% di possibilità di pescare una carta aggiuntiva        *trigger
- Distruggi tutti i personaggi che appartengono ad una deterimata classse           *Grido
- Imposta l'attacco e la salute di tutti i personaggi a 2/2                     *Grido
- Quando giochi questa carta distrugge un determinato personaggio             *trigger
- Evoca 2 personaggi generici                                        *Grido
- Fornisce +2/+2 o (+1/+1) a una determinata classe                          *Grido
- Quando giochi questa carta hai il 50% di possibilità di scartare una carta casuale dalla tua mano                    *Grido
- Ti da una 1 punto mana solo per questo turno                               *Grido 
- Trasforma un personaggio nemico in un personale ATA 1/1                      *Grido
- Se controlli determinati persoanggi aumenta i loro parametri             
- Scegli un bersaglio, tutti i personaggi sul terreno copiano la sua salute e il suo attacco

## Idee
- Le carte rappresenteranno gli alunni della classe 5C, ma anche delle altre personne speciale, come prof
- Per sbloccare delle nuove carte, bisogna aprire dei chest, che si comprano con i soldi
- Carta edificio che ti aggiunge un mana in piu tipo estrattore elisiro (cos ?)
