package br.com.banconeon;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import br.com.banconeon.ui.activity.MainActivity_;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity_> mMainActivity = new ActivityTestRule<>(
            MainActivity_.class);

    @Test
    public void executarTesteCarregamentoDados() {
        //Realiza o Swype para esquerda
        onView(withId(R.id.btnHistorico)).perform(click());

        //Realiza o Swype para esquerda
        onView(withId(R.id.historylist)).perform(swipeUp());

        //Realiza o Swype para esquerda
        onView(withId(R.id.graphList)).perform(swipeLeft());
    }
}
