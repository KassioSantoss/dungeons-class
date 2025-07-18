package brcomkassin.dungeonsClass.api;

import brcomkassin.dungeonsClass.data.cache.DungeonClassInMemory;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import brcomkassin.dungeonsClass.internal.DungeonClassProvider;
import brcomkassin.dungeonsClass.internal.manager.DungeonClassManager;
import brcomkassin.dungeonsClass.attribute.gui.AttributeGUI;
import brcomkassin.dungeonsClass.internal.manager.PlayerAttributeManager;
import brcomkassin.dungeonsClass.internal.manager.PlayerClassManager;

/**
 * Classe principal da API do sistema de classes do Dungeon.
 * Fornece acesso centralizado a todos os serviços e gerenciadores do sistema.
 * <p>
 * Padrão: Singleton (uma única instância por plugin)
 */
public class DungeonClassAPI {
    private final DungeonClassProvider provider;
    private static DungeonClassAPI INSTANCE;

    /**
     * Construtor privado para garantir o padrão Singleton.
     * Inicializa o provedor de serviços.
     */
    private DungeonClassAPI(DungeonClassProvider provider) {
        this.provider = provider;
    }

    /**
     * Obtém a instância única da API.
     * Uso: DungeonClassAPI.get().métodoDesejado()
     */
    public static void create(DungeonClassProvider provider) {
        if (INSTANCE == null) {
            INSTANCE = new DungeonClassAPI(provider);
        }
    }

    public static DungeonClassAPI get() {
        return INSTANCE;
    }

    /**
     * Obtém o provedor central de dependências.
     * Use este método quando precisar acessar diretamente o Provider
     * para configurações avançadas.
     *
     * @return Instância do DungeonClassProvider
     */
    public DungeonClassProvider getProvider() {
        return provider;
    }

    /**
     * Obtém o gerenciador de classes dos jogadores.
     * Responsável por:
     * - Atribuir/remover classes de jogadores
     * - Verificar classe atual do jogador
     * - Gerenciar instâncias UserClass
     *
     * @return PlayerClassManager
     */
    public PlayerClassManager getPlayerClassManager() {
        return provider.getPlayerClassManager();
    }

    /**
     * Obtém o gerenciador de classes do dungeon.
     * Responsável por:
     * - Carregar/recuperar classes pré-definidas
     * - Verificar existência de classes
     * - Listar todas as classes disponíveis
     *
     * @return DungeonClassManager
     */
    public DungeonClassManager getDungeonClassManager() {
        return provider.getDungeonClassManager();
    }

    /**
     * Obtém a interface gráfica de atributos.
     * Use para abrir menus de atributos para jogadores.
     *
     * @return AttributeGUI
     */
    public AttributeGUI getAttributeGUI() {
        return provider.getAttributeGUI();
    }

    /**
     * Obtém o gerenciador de atributos dos jogadores.
     * Responsável por:
     * - Modificar valores de atributos
     * - Aplicar bônus/malus
     * - Gerenciar progressão de atributos
     *
     * @return PlayerAttributeManager
     */
    public PlayerAttributeManager getPlayerAttributeManager() {
        return provider.getPlayerAttributeManager();
    }

    public MemberClassService getMemberClassService() {
        return provider.getMemberClassService();
    }

}