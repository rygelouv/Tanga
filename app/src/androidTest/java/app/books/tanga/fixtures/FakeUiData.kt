package app.books.tanga.fixtures

import app.books.tanga.R
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.search.CategoryUi
import app.books.tanga.feature.summary.SummaryUi
import java.util.UUID

object FakeUiData {
    fun categories() =
        buildList {
            add(
                CategoryUi(
                    id = "1",
                    name = "Business",
                    icon = app.books.tanga.coreui.R.drawable.ic_business
                )
            )
            add(
                CategoryUi(
                    id = "2",
                    name = "Personal Development",
                    icon = app.books.tanga.coreui.R.drawable.ic_self_development
                )
            )
            add(
                CategoryUi(
                    id = "3",
                    name = "Psychology",
                    icon = app.books.tanga.coreui.R.drawable.ic_productivity
                )
            )
            add(
                CategoryUi(
                    id = "4",
                    name = "Financial Education",
                    icon = app.books.tanga.coreui.R.drawable.ic_financial_education
                )
            )
        }

    fun allSummaries() =
        buildList<SummaryUi> {
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = app.books.tanga.coreui.R.drawable.atomic_habits_cover,
                    title = "It doesn't have to be crazy at work",
                    author = "Jason Fried and David Heinemeir Hannson",
                    duration = "10",
                    hasGraphic = true,
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_atomic_habit,
                    title = "Atomic Habits",
                    author = "James Clear",
                    duration = "15",
                    hasVideo = true,
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_deep_work,
                    title = "Deep Work",
                    author = "Cal Newport",
                    duration = "9",
                    hasGraphic = true,
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_ego_is_enemy,
                    title = "Ego is the Enemy",
                    author = "Ryan Holiday",
                    duration = "13",
                    hasVideo = true,
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_so_good_they_cant_ignore_you,
                    title = "So Good They Can't Ignore You",
                    author = "Cal Newport",
                    duration = "11",
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_good_to_great,
                    title = "Good To Great",
                    author = "Jim Collins",
                    duration = "8",
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_never_split_difference,
                    title = "Never Split The Difference",
                    author = "Chris Voss",
                    duration = "10",
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_obstacle_is_the_way,
                    title = "The Obstacle Is the Way",
                    author = "Ryan Holiday",
                    duration = "10",
                    hasGraphic = true,
                    hasVideo = true,
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_psychology_of_money,
                    title = "Psychology Of Money",
                    author = "Morgan Housel",
                    duration = "10",
                    hasGraphic = true,
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_rework,
                    title = "Rework",
                    author = "Jason Fried and David Heinemeir Hannson",
                    duration = "11",
                    hasGraphic = true,
                    hasVideo = true,
                    authorPictureUrl = null
                )
            )
            add(
                SummaryUi(
                    id = SummaryId(UUID.randomUUID().toString()),
                    cover = R.drawable.cover_subtle_art_not_giving_fuck,
                    title = "The Subtle Art Of Not Giving A Fuck",
                    author = "Mark Manson",
                    duration = "9",
                    hasGraphic = true,
                    hasVideo = true,
                    authorPictureUrl = null
                )
            )
        }
}
