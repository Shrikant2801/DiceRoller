package com.example.diceroller

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Preview
@Composable
fun app(modifier: Modifier = Modifier, innerPadding: PaddingValues = PaddingValues()) {
    var scorePlayer1 by remember { mutableStateOf(0) }
    var scorePlayer2 by remember { mutableStateOf(0) }
    var isPlayer1Turn by remember { mutableStateOf(true) }
    var currentImage by remember { mutableStateOf(0) }
    val images = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    // Colors
    val primaryColor = Color(0xFF078ABF)
    val secondaryColor = Color(0xFF009688)
    val backgroundColor = Color(0xFFF2F2F2)
    val buttonColor = Color(0xFFFFD700)

    // Define the game screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(innerPadding)
    ) {
        // Winner display and restart button
        if (scorePlayer1 >= 20 || scorePlayer2 >= 20) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (scorePlayer1 > scorePlayer2) "Player 1 Won!" else "Player 2 Won!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Restart button
                    Button(
                        onClick = {
                            scorePlayer1 = 0
                            scorePlayer2 = 0
                            isPlayer1Turn = true
                            currentImage = 0
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = secondaryColor),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "Restart Game",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        } else {
            // Main game UI
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isPlayer1Turn) "Player 1's Turn" else "Player 2's Turn",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Display the dice image
                Image(
                    painter = if (currentImage == 0) {
                        painterResource(R.drawable.dice)
                    } else {
                        painterResource(images[currentImage - 1])
                    },
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Row for player actions (buttons)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Player 1 roll button
                    Button(
                        onClick = {
                            val random = Random.nextInt(6) + 1
                            currentImage = random
                            scorePlayer1 += random
                            isPlayer1Turn = !isPlayer1Turn
                        },
                        enabled = isPlayer1Turn,
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(width = 150.dp, height = 50.dp)
                    ) {
                        Text(text = "Player 1 Roll", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    // Player 2 roll button
                    Button(
                        onClick = {
                            val random = Random.nextInt(6) + 1
                            currentImage = random
                            scorePlayer2 += random
                            isPlayer1Turn = !isPlayer1Turn
                        },
                        enabled = !isPlayer1Turn,
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(width = 150.dp, height = 50.dp)
                    ) {
                        Text(text = "Player 2 Roll", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Display scores
                Text(
                    text = "Player 1: $scorePlayer1",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = secondaryColor
                )
                Text(
                    text = "Player 2: $scorePlayer2",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = secondaryColor
                )
            }
        }
    }
}
