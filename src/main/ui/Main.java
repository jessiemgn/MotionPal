package ui;

//import java.io.FileNotFoundException;
//uncomment line above when running WorkoutPlanTrackerAppConsole

import model.EventLog;

// Run the WorkoutPlanTracker
public class Main {
    public static void main(String[] args) {
//        Below is code to run WorkoutPlanTrackerAppConsole
//        try {
//            new WorkoutPlanTrackerAppConsole();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }

//        Below is code to run WorkoutPlanTrackerAppGUI
        new WorkoutPlanTrackerAppGUI();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> EventLog.getInstance().printEventLog()));
    }
}
