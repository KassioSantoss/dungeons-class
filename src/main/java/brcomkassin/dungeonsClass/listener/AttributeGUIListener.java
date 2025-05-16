package brcomkassin.dungeonsClass.listener;

import brcomkassin.dungeonsClass.attribute.gui.AttributeGUI;
import brcomkassin.dungeonsClass.internal.DungeonClassProvider;
import brcomkassin.dungeonsClass.attribute.AttributeCategory;
import brcomkassin.dungeonsClass.utils.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AttributeGUIListener implements Listener {

    private final AttributeGUI gui;
    private final Map<UUID, Integer> multipliers = new HashMap<>();

    public AttributeGUIListener(DungeonClassProvider provider) {
        this.gui = provider.getAttributeGUI();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getType() == Material.AIR) return;

        String title = event.getView().getTitle();

        if (title.equals(AttributeGUI.Names.INITIAL_INVENTORY)) {
            handleInitialInventoryClick(player, clicked);
            multipliers.put(player.getUniqueId(), 1);
            event.setCancelled(true);
            return;
        }

        if (gui.isAttributeInventory(title)) {
            event.setCancelled(true);
            if (gui.isMultiplierButton(clicked)) {
                multipliers.put(player.getUniqueId(), gui.getMultiplierFromItem(clicked));

                AttributeCategory category = gui.getCategoryFromTitle(title);
                gui.openAttributeInventory(player, category, multipliers.get(player.getUniqueId()));
                return;
            }

            if (!gui.isAttributeItem(clicked)) return;
            boolean upgradeAttribute = gui.upgradeAttribute(player, clicked, multipliers.get(player.getUniqueId()));
            if (upgradeAttribute) {
                Message.Chat.send(player, "&aAtributo atualizado com sucesso!");
            } else {
                Message.Chat.send(player, "&cVocê Não possui pontos suficientes!");
                player.closeInventory();
            }
        }
    }

    private void handleInitialInventoryClick(Player player, ItemStack clicked) {
        if (!clicked.hasItemMeta()) return;
        String displayName = clicked.getItemMeta().getDisplayName();
        AttributeCategory.fromDisplayName(displayName).ifPresent(category -> {
            gui.openAttributeInventory(player, category, 1);
        });
    }
}