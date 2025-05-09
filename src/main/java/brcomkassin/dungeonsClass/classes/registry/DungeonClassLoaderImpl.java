package brcomkassin.dungeonsClass.classes.registry;

import brcomkassin.dungeonsClass.attribute.AttributeController;
import brcomkassin.dungeonsClass.attribute.DungeonClassInMemory;
import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.classes.DungeonClass;
import brcomkassin.dungeonsClass.utils.ColoredLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class DungeonClassLoaderImpl implements DungeonClassLoader {

    private final FileConfiguration config;
    private final DungeonClassInMemory dungeonClassInMemory;

    public DungeonClassLoaderImpl(FileConfiguration config, DungeonClassInMemory dungeonClassInMemory) {
        this.config = config;
        this.dungeonClassInMemory = dungeonClassInMemory;
    }

    @Override
    public void loadClasses() {
        ConfigurationSection classesSection = config.getConfigurationSection("classes");
        if (classesSection == null) {
            ColoredLogger.info("&4Seção 'classes' não encontrada no config.yml");
            return;
        }

        classesSection.getKeys(false).forEach(className -> {
            ConfigurationSection attributesSection = classesSection.getConfigurationSection(className + ".attributes");
            if (attributesSection == null) {
                ColoredLogger.info("&4Classe %s não possui seção 'attributes'".formatted(className));
                return;
            }

            DungeonClass dungeonClass = new DungeonClass(className);
            loadAttributes(dungeonClass, attributesSection);
            dungeonClassInMemory.saveClass(dungeonClass);
        });
    }

    private void loadAttributes(DungeonClass dungeonClass, ConfigurationSection attributesSection) {
        attributesSection.getKeys(false).forEach(categoryName -> {
            AttributeCategory category = AttributeCategory.fromString(categoryName);
            if (category == null) {
                ColoredLogger.info("&4Categoria desconhecida: %s".formatted(categoryName));
                return;
            }
            ConfigurationSection categorySection = attributesSection.getConfigurationSection(categoryName);
            if (categorySection == null) return;

            categorySection.getKeys(false).forEach(attributeKey -> {
                AttributeType type = AttributeType.fromKey(attributeKey);
                if (type == null || type.getCategory() != category) {
                    ColoredLogger.info("&4Atributo %s não pertence à categoria %s ou é inválido"
                            .formatted(attributeKey, category));
                    return;
                }

                float value = (float) categorySection.getDouble(attributeKey);
                dungeonClass.addAttribute(category, new Attribute(attributeKey, value));
                ColoredLogger.info("&aAtributo %s adicionado ao classe&6 %s".formatted(attributeKey, dungeonClass.getName()));
            });
        });
    }
}