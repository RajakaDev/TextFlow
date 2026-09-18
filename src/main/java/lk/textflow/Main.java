package lk.textflow;

import lk.textflow.dao.UserDAO;
import lk.textflow.model.User;
import lk.textflow.util.PasswordUtil;
import lk.textflow.service.AuthenticationService;

import java.util.List;

import lk.textflow.dao.AttendanceDAO;
import lk.textflow.model.Attendance;

import java.time.LocalDate;
import java.time.LocalTime;

import lk.textflow.ui.LoginFrame;


public class Main {

    public static void main(String[] args) {

        User user = new User();

        user.setName("Hash Test User");
        user.setUsername("hashtest");
        user.setPasswordHash("test123");
        user.setRole("EMPLOYEE");
        user.setPosition("Cashier");
        user.setContactNumber("0771234567");
        user.setStatus("ACTIVE");



        AttendanceDAO attendanceDAO = new AttendanceDAO();

        List<Attendance> attendanceList = attendanceDAO.getAllAttendance();

        for (Attendance attendance : attendanceList) {

            System.out.println(
                    attendance.getAttendanceId() + " | " +
                            attendance.getUserId() + " | " +
                            attendance.getAttendanceDate() + " | " +
                            attendance.getTimeIn() + " | " +
                            attendance.getTimeOut() + " | " +
                            attendance.getStatus()
            );
        }


        LoginFrame loginFrame = new LoginFrame();

        loginFrame.setVisible(true);

    }
}