//package ui;
//
//import model.Exercise;
//import model.Plan;
//import model.WorkoutPlanTracker;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Scanner;
//
//// Workout Plan Tracker - console
//public class WorkoutPlanTrackerAppConsole {
//    private WorkoutPlanTracker workoutPlanTracker;
//    private final Scanner scanner;
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;
//    private static final String JSON_STORE = "./data/workout-plan-tracker.json";
//
//    // EFFECTS: constructs workout plan tracker and runs application
//    public WorkoutPlanTrackerAppConsole() throws FileNotFoundException {
//        scanner = new Scanner(System.in);
//        workoutPlanTracker = new WorkoutPlanTracker();
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//        runWorkoutPlanTrackerApp();
//    }
//
//    @SuppressWarnings("methodlength")
//    // EFFECTS: prompts user to select between the given options
//    private void runWorkoutPlanTrackerApp() {
//        System.out.println("Welcome to MotionPal");
//        System.out.println("Let's create your workout plan!");
//        while (true) {
//            System.out.print(
//                    "1. Add Plan\n"
//                            + "2. Modify Plan\n"
//                            + "3. Plan Summary\n"
//                            + "4. Mark Plan as Complete\n"
//                            + "5. Load Plan from File\n"
//                            + "6. Save Changes to File\n"
//                            + "0. Exit\n"
//                            + "Choose: "
//            );
//            String menu = scanner.nextLine();
//            switch (menu) {
//                case "1":
//                    addPlan();
//                    break;
//                case "2":
//                    Plan plan = getPlan();
//                    if (plan == null) {
//                        notInPlans();
//                        break;
//                    }
//                    modifyPlan(plan);
//                    break;
//                case "3":
//                    System.out.println(workoutPlanTracker.summary());
//                    break;
//                case "4":
//                    markAsComplete();
//                    break;
//                case "5":
//                    loadWorkoutPlanTracker();
//                    break;
//                case "6":
//                    saveWorkoutPlanTracker();
//                    break;
//                case "0":
//                    return;
//            }
//        }
//    }
//
//    // MODIFIES: plan
//    // EFFECTS: marks incomplete workout plan as complete
//    private void markAsComplete() {
//        Plan plan = workoutPlanTracker.getFirstIncompletePlan();
//        if (plan == null) {
//            System.out.println("All plan has been completed");
//            return;
//        }
//        System.out.println(plan.getName());
//        System.out.println("Do you want to mark the plan as complete? (yes/no)");
//        String choice;
//        do {
//            choice = scanner.nextLine();
//            if (choice.equalsIgnoreCase("yes")) {
//                System.out.println("Workout Plan Completed");
//                break;
//            } else if (choice.equalsIgnoreCase("no")) {
//                System.out.println("Workout Plan Not Completed");
//                break;
//            }
//            System.out.println("Answer has to be 'yes' or 'no'");
//        } while (true);
//        if (choice.equalsIgnoreCase("yes")) {
//            plan.completePlan();
//        }
//    }
//
//    // EFFECTS: return plan for a certain week and day
//    private Plan getPlan() {
//        System.out.println("Week : ");
//        int week = Integer.parseInt(scanner.nextLine());
//        System.out.println("Day : ");
//        int day = Integer.parseInt(scanner.nextLine());
//        return workoutPlanTracker.getPlan(week,day);
//    }
//
//    // MODIFIES: plans
//    // EFFECTS: adds new Plan
//    private void addPlan() {
//        modifyPlan(workoutPlanTracker.addNewPlan());
//    }
//
//    // EFFECTS: adds detail of exercise
//    public int addDetails(String element) {
//        System.out.print(element);
//        return Integer.parseInt(scanner.nextLine());
//    }
//
//    // MODIFIES: plan
//    // EFFECTS: adds new exercise
//    public void addExercise(Plan plan) {
//        System.out.print("Exercise name: ");
//        String name = scanner.nextLine();
//        int sets = addDetails("Sets: ");
//        int reps = addDetails("Reps: ");
//        int weight = addDetails("Weight (lbs): ");
//        int duration = addDetails("Duration (seconds): ");
//        plan.addExercise(new Exercise(name,sets,reps,weight,duration));
//        System.out.println(plan.summary());
//    }
//
//    @SuppressWarnings("methodlength")
//    // MODIFIES: plan
//    // EFFECTS: edit given workout plan
//    public void modifyPlan(Plan plan) {
//        String option;
//        boolean showOptions = true;
//        do {
//            if (showOptions) {
//                System.out.println(plan.getName());
//                System.out.println("Enter 'add' to add an exercise");
//                System.out.println("Enter 'remove' to remove an exercise");
//                System.out.println("Enter 'modify' to modify the details "
//                        + "(sets, reps, weight, duration) of an exercise");
//                System.out.println("Enter 'summary' to view workout plan summary and finish updating it");
//            }
//            showOptions = true;
//            option = scanner.nextLine();
//            switch (option) {
//                case "summary":
//                    if (savePlan(plan)) {
//                        return;
//                    }
//                case "add":
//                    addExercise(plan);
//                    break;
//                case "remove":
//                    removeExercise(plan);
//                    break;
//                case "modify":
//                    modifyExercise(plan);
//                    break;
//                default:
//                    System.out.println("Enter 'add', 'remove', 'modify', or 'summary'.");
//                    showOptions = false;
//                    break;
//            }
//        } while (!option.equals("no"));
//    }
//
//    // EFFECTS: saves created Workout Plan
//    public boolean savePlan(Plan plan) {
//        System.out.println(plan.summary());
//        System.out.println("Would you like to finish updating this workout plan? (yes/no)");
//        String ans = scanner.nextLine();
//        while (!ans.equals("no") && !ans.equals("yes")) {
//            System.out.println("Answer has to be 'yes' or 'no'");
//            System.out.println("Would you like to finish updating this workout plan? (yes/no)");
//            ans = scanner.nextLine();
//        }
//        if (ans.equals("yes")) {
//            System.out.println("Finished updating workout plan.");
//            return true;
//        }
//        return false;
//    }
//
//    // REQUIRES: given exercise has to be part of Plan
//    // MODIFIES: plan
//    // EFFECTS: remove given exercise
//    public void removeExercise(Plan plan) {
//        System.out.println("Which exercise do you want to remove?");
//        String exerciseName = scanner.nextLine();
//        if (plan.removeExercise(exerciseName)) {
//            System.out.println(exerciseName + " has been removed from your workout plan.");
//        } else {
//            notInPlan();
//        }
//        System.out.println(plan.summary());
//    }
//
//    // REQUIRES: given exercise has to be part of Plan
//    //           for new details, name must be filled, sets > 0, reps > 0, weight > 0, duration > 0
//    // MODIFIES: exercise, plan
//    // EFFECTS: edit given exercise
//    public void modifyExercise(Plan plan) {
//        System.out.println("Which exercise do you want to modify?");
//        String exerciseName = scanner.nextLine();
//        Exercise exercise = plan.getExercise(exerciseName);
//        if (exercise == null) {
//            notInPlan();
//            return;
//        }
//        exercise.setSets(addDetails("Sets: "));
//        exercise.setReps(addDetails("Reps: "));
//        exercise.setWeight(addDetails("Weight (lbs): "));
//        exercise.setDuration(addDetails("Duration (seconds): "));
//        System.out.println(exerciseName + " has been modified.");
//        System.out.println(plan.summary());
//    }
//
//    //EFFECTS: notify user if given exercise to be modified or removed is not part of workout plan
//    public void notInPlan() {
//        System.out.println("This exercise is not part of your workout plan.");
//    }
//
//    //EFFECTS: notify user if plan schedule (given week and day) to be modified
//    //         is not part of workout plan
//    public void notInPlans() {
//        System.out.println("This plan is not part of your workout plans.");
//    }
//
//    // EFFECTS: saves workout plan to file
//    private void saveWorkoutPlanTracker() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(workoutPlanTracker);
//            jsonWriter.close();
//            System.out.println("Saved data to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads workout plan from file
//    private void loadWorkoutPlanTracker() {
//        try {
//            workoutPlanTracker = jsonReader.read();
//            System.out.println("Loaded data from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//}