package org.example.script;

public class RunScript {
    public static void runScript(String scriptName) {
        try {
            System.out.println("start runScript: " + scriptName);
            // Make the script executable
            Runtime.getRuntime().exec(new String[] {"chmod", "+x", scriptName});

            // Execute the script
            Process p = Runtime.getRuntime().exec(new String[] {scriptName});
            p.waitFor();
            System.out.println("End runScript: " + scriptName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

