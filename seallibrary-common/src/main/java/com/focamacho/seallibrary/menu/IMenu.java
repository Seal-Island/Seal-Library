package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.menu.item.IMenuItem;
import com.focamacho.seallibrary.player.ISealPlayer;

import java.util.List;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface IMenu {

    /**
     * Retorna o título desse menu.
     * @return o título do menu.
     */
    String getTitle();

    /**
     * Define o título desse menu.
     * @param title o título desejado.
     */
    IMenu setTitle(String title);

    /**
     * Retorna o número de colunas
     * desse menu.
     * @return o número de colunas.
     */
    int getRows();

    /**
     * Define o número de colunas
     * desse menu.
     * @param rows o número de colunas desejado.
     */
    IMenu setRows(int rows);

    /**
     * Adiciona o item ao
     * menu.
     * @param item o item para adicionar.
     */
    IMenu addItem(IMenuItem item);

    /**
     * Retorna o item que está
     * no slot inserido.
     * @param slot o slot do item.
     * @return o item no slot inserido ou
     * null caso não tenha um item nesse slot.
     */
    IMenuItem getItem(int slot);

    /**
     * Retorna todos os itens desse
     * menu.
     * @return os itens desse menu.
     */
    List<IMenuItem> getItems();

    /**
     * Sobreescreve todos os itens
     * desse menu.
     * @param items os itens para definir.
     */
    IMenu setItems(List<IMenuItem> items);

    /**
     * Remove todos os itens
     * desse menu.
     */
    IMenu clearItems();

    /**
     * Retorna a instância do
     * plugin que criou esse menu.
     * @return a instância do plugin.
     */
    Object getPlugin();

    /**
     * Retorna o inventário criado
     * para esse menu.
     * @return o inventário criado.
     */
    Object get();

    /**
     * Atualiza o menu para
     * que os itens sejam
     * sincronizados com o inventário
     * aberto.
     */
    IMenu update();

    /**
     * Abre esse menu para
     * um jogador.
     */
    default void open(ISealPlayer player) {
        player.openInventory(this);
    }

    /**
     * Cria uma cópia desse menu.
     */
    default IMenu copy() {
        IMenu copy = Menu.create(getPlugin());
        //copy.setOnOpen(getOnOpen());
        //copy.setOnClose(getOnClose());
        copy.setTitle(getTitle());
        copy.setRows(getRows());
        getItems().forEach(item -> copy.addItem(item.copy()));
        return copy;
    }

}
