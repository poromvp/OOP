import java.io.*;
import java.util.Scanner;

public class ImportDetail {
    public String importID;
    public String productID;
    public int quantity;
    public int price;
    public int toTal;
    public static ImportDetail[] importDetailsList = new ImportDetail[10];
    public static int cnt=0;

    public ImportDetail(String importID, String productID, int quantity, int price) {
        this.importID = importID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.toTal = this.price*this.quantity;
    }

    public ImportDetail() {
    }

    public String getImportID() {
        return importID;
    }

    public void setImportID(String importID) {
        this.importID = importID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getToTal() {
        return toTal;
    }

    public void setToTal() {
        this.toTal = this.price*this.quantity;
    }

    public static ImportDetail[] getImportDetailsList() {
        return importDetailsList;
    }

    public static void setImportDetailsList(ImportDetail[] importDetailsList) {
        ImportDetail.importDetailsList = importDetailsList;
    }

    public static int getCnt() {
        return cnt;
    }

    public static void setCnt(int cnt) {
        ImportDetail.cnt = cnt;
    }

    public static void addImportDetail(Scanner sc,String importID){
        if(cnt == importDetailsList.length){
            ImportDetail[] newList = new ImportDetail[importDetailsList.length*2];
            System.arraycopy(importDetailsList,0,newList,0,importDetailsList.length);
            importDetailsList=newList;
        }
        ImportDetail a = new ImportDetail();
        a.importID=importID;
//        do{
//            System.out.println("Nhap vao id cua phieu nhap kho.");
//            String id = sc.nextLine();
//            if(Import.checkImportID(id)&&Import.checkImportList(id)){
//                a.importID=id;
//                break;
//            }
//            else {
//                System.out.println("Ban da nhap sai ID cua phieu nhap kho. Hay nhap lai.");
//            }
//        }while(true);
        do {
            System.out.println("Nhap vao id cua san pham.");
            String id = sc.nextLine();
            if (Product.checkIDProduct(id)){
                a.productID=id;
                break;
            }
            else {
                System.out.println("Ban da nhap sai ID cua san pham. Hay nhap lai.");
            }
        }while(true);
        int quantity;
        do {
            System.out.println("Nhap so luong san pham: ");
            try {
                quantity = Integer.parseInt(sc.nextLine());
                if (quantity < 0) {
                    System.out.println("So luong phai lon hon hoac bang 0. Vui long nhap lai.");
                } else {
                    a.setQuantity(quantity);
                }
            } catch (NumberFormatException e) {
                System.out.println("So luong nhap vao phai la mot so nguyen hop le. Vui long nhap lai.");
                quantity = -1;
            }
        } while (quantity < 0);
        int price;
        do {
            System.out.println("Nhap don gia cho 1 san pham: ");
            try {
                price = Integer.parseInt(sc.nextLine());
                if (price < 0) {
                    System.out.println("Gia san pham phai lon hon hoac bang 0. Vui long nhap lai.");
                } else {
                    a.setPrice(price);
                }
            } catch (NumberFormatException e) {
                System.out.println("Gia nhap vao phai la mot so nguyen hop le. Vui long nhap lai.");
                price = -1;
            }
        } while (price < 0);
        a.setToTal();
        importDetailsList[cnt++]=a;
        if (!Product.checkProductList(a.getProductID())){
            System.out.println("San pham nay chua co trong danh sach san pham hien co.");
            System.out.println("Vui long dien day du thong tin cho san pham.");
            Product.addProductForImport(sc,a.getProductID(),a.getQuantity());
        }
        else {
            for(int i=1;i<Product.cnt;i++){
                if(Product.productList[i].getProductID().equals(a.getProductID())){
                    Product.productList[i].setQuantity(a.quantity+Product.productList[i].getQuantity());
                    System.out.println("Da cap nhat cho san pham co id "+a.productID+" thanh cong.");
                }
            }
        }
    }
    public void outImportDetail(){
        System.out.println(productID+"     "+quantity+"     "+price+"     "+toTal);
    }
    //read from file
    public static void readFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (cnt == importDetailsList.length) {
                    ImportDetail[] newList = new ImportDetail[importDetailsList.length * 2];
                    System.arraycopy(importDetailsList, 0, newList, 0, importDetailsList.length);
                    importDetailsList = newList;
                }
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String importID = parts[0];
                    String productID = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    int price = Integer.parseInt(parts[3]);
                    int total = quantity*price;
                    // Thêm vào danh sách
                    ImportDetail detail = new ImportDetail(importID, productID,quantity,price);
                    importDetailsList[cnt++] = detail;
                }
            }
            System.out.println("Đọc file thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi định dạng số trong file.");
        }
    }
    //write to file
    public static void writeFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < cnt; i++) {
                ImportDetail detail = importDetailsList[i];
                writer.write(detail.importID + "," + detail.productID + "," + detail.quantity + "," + detail.price + "," + detail.toTal + "\n");
            }
            System.out.println("Ghi file thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
}
