package com.focamacho.seallibrary.nbt;

import java.util.Set;

/**
 * Representa uma tag NBT
 * no jogo.
 */
@SuppressWarnings("unused")
public interface ISealNBT {

    /**
     * Verifica se esse NBT possui
     * uma key.
     * @param key a key para verificar.
     * @return se possui ou não essa key.
     */
    boolean hasKey(String key);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void set(String key, ISealNBT value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setByte(String key, byte value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setShort(String key, short value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setInt(String key, int value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setLong(String key, long value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setFloat(String key, float value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setDouble(String key, double value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setString(String key, String value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setByteArray(String key, byte[] value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setIntArray(String key, int[] value);

    /**
     * Define um valor nesse NBT.
     * @param key a key para definir.
     * @param value o valor para definir.
     */
    void setBoolean(String key, boolean value);


    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    ISealNBT get(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    byte getByte(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    short getShort(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    int getInt(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    long getLong(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    float getFloat(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    double getDouble(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    String getString(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    byte[] getByteArray(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    int[] getIntArray(String key);

    /**
     * Consulta um valor nesse NBT.
     * @param key a key para consultar.
     * @return o valor da key inserida, ou null
     * caso ela não exista.
     */
    boolean getBoolean(String key);

    /**
     * Retorna todas as keys nesse
     * NBT.
     * @return as keys nesse NBT.
     */
    Set<String> getKeys();

    /**
     * Remove uma key desse NBT.
     * @param key a key para remover.
     */
    void remove(String key);

    /**
     * Retorna o objeto de NBTTag
     * original.
     * @return o objeto original.
     */
    Object toOriginal();
    
}
