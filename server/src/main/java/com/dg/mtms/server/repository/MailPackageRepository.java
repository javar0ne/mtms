package com.dg.mtms.server.repository;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.db.DB;
import com.dg.mtms.server.model.MailPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

public class MailPackageRepository extends Singleton<MailPackageRepository> {
    private MailPackageRepository() {}

    public static void createInstance() {
        addInstance(new MailPackageRepository());
    }

    public static MailPackageRepository getInstance() {
        return Singleton.getInstance(MailPackageRepository.class);
    }

    public MailPackage insertPackage(MailPackage mailPackage) {
        String sql = new StringJoiner(" ")
            .add("INSERT INTO")
            .add("MAIL_PACKAGE")
            .add("(receiver, address, weight, user_id) VALUES (?, ?, ?, ?)")
            .toString();

        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, mailPackage.getReceiver());
            preparedStatement.setString(2, mailPackage.getAddress());
            preparedStatement.setDouble(3, mailPackage.getWeight());
            preparedStatement.setLong(4, mailPackage.getUserId());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                mailPackage.setId(generatedKeys.getLong(1));
            }

            return mailPackage;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
