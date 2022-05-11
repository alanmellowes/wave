package com.alan.wave;

import android.app.Activity;
import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginTests {

    @Rule
    public ActivityTestRule<LogActivity> mLogActivityActivityTestRule =
            new ActivityTestRule<LogActivity>(LogActivity.class);

    @Test
    public void enterLoginDetails_logsUserIn() {
        String email = "alan14@gmail.com";
        String password = "123456";
        onView(withId(R.id.email)).perform(typeText(email));
        onView(withId(R.id.password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton2)).perform(click());
    }
}
