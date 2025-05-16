package brcomkassin.dungeonsClass.registry;

import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.attribute.PlayerAttributes;
import brcomkassin.dungeonsClass.internal.DungeonClassProvider;
import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.data.model.DungeonClass;
import brcomkassin.dungeonsClass.internal.manager.DungeonClassManager;
import brcomkassin.dungeonsClass.utils.ColoredLogger;
import brcomkassin.dungeonsClass.utils.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class DungeonClassLoaderImpl implements DungeonClassLoader {

    private final FileConfiguration config;
    private final DungeonClassManager manager;

    public DungeonClassLoaderImpl(DungeonsClassPlugin plugin, DungeonClassProvider provider) {
        this.config = new Config(plugin, "classes.yml");
        this.manager = provider.getDungeonClassManager();
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
            manager.saveClass(dungeonClass);
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

            PlayerAttributes playerAttributes = new PlayerAttributes();

            categorySection.getKeys(false).forEach(attributeKey -> {
                AttributeType type = AttributeType.fromKey(attributeKey);
                if (type == null || type.getCategory() != category) {
                    ColoredLogger.info("&4Atributo %s não pertence à categoria %s ou é inválido"
                            .formatted(attributeKey, category));
                    return;
                }

                int value = categorySection.getInt(attributeKey);

                Attribute attribute = new Attribute(attributeKey, value);
                attribute.setBaseValue(value);

                playerAttributes.addAttribute(type, attribute);

                ColoredLogger.info("&aAtributo %s adicionado ao classe&6 %s".formatted(attributeKey, dungeonClass.getName()));
            });
            dungeonClass.addAttribute(category, playerAttributes);
        });
    }
}