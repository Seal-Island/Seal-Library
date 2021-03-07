package com.focamacho.seallibrary.player;

import com.focamacho.seallibrary.chat.ChatHandler;
import com.focamacho.seallibrary.chat.lib.Runnable;
import com.focamacho.seallibrary.economy.EconomyHandler;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.menu.Menu;
import com.focamacho.seallibrary.permission.PermissionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *  Classe que representa um jogador
 *  no servidor.
 */
@SuppressWarnings("unused")
public interface ISealPlayer {

    /**
     * Retorna o jogador original
     * @return o jogador original.
     */
    Object toOriginal();

    /**
     * Retorna o UUID do jogador.
     * @return o UUID do jogador.
     */
    UUID getUUID();

    /**
     * Retorna o nome do jogador.
     * @return o nome do jogador.
     */
    String getName();

    /**
     * Envia uma mensagem de chat
     * para o jogador.
     * @param message a mensagem que será enviada.
     */
    void sendMessage(String message);

    /**
     * Envia mensagens de chat
     * para o jogador.
     * @param messages as mensagens que serão enviadas.
     */
    default void sendMessages(String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    /**
     * Desconecta o jogador do
     * servidor.
     * @param message a mensagem de desconexão.
     */
    void kick(String message);

    /**
     * Abre um inventário para
     * o jogador.
     * @param inventory o inventário para ser aberto.
     */
    void openInventory(Object inventory);

    /**
     * Abre um menu para
     * o jogador.
     * @param menu o menu para ser aberto.
     */
    default void openInventory(Menu menu) {
        openInventory(menu.get());
    }

    /**
     * Fecha o inventário que o
     * jogador tem aberto no momento.
     */
    void closeInventory();

    /**
     * Retorna o prefix do jogador definido
     * no plugin de permissões.
     * @return o prefix do jogador ou null
     * caso ele não possua um.
     */
    default String getPrefix() {
        return ChatHandler.getPrefix(getUUID());
    }

    /**
     * Retorna o suffix do jogador definido
     * no plugin de permissões.
     * @return o suffix do jogador ou null
     * caso ele não possua um.
     */
    default String getSuffix() {
        return ChatHandler.getSuffix(getUUID());
    }

    /**
     *  Consulta se o jogador possui uma
     *  permissão.
     *
     * @param permission a permissão para consulta
     */
    default boolean hasPermission(String permission) {
        return PermissionHandler.hasPermission(getUUID(), permission);
    }

    /**
     *  Consulta se o jogador está em um
     *  grupo.
     *
     * @param group o grupo para consulta
     */
    default boolean hasGroup(String group) {
        return PermissionHandler.hasGroup(getUUID(), group);
    }

    /**
     * Adiciona o jogador no grupo
     * especificado.
     *
     * @param group o grupo desejado
     * @return true se a ação foi efetuada com sucesso
     * e false caso algum erro tenha ocorrido.
     */
    default boolean addGroup(String group) {
        return PermissionHandler.addGroup(getUUID(), group);
    }

    /**
     * Remove o jogador do grupo
     * especificado.
     *
     * @param group o grupo desejado
     * @return true se a ação foi efetuada com sucesso
     * e false caso algum erro tenha ocorrido.
     */
    default boolean removeGroup(String group) {
        return PermissionHandler.removeGroup(getUUID(), group);
    }

    /**
     * Consulta uma option do jogador.
     *
     * @param option a option para consulta
     * @return uma String vazia se ela não existe, ou então
     * a option.
     */
    default String getOption(String option) {
        return PermissionHandler.getOption(getUUID(), option);
    }

    /**
     * Define uma option do jogador.
     *
     * @param option a option para definir
     * @param value o valor para definir
     * @return uma String vazia se ela não existe, ou então
     * a option.
     */
    default boolean setOption(String option, String value) {
        return PermissionHandler.setOption(getUUID(), option, value);
    }

    /**
     * Verifica se o jogador possui a quantidade
     * de itens especificada.
     *
     * @param item o item para verificar.
     * @param amount a quantidade para verificar.
     * @return boolean se o jogador possui ou não a quantidade
     * de itens verificada.
     */
    boolean hasItems(ISealStack item, int amount);

    /**
     * Remove o item especificado do inventário
     * do jogador, na quantia inserida.
     *
     * @param item o item para remover.
     * @param amount a quantidade para remover.
     */
    void removeItems(ISealStack item, int amount);

    /**
     * Remove o item especificado do inventário
     * do jogador.
     *
     * @param item o item para remover.
     */
    default void removeItem(ISealStack item) {
        removeItems(item, item.getAmount());
    }

    /**
     * Remove os itens especificados do inventário
     * do jogador.
     *
     * @param items os itens para remover.
     */
    default void removeItems(ISealStack... items) {
        for (ISealStack item : items) {
            removeItems(item);
        }
    }

    /**
     * Tenta dar os itens especificados para
     * o jogador.
     *
     * @param items os itens para dar pro jogador.
     * @return os itens que foram rejeitados pelo,
     * seja por causa de inventário cheio ou outro
     * motivo.
     */
    List<ISealStack> giveItems(ISealStack... items);

    /**
     * Tenta dar o item especificado para
     * o jogador, na quantidade informada.
     * Caso a quantia seja maior que o limite
     * de stack do item, novos stacks serão
     * criados.
     *
     * @param item o item para dar pro jogador.
     * @param amount a quantidade para dar.
     * @return os itens que foram rejeitados,
     * seja por causa de inventário cheio ou outro
     * motivo.
     */
    default List<ISealStack> giveItems(ISealStack item, int amount) {
        List<ISealStack> rejected = new ArrayList<>();
        while(amount > 0) {
            if(item.getMaxAmount() >= amount) {
                rejected.addAll(giveItems(item.copy().setAmount(amount)));
                amount = 0;
            } else {
                rejected.addAll(giveItems(item.copy().setAmount(item.getMaxAmount())));
                amount -= item.getMaxAmount();
            }
        }
        return rejected;
    }

    /**
     * Tenta dar os itens especificados para
     * o jogador. Caso não seja possível colocá-los
     * no inventário do mesmo, os itens são dropados
     * no chão.
     *
     * @param items os itens para dar pro jogador.
     */
    void giveOrDropItems(ISealStack... items);

    /**
     * Tenta dar o item especificado para
     * o jogador, na quantidade informada.
     * Caso não seja possível colocá-los no
     * inventário do mesmo, os itens são dropados
     * no chão.
     * Caso a quantia seja maior que o limite
     * de stack do item, novos stacks serão
     * criados.
     *
     * @param item o item para dar pro jogador.
     * @param amount a quantidade para dar.
     */
    default void giveOrDropItems(ISealStack item, int amount) {
        while(amount > 0) {
            if(item.getMaxAmount() >= amount) {
                giveOrDropItems(item.copy().setAmount(amount));
                amount = 0;
            } else {
                giveOrDropItems(item.copy().setAmount(item.getMaxAmount()));
                amount -= item.getMaxAmount();
            }
        }
    }

    /**
     * Retorna todos os itens que o jogador
     * possui no inventário.
     *
     * @return todos os itens no inventário do
     * jogador.
     */
    List<ISealStack> getInventory();

    /**
     * Faz o jogador executar um comando.
     *
     * @param command o comando para ser
     *                executado.
     */
    void runCommand(String command);

    /**
     * Consulta a quantia de dinheiro
     * que o jogador possui.
     *
     * @return a quantia que o jogador possui.
     */
    default double getMoney() {
        return EconomyHandler.getMoney(getUUID());
    }

    /**
     * Consulta a quantia de dinheiro
     * que o jogador possui na moeda
     * especificada.
     *
     * @param currency a moeda para consulta.
     * @return a quantia que o jogador possui.
     */
    default double getMoney(String currency) {
        return EconomyHandler.getMoney(getUUID(), currency);
    }

    /**
     * Consulta se o jogador possui
     * uma quantia de dinheiro especificada.
     *
     * @param value o valor para consulta.
     * @return se o jogador possui o money.
     */
    default boolean hasMoney(double value) {
        return EconomyHandler.hasMoney(getUUID(), value);
    }

    /**
     * Consulta se o jogador possui
     * uma quantia de dinheiro na moeda
     * especificada.
     *
     * @param value o valor para consulta.
     * @param currency a moeda para consulta.
     * @return se o jogador possui o money.
     */
    default boolean hasMoney(double value, String currency) {
        return EconomyHandler.hasMoney(getUUID(), value, currency);
    }

    /**
     * Adiciona uma quantia de dinheiro
     * na conta do jogador.
     *
     * @param value o valor para adição.
     */
    default void addMoney(double value) {
        EconomyHandler.addMoney(getUUID(), value);
    }

    /**
     * Adiciona uma quantia de dinheiro
     * na conta do jogador na moeda
     * especificada.
     *
     * @param value o valor para adição.
     * @param currency a moeda desejada.
     */
    default void addMoney(double value, String currency) {
        EconomyHandler.addMoney(getUUID(), value, currency);
    }

    /**
     * Remove uma quantia de dinheiro
     * da conta do jogador.
     *
     * @param value o valor para remoção.
     */
    default void removeMoney(double value) {
        EconomyHandler.removeMoney(getUUID(), value);
    }

    /**
     * Remove uma quantia de dinheiro
     * da conta do jogador na moeda
     * especificada.
     *
     * @param value o valor para remoção.
     * @param currency a moeda desejada.
     */
    default void removeMoney(double value, String currency) {
        EconomyHandler.removeMoney(getUUID(), value, currency);
    }

    /**
     * Aguarda uma mensagem do jogador
     * para executar uma ação.
     *
     * @param onReceive a ação efetuada ao receber
     *                  a mensagem.
     * @param secondsLimit o limite de segundos antes desse
     *                     waiter se auto-destruir.
     * @param onExpire a ação efetuada ao tempo limiter ser
     *                 esgotado.
     */
    default void waitForMessage(Runnable.MessageRunnable onReceive, int secondsLimit, java.lang.Runnable onExpire) {
        ChatHandler.waitForMessage(getUUID(), onReceive, secondsLimit, onExpire);
    }

    /**
     * Aguarda uma mensagem do jogador
     * para executar uma ação.
     *
     * @param onReceive a ação efetuada ao receber
     *                  a mensagem.
     * @param secondsLimit o limite de segundos antes desse
     *                     waiter se auto-destruir.
     */
    default void waitForMessage(Runnable.MessageRunnable onReceive, int secondsLimit) {
        ChatHandler.waitForMessage(getUUID(), onReceive, secondsLimit, () -> {});
    }

    /**
     * Aguarda uma mensagem do jogador
     * para executar uma ação.
     *
     * @param onReceive a ação efetuada ao receber
     *                  a mensagem.
     */
    default void waitForMessage(Runnable.MessageRunnable onReceive) {
        ChatHandler.waitForMessage(getUUID(), onReceive);
    }

}
