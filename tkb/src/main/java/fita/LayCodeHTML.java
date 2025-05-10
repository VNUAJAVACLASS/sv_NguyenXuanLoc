package fita;

import com.microsoft.playwright.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LayCodeHTML {

    public void layVaLuuTKBHtml() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
            );
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            // 1. Đăng nhập
            page.navigate("https://daotao.vnua.edu.vn/");
            page.waitForSelector("input[name='username']");
            page.fill("input[name='username']", "687699");
            page.fill("input[name='password']", "342116");
            page.click("button:has-text(\"Đăng nhập\")");
            page.waitForTimeout(3000); // đợi load sau khi đăng nhập

            // 2. Chọn thời khóa biểu học kỳ
            page.waitForSelector("#WEB_TKB_HK");
            page.click("#WEB_TKB_HK");

            page.waitForSelector("div[role='combobox']");
            page.click("div[role='combobox']");
            page.waitForSelector("div[role='option']:has-text('Học kỳ 2 - Năm học 2024 - 2025')");
            page.click("div[role='option']:has-text('Học kỳ 2 - Năm học 2024 - 2025')");

            // 3. Đảm bảo bảng đã xuất hiện
            page.waitForSelector("table.table"); // Đảm bảo bảng đã tải
            page.waitForTimeout(2000); // Thêm 2 giây để chắc chắn

            // 4. Lấy HTML của bảng
            String bangHtml = page.evaluate("document.querySelector('table.table')?.outerHTML").toString();
            System.out.println("Bảng HTML: " + bangHtml); // In bảng ra console để kiểm tra

            // 5. Lưu vào file HTML nếu có bảng
            if (bangHtml != null && !bangHtml.isEmpty()) {
                String htmlWrapper = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                      <meta charset="UTF-8">
                      <title>Thời khóa biểu</title>
                    </head>
                    <body>
                """ + bangHtml + """
                    </body>
                    </html>
                """;

                Files.writeString(Paths.get("src/main/resources/timetable.html"), htmlWrapper);
                System.out.println("✅ Đã lưu bảng thời khóa biểu vào src/main/resources/timetable.htmll");
            } else {
                System.out.println("❌ Không lấy được bảng thời khóa biểu.");
            }

            browser.close();
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi lấy hoặc lưu HTML: " + e.getMessage());
        }
    }
}
