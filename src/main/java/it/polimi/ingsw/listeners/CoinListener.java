package it.polimi.ingsw.listeners;

import it.polimi.ingsw.network.message.serverToclient.CoinMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.Map;

public class CoinListener extends Listener {

    public CoinListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Map.Entry<String, Integer> nicknameCoins = (Map.Entry<String, Integer>) evt.getNewValue();

        String nickname = nicknameCoins.getKey();
        int coins = nicknameCoins.getValue();

        CoinMessage message = new CoinMessage(nickname, coins);
        virtualView.sendAll(message);
    }
}
