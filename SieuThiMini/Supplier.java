import java.io.*;
import java.util.Scanner;

public class Supplier {
    public String supplierID;
    public String supplierName;
    public static Supplier[] supplierList=new Supplier[10];
    public static int cnt;

    public Supplier() {
    }

    public Supplier(String supplierID, String supplierName) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
    }

    public static Supplier[] getSupplierList() {
        return supplierList;
    }

    public static void setSupplierList(Supplier[] supplierList) {
        Supplier.supplierList = supplierList;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public static Supplier getSupplierById(String id){
        int tmp = 0;
        int size = supplierList.length;
        for (int i = 0; i < size; i++) {
            if (supplierList[i] != null && supplierList[i].getSupplierID().equals(id)) {
                tmp = i;
                break;
            }
        }
        return supplierList[tmp];
    }
    public static void readSupplierFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (cnt == supplierList.length) {
                    // Mở rộng mảng khi đạt giới hạn
                    Supplier[] newSupplierList= new Supplier[supplierList.length*2];
                    System.arraycopy(supplierList, 0, newSupplierList, 0, supplierList.length);
                    supplierList=newSupplierList;
                }
                String[] parts = line.split(",");
                if (parts.length == 2 ) { // Kiểm tra nếu mảng chưa đầy
                    Supplier a = new Supplier(
                            parts[0],
                            parts[1]
                    );
                    supplierList[cnt] = a;
                    cnt++; // Di chuyển đến vị trí tiếp theo trong mảng
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
    public static void writeSupplierFromFile(String filePath){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < cnt; i++) {
                Supplier s = supplierList[i];
                if (s != null) {
                    // Ghi thông tin sản phẩm vào file, cách nhau bởi dấu phẩy
                    writer.write(s.getSupplierID()+','+s.getSupplierName());
                    writer.newLine(); // Xuống dòng cho sản phẩm tiếp theo
                }
            }
            System.out.println("Đã ghi nhà cung cấp vào file thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    public static void addSupplier(Scanner sc){
        if (cnt == supplierList.length) {
            // Mở rộng mảng khi đạt giới hạn
            Supplier[] newSupplierList= new Supplier[supplierList.length*2];
            System.arraycopy(supplierList, 0, newSupplierList, 0, supplierList.length);
            supplierList=newSupplierList;
        }
        Supplier a = new Supplier();
        int check =0;
        do{
            System.out.println("Nhap supplier id: ");
            String tmp= sc.nextLine();
            if(!checkIDSupplier(tmp) || !checkDuplicateID(tmp)){
                System.out.println("Ban da nhap sai id. Vui long nhap lai");
            }
            else {
                a.setSupplierID(tmp);
                check=1;
            }
        } while(check ==0);
        System.out.println("Nhap vao ten nha cung cap: ");
        a.setSupplierName(sc.nextLine());
        supplierList[cnt++]=a;
        System.out.println("Them nha cung cap thanh cong.");
    }
    //Xoa
    public static void deleteSupplier( String supplierID) {
        int indexToDelete = -1;

        // Tìm chỉ mục sản phẩm cần xóa
        for (int i = 0; i < cnt; i++) {
            if (supplierList[i] != null && supplierList[i].getSupplierID().equals(supplierID)) {
                indexToDelete = i;
                break;
            }
        }

        //Xóa sản phẩm và dịch chuyển các phần tử còn lại
        if (indexToDelete != -1) {
            for (int i = indexToDelete; i < cnt - 1; i++) {
                supplierList[i] = supplierList[i + 1];
            }
            supplierList[--cnt] = null; // Đặt phần tử cuối cùng thành null
            System.out.println("Đã xóa nhà cung cấp thành công.");
        } else {
            System.out.println("Không tìm thấy nhà cung cấp với ID: " + supplierID);
        }
    }

    public static boolean checkIDSupplier(String id){
        if(id.length()!=5){
            return false;
        }
        if(!id.startsWith("SL")){
            return false;
        }
        for(int i=2;i<5;i++){
            if(!Character.isDigit(id.charAt(i))){
                return false;
            }
        }
        return true;
    }
    //Kiem tra id co bi trung khong
    public static boolean checkDuplicateID(String id) {
        for (Supplier sl: supplierList) {
            if (sl!= null && sl.getSupplierID().equals(id)) {
                return false; // Trùng ID
            }
        }
        return true; // Không trùng
    }
    // Sua Supplier
    public static void updateSupplier(String id,Scanner sc) {
        int choice;
        int index = -1;

        // Tìm vị trí của danh mục cần cập nhật
        for (int i = 0; i < cnt; i++) {
            if (supplierList[i] != null && supplierList[i].getSupplierID().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            do {
                System.out.println("------ CHINH SUA NHA CUNG CAP SAN PHAM ------");
                System.out.println("0. Thoát.");
                System.out.println("1. Chinh sua id nha cung cap san pham.");
                System.out.println("2. Chinh sua ten nha cung cap san pham.");
                System.out.print("Lua chon cua ban: ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Lua chon phai la mot so nguyen hop le.");
                    return;
                }
                switch (choice) {
                    case 0:
                        System.out.println("Thoát chỉnh sửa.");
                        break;
                    case 1:
                        System.out.println("Nhap vao id moi(co dang SL___). ");
                        String newId = sc.nextLine();
                        if (!checkIDSupplier(newId) || !checkDuplicateID(newId)) {
                            System.out.println("ID nhap vao khong dung dinh dang hoac bi trung !!!");
                        } else {
                            supplierList[index].setSupplierID(newId);
                            System.out.println("Da chinh sua ID thanh cong.");
                        }
                        break;
                    case 2:
                        System.out.println("Nhap vao ten moi. ");
                        String newName = sc.nextLine();
                        supplierList[index].setSupplierName(newName);
                        System.out.println("Da chinh sua ten thanh cong.");
                        break;
                    default:
                        System.out.println("Lua chon sai. Vui long chon lai.");
                }
            } while (choice != 0);

        } else {
            System.out.println("Khong tim thay nha cung cap san pham voi ID: " + id);
        }
    }
}