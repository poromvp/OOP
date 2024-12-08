import java.io.IOException;
import java.util.Scanner;


public class ImportManager {
    public ImportManager() {
        Import.readFile("import.txt");
        ImportDetail.readFile("importDetail.txt");
    }

    public void addImport(Scanner sc,String staffID){
        System.out.println("Nhap ma don hang (dang IM___)");
        String importID;
        while (true) {
            String tmp = sc.nextLine();
            if (Import.checkImportID(tmp)&&!Import.checkImportList(tmp)) {
                importID=tmp;
                break;
            }
            else System.out.println("Ma nhap sai hoac da bi trung ! Vui lòng nhập lại.");
        }
        Import.addImport(sc,importID,staffID);
        System.out.println("Da tao don nhap hang thanh cong. Hay nhap vao so luong san pham can nhap hang.");
        int n = Integer.parseInt(sc.nextLine());
        for(int i=0;i<n;i++){
            ImportDetail.addImportDetail(sc,importID);
        }
    }
    //Xuat 1 phan tu
    public void outImportByImportId(Import a){
        a.outImport();
        double Total = 0;
        for(int i=0;i<ImportDetail.cnt;i++){
            if(ImportDetail.importDetailsList[i].getImportID().equals(a.importID)){
                ImportDetail.importDetailsList[i].outImportDetail();
                Total+=ImportDetail.importDetailsList[i].getToTal();
            }
        }
        System.out.println("-----------------------------------------");
        System.out.println("Tong hoa don: "+Total);
        System.out.println();
    }
    //Xuat toan bo
    public void outAllImport(){
        for(int i=0;i<Import.cnt;i++){
            outImportByImportId(Import.importsList[i]);
        }
    }
    //Find
    public void findByImportId(Scanner sc){
        String id;
        do{
            String tmp;
            System.out.println("Nhap vao ma cua don nhap hang can tim:");
            tmp = sc.nextLine();
            if(Import.checkImportID(tmp)&&Import.checkImportList(tmp)){
                id=tmp;
                break;
            }
            else {
                System.out.println("Ban da nhap sai hoac id khong ton tai. Vui long nhap lai.");
            }
        }while (true);
        outImportByImportId(Import.getImportById(id));
    }
    //Delete
    public void removeImport(Scanner sc){
        String id;
        do{
            String tmp;
            System.out.println("Nhap vao ma cua don nhap hang can xoa:");
            tmp = sc.nextLine();
            if(Import.checkImportID(tmp)){
                id=tmp;
                break;
            }
            else {
                System.out.println("Ban da nhap sai id. Vui long nhap lai.");
            }
        }while (true);
        int index = -1;
        for (int i = 0; i < Import.cnt; i++) {
            if (Import.importsList[i].getImportID().equals(id)) {
                index = i;
            }
        }
        if (index != -1) {
            Import.removeByImportIndex(index);
        } else {
            System.out.println("Khong tim thay ma don nhap hang.");
        }
    }
     public static void statisticImportByTotal(Scanner sc){
        ImportDetail[] toTal = new ImportDetail[Product.cnt];
         for (int i = 0; i < Product.cnt; i++) {
             toTal[i] = new ImportDetail();
             toTal[i].toTal=0;
             toTal[i].setProductID(Product.productList[i].getProductID());
         }

         // Tính tổng giá trị nhập hàng theo sản phẩm
         for (int i = 0; i < Product.cnt; i++) {
             for (int j = 0; j < ImportDetail.cnt; j++) { // Chỉ lặp đến cnt - 1
                 if (ImportDetail.importDetailsList[j].getProductID().equals(Product.productList[i].getProductID())) {
                     toTal[i].toTal=(toTal[i].getToTal() + ImportDetail.importDetailsList[j].getToTal());
                 }
             }
         }
        for(int i=0;i<=toTal.length-1;i++){
            for(int j=i+1;j<toTal.length;j++){
                if(toTal[i].getToTal()<toTal[j].getToTal()){
                    ImportDetail tmp=toTal[i];
                    toTal[i]=toTal[j];
                    toTal[j]=tmp;
                }
            }
        }
         System.out.println("Ban muon xem bao nhieu san pham co gia tri nhap hang lon nhat");
         int n;
         do {
             try {
                 int tmp=Integer.parseInt(sc.nextLine());
                 if(tmp>Product.cnt){
                     System.out.println("So luong san pham phai be hon so san pham hien co.");
                 }
                 else if (tmp<0) {
                     System.out.println("So luong nhap vao khong duoc am.");
                 } else {
                     n=tmp;
                     break;
                 }
             }
             catch (NumberFormatException e){
                 System.out.println("Gia tri nhap vao la 1 so nguyen hop le."+e.getMessage());
                 return;
             }
         }while (true);
         System.out.println("Thong ke nhung san pham duoc nhap vao voi gia tri lon nhat.");
         for(int i=0;i<n;i++){
             System.out.println(toTal[i].productID+"    "+Product.getProductById(toTal[i].productID).getName()+"   "+toTal[i].toTal);
         }
     }
    public static void statisticImportByQuantity(Scanner sc){
        ImportDetail[] toTal = new ImportDetail[Product.cnt];
        for (int i = 0; i < Product.cnt; i++) {
            toTal[i] = new ImportDetail();
            toTal[i].quantity=0;
            toTal[i].setProductID(Product.productList[i].getProductID());
        }
        for (int i = 0; i < Product.cnt; i++) {
            for (int j = 0; j < ImportDetail.cnt; j++) {
                if (ImportDetail.importDetailsList[j].getProductID().equals(Product.productList[i].getProductID())) {
                    toTal[i].quantity=(toTal[i].getQuantity() + ImportDetail.importDetailsList[j].getQuantity());
                }
            }
        }
        for(int i=0;i<=toTal.length-1;i++){
            for(int j=i+1;j<toTal.length;j++){
                if(toTal[i].getQuantity()<toTal[j].getQuantity()){
                    ImportDetail tmp=toTal[i];
                    toTal[i]=toTal[j];
                    toTal[j]=tmp;
                }
            }
        }
        System.out.println("Ban muon xem bao nhieu san pham co so luong nhap hang lon nhat");
        int n;
        do {
            try {
                int tmp=Integer.parseInt(sc.nextLine());
                if(tmp>Product.cnt){
                    System.out.println("So luong san pham phai be hon so san pham hien co.");
                }
                else if (tmp<0) {
                    System.out.println("So luong nhap vao khong duoc am.");
                } else {
                    n=tmp;
                    break;
                }
            }
            catch (NumberFormatException e){
                System.out.println("Gia tri nhap vao la 1 so nguyen hop le."+e.getMessage());
                return;
            }
        }while (true);
        System.out.println("Thong ke nhung san pham duoc nhap vao voi so luong lon nhat.");
        for(int i=0;i<n;i++){
            System.out.println(toTal[i].productID+"    "+Product.getProductById(toTal[i].productID).getName()+"    "+toTal[i].quantity);
        }
    }
}
