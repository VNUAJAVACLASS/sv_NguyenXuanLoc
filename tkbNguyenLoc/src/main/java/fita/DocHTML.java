
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
    private CtrinhChinh ctrinhChinh;
    private int lichHocCount = 0;

    public DocHTML(CtrinhChinh ctrinhChinh) {
        this.ctrinhChinh = ctrinhChinh;
    }

    public void khoiTaoVaDocFileHTML(String tenFile) throws IOException {
        File file = new File(tenFile);
        if (!file.exists()) {
            throw new IOException("File không tồn tại: " + file.getAbsolutePath());
        }
        if (!file.canRead()) {
            throw new IOException("Không có quyền đọc file: " + file.getAbsolutePath());
        }

        docFileHTML(tenFile);
    }

    public void docFileHTML(String tenFile) throws IOException {
        File input = new File(tenFile);
        Document doc = Jsoup.parse(input, "UTF-8");
        Element table = doc.select("table").first();
        if (table == null) {
            throw new IOException("Không tìm thấy bảng trong file HTML");
        }

        Elements rows = table.select("tr");
        boolean isHeader = true;
        List<String> cachedData = new ArrayList<>(List.of("", "", "", "", ""));
        int[] rowspanCounts = new int[5];
        int rowCount = 0;

        for (Element row : rows) {
            if (isHeader) {
                isHeader = false;
                continue;
            }

            rowCount++;
            Elements cols = row.select("td:not(.d-none)");
            List<String> currentRowData = new ArrayList<>();
            int colIndex = 0;

            for (int i = 0; i < 5; i++) {
                if (rowspanCounts[i] > 0) {
                    currentRowData.add(cachedData.get(i));
                    rowspanCounts[i]--;
                } else if (colIndex < cols.size()) {
                    currentRowData.add(cols.get(colIndex).text());
                    String rowspanAttr = cols.get(colIndex).attr("rowspan");
                    if (!rowspanAttr.isEmpty()) {
                        try {
                            rowspanCounts[i] = Integer.parseInt(rowspanAttr) - 1;
                            cachedData.set(i, cols.get(colIndex).text());
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    colIndex++;
                }
            }

            for (; colIndex < cols.size(); colIndex++) {
                currentRowData.add(cols.get(colIndex).text());
            }

            if (currentRowData.size() >= 11) {
                LichHoc lichHoc = taoLichHoc(currentRowData);
                if (lichHoc != null) {
                    ctrinhChinh.themLichHoc(lichHoc);
                    lichHocCount++;
                }
            }
        }
    }

    private LichHoc taoLichHoc(List<String> cols) {
        try {
            String maMonHoc = cols.get(0);
            String tenMonHoc = cols.get(1);
            String nhomTo = cols.get(2);
            int soTinChi = Integer.parseInt(cols.get(3));
            String lop = cols.get(4);
            String thuStr = cols.get(5);
            int thu;
            try {
                thu = Integer.parseInt(thuStr);
            } catch (NumberFormatException e) {
                switch (thuStr.toLowerCase().trim()) {
                    case "thứ hai": case "thu hai": thu = 2; break;
                    case "thứ ba": case "thu ba": thu = 3; break;
                    case "thứ tư": case "thu tu": thu = 4; break;
                    case "thứ năm": case "thu nam": thu = 5; break;
                    case "thứ sáu": case "thu sau": thu = 6; break;
                    case "chủ nhật": case "chu nhat": thu = 8; break;
                    default:
                        return null;
                }
            }
            int tietBatDau = Integer.parseInt(cols.get(6));
            int soTiet = Integer.parseInt(cols.get(7));
            String phong = cols.get(8);
            String giangVien = cols.get(9);
            String thoiGianHoc = cols.get(10).replaceAll("[^0-9-]", "");

            if (thoiGianHoc.length() < 22) {
                thoiGianHoc = thoiGianHoc + "-".repeat(22 - thoiGianHoc.length());
            }
            if (thoiGianHoc.length() > 22) {
                thoiGianHoc = thoiGianHoc.substring(0, 22);
            }

            if (thu < 2 || thu > 8 || thu == 7) {
                return null;
            }

            return new LichHoc(maMonHoc, tenMonHoc, nhomTo, soTinChi, lop, thu, tietBatDau, soTiet, phong, giangVien, thoiGianHoc);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return null;
        }
    }
}