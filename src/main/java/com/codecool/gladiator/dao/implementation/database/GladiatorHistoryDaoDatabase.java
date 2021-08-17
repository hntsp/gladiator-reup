package com.codecool.gladiator.dao.implementation.database;

import com.codecool.gladiator.dao.GladiatorHistoryDao;
import com.codecool.gladiator.model.GladiatorHistory;
import com.codecool.gladiator.model.exception.GladiatorHistoryNotFoundException;
import com.codecool.gladiator.model.gladiators.Gladiator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GladiatorHistoryDaoDatabase extends DatabaseConnection implements GladiatorHistoryDao {
    private static final String TABLE_NAME = "gladiator_history";
    private static final String FULL_NAME = "full_name";
    private static final String WINS = "wins";
    private static final String LOSSES = "losses";
    private static final String TOURNAMENT_WINS = "tournament_wins";
    private static final String SCHEMA = String.format(
            "(%s TEXT PRIMARY KEY, %s INTEGER DEFAULT 0, %s INTEGER DEFAULT 0, %s INTEGER DEFAULT 0)",
            FULL_NAME, WINS, LOSSES, TOURNAMENT_WINS
    );

    @Override
    public void saveWin(Gladiator gladiator) {
        incrementOrSaveNew(gladiator, WINS);
    }

    @Override
    public void saveLoss(Gladiator gladiator) {
        incrementOrSaveNew(gladiator, LOSSES);
    }

    @Override
    public void saveTournamentWin(Gladiator gladiator) {
        incrementOrSaveNew(gladiator, TOURNAMENT_WINS);
    }

    @Override
    public GladiatorHistory findBy(Gladiator gladiator) throws GladiatorHistoryNotFoundException {
        String query = String.format(
           "SELECT * FROM %1$s WHERE %2$s = ?",
           TABLE_NAME, FULL_NAME
        );
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, gladiator.getFullName());

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            return new GladiatorHistory(
                    gladiator.getFullName(),
                    resultSet.getInt(WINS),
                    resultSet.getInt(LOSSES),
                    resultSet.getInt(TOURNAMENT_WINS)
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        throw new GladiatorHistoryNotFoundException();
    }

    @Override
    public void remove(Gladiator gladiator) {
        String query = String.format("DELETE FROM %1$s WHERE %2$s = ?", TABLE_NAME, FULL_NAME);
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, gladiator.getFullName());

            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<GladiatorHistory> getAll() {
        List<GladiatorHistory> gladiatorHistories = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_NAME);
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                gladiatorHistories.add(new GladiatorHistory(
                        resultSet.getString(FULL_NAME),
                        resultSet.getInt(WINS),
                        resultSet.getInt(LOSSES),
                        resultSet.getInt(TOURNAMENT_WINS)
                ));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return gladiatorHistories;
    }

    private void incrementOrSaveNew(Gladiator gladiator, String column) {
        createTableIfNotExists(TABLE_NAME, SCHEMA);
        String query = String.format(
                "UPDATE %1$s SET %2$s = %2$s + 1 WHERE %3$s = ?",
                TABLE_NAME, column, FULL_NAME
        );
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, gladiator.getFullName());

            if (statement.executeUpdate() == 0) {
                saveNew(gladiator, column);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void saveNew(Gladiator gladiator, String column) throws SQLException {
        String query = String.format(
                "INSERT INTO %1$s (%2$s, %3$s) VALUES (?, ?)",
                TABLE_NAME, FULL_NAME, column
        );
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, gladiator.getFullName());
        statement.setInt(2, 1);

        statement.execute();
    }
}
