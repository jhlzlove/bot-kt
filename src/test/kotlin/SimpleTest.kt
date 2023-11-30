import kotlinx.datetime.Clock
import org.junit.jupiter.api.Test

/**
 * @author jhlz
 * @since 1.0
 */
class SimpleTest {
    /**
     * test:
     */
    @Test
    fun kotlinx_datetime_parser_Test() {
        val now = Clock.System.now()
        println(now)
    }
}