package brcomkassin.dungeonsClass.attribute.gui;

import brcomkassin.dungeonsClass.attribute.attributes.AttributeCategory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AttributeGUIListener implements Listener {

    private final AttributeGUI attributeGUI;

    public AttributeGUIListener(AttributeGUI attributeGUI) {
        this.attributeGUI = attributeGUI;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getType() == Material.AIR) return;

        String title = event.getView().getTitle();

        if (title.equals(AttributeGUI.Names.INITIAL_INVENTORY)) {
            if (!clicked.hasItemMeta()) return;

            String displayName = clicked.getItemMeta().getDisplayName();
            AttributeCategory.fromDisplayName(displayName).ifPresent(category -> {
                attributeGUI.openCategoryInventory(player, category);
            });
            event.setCancelled(true);
        }
        if (attributeGUI.isInventoryUpdated(title)) {
            event.setCancelled(true);
            if (!attributeGUI.isAttributeItem(clicked)) return;
            attributeGUI.upgradeAttribute(player, clicked);
        }

    }

}
