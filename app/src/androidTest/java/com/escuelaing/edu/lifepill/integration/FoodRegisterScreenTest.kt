package com.escuelaing.edu.lifepill.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.escuelaing.edu.lifepill.ui.screens.homeScreen.UserHomeScreen.FoodRegisterScreen
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodRegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun foodRegisterScreen_displaysCorrectly() {
        composeTestRule.setContent {
            LifePillTheme {
                FoodRegisterScreen()
            }
        }

        composeTestRule.onNodeWithText("Agregar Comida").assertIsDisplayed()
        composeTestRule.onNodeWithText("Resultados de búsqueda:").assertExists()
    }

    @Test
    fun foodRegisterScreen_searchField_acceptsInput() {
        composeTestRule.setContent {
            LifePillTheme {
                FoodRegisterScreen()
            }
        }

        composeTestRule
            .onNodeWithText("Buscar alimento...")
            .performTextInput("Manzana")

        composeTestRule
            .onNodeWithText("Manzana")
            .assertIsDisplayed()
    }

    @Test
    fun foodRegisterScreen_addButton_opens_dialog() {
        composeTestRule.setContent {
            LifePillTheme {
                FoodRegisterScreen()
            }
        }

        composeTestRule
            .onNodeWithText("Agregar Comida")
            .performClick()

        composeTestRule
            .onNodeWithText("¿Cómo quieres agregar tu comida?")
            .assertIsDisplayed()
    }

    @Test
    fun foodRegisterScreen_foodList_displays() {
        composeTestRule.setContent {
            LifePillTheme {
                FoodRegisterScreen()
            }
        }

        composeTestRule.onNodeWithText("Manzana").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pollo a la plancha").assertIsDisplayed()
    }

    @Test
    fun foodRegisterScreen_selectFood_showsSaveButton() {
        composeTestRule.setContent {
            LifePillTheme {
                FoodRegisterScreen()
            }
        }

        // Select a food item
        composeTestRule
            .onAllNodesWithText("+")[0]
            .performClick()

        // Verify save button appears
        composeTestRule
            .onNodeWithText("Guardar Registro")
            .assertIsDisplayed()
    }
}