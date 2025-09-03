package com.dg.mtms.server.repository;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.db.DB;
import com.dg.mtms.server.exception.UsernameAlreadyExistsException;
import com.dg.mtms.server.model.User;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.StringJoiner;

public class UserRepository extends Singleton<UserRepository> {
    public static void createInstance() {
        addInstance(new UserRepository());
    }

    public static UserRepository getInstance() {
        return Singleton.getInstance(UserRepository.class);
    }

    public Optional<User> findByUsername(String username) {
        String sql = new StringJoiner(" ")
            .add("SELECT ID, USERNAME FROM")
            .add("USER")
            .add("WHERE USERNAME = ?")
            .toString();

        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return Optional.empty();

            User user = new User();
            user.setId(resultSet.getLong("ID"));
            user.setUsername(resultSet.getString("USERNAME"));

            return Optional.of(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User save(User user) {
        String sql = new StringJoiner(" ")
            .add("INSERT INTO")
            .add("USER")
            .add("(username) VALUES (?)")
            .toString();

        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }

            return user;
        } catch (SQLiteException e) {
            if(e.getResultCode().code == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE.code) {
                throw new UsernameAlreadyExistsException(user.getUsername());
            } else {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
