import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
                String[] parts = line.split(",");
                if (parts.length == 2 && cnt< 100) { // Kiểm tra nếu mảng chưa đầy
                    Supplier a = new Supplier(
                            parts[0],                // productID
                            parts[1]               // supplier
                    );
                    supplierList[cnt] = a;
                    cnt++; // Di chuyển đến vị trí tiếp theo trong mảng
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}