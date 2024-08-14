package com.reea.fantestic.level01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * READ THIS
 *
 * This class is testing the Cloud class using basic assertions.
 * We are using both AssertJ and JUnit5 for the sake of example.
 *
 * Step 1:
 * - Almost ALL existing tests should be FAILING in the beginning.
 * - WITHOUT changing this test class at all, correct the code from the **Cloud** class and make them all PASS.
 *
 * Step 2:
 * - Now that you corrected the Cloud class, run the tests with coverage
 * - You will notice that the line coverage is not 100% because of two lines that were not tested at all
 * - Write unit tests for the two important lines that were not covered, choose whichever library you want from the two presented
 * - The new line coverage should be close to 100% for this level to be passed (the missing percents are caused by Lombok)
 */
public class CloudTest {

    // Using constants with suggestive names makes the unit tests more readable
    private static final int ORIGINAL_ALTITUDE = 100;
    private static final int VALID_MOVEMENT = 50;
    private static final int INVALID_MOVEMENT = -40;
    private static final int INVALID_MOVEMENT_UP = 20_000;
    private static final int INVALID_MOVEMENT_DOWN = 101;

    // The @test annotation should never be omitted. Make sure you are importing the JUnit5 one, not the JUnit4 version (5 has jupiter in the package name)
    @Test
    // The @DisplayName annotation gives more insight into what a test must do. It explains the test for people that might have to read it after a longer period of time
    // Remember that a unit test is testing one and only one possible path
    @DisplayName("For floatUp(), when given a movement that results in an altitude lower than the max possible, update the altitude.")
    // Please do not use the word "test" in the method name as it is redundant.
    // Keep the method name as short as possible, let de description inside @DisplayName give more details
    public void floatUpMovementSucceeds() {
        Cloud cloud = getCloudForTesting();
        cloud.floatUp(VALID_MOVEMENT);

        // AssertJ
        assertThat(cloud).isNotNull();
        assertThat(cloud.getAltitude()).isEqualTo(VALID_MOVEMENT + ORIGINAL_ALTITUDE);
        // JUnit 5
        assertNotNull(cloud);
        assertEquals(cloud.getAltitude(), VALID_MOVEMENT + ORIGINAL_ALTITUDE);
    }

    @Test
    @DisplayName("For floatUp(), when given an invalid movement, throw an IllegalArgumentException.")
    public void floatUpMovementFails(){
        Cloud cloud = getCloudForTesting();

        assertNotNull(cloud);
        assertThrows(IllegalArgumentException.class,
                () -> cloud.floatUp(INVALID_MOVEMENT));
    }

    @Test
    @DisplayName("For floatUp(), when given a movement that results in an altitude higher than the max possible, do not update the altitude.")
    public void floatUpMovementStaysSame() {
        Cloud cloud = getCloudForTesting();
        cloud.floatUp(INVALID_MOVEMENT_UP);

        // AssertJ
        assertThat(cloud.getAltitude()).isEqualTo(ORIGINAL_ALTITUDE);
        // JUnit5
        assertEquals(cloud.getAltitude(), ORIGINAL_ALTITUDE);
    }

    @Test
    @DisplayName("For floatDown(), when given a movement that result in an altitude above 0, update the altitude.")
    public void floatDownMovementSucceeds() throws CloudWentDownException {
        Cloud cloud = getCloudForTesting();
        cloud.floatDown(VALID_MOVEMENT);

        // AssertJ
        assertThat(cloud.getAltitude()).isGreaterThan(0);
        assertThat(cloud.getAltitude()).isEqualTo(ORIGINAL_ALTITUDE - VALID_MOVEMENT);

        // JUnit5
        assertTrue(cloud.getAltitude() > 0); // notice that JUnit5 does not have an equivalent for isGreaterThan from AssertJ
        assertEquals(cloud.getAltitude(), ORIGINAL_ALTITUDE - VALID_MOVEMENT);
    }

    @Test
    @DisplayName("For floatDown(), when given a movement that result in an altitude lower than 0, throw a CloudWentDownException.")
    public void floatDownMovementFails() {
        Cloud cloud = getCloudForTesting();

        // AssertJ
        assertThatThrownBy(() -> cloud.floatDown(INVALID_MOVEMENT_DOWN))
                .isInstanceOf(CloudWentDownException.class)
                .hasMessage("The cloud no longer exists!");
        // JUnit5
        assertThrows(CloudWentDownException.class,
                () -> cloud.floatDown(INVALID_MOVEMENT_DOWN));
    }

    @Test
    @DisplayName("For floatDown(), when given an invalid movement, throw an IllegalArgumentException.")
    public void floatDownMovementThrowsError(){
        Cloud cloud = getCloudForTesting();

        assertNotNull(cloud);
        assertThrows(IllegalArgumentException.class,
                () -> cloud.floatDown(INVALID_MOVEMENT));
    }

    // We can use this kind of util methods in order to avoid duplicated code.
    // It also eases the test object creation, especially when we have lots of fields (think about an entity with 10 fields).
    private Cloud getCloudForTesting() {
        return new Cloud(ORIGINAL_ALTITUDE);
    }

    // TODO: Write unit tests for the two lines that were not touched by the existing tests.
}
