package brcomkassin.dungeonsClass.attribute.gui;

import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.attribute.DungeonClassInMemory;
import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.user.UserClass;
import brcomkassin.dungeonsClass.utils.ItemBuilder;
import brcomkassin.dungeonsClass.utils.Message;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AttributeGUI {

    private final DungeonClassInMemory dungeonClassInMemory;

    public AttributeGUI(DungeonClassInMemory dungeonClassInMemory) {
        this.dungeonClassInMemory = dungeonClassInMemory;
    }

    public void openInitialInventory(Player player) {
        Inventory inventory = createInventory(Names.INITIAL_INVENTORY, 27);

        ItemStack offensiveItem = ItemBuilder.of(Material.IRON_SWORD)
                .setName("&cAtributos Ofensivos")
                .setLore(
                        "&7Melhore seu poder de ataque,",
                        "&7dano crítico e velocidade de ataque.",
                        "",
                        "&eClique para abrir os atributos ofensivos"
                )
                .build();

        ItemStack defensiveItem = ItemBuilder.of(Material.SHIELD)
                .setName("&bAtributos Defensivos")
                .setLore(
                        "&7Aumente sua resistência,",
                        "&7vida máxima e defesa contra dano.",
                        "",
                        "&eClique para abrir os atributos defensivos"
                )
                .build();

        ItemStack mobilityItem = ItemBuilder.of(Material.FEATHER)
                .setName("&aAtributos de Mobilidade")
                .setLore(
                        "&7Aprimore velocidade de movimento,",
                        "&7altura de pulo e agilidade.",
                        "",
                        "&eClique para abrir os atributos de mobilidade"
                )
                .build();

        ItemStack utilityItem = ItemBuilder.of(Material.CLOCK)
                .setName("&dAtributos Utilitários")
                .setLore(
                        "&7Melhore habilidades especiais como",
                        "&7regeneração, cooldowns e coleta de recursos.",
                        "",
                        "&eClique para abrir os atributos utilitários"
                )
                .build();

        inventory.setItem(10, offensiveItem);
        inventory.setItem(12, defensiveItem);
        inventory.setItem(14, mobilityItem);
        inventory.setItem(16, utilityItem);

        player.openInventory(inventory);
    }

    private Inventory createInventory(final String title, final int size) {
        return Bukkit.createInventory(null, size, title);
    }

    public void openCategoryInventory(Player player, AttributeCategory category) {
        String title;
        switch (category) {
            case OFFENSIVE -> title = Names.OFFENSIVE;
            case DEFENSIVE -> title = Names.DEFENSIVE;
            case MOBILITY -> title = Names.MOBILITY;
            case UTILITY -> title = Names.UTILITY;
            default -> throw new IllegalArgumentException("Categoria inválida");
        }

        Inventory inventory = createInventory(title, 27);

        List<AttributeType> attributes = Arrays.stream(AttributeType.values())
                .filter(attr -> attr.getCategory() == category)
                .toList();

        int slot = 10;
        for (AttributeType type : attributes) {
            ItemStack item = ItemBuilder.of(getMaterialForAttribute(type))
                    .setName("&e" + formatKeyName(type.getKey()))
                    .setLore(
                            "&7Clique para melhorar o atributo",
                            "&7Categoria: &f" + category.name()
                    )
                    .setNameSpacedKey(type.getKey())
                    .build();

            inventory.setItem(slot, item);
            slot += 2;
        }

        player.openInventory(inventory);
    }

    public boolean isInventoryUpdated(String string) {
        return string.equals(AttributeGUI.Names.INITIAL_INVENTORY) ||
                string.equals(AttributeGUI.Names.OFFENSIVE) ||
                string.equals(AttributeGUI.Names.DEFENSIVE) ||
                string.equals(AttributeGUI.Names.MOBILITY) ||
                string.equals(AttributeGUI.Names.UTILITY);
    }

    private Material getMaterialForAttribute(AttributeType type) {
        return switch (type) {
            case PHYSICAL_DAMAGE -> Material.IRON_SWORD;
            case CRITICAL_CHANCE -> Material.NETHERITE_SWORD;
            case ATTACK_SPEED -> Material.GOLDEN_SWORD;
            case ARMOR_PENETRATION -> Material.ARROW;

            case MAX_HEALTH -> Material.RED_DYE;
            case ARMOR -> Material.IRON_CHESTPLATE;
            case MAGIC_RESIST -> Material.ENCHANTED_BOOK;
            case DODGE_CHANCE -> Material.RABBIT_FOOT;

            case COOLDOWN_REDUCTION -> Material.CLOCK;
            case RESOURCE_GAIN -> Material.EMERALD;

            case MOVE_SPEED -> Material.FEATHER;
            case JUMP_HEIGHT -> Material.SLIME_BALL;
        };
    }

    public void upgradeAttribute(Player player, ItemStack item) {
        UserClass userClass = dungeonClassInMemory.getUserClass(player.getUniqueId());
        AttributeType type = getAttributeFromItem(item);
        if (userClass == null || type == null) return;

        Attribute attribute = userClass.getAttribute(type);

        if (attribute != null) {
            Message.Chat.send(player, "Aprimorando o atributo &6" + formatKeyName(type.getKey()) + " &7para &e" + attribute.getValue());
            attribute.setValue(attribute.getValue() + 1);
        }
    }

    public boolean isAttributeItem(ItemStack item) {
        return getAttributeFromItem(item) != null;
    }

    private AttributeType getAttributeFromItem(ItemStack item) {
        PersistentDataContainerView persistentDataContainer = item.getPersistentDataContainer();
        for (AttributeType type : AttributeType.values()) {
            NamespacedKey namespacedKey = new NamespacedKey(DungeonsClassPlugin.getInstance(), type.getKey());
            if (persistentDataContainer.has(namespacedKey, PersistentDataType.STRING)) {
                return type;
            }
        }
        return null;
    }

    private String formatKeyName(String key) {
        return Arrays.stream(key.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static class Names {
        public static final String INITIAL_INVENTORY = translateAlternateColorCodes("&7Atributos");
        public static final String OFFENSIVE = translateAlternateColorCodes("&cAtributos Ofensivos");
        public static final String DEFENSIVE = translateAlternateColorCodes("&bAtributos Defensivos");
        public static final String MOBILITY = translateAlternateColorCodes("&aAtributos de Mobilidade");
        public static final String UTILITY = translateAlternateColorCodes("&dAtributos Utilitários");

        private static String translateAlternateColorCodes(String string) {
            return ChatColor.translateAlternateColorCodes('&', string);
        }
    }
}
