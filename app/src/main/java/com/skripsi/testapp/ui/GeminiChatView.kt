package com.skripsi.testapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.skripsi.testapp.R
import kotlinx.coroutines.launch

@Composable
fun GeminiChatView(
    apiKey: String,
    appThemColor: Color = Color.Green,
    chatContext: List<GeminiContent> = emptyList()
) {
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    val chatDataList = remember { mutableStateOf(listOf<ChatMember>()) }

    val generativeModel = remember {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = apiKey
        )
    }

    val chat = remember {
        generativeModel.startChat(history = chatContext.map {
            Content(role = it.role, parts = listOf(TextPart(text = it.text)))
        })
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val requestFocus = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        requestFocus.value = true
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = appThemColor,
        ) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(55.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "HICARE",
                        color = Color.Black,
                        fontSize = 24.sp,
                    )

                    Text(
                        text = stringResource(id = R.string.specialist_text),
                        color = Color.Black,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyColumnListState
            ) {
                items(chatDataList.value) { chat ->
                    ChatBubble(
                        text = chat.text,
                        memberType = chat.memberType,
                        appThemeColor = appThemColor
                    )
                }
            }
        }

        RoundedCornerTextFieldWithSend(
            modifier = Modifier.fillMaxWidth(),
            onSendClick = {
                coroutineScope.launch {
                    chatDataList.value += listOf(ChatMember(MemberType.USER, it))
                    lazyColumnListState.animateScrollToItem(chatDataList.value.size - 1)
                    isLoading.value = true
                    try {
                        val response = chat.sendMessage(it)
                        chatDataList.value += listOf(
                            ChatMember(MemberType.BOT, response.text ?: "")
                        )
                        lazyColumnListState.animateScrollToItem(chatDataList.value.size - 1)
                        isLoading.value = false
                    } catch (ex: Exception) {
                        isLoading.value = false
                        ex.printStackTrace()
                    }
                }
            },
            isLoading.value,
            appThemColor,
            requestFocus = requestFocus.value
        )
    }
}

@Composable
fun ChatBubble(
    text: String,
    memberType: MemberType,
    appThemeColor: Color
) {
    val bubbleColor = if (memberType == MemberType.USER) {
        colorResource(id = R.color.greensky)    } else {
        appThemeColor
    }

    val alignment = if (memberType == MemberType.USER) {
        Alignment.End
    } else {
        Alignment.Start
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (memberType == MemberType.USER) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (memberType == MemberType.BOT) {
            Icon(
                painter = painterResource(id = R.drawable.bot),
                contentDescription = "Bot Avatar",
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = bubbleColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = text,
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.widthIn(max = 240.dp)
                )
            }

            if (memberType == MemberType.USER) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun RoundedCornerTextFieldWithSend(
    modifier: Modifier = Modifier,
    onSendClick: (String) -> Unit,
    isLoading: Boolean,
    appThemColor: Color,
    requestFocus: Boolean = false // New parameter to control focus
) {
    val focusRequester = remember { FocusRequester() }
    val textState = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
        }
    }

    Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            label = { Text(stringResource(R.string.apa_yang_ingin_anda_ketahui)) },
            maxLines = 6,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.greensky),
                focusedLabelColor = Color.Black,
                unfocusedBorderColor = Color.Green,
                unfocusedLabelColor = Color.Green,
                cursorColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(36.dp),
                strokeWidth = 4.dp,
                color = Color.Green,
            )
        } else {
            Button(
                modifier = Modifier.size(50.dp),
                contentPadding = PaddingValues(0.dp),
                enabled = textState.value.isNotBlank(),
                onClick = {
                    onSendClick(textState.value)
                    textState.value = ""
                    keyboardController?.hide()
                },
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = appThemColor
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.Black
                )
            }
        }
    }
}

data class GeminiContent(var role: String, var text: String)
data class ChatMember(var memberType: MemberType, var text: String)
enum class MemberType { BOT, USER }
