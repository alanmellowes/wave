package com.alan.wave;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegisterTests {

    @Rule
    public ActivityTestRule<RegisterActivity> mRegisterActivityActivityTestRule =
            new ActivityTestRule<RegisterActivity>(RegisterActivity.class);

    @Test
    public void enterRegistrationDetails_RegistersUser() {
        String email = "alan14@gmail.com";
        String password = "123456";
        onView(withId(R.id.email2)).perform(typeText(email));
                //.check();
        onView(withId(R.id.rpassword)).perform(typeText(password));
        onView(withId(R.id.rpassword2)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(click());
        //.check();
    }
}
