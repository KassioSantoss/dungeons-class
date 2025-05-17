package brcomkassin.dungeonsClass.commands;

import brcomkassin.dungeonsClass.data.model.DungeonClass;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import brcomkassin.dungeonsClass.internal.DungeonClassProvider;
import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.internal.manager.PlayerAttributeManager;
import brcomkassin.dungeonsClass.attribute.gui.AttributeGUI;
import brcomkassin.dungeonsClass.data.model.MemberClass;
import brcomkassin.dungeonsClass.internal.manager.DungeonClassManager;
import brcomkassin.dungeonsClass.internal.manager.PlayerClassManager;
import brcomkassin.dungeonsClass.utils.DecimalFormatUtil;
import brcomkassin.dungeonsClass.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DungeonClassCommand implements CommandExecutor, TabExecutor {

    private final AttributeGUI attributeGUI;
    private final PlayerAttributeManager playerAttributeManager;
    private final PlayerClassManager playerClassManager;
    private final DungeonClassManager classManager;
    private final MemberClassService service;

    public DungeonClassCommand(DungeonClassProvider provider) {
        this.service = provider.getMemberClassService();
        this.attributeGUI = provider.getAttributeGUI();
        this.playerAttributeManager = provider.getPlayerAttributeManager();
        this.playerClassManager = provider.getPlayerClassManager();
        this.classManager = provider.getDungeonClassManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return true;

        if (args.length == 0) {
            player.sendMessage("Use /classes entrar <classe>");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "pontos" -> {
                if (args.length == 1) {
                    if (service.findById(player.getUniqueId()).isEmpty()) {
                        Message.Chat.send(player, "&4Você não está em uma classe.");
                        return false;
                    }

                    MemberClass memberClass = getMemberClass(player);
                    int attributePoints = memberClass.getAttributePoints();
                    Message.Chat.send(player, "Pontos de atributos: &e" + attributePoints);
                    return true;
                }

                if (args.length >= 3 && (args[1].equalsIgnoreCase("add") ||
                        args[1].equalsIgnoreCase("set"))) {
                    String action = args[1].toLowerCase();

                    int amount;
                    try {
                        amount = Integer.parseInt(args[2]);
                        if (amount < 0) {
                            Message.Chat.send(player, "&cA quantia deve ser positiva.");
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        Message.Chat.send(player, "&cQuantia inválida: " + args[2]);
                        return false;
                    }

                    Player target = player;
                    if (args.length >= 4) {
                        target = Bukkit.getPlayer(args[3]);
                        if (target == null || !target.isOnline()) {
                            Message.Chat.send(player, "&cJogador &e" + args[3] + " &cnão está online.");
                            return false;
                        }
                    }

                    if (service.findById(target.getUniqueId()).isEmpty()) {
                        Message.Chat.send(player, "&4O jogador não possui uma classe.");
                        return false;
                    }

                    MemberClass targetClass = getMemberClass(player);

                    if (action.equals("add")) {
                        targetClass.addAttributePoints(amount);
                        service.save(targetClass);
                        Message.Chat.send(player, "&aAdicionado &e" + amount + " &aponto(s) ao jogador &e" + target.getName());
                    } else if (action.equals("set")) {
                        targetClass.setAttributePoints(amount);
                        service.save(targetClass);
                        Message.Chat.send(player, "&aSetado para &e" + amount + " &aponto(s) o jogador &e" + target.getName());
                    }

                    if (!target.getName().equalsIgnoreCase(player.getName())) {
                        Message.Chat.send(target, "&eSeus pontos de atributos foram " +
                                (action.equals("set") ? "setados" : "aumentados") +
                                " para &a" + targetClass.getAttributePoints());
                    }

                    return true;
                }

                Message.Chat.send(player, "&cUso correto: /classe pontos <add/set> <quantia> [jogador]");
                return false;
            }
            case "atributos" -> {
                if (service.findById(player.getUniqueId()).isEmpty()) {
                    Message.Chat.send(player, "&4Você não está em uma classe.");
                    return false;
                }

                MemberClass memberClass = getMemberClass(player);

                if (args.length >= 2 && (args[1].equalsIgnoreCase("add")
                        || args[1].equalsIgnoreCase("set"))) {

                    if (args.length < 4) {
                        Message.Chat.send(player, "&cUso correto: /classes atributos <add/set> <atributo> <valor>");
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
                        Attribute attribute = memberClass.getAttribute(type);

                        if (attribute == null) {
                            Message.Chat.send(player, "&4Atributo não encontrado na sua classe.");
                            return false;
                        }

                        boolean success = false;

                        if (args[1].equalsIgnoreCase("set")) {
                            success = playerAttributeManager.set(player, type, value);
                        } else {
                            success = playerAttributeManager.add(player, type, value);
                        }
                        if (!success) {
                            Message.Chat.send(player, "&4Falha ao " + (args[1].equalsIgnoreCase("set") ? "setar" : "adicionar") + " o atributo.");
                        }

                    } catch (NumberFormatException e) {
                        Message.Chat.send(player, "&cValor inválido: " + args[3]);
                    }
                    return true;
                }
                if (args.length >= 2 && args[1].equalsIgnoreCase("menu")) {
                    attributeGUI.openInitialInventory(player);
                    return true;
                }

                Message.Chat.send(player, "&aAtributos da sua classe: &6" + memberClass.getDungeonClass().getName());
                for (Attribute attribute : memberClass.getAllAttributes()) {
                    Message.Chat.send(player, "&e" + attribute.getName() + ": &6" + DecimalFormatUtil.format(attribute.getCurrentValue())
                            + " &a| " + DecimalFormatUtil.format(attribute.getBaseValue()));
                }
                return true;
            }

            case "entrar" -> {
                if (args.length < 2) {
                    Message.Chat.send(player, "&cUse /classes entrar <classe>");
                    return false;
                }

                if (service.findById(player.getUniqueId()).isPresent()) {
                    Message.Chat.send(player, "&4Você já está em uma classe.");
                    return false;
                }

                if (!classManager.containsClass(args[1])) {
                    Message.Chat.send(player, "&4Classe &6" + args[1] + " &4não encontrada.");
                    return false;
                }

                MemberClass memberClass = new MemberClass(player.getUniqueId(), args[1]);
                service.save(memberClass);
                Message.Chat.send(player, "&aVocê entrou na classe: &6" + args[1]);
                return true;
            }

            case "sair" -> {
                if (service.findById(player.getUniqueId()).isEmpty()) {
                    Message.Chat.send(player, "&4Você não está em uma classe.");
                    return false;
                }

                MemberClass memberClass = getMemberClass(player);
                service.remove(memberClass);
                Message.Chat.send(player, "&aVocê saiu da classe.");
                return true;
            }
            default -> {
                Message.Chat.send(player, "&cComando desconhecido.");
                return false;
            }
        }
    }

    private MemberClass getMemberClass(Player player) {
        Optional<MemberClass> optional = service.findById(player.getUniqueId());
        return optional.orElse(null);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1 -> completions.addAll(List.of("entrar", "sair", "atributos", "pontos"));
            case 2 -> {
                if (args[0].equalsIgnoreCase("entrar")) {
                    completions.addAll(classManager.getAllClasses().stream().map(DungeonClass::getName).toList());
                } else if (args[0].equalsIgnoreCase("atributos")) {
                    completions.add("add");
                    completions.add("set");
                    completions.add("menu");
                } else if (args[0].equalsIgnoreCase("pontos")) {
                    completions.add("add");
                    completions.add("set");
                }
            }
            case 3 -> {
                if (args[0].equalsIgnoreCase("atributos")
                        && args[1].equalsIgnoreCase("add")
                        || args[1].equalsIgnoreCase("set")) {
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

