package lk.textflow.ui;

import lk.textflow.dao.AttendanceDAO;
import lk.textflow.model.Attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceManagementFrame
        extends JFrame {

    private JTextField userIdField;
    private JTextField dateField;
    private JTextField timeInField;
    private JTextField timeOutField;

    private JComboBox<String> statusComboBox;

    private JTextField searchDateField;

    private JTable attendanceTable;

    private DefaultTableModel tableModel;

    private int selectedUserId;

    private boolean canManageAttendance;


    public AttendanceManagementFrame(
            int userId,
            boolean canManageAttendance
    ) {

        this.selectedUserId =
                userId;

        this.canManageAttendance =
                canManageAttendance;


        setTitle(
                "TextFlow - Attendance Management"
        );

        setSize(950, 600);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );


        // =====================================
        // TITLE
        // =====================================

        JLabel titleLabel =
                new JLabel(
                        "Attendance Management",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );


        JLabel permissionLabel =
                new JLabel(
                        canManageAttendance
                                ? "Attendance Management Access"
                                : "Attendance View Only",
                        SwingConstants.CENTER
                );


        // =====================================
        // FORM
        // =====================================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                10
                        )
                );


        userIdField =
                new JTextField(
                        String.valueOf(
                                userId
                        )
                );

        userIdField.setEditable(false);


        dateField =
                new JTextField();

        timeInField =
                new JTextField();

        timeOutField =
                new JTextField();


        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "PRESENT",
                                "ABSENT",
                                "HALF_DAY"
                        }
                );


        formPanel.add(
                new JLabel("User ID:")
        );

        formPanel.add(userIdField);


        formPanel.add(
                new JLabel(
                        "Date (YYYY-MM-DD):"
                )
        );

        formPanel.add(dateField);


        formPanel.add(
                new JLabel(
                        "Time In (HH:MM):"
                )
        );

        formPanel.add(timeInField);


        formPanel.add(
                new JLabel(
                        "Time Out (HH:MM):"
                )
        );

        formPanel.add(timeOutField);


        formPanel.add(
                new JLabel("Status:")
        );

        formPanel.add(statusComboBox);


        // =====================================
        // BUTTONS
        // =====================================

        JButton addButton =
                new JButton(
                        "Add Attendance"
                );

        JButton updateButton =
                new JButton(
                        "Update Attendance"
                );

        JButton deleteButton =
                new JButton(
                        "Delete Attendance"
                );

        JButton clearButton =
                new JButton("Clear");

        JButton refreshButton =
                new JButton("Refresh");

        JButton closeButton =
                new JButton("Close");


        JPanel buttonPanel =
                new JPanel();


        buttonPanel.add(addButton);

        buttonPanel.add(updateButton);

        buttonPanel.add(deleteButton);

        buttonPanel.add(clearButton);

        buttonPanel.add(refreshButton);

        buttonPanel.add(closeButton);


        // Owner/Manager may change attendance.
        // Employee is view-only.
        addButton.setEnabled(
                canManageAttendance
        );

        updateButton.setEnabled(
                canManageAttendance
        );

        deleteButton.setEnabled(
                canManageAttendance
        );


        dateField.setEditable(
                canManageAttendance
        );

        timeInField.setEditable(
                canManageAttendance
        );

        timeOutField.setEditable(
                canManageAttendance
        );

        statusComboBox.setEnabled(
                canManageAttendance
        );


        // =====================================
        // SEARCH
        // =====================================

        searchDateField =
                new JTextField(12);


        JButton searchButton =
                new JButton(
                        "Search Date"
                );


        JPanel searchPanel =
                new JPanel();


        searchPanel.add(
                new JLabel(
                        "Search Date (YYYY-MM-DD):"
                )
        );

        searchPanel.add(
                searchDateField
        );

        searchPanel.add(
                searchButton
        );


        // =====================================
        // TOP PANEL
        // =====================================

        JPanel topPanel =
                new JPanel();


        topPanel.setLayout(
                new BoxLayout(
                        topPanel,
                        BoxLayout.Y_AXIS
                )
        );


        topPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        10,
                        20
                )
        );


        topPanel.add(titleLabel);

        topPanel.add(
                Box.createVerticalStrut(5)
        );

        topPanel.add(permissionLabel);

        topPanel.add(
                Box.createVerticalStrut(15)
        );

        topPanel.add(formPanel);

        topPanel.add(
                Box.createVerticalStrut(10)
        );

        topPanel.add(buttonPanel);

        topPanel.add(
                Box.createVerticalStrut(10)
        );

        topPanel.add(searchPanel);


        add(
                topPanel,
                BorderLayout.NORTH
        );


        // =====================================
        // TABLE
        // =====================================

        String[] columns = {

                "Attendance ID",
                "User ID",
                "Date",
                "Time In",
                "Time Out",
                "Status"

        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        attendanceTable =
                new JTable(
                        tableModel
                );


        attendanceTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        attendanceTable
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =====================================
        // TABLE ROW CLICK
        // =====================================

        attendanceTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                int row =
                                        attendanceTable
                                                .getSelectedRow();


                                if (row != -1) {

                                    dateField.setText(
                                            value(
                                                    row,
                                                    2
                                            )
                                    );


                                    timeInField.setText(
                                            value(
                                                    row,
                                                    3
                                            )
                                    );


                                    timeOutField.setText(
                                            value(
                                                    row,
                                                    4
                                            )
                                    );


                                    statusComboBox
                                            .setSelectedItem(
                                                    value(
                                                            row,
                                                            5
                                                    )
                                            );
                                }
                            }
                        }
                );


        // =====================================
        // ADD ATTENDANCE
        // =====================================

        addButton.addActionListener(e -> {

            if (!canManageAttendance) {

                showMessage(
                        "You do not have permission to add attendance."
                );

                return;
            }


            Attendance attendance =
                    buildAttendanceFromForm();


            if (attendance == null) {
                return;
            }


            AttendanceDAO dao =
                    new AttendanceDAO();


            List<Attendance> records =
                    dao.getAllAttendance();


            // Prevent same user + same date twice
            for (Attendance existing
                    : records) {

                if (existing.getUserId()
                        == selectedUserId

                        && existing
                        .getAttendanceDate()
                        .equals(
                                attendance
                                        .getAttendanceDate()
                        )) {

                    showMessage(
                            "Attendance already exists for this user on this date."
                    );

                    return;
                }
            }


            boolean added =
                    dao.addAttendance(
                            attendance
                    );


            if (added) {

                showMessage(
                        "Attendance added successfully!"
                );

                clearForm();

                loadAttendance();

            } else {

                showMessage(
                        "Attendance could not be added."
                );
            }
        });


        // =====================================
        // UPDATE ATTENDANCE
        // =====================================

        updateButton.addActionListener(e -> {

            if (!canManageAttendance) {

                showMessage(
                        "You do not have permission to update attendance."
                );

                return;
            }


            int row =
                    attendanceTable
                            .getSelectedRow();


            if (row == -1) {

                showMessage(
                        "Please select an attendance record first."
                );

                return;
            }


            Attendance attendance =
                    buildAttendanceFromForm();


            if (attendance == null) {
                return;
            }


            int attendanceId =
                    Integer.parseInt(
                            value(
                                    row,
                                    0
                            )
                    );


            attendance.setAttendanceId(
                    attendanceId
            );


            AttendanceDAO dao =
                    new AttendanceDAO();


            boolean updated =
                    dao.updateAttendance(
                            attendance
                    );


            if (updated) {

                showMessage(
                        "Attendance updated successfully!"
                );

                clearForm();

                loadAttendance();

            } else {

                showMessage(
                        "Attendance could not be updated."
                );
            }
        });


        // =====================================
        // DELETE ATTENDANCE
        // =====================================

        deleteButton.addActionListener(e -> {

            if (!canManageAttendance) {

                showMessage(
                        "You do not have permission to delete attendance."
                );

                return;
            }


            int row =
                    attendanceTable
                            .getSelectedRow();


            if (row == -1) {

                showMessage(
                        "Please select an attendance record first."
                );

                return;
            }


            int attendanceId =
                    Integer.parseInt(
                            value(
                                    row,
                                    0
                            )
                    );


            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete this attendance record?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );


            if (confirm
                    != JOptionPane.YES_OPTION) {

                return;
            }


            AttendanceDAO dao =
                    new AttendanceDAO();


            boolean deleted =
                    dao.deleteAttendance(
                            attendanceId
                    );


            if (deleted) {

                showMessage(
                        "Attendance deleted successfully!"
                );

                clearForm();

                loadAttendance();

            } else {

                showMessage(
                        "Attendance could not be deleted."
                );
            }
        });


        // =====================================
        // SEARCH
        // =====================================

        searchButton.addActionListener(e -> {

            String text =
                    searchDateField
                            .getText()
                            .trim();


            if (text.isEmpty()) {

                loadAttendance();

                return;
            }


            try {

                LocalDate date =
                        LocalDate.parse(
                                text
                        );


                loadAttendanceByDate(
                        date
                );


            } catch (Exception ex) {

                showMessage(
                        "Enter date as YYYY-MM-DD."
                );
            }
        });


        // =====================================
        // CLEAR
        // =====================================

        clearButton.addActionListener(
                e -> clearForm()
        );


        // =====================================
        // REFRESH
        // =====================================

        refreshButton.addActionListener(e -> {

            searchDateField.setText("");

            clearForm();

            loadAttendance();
        });


        // =====================================
        // CLOSE
        // =====================================

        closeButton.addActionListener(
                e -> dispose()
        );


        loadAttendance();
    }


    // =====================================
    // BUILD ATTENDANCE OBJECT
    // =====================================

    private Attendance
    buildAttendanceFromForm() {

        String dateText =
                dateField
                        .getText()
                        .trim();

        String timeInText =
                timeInField
                        .getText()
                        .trim();

        String timeOutText =
                timeOutField
                        .getText()
                        .trim();


        if (dateText.isEmpty()) {

            showMessage(
                    "Date is required."
            );

            return null;
        }


        try {

            LocalDate date =
                    LocalDate.parse(
                            dateText
                    );


            LocalTime timeIn = null;

            LocalTime timeOut = null;


            if (!timeInText.isEmpty()) {

                timeIn =
                        LocalTime.parse(
                                timeInText
                        );
            }


            if (!timeOutText.isEmpty()) {

                timeOut =
                        LocalTime.parse(
                                timeOutText
                        );
            }


            if (timeIn != null
                    && timeOut != null
                    && timeOut.isBefore(
                    timeIn
            )) {

                showMessage(
                        "Time Out cannot be before Time In."
                );

                return null;
            }


            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();


            // ABSENT means no working times
            if ("ABSENT"
                    .equalsIgnoreCase(
                            status
                    )) {

                timeIn = null;

                timeOut = null;
            }


            Attendance attendance =
                    new Attendance();


            attendance.setUserId(
                    selectedUserId
            );

            attendance.setAttendanceDate(
                    date
            );

            attendance.setTimeIn(
                    timeIn
            );

            attendance.setTimeOut(
                    timeOut
            );

            attendance.setStatus(
                    status
            );


            return attendance;


        } catch (Exception ex) {

            showMessage(
                    "Invalid values.\n"
                            + "Date: YYYY-MM-DD\n"
                            + "Time: HH:MM"
            );

            return null;
        }
    }


    // =====================================
    // LOAD SELECTED USER ATTENDANCE
    // =====================================

    private void loadAttendance() {

        AttendanceDAO dao =
                new AttendanceDAO();


        List<Attendance> list =
                dao.getAllAttendance();


        tableModel.setRowCount(0);


        for (Attendance attendance
                : list) {

            if (attendance.getUserId()
                    == selectedUserId) {

                addAttendanceToTable(
                        attendance
                );
            }
        }
    }


    // =====================================
    // SEARCH BY DATE
    // =====================================

    private void loadAttendanceByDate(
            LocalDate date
    ) {

        AttendanceDAO dao =
                new AttendanceDAO();


        List<Attendance> list =
                dao.getAllAttendance();


        tableModel.setRowCount(0);


        for (Attendance attendance
                : list) {

            if (attendance.getUserId()
                    == selectedUserId

                    && attendance
                    .getAttendanceDate()
                    .equals(date)) {

                addAttendanceToTable(
                        attendance
                );
            }
        }
    }


    private void addAttendanceToTable(
            Attendance attendance
    ) {

        Object[] row = {

                attendance.getAttendanceId(),

                attendance.getUserId(),

                attendance.getAttendanceDate(),

                attendance.getTimeIn(),

                attendance.getTimeOut(),

                attendance.getStatus()

        };


        tableModel.addRow(row);
    }


    private String value(
            int row,
            int column
    ) {

        Object value =
                tableModel.getValueAt(
                        row,
                        column
                );


        return value == null
                ? ""
                : value.toString();
    }


    private void clearForm() {

        userIdField.setText(
                String.valueOf(
                        selectedUserId
                )
        );

        dateField.setText("");

        timeInField.setText("");

        timeOutField.setText("");

        statusComboBox.setSelectedIndex(0);

        attendanceTable.clearSelection();
    }


    private void showMessage(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message
        );
    }
}