package diary.diaryspring.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Board {
    private int id; // 글의 번호
    private String title;
    private String content;
    private String writer;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        LocalDateTime now = LocalDateTime.now(); // 2021-06-17T06:43:21.419878100
        String formatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.date = formatNow;
    }
}
