package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.menu.Menu;
import com.focamacho.seallibrary.player.ISealPlayer;
import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

public class ImpInterfaces {

    /**
     * Interface usada para a obtenção
     * de um Menu.
     */
    public interface MenuBuilder {
        Menu create();
    }

    /**
     * Interface usada para a obtenção
     * de um SealPlayer
     */
    public interface ISealPlayerGetter {
        ISealPlayer get(Object player);
        Optional<ISealPlayer> get(UUID uuid);
    }

    /**
     * Interface usada para a obtenção
     * de um SealStack.
     */
    public interface IStackBuilder {
        ISealStack get(Object item);
        ISealStack get(String item);

        static ISealStack fromJson(String item) {
            JSONObject object = new JSONObject(item);
            ISealStack stack = SealStack.get(object.getString("item"));

            String data = object.getString("data").replace("\\\"", "\"");
            if(!data.isEmpty() && !data.equalsIgnoreCase("{}")) {
                stack.setData(data);
            }

            return stack.setAmount(object.getInt("amount"));
        }
    }

}
