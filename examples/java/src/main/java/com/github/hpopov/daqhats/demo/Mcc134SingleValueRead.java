package com.github.hpopov.daqhats.demo;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.github.hpopov.daqhats.TCHatId;
import com.github.hpopov.daqhats.ThermoCoupleHat;
import com.github.hpopov.daqhats.ThermoCoupleType;
import com.github.hpopov.daqhats.ThermoCoupleValue;

public class Mcc134SingleValueRead {

    public static void main(String[] args) throws InterruptedException, IOException {
        ThermoCoupleType thermoCoupleType = ThermoCoupleType.K;
        long milliSecondsBetweenReads = 1000;

        System.out.println("\nMCC 134 single data value read example");
        System.out.println("    Function demonstrated: mcc134_t_in_read");
        System.out.println("    Thermocouple type: " + thermoCoupleType);

        TCHatId[] connectedHatIds = TCHatId.findConnectedHats();
        if (connectedHatIds.length == 0) {
            System.out.println("No connected Hats found. Exiting now...");
            return;
        }
        TCHatId firstHatId = connectedHatIds[0];
        System.out.println(String.format("Found %d connected hats. Using the first one with address %d",
                connectedHatIds.length, firstHatId.getAddress()));

        Scanner scanner = new Scanner(System.in);
        try (ThermoCoupleHat hat = ThermoCoupleHat.open(firstHatId, thermoCoupleType)) {
            int lowestChannel = hat.getLowestChannel();
            int highestChannel = hat.getHighestChannel();
            System.out.println(String.format("    Channels: %d - %d", lowestChannel, highestChannel));

            System.out.println("\nPress enter to continue");

            scanner.nextLine();

            System.out.println("Acquiring data ... Press enter to abort\n");

            // Display the header row for the data table
            System.out.print("  Sample\t");
            for (int channel = lowestChannel; channel <= highestChannel; channel++) {
                System.out.print(String.format("       Channel %1d\t", channel));
            }
            System.out.println("");

            int samplesPerChannel = 0;

            for (int i = 0; i < 25; i++) {
                // Display the updated samples per channel
                System.out.print(String.format("\r%8d\t", ++samplesPerChannel));
                // Read a single value from each selected channel
                for (int channel = lowestChannel; channel <= highestChannel; channel++) {
                    ThermoCoupleValue value = hat.readThermoCoupleValue(channel);
                    System.out.print(String.format("%16s", value.getValue()) + "\t");
                }

                TimeUnit.MILLISECONDS.sleep(milliSecondsBetweenReads);
            }
            System.out.println();
        } finally {
            scanner.close();
        }
    }
}
