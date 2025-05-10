package fita;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocHTML {
    public List<LichHoc> docThoiKhoaBieu(String filePath) {
        List<LichHoc> danhSachLichHoc = new ArrayList<>();
        try {
            // Đọc file HTML
            File file = new File(filePath);
            Document doc = Jsoup.parse(file, "UTF-8");

            // Lấy bảng thời khóa biểu
            Element table = doc.select("table.table").first();
            if (table == null) {
                System.out.println("Không tìm thấy bảng thời khóa biểu trong file HTML.");
                return danhSachLichHoc;
            }

            // Lấy tất cả các hàng trong tbody
            Elements rows = table.select("tbody tr");
            String currentMaMonHoc = "";
            String currentTenMonHoc = "";
            String currentNhomTo = "";
            int currentSoTinChi = 0;
            String currentLop = "";
            int currentRowSpan = 0;
            int cellOffset = 0; // Offset để điều chỉnh chỉ số cột khi không có rowspan

            for (Element row : rows) {
                Elements cells = row.select("td");

                // Kiểm tra xem đây có phải là hàng bắt đầu môn học mới (có rowspan)
                if (cells.get(0).hasAttr("rowspan") && !cells.get(0).attr("rowspan").isEmpty()) {
                    currentRowSpan = Integer.parseInt(cells.get(0).attr("rowspan"));
                    currentMaMonHoc = cells.get(0).text().trim();
                    currentTenMonHoc = cells.get(1).text().trim();
                    currentNhomTo = cells.get(2).text().trim();
                    currentSoTinChi = Integer.parseInt(cells.get(3).text().trim());
                    currentLop = cells.get(6).text().trim();
                    cellOffset = 8; // Cột bắt đầu cho các thông tin lịch học (thứ, tiết, ...)
                } else {
                    cellOffset = 0; // Không có rowspan, các cột bắt đầu từ 0
                    currentRowSpan--; // Giảm rowspan cho các hàng tiếp theo
                }

                // Lấy thông tin lịch học
                String thu = cells.get(cellOffset).text().trim();
                int tietBatDau = Integer.parseInt(cells.get(cellOffset + 1).text().trim());
                int soTiet = Integer.parseInt(cells.get(cellOffset + 2).text().trim());
                String phong = cells.get(cellOffset + 3).text().trim();
                String giangVien = cells.get(cellOffset + 4).text().trim();
                String thoiGianHoc = cells.get(cellOffset + 5).select("span.text-left").text().trim();

                // Tạo đối tượng LichHoc
                LichHoc lichHoc = new LichHoc(
                        currentMaMonHoc, currentTenMonHoc, currentNhomTo, currentSoTinChi, currentLop,
                        thu, tietBatDau, soTiet, phong, giangVien, thoiGianHoc
                );
                danhSachLichHoc.add(lichHoc);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file HTML: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi khi chuyển đổi số: " + e.getMessage());
        }
        return danhSachLichHoc;
    }
}