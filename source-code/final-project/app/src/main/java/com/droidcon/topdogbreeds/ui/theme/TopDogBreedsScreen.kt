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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.droidcon.topdogbreeds.R
import java.lang.Float.min

const val DISK_CACHE_KEY = "dogsImageDisk"
const val MEMORY_CACHE_KEY = "dogsImageMemory"

@Preview
@Composable
fun TopDogBreedsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO 2:
        /**
         * Now, we've added the dependency, we'll use
         * AsyncImage to load and display an image.
         * Add AsyncImage
         * When you add this go to the documentation and shoe the parameters
         * model represents a request for an image...image request or data
         */
        /**
         * french bull dog: https://www.akc.org/wp-content/uploads/2021/05/French-Bulldog-puppy-head-portrait-outdoors.jpeg
         * Golden retriever:  https://www.vidavetcare.com/wp-content/uploads/sites/234/2022/04/golden-retriever-dog-breed-info.jpeg
         * German Shepherd boy: https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg
         */

        /**
         * After adding Todo 2, build and run the project
         * Then start talking about customization
         *
         * Todo 3: Customization
         * A model can be an imageRequest or imageRequestData. Here I passed an image url
         * You could also pass an instance of ImageRequest. This provides us more functionality than just using the url
         * Using an ImageRequest instance gives you more control on how you load your images.
         *  cmd+ b go to documentation
         *
         * memory cache key and disk cache key identify the location where the image is cached.
         * Use the key to delete images from the cache when they are no longer required
         * cache policy: Represent read/write policy to a cache source... navigating to source. Joke: Keep sources close
         *
         * Placeholder: The place holder image to show while loading image from network
         * error: The image to show when loading images fail... these two are preferably local files
         *
         * .. Check documentaion... and short cuts
         * ContentScale determines how the image is fitted in the screen. There are options like [Crop], [Fit].. scale an image until it fits the vaialble space
         * [FillHeight], [FillWidth], [FillBound], [None]
         * Show fillBound and fit examples
         * Alignment: used to place an image within a given within contraints set by width and height.
         *  Alpha: Opacity to be applied to an iamge when it's rendered on screen(0.2F)
         *  Check out other like filterQuality, Color filter etc
         *
         */

        AsyncImage(
            modifier = Modifier.size(width = 500.dp, height = 200.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://www.akc.org/wp-content/uploads/2021/05/French-Bulldog-puppy-head-portrait-outdoors.jpeg")
                .diskCacheKey(DISK_CACHE_KEY)
                .networkCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)// How do I test this caching. What impact does each have on the loading and caching of the image
                .build(),

            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_downloading),
            error = painterResource(id = R.drawable.ic_error),
            contentScale = ContentScale.Fit,
            alignment = Alignment.BottomEnd,
            alpha = 0.1F,

        )
        // TOdo 4: SubcomposeAsyncImage
        /**
         *  When using AsyncImage, the placeholder  is a painter. `painterResource
         *  ()` function only accepts an Int. What if you want you want to display
         *  a composable when loading or an error occurs? In such a case, you'd use SubcomposeAsyncImage.
         *  SubcomposeAsyncImage provides a slot API for different AsyncImagePainter's state.
         *
         *  create a variable state whose value is AsyncImagePainterState.. show in the documenation
         *
         */

        SubcomposeAsyncImage(
            model = "https://www.vidavetcare.com/wp-content/uploads/sites/234/2022/04/golden-retriever-dog-breed-info.jpeg",
            contentDescription = "Golden retriever"
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
        /**
         *  SubComposiion is less performant compared to reqular composition. Use subcomposition only when you have to and in the parts
         *  of the UI where performance is not very crucial. Check out this blog: https://kinya.hashnode.dev/custom-compose-layouts-clf5ua9jw01mms1nv5k6d84zp
         *  to learn more about subcomposition and subcomposelayout in jetpack compose.
         */

        // TODO 5: AsyncImagePainter
        /**
         *  AsyncImage and SubcomposeAsyncImage use AsyncImagePainter under the hood. AsyncImagePainter is a painter that executes an [ImageRequest]
         *  asynchrously and renders the result.
         *  It returns state
         *  If you want to use the painter but cannot use AsyncImage, for instance in advanced animation,  you can use AsyncImagePainter
         *
         *  create an instance of AsyncImagePainter using `rememberAsyncImagePainter()` function.
         *
         */

//        val painter = rememberAsyncImagePainter(model ="https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg" )
//        Image(painter = painter, contentDescription ="German Shepherd Dog")
        // TODO 6: Size part
        /**
         * An image request needs size to determine the output image dimensions, i.e width and height.
         * By default Compose resolves size after composition but before first frame is drawn. Check documentation to learn more about compose phases: https://developer.android.com/jetpack/compose/layouts/basics
         * Therefore, AsyncImagePainter.state will be loading for the first composition even if the image is present in memory.
         * if you need AsyncImagePainter.state to be up-to-date in the first composition, you'd use `SubcomposeAsyncImage` or set
         * size on the image request using `ImageRequest.Builder().size. For example
         */
//        val imageRequest = ImageRequest.Builder(LocalContext.current)
//            .data("https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg")
//            .size(Size.ORIGINAL)
//            .build()
//        val painter1 = rememberAsyncImagePainter(model = imageRequest)
//        Image(painter = painter1, contentDescription = "German Shepherd Dog")
        /**
         * Build and run
         */
        /**
         * Note: AsyncImagePainter will not finish loading if AsyncIamgePainter.onDraw() is not called. This can occur
         * if the composable has unbounded width and height constraints e.g. in a LazyColumn or LazyRow.
         * To use AsyncImagePainter with a LazyColumn or LazyRow you must set a bounded width or height respectively
         * using Modifier.width or Modifier.height.... Example code would be awesome. Let me do this as I work on the slides(TODO)
         */

        // TODO 7: Transition
        /**
         * Coil offers basic crossfade transition that could be enabled using ImageRequest.builder
         *
         */
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data("https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg")
//                .crossfade(5000)// You could pass duration in miliseconds or a boolean
//                .build(),
//            contentDescription = "German Shepherd Dog"
//        )
        /**
         * This is quick and simple and can be used when you
         * just need a crossfade when the image is loading.
         */

        // TODO 8: Custom transitions
        /**
         * Coil does not support custom transitions. However, you'd create custom transitions in compose
         * by observing AsyncImagePainter state
         */
        val painter2 =
            rememberAsyncImagePainter(model = "https://www.akc.org/wp-content/uploads/2021/05/French-Bulldog-puppy-head-portrait-outdoors.jpeg")

        /**
         * .saturation(transition)
        .scale(.8f + (.2f * transition))
        .graphicsLayer { rotationX = (1f - transition) * 5f }
        .alpha(min(1f, transition / .2f))
         */

        val state = painter2.state
//
        val transition = animateFloatAsState(
            targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f
        ).value

        Log.d("TRANSLATION", "is $transition")
        if (state is AsyncImagePainter.State.Loading) {
            Log.d("ANIMATION", "Does it really get here")
           LoadingAnimation()
        }
        Image(
            painter = painter2,
            contentDescription = null,
            modifier =  Modifier
                .scale(.8f+ (.5f * transition))
                .graphicsLayer { rotationX = (1f - transition) * 40f }
                .alpha(min(1f, transition / .2f)),
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
        )
    }
    /**
     * Build and run
     *
     * Explain code... graphics and draw modifiers
     */
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