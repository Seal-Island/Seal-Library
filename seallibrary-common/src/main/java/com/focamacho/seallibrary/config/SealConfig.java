package com.focamacho.seallibrary.config;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.Jankson;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.JsonObject;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.JsonPrimitive;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementação da SealConfig na SealLibrary para
 * criação de arquivos de configuração e idioma de
 * forma fácil.
 */
@SuppressWarnings({"unused"})
public class SealConfig extends com.focamacho.sealconfig.SealConfig {

    private final File langFolder;
    @Getter private final ILangConfig langConfig;

    /**
     * Construtor para configurações sem arquivos
     * de idioma.
     */
    public SealConfig() {
        super();
        this.langFolder = null;
        this.langConfig = null;
    }

    /**
     * Construtor para configurações com arquivos
     * de idioma.
     * @param langFolder a pasta onde os arquivos de idioma ficarão.
     * @param langConfig um objeto criado implementando ILangConfig
     */
    public SealConfig(File langFolder, ILangConfig langConfig) {
        super();
        this.langFolder = langFolder;
        this.langConfig = langConfig;
        createLangConfig();
    }

    /**
     * Recarrega as configurações.
     */
    @Override
    public void reload() {
        super.reload();
        createLangConfig();
    }

    /**
     * Cria ou carrega os arquivos de idioma.
     *
     * Esse método é somente utilizado privadamente
     * e tem seus resultados salvos na variável
     * langConfig, que pode ser obtida por meio
     * do SealConfig#getLangConfig.
     */
    private void createLangConfig() {
        if(langFolder == null || langConfig == null) return;
        langConfig.translations.clear();

        //Carregar as traduções padrões
        langConfig.getDefaultTranslations().forEach(langConfig.translations::put);

        if(!langFolder.exists()) {
            boolean mk = langFolder.mkdirs();
        }
        if(!langFolder.isDirectory()) {
            logger.severe("Não foi possível carregar os arquivos de idioma.");
            logger.severe(langFolder.toString() + " não é uma pasta.");
            return;
        }

        for (File file : langFolder.listFiles()) {
            try {
                if (file.getName().endsWith(".json")) {
                    String lang = file.getName().replace(".json", "").toLowerCase();

                    if(!langConfig.translations.containsKey(lang)) langConfig.translations.put(lang, new LinkedHashMap<>());

                    Map<String, String> langMap = langConfig.translations.get(lang);

                    JsonObject configObject = Jankson
                            .builder()
                            .build()
                            .load(file);

                    configObject.forEach((key, value) -> {
                        if (value instanceof JsonPrimitive && ((JsonPrimitive) value).getValue() instanceof String) langMap.put(key, (String)((JsonPrimitive) value).getValue());
                    });
                }
            } catch(Exception e) {
                logger.severe("Erro ao carregar um arquivo de idioma:");
                e.printStackTrace();
            }
        }

        langConfig.translations.forEach((lang, map) -> {
            File file = new File(langFolder, lang.toLowerCase() + ".json");
            try {
                JsonObject configObject;
                if(!file.exists()) {
                    boolean nf = file.createNewFile();
                    configObject = Jankson.builder().build().load("{}");
                } else configObject = Jankson.builder().build().load(file);
                map.forEach((key, value) -> configObject.putDefault(key, value, null));

                FileUtils.write(file, unicodeUnescaper.translate(configObject.toJson(false, true, 0)), StandardCharsets.UTF_8);
            } catch (Exception e) {
                logger.severe("Erro ao salvar um arquivo de idioma:");
                e.printStackTrace();
            }
        });
    }


}
