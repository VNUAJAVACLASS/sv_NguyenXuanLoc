package fita;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            CtrinhChinh ctrinh = new CtrinhChinh();
            File input = new File("src/tkb1.html");
            if (!input.exists()) {
                System.out.println("Lỗi: File tkb1.html không tồn tại trong src/");
                return;
            }
            Document doc = Jsoup.parse(input, "UTF-8");
            Element table = doc.select("table").first();
            if (table == null) {
                System.out.println("Lỗi: Không tìm thấy bảng trong tkb1.html");
                return;
            }
            Elements rows = table.select("tr");
            if (rows.isEmpty()) {
                System.out.println("Lỗi: Bảng không có hàng dữ liệu");
                return;
            }

            // Duyệt qua các hàng, bắt đầu từ hàng thứ 2 (bỏ qua tiêu đề)
            for (int rowIndex = 1; rowIndex < rows.size() - 1; rowIndex++) {
                Element row = rows.get(rowIndex);
                Elements cols = row.select("td");
                int tiet = rowIndex;

                // Số cột tối đa là 9 (Tiết, Thứ 2-7, Thời gian), nhưng có thể ít hơn
                int maxColIndex = Math.min(cols.size() - 1, 8); // -1 vì bỏ cột Thời gian

                // Duyệt qua các cột từ 1 đến 7 (tương ứng Thứ 2 đến Chủ Nhật), nhưng không vượt quá số cột thực tế
                for (int colIndex = 1; colIndex <= 7 && colIndex < maxColIndex; colIndex++) {
                    Element col = cols.get(colIndex);
                    if (col.hasAttr("rowspan")) {
                        String rowspanStr = col.attr("rowspan");
                        int rowspan = Integer.parseInt(rowspanStr);
                        int tietBatDau = tiet;
                        int tietKetThuc = tiet + rowspan - 1;

                        // Lấy thông tin từ các thẻ <p>
                        Elements pTags = col.select("p");
                        if (pTags.size() >= 4) {
                            // Xử lý môn học
                            String mon = pTags.get(0).text(); // Lấy toàn bộ văn bản, bao gồm mã môn

                            // Xử lý nhóm, phòng, giảng viên, loại bỏ tiền tố
                            String nhomTo = pTags.get(1).text().replaceAll("Nhóm:\\s*", "");
                            String phong = pTags.get(2).text().replaceAll("Phòng:\\s*", "").replaceAll("-\\s*$", "");
                            String giangVien = pTags.get(3).text().replaceAll("GV:\\s*", "");

                            int thu = colIndex + 1; // Thứ 2 = 2, ..., Chủ Nhật = 8
                            int[] tietBDKT = new int[]{tietBatDau, tietKetThuc};

                            // Giả định lịch áp dụng cho tuần 1-22
                            List<Integer> cacTuan = new ArrayList<>();
                            for (int i = 1; i <= 22; i++) {
                                cacTuan.add(i);
                            }

                            for (int tuan : cacTuan) {
                                LichHoc lh = new LichHoc(mon, phong, nhomTo, giangVien, tietBDKT);
                                ctrinh.themLich(tuan, thu, lh);
                            }
                        } else {
                            System.out.println("Cảnh báo: Ô tại hàng " + rowIndex + ", cột " + colIndex + " thiếu thông tin môn học");
                        }
                    }
                }
            }

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("\n==== MENU ====");
                System.out.println("1. Xem TKB ngày hiện tại");
                System.out.println("2. Xem TKB theo tuần (1-22)");
                System.out.println("3. Xem TKB theo tuần + thứ");
                System.out.println("4. Xem TKB theo ngày bất kỳ (dd/MM/yyyy)");
                System.out.println("0. Thoát");
                System.out.print("Chọn: ");
                int chon = Integer.parseInt(sc.nextLine());
                if (chon == 0) break;
                if (chon == 1) {
                    LocalDate homNay = LocalDate.now();
                    int tuan = ctrinh.quyDoiTuan(homNay);
                    int thu = ctrinh.quyDoiThu(homNay);
                    inTKB(ctrinh, tuan, thu);
                } else if (chon == 2) {
                    System.out.print("Nhập tuần (1-22): ");
                    int tuan = Integer.parseInt(sc.nextLine());
                    for (int thu = 2; thu <= 8; thu++) {
                        inTKB(ctrinh, tuan, thu);
                    }
                } else if (chon == 3) {
                    System.out.print("Nhập tuần: ");
                    int tuan = Integer.parseInt(sc.nextLine());
                    System.out.print("Nhập thứ (2-8, trong đó CN là 8): ");
                    int thu = Integer.parseInt(sc.nextLine());
                    inTKB(ctrinh, tuan, thu);
                } else if (chon == 4) {
                    System.out.print("Nhập ngày (dd/MM/yyyy): ");
                    String s = sc.nextLine();
                    LocalDate ngay = LocalDate.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    int tuan = ctrinh.quyDoiTuan(ngay);
                    int thu = ctrinh.quyDoiThu(ngay);
                    inTKB(ctrinh, tuan, thu);
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void inTKB(CtrinhChinh ct, int tuan, int thu) {
        LocalDate ngay = ct.quyDoiNgay(tuan, thu);
        System.out.printf("== Tuần %d - Thứ %d (%s) ==\n", tuan, thu, ngay);
        Tuan t = ct.getTuan(tuan);
        if (t == null) {
            System.out.println("Không có dữ liệu tuần này.");
            return;
        }
        Thu th = t.getThu(thu);
        if (th == null || th.getLichHoc().isEmpty()) {
            System.out.println("Không có lịch học.");
            return;
        }
        for (LichHoc lh : th.getLichHoc()) {
            System.out.println(lh);
        }
    }

    private static int chuyenThuSangSo(String thu) {
        thu = thu.trim().toLowerCase();
        switch (thu) {
            case "thứ 2":
                return 2;
            case "thứ 3":
                return 3;
            case "thứ 4":
                return 4;
            case "thứ 5":
                return 5;
            case "thứ 6":
                return 6;
            case "thứ 7":
                return 7;
            case "chủ nhật":
            case "cn":
                return 8;
            default:
                throw new IllegalArgumentException("Sai thứ: " + thu);
        }
    }

    private static int[] chuyenTiet(String tiet) {
        String[] parts = tiet.split("-");
        int batDau = Integer.parseInt(parts[0].trim());
        int ketThuc = Integer.parseInt(parts[1].trim());
        return new int[]{batDau, ketThuc};
    }

    private static List<Integer> tachTuan(String tuanStr) {
        List<Integer> ds = new ArrayList<>();
        for (String part : tuanStr.split(",")) {
            if (part.contains("-")) {
                String[] range = part.split("-");
                int start = Integer.parseInt(range[0].trim());
                int end = Integer.parseInt(range[1].trim());
                for (int i = start; i <= end; i++) ds.add(i);
            } else {
                ds.add(Integer.parseInt(part.trim()));
            }
        }
        return ds;
    }
}