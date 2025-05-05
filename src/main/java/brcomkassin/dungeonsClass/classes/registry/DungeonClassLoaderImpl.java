package brcomkassin.dungeonsClass.classes.registry;

import brcomkassin.dungeonsClass.attribute.DungeonClassInMemory;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import brcomkassin.dungeonsClass.classes.DungeonClass;
import brcomkassin.dungeonsClass.utils.ColoredLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class DungeonClassLoaderImpl implements DungeonClassLoader {

    private final FileConfiguration config;

    public DungeonClassLoaderImpl(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public void loadClasses() {
        ColoredLogger.info("&e[Dungeon Class] Carregando configurações das classes");

        ConfigurationSection classesSection = config.getConfigurationSection("classes");
        if (classesSection == null) {
            ColoredLogger.info("&cConfiguração inválida! Seção 'classes' não encontrada.");
            return;
        }

        int classesCount = classesSection.getKeys(false).size();

        for (String className : classesSection.getKeys(false)) {
            try {
                ConfigurationSection attributesSection = classesSection.getConfigurationSection(className + ".attributes");
                if (attributesSection == null) {
                    ColoredLogger.info(String.format(
                            "&cClasse '%s' não possui seção 'attributes'. Pulando...",
                            className
                    ));
                    continue;
                }

                DungeonClass dungeonClass = new DungeonClass(className);

                for (String attributeTypeStr : attributesSection.getKeys(false)) {
                    try {
                        AttributeType attributeType = AttributeType.valueOf(attributeTypeStr.toUpperCase());
                        ConfigurationSection typeSection = attributesSection.getConfigurationSection(attributeTypeStr);

                        if (typeSection == null) {
                            ColoredLogger.info(String.format(
                                    "&cTipo de atributo '%s' na classe '%s' não é uma seção válida. Pulando...",
                                    attributeTypeStr, className
                            ));
                            continue;
                        }

                        Set<Attribute> attributes = new HashSet<>();

                        for (String attributeKey : typeSection.getKeys(false)) {
                            try {
                                double value = typeSection.getDouble(attributeKey);
                                Attribute attribute = new Attribute(attributeKey, value);
                                attributes.add(attribute);
                            } catch (Exception e) {
                                ColoredLogger.info(String.format("&cValor inválido para atributo '%s.%s.%s'. Pulando...",
                                        className, attributeTypeStr, attributeKey
                                ));
                            }
                        }
                        dungeonClass.addInitialAttributes(attributeType, attributes);
                        DungeonClassInMemory.saveClass(dungeonClass);
                    } catch (IllegalArgumentException e) {
                        ColoredLogger.info(String.format("&cTipo de atributo '%s' na classe '%s' é inválido. Tipos válidos: %s",
                                attributeTypeStr, className, Arrays.toString(AttributeType.values())
                        ));
                    }
                }
            } catch (Exception e) {
                ColoredLogger.info(String.format("&cErro ao carregar classe '%s'. Motivo: %s", className, e.getMessage()
                ));
            }
        }
        ColoredLogger.info("&e[Dungeon Class] Configurações das classes carregadas com sucesso! " + classesCount + " classes carregadas.");
    }
}
