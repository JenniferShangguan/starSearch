public class Main {

    public static void main(String[] args) {
        ScenarioFile sFile = new ScenarioFile();
        SimManager simManager = new SimManager();
        Drones drones = new Drones();
        Monitor monitor = new Monitor();
        Termination termination = new Termination();

        int trackTurnsCompleted = 0;
        Boolean showState = Boolean.FALSE;

        // check for the test scenario file name
        if (args.length < 1) {
            System.out.println("ERROR: Test scenario file name not found.");
            return;
        }

        if (args.length >= 2 && (args[1].equals("-v") || args[1].equals("-verbose"))) { showState = Boolean.TRUE; }

        sFile.uploadStartingFile(args[0]);

        // run the simulation for a fixed number of steps
        for(int turns = 0; turns < monitor.simulationDuration(); turns++) {
            trackTurnsCompleted = turns;

            if (termination.dronesAllStopped()) { break; }

            for (int k = 0; k < monitor.droneCount(); k++) {

                if (termination.droneStopped(k)) { continue; }

                simManager.pollDroneForAction(k);
                simManager.validateDroneAction(k);
                simManager.displayActionAndResponses(k);

                // render the state of the space region after each command
                if (showState) { monitor.renderRegion(); }
            }
        }

        termination.finalReport(trackTurnsCompleted);
    }

}

