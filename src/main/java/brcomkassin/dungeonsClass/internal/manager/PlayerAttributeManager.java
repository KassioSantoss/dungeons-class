package brcomkassin.dungeonsClass.internal.manager;

import brcomkassin.dungeonsClass.attribute.control.AttributeController;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.data.model.MemberClass;
import brcomkassin.dungeonsClass.utils.ColoredLogger;
import brcomkassin.dungeonsClass.utils.Message;
import com.google.gson.Gson;
import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlayerAttributeManager {

    private final MemberClassService service;

    public PlayerAttributeManager(MemberClassService service) {
        this.service = service;
    }

    public void increaseBaseValue(Player player, AttributeType type, double amount) {
        Optional<MemberClass> optional = service.findById(player.getUniqueId());
        if (optional.isEmpty()) return;

        MemberClass memberClass = optional.get();

        Attribute attribute = memberClass.getAttribute(type);
        if (attribute == null) return;

        attribute.increaseBaseValue(amount);
        AttributeController.of().applyAttributeModifier(player, type, attribute.getAppliedValue());
        service.save(memberClass);

        Message.Chat.send(player, "Capacidade de &6" + type.getKey() + " &7aumentada para &e" + attribute.getBaseValue());
    }

    public boolean increaseCurrentValue(Player player, AttributeType type, double amount) {
        MemberClass memberClass = service.findById(player.getUniqueId()).orElse(null);
        if (memberClass == null) return false;

        Attribute attribute = memberClass.getAttribute(type);
        if (attribute == null) return false;

        attribute.increaseCurrentValue(amount);
        service.save(memberClass);

        Message.Chat.send(player, "&6" + type.getKey() + " &7restaurado para &e" + attribute.getCurrentValue());
        return true;
    }

    public boolean add(Player player, AttributeType type, double value) {
        MemberClass memberClass = service.findById(player.getUniqueId()).orElse(null);
        if (memberClass == null) return false;

        Attribute attribute = memberClass.getAttribute(type);
        if (attribute == null) return false;

        double newValue = attribute.getCurrentValue() + value;
        attribute.setCurrentValue(newValue);
        attribute.setBaseValue(newValue);
        service.save(memberClass);

        AttributeController.of().applyAttributeModifier(player, type, attribute.getAppliedValue());
        Message.Chat.send(player, "Atributo &6" + type.getKey() + " &7aumentado para &e" + newValue);
        return true;
    }

    public boolean set(Player player, AttributeType type, double value) {
        MemberClass memberClass = service.findById(player.getUniqueId()).orElse(null);
        if (memberClass == null) return false;

        Attribute attribute = memberClass.getAttribute(type);
        if (attribute == null) return false;

        attribute.setCurrentValue(value);
        attribute.setBaseValue(value);
        service.save(memberClass);
        AttributeController.of().applyAttributeModifier(player, type, attribute.getAppliedValue());

        Message.Chat.send(player, "Atributo &6" + type.getKey() + " &7foi setado para: &e" + value);
        return true;
    }

    public Attribute get(Player player, AttributeType type) {
        MemberClass memberClass = service.findById(player.getUniqueId()).orElse(null);
        return memberClass != null ? memberClass.getAttribute(type) : null;
    }

    public List<Attribute> getAll(Player player) {
        MemberClass memberClass = service.findById(player.getUniqueId()).orElse(null);
        return memberClass != null ? memberClass.getAllAttributes() : Collections.emptyList();
    }
}

