package org.example.script;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

public class RunShellScript {
    public static String run(String scriptPath, String subnetId) {
        try {
            // Create process builder
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, subnetId);
            // Redirect error stream to output stream
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Get input stream of the process
            InputStream inputStream = process.getInputStream();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            return output.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String register(String scriptPath, String subnetId, String threshold, String hotkey) {
        try {
            // Create process builder
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, subnetId, threshold, hotkey);
            // Redirect error stream to output stream
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Get input stream of the process
            InputStream inputStream = process.getInputStream();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            return output.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPrice(String netId, String coldKey, String hotKey) throws Exception {
        String command = String.format("btcli subnet register --netuid %s --wallet.name %s --wallet.hotkey %s", netId, coldKey, hotKey);
        String networkAnswer = "finney";
        String isContinueAnswer = "n";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();
            process.getOutputStream().write((networkAnswer + "\n").getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().write((isContinueAnswer + "\n").getBytes());
            process.getOutputStream().flush();

            // Get input stream of the process
            InputStream inputStream = process.getInputStream();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            String[] array = output.toString().split("\n");
            return array[1].split("τ")[1];
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String checkBalance() throws Exception {
        String command = String.format("btcli subnet register --netuid %s --wallet.name %s --wallet.hotkey %s", "6", "default", "jackie_hotkey_22");
        String networkAnswer = "finney";
        String isContinueAnswer = "n";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();
            process.getOutputStream().write((networkAnswer + "\n").getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().write((isContinueAnswer + "\n").getBytes());
            process.getOutputStream().flush();

            // Get input stream of the process
            InputStream inputStream = process.getInputStream();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            String[] array = output.toString().split("\n");
            return array[0].split("τ")[1];
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static String autoRegister(String netId, String coldKey, String hotKey) throws Exception {
        String command = String.format("btcli subnet register --netuid %s --wallet.name %s --wallet.hotkey %s", netId, coldKey, hotKey);
        String networkAnswer = "finney";
        String isContinueAnswer = "y";
        String password = "Iltmt@e1";
        String recycleAnswer = "n";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();
            process.getOutputStream().write((networkAnswer + "\n").getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().write((isContinueAnswer + "\n").getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().write((password + "\n").getBytes());
            process.getOutputStream().flush();
            // Get input stream of the process
            InputStream inputStream = process.getInputStream();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                System.out.println(output.toString());
                output.append(line).append("\n");
                if(output.toString().contains("to register on subnet")){
                    process.getOutputStream().write((recycleAnswer + "\n").getBytes());
                    process.getOutputStream().flush();
                }
            }

            String[] array = output.toString().split("\n");
            return array[1].split("τ")[1];
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static void main(String[] args) {
        try {
            String netId = "6";
            String coldKey = "default";
            String hotKey = "jackie_hotkey_22";
            String command = String.format("btcli subnet register --netuid %s --wallet.name %s --wallet.hotkey %s", netId, coldKey, hotKey);

            List<String> inputs = Arrays.asList("finney", "y", "Iltmt@e1", "n");

            // Create ProcessBuilder and start the process
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(command.split(" "));
            Process process = processBuilder.start();

            // Get process input and output streams
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(process.getOutputStream());

            // Send input value to the command and get output
            boolean isReadOutput = true;

            for (String input: inputs) {
                // Write input value to the process
                outputStreamWriter.write(input + "\n");
                outputStreamWriter.flush();

                // Read output from the process
                String line;
                while (isReadOutput && (line = inputReader.readLine()) != null) {
                    System.out.println(line);
                    if(line.contains("The cost to register by recycle")){
                        String price = line.split("τ")[1];

                        if(Double.valueOf(price).compareTo(0.01D) < 0){
                            inputs.set(3, "y");
                        }
                        isReadOutput = false;
                        break;
                    }
                }

                // Read error messages, if any
                while (isReadOutput && (line = errorReader.readLine()) != null) {
                    System.err.println("Error: " + line);
                }
            }

            // Close the input stream to signal the end of input
            outputStreamWriter.close();

            // Wait for the command to finish
            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}