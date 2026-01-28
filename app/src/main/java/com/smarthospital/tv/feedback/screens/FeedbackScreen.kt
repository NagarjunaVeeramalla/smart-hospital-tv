package com.smarthospital.tv.feedback.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.smarthospital.tv.feedback.datamodels.FeedbackScreenData
import com.smarthospital.tv.feedback.datamodels.Question
import com.smarthospital.tv.feedback.datamodels.SurveyFeedback
import com.smarthospital.tv.feedback.ui_states.FeedbackScreenUiState
import com.smarthospital.tv.feedback.ui_states.FeedbackSubmissionState
import com.smarthospital.tv.myhealth.ui.theme.SmartHospitalAppTheme
import com.smarthospital.tv.myhealth.ui.tvFocusDesign

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FeedbackScreen(
    feedbackScreenUiState: FeedbackScreenUiState,
    feedbackSubmissionState: FeedbackSubmissionState,
    onSubmitFeedback: (title: String, questions: List<Question>) -> Unit,
    onUpdateRating: (questionId: Int, rating: Int) -> Unit,
    onFeedbackComplete: () -> Unit = {}
) {
    val localContext = LocalContext.current
    val firstFocusRequester = remember { FocusRequester() }

    LaunchedEffect(feedbackSubmissionState) {
        when (feedbackSubmissionState) {
            is FeedbackSubmissionState.Success -> {
                Toast.makeText(localContext, feedbackSubmissionState.response.message, Toast.LENGTH_SHORT).show()
            }
            is FeedbackSubmissionState.Error -> {
                Toast.makeText(localContext, feedbackSubmissionState.message, Toast.LENGTH_LONG).show()
            }
            else -> {} // Idle or Submitting
        }
    }

    when (feedbackScreenUiState) {
        is FeedbackScreenUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is FeedbackScreenUiState.Success -> {
            val data = feedbackScreenUiState.data
            var currentQuestions by remember(data.surveyFeedback.questions) { mutableStateOf(data.surveyFeedback.questions) }
            val isButtonEnabled = currentQuestions.all { it.rating > 0 } && !data.isFeedbackComplete

            LaunchedEffect(Unit) {
                if(currentQuestions.isNotEmpty()) {
                    firstFocusRequester.requestFocus()
                }
            }

            if (data.isFeedbackComplete && feedbackSubmissionState == FeedbackSubmissionState.Idle) {
                LaunchedEffect(data.isFeedbackComplete, feedbackSubmissionState) {
                    delay(2000) // Show thanks for 2 seconds
                    onFeedbackComplete()
                }
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Thank you for your feedback!",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp).background(color = Color.DarkGray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item {
                        Text(
                            text = data.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    items(currentQuestions.size) { index ->
                        val question = currentQuestions[index]
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = question.mainQuestion, style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White, fontSize = 20.sp
                                )
                            )
                            Text(
                                text = question.subQuestion, style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.Gray, fontSize = 16.sp
                                )
                            )
                            StarRatingBar( // Assuming StarRatingBar is an existing composable
                                currentRating = question.rating,
                                onRatingChanged = { newRating ->
                                    onUpdateRating(question.id, newRating)
                                    // Update local state immediately for responsiveness
                                    currentQuestions = currentQuestions.map {
                                        if (it.id == question.id) it.copy(rating = newRating) else it
                                    }
                                },
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .then(
                                        if (index == 0) Modifier.focusRequester(firstFocusRequester)
                                        else Modifier
                                    )
                            )
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                enabled = isButtonEnabled && feedbackSubmissionState != FeedbackSubmissionState.Submitting,
                                onClick = {
                                    onSubmitFeedback(data.title, currentQuestions)
                                    currentQuestions.forEach { question ->
                                        Log.d("FeedbackScreen", "${question.mainQuestion}: ${question.rating} stars")
                                    }
                                },
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .tvFocusDesign(240.dp),
                                shape = ButtonDefaults.shape(shape = RectangleShape),
                                colors = ButtonDefaults.colors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black
                                )
                            ) {
                                if (feedbackSubmissionState == FeedbackSubmissionState.Submitting) {
                                    CircularProgressIndicator(modifier = Modifier.height(24.dp))
                                } else {
                                    Text("Submit Feedback")
                                }
                            }
                        }
                    }
                }
            }
        }

        is FeedbackScreenUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = feedbackScreenUiState.message,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(device = "id:tv_1080p")
@Composable
fun FeedbackScreenPreview() {
    SmartHospitalAppTheme {
        FeedbackScreen(
            feedbackScreenUiState = FeedbackScreenUiState.Success(
                FeedbackScreenData(
                    title = "Patient Feedback",
                    surveyFeedback = SurveyFeedback(
                        questions = listOf(
                            Question(id = 1, mainQuestion = "How was your overall experience?", subQuestion = "Rate your stay", rating = 4),
                            Question(id = 2, mainQuestion = "How was the staff?", subQuestion = "Were they helpful and attentive?", rating = 5),
                            Question(id = 3, mainQuestion = "Cleanliness of the facility", subQuestion = "Was your room and the facility clean?", rating = 3)
                        )
                    ),
                    isFeedbackComplete = false
                )
            ),
            feedbackSubmissionState = FeedbackSubmissionState.Idle,
            onSubmitFeedback = { _, _ -> },
            onUpdateRating = { _, _ -> }
        )
    }
}
