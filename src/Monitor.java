public class Monitor {
    ScenarioFile sFile = new ScenarioFile();
    int numberOfDrones = sFile.getNumberOfDrones();
    int turnLimit = sFile.getTurnLimit();
    Integer[] droneX = sFile.getDroneX();
    Integer[] droneY = sFile.getDroneY();
    int MAX_DRONES = sFile.getMAX_DRONES();
    int OK_CODE = sFile.getOK_CODE();
    int CRASH_CODE = sFile.getCRASH_CODE();
    int regionWidth = sFile.getRegionWidth();
    int regionHeight = sFile.getRegionHeight();
    Integer[] droneStatus = sFile.getDroneStatus();
    Integer[][] regionInfo = sFile.getRegionInfo();
    String[] droneDirection = sFile.getDroneDirection();

    public Integer simulationDuration() {
        return turnLimit;
    }

    public Integer droneCount() {
        return numberOfDrones;
    }

    private void renderHorizontalBar(int size) {
        System.out.print(" ");
        for (int k = 0; k < size; k++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    public void renderRegion() {
        int i, j;
        int charWidth = 2 * regionWidth + 2;

        // display the rows of the region from top to bottom
        for (j = regionHeight - 1; j >= 0; j--) {
            renderHorizontalBar(charWidth);

            // display the Y-direction identifier
            System.out.print(j);

            // display the contents of each square on this row
            for (i = 0; i < regionWidth; i++) {
                System.out.print("|");

                // the drone overrides all other contents
                if (droneStatus[0] == OK_CODE && i == droneX[0] && j == droneY[0]) {
                    System.out.print("0");
                } else if (droneStatus[1] == OK_CODE && i == droneX[1] && j == droneY[1]) {
                    System.out.print("1");
                } else {
                    switch (regionInfo[i][j]) {
                        case ScenarioFile.EMPTY_CODE:
                            System.out.print(" ");
                            break;
                        case ScenarioFile.STARS_CODE:
                            System.out.print(".");
                            break;
                        case ScenarioFile.SUN_CODE:
                            System.out.print("s");
                            break;
                        default:
                            break;
                    }
                }
            }
            System.out.println("|");
        }
        renderHorizontalBar(charWidth);

        // display the column X-direction identifiers
        System.out.print(" ");
        for (i = 0; i < regionWidth; i++) {
            System.out.print(" " + i);
        }
        System.out.println("");

        // display the drone's directions
        for(int k = 0; k < MAX_DRONES; k++) {
            if (droneStatus[k] == CRASH_CODE) { continue; }
            System.out.println("dir d" + String.valueOf(k) + ": " + droneDirection[k]);
        }
        System.out.println("");
    }
}
