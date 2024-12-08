package Login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import model.Tasks;
import model.db;

import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;

class Tasklist extends javax.swing.JFrame {

    int rowIndex;
    DefaultTableModel model;
    db dao = new db();
    private JSpinner jSpinField2;

    public Tasklist() {

        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        ShowTimeJSpinner();

        setDateTime();
        tableTasks();
        id.setVisible(false);
        update.setVisible(false);
        priot.setVisible(false);

        jTable1.setDefaultRenderer(Object.class, (TableCellRenderer) new MyTableCellRenderer());

        jTable1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int lastRow = jTable1.getRowCount() - 1; // Get the last row index
                if (lastRow >= 0) { // Ensure there is at least one row
                    jTable1.scrollRectToVisible(jTable1.getCellRect(lastRow, 0, true));
                }
            }
        });

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void OnEdit(int row) {
                try {
                    // Check for empty fields
                    if (title.getText().isEmpty() || Description.getText().isEmpty()
                            || stat.getSelectedItem().toString().isEmpty() || jDateChooser1.getDate() == null
                            || priot.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(Tasklist.this, "Please fill in all the fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int rowIndex = jTable1.getSelectedRow();
                    if (rowIndex == -1) {
                        JOptionPane.showMessageDialog(Tasklist.this, "Please select a Task.", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        Tasks tasks = new Tasks();

                        // Set the Task ID from the selected row in the table
                        tasks.setTaskID(Integer.parseInt(model.getValueAt(rowIndex, 0).toString()));
                        tasks.setTitle(title.getText().trim());
                        tasks.setDes(Description.getText().trim());
                        tasks.setStatus(stat.getSelectedItem().toString().trim());

                        // Format the selected date to 'yyyy-MM-dd' format
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String dateStr = outputFormat.format(jDateChooser1.getDate());
                        tasks.setDate(dateStr);

                        // Get the time from the JSpinner and format it as 'HH:mm:ss'
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        String timeStr = timeFormat.format(jSpinner1.getValue());
                        tasks.setTime(timeStr); // Store the time as a string in 'HH:mm:ss' format

                        tasks.setPrio(priot.getText().trim());
                        
                        if (dao.updateTasks(tasks)) {
                            JOptionPane.showMessageDialog(Tasklist.this, "Task update successful.");
                            dao.getallTasks(jTable1);
                            clear();
                             sortTableByStatus(jTable1);
                            // Sort table rows to prioritize Incomplete tasks
                        } else {
                            JOptionPane.showMessageDialog(Tasklist.this, "Failed to update task.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Tasklist.this, "Unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            }

            @Override
            public void OnRemove(int row) {
                int rowIndex = jTable1.getSelectedRow();
                if (rowIndex == -1 || rowIndex >= jTable1.getRowCount()) {
                    JOptionPane.showMessageDialog(jTable1, "Please select a Task from the table.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Check if the status is "Incomplete"
                    String status = jTable1.getValueAt(rowIndex, 3).toString(); // Assuming the Status column is index 3
                    if ("Incomplete".equalsIgnoreCase(status.trim())) {
                        JOptionPane.showMessageDialog(jTable1, "Task has not been completed yet, try again.", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int confirmDialogResult = JOptionPane.showConfirmDialog(
                            null, // Pass null to center on the screen, or replace with your JFrame or JTable to center relative to it
                            "Are you sure you want to delete this Task?",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmDialogResult == JOptionPane.YES_OPTION) {
                        try {
                            Tasks tasks = new Tasks();
                            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                            // Fetch task data from the table model
                            tasks.setTaskID(Integer.parseInt(model.getValueAt(rowIndex, 0).toString()));
                            tasks.setTitle(model.getValueAt(rowIndex, 1).toString());
                            tasks.setDes(model.getValueAt(rowIndex, 2).toString());
                            tasks.setStatus(model.getValueAt(rowIndex, 3).toString());
                            tasks.setDate(model.getValueAt(rowIndex, 4).toString());
                            tasks.setPrio(model.getValueAt(rowIndex, 5).toString());

                            // Perform deletion
                            if (dao.delete(tasks)) {
                                JOptionPane.showMessageDialog(jTable1, "Task has been deleted.");
                                model.removeRow(rowIndex); // Update the table UI by removing the row
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(jTable1, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

        };
        jTable1.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender());
        jTable1.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(event));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        update = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        title = new javax.swing.JTextField();
        stat = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        Description = new javax.swing.JTextArea();
        id = new javax.swing.JTextField();
        label8 = new java.awt.Label();
        label9 = new java.awt.Label();
        label11 = new java.awt.Label();
        search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        priot = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jButton2 = new javax.swing.JButton();
        label10 = new java.awt.Label();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(45, 45, 45));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label3.setAlignment(java.awt.Label.CENTER);
        label3.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 1, 24)); // NOI18N
        label3.setForeground(new java.awt.Color(0, 153, 0));
        label3.setName(""); // NOI18N
        label3.setText("To-Do List Manager");
        jPanel2.add(label3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 30));

        label4.setAlignment(java.awt.Label.CENTER);
        label4.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label4.setForeground(new java.awt.Color(255, 255, 255));
        label4.setName(""); // NOI18N
        label4.setText("Notes/Reminders");
        jPanel2.add(label4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 120, 20));

        jTable1.setFont(new java.awt.Font("Ebrima", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Task ID", "Title", "Description", "Status", "Duedate", "DueTime", "Priorities", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(0, 0, 0));
        jTable1.setRowHeight(50);
        jTable1.setSelectionBackground(new java.awt.Color(57, 137, 111));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 1290, 440));

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Add Task");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 190, 130, 30));

        update.setBackground(new java.awt.Color(204, 204, 204));
        update.setText("Edit");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel2.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 370, 80, 30));

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("00:00:00");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, -1, 20));

        jLabel20.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("PM");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 0, -1, 20));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Wednesday, March 27, 2024");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 0, 250, 20));

        title.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jPanel2.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 270, 30));

        stat.setBackground(new java.awt.Color(45, 45, 45));
        stat.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        stat.setForeground(new java.awt.Color(255, 255, 255));
        stat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Incomplete", "Completed" }));
        stat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        stat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statActionPerformed(evt);
            }
        });
        jPanel2.add(stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 180, 160, 30));

        jDateChooser1.setBackground(new java.awt.Color(45, 45, 45));
        jDateChooser1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jDateChooser1.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jPanel2.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 160, 30));
        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, -1, -1));

        Description.setColumns(20);
        Description.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        Description.setRows(5);
        Description.setAutoscrolls(false);
        Description.setBorder(null);
        jScrollPane3.setViewportView(Description);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 270, 100));
        jPanel2.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 380, 120, -1));

        label8.setAlignment(java.awt.Label.CENTER);
        label8.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label8.setForeground(new java.awt.Color(255, 255, 255));
        label8.setName(""); // NOI18N
        label8.setText("DueTime");
        jPanel2.add(label8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, 60, 20));

        label9.setAlignment(java.awt.Label.CENTER);
        label9.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        label9.setForeground(new java.awt.Color(255, 255, 255));
        label9.setName(""); // NOI18N
        label9.setText("Task");
        jPanel2.add(label9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 50, 30));

        label11.setAlignment(java.awt.Label.CENTER);
        label11.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label11.setForeground(new java.awt.Color(255, 255, 255));
        label11.setName(""); // NOI18N
        label11.setText("Status");
        jPanel2.add(label11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, -1, 20));

        search.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                searchMouseReleased(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });
        jPanel2.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 190, 200, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/model/1.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 180, 40, 50));
        jPanel2.add(priot, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 390, 110, 30));

        jSpinner1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jPanel2.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, 160, 30));

        jButton2.setBackground(new java.awt.Color(51, 102, 255));
        jButton2.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Clear");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 190, 90, 30));

        label10.setAlignment(java.awt.Label.CENTER);
        label10.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        label10.setForeground(new java.awt.Color(255, 255, 255));
        label10.setName(""); // NOI18N
        label10.setText("Duedate");
        jPanel2.add(label10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, 60, 20));

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 102, 255));
        jButton3.setText("Refresh");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 190, 110, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1082, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        model = (DefaultTableModel) jTable1.getModel();
        rowIndex = jTable1.getSelectedRow();

        if (rowIndex >= 0) {
            // Set the fields from the selected row
            id.setText(model.getValueAt(rowIndex, 0).toString());
            title.setText(model.getValueAt(rowIndex, 1).toString());
            Description.setText(model.getValueAt(rowIndex, 2).toString());
            stat.setSelectedItem(model.getValueAt(rowIndex, 3).toString());
            priot.setText(model.getValueAt(rowIndex, 6).toString());

            try {
                // Set the Date in jDateChooser
                Object dateObj = model.getValueAt(rowIndex, 4); // Date (column index 4)
                if (dateObj != null) {
                    String dateStr = dateObj.toString(); // Assuming date is stored as String in "yyyy-MM-dd"

                    // Parse the date string into a Date object
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = inputFormat.parse(dateStr);
                    jDateChooser1.setDate(parsedDate); // Set the parsed date in the JDateChooser

                    // Set the time for the JSpinner
                    String timeStr = model.getValueAt(rowIndex, 5).toString(); // Assuming time is stored as HH:mm:ss
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    java.util.Date timeDate = timeFormat.parse(timeStr); // Parse time to Date object

                    jSpinner1.setValue(timeDate); 
                   
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error parsing date/time: " + e.getMessage(),
                        "Date/Time Parsing Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
           
    }//GEN-LAST:event_jTable1MouseClicked
    }
    private void statActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statActionPerformed
    private boolean validateField(JTextField field, String errorMessage) {
        if (field == null || field.getText() == null || field.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, errorMessage, "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {

            // Validate fields
            if (title == null || title.getText() == null || title.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Title", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (Description == null || Description.getText() == null || Description.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Add Description", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (stat == null || stat.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Select a Status", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (jDateChooser1 == null || jDateChooser1.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Select a Date", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String status = stat.getSelectedItem().toString().trim();
            if ("Completed".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(null, "Task is Completed, try another Incompleted task.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get only time from jSpinner1 (HH:mm:ss format)
            Object spinnerValue = jSpinner1.getValue();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String timeString = timeFormat.format(spinnerValue); // Format as HH:mm:ss

            Tasks tasks = new Tasks();
            tasks.setTitle(title.getText().trim());
            tasks.setDes(Description.getText().trim());
            tasks.setStatus(stat.getSelectedItem().toString().trim());

            // Set date (without time)
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd, yy");
            tasks.setDate(dateFormat.format(jDateChooser1.getDate()));

            // Set the formatted time to the tasks object
            tasks.setTime(timeString);  // Store time in HH:mm:ss format

            // Set priority
            tasks.setPrio(jLabel4.getText().trim());

            // Insert task into database
            if (dao.insertTask(tasks)) {
                JOptionPane.showMessageDialog(null, "Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clear();
                tableTasks();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add task. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        try {
            // Check if any field is empty
            if (title.getText().isEmpty() || Description.getText().isEmpty()
                    || stat.getSelectedItem().toString().isEmpty() || jDateChooser1.getDate() == null
                    || priot.getText().toString().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Please fill in all the fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (rowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a Task.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                Tasks tasks = new Tasks();
                tasks.setTaskID(Integer.parseInt(model.getValueAt(rowIndex, 0).toString()));
                tasks.setTitle(title.getText().trim());
                tasks.setDes(Description.getText().trim());
                tasks.setStatus(stat.getSelectedItem().toString().trim());

                // Define input format for parsing the date (from JDateChooser)
                SimpleDateFormat inputFormat = new SimpleDateFormat("MM d, yy");  // Format for date from JDateChooser
                // Define output format for saving the date (to the database)
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");  // Database format

                // Convert the date from JDateChooser to string in the format required for the database
                String dateStr = outputFormat.format(jDateChooser1.getDate());
                tasks.setDate(dateStr);  // Set the formatted date as a string

                tasks.setPrio(priot.getText().toString().trim());

                if (dao.updateTasks(tasks)) {
                    JOptionPane.showMessageDialog(this, "Task update successful.");
                    dao.getallTasks(jTable1);  // Refresh the task list

                    clear();  // Clear the fields
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update task", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }


    }//GEN-LAST:event_updateActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void searchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseReleased

    }//GEN-LAST:event_searchMouseReleased

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        DefaultTableModel ob = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        jTable1.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(search.getText()));
    }//GEN-LAST:event_searchKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        sortTableByStatus(jTable1);

        // Optionally, show a message to the user
        JOptionPane.showMessageDialog(null, "Table has been refreshed. Incomplete tasks are now prioritized.", "Refreshed", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void sortTableByStatus(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Create a list of rows from the table model
        List<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] row = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                row[j] = model.getValueAt(i, j);
            }
            rows.add(row);
        }

        // Sort rows: Incomplete tasks first, then Completed tasks
        rows.sort((row1, row2) -> {
            String status1 = row1[3].toString(); // Assuming the Status column is index 3
            String status2 = row2[3].toString();

            if (status1.equalsIgnoreCase("Incomplete") && status2.equalsIgnoreCase("Completed")) {
                return -1; // Incomplete comes before Completed
            } else if (status1.equalsIgnoreCase("Completed") && status2.equalsIgnoreCase("Incomplete")) {
                return 1; // Completed comes after Incomplete
            }
            return 0; // Keep the order if both statuses are the same
        });

        // Clear the model and re-add sorted rows
        model.setRowCount(0);
        for (Object[] row : rows) {
            model.addRow(row);
        }
    }

    private void clear() {
        if (title != null) {
            title.setText("");
        }
        if (Description != null) {
            Description.setText("");
        }
        if (stat != null) {
            stat.setSelectedIndex(0);
        }
        if (priot != null) {
            priot.setText("");
        }

        if (jDateChooser1 != null) {
            jDateChooser1.setDate(null);
        }
    }

    private void tableTasks() {
        dao.getallTasks(jTable1);
        model = (DefaultTableModel) jTable1.getModel();
        jTable1.setRowHeight(80);
        jTable1.getTableHeader().setReorderingAllowed(false);

        jTable1.getColumnModel().getColumn(1).setPreferredWidth(130); // 6th column
        jTable1.getColumnModel().getColumn(1).setMaxWidth(130);
        jTable1.getColumnModel().getColumn(1).setMinWidth(130);

        jTable1.getColumnModel().getColumn(3).setPreferredWidth(75); // 4th column
        jTable1.getColumnModel().getColumn(3).setMaxWidth(75);
        jTable1.getColumnModel().getColumn(3).setMinWidth(75);

        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80); // 5th column
        jTable1.getColumnModel().getColumn(4).setMaxWidth(80);
        jTable1.getColumnModel().getColumn(4).setMinWidth(80);

        jTable1.getColumnModel().getColumn(5).setPreferredWidth(70); // 6th column
        jTable1.getColumnModel().getColumn(5).setMaxWidth(70);
        jTable1.getColumnModel().getColumn(5).setMinWidth(70);

        jTable1.getColumnModel().getColumn(6).setPreferredWidth(70); // 6th column
        jTable1.getColumnModel().getColumn(6).setMaxWidth(70);
        jTable1.getColumnModel().getColumn(6).setMinWidth(70);

        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80); // 6th column
        jTable1.getColumnModel().getColumn(7).setMaxWidth(80);
        jTable1.getColumnModel().getColumn(7).setMinWidth(80);

        jTable1.getColumnModel().getColumn(7).setPreferredWidth(130); // 6th column
        jTable1.getColumnModel().getColumn(7).setMaxWidth(130);
        jTable1.getColumnModel().getColumn(7).setMinWidth(130);

        //   jTable1.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer());
        //  jTable1.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer());
        JTableHeader header = jTable1.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(new Color(0, 102, 51));
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    public void ShowTimeJSpinner() {
        Date date = new Date(); // Current time

        // Create SpinnerDateModel with time range, and set it to use hour format only
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);

        // Set the model to the spinner
        jSpinner1.setModel(sm);

        // Set the date editor format to HH:mm:ss for displaying time only
        JSpinner.DateEditor de = new JSpinner.DateEditor(jSpinner1, "HH:mm:ss");
        jSpinner1.setEditor(de);
    }

    private void setDateTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat tf = new SimpleDateFormat("h:mm:ss aa");
                        SimpleDateFormat df = new SimpleDateFormat("EEEE, yyyy-MM-dd");
                        String time = tf.format(calendar.getTime());
                        final String[] timeSplit = time.split(" ");
                        final String timePart = timeSplit[0];
                        final String amPmPart = timeSplit[1];
                        final String dateStr = df.format(calendar.getTime());
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                jLabel4.setText(timePart);
                                jLabel20.setText(amPmPart);
                                jLabel2.setText(dateStr);
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace(); // Print the stack trace for debugging purposes
                }
            }
        }).start();

    }

    public static void main(String args[]) {

        //   FlatLightLaf.setup();
        try {

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tasklist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tasklist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tasklist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tasklist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tasklist().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Description;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private javax.swing.JTextField priot;
    private javax.swing.JTextField search;
    private javax.swing.JComboBox<String> stat;
    private javax.swing.JTextField title;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables

    private void setExtentedState(int MAXIMIZED_BOTH) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
