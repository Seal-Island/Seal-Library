package com.focamacho.seallibrary.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public interface ILangConfig {

    /**
     * HashMap contendo todas as traduções
     * carregadas pelo plugin.
     */
    Map<String, LinkedHashMap<String, String>> translations = new HashMap<>();

    /**
     * Retorna todas as traduções padrões do plugin.
     * Formato: CodigoDeIdioma - keyDeTraducao-Traducao
     *
     * Esse método deve ser sobreescrito para retornar
     * as traduções do seu plugin.
     *
     * @return as traduções padrões do plugin.
     */
    Map<String, LinkedHashMap<String, String>> getDefaultTranslations();

    /**
     * O idioma padrão que será usado pelo método
     * get(String key).
     *
     * Tenha certeza que esse idioma possui todas
     * as traduções setadas em getDefaultTranslations().
     *
     * @return o idioma padrão para tradução.
     */
    default String getDefaultLang() {
        return "en_us";
    }

    /**
     * Retorna a tradução no idioma
     * padrão definido em getDefaultLang().
     *
     * @param key a key de tradução
     * @return caso encontrada, a tradução
     * caso não, retorna a key
     */
    default String get(String key) {
        return get(getDefaultLang(), key);
    }

    /**
     * Retorna a tradução no idioma
     * inserido.
     *
     * @param lang o idioma desejado
     * @param key a key de tradução
     * @return caso encontrada, a tradução
     * caso não, retorna a key
     */
    default String get(String lang, String key) {
        return translations.getOrDefault(lang, translations.get(getDefaultLang())).getOrDefault(key, key);
    }

}
