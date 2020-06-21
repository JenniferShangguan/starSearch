public class Termination {
    ScenarioFile sFile = new ScenarioFile();
    int MAX_DRONES = sFile.getMAX_DRONES();
    int OK_CODE = sFile.getOK_CODE();
    int CRASH_CODE = sFile.getCRASH_CODE();
    int regionWidth = sFile.getRegionWidth();
    int regionHeight = sFile.getRegionHeight();
    Integer[] droneStatus = sFile.getDroneStatus();
    Integer[][] regionInfo = sFile.getRegionInfo();

    public Boolean dronesAllStopped() {
        for(int k = 0; k < MAX_DRONES; k++) {
            if (droneStatus[k] == OK_CODE) { return Boolean.FALSE; }
        }
        return Boolean.TRUE;
    }

    public Boolean droneStopped(int id) {
        return droneStatus[id] == CRASH_CODE;
    }

    public void finalReport(int completeTurns) {
        int regionSize = regionWidth * regionHeight;
        int numSuns = 0;
        int numStars = 0;
        for (int i = 0; i < regionWidth; i++) {
            for (int j = 0; j < regionHeight; j++) {
                if (regionInfo[i][j] == sFile.SUN_CODE) { numSuns++; }
                if (regionInfo[i][j] == sFile.STARS_CODE) { numStars++; }
            }
        }
        int potentialCut = regionSize - numSuns;
        int actualCut = potentialCut - numStars;
        System.out.println(String.valueOf(regionSize) + "," + String.valueOf(potentialCut) + "," + String.valueOf(actualCut) + "," + String.valueOf(completeTurns));
    }
}
