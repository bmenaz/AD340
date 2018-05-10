package com.android.bmeng.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.matcher.BundleMatchers;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.intent.matcher.UriMatchers.hasHost;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class TestSenderFailSuccessTest {

    private static final String TEST_MESSAGE = "test message";
    private static final String MESSAGE_PACKAGE = "com.android.bmeng.myapplication";

    @Rule
    public IntentsTestRule<TextSender> mActivityIntentsTestRule = new IntentsTestRule<>(TextSender.class);


    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void testSuccessSendMessage(){
        ViewInteraction appCompatEditText = onView(allOf(
                withId(R.id.editText),
                isDisplayed())).perform(replaceText(TEST_MESSAGE));
        ViewInteraction button = onView(withId(R.id.button)).perform(click());
        //check the intent if it was fired
        intended(allOf(
                toPackage(MESSAGE_PACKAGE),
                hasExtras(BundleMatchers.hasValue(TEST_MESSAGE))));
    }

    @Test
    public void testFailSendMessage(){
        ViewInteraction appCompatEditText = onView(allOf(
                withId(R.id.editText),
                isDisplayed())).perform(replaceText(""));
        ViewInteraction button = onView(withId(R.id.button)).perform(click());
        onView(withText("Message wasn't entered")).inRoot(withDecorView(not(mActivityIntentsTestRule.
                getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }

}
