package fita;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlFileParser implements ScheduleParser {
    private final String filePath;

    public HtmlFileParser(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<LichHoc> parseSchedule() {
        List<LichHoc> danhSachLichHoc = new ArrayList<>();
        try {
            File file = new File(filePath);
            Document doc = Jsoup.parse(file, "UTF-8");

            Element table = doc.select("table.table").first();
            if (table == null) {
                System.err.println("Không tìm thấy bảng thời khóa biểu trong file HTML.");
                return danhSachLichHoc;
            }

            Elements rows = table.select("tbody tr");

            String currentMaMonHoc = "";
            String currentTenMonHoc = "";
            String currentNhomTo = "";
            int currentSoTinChi = 0;
            String currentLop = "";
            int currentRowSpan = 0;
            int cellOffset = 0;

            for (Element row : rows) {
                Elements cells = row.select("td");

                if (cells.get(0).hasAttr("rowspan") && !cells.get(0).attr("rowspan").isEmpty()) {
                    currentRowSpan = Integer.parseInt(cells.get(0).attr("rowspan"));
                    currentMaMonHoc = cells.get(0).text().trim();
                    currentTenMonHoc = cells.get(1).text().trim();
                    currentNhomTo = cells.get(2).text().trim();
                    try {
                        currentSoTinChi = Integer.parseInt(cells.get(3).text().trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi khi đọc số tín chỉ cho môn " + currentTenMonHoc + ": " + e.getMessage());
                        currentSoTinChi = 0; // Mặc định 0 nếu lỗi
                    }
                    currentLop = cells.get(6).text().trim();
                    cellOffset = 8;
                } else {
                    cellOffset = 0;
                    currentRowSpan--;
                }

                String thu = cells.get(cellOffset).text().trim();
                int tietBatDau;
                try {
                    tietBatDau = Integer.parseInt(cells.get(cellOffset + 1).text().trim());
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi khi đọc tiết bắt đầu: " + e.getMessage());
                    continue;
                }
                int soTiet;
                try {
                    soTiet = Integer.parseInt(cells.get(cellOffset + 2).text().trim());
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi khi đọc số tiết: " + e.getMessage());
                    continue;
                }
                String phong = cells.get(cellOffset + 3).text().trim();
                String giangVien = cells.get(cellOffset + 4).text().trim();
                String thoiGianHoc = cells.get(cellOffset + 5).select("span.text-left").text().trim();

                if (thoiGianHoc.length() < 20) {
                    thoiGianHoc = String.format("%-" + 20 + "s", thoiGianHoc).replace(' ', '-');
                }

                LichHoc lichHoc = new LichHoc(
                        currentMaMonHoc, currentTenMonHoc, currentNhomTo, currentSoTinChi, currentLop,
                        thu, tietBatDau, soTiet, phong, giangVien, thoiGianHoc
                );
                danhSachLichHoc.add(lichHoc);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file HTML: " + e.getMessage());
        }
        return danhSachLichHoc;
    }
}