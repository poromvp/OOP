import java.util.Scanner;

public class ImportManager {
    public ImportManager() {
        Import.readFile("import.txt");
        ImportDetail.readFile("importDetail.txt");
    }

    public void addImport(Scanner sc){
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
        Import.addImport(sc,importID);
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

}
