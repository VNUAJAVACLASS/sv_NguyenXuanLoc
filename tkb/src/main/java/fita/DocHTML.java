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
    // Ham doc thoi khoa bieu tu file HTML
    public List<LichHoc> docThoiKhoaBieu(String filePath) {
        List<LichHoc> danhSachLichHoc = new ArrayList<>();
        try {
            // Mo file HTML
            File file = new File(filePath);
            Document doc = Jsoup.parse(file, "UTF-8");

            // Tim bang co class la 'table'
            Element table = doc.select("table.table").first();
            if (table == null) {
                System.err.println("Khong tim thay bang thoi khoa bieu trong file HTML.");
                return danhSachLichHoc;
            }

            // Lay tat ca cac dong trong tbody
            Elements rows = table.select("tbody tr");

            // Cac bien tam de giu thong tin mon hoc khi co rowspan
            String currentMaMonHoc = "";
            String currentTenMonHoc = "";
            String currentNhomTo = "";
            int currentSoTinChi = 0;
            String currentLop = "";
            int currentRowSpan = 0;   // So dong duoc gop lai
            int cellOffset = 0;       // Vi tri bat dau cua cot thong tin buoi hoc

            // Duyet qua tung dong
            for (Element row : rows) {
                Elements cells = row.select("td");

                // Neu co rowspan tuc la dong moi cua mot mon hoc moi
                if (cells.get(0).hasAttr("rowspan") && !cells.get(0).attr("rowspan").isEmpty()) {
                    currentRowSpan = Integer.parseInt(cells.get(0).attr("rowspan"));
                    currentMaMonHoc = cells.get(0).text().trim();
                    currentTenMonHoc = cells.get(1).text().trim();
                    currentNhomTo = cells.get(2).text().trim();
                    currentSoTinChi = Integer.parseInt(cells.get(3).text().trim());
                    currentLop = cells.get(6).text().trim();
                    cellOffset = 8; // Vi tri cac thong tin buoi hoc sau thong tin mon hoc
                } else {
                    // Dong thuoc ve mon hoc da doc o tren (do rowspan)
                    cellOffset = 0;
                    currentRowSpan--;
                }

                // Lay thong tin buoi hoc
                String thu = cells.get(cellOffset).text().trim();
                int tietBatDau = Integer.parseInt(cells.get(cellOffset + 1).text().trim());
                int soTiet = Integer.parseInt(cells.get(cellOffset + 2).text().trim());
                String phong = cells.get(cellOffset + 3).text().trim();
                String giangVien = cells.get(cellOffset + 4).text().trim();
                String thoiGianHoc = cells.get(cellOffset + 5).select("span.text-left").text().trim();

                // Neu chuoi thoi gian hoc ngan hon 20 ky tu thi them ky tu '-' vao cuoi de dem
                if (thoiGianHoc.length() < 20) {
                    thoiGianHoc = String.format("%-" + 20 + "s", thoiGianHoc).replace(' ', '-');
                }

                // Tao doi tuong LichHoc va them vao danh sach
                LichHoc lichHoc = new LichHoc(
                        currentMaMonHoc, currentTenMonHoc, currentNhomTo, currentSoTinChi, currentLop,
                        thu, tietBatDau, soTiet, phong, giangVien, thoiGianHoc
                );
                danhSachLichHoc.add(lichHoc);
            }
        } catch (IOException e) {
            System.err.println("Loi khi doc file HTML: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Loi khi chuyen doi so: " + e.getMessage());
        }

        // Tra ve danh sach lich hoc da doc duoc
        return danhSachLichHoc;
    }
}
