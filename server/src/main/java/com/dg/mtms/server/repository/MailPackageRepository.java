package com.dg.mtms.server.repository;

import com.dg.mtms.server.model.Singleton;
import com.dg.mtms.server.db.DB;
import com.dg.mtms.server.model.Dimension;
import com.dg.mtms.server.model.MailPackage;
import com.dg.mtms.common.model.PackageStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class MailPackageRepository extends Singleton<MailPackageRepository> {
    private MailPackageRepository() {}

    public static void createInstance() {
        addInstance(new MailPackageRepository());
    }

    public static MailPackageRepository getInstance() {
        return Singleton.getInstance(MailPackageRepository.class);
    }

    public MailPackage save(MailPackage mailPackage) {
        String sql = new StringJoiner(" ")
            .add("INSERT INTO")
            .add("MAIL_PACKAGE")
            .add("(RECEIVER, ADDRESS, PACKAGE_LENGTH, PACKAGE_WIDTH, PACKAGE_HEIGHT, PACKAGE_WEIGHT, STATUS, USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
            .toString();

        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, mailPackage.getReceiver());
            preparedStatement.setString(2, mailPackage.getAddress());
            preparedStatement.setDouble(3, mailPackage.getDimension().length());
            preparedStatement.setDouble(4, mailPackage.getDimension().width());
            preparedStatement.setDouble(5, mailPackage.getDimension().height());
            preparedStatement.setDouble(6, mailPackage.getWeight());
            preparedStatement.setString(7, mailPackage.getStatus().name());
            preparedStatement.setLong(8, mailPackage.getUserId());
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

    public Optional<MailPackage> findById(Long id) {
        String sql = new StringJoiner(" ")
            .add("SELECT ID, RECEIVER, ADDRESS, PACKAGE_LENGTH, PACKAGE_WIDTH, PACKAGE_HEIGHT, PACKAGE_WEIGHT, STATUS, USER_ID FROM")
            .add("MAIL_PACKAGE")
            .add("WHERE ID = ?")
            .toString();

        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return Optional.empty();

            MailPackage mailPackage = new MailPackage();
            mailPackage.setId(resultSet.getLong("ID"));
            mailPackage.setReceiver(resultSet.getString("RECEIVER"));
            mailPackage.setAddress(resultSet.getString("ADDRESS"));
            mailPackage.setDimension(new Dimension(
                    resultSet.getDouble("PACKAGE_LENGTH"),
                    resultSet.getDouble("PACKAGE_WIDTH"),
                    resultSet.getDouble("PACKAGE_HEIGHT")
            ));
            mailPackage.setWeight(resultSet.getDouble("PACKAGE_WEIGHT"));
            mailPackage.setStatus(PackageStatus.valueOf(resultSet.getString("STATUS")));
            mailPackage.setUserId(resultSet.getLong("USER_ID"));

            return Optional.of(mailPackage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatus(Long id, String status) {
        String sql = new StringJoiner(" ")
            .add("UPDATE MAIL_PACKAGE")
            .add("SET STATUS = ?")
            .add("WHERE ID = ?")
            .toString();

        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, PackageStatus.valueOf(status).name());
            preparedStatement.setLong(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Double> findFee(Double weight, Dimension dimension) {
        String sql = new StringJoiner(" ")
            .add("SELECT FEE FROM")
            .add("SHIPPING_FEE")
            .add("WHERE PACKAGE_WEIGHT_MIN <= ? AND PACKAGE_WEIGHT_MAX >= ? AND " +
                    "PACKAGE_LENGTH_MIN <= ? AND PACKAGE_LENGTH_MAX >= ? AND " +
                    "PACKAGE_WIDTH_MIN <= ? AND PACKAGE_WIDTH_MAX >= ? AND " +
                    "PACKAGE_HEIGHT_MIN <= ? AND PACKAGE_HEIGHT_MAX >= ?")
            .toString();

        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setDouble(1, weight);
            preparedStatement.setDouble(2, weight);
            preparedStatement.setDouble(3, dimension.length());
            preparedStatement.setDouble(4, dimension.length());
            preparedStatement.setDouble(5, dimension.width());
            preparedStatement.setDouble(6, dimension.width());
            preparedStatement.setDouble(7, dimension.height());
            preparedStatement.setDouble(8, dimension.height());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return Optional.empty();

            return Optional.of(resultSet.getDouble("FEE"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MailPackage> findPackages(Long userId) {
        String sql = new StringJoiner(" ")
            .add("SELECT ID, RECEIVER, ADDRESS, PACKAGE_LENGTH, PACKAGE_WIDTH, PACKAGE_HEIGHT, PACKAGE_WEIGHT, STATUS, USER_ID FROM")
            .add("MAIL_PACKAGE")
            .add("WHERE USER_ID = ?")
            .toString();

        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<MailPackage> mailPackages = new ArrayList<>();
            while(resultSet.next()) {
                MailPackage mailPackage = new MailPackage();
                mailPackage.setId(resultSet.getLong("ID"));
                mailPackage.setReceiver(resultSet.getString("RECEIVER"));
                mailPackage.setAddress(resultSet.getString("ADDRESS"));
                mailPackage.setDimension(new Dimension(
                        resultSet.getDouble("PACKAGE_LENGTH"),
                        resultSet.getDouble("PACKAGE_WIDTH"),
                        resultSet.getDouble("PACKAGE_HEIGHT")
                        ));
                mailPackage.setWeight(resultSet.getDouble("PACKAGE_WEIGHT"));
                mailPackage.setStatus(PackageStatus.valueOf(resultSet.getString("STATUS")));
                mailPackage.setUserId(resultSet.getLong("USER_ID"));

                mailPackages.add(mailPackage);
            }

            return mailPackages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
