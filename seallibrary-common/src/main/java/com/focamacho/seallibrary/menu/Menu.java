package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.impl.Implementations;
import com.focamacho.seallibrary.menu.item.IMenuItem;
import com.focamacho.seallibrary.menu.lib.Runnable;
import com.focamacho.seallibrary.player.ISealPlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "UnusedReturnValue"})
@Accessors(chain = true)
public abstract class Menu {

    /**
     * Cria um novo menu.
     * @return o menu criado.
     */
    public static Menu create() {
        return Implementations.menuBuilder.create();
    }

    /**
     * Copia um menu.
     * @param menu o menu para ser copiado.
     * @return o menu copiado.
     */
    public static Menu create(Menu menu) {
        return menu.copy();
    }

    /**
     * O título do inventário desse menu.
     */
    @Getter @Setter protected String title = "";

    /**
     * O número de colunas que o inventário
     * desse menu possui.
     */
    @Getter @Setter protected int rows = 1;

    /**
     * A ação efetuada ao inventário desse menu
     * ser aberto.
     */
    @Getter @Setter protected Runnable.InteractRunnable onOpen = (interact) -> {};

    /**
     * A ação efetuada ao inventário desse menu
     * ser fechado.
     */
    @Getter @Setter protected Runnable.InteractRunnable onClose = (interact) -> {};

    /**
     * A ação efetuada ao clicar em um no inventário
     * desse menu, ou no inventário do jogador
     * enquanto ele estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onClick = (click) -> {};

    /**
     * A ação efetuada ao clicar com o botão
     * esquerdo do mouse em um no inventário
     * desse menu, ou no inventário do jogador
     * enquanto ele estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onPrimary = (click) -> {};

    /**
     * A ação efetuada ao clicar com o botão
     * central do mouse em um no inventário
     * desse menu, ou no inventário do jogador
     * enquanto ele estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onMiddle = (click) -> {};

    /**
     * A ação efetuada ao clicar com o botão
     * direito do mouse em um no inventário
     * desse menu, ou no inventário do jogador
     * enquanto ele estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onSecondary = (click) -> {};

    /**
     * A ação efetuada ao clicar com o shift +
     * botão esquerdo do mouse em um no inventário
     * desse menu, ou no inventário do jogador
     * enquanto ele estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onShift = (click) -> {};

    /**
     * A ação efetuada ao clicar duas vezes com o
     * botão esquerdo do mouse em um no inventário
     * desse menu, ou no inventário do jogador
     * enquanto ele estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onDouble = (click) -> {};

    /**
     * A ação efetuada ao dropar um item do
     * inventário desse menu, ou do inventário do jogador
     * enquanto ele estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onDrop = (click) -> {};

    /**
     * A ação efetuada ao clicar com o shift +
     * botão direito do mouse em um no inventário
     * desse menu, ou no inventário do jogador
     * enquanto ele estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onShiftSecondary = (click) -> {};

    /**
     * A ação efetuada ao dropar um item do
     * inventário desse menu utilizando CTRL + Q,
     * ou do inventário do jogador enquanto ele
     * estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onDropAll = (click) -> {};

    /**
     * A ação efetuada ao clicar com uma tecla
     * numérica em um item do inventário
     * desse menu, ou do inventário do jogador
     * enquanto ele estiver com esse menu aberto.
     */
    @Getter @Setter protected Runnable.ClickRunnable onNumber = (click) -> {};

    /**
     * Todos os itens contidos nesse menu.
     */
    protected Map<Integer, IMenuItem> items = new HashMap<>();

    /**
     * Adiciona o item ao
     * menu.
     * @param item o item para adicionar.
     */
    public Menu addItem(IMenuItem item) {
        items.put(item.getSlot(), item);
        return this;
    }

    /**
     * Retorna o item que está
     * no slot inserido.
     * @param slot o slot do item.
     * @return o item no slot inserido ou
     * null caso não tenha um item nesse slot.
     */
    public IMenuItem getItem(int slot) {
        return items.get(slot);
    }

    /**
     * Retorna todos os itens desse
     * menu.
     * @return os itens desse menu.
     */
    public List<IMenuItem> getItems() {
        return new ArrayList<>(this.items.values());
    }

    /**
     * Retorna todos os itens contidos
     * no inventário original, em sua forma
     * original.
     * @return os itens no inventário.
     */
    public abstract Map<Integer, Object> getOriginalItems();

    /**
     * Sobreescreve todos os itens
     * desse menu.
     * @param items os itens para definir.
     */
    public Menu setItems(List<IMenuItem> items) {
        this.items.clear();
        items.forEach(this::addItem);
        return this;
    }

    /**
     * Remove todos os itens
     * desse menu.
     */
    public Menu clearItems() {
        items.clear();
        return this;
    }

    /**
     * Retorna o inventário criado
     * para esse menu.
     * @return o inventário criado.
     */
    public abstract Object get();

    /**
     * Atualiza o menu para
     * que os itens sejam
     * sincronizados com o inventário
     * aberto.
     */
    public abstract Menu update();

    /**
     * Abre esse menu para
     * um jogador.
     */
    public void open(ISealPlayer player) {
        player.openInventory(this);
    }

    /**
     * Cria uma cópia desse menu.
     */
    public Menu copy() {
        Menu copy = Menu.create();
        copy.setTitle(getTitle());
        copy.setRows(getRows());

        copy.setOnOpen(getOnOpen());
        copy.setOnClose(getOnClose());

        copy.setOnPrimary(getOnPrimary());
        copy.setOnSecondary(getOnSecondary());
        copy.setOnDrop(getOnDrop());
        copy.setOnDropAll(getOnDropAll());
        copy.setOnMiddle(getOnMiddle());
        copy.setOnNumber(getOnNumber());
        copy.setOnShift(getOnShift());
        copy.setOnShiftSecondary(getOnShiftSecondary());
        copy.setOnDouble(getOnDouble());

        getItems().forEach(item -> copy.addItem(item.copy()));
        return copy;
    }

}