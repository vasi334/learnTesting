package com.reea.fantestic.level01;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * There are some mistakes in this class.
 *
 * Correct them and make the tests in CloudTest pass.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class Cloud {

    public static final int MAX_ALTITUDE = 12_000;
    private int altitude;

    /**
     * Moves a cloud up in the sky a number of meters (positive integer). The altitude property is updated.
     * @param movement the number of meters the cloud goes up
     * @return the new altitude if it does not exceed the maximum possible, otherwise stays the same
     */
    public int floatUp(int movement) {
        if (movement <=  0) {
            throw new IllegalArgumentException("The cloud cannot go up a negative number of meters! That means it is going down.");
        }

        int newAltitude = altitude + movement;
        if (newAltitude > MAX_ALTITUDE) {
            log.info("A cloud cannot go higher than {}. We are staying here.", MAX_ALTITUDE);
        } else {
            altitude = newAltitude;
        }
        return altitude;
    }

    /**
     * Moves a cloud down a number of meters (positive integer). The altitude property is updated.
     * @param movement the number of meters the cloud goes down
     * @return the new altitude if it does not go lower than the ground, otherwise throws an exception
     * @throws CloudWentDownException thrown when the cloud reaches the ground
     */
    public int floatDown(int movement) throws CloudWentDownException {
        if (movement <=  0) {
            throw new IllegalArgumentException("The cloud cannot go down a negative number of meters! That means it is going up.");
        }

        int newAltitude = altitude - movement;
        if (newAltitude <= 0) {
            throw new CloudWentDownException("The cloud no longer exists!");
        } else {
            altitude = newAltitude;
        }

        return altitude;
    }

}
