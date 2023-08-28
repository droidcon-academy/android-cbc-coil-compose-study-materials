package com.droidcon.topdogbreeds

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import java.lang.Float.min

const val DISK_CACHE_KEY = "dogsImageDisk"
const val CONTENT_DESCRIPTION = "Top Dog Breed"
const val ERROR_PLACEHOLDER = "Error Occurred"

// Dog breeds image urls
const val FRENCH_BULL_DOG =
    "https://www.akc.org/wp-content/uploads/2021/05/French-Bulldog-puppy-head-portrait-outdoors.jpeg"
const val GOLDEN_RETRIEVER =
    "https://www.vidavetcare.com/wp-content/uploads/sites/234/2022/04/golden-retriever-dog-breed-info.jpeg"
const val GERMAN_SHEPHERD_BOY =
    "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg"

@Preview
@Composable
fun DogBreedsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Top Dog Breeds",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

//        val painterState = painter.state
//        val transition = animateFloatAsState(
//            targetValue = if (painterState is AsyncImagePainter.State.Success) 1f else 0f,
//            label = ""
//        ).value
//
//        if (painterState is AsyncImagePainter.State.Loading) {
//            LoadingAnimation()
//        }
//        Image(
//            painter = painter,
//            contentDescription = null,
//            modifier = Modifier
//                .scale(.8f + (.5f * transition))
//                .graphicsLayer { rotationX = (1f - transition) * 90f }
//                .alpha(min(1f, transition / .7f)),
//            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
//        )
    }
}

@Composable
fun LoadingAnimation() {
    val animation = rememberInfiniteTransition(label = "")
    val progress = animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Restart,
        ),
        label = ""
    ).value

    Box(
        modifier = Modifier
            .size(60.dp)
            .scale(progress)
            .alpha(1f - progress)
            .border(
                5.dp,
                color = Color.Black,
                shape = CircleShape
            )
    )
}