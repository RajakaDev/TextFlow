package lk.textflow.ui;

import lk.textflow.dao.AttendanceDAO;
import lk.textflow.model.Attendance;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceManagementFrame extends JFrame {

    private JTextField userIdField;
    private JTextField dateField;
    private JTextField timeInField;
    private JTextField timeOutField;

    private JComboBox<String> statusComboBox;

    private JTextField searchDateField;

    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    private final int selectedUserId;
    private final boolean canManageAttendance;

    private final Color DARK_BLUE =
            new Color(31, 60, 136);

    private final Color LIGHT_BACKGROUND =
            new Color(245, 247, 250);

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

        setSize(
                1000,
                650
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setLayout(
                new BorderLayout()
        );

        // ==================================================
        // HEADER
        // ==================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                DARK_BLUE
        );

        header.setBorder(
                new EmptyBorder(
                        15,
                        25,
                        15,
                        25
                )
        );

        JLabel title =
                new JLabel(
                        "TEXTFLOW  |  Attendance Management"
                );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        23
                )
        );

        JLabel accessLabel =
                new JLabel(
                        canManageAttendance
                                ? "Management Access"
                                : "View Only"
                );

        accessLabel.setForeground(
                Color.WHITE
        );

        accessLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                accessLabel,
                BorderLayout.EAST
        );

        add(
                header,
                BorderLayout.NORTH
        );

        // ==================================================
        // MAIN
        // ==================================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        mainPanel.setBackground(
                LIGHT_BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        // ==================================================
        // FORM CARD
        // ==================================================

        JPanel formCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        formCard.setBackground(
                Color.WHITE
        );

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        225,
                                        230
                                )
                        ),
                        new EmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        JLabel formTitle =
                new JLabel(
                        "Attendance Details"
                );

        formTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        formTitle.setForeground(
                DARK_BLUE
        );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                4,
                                12,
                                12
                        )
                );

        formPanel.setOpaque(false);

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
                new JLabel(
                        "User ID:"
                )
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
                new JLabel(
                        "Status:"
                )
        );

        formPanel.add(statusComboBox);

        formPanel.add(
                new JLabel("")
        );

        formPanel.add(
                new JLabel("")
        );

        // ==================================================
        // LIGHT BUTTONS
        // ==================================================

        JButton addButton =
                createButton(
                        "Add",
                        new Color(
                                220,
                                245,
                                228
                        ),
                        new Color(
                                34,
                                100,
                                58
                        )
                );

        JButton updateButton =
                createButton(
                        "Update",
                        new Color(
                                219,
                                234,
                                254
                        ),
                        new Color(
                                30,
                                64,
                                175
                        )
                );

        JButton deleteButton =
                createButton(
                        "Delete",
                        new Color(
                                254,
                                226,
                                226
                        ),
                        new Color(
                                153,
                                27,
                                27
                        )
                );

        JButton clearButton =
                createButton(
                        "Clear",
                        new Color(
                                243,
                                244,
                                246
                        ),
                        new Color(
                                55,
                                65,
                                81
                        )
                );

        JButton closeButton =
                createButton(
                        "Close",
                        new Color(
                                229,
                                231,
                                235
                        ),
                        new Color(
                                31,
                                41,
                                55
                        )
                );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);

        formCard.add(
                formTitle,
                BorderLayout.NORTH
        );

        formCard.add(
                formPanel,
                BorderLayout.CENTER
        );

        formCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                formCard,
                BorderLayout.NORTH
        );

        // ==================================================
        // TABLE CARD
        // ==================================================

        JPanel tableCard =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        tableCard.setBackground(
                Color.WHITE
        );

        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        225,
                                        230
                                )
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        JLabel recordTitle =
                new JLabel(
                        "Attendance Records"
                );

        recordTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        recordTitle.setForeground(
                DARK_BLUE
        );

        searchDateField =
                new JTextField(12);

        JButton searchButton =
                createButton(
                        "Search",
                        new Color(
                                254,
                                243,
                                199
                        ),
                        new Color(
                                146,
                                64,
                                14
                        )
                );

        JButton refreshButton =
                createButton(
                        "Refresh",
                        new Color(
                                204,
                                251,
                                241
                        ),
                        new Color(
                                17,
                                94,
                                89
                        )
                );

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        searchPanel.setOpaque(false);

        searchPanel.add(
                new JLabel(
                        "Search Date:"
                )
        );

        searchPanel.add(
                searchDateField
        );

        searchPanel.add(
                searchButton
        );

        searchPanel.add(
                refreshButton
        );

        JPanel tableTop =
                new JPanel(
                        new BorderLayout()
                );

        tableTop.setOpaque(false);

        tableTop.add(
                recordTitle,
                BorderLayout.WEST
        );

        tableTop.add(
                searchPanel,
                BorderLayout.EAST
        );

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

        attendanceTable.setRowHeight(28);

        attendanceTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        attendanceTable
                .getTableHeader()
                .setBackground(
                        DARK_BLUE
                );

        attendanceTable
                .getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        attendanceTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        attendanceTable
                );

        tableCard.add(
                tableTop,
                BorderLayout.NORTH
        );

        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                tableCard,
                BorderLayout.CENTER
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // ==================================================
        // PERMISSIONS
        // ==================================================

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

        // ==================================================
        // TABLE CLICK
        // ==================================================

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

                                    statusComboBox.setSelectedItem(
                                            value(
                                                    row,
                                                    5
                                            )
                                    );
                                }
                            }
                        }
                );

        // ==================================================
        // ADD
        // ==================================================

        addButton.addActionListener(e -> {

            Attendance attendance =
                    buildAttendance();

            if (attendance == null) {
                return;
            }

            AttendanceDAO dao =
                    new AttendanceDAO();

            List<Attendance> list =
                    dao.getAllAttendance();

            for (Attendance existing : list) {

                if (existing.getUserId()
                        == selectedUserId

                        && existing
                        .getAttendanceDate()
                        .equals(
                                attendance
                                        .getAttendanceDate()
                        )) {

                    message(
                            "Attendance already exists for this date."
                    );

                    return;
                }
            }

            if (dao.addAttendance(
                    attendance
            )) {

                message(
                        "Attendance added successfully!"
                );

                clearForm();

                loadAttendance();

            } else {

                message(
                        "Attendance could not be added."
                );
            }
        });

        // ==================================================
        // UPDATE
        // ==================================================

        updateButton.addActionListener(e -> {

            int row =
                    attendanceTable
                            .getSelectedRow();

            if (row == -1) {

                message(
                        "Select an attendance record first."
                );

                return;
            }

            Attendance attendance =
                    buildAttendance();

            if (attendance == null) {
                return;
            }

            attendance.setAttendanceId(
                    Integer.parseInt(
                            value(
                                    row,
                                    0
                            )
                    )
            );

            AttendanceDAO dao =
                    new AttendanceDAO();

            if (dao.updateAttendance(
                    attendance
            )) {

                message(
                        "Attendance updated successfully!"
                );

                clearForm();

                loadAttendance();

            } else {

                message(
                        "Attendance could not be updated."
                );
            }
        });

        // ==================================================
        // DELETE
        // ==================================================

        deleteButton.addActionListener(e -> {

            int row =
                    attendanceTable
                            .getSelectedRow();

            if (row == -1) {

                message(
                        "Select an attendance record first."
                );

                return;
            }

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete this attendance record?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm
                    != JOptionPane.YES_OPTION) {

                return;
            }

            int id =
                    Integer.parseInt(
                            value(
                                    row,
                                    0
                            )
                    );

            AttendanceDAO dao =
                    new AttendanceDAO();

            if (dao.deleteAttendance(
                    id
            )) {

                message(
                        "Attendance deleted."
                );

                clearForm();

                loadAttendance();

            } else {

                message(
                        "Attendance could not be deleted."
                );
            }
        });

        // ==================================================
        // SEARCH
        // ==================================================

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

                loadAttendanceByDate(
                        LocalDate.parse(
                                text
                        )
                );

            } catch (Exception ex) {

                message(
                        "Enter date as YYYY-MM-DD."
                );
            }
        });

        refreshButton.addActionListener(e -> {

            searchDateField.setText("");

            clearForm();

            loadAttendance();
        });

        clearButton.addActionListener(
                e -> clearForm()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        loadAttendance();
    }

    // ==================================================
    // BUTTON STYLE
    // ==================================================

    private JButton createButton(
            String text,
            Color backgroundColor,
            Color textColor
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(
                backgroundColor
        );

        button.setForeground(
                textColor
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setFocusPainted(false);

        button.setOpaque(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        110,
                        36
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                backgroundColor.darker()
                        ),
                        BorderFactory.createEmptyBorder(
                                7,
                                14,
                                7,
                                14
                        )
                )
        );

        return button;
    }

    private Attendance buildAttendance() {

        String dateText =
                dateField
                        .getText()
                        .trim();

        String inText =
                timeInField
                        .getText()
                        .trim();

        String outText =
                timeOutField
                        .getText()
                        .trim();

        if (dateText.isEmpty()) {

            message(
                    "Date is required."
            );

            return null;
        }

        try {

            LocalDate date =
                    LocalDate.parse(
                            dateText
                    );

            LocalTime timeIn =
                    inText.isEmpty()
                            ? null
                            : LocalTime.parse(
                            inText
                    );

            LocalTime timeOut =
                    outText.isEmpty()
                            ? null
                            : LocalTime.parse(
                            outText
                    );

            if (timeIn != null
                    && timeOut != null
                    && timeOut.isBefore(
                    timeIn
            )) {

                message(
                        "Time Out cannot be before Time In."
                );

                return null;
            }

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            if ("ABSENT".equalsIgnoreCase(
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

            message(
                    "Invalid input.\n"
                            + "Date: YYYY-MM-DD\n"
                            + "Time: HH:MM"
            );

            return null;
        }
    }

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

                addAttendanceRow(
                        attendance
                );
            }
        }
    }

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

                addAttendanceRow(
                        attendance
                );
            }
        }
    }

    private void addAttendanceRow(
            Attendance attendance
    ) {

        tableModel.addRow(
                new Object[]{
                        attendance
                                .getAttendanceId(),

                        attendance
                                .getUserId(),

                        attendance
                                .getAttendanceDate(),

                        attendance
                                .getTimeIn(),

                        attendance
                                .getTimeOut(),

                        attendance
                                .getStatus()
                }
        );
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

    private void message(
            String text
    ) {

        JOptionPane.showMessageDialog(
                this,
                text
        );
    }
}