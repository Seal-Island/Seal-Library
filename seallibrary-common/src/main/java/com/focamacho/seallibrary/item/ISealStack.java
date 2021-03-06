package com.focamacho.seallibrary.item;

import com.focamacho.seallibrary.item.lib.ItemFlag;
import com.focamacho.seallibrary.nbt.ISealNBT;

import java.util.List;

/**
 * Representa um ItemStack
 * no jogo.
 */
@SuppressWarnings("unused")
public interface ISealStack {

    /**
     * Retorna o nome atual do item.
     * @return o nome do item.
     */
    String getName();

    /**
     * Renomeia o item.
     * @param name o nome desejado.
     */
    ISealStack setName(String name);

    /**
     * Verifica se o item tem um nome
     * customizado ou não.
     * @return se o nome do item foi editado
     * ou não.
     */
    boolean hasCustomName();

    /**
     * Retorna o Registry Name desse item.
     * Por exemplo, um carvão tem o Registry Name
     * de "minecraft:coal".
     * @return o Registry Name do item.
     */
    String getRegistryName();

    /**
     * Retorna a meta-data(ou durability data) desse item.
     * @return a meta-data do item.
     */
    int getMeta();

    /**
     * Retorna a lore atual do item.
     * @return a lore do item.
     */
    List<String> getLore();

    /**
     * Define a lore do item.
     * @param lore a lore desejada.
     */
    ISealStack setLore(List<String> lore);

    /**
     * Define se o stack tem o
     * efeito de brilho de encantamento.
     * @param glowing brilho ativado/desativado.
     */
    ISealStack setGlowing(boolean glowing);

    /**
     * Define uma flag de item
     * para esse stack.
     * @param flag a flag desejada.
     * @param value se ela está ativa ou não.
     */
    ISealStack setFlag(ItemFlag flag, boolean value);

    /**
     * Retorna o valor de uma flag
     * para esse stack.
     * @param flag a flag desejada.
     */
    boolean getFlag(ItemFlag flag);

    /**
     * Retorna a quantidade de itens
     * que esse stack possui.
     * @return a quantidade de itens no stack.
     */
    int getAmount();

    /**
     * Define a quantidade de itens
     * que esse stack possui.
     * @param amount a quantidade desejada.
     */
    ISealStack setAmount(int amount);

    /**
     * Retorna a quantidade máxima
     * de itens que esse stack
     * suporta.
     * @return a quantidade máxima de itens.
     */
    int getMaxAmount();

    /**
     * Retorna o NBT do item.
     * @return o NBT do item em String.
     */
    String getData();

    /**
     * Define o NBT do Item.
     * @param nbt o NBT em String.
     */
    ISealStack setData(String nbt);

    /**
     * Retorna o NBT do item.
     * @return o NBT do item.
     */
    ISealNBT getNBT();

    /**
     * Define o NBT do Item.
     * @param nbt o NBT para definir.
     */
    ISealStack setNBT(ISealNBT nbt);

    /**
     * Retorna o ItemStack original.
     * @return o ItemStack original.
     */
    Object toOriginal();

    /**
     * Retorna um JSON contendo informações
     * sobre esse Stack.
     * O JSON pode ser convertido novamente
     * em um SealStack utilizando o método
     * @see SealStack#fromJson(String) ();
     */
    String toJson();
    
    /**
     * Retorna uma copia
     * desse item.
     * @return a copia do item.
     */
    ISealStack copy();

    /**
     * Confere se o item nesse SealStack
     * é igual a outro.
     * @return se o item é ou não igual.
     */
    boolean equals(ISealStack toCompare);

    /**
     * Confere se o item nesse SealStack
     * é igual a outro, ignorando a quantidade
     * do item.
     * @return se o item é ou não igual.
     */
    boolean equalsIgnoreAmount(ISealStack toCompare);

    /**
     * Confere se o item nesse SealStack
     * é igual a outro, ignorando o nbt
     * do item.
     * @param toCompare o item para ser comparado.
     * @return se o item é ou não igual.
     */
    default boolean equalsIgnoreNBT(ISealStack toCompare) {
        return this.copy().setData("{}").equals(toCompare.copy().setData("{}"));
    }

    /**
     * Confere se o item nesse SealStack
     * é igual a outro, fazendo a comparação
     * somente do item, ignorando a meta-data,
     * durabilidade, nbt, e outros valores que
     * são atribuídos ao ItemStack e não ao
     * Item.
     * @param toCompare o item para ser comparado.
     * @return se o item é ou não igual.
     */
    default boolean equalsType(ISealStack toCompare) {
        return toCompare.getRegistryName().equalsIgnoreCase(this.getRegistryName());
    }

    /**
     * Confere se esse stack não é somente
     * um item vazio.
     *
     * @return se é um item vazio ou não.
     */
    boolean isEmpty();

}
