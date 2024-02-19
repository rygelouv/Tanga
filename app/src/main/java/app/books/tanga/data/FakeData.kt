package app.books.tanga.data

import app.books.tanga.R
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.search.CategoryUi
import app.books.tanga.feature.summary.SummaryUi
import java.util.UUID

object FakeData {
    fun allCategories() =
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

    const val SUMMARY_TEXT = "## Summary of Atomic Habits\n" +
        "\n" +
        "### Introduction: Unveiling the Hidden Power of Everyday Actions\n" +
        "\n" +
        "_\"Have you ever wondered how small, seemingly insignificant actions can dramatically alter the course of" +
        " your life?\"_\n" +
        "\n" +
        "Welcome to a journey of transformation and self-discovery through \"Atomic Habits\" by James Clear. " +
        "This isn't just a book; it's a roadmap to reshaping your life by changing your habits. Here, " +
        "we unveil the extraordinary power of small habits and their profound impact on our lives. " +
        "We delve into the surprising truth that monumental successes often stem not from radical changes, " +
        "but from small, consistent actions performed day after day.\n" +
        "\n" +
        "In this exploration, you'll learn:\n" +
        "1. How tiny changes can yield remarkable results over time.\n" +
        "2. The science behind habit formation and its influence on our behavior.\n" +
        "3. Practical strategies for building good habits and breaking bad ones.\n" +
        "4. The profound role of identity in personal transformation.\n" +
        "5. Navigating challenges and setbacks in habit formation.\n" +
        "6. The path to lasting change and the importance of perseverance.\n" +
        "\n" +
        "Prepare to be inspired, to challenge your perceptions, and to discover the immense power hidden in the " +
        "routines of your daily life. Let's embark on this transformative journey together.\n" +
        "\n" +
        "### Part 1: The Power of Small Daily Improvements\n" +
        "\n" +
        "_\"Success comes from what you do every day, not from big changes that happen once in a lifetime.\"_ â€“ " +
        "James Clear\n" +
        "\n" +
        "Embracing small, consistent changes can lead to significant transformations over time. It's the tiny " +
        "improvements made daily that accumulate and create major impacts. Imagine how a modest 1% " +
        "betterment in daily" +
        " activities can result in a substantial 37% improvement over a year. This compound effect " +
        "demonstrates that " +
        "even minimal, steady changes can yield extraordinary outcomes.\n" +
        "\n" +
        "Consider the analogy of an ice cube melting. The ice begins to melt at 32 degrees, but the gradual " +
        "temperature increase leading up to this point is critical. Similarly, the benefits of small habits " +
        "accumulate" +
        " gradually, often going unnoticed until they reach a critical point where substantial changes " +
        "become visible.\n" +
        "\n" +
        "Acknowledging and celebrating small achievements is vital. Each minor success is a step towards larger" +
        " goals, bolstering motivation and commitment. It's the accumulation of these small victories that " +
        "pave the way" +
        " for major achievements. Starting new habits can be challenging, akin to the effort needed to " +
        "launch a rocket." +
        " However, persistence makes maintaining these habits easier over time due to the momentum " +
        "that has been built." +
        " Consistent, small actions gradually drive progress towards goals.\n" +
        "\n" +
        "This approach teaches that transformative change doesn't require drastic measures but" +
        " rather the consistent " +
        "accumulation of small, daily habits. By understanding and practicing this principle, " +
        "significant improvements " +
        "in both personal and professional life are achievable.\n" +
        "\n" +
        "### Part 2: The Science of Habit Formation and Behavior\n" +
        "\n" +
        "_\"The process of building a habit can be divided into four simple steps: cue, craving, response, and " +
        "reward.\"_ â€“ James Clear\n" +
        "\n" +
        "Understanding habit formation is crucial, and it can be simplified into a four-step model: cue, craving, " +
        "response, and reward. This model not only sheds light on how habits are formed " +
        "and become part of our behavior" +
        " but also provides insights into how they can be effectively changed.\n" +
        "\n" +
        "1. **Cue**: Recognize the triggers in your environment that initiate a behavior. " +
        "These cues are the starting points that prompt actions.\n" +
        "2. **Craving**: Understand the motivational force behind every habit. " +
        "It's the craving that propels you to act, driven by a desire for change.\n" +
        "3. **Response**: Focus on the habit or action itself. This is where the behavior in response to " +
        "the craving and cue takes place.\n" +
        "4. **Reward**: Pay attention to the satisfaction or benefit gained from the habit, as this reinforces " +
        "the behavior and completes the habit loop.\n" +
        "\n" +
        "The formation of habits involves the creation of neural pathways in the brain, making repeated actions " +
        "more automatic and efficient. By grasping this cycle, new habits can be introduced and existing ones" +
        " modified" +
        ". Altering cues in your environment, for example, can be a strategy to break undesirable habits. " +
        "Similarly, finding alternative actions that satisfy the same cravings can help in establishing" +
        " new, beneficial habits.\n" +
        "\n" +
        "Grasping the science behind habits is a powerful tool for controlling and influencing behavior, paving " +
        "the way for personal transformation and growth.\n" +
        "\n" +
        "### Part 3: Effective Techniques for Habit Transformation\n" +
        "\n" +
        "_\"Make it obvious, make it attractive, make it easy, make it satisfying.\"_ â€“ James Clear\n" +
        "\n" +
        "Transforming your habits can be streamlined with practical strategies, focusing not just on" +
        " making changes but ensuring they last. Begin by modifying your surroundings to make cues for" +
        " desired habits more visible. For example, placing a book on your bedside table can naturally " +
        "encourage more reading. This adjustment in your environment can significantly influence your " +
        "daily actions.\n" +
        "\n" +
        "Another powerful technique is temptation bundling, where you pair a habit you need to develop " +
        "with an activity you enjoy, like listening to a favorite podcast only while exercising. This " +
        "approach links an enjoyable activity with a necessary habit, making the latter more appealing" +
        " and sustainable.\n" +
        "\n" +
        "Additionally, the Two-Minute Rule is a simple yet effective method for adopting new habits. " +
        "Start with actions that take two minutes or less to complete. This strategy reduces the intimidation" +
        " factor of new habits, making them feel more achievable and less overwhelming.\n" +
        "\n" +
        "On the flip side, to eliminate negative habits, make them less visible in your environment. " +
        "A practical step, such as hiding your phone, can significantly reduce the temptation to " +
        "engage in time-wasting activities like excessive social media browsing. Similarly, by increasing" +
        " the difficulty of engaging in negative habits, such as uninstalling addictive apps, you add " +
        "a barrier that can discourage you from these actions.\n" +
        "\n" +
        "These strategies provide a hands-on approach to habit modification. By focusing on small yet " +
        "impactful changes in your daily routine and environment, you can effectively steer your " +
        "habit patterns towards positive outcomes.\n" +
        "\n" +
        "### Part 4: The Transformative Power of Identity\n" +
        "\n" +
        "_\"Every action you take is a vote for the type of person you wish to become.\"_ â€“ James Clear\n" +
        "\n" +
        "In \"Atomic Habits,\" the role of identity in personal transformation is presented as a cornerstone." +
        " True change, it's emphasized, starts with a shift in how we see ourselves and aligning our habits " +
        "with the identity we aspire to. This is more than just habit modification; it's about embodying the" +
        " traits and characteristics of the person we aim to become.\n" +
        "\n" +
        "The concept of identity-based habits is a game-changer. It shifts the focus from merely achieving " +
        "outcomes to becoming the type of person who can achieve those outcomes. For example, if you want " +
        "to be a writer, start by building the habit of writing daily. This practice isn't just about " +
        "completing a task; it's about reinforcing your identity as a writer.\n" +
        "\n" +
        "There's a dynamic relationship between habits and identity. Each action in line with your " +
        "aspirational identity strengthens that identity. It's a positive feedback loop: your habits" +
        " reinforce your identity, and your identity shapes your habits. By adopting habits that" +
        " resonate with your desired self, you create a foundation for enduring change.\n" +
        "\n" +
        "This approach to transformation is profound and empowering. It suggests that by consciously" +
        " shaping our habits to reflect the identity we seek, we can steer our life's trajectory in " +
        "meaningful directions. Embrace this perspective, and watch as your daily habits become a " +
        "powerful tool for personal growth and transformation.\n" +
        "\n" +
        "### Part 5: Navigating the Ups and Downs of Habit Formation\n" +
        "\n" +
        "_\"You do not rise to the level of your goals. You fall to the level of your systems.\"_ â€“ James Clear\n" +
        "\n" +
        "In \"Atomic Habits,\" the journey of habit formation is acknowledged as one filled with" +
        " inevitable challenges and setbacks. Rather than viewing these obstacles as roadblocks," +
        " they are reframed as integral parts of the learning and growth process. \n" +
        "\n" +
        "Understanding that setbacks are a natural aspect of forming new habits is crucial. Instead" +
        " of perceiving these moments as failures, they should be seen as opportunities to gain insight" +
        " and strengthen resolve. This shift in perspective is vital in maintaining" +
        " momentum during the habit-forming journey.\n" +
        "\n" +
        "The emphasis is placed on building resilient systems rather than just setting ambitious " +
        "goals. The idea is to design your environment and daily routines to support and reinforce your" +
        " habits. This approach ensures that you stay on course, even when motivation dips or unforeseen " +
        "challenges arise. It's about creating a structure that aligns with your habits, making" +
        " them more sustainable and less reliant on fluctuating willpower.\n" +
        "\n" +
        "Adaptability is another key aspect of successful habit formation. Life is constantly " +
        "changing, and so should your habits. Adjusting and modifying habits to suit new schedules, " +
        "environments, or priorities is not only practical but necessary for long-term adherence." +
        " This flexibility ensures that your habits evolve in tandem with your lifeâ€™s changes.\n" +
        "\n" +
        "Navigating the complexities of habit formation effectively involves recognizing the" +
        " role of setbacks as growth opportunities, building systems that support your habits, " +
        "and remaining adaptable to lifeâ€™s ever-changing nature. By embracing these principles, " +
        "the path to sustainable habit formation becomes clearer and more achievable.\n" +
        "\n" +
        "### Part 6: Cultivating Change Through Steadfast Perseverance\n" +
        "\n" +
        "_\"Changes that seem small and unimportant at first will compound into remarkable " +
        "results if youâ€™re willing to stick with them for years.\"_ â€“ James Clear\n" +
        "\n" +
        "In the culmination of \"Atomic Habits,\" the focus shifts to the essential ingredient " +
        "for lasting change: perseverance. This concept is crucial for anyone committed to " +
        "achieving lasting transformation.\n" +
        "\n" +
        "The journey to long-term change is portrayed as an ongoing process, one that relies on " +
        "the accumulation of small, consistent actions. Imagine how a river, through its " +
        "persistent flow, gradually shapes and alters its banks; similarly, our habits, when " +
        "applied steadily, have the power to bring about significant transformations in our lives.\n" +
        "\n" +
        "James Clear emphasizes that the key to lasting change is consistency rather than " +
        "intensity. It's the small, regular actions, performed consistently over time, that" +
        " have the most substantial impact. This consistent application of habits is what " +
        "nurtures and solidifies lasting change.\n" +
        "\n" +
        "Perseverance, therefore, becomes a pivotal aspect of habit formation. It's about " +
        "maintaining a relentless commitment to your habits, especially when progress seems " +
        "slow or imperceptible. By focusing on the process and dedicating yourself to it " +
        "every day, you lay the groundwork for real and lasting change.\n" +
        "\n" +
        "By embracing perseverance and consistency, you can steer the course of your life " +
        "towards continuous improvement and transformation. This approach ensures that the" +
        " journey of habit formation is not just a fleeting endeavor but a sustainable " +
        "path to personal development and growth.\n" +
        "### Conclusion: Embracing the Atomic Approach to Transformative Change\n" +
        "\n" +
        "\"Atomic Habits\" offers much more than just a set of techniques; it lays out a" +
        " comprehensive plan for deep personal change. The book masterfully combines th" +
        "e science behind habits with practical applications, demonstrating that even the " +
        "smallest actions can lead to substantial life transformations. It stands as a " +
        "powerful endorsement of the atomic approach, highlighting how consistent, minor " +
        "efforts can spark a series of positive changes in every facet of life.\n" +
        "\n" +
        "\n" +
        "##### Key Takeaways:\n" +
        "\n" +
        "1. **The Compound Effect of Small Habits**: Understand that small changes c" +
        "ompound over time, leading to significant results.\n" +
        "2. **The Science of Habit Formation**: Grasp the four-step model of habit " +
        "formation - cue, craving, response, and reward - to better control your actions.\n" +
        "3. **Practical Strategies for Habit Change**: Implement Clearâ€™s strategies " +
        "like making habits obvious, attractive, easy, and satisfying to build good " +
        "habits and break bad ones.\n" +
        "4. **Identity and Transformation**: Recognize that true change begins with " +
        "a shift in identity, aligning your habits with who you want to become.\n" +
        "5. **Navigating Challenges**: Learn to view setbacks as part of the process" +
        " and focus on building resilient systems to maintain your habits.\n" +
        "6. **The Path to Lasting Change**: Embrace the journey of long-term change," +
        " understanding that consistency and perseverance are key to achieving your goals.\n" +
        "\n" +
        "The message is clear: the vast landscape of our lives is intricately crafted " +
        "by the small habits we practice daily. These habits are the threads that weave " +
        "the fabric of our existence. As you navigate the choices and actions that shape" +
        " your life, let \"Atomic Habits\" be your compass in mastering habit formation" +
        " and, consequently, reshaping your future. This book isn't just a read; it's a " +
        "journey towards self-mastery and a transformed life, built one small habit at a time."
}
