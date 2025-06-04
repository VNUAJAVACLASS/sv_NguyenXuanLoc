package fita;

import com.microsoft.playwright.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScheduleParser implements ScheduleParser {
    private int activeIndex = -1;

    @Override
    public List<LichHoc> parseSchedule() {
        List<LichHoc> danhSachLichHoc = new ArrayList<>();
        try (Playwright playwright = Playwright.create()) {
            // Khởi tạo trình duyệt ở chế độ headless (có thể đổi thành false để debug)
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            // 1. Đăng nhập
            System.out.println("Đang truy cập trang đăng nhập...");
            page.navigate("https://daotao.vnua.edu.vn", new Page.NavigateOptions().setTimeout(30000));

            if (page.isClosed() || page.url().isEmpty()) {
                System.err.println("❌ Không thể tải trang đăng nhập.");
                return danhSachLichHoc;
            }

            System.out.println("Đang nhập thông tin đăng nhập...");
            page.waitForSelector("input[name='username']", new Page.WaitForSelectorOptions().setTimeout(10000));
            page.fill("input[name='username']", "687699");
            page.fill("input[name='password']", "342116");
            page.click("button:has-text(\"Đăng nhập\")");
            page.waitForTimeout(5000);

            // 2. Lấy ngày (tuần hiện tại)
            System.out.println("Lấy ngày...");
            page.waitForSelector("#WEB_TKB_1TUAN", new Page.WaitForSelectorOptions().setTimeout(10000));
            page.click("#WEB_TKB_1TUAN");

            Locator comboBox = page.locator("div[role='combobox']").last();
            comboBox.click();
            page.waitForSelector("[role='option']", new Page.WaitForSelectorOptions().setTimeout(5000));

            Locator dropdownContainer = page.locator("[role='listbox']");
            int lastCount = 0;
            int currentCount = 0;
            do {
                lastCount = page.locator("[role='option']").count();
                dropdownContainer.evaluate("el => el.scrollTop = el.scrollHeight");
                page.waitForTimeout(1000);
                currentCount = page.locator("[role='option']").count();
            } while (currentCount > lastCount);

            Locator options = page.locator("[role='option']");
            int optionCount = options.count();
            System.out.println("Số phần tử trong dropdown: " + optionCount);

            List<Integer> indexList = new ArrayList<>();
            Pattern p = Pattern.compile(".*-(\\d+)$"); // Regex để trích xuất số cuối từ id

            for (int i = 0; i < optionCount; i++) {
                Locator option = options.nth(i);
                String id = option.getAttribute("id");
                if (id != null) {
                    Matcher m = p.matcher(id);
                    if (m.matches()) {
                        int index = Integer.parseInt(m.group(1));
                        indexList.add(index);
                        System.out.println("Phần tử thứ " + i + " có ID: " + id + ", Index: " + index);
                    } else {
                        System.out.println("ID không khớp với regex: " + id);
                    }
                } else {
                    System.out.println("Phần tử thứ " + i + " không có ID");
                }
            }

            System.out.println("Danh sách tất cả index trong dropdown: " + indexList);

            // Lấy ID active element
            Locator input = comboBox.locator("input[aria-activedescendant]");
            String activeId = input.getAttribute("aria-activedescendant");
            System.out.println("Active ID: " + activeId);

            if (activeId != null) {
                Matcher m = p.matcher(activeId);
                if (m.matches()) {
                    activeIndex = Integer.parseInt(m.group(1));
                    Locator activeOption = page.locator("#" + activeId);
                    if (activeOption.count() > 0) {
                        activeOption.evaluate("el => el.scrollIntoView({block: 'center', behavior: 'smooth'})");
                    } else {
                        System.out.println("Không tìm thấy phần tử với ID: " + activeId);
                    }
                } else {
                    System.out.println("ID không khớp với regex: " + activeId);
                }
            } else {
                System.out.println("Không tìm thấy aria-activedescendant, chọn tuần 1 làm mặc định.");
                activeIndex = 0;
            }

            System.out.println("Phần tử đang chọn là phần tử thứ: " + activeIndex);
            System.out.println("Lấy ngày thành công");

            // 3. Chọn thời khóa biểu học kỳ
            System.out.println("Đang chọn thời khóa biểu học kỳ...");
            page.waitForSelector("#WEB_TKB_HK", new Page.WaitForSelectorOptions().setTimeout(10000));
            page.click("#WEB_TKB_HK");

            page.waitForSelector("div[role='combobox']", new Page.WaitForSelectorOptions().setTimeout(10000));
            page.click("div[role='combobox']");
            page.waitForSelector("div[role='option']:has-text('Học kỳ 2 - Năm học 2024 - 2025')",
                    new Page.WaitForSelectorOptions().setTimeout(10000));
            page.click("div[role='option']:has-text('Học kỳ 2 - Năm học 2024 - 2025')");

            // 4. Đảm bảo bảng thời khóa biểu đã xuất hiện
            System.out.println("Đang đợi bảng thời khóa biểu...");
            page.waitForSelector("table.table", new Page.WaitForSelectorOptions().setTimeout(15000));
            page.waitForTimeout(3000);

            // 5. Lấy HTML của bảng
            String bangHtml = page.evaluate("document.querySelector('table.table')?.outerHTML").toString();
            if (bangHtml == null || bangHtml.isEmpty()) {
                System.err.println("❌ Không lấy được bảng thời khóa biểu. HTML rỗng.");
                System.out.println("Nội dung trang: " + page.content().substring(0, Math.min(page.content().length(), 500)));
                return danhSachLichHoc;
            }

            // In một phần HTML để kiểm tra
            System.out.println("Bảng HTML (một phần): " + bangHtml.substring(0, Math.min(bangHtml.length(), 500)));

            // 6. Lưu vào file HTML
            String htmlWrapper = "<html><body>" + bangHtml + "</body></html>";
            Files.writeString(Paths.get("src/main/resources/timetable.html"), htmlWrapper);
            System.out.println("✅ Đã lưu bảng thời khóa biểu vào src/main/resources/timetable.html");

            // 7. Đọc file HTML đã lưu
            ScheduleParser fileParser = new HtmlFileParser("src/main/resources/timetable.html");
            danhSachLichHoc = fileParser.parseSchedule();

            // Đóng trình duyệt
            browser.close();

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi lấy hoặc lưu HTML: " + e.getMessage());
            e.printStackTrace();
        }
        return danhSachLichHoc;
    }

    public int getActiveIndex() {
        return activeIndex;
    }
}