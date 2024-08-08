package com.example.finalproj.views

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproj.Eat2Fit
import com.example.finalproj.R
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Eat2FitScaffold
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.components.HeaderLogo
import com.example.finalproj.components.NavigateBackArrow
import com.example.finalproj.database.models.Question
import com.example.finalproj.database.models.QuizQuestions
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.util.icons.rememberTimer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun QuizIntroductionScreen(popBack: () -> Boolean, navigateToQuiz: () -> Unit) {
    Eat2FitScaffold(
        topBar = { HeaderLogo() }
    ) { paddingValues ->
        Eat2FitSurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            backgroundImage = painterResource(id = R.drawable.quizbg)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                NavigateBackArrow(popBack)
                Image(
                    painterResource(id = R.drawable.question_marks_illustration),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(400.dp, 200.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            color = Eat2FitTheme.colors.lightGreen,
                            text = "QUIZ",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            color = Color.Gray,
                            text = "The QUIZ IS BASED ON THE INFORMATION PAGES ON THIS APP.\n\nLET'S SEE HOW WELL YOU CAN REMEMBER WHAT YOU JUST READ",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = rememberTimer(),
                                contentDescription = null
                            )
                            Text(
                                text = "Time\n10 sec.",
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Eat2FitButton(
                        onClick = navigateToQuiz,
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 24.dp)
                            .size(80.dp, 80.dp)
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuizQuestionScreen(
    navigateAndClear: (String) -> Unit,
    onNavigateToRoute: (String) -> Unit
) {
    val currentQuestion by remember { mutableStateOf(QuizQuestions.questions.random()) }
    var progress by remember { mutableFloatStateOf(1f) }
    var isAnswered by remember { mutableStateOf(false) }
    var answeredIndex by remember { mutableIntStateOf(-1) }

    LaunchedEffect(Unit) {
        val totalTime = 10_000L
        val interval = 20L
        var elapsedTime = 0L

        while (elapsedTime < totalTime) {
            delay(interval)
            elapsedTime += interval
            withContext(Dispatchers.Main) {
                progress = 1f - (elapsedTime / totalTime.toFloat())
            }
        }
        withContext(Dispatchers.Main) {
            isAnswered = true
        }
    }

    LaunchedEffect(isAnswered) {
        if (isAnswered) {
            delay(1000)
            val isCorrect = answeredIndex == currentQuestion.correctAnswer
            withContext(Dispatchers.Main) {
                onNavigateToRoute("${AppSections.HOME.route}/quiz/result/$isCorrect")
            }
        }
    }

    Eat2FitScaffold(
        topBar = { HeaderLogo() }
    ) { paddingValues ->
        Eat2FitSurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            backgroundImage = painterResource(id = R.drawable.quizbg)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
            ) {
                NavigateBackArrow { navigateToHome(navigateAndClear) }
                Spacer(Modifier.height(40.dp))
                Column(
                    modifier = Modifier.padding(32.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth(),
                        color = Eat2FitTheme.colors.lightGreen,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = currentQuestion.question,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.Center),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                    currentQuestion.options.forEachIndexed { index, option ->
                        val buttonColor =
                            when (index) {
                                currentQuestion.correctAnswer -> Eat2FitTheme.colors.green
                                answeredIndex -> Eat2FitTheme.colors.redGrad
                                else -> Eat2FitTheme.colors.orange
                            }
                        Eat2FitButton(
                            backgroundGradient = if (isAnswered) buttonColor else Eat2FitTheme.colors.orange,
                            onClick = {
                                isAnswered = true
                                answeredIndex = index
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = option,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun QuizResultScreen(
    isCorrect: Boolean,
    onPlayAgain: () -> Unit,
    navigateAndClear: (String) -> Unit
) {
    Eat2FitScaffold(
        topBar = { HeaderLogo() }
    ) { paddingValues ->
        Eat2FitSurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            backgroundImage = painterResource(id = R.drawable.quizbg)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavigateBackArrow { navigateToHome(navigateAndClear) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(24.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            32.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            color = if (isCorrect) Eat2FitTheme.colors.lightGreen else Eat2FitTheme.colors.red,
                            text = if (isCorrect) "Nice Work" else "Wrong Answer",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Image(
                            painter = painterResource(id = if (isCorrect) R.drawable.success else R.drawable.fail),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(200.dp, 200.dp)
                        )
                    }
                }
                Spacer(Modifier.height(40.dp))
                Eat2FitButton(
                    onClick = onPlayAgain,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (isCorrect) "Play Again" else "Try Again",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}