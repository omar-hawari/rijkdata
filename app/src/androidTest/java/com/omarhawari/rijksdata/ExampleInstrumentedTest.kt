package com.omarhawari.rijksdata

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.omarhawari.rijksdata.presentation.MainActivity
import com.omarhawari.rijksdata.presentation.PARAM_OBJECT_NUMBER
import com.omarhawari.rijksdata.presentation.Screen
import com.omarhawari.rijksdata.presentation.art_object_details.ArtObjectDetails
import com.omarhawari.rijksdata.presentation.art_objects_list.ArtObjectList
import com.omarhawari.rijksdata.theme.MyApplicationTheme
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.omarhawari.rijksdata", appContext.packageName)
    }
}

@HiltAndroidTest
//@CustomTestApplication(HiltTestApplication::class)
class MyComposeTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {

            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ArtObjectsListScreen.route
                    ) {
                        composable(route = Screen.ArtObjectsListScreen.route) {
                            ArtObjectList(navController = navController)
                        }
                        composable(
                            route = Screen.ArtObjectDetailsScreen.route + "/{$PARAM_OBJECT_NUMBER}"
                        ) {
                            ArtObjectDetails(navController = navController)
                        }
                    }
                }
            }
        }

        composeTestRule.onNodeWithTag("art_object_item").performClick()

        composeTestRule.onRoot().assertIsDisplayed()
    }
}
