package com.mrp.sml;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivitySmokeTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void launchMainActivity_smokeTest() {
        activityRule.getScenario().onActivity(activity -> {
            // Smoke launch assertion: activity starts and is not finishing.
            if (activity.isFinishing()) {
                throw new AssertionError("MainActivity should be active");
            }
        });
    }
}
