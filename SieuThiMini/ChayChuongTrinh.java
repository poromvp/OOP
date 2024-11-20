import java.util.Scanner;
public class ChayChuongTrinh{
    public static void main(String[] args) {
        byte choice;
        Store sieuthi=new Store();
        Scanner scanner =new Scanner (System.in);
        do{
            System.out.println("=================================");
            System.out.println("1. Them nhan su vao danh sach");
            System.out.println("2. Xuat thong tin nhan su");
            System.out.println("3. aaa");
            System.out.println("0. exit");
            System.out.println("=================================");
            System.out.println("Nhap lua chon cua ban:");
            choice=Byte.parseByte(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Dung menu 1. ");
                    byte index;
                    System.out.println("Nhap vi tri muon them: ");
                    index=Byte.parseByte(scanner.nextLine());
                    sieuthi.addStaff(index);
                    break;
                case 2:
                    System.out.println("Dung menu 2. ");
                    sieuthi.xuatStaff();
                    break;
                case 3:
                    System.out.println("Dung menu 3. ");
                    break;
                case 0:
                    System.out.println("Exit Success!. ");
                    break;
                default:
                    System.out.println("Lua chon khong hop le, hay nhap lai!");
                    break;
            }
        }while(choice!=0);
    }
}