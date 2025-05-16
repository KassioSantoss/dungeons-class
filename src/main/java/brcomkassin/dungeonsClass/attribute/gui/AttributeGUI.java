package brcomkassin.dungeonsClass.attribute.gui;

import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.data.model.MemberClass;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import brcomkassin.dungeonsClass.internal.manager.PlayerAttributeManager;
import brcomkassin.dungeonsClass.internal.DungeonClassProvider;
import brcomkassin.dungeonsClass.attribute.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.utils.ColoredLogger;
import brcomkassin.dungeonsClass.utils.ItemBuilder;
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

    private final MemberClassService service;
    private final PlayerAttributeManager attributeManager;
    private final Inventory INITIAL_INVENTORY;

    public AttributeGUI(DungeonClassProvider provider) {
        this.service = provider.getMemberClassService();
        this.attributeManager = provider.getPlayerAttributeManager();
        INITIAL_INVENTORY = createInventory(Names.INITIAL_INVENTORY, 27);
        INITIAL_INVENTORY.setItem(10, createCategoryItem(Material.IRON_SWORD, "&cAtributos Ofensivos", "ofensivos"));
        INITIAL_INVENTORY.setItem(12, createCategoryItem(Material.SHIELD, "&bAtributos Defensivos", "defensivos"));
        INITIAL_INVENTORY.setItem(14, createCategoryItem(Material.FEATHER, "&aAtributos de Mobilidade", "mobilidade"));
        INITIAL_INVENTORY.setItem(16, createCategoryItem(Material.CLOCK, "&dAtributos Utilitários", "utilitários"));
    }

    public void openInitialInventory(Player player) {
        player.openInventory(INITIAL_INVENTORY);
    }

    public void openAttributeInventory(Player player, AttributeCategory category, int multiplier) {
        String title = getTitleForCategory(category);
        Inventory inventory = createInventory(title, 36);

        List<AttributeType> attributes = Arrays.stream(AttributeType.values())
                .filter(attr -> attr.getCategory() == category)
                .toList();

        int slot = 10;
        for (AttributeType type : attributes) {
            ItemStack item = ItemBuilder.of(getMaterialForAttribute(type))
                    .setName("&e" + formatKey(type.getKey()))
                    .setLore("&7Clique para melhorar", "&7Categoria: &f" + category.name())
                    .setNameSpacedKey(type.getKey())
                    .build();
            inventory.setItem(slot, item);
            slot += 2;
        }

        inventory.setItem(35, createMultiplierItem(multiplier));
        player.openInventory(inventory);
    }

    public boolean isAttributeInventory(String title) {
        return Names.ALL_TITLES.contains(title);
    }

    public boolean isMultiplierButton(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().getDisplayName().startsWith(Names.MULTIPLIER_ITEM);
    }

    public int getMultiplierFromItem(ItemStack itemStack) {
        String rawName = itemStack.getItemMeta().getDisplayName();
        String stripped = rawName.replace(Names.MULTIPLIER_ITEM, "").replace("X", "");

        int x;
        try {
            x = Integer.parseInt(stripped);
        } catch (NumberFormatException e) {
            ColoredLogger.error("Erro ao converter multiplicador: " + stripped);
            return 1;
        }

        return switch (x) {
            case 1 -> 5;
            case 5 -> 10;
            default -> 1;
        };
    }

    private Material getMaterialForAttribute(AttributeType type) {
        return switch (type) {
            case PHYSICAL_DAMAGE -> Material.IRON_SWORD;
            case CRITICAL_CHANCE -> Material.NETHERITE_SWORD;
            case ATTACK_SPEED -> Material.GOLDEN_SWORD;
            case ARMOR_PENETRATION -> Material.ARROW;

            case MAX_HEALTH -> Material.RED_DYE;
            case RESISTANCE -> Material.IRON_CHESTPLATE;
            case MAGIC_RESIST -> Material.ENCHANTED_BOOK;
            case DODGE_CHANCE -> Material.RABBIT_FOOT;

            case COOLDOWN_REDUCTION -> Material.CLOCK;
            case RESOURCE_GAIN -> Material.EMERALD;

            case MOVE_SPEED -> Material.FEATHER;
        };
    }

    public boolean isAttributeItem(ItemStack item) {
        return getAttributeFromItem(item) != null;
    }

    public boolean upgradeAttribute(Player player, ItemStack item, int multiplier) {
        if (!hasPoints(player, multiplier)) return false;

        MemberClass memberClass = service.findById(player.getUniqueId()).orElse(null);
        if (memberClass == null) return false;

        memberClass.useAttributePoints(multiplier);
        AttributeType type = getAttributeFromItem(item);
        attributeManager.increaseBaseValue(player, type, multiplier);
        return true;
    }

    public boolean hasPoints(Player player, int points) {
        MemberClass memberClass = service.findById(player.getUniqueId()).orElse(null);
        if (memberClass == null) return false;
        return (memberClass.getAttributePoints() >= points);
    }

    private AttributeType getAttributeFromItem(ItemStack item) {
        PersistentDataContainerView container = item.getPersistentDataContainer();
        for (AttributeType type : AttributeType.values()) {
            NamespacedKey key = new NamespacedKey(DungeonsClassPlugin.getInstance(), type.getKey());
            if (container.has(key, PersistentDataType.STRING)) return type;
        }
        return null;
    }

    private ItemStack createMultiplierItem(int x) {
        return ItemBuilder.of(Material.EXPERIENCE_BOTTLE)
                .setName(Names.MULTIPLIER_ITEM + x + "X")
                .setLore("&7Clique para aumentar o atributo em: &e" + x + "X")
                .build();
    }

    private ItemStack createCategoryItem(Material mat, String name, String loreLabel) {
        return ItemBuilder.of(mat)
                .setName(name)
                .setLore("&7Clique para abrir os atributos " + loreLabel)
                .build();
    }

    private String formatKey(String key) {
        return Arrays.stream(key.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    private String getTitleForCategory(AttributeCategory category) {
        return switch (category) {
            case OFFENSIVE -> Names.OFFENSIVE;
            case DEFENSIVE -> Names.DEFENSIVE;
            case MOBILITY -> Names.MOBILITY;
            case UTILITY -> Names.UTILITY;
        };
    }

    private Inventory createInventory(String title, int size) {
        return Bukkit.createInventory(null, size, title);
    }

    public AttributeCategory getCategoryFromTitle(String title) {
        if (title.equals(Names.OFFENSIVE)) return AttributeCategory.OFFENSIVE;
        if (title.equals(Names.DEFENSIVE)) return AttributeCategory.DEFENSIVE;
        if (title.equals(Names.MOBILITY)) return AttributeCategory.MOBILITY;
        if (title.equals(Names.UTILITY)) return AttributeCategory.UTILITY;
        return null;
    }

    public static class Names {
        public static final String INITIAL_INVENTORY = color("&7Atributos");
        public static final String OFFENSIVE = color("&cAtributos Ofensivos");
        public static final String DEFENSIVE = color("&bAtributos Defensivos");
        public static final String MOBILITY = color("&aAtributos de Mobilidade");
        public static final String UTILITY = color("&dAtributos Utilitários");
        public static final String MULTIPLIER_ITEM = color("&eMultiplicador: &6");
        public static final List<String> ALL_TITLES = List.of(INITIAL_INVENTORY, OFFENSIVE, DEFENSIVE, MOBILITY, UTILITY);

        private static String color(String s) {
            return ChatColor.translateAlternateColorCodes('&', s);
        }
    }
}
