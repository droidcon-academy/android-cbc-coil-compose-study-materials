package com.droidcon.topdogbreeds.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Preview
@Composable
fun TopDogBreedsScreen(
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.padding(16.dp)) {
        // TODO 2:
        /**
         * AsyncImage is a composable to load and display an image.
         * Add AsyncImage
         * When you add this go to the documentation and shoe the parameters
         * model represents a request for an image...image request or data
         */
        /**
         * french bull dog: https://www.akc.org/wp-content/uploads/2021/05/French-Bulldog-puppy-head-portrait-outdoors.jpeg
         * Golden retriever:  https://www.vidavetcare.com/wp-content/uploads/sites/234/2022/04/golden-retriever-dog-breed-info.jpeg
         * German Shepherd boy: https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg
         */

        AsyncImage(
            model = "https://www.akc.org/wp-content/uploads/2021/05/French-Bulldog-puppy-head-portrait-outdoors.jpeg" , contentDescription = null)
    }
}