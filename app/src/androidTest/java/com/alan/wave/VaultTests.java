package com.alan.wave;//package com.alan.wave;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
public class VaultTests {

    @Rule
    public ActivityTestRule<VaultActivity> mVaultActivityActivityTestRule =
            new ActivityTestRule<VaultActivity>(VaultActivity.class);

    @Test
    public void enterVaultAmount_addsMoneyToVault() {
        String amount = "1000";
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.amount)).perform(typeText(amount));
        onView(withId(R.id.save)).perform(click());
        Espresso.closeSoftKeyboard();
    }
}
