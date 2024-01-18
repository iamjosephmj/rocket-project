package com.example.rocketproject.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rocketproject.domain.model.SpaceFlightsItem


@Composable
internal fun SpaceFlightItemComponent(
    spaceFlightItems: List<SpaceFlightsItem>,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier) {
        items(spaceFlightItems) { spaceFlightItem ->
            SpaceFlightItem(
                title = spaceFlightItem.title,
                description = spaceFlightItem.summary,
                dateTime = spaceFlightItem.publishedAt,
            )
        }
    }


}

@Composable
fun SpaceFlightItem(
    title: String,
    description: String,
    dateTime: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp)
    ) {
        Column(modifier.padding(8.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontWeight = FontWeight.Light,
                fontSize = 11.sp

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = dateTime,
                fontWeight = FontWeight.Thin,
                fontSize = 8.sp
            )
        }

    }
}

@Preview
@Composable
internal fun RenderSpaceSpaceFlightItem() {
    SpaceFlightItem(
        title = "Astrobotic confirms Peregrine reentry plans",
        description = "Astrobotic confirmed Jan. 17 that its Peregrine lunar lander will reenter over the South Pacific on Jan. 18, concluding a 10-day mission that failed to land on the moon because of a propellant leak.",
        dateTime = "21:15 12/12/2023"
    )
}
