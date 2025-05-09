package brcomkassin.dungeonsClass;

import brcomkassin.dungeonsClass.attribute.AttributeController;
import brcomkassin.dungeonsClass.attribute.DungeonClassInMemory;
import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.user.UserClass;
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

    private final DungeonClassInMemory dungeonClassInMemory;

    public DungeonClassCommand(DungeonClassInMemory dungeonClassInMemory) {
        this.dungeonClassInMemory = dungeonClassInMemory;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return true;

        if (args.length == 0) {
            player.sendMessage("Use /classes entrar <classe>");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "atributos" -> {
                if (!dungeonClassInMemory.hasUserClass(player.getUniqueId())) {
                    Message.Chat.send(player, "&4Você não está em uma classe.");
                    return false;
                }

                UserClass userClass = dungeonClassInMemory.getUserClass(player.getUniqueId());

                if (args.length >= 2 && args[1].equalsIgnoreCase("add")) {
                    if (args.length < 4) {
                        Message.Chat.send(player, "&cUso correto: /classes atributos add <atributo> <valor>");
                        return false;
                    }

                    String key = args[2];
                    AttributeType type = AttributeType.fromKey(key);

                    if (type == null) {
                        Message.Chat.send(player, "&4Atributo: &6" + key + " &4não encontrado.");
                        return false;
                    }

                    try {
                        float value = Float.parseFloat(args[3]);
                        Attribute attribute = userClass.getAttribute(type);
                        if (attribute == null) {
                            Message.Chat.send(player, "&4Atributo não encontrado na sua classe.");
                            return false;
                        }
                        attribute.setBaseValue(value);

                        Message.Chat.send(player, "Mudando atributo para o jogador: " + player.getName());

                        boolean modifier = AttributeController.of().applyAttributeModifier(player, type, attribute.getBaseValue());
                        if (!modifier) {
                            Message.Chat.send(player, "&cAtributo: &6" + type.getKey() + " &a foi alterado para o valor:&6" + value);
                        }
                    } catch (NumberFormatException e) {
                        Message.Chat.send(player, "&cValor inválido: " + args[3]);
                    }
                    return true;
                }

                Message.Chat.send(player, "&aAtributos da sua classe: &6" + userClass.getClasse().getName());
                for (Attribute attribute : userClass.getAllAttributes()) {
                    Message.Chat.send(player, "&e" + attribute.getName() + ": &f" + attribute.getBaseValue());
                }
                return true;
            }

            case "entrar" -> {
                if (args.length < 2) {
                    Message.Chat.send(player, "&cUse /classes entrar <classe>");
                    return false;
                }

                if (dungeonClassInMemory.hasUserClass(player.getUniqueId())) {
                    Message.Chat.send(player, "&4Você já está em uma classe.");
                    return false;
                }

                if (!dungeonClassInMemory.containsClass(args[1])) {
                    Message.Chat.send(player, "&4Classe &6" + args[1] + " &4não encontrada.");
                    return false;
                }

                UserClass userClass = new UserClass(dungeonClassInMemory.getClass(args[1]));
                dungeonClassInMemory.addUserClass(player.getUniqueId(), userClass);
                Message.Chat.send(player, "&aVocê entrou na classe: &6" + args[1]);
                return true;
            }

            case "sair" -> {
                if (!dungeonClassInMemory.hasUserClass(player.getUniqueId())) {
                    Message.Chat.send(player, "&4Você não está em uma classe.");
                    return false;
                }
                dungeonClassInMemory.removeUserClass(player.getUniqueId());
                Message.Chat.send(player, "&aVocê saiu da classe.");
                return true;
            }

            default -> {
                Message.Chat.send(player, "&cComando desconhecido.");
                return false;
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1 -> completions.addAll(List.of("entrar", "sair", "atributos"));
            case 2 -> {
                if (args[0].equalsIgnoreCase("entrar")) {
                    completions.addAll(dungeonClassInMemory.getAllClassesName());
                } else if (args[0].equalsIgnoreCase("atributos")) {
                    completions.add("add");
                }
            }
            case 3 -> {
                if (args[0].equalsIgnoreCase("atributos") && args[1].equalsIgnoreCase("add")) {
                    for (AttributeType type : AttributeType.values()) {
                        completions.add(type.getKey());
                    }
                }
            }
        }

        return completions.stream()
                .filter(c -> c.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .toList();
    }
}

