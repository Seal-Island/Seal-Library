package com.focamacho.seallibrary.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import com.focamacho.seallibrary.logger.SealLogger;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.translate.UnicodeUnescaper;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe para criação de arquivos
 * de configuração e idioma de
 * forma fácil.
 */
@SuppressWarnings({"deprecation", "unchecked"})
public class SealConfig {

    private final Map<Class, AbstractMap.SimpleEntry<File, Object>> configs = new HashMap<>();
    private final UnicodeUnescaper unicodeUnescaper = new UnicodeUnescaper();

    private final File langFolder;
    @Getter private final ILangConfig langConfig;

    /**
     * Construtor para configurações sem arquivos
     * de idioma.
     */
    public SealConfig() {
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
        this.langFolder = langFolder;
        this.langConfig = langConfig;
        createLangConfig();
    }

    /**
     * Recarrega as configurações.
     */
    public void reload() {
        configs.forEach((classe, entry) -> createConfig(entry.getKey(), classe));
        createLangConfig();
    }

    /**
     * Carrega ou cria o arquivo de configuração, e
     * retorna seu valor.
     * @param configFile o arquivo de configuração.
     * @param classe a classe de configuração
     * @return um objeto da classe de configuração.
     */
    public <T> T getConfig(File configFile, Class<T> classe) {
        Map.Entry<File, Object> config = configs.get(classe);
        if(config == null) return createConfig(configFile, classe);
        return (T) config.getValue();
    }

    /**
     * Cria o arquivo de configuração, e
     * retorna o seu valor.
     *
     * Esse método é somente utilizado privadamente
     * pelo getConfig().
     *
     * @param configFile o arquivo de configuração.
     * @param configClass a classe de configuração
     * @return um objeto da classe de configuração.
     */
    private <T> T createConfig(File configFile, Class<T> configClass) {
        try {
            JsonObject defaults = Jankson.builder().build().load(Jankson.builder().build().toJson(configClass.newInstance()).toJson(true, true, 0));

            JsonObject configObject;
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
                configObject = defaults;
            } else configObject = Jankson.builder().build().load(configFile);

            defaults.forEach((string, element) -> {
                if(!configObject.containsKey(string)) configObject.putDefault(string, element, defaults.getComment(string));
            });

            FileUtils.write(configFile, unicodeUnescaper.translate(configObject.toJson(true, true, 0)), StandardCharsets.UTF_8);
            T config = Jankson.builder().build().fromJson(configObject.toJson(), configClass);
            configs.put(configClass, new AbstractMap.SimpleEntry<>(configFile, config));
            return config;
        } catch(Exception e) {
            SealLogger.error("Error loading config file:");
            e.printStackTrace();
            return null;
        }
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

        if(!langFolder.exists()) langFolder.mkdirs();
        if(!langFolder.isDirectory()) {
            SealLogger.error("Can't load lang files.");
            SealLogger.error(langFolder.toString() + " is not a directory.");
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
                SealLogger.error("Error loading lang file:");
                e.printStackTrace();
            }
        }

        langConfig.translations.forEach((lang, map) -> {
            File file = new File(langFolder, lang.toLowerCase() + ".json");
            try {
                JsonObject configObject;
                if(!file.exists()) {
                    file.createNewFile();
                    configObject = Jankson.builder().build().load("{}");
                } else configObject = Jankson.builder().build().load(file);
                map.forEach((key, value) -> configObject.putDefault(key, value, null));

                FileUtils.write(file, unicodeUnescaper.translate(configObject.toJson(false, true, 0)), StandardCharsets.UTF_8);
            } catch (Exception e) {
                SealLogger.error("Error saving lang file:");
                e.printStackTrace();
            }
        });
    }


}
