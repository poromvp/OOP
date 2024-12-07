
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Scanner;

public class Import {
    public String importID;
    public LocalDate importDate;
    public String staffID;
    public static Import[] importsList = new Import[10];
    public static int cnt=0;
    DateTimeFormatter  formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    // Constructor
    public Import() {
    }

    public Import(String importID, LocalDate importDate, String staffID) {
        this.importID = importID;
        this.importDate = importDate;
        this.staffID = staffID;
    }

    public String getImportID() {
        return importID;
    }

    public void setImportID(String importID) {
        this.importID = importID;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
    // Kiểm tra format ID
    public static boolean checkImportID(String id) {
        if (id.length() != 5 || !id.startsWith("IM")) return false;
        for (int i = 2; i < 5; i++) {
            if (!Character.isDigit(id.charAt(i))) return false;
        }
        return true;
    }
    //Kiem tra xem 1 ma don hang co ton tai trong mảng hay không
    public static boolean checkImportList(String check){
        boolean c =false;
        for (int i=0;i<cnt;i++) {
            if (importsList[i].getImportID().equals(check)) {
                c = true;
                break;
            }
        }
        return c;
    }
    //Tim bang id
    public static Import getImportById(String id){
        int tmp = 0;
        for (int i = 0; i < importsList.length; i++) {
            if (importsList[i] != null && importsList[i].getImportID().equals(id)) {
                tmp = i;
                break;
            }
        }
        return importsList[tmp];
    }
    // Thêm nhập hàng
    public static  void addImport(Scanner sc,String importID) {
        if(cnt == importsList.length){
            Import[] newList = new Import[importsList.length*2];
            System.arraycopy(importsList,0,newList,0,importsList.length);
            importsList=newList;
        }
        Import newImport = new Import();
        System.out.println("Nhap ma nguoi thuc hien nhap hang");
        newImport.staffID=sc.nextLine();
        newImport.importID = importID;
        boolean CheckDate = false;
        while (!CheckDate) {
            System.out.println("Nhap ngay nhan hang: ");
            String ngaySinhInput = sc.nextLine();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                newImport.importDate = LocalDate.parse(ngaySinhInput, format);
                CheckDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Ngay nhan hang khong hop le. Vui long nhap lai(dd/MM/yyyy).");
            }
        }
        importsList[cnt++]=newImport;
    }
    public void outImport() {
        System.out.println("----------Phieu nhap hang----------");
        System.out.println("Ma phieu nhap hang: "+importID);
        System.out.println("Ngay nhap hang: "+importDate.format(formatter));
        System.out.println("Nhan vien thuc hien: "+staffID);
    }
    //read from file
    public static void readFile(String fileName) {
        DateTimeFormatter  format=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0];
                    LocalDate date = LocalDate.parse(parts[1],format);
                    String staff = parts[2];
                    importsList[cnt++] = new Import(id, date, staff);
                }
            }
        } catch (IOException e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Loi ngay thang trong file input.");
        }
    }

    //write to file
    public static void writeFile(String fileName) {
        DateTimeFormatter  formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (BufferedWriter writer= new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < cnt; i++) {
                Import imp = importsList[i];
                writer.write(imp.importID + "," + imp.importDate.format(formatter) + "," + imp.staffID + "\n");
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    //xoa
    public static void removeByImportIndex(int index){
        String id = importsList[index].getImportID();
        ImportDetail.removeByImportId(id);
        for(int i=index;i<cnt-1;i++){
            importsList[i]=importsList[i+1];
        }
        importsList[importsList.length-1]=null;
        cnt--;
        System.out.println("Xoa don nhap hang voi id "+id+" thanh cong.");
    }
}

