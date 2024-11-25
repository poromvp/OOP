import java.util.Scanner;

public class test {
    
      public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      int choice;
      do {
      System.out.printf("%-20s%s","","|========================================|\n"
      );
      System.out.printf("%-20s| %-8s %-29s |\n","","" ,"QUẢN LÝ NHÂN SỰ");
      System.out.printf("%-20s%s","","|========================================|\n"
      );
      System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Quản lý nhân viên");
      System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Quản lý phòng ban");
      System.out.printf("%-20s| %-2s %-35s |\n","", "3.", "Quản lý ca làm");
      System.out.printf("%-20s| %-2s %-35s |\n","", "4.", "Quản lý kho hàng");
      System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
      System.out.printf("%-20s%s","","|========================================|\n"
      );
      System.out.print("Lựa chọn của bạn: ");
      choice = Integer.parseInt(scanner.nextLine());
      
      switch (choice) {
      case 1:
      do{
      System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Thêm nhân viên");
      System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Xóa nhân viên");
      System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
      System.out.print("Lựa chọn của bạn: ");
      choice = Integer.parseInt(scanner.nextLine());
      switch (choice) {
      case 1:
      String filepath = "dsnv.txt";
      Staff Mana = new Manager();
      Mana.readFromFile(filepath);
      Mana.writeToFile(filepath);
      break;
      case 2:
      filepath = "dsnv.txt";
      Staff Manb = new Manager();
      Manb.readFromFile(filepath);
      ((Manager)Manb).removeStaff();
      case 0:
      break;
      default:
      System.out.println("Lựa chọn không hợp lệ.");
      }
      } while (choice != 0);
      break;
      
      case 2:
      do {
      System.out.printf("%-20s| %-2s %-35s |\n","", "1.",
      "Thêm nhân viên vào phòng ban");
      System.out.printf("%-20s| %-2s %-35s |\n","", "2.",
      "Xóa nhân viên ra khỏi phòng ban");
      System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
      System.out.print("Lựa chọn của bạn: ");
      choice = Integer.parseInt(scanner.nextLine());
      switch (choice) {
      case 1:
      System.out.println("danh sach hien tai: ");
      String filepath = "DepartmentStaffList.txt";
      Staff DepA = new Department();
      DepA.readFromFile(filepath);
      DepA.writeToFile(filepath);
      break;
      case 2:
      filepath = "DepartmentStaffList.txt";
      Staff DepB= new Department();
      DepB.readFromFile(filepath);
      ((Department)DepB).removeStaffFromDepartment();
      case 0:
      break;
      default:
      System.out.println("Lựa chọn không hợp lệ.");
      }
      } while (choice != 0);
      break;
      
      case 3:
      do {
      System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Sắp xếp lịch làm");
      System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Xóa lịch làm");
      System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
      System.out.print("Lựa chọn của bạn: ");
      choice = Integer.parseInt(scanner.nextLine());
      switch (choice) {
      case 1:
      String filepath= "CashierList.txt";
      Staff DepA = new Cashier();
      DepA.readFromFile(filepath);
      DepA.writeToFile(filepath);
      break;
      case 2:
      filepath= "CashierList.txt";
      Staff DepB= new Cashier();
      DepB.readFromFile(filepath);
      ((Cashier)DepB).removeShift();
      case 0:
      break;
      default:
      System.out.println("Lựa chọn không hợp lệ.");
      }
      } while (choice != 0);
      break;
      
      case 4:
      do {
      System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Hàng trong kho");
      System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Đặt hàng");
      System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
      System.out.print("Lựa chọn của bạn: ");
      choice = Integer.parseInt(scanner.nextLine());
      switch (choice) {
      case 1:
      do {
      System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Thêm hàng trong kho");
      System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Xóa hàng trong kho");
      System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
      System.out.print("Lựa chọn của bạn: ");
      choice = Integer.parseInt(scanner.nextLine());
      switch (choice) {
      case 1:
      String filepath = "Inventory.txt";
      Staff Ivenadd = new InventoryManager();
      Ivenadd.readFromFile(filepath);
      Ivenadd.writeToFile(filepath);
      break;
      case 2:
      filepath = "Inventory.txt";
      Staff Ivenrev = new InventoryManager();
      Ivenrev.readFromFile(filepath);
      ((InventoryManager)Ivenrev).removeIvenProduct();
      break;
      case 0:
      break;
      default:
      System.out.println("Lựa chọn không hợp lệ.");
      }
      } while (choice != 0);
      break;
      
      case 2:
      do {
      System.out.printf("%-20s| %-2s %-35s |\n","", "1.",
      "Thêm hàng trong danh sách đặt hàng");
      System.out.printf("%-20s| %-2s %-35s |\n","", "2.",
      "Xóa hàng trong danh sách đặt hàng");
      System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
      System.out.print("Lựa chọn của bạn: ");
      choice = Integer.parseInt(scanner.nextLine());
      switch (choice) {
      case 1:
      String filepath = "OrderInventory.txt";
      Staff IvenOrderadd = new InventoryManager();
      ((InventoryManager)IvenOrderadd).readFromFileOrder(filepath);
      ((InventoryManager)IvenOrderadd).OrderInventory();
      break;
      case 2:
      filepath = "OrderInventory.txt";
      Staff IvenOrderrev = new InventoryManager();
      ((InventoryManager)IvenOrderrev).readFromFileOrder(filepath);
      ((InventoryManager)IvenOrderrev).removeOrderProduct();
      break;
      case 0:
      break;
      default:
      System.out.println("Lựa chọn không hợp lệ.");
      }
      } while (choice != 0);
      break;
      case 0:
      break;
      default:
      System.out.println("Lựa chọn không hợp lệ.");
      }
      }while(choice!=0);
      break;
      case 0:
      break;
      default:
      System.out.println("Lựa chọn không hợp lệ.");
      }
      } while (choice != 0);
      }
     
}
