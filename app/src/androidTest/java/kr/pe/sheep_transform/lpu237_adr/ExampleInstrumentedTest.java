package kr.pe.sheep_transform.lpu237_adr;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <custom_simple_dropdown_item_1line href="http://d.android.com/tools/testing">Testing documentation</custom_simple_dropdown_item_1line>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("kr.pe.sheep_transform.lpu237_adr", appContext.getPackageName());
    }
}
