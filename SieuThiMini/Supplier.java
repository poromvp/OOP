import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Supplier {
    public String supplierID;
    public String supplierName;
    public static Supplier[] supplierList=new Supplier[100];
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
    public static void addSupplier(){
        if (cnt == supplierList.length) {
            // Mở rộng mảng khi đạt giới hạn
            Supplier[] newSupplierList= new Supplier[supplierList.length*2];
            System.arraycopy(supplierList, 0, newSupplierList, 0, supplierList.length);
            supplierList=newSupplierList;
        }
        Supplier a = new Supplier();
        Scanner sc = new Scanner(System.in);
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
        System.out.println("Nhap gia ten nha cung cap: ");
        a.setSupplierName(sc.nextLine());
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
}