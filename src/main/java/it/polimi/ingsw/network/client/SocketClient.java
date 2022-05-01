package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.message.clientToserver.CharacterMessage;
import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.clientToserver.PingMessage;
import it.polimi.ingsw.network.message.serverToclient.*;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClient extends Client {

    private static final int PING_INITIAL_DELAY = 0;
    private static final int PING_PERIOD = 15000;

    private final Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private final ExecutorService readQueue;
    private final ScheduledExecutorService pinger;

    public SocketClient(Socket socket) {
        this.socket = socket;

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("A problem with I/O streams instantiation occurred.");
            disconnect();
        }

        this.readQueue = Executors.newSingleThreadExecutor();
        this.pinger = Executors.newSingleThreadScheduledExecutor();
    }

    // From server
    @Override
    public void readMessage() {
        readQueue.execute(() -> {
            while(!readQueue.isShutdown()) {
                try {
                    String message = reader.readLine();
                    handleAnswer(message);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    // To server
    @Override
    public void sendMessage(Message message) {
        try {
            Gson gson = new Gson();
            String msg = gson.toJson(message);

            writer.write(msg);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            disconnect();
        }
    }

    // From server
    public void handleAnswer(String message) {
        Gson gson = new Gson();

        Answer answer = gson.fromJson(message, Answer.class);

        switch(answer.getMessageType()) {
            case LOGIN_REPLY_MESSAGE -> {
                LoginReplyMessage msg = gson.fromJson(message, LoginReplyMessage.class);
                System.out.println(msg.getMessage());
            }
            case WIZARDS_AVAILABLE_MESSAGE -> {
                WizardsAvailableMessage msg = gson.fromJson(message, WizardsAvailableMessage.class);
                //todo
            }
            case ASSISTANTS_MESSAGE -> {
                AssistantsMessage msg = gson.fromJson(message, AssistantsMessage.class);
                //todo
            }
            case MATCH_INFO_MESSAGE -> {
                MatchInfoMessage msg = gson.fromJson(message, MatchInfoMessage.class);
                //todo
            }
            case COLORS_AVAILABLE_MESSAGE -> {
                ColorsAvailableMessage msg = gson.fromJson(message, ColorsAvailableMessage.class);
                //todo
            }
            case BOARD_MESSAGE -> {
                BoardMessage msg = gson.fromJson(message, BoardMessage.class);
                //todo
            }
            case ISLAND_GROUPS_MESSAGE -> {
                IslandGroupsMessage msg = gson.fromJson(message, IslandGroupsMessage.class);
                //todo
            }
            case ISLAND_MESSAGE -> {
                IslandMessage msg = gson.fromJson(message, IslandMessage.class);
                //todo
            }
            case CLOUDS_MESSAGE -> {
                CloudsMessage msg = gson.fromJson(message, CloudsMessage.class);
                //todo
            }
            case COIN_MESSAGE -> {
                CoinMessage msg = gson.fromJson(message, CoinMessage.class);
                //todo
            }
            case CURRENT_PLAYER_MESSAGE -> {
                CurrentPlayerMessage msg = gson.fromJson(message, CurrentPlayerMessage.class);
                //todo
            }
            case CURRENT_PHASE_MESSAGE -> {
                CurrentPhaseMessage msg = gson.fromJson(message, CurrentPhaseMessage.class);
                //todo
            }
            case CHARACTERS_DRAWN_MESSAGE -> {
                CharactersDrawnMessage msg = gson.fromJson(message, CharactersDrawnMessage.class);
                //todo
            }
            case CHARACTER_INFO_MESSAGE -> {
                CharacterInfoMessage msg = gson.fromJson(message, CharacterInfoMessage.class);
                //todo
            }
            case CHARACTER_PLAYED_MESSAGE -> {
                CharacterMessage msg = gson.fromJson(message, CharacterMessage.class);
                //todo
            }
            case WINNER_MESSAGE -> {
                WinnerMessage msg = gson.fromJson(message, WinnerMessage.class);
                //todo
            }

            case GENERIC_MESSAGE -> {
                GenericMessage msg = gson.fromJson(message, GenericMessage.class);
                System.out.println(msg.getMessage());
            }
            case ERROR_MESSAGE -> {
                ErrorMessage msg = gson.fromJson(message, ErrorMessage.class);
                System.out.println(msg.getError());
            }
            case DISCONNECTION_MESSAGE -> disconnect();
            case PONG_MESSAGE -> {}

            default -> System.out.println(answer.getMessageType());
        }
    }

    @Override
    public void disconnect() {
        closeEverything();
    }

    @Override
    public void enablePinger(boolean enabled) {
        if(enabled) {
            pinger.scheduleAtFixedRate(() -> sendMessage(new PingMessage()), PING_INITIAL_DELAY, PING_PERIOD, TimeUnit.MILLISECONDS);
        } else {
            pinger.shutdownNow();
        }
    }

    private void closeEverything() {
        try {
            enablePinger(false);
            readQueue.shutdownNow();

            if(reader != null) {
                reader.close();
            }
            if(writer != null) {
                writer.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            System.exit(0);
        }
    }
}
