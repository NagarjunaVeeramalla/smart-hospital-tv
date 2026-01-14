package com.smarthospital.tv.feedback.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smarthospital.tv.feedback.viewmodel.NavRailViewModel

@Composable
fun FeedbackDirectScreen(
    modifier: Modifier = Modifier,
    navRailViewModel: NavRailViewModel = viewModel(),
    onFeedbackComplete: () -> Unit = {}
) {
    val feedbackScreenUiState by navRailViewModel.feedbackScreenUiState.collectAsState()
    val feedbackSubmissionState by navRailViewModel.feedbackSubmissionState.collectAsState()
    FeedbackScreen(
        feedbackScreenUiState = feedbackScreenUiState,
        feedbackSubmissionState = feedbackSubmissionState,
        onSubmitFeedback = { title, questions ->
            navRailViewModel.submitFeedback(title, questions)
        },
        onUpdateRating = { questionId, rating ->
            navRailViewModel.updateQuestionRating(questionId, rating)
        },
        onFeedbackComplete = onFeedbackComplete
    )
}