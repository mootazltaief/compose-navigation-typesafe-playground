package com.ltaief.navigationtypesafe

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ltaief.navigationtypesafe.TopLevelDestination.Home
import com.ltaief.navigationtypesafe.TopLevelDestination.Profile
import com.ltaief.navigationtypesafe.TopLevelDestination.Search
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MyBottomBar(navController)
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithText("Home")
            .assertIsDisplayed()
        assertTrue(navController.currentDestination?.route == Home::class.qualifiedName)
    }

    @Test
    fun appNavHost_verifySearchDestination() {
        composeTestRule
            .onNodeWithContentDescription("Search")
            .performClick()
        assertTrue(navController.currentDestination?.route == Search::class.qualifiedName)
    }

    @Test
    fun appNavHost_verifyProfileDestination() {
        composeTestRule
            .onNodeWithContentDescription("Profile")
            .performClick()
        val profileQualifiedName = requireNotNull(Profile::class.qualifiedName)
        // it won't be equal because of the dynamic name of the profile parameter
        // -> com.ltaief.navigationtypesafe.TopLevelDestination.Profile/{name}
        assertTrue(navController.currentDestination?.route?.contains(profileQualifiedName) == true)
    }

}