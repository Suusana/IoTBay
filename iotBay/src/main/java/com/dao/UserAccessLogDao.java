package com.dao;

import com.bean.UserAccessLog;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class UserAccessLogDao {
    private final Connection connection;

    public UserAccessLogDao(Connection connection) {
        this.connection = connection;
    }

    // Create log - time is generated in database
    public int logLogin(int userId, String userType) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO UserAccessLog " +
                "(user_id, user_type) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, userType);
        preparedStatement.executeUpdate();

        // Return autogenerated id for log
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    // Update log to include logout time
    public void logLogout(int userAccessLogId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE UserAccessLog SET " +
                "logout_time = CURRENT_TIMESTAMP WHERE user_access_log_id = ?");
        preparedStatement.setInt(1, userAccessLogId);
        preparedStatement.executeUpdate();
    }

    // Read Logs for a given user
    public LinkedList<UserAccessLog> getLogsByUser(int userId, String userType) throws SQLException {
        LinkedList<UserAccessLog> userAccessLogs = new LinkedList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM UserAccessLog " +
                "WHERE user_id = ? AND user_type = ? ORDER BY login_time DESC");
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, userType);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            UserAccessLog accessLog = new UserAccessLog();
            accessLog.setUserAccessLogId(resultSet.getInt("user_access_log_id"));
            accessLog.setUserId(resultSet.getInt("user_id"));
            accessLog.setUserType(resultSet.getString("user_type"));
            accessLog.setLoginTime(resultSet.getTimestamp("login_time").toLocalDateTime());

            Timestamp logoutTimeStamp = resultSet.getTimestamp("logout_time");
            if (logoutTimeStamp != null ) {
                accessLog.setLogoutTime(logoutTimeStamp.toLocalDateTime());
            } else {
                accessLog.setLogoutTime(null);
            }
            userAccessLogs.add(accessLog);
        }
        return userAccessLogs;
    }

    // Read logs between specific dates for a user
    public LinkedList<UserAccessLog> getLogsBetweenDate(int userId, String userType, Timestamp startDate, Timestamp endDate) throws SQLException {
        LinkedList<UserAccessLog> userAccessLogs = new LinkedList<>();
        String startDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDate);
        String endDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endDate);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM UserAccessLog" +
                " WHERE user_id = ? AND user_type = ? AND login_time BETWEEN ? AND ? ORDER BY login_time DESC");

        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, userType);
        preparedStatement.setString(3, startDateStr);
        preparedStatement.setString(4, endDateStr);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            UserAccessLog accessLog = new UserAccessLog();
            accessLog.setUserAccessLogId(resultSet.getInt("user_access_log_id"));
            accessLog.setUserId(resultSet.getInt("user_id"));
            accessLog.setUserType(resultSet.getString("user_type"));

            String loginTimeStr = resultSet.getString("login_time");
            if (loginTimeStr != null && !loginTimeStr.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime loginTime = LocalDateTime.parse(loginTimeStr, formatter);
                accessLog.setLoginTime(loginTime);
            }

            String logoutTimeStr = resultSet.getString("logout_time");
            if (logoutTimeStr != null && !logoutTimeStr.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime logoutTime = LocalDateTime.parse(logoutTimeStr, formatter);
                accessLog.setLogoutTime(logoutTime);
            }
            userAccessLogs.add(accessLog);
        }
        return userAccessLogs;
    }

    // User cannot change or delete logs
}
