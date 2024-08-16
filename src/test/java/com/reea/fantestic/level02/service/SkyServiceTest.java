package com.reea.fantestic.level02.service;

import com.reea.fantestic.level01.Cloud;
import com.reea.fantestic.level02.entity.Sky;
import com.reea.fantestic.level02.exception.SkyNotFoundException;
import com.reea.fantestic.level02.repository.FakeSkyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * There are explanations for everything mockito related in the code.
 *
 * Please complete the TODOs you can find in here without changing the code we are testing
 * (you are allowed to change only this class)
 *
 * Step 1:
 * - fix the tests that have small mistakes in them
 *
 * Step 2:
 * - write new tests from scratch for the create method
 * - for that you will need to implement a method that returns how the sky object will look like after insertion/creation
 */

// Do not forget to put this annotation. This is what actually enables the mocking
@ExtendWith(MockitoExtension.class)
public class SkyServiceTest {

    // The class we want to test usually has the @InjectMocks annotation
    // to point that the mocks should be injected here
    @InjectMocks
    SkyService skyService;

    // The @Mock annotation means that the object is actually mocked,
    // a fake that behaves how we want it to without using a real instance.
    //
    // We have to mock the classes that are called by the one we are testing
    // so that we test ONE AND ONLY ONE thing with the unit test.
    // Otherwise, the classes would be communicating and that destroys the
    // purpose of a unit test.
    @Mock
    FakeSkyRepository fakeSkyRepository;

    private static final String UPPERCASE_NAME = "SKYTEST";
    private static final String LOWERCASE_NAME = "skytest";

    @Test
    @DisplayName("For findByName(), when looking for a valid sky," +
            "using a lowercase name, return the sky with the same name.")
    void findSkyByNameSuccessful() throws SkyNotFoundException {
        // The typical structure of a test is: *given, when, then*

        // GIVEN - what we are given in the beginning of the test (e.g. initialize arguments, mock the methods that we have to)
        Sky sky = getSkyForTesting();

        // The *when* method is what does the actual method mocking
        // You tell *when* what method from the mock will be called, with what *arguments* and also what should be returned
        // The argument matching is a science itself as you can have primitives, strings and other objects as arguments
        //      any() - for anything
        //      any(ClassName.class) - for an argument of a certain class
        //      eq(sth) - needs to equal what is inside, uses equals
        //      anyString() - this is any but for strings
        when(fakeSkyRepository.findByName(eq(LOWERCASE_NAME))).thenReturn(Optional.of(sky)); // <- I know it is a little confusing that the when's are in the given part

        // WHEN - what we are actually testing (calling the method with the appropriate arguments)
        Sky result = skyService.findByName(LOWERCASE_NAME);

        // THEN - checking that everything went fine (assertions and verify-ing the mocked methods were actually called how we wanted)
        assertThat(result).isEqualTo(getSkyForTesting());

        // The *verify* method is using the same argument matching as when
        // This actually checks if the mocked method from *when* is actually called as we wanted
        // You can even check how many times it was called
        verify(fakeSkyRepository, times(1)).findByName(eq(LOWERCASE_NAME));
    }

    // TODO: There is a very small mistake in this test. Solve it and make the test run green.
    @Test
    @DisplayName("For findByName(), when looking for a valid sky by name," +
            "using a name that is not lowercase, return the sky with the name in lowercase.")
    void findSkyByNameSuccessfulConverted() throws SkyNotFoundException {
        // The typical structure of a test is: *given, when, then*

        // GIVEN - what we are given in the beginning of the test (e.g. initialize arguments, mock the methods that we have to)
        Sky sky = getSkyForTesting();
        when(fakeSkyRepository.findByName(eq(UPPERCASE_NAME.toLowerCase()))).thenReturn(Optional.of(sky));

        // WHEN - what we are actually testing (calling the method with the appropriate arguments)
        Sky result = skyService.findByName(UPPERCASE_NAME);

        // THEN - checking that everything went fine (assertions and verify-ing the mocked methods were actually called how we wanted)
        assertThat(result).isEqualTo(getSkyForTesting());
        verify(fakeSkyRepository, times(0)).findByName(eq(UPPERCASE_NAME));
    }

    // TODO: There is a very small mistake in this test. Solve it and make the test run green.
    @Test
    @DisplayName("For findByName(), when looking for a sky that does not exist, " +
            "throw a SkyNotFoundException.")
    void findByNameFailure() {
        // GIVEN
        when(fakeSkyRepository.findByName(anyString())).thenReturn(Optional.empty());

        // WHEN + THEN - when checking for exceptions, we usually merge the when and then using the appropriate assertion
        assertThrows(SkyNotFoundException.class, () -> skyService.findByName(LOWERCASE_NAME));
        verify(fakeSkyRepository, times(1)).findByName(anyString());
    }

    // TODO: There is a very small mistake in this test. Solve it and make the test run green.
    @Test
    @DisplayName("For findByName(), when looking for a valid sky," +
            "based on a lowercase name and a cloud," +
            "return the sky with the same name.")
    void findByNameAndCloudSuccessful() throws SkyNotFoundException {
        // GIVEN - what we are given in the beginning of the test (e.g. initialize arguments, mock the methods that we have to)
        Sky sky = getSkyForTesting();
        when(fakeSkyRepository.findByNameAndCloud(eq(UPPERCASE_NAME.toLowerCase()), eq(sky.getClouds().get(0)))).thenReturn(Optional.of(sky));

        // WHEN - what we are actually testing (calling the method with the appropriate arguments)
        Sky result = skyService.findByNameAndCloud(UPPERCASE_NAME, sky.getClouds().get(0));

        System.out.println(result);

        // THEN - checking that everything went fine (assertions and verify-ing the mocked methods were actually called how we wanted)
        assertThat(result).isEqualTo(getSkyForTesting());
        verify(fakeSkyRepository, times(1)).findByNameAndCloud(eq(UPPERCASE_NAME.toLowerCase()), any(Cloud.class));
    }

    // TODO: There is a very small mistake in this test. Solve it and make the test run green.
    @Test
    @DisplayName("For deleteByName(), when wanting to delete a sky by name," +
            "do it and return nothing")
    void deleteByNameSuccessful() {
        doNothing().when(fakeSkyRepository).deleteByName(LOWERCASE_NAME);
        skyService.deleteByName(UPPERCASE_NAME);
        verify(fakeSkyRepository, times(0)).deleteByName(eq("SKYTST"));
    }

    // this utility object is used to ease the given part of the tests
    private Sky getSkyForTesting() {
        Sky sky = new Sky();
        sky.setClouds(new ArrayList<>());
        for (int i = 0; i < 3; i++) {
            Cloud cloud = new Cloud((i + 100) * i);
            sky.getClouds().add(cloud);
        }
        sky.setName(LOWERCASE_NAME);
        sky.setDaylightDuration(Duration.ofHours(14));
        return sky;
    }

    // TODO: write the unit tests for the method createSky() in the same manner and using the utility function getSkyAfterCreation() that you will implement
    // 1 test for when the cloud altitude is increased and everything goes well - do not forget to check the clouds
    // 1 test for then the sky does not exist

    @Test
    @DisplayName("For createSky(), if you increase the altitude, everything should go well")
    void createSkyIncreaseAltitude() {
        Sky initialSky = getSkyForTesting();
        Sky expectedSky = getSkyAfterCreation();
    }

    // TODO: create a Sky object identical to the one the method createSky() would return for the one created in getSkyForTesting()
    // see how the method behaves and create how the result is supposed to look like
    private Sky getSkyAfterCreation() {
        return getSkyForTesting(); // change what should be changed inside it
    }
}
