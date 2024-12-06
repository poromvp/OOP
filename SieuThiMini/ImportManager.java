import java.util.Scanner;

public class ImportManager {
    public ImportManager() {
    }

    public void addImport(Scanner sc){
        System.out.println("Nhập mã nhập hàng (dạng IM___): ");
        String importID;
        while (true) {
            String tmp = sc.nextLine();
            if (Import.checkImportID(tmp)&&!Import.checkImportList(tmp)) {
                importID=tmp;
                break;
            }
            else System.out.println("Mã nhập không hợp lệ! Vui lòng nhập lại.");
        }
        Import.addImport(sc,importID);
        System.out.println("Da tao don nhap hang thanh cong. Hay nhap vao so luong san pham can nhap hang.");
        int n = Integer.parseInt(sc.nextLine());
        for(int i=0;i<n;i++){
            ImportDetail.addImportDetail(sc,importID);
        }
    }

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

    public void outAllImport(){
        for(int i=0;i<Import.cnt;i++){
            outImportByImportId(Import.importsList[i]);
        }
    }
}
