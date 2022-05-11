package com.alan.wave;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.regex.Pattern.matches;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
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
public class CryptoTests {

    @Rule
    public ActivityTestRule<cryptoActivity> mCryptoActivityActivityTestRule =
            new ActivityTestRule<cryptoActivity>(cryptoActivity.class);

    @Test
    public void enterCryptoName_DisplaysCrypto() {
        String cryptocurrency = "DOT";
        onView(withId(R.id.EditSearch)).perform(typeText(cryptocurrency));
        Espresso.closeSoftKeyboard();
        onView(withText(cryptocurrency));
    }
}
