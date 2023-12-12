package ui;

import model.Exercise;
import model.Plan;
import model.WorkoutPlanTracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents a Workout Plan Tracker with GUI
public class WorkoutPlanTrackerAppGUI {
    private WorkoutPlanTracker workoutPlanTracker;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/workout-plan-tracker.json";
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTable planTable;
    private JTable exerciseTable;
    private Plan selectedPlan;

    JButton addExerciseButton;
    JButton modifyExerciseButton;
    JButton removeExerciseButton;

    JTextField nameTf;
    JTextField setsTf;
    JTextField repsTf;
    JTextField weightTf;
    JTextField durationTf;

    Color primary;
    Color secondary;
    Color accent1;
    Color accent2;
    Color textColor;

    // EFFECTS: constructs a Workout Plan Tracker with GUI, jsonWriter, and jsonReader
    public WorkoutPlanTrackerAppGUI() {
        workoutPlanTracker = new WorkoutPlanTracker();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        showGUI();
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: creates frame
    public void showGUI() {
//        primary = new Color(36, 41, 62);
//        secondary = new Color(59, 71, 122);
//        accent1 = new Color(164, 193, 236);
//        textColor = new Color(244, 245, 252);

        primary = new Color(36, 41, 62);
        secondary = new Color(59, 71, 122);
        accent1 = new Color(209, 221, 237);
        accent2 = new Color(188, 203, 227);
        textColor = new Color(240, 240, 240);

        JFrame mainFrame = new JFrame("MotionPal");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(700, 400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setBackground(primary);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(primary);

        JPanel splashPanel = splashScreenPanel();
        splashPanel.setBackground(primary);
        cardPanel.add(splashPanel, "splash");

        JPanel menuPanel = menuPanel();
        cardPanel.add(menuPanel, "main");
        menuPanel.setBackground(primary);

        JPanel modifyMenuPanel = modifyPlanPanel();
        cardPanel.add(modifyMenuPanel, "modify");
        modifyMenuPanel.setBackground(primary);

        cardLayout.show(cardPanel, "splash");
        mainFrame.add(cardPanel);
        mainFrame.setVisible(true);
        simulateLoadingProcess();
    }

    // EFFECTS: simulates loading process for 3000 ms
    private void simulateLoadingProcess() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cardLayout.show(cardPanel, "main");
    }

    // EFFECTS: restrict inputs to number
    @SuppressWarnings("methodlength")
    private void setNumericFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                StringBuilder sb = new StringBuilder(string);
                for (int i = sb.length() - 1; i >= 0; i--) {
                    char c = sb.charAt(i);
                    if (!Character.isDigit(c)) {
                        sb.deleteCharAt(i);
                    }
                }
                super.insertString(fb, offset, sb.toString(), attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                StringBuilder sb = new StringBuilder(text);
                for (int i = sb.length() - 1; i >= 0; i--) {
                    char c = sb.charAt(i);
                    if (!Character.isDigit(c)) {
                        sb.deleteCharAt(i);
                    }
                }
                super.replace(fb, offset, length, sb.toString(), attrs);
            }
        });
    }

    // EFFECTS: resets exercise name, sets, reps, weight, and duration
    private void resetFieldExercise() {
        nameTf.setText(null);
        setsTf.setText(null);
        repsTf.setText(null);
        weightTf.setText(null);
        durationTf.setText(null);
    }

    // EFFECTS: action for modify exercise
    private void selectModifyExercise(Exercise exercise) {
        addExerciseButton.setEnabled(false);
        modifyExerciseButton.setEnabled(true);
        removeExerciseButton.setEnabled(true);
        nameTf.setText(exercise.getName());
        setsTf.setText(String.valueOf(exercise.getSets()));
        repsTf.setText(String.valueOf(exercise.getReps()));
        weightTf.setText(String.valueOf(exercise.getWeight()));
        durationTf.setText(String.valueOf(exercise.getDuration()));
    }

    // EFFECTS: action for after modifying exercise
    private void resetModifyExercise() {
        addExerciseButton.setEnabled(true);
        modifyExerciseButton.setEnabled(false);
        removeExerciseButton.setEnabled(false);
        resetFieldExercise();
        loadExerciseTable();
    }

    // EFFECTS: checks if input is valid
    private boolean validateExerciseInput() {
        boolean valid = true;
        if (nameTf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Name cannot be empty");
            valid = false;
        }
        if (setsTf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Sets cannot be empty");
            valid = false;
        }
        if (repsTf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Reps cannot be empty");
            valid = false;
        }
        if (weightTf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Weight cannot be empty");
            valid = false;
        }
        if (durationTf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Duration cannot be empty");
            valid = false;
        }
        return valid;
    }

    // EFFECTS: creates splash screen
    private JPanel splashScreenPanel() {
        ImageIcon imageIcon = new ImageIcon("./data/motionpal.png");
        Image adjustImage = imageIcon.getImage().getScaledInstance(100,100,100);
        ImageIcon adjustedImage = new ImageIcon(adjustImage);
        JLabel appIconLabel = new JLabel(adjustedImage);

        JPanel splashPanel = new JPanel();
        splashPanel.setLayout(new BorderLayout());
        splashPanel.setBackground(primary);
        splashPanel.add(appIconLabel, BorderLayout.CENTER);

        return splashPanel;
    }

    // EFFECTS: creates panel for main menu
    @SuppressWarnings("methodlength")
    private JPanel menuPanel() {
        JPanel mainMenuPanel = new JPanel(new BorderLayout());
        mainMenuPanel.setBackground(primary);

        planTable = new JTable();
        planTable.setBackground(primary);
        planTable.setForeground(textColor);
        loadPlanTable();

        JScrollPane scrollPane = new JScrollPane(planTable);
        scrollPane.setBackground(primary);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                trackColor = primary;
            }

            @Override
            protected void paintThumb(Graphics graphics, JComponent component, Rectangle thumb) {
                graphics.setColor(accent2);
                graphics.fillRoundRect(thumb.x + 4, thumb.y + 3,
                        thumb.width - 8, thumb.height - 6,
                        10, 10);
            }
        });

        JButton markPlanButton = new JButton("Change Completion Status");
        JButton addPlanButton = new JButton("Add Plan");
        JButton modifyPlanButton = new JButton("View/Modify Plan");
        JButton loadPlansButton = new JButton("Load Plan from File");
        JButton savePlansButton = new JButton("Save Changes to File");

        markPlanButton.setBackground(accent1);
        addPlanButton.setBackground(accent1);
        modifyPlanButton.setBackground(accent1);
        loadPlansButton.setBackground(accent1);
        savePlansButton.setBackground(accent1);

        markPlanButton.setForeground(secondary);
        addPlanButton.setForeground(secondary);
        modifyPlanButton.setForeground(secondary);
        loadPlansButton.setForeground(secondary);
        savePlansButton.setForeground(secondary);

        JPanel centerButtonPanel = new JPanel(new GridLayout(1, 2));
        centerButtonPanel.add(addPlanButton);
        centerButtonPanel.add(modifyPlanButton);
        centerButtonPanel.setBackground(primary);
        centerButtonPanel.setForeground(accent1);

        JPanel bottomButtonPanel = new JPanel(new GridLayout(1, 2));
        bottomButtonPanel.add(loadPlansButton);
        bottomButtonPanel.add(savePlansButton);
        bottomButtonPanel.setBackground(primary);
        bottomButtonPanel.setForeground(accent1);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(markPlanButton, BorderLayout.NORTH);
        buttonPanel.add(centerButtonPanel, BorderLayout.CENTER);
        buttonPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        buttonPanel.setBackground(primary);
        buttonPanel.setForeground(accent1);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainMenuPanel.add(scrollPane, BorderLayout.CENTER);
        mainMenuPanel.add(buttonPanel, BorderLayout.SOUTH);

        markPlanButton.addActionListener(actionEvent -> {
            int idx = planTable.getSelectedRow();
            if (idx != -1) {
                Plan plan  = workoutPlanTracker.getPlans().get(idx);
                plan.switchPlan();
                loadPlanTable();
            } else {
                JOptionPane.showMessageDialog(null,"Please select plan on table");
            }
        });
        addPlanButton.addActionListener(actionEvent -> {
            workoutPlanTracker.addNewPlan();
            loadPlanTable();
        });
        modifyPlanButton.addActionListener(actionEvent -> {
            int idx = planTable.getSelectedRow();
            if (idx != -1) {
                selectedPlan = workoutPlanTracker.getPlans().get(idx);
                resetModifyExercise();
                cardLayout.show(cardPanel, "modify");
            } else {
                JOptionPane.showMessageDialog(null,"Please select plan on table");
            }
        });
        savePlansButton.addActionListener(actionEvent -> saveWorkoutPlanTracker());
        loadPlansButton.addActionListener(actionEvent -> {
            loadWorkoutPlanTracker();
            loadPlanTable();
        });
        return mainMenuPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates panel for modify plan
    @SuppressWarnings("methodlength")
    private JPanel modifyPlanPanel() {
        JPanel mainMenuPanel = new JPanel(new BorderLayout());
        mainMenuPanel.setBackground(primary);

        exerciseTable = new JTable();
        exerciseTable.setBackground(primary);
        exerciseTable.setForeground(textColor);

        JScrollPane scrollPane = new JScrollPane(exerciseTable);
        scrollPane.setBackground(primary);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                trackColor = primary;
            }

            @Override
            protected void paintThumb(Graphics graphics, JComponent component, Rectangle thumb) {
                graphics.setColor(accent2);
                graphics.fillRoundRect(thumb.x + 4, thumb.y + 3,
                        thumb.width - 8, thumb.height - 6,
                        10, 10);
            }
        });

        JButton backButton = new JButton("Back");
        addExerciseButton = new JButton("Add Exercise");
        modifyExerciseButton = new JButton("Modify Exercise");
        removeExerciseButton = new JButton("Remove Exercise");

        nameTf = new JTextField();
        setsTf = new JTextField();
        repsTf = new JTextField();
        weightTf = new JTextField();
        durationTf = new JTextField();
        setNumericFilter(setsTf);
        setNumericFilter(repsTf);
        setNumericFilter(weightTf);
        setNumericFilter(durationTf);

        JPanel nameInput = new JPanel(new GridLayout(2, 1));
        JLabel exerciseName = new JLabel("Exercise Name");
        exerciseName.setForeground(textColor);
        nameInput.add(exerciseName);
        nameInput.add(nameTf);

        JPanel setsInput = new JPanel(new GridLayout(2, 1));
        JLabel sets = new JLabel("Sets");
        sets.setForeground(textColor);
        setsInput.add(sets);
        setsInput.add(setsTf);

        JPanel repsInput = new JPanel(new GridLayout(2, 1));
        JLabel reps = new JLabel("Reps");
        reps.setForeground(textColor);
        repsInput.add(reps);
        repsInput.add(repsTf);

        JPanel weightInput = new JPanel(new GridLayout(2, 1));
        JLabel weight = new JLabel("Weight (lbs)");
        weight.setForeground(textColor);
        weightInput.add(weight);
        weightInput.add(weightTf);

        JPanel durationInput = new JPanel(new GridLayout(2, 1));
        JLabel duration = new JLabel("Duration (seconds)");
        duration.setForeground(textColor);
        durationInput.add(duration);
        durationInput.add(durationTf);

        JPanel topInputPanel = new JPanel(new GridLayout(1, 5));
        topInputPanel.add(nameInput);
        topInputPanel.add(setsInput);
        topInputPanel.add(repsInput);
        topInputPanel.add(weightInput);
        topInputPanel.add(durationInput);
        topInputPanel.setBackground(primary);
        topInputPanel.setBorder(BorderFactory.createEmptyBorder(0, 6, 3, 6));

        JPanel centerButtonPanel = new JPanel(new GridLayout(1, 3));
        centerButtonPanel.add(addExerciseButton);
        centerButtonPanel.add(modifyExerciseButton);
        centerButtonPanel.add(removeExerciseButton);
        centerButtonPanel.setBackground(primary);

        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(topInputPanel, BorderLayout.NORTH);
        formPanel.add(centerButtonPanel, BorderLayout.CENTER);
        formPanel.add(backButton, BorderLayout.SOUTH);
        formPanel.setBackground(primary);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainMenuPanel.add(scrollPane, BorderLayout.CENTER);
        mainMenuPanel.add(formPanel, BorderLayout.SOUTH);

        nameInput.setBackground(primary);
        setsInput.setBackground(primary);
        repsInput.setBackground(primary);
        weightInput.setBackground(primary);
        durationInput.setBackground(primary);
        backButton.setBackground(primary);

        nameTf.setBackground(accent1);
        setsTf.setBackground(accent1);
        repsTf.setBackground(accent1);
        weightTf.setBackground(accent1);
        durationTf.setBackground(accent1);

        nameTf.setForeground(secondary);
        setsTf.setForeground(secondary);
        repsTf.setForeground(secondary);
        weightTf.setForeground(secondary);
        durationTf.setForeground(secondary);

        setsTf.setHorizontalAlignment(JTextField.CENTER);
        repsTf.setHorizontalAlignment(JTextField.CENTER);
        weightTf.setHorizontalAlignment(JTextField.CENTER);
        durationTf.setHorizontalAlignment(JTextField.CENTER);

        addExerciseButton.setForeground(secondary);
        modifyExerciseButton.setForeground(secondary);
        removeExerciseButton.setForeground(secondary);

        exerciseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectModifyExercise(selectedPlan.getExercises().get(exerciseTable.getSelectedRow()));
            }
        });

        addExerciseButton.addActionListener(actionEvent -> {
            if (validateExerciseInput()) {
                selectedPlan.addExercise(
                        new Exercise(
                                nameTf.getText(),
                                Integer.parseInt(setsTf.getText()),
                                Integer.parseInt(repsTf.getText()),
                                Integer.parseInt(weightTf.getText()),
                                Integer.parseInt(durationTf.getText())
                        ),
                        true
                );
                loadExerciseTable();
                resetFieldExercise();
            }
        });
        modifyExerciseButton.addActionListener(actionEvent -> {
            Exercise exercise = selectedPlan.getExercises().get(exerciseTable.getSelectedRow());
            exercise.setName(nameTf.getText());
            exercise.setSets(Integer.parseInt(setsTf.getText()));
            exercise.setReps(Integer.parseInt(repsTf.getText()));
            exercise.setWeight(Integer.parseInt(weightTf.getText()));
            exercise.setDuration(Integer.parseInt(durationTf.getText()));
            resetModifyExercise();
        });
        removeExerciseButton.addActionListener(actionEvent -> {
            selectedPlan.removeExercise(exerciseTable.getSelectedRow());
            resetModifyExercise();
        });
        backButton.addActionListener(actionEvent -> cardLayout.show(cardPanel, "main"));

        return mainMenuPanel;
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: loads plan table with 3 columns (week, day, and completion status)
    public void loadPlanTable() {
        DefaultTableModel plansTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        planTable.setModel(plansTableModel);
        planTable.setBackground(accent1);
        planTable.setForeground(primary);
        plansTableModel.addColumn("Week");
        plansTableModel.addColumn("Day");
        plansTableModel.addColumn("Completed");

        for (Plan plan : workoutPlanTracker.getPlans()) {
            plansTableModel.addRow(new Object[]{
                    plan.getWeek(),
                    plan.getDay(),
                    plan.isComplete() ? "Yes" : "No"
            });
        }

        planTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(JLabel.CENTER);
                setBackground(primary);
                setForeground(textColor);
            }
        });

        planTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component tableCell = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);

                tableCell.setForeground(primary);
                setHorizontalAlignment(JLabel.CENTER);

                if ((row / 7) % 2 == 0) {
                    tableCell.setBackground(accent1);
                } else {
                    tableCell.setBackground(accent2);
                }

                if (isSelected) {
                    tableCell.setBackground(secondary);
                    tableCell.setForeground(textColor);
                }

                return tableCell;
            }
        });
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: loads exercise table
    public void loadExerciseTable() {
        DefaultTableModel exercisesTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        exerciseTable.setModel(exercisesTableModel);
        exerciseTable.setBackground(accent1);
        exerciseTable.setForeground(primary);
        exercisesTableModel.addColumn("Exercise Name");
        exercisesTableModel.addColumn("Sets");
        exercisesTableModel.addColumn("Reps");
        exercisesTableModel.addColumn("Weight (lbs)");
        exercisesTableModel.addColumn("Duration (seconds)");

        for (Exercise exercise : selectedPlan.getExercises()) {
            exercisesTableModel.addRow(new Object[]{
                    exercise.getName(),
                    exercise.getSets(),
                    exercise.getReps(),
                    exercise.getWeight(),
                    exercise.getDuration()
            });
        }

        exerciseTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(JLabel.CENTER);
                setBackground(primary);
                setForeground(textColor);
            }
        });

        exerciseTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component tableCell = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);

                tableCell.setForeground(primary);
                setHorizontalAlignment(JLabel.CENTER);

                if (row % 2 == 0) {
                    tableCell.setBackground(accent1);
                } else {
                    tableCell.setBackground(accent2);
                }

                if (isSelected) {
                    tableCell.setBackground(secondary);
                    tableCell.setForeground(textColor);
                }

                return tableCell;
            }
        });
    }

    // EFFECTS: saves workout plan to file
    private void saveWorkoutPlanTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(workoutPlanTracker);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,"Saved data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workout plan from file
    private void loadWorkoutPlanTracker() {
        try {
            workoutPlanTracker = jsonReader.read();
            JOptionPane.showMessageDialog(null,"Loaded data from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Unable to read from file: " + JSON_STORE);
        }
    }
}
