package com.droidcon.topdogbreeds.ui.theme

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
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
import com.droidcon.topdogbreeds.R
import java.lang.Float.min

const val DISK_CACHE_KEY = "dogsImageDisk"
const val CONTENT_DESCRIPTION = "Top Dog Breed"

// Dog breeds image urls
const val FRENCH_BULL_DOG =
    "https://www.akc.org/wp-content/uploads/2021/05/French-Bulldog-puppy-head-portrait-outdoors.jpeg"
const val GOLDEN_RETRIEVER =
    "https://www.vidavetcare.com/wp-content/uploads/sites/234/2022/04/golden-retriever-dog-breed-info.jpeg"
const val GERMAN_SHEPHERD_BOY =
    "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg"


@Preview
@Composable
fun TopDogBreedsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = "Top Dog Breeds",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(GERMAN_SHEPHERD_BOY)
                .diskCacheKey(DISK_CACHE_KEY)
                .networkCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_downloading),
            error = painterResource(id = R.drawable.ic_error),
            alignment = Alignment.BottomEnd,
            modifier = Modifier
                .size(width = 220.dp, height = 200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds,
            contentDescription = CONTENT_DESCRIPTION
        )
        SubcomposeAsyncImage(
            model = GOLDEN_RETRIEVER,
            contentDescription = CONTENT_DESCRIPTION
        ) {
            val state = painter.state
            when (state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_downloading),
                        contentDescription = "Empty State"
                    )
                }

                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }

                is AsyncImagePainter.State.Empty -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_downloading),
                        contentDescription = "Empty State"
                    )
                }
            }
        }

        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(GERMAN_SHEPHERD_BOY)
            .size(Size.ORIGINAL)
            .build()
        val painter = rememberAsyncImagePainter(model = imageRequest)
        Image(painter = painter, contentDescription = "German Shepherd Dog")

        val painterWithTransition =
            rememberAsyncImagePainter(model = FRENCH_BULL_DOG)
        val stateWithTransition = painterWithTransition.state
        val transition = animateFloatAsState(
            targetValue = if (stateWithTransition is AsyncImagePainter.State.Success) 1f else 0f
        ).value

        if (stateWithTransition is AsyncImagePainter.State.Loading) {
            LoadingAnimation()
        }
        Image(
            painter = painterWithTransition,
            contentDescription = null,
            modifier = Modifier
                /**
                 * Column has unbound height
                 * Specify the height for the image so that compose can resolve its size
                 * and call [AsyncImagePainter.onDraw]
                 */
                .height(200.dp)
                .scale(.8f + (.5f * transition))
                .graphicsLayer { rotationX = (1f - transition) * 40f }
                .alpha(min(1f, transition / .2f)),
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
        )
    }
}

@Composable
fun LoadingAnimation() {
    val animation = rememberInfiniteTransition()
    val progress = animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Restart,
        )
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