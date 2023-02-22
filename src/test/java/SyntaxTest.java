import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SyntaxTest {
    @Test
    void 문법테스트() {

        LocalDateTime now = LocalDateTime.now(); // 2021-06-17T06:43:21.419878100

        String formatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.println(formatNow);
    }

}
