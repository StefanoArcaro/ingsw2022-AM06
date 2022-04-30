package it.polimi.ingsw.controller;

//gestisce messaggi in uscita client->server (onUpdate...)
// CLIENT [Cli -> clientController -> socketClient] -> SERVER [socketClientHandler -> socketServer -> server -> gameController]

//gestisce messaggi in entrata server->client (@update):
// SERVER [server -> socketServer -> socketClientHandler] -> CLIENT [socketClient -> clientController -> view (Cli?)]

//determina l'azione da fare in base al messaggio ricevuto dal server


import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.observer.Observer;

public class ClientController implements Observer {









    @Override
    public void update(Message message) {

    }
}
