package brcomkassin.dungeonsClass;

import brcomkassin.dungeonsClass.attribute.DungeonClassInMemory;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.user.UserClass;
import brcomkassin.dungeonsClass.classes.DungeonClass;
import brcomkassin.dungeonsClass.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DungeonClassCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return true;

        if (args.length == 0) {
            player.sendMessage("Use /classes entrar <classe>");
            return false;
        }

        if (args[0].equalsIgnoreCase("atributos")) {
            if (!DungeonClassInMemory.hasUserClass(player.getUniqueId())) {
                Message.Chat.send(player, "&4Voce nao esta em uma classe.");
                return false;
            }

            UserClass userClass = DungeonClassInMemory.getUserClass(player.getUniqueId());
            Message.Chat.send(player, "&aAtributos da sua classe: &6" + userClass.getClasse().getName());
            for (AttributeType type : AttributeType.values()) {
                Message.Chat.send(player, "&a" + type.toString());
            }
        }

        if (args[0].equalsIgnoreCase("entrar")) {
            if (args.length == 1) {
                Message.Chat.send(player, "&alUse /classes entrar <classe>");
                return false;
            }

            if (DungeonClassInMemory.hasUserClass(player.getUniqueId())) {
                Message.Chat.send(player, "&4Voce ja esta em uma classe.");
                return false;
            }

            if (!DungeonClassInMemory.containsClass(args[1])) {
                Message.Chat.send(player, "&4Classe: &6" + args[1] + " &4nao encontrada.");
                return false;
            }

            UserClass userClass = new UserClass(player, DungeonClassInMemory.getClass(args[1]));
            DungeonClassInMemory.addUserClass(player.getUniqueId(), userClass);
            Message.Chat.send(player, "&aVoce entrou na classe: &6" + args[1]);
            return false;
        }

        if (args[0].equalsIgnoreCase("sair")) {
            if (!DungeonClassInMemory.hasUserClass(player.getUniqueId())) {
                Message.Chat.send(player, "&4Voce nao esta em uma classe.");
                return false;
            }
            DungeonClassInMemory.removeUserClass(player.getUniqueId());
            Message.Chat.send(player, "&aVoce saiu da classe.");
            return false;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command
            command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("entrar");
            arguments.add("atributos");
            arguments.addAll(DungeonClassInMemory.getAllClassesName());
            return arguments;
        }
        return List.of();
    }
}
