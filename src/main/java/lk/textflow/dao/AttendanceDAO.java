package lk.textflow.dao;

import lk.textflow.config.DatabaseConnection;
import lk.textflow.model.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public boolean addAttendance(Attendance attendance) {

        String sql = """
            INSERT INTO attendance
            (user_id, attendance_date, time_in, time_out, status)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, attendance.getUserId());
            statement.setDate(
                    2,
                    java.sql.Date.valueOf(attendance.getAttendanceDate())
            );
            statement.setTime(
                    3,
                    java.sql.Time.valueOf(attendance.getTimeIn())
            );
            statement.setTime(
                    4,
                    java.sql.Time.valueOf(attendance.getTimeOut())
            );
            statement.setString(5, attendance.getStatus());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Attendance> getAllAttendance() {

        List<Attendance> attendanceList = new ArrayList<>();

        String sql = "SELECT * FROM attendance";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Attendance attendance = new Attendance();

                attendance.setAttendanceId(
                        resultSet.getInt("attendance_id")
                );

                attendance.setUserId(
                        resultSet.getInt("user_id")
                );

                attendance.setAttendanceDate(
                        resultSet.getDate("attendance_date").toLocalDate()
                );

                java.sql.Time timeIn = resultSet.getTime("time_in");

                if (timeIn != null) {
                    attendance.setTimeIn(timeIn.toLocalTime());
                }

                java.sql.Time timeOut = resultSet.getTime("time_out");

                if (timeOut != null) {
                    attendance.setTimeOut(timeOut.toLocalTime());
                }

                attendance.setStatus(
                        resultSet.getString("status")
                );

                attendance.setCreatedAt(
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );

                attendanceList.add(attendance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attendanceList;
    }

}