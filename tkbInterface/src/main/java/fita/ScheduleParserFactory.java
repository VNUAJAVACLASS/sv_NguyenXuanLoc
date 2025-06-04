package fita;

public class ScheduleParserFactory {
    public enum ParserType {
        HTML_FILE, WEB
    }

    public static ScheduleParser createParser(ParserType type, String filePath) {
        switch (type) {
            case HTML_FILE:
                return new HtmlFileParser("src/main/resources/timetable.html");
            case WEB:
                return new WebScheduleParser();
            default:
                throw new IllegalArgumentException("Unknown parser type: " + type);
        }
    }
}