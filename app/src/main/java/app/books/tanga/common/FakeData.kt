package app.books.tanga.common

import app.books.tanga.R
import app.books.tanga.feature.summary.SummaryUi
import java.util.UUID

object FakeData {
    fun allSummaries() = buildList<SummaryUi> {
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_atomic_habits,
                title = "Atomic Habits",
                author = "James Clear"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_deep_work,
                title = "Deep Work",
                author = "Cal Newport"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_doesnt_have_be_crazy_work,
                title = "It doesn't have to be crazy at work",
                author = "Jason Fried and David Heinemeir Hannson"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_ego_enemy,
                title = "Ego is the Enemy",
                author = "Ryan Holiday"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_good_ignore_you,
                title = "So Good They Can't Ignore You",
                author = "Cal Newport"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_good_to_great,
                title = "Good To Great",
                author = "Jim Collins"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_never_split_different,
                title = "Never Split The Difference",
                author = "Chris Voss"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_obstacle_is_way,
                title = "The Obstacle Is the Way",
                author = "Ryan Holiday"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_psychology_money,
                title = "Psychology Of Money",
                author = "Morgan Housel"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_rework,
                title = "Rework",
                author = "Jason Fried and David Heinemeir Hannson"
            )
        )
        add(
            SummaryUi(
                id = UUID.randomUUID().toString(),
                cover = R.drawable.cover_subtle_art_not_giving_fuck,
                title = "The Subtle Art Of Not Giving A Fuck",
                author = "Mark Manson"
            )
        )
    }
}