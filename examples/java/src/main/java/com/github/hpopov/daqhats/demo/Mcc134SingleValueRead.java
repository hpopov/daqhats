package com.github.hpopov.daqhats.demo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.github.hpopov.daqhats.TCHatId;
import com.github.hpopov.daqhats.ThermoCoupleHat;
import com.github.hpopov.daqhats.ThermoCoupleType;
import com.github.hpopov.daqhats.ThermoCoupleValue;

public class Mcc134SingleValueRead {

    public static void main(String[] args) throws InterruptedException, IOException {
        ThermoCoupleType thermoCoupleType = ThermoCoupleType.J;
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
        try (ThermoCoupleHat hat = ThermoCoupleHat.open(firstHatId, thermoCoupleType)) {
            int lowestChannel = hat.getLowestChannel();
            int highestChannel = hat.getHighestChannel();
            System.out.println(String.format("    Channels: %d - %d", lowestChannel, highestChannel));

            System.out.println("\nPress any key to continue");

            System.in.readNBytes(2);

            System.out.println("Acquiring data ... Press any key to abort\n");

            // Display the header row for the data table
            System.out.println("  Sample");
            for (int channel = lowestChannel; channel <= highestChannel; channel++) {
                System.out.println(String.format("     Channel %d", channel));
            }
            System.out.println("");

            int samplesPerChannel = 0;

            while (System.in.available() == 0) {
                // Display the updated samples per channel
                System.out.println(String.format("\r%8d", ++samplesPerChannel));
                // Read a single value from each selected channel
                for (int channel = lowestChannel; channel <= highestChannel; channel++) {
                    ThermoCoupleValue value = hat.readThermoCoupleValue(channel);
                    System.out.println("\t" + value.getValue());
                }

                TimeUnit.MILLISECONDS.sleep(milliSecondsBetweenReads);
            }
        }
    }
}
