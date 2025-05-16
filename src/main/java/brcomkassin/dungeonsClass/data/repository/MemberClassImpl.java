package brcomkassin.dungeonsClass.data.repository;

import brcomkassin.dungeonsClass.data.model.MemberClass;
import brcomkassin.dungeonsClass.utils.ColoredLogger;
import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;
import java.util.Optional;

public class MemberClassImpl extends MemberClassRepository {

    private final Gson gson = new Gson();

    public MemberClassImpl(HikariDataSource source) {
        super(source);
    }

    @Override
    public void init() {
        try (var connection = source.getConnection();
             var statement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS member_class (" +
                             "id VARCHAR(36) PRIMARY KEY," +
                             "data TEXT NOT NULL)"
             )) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void merge(MemberClass memberClass) {
        String json = gson.toJson(memberClass);
        try (var connection = source.getConnection();
             var statement = connection.prepareStatement(
                     "REPLACE INTO member_class (id, data) VALUES (?, ?)"
             )) {
            statement.setString(1, memberClass.getClasse().getName());
            statement.setString(2, json);
            statement.executeUpdate();
        } catch (SQLException e) {
            ColoredLogger.error("Erro ao salvar member class: " + memberClass.getClasse().getName());
        }
    }

    @Override
    public Optional<MemberClass> find(String id) {
        try (var connection = source.getConnection();
             var statement = connection.prepareStatement(
                     "SELECT data FROM member_class WHERE id = ?"
             )) {
            statement.setString(1, id);
            try (var result = statement.executeQuery()) {
                if (result.next()) {
                    String json = result.getString("data");
                    MemberClass memberClass = gson.fromJson(json, MemberClass.class);

                    if (memberClass == null) {
                        ColoredLogger.error("Erro ao buscar member class: " + id);
                        return Optional.empty();
                    }
                    ColoredLogger.info("Member class encontrada com sucesso!");
                    return Optional.of(memberClass);
                }
            }
        } catch (SQLException e) {
            ColoredLogger.error("Erro ao buscar member class: " + id);
        }
        return Optional.empty();
    }

    @Override
    public void remove(MemberClass member) {
        try (var connection = source.getConnection();
             var statement = connection.prepareStatement(
                     "DELETE FROM member_class WHERE id = ?"
             )) {
            statement.setString(1, member.getClasse().getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            ColoredLogger.error("Erro ao remover member class: " + member.getClasse().getName());
        }
    }
}
