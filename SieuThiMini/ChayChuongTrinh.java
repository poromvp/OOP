
public class ChayChuongTrinh{
    public static void main(String[] args) {
        // minh update
        private static void manageInvoice(Scanner scanner, Store store) {
            InvoiceManager manager = new InvoiceManager(100);
            int choice;
            do {
                System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
                System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ HÓA ĐƠN");
                System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thêm hóa đơn mới");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Sửa hóa đơn");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Thống kê hóa đơn");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Xóa hóa đơn");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Xuất hóa đơn");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
                System.out.print("Lựa chọn của bạn: ");
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Thêm hóa đơn mới :");
                        manager.createReceipt();
                        break;
                    case 2:
                        System.out.print("Sửa hóa đơn: ");
                        System.out.print("Nhập mã hóa đơn cần sửa: ");
                        int updateId = Integer.parseInt(scanner.nextLine());
                        manager.updateReceipt(updateId, scanner);
                        break;
                    case 3:
                        System.out.print("Thống kê hóa đơn: ");
                        int control;
                        do {
                            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
                            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"THỐNG KÊ HÓA ĐƠN");
                            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thống kê đơn hàng theo thời gian (ngày/tháng/năm) mới, cũ");
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Thống kê đơn hàng theo tổng số tiền giảm dần, tăng dần");
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Thống kê đơn hàng theo quantity giảm dần, tăng dần");
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Thống kê đơn hàng theo mã đơn hàng tăng dần, giảm dần");
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
                            System.out.print("Lựa chọn của bạn: ");
                            control = Integer.parseInt(scanner.nextLine());

                            switch (control) {
                                case 1:
                                    System.out.print("Chọn thứ tự (1: mới -> cũ, 2: cũ -> mới): ");
                                    int order1 = scanner.nextInt();
                                    manager.sortByDate(order1 == 2);
                                    manager.printReceipts();
                                    break;
                                case 2:
                                    System.out.print("Chọn thứ tự (1: giảm dần, 2: tăng dần): ");
                                    int order2 = scanner.nextInt();
                                    manager.sortByTotalAmount(order2 == 2);
                                    manager.printReceipts();
                                    break;
                                case 3:
                                    System.out.print("Chọn thứ tự (1: giảm dần, 2: tăng dần): ");
                                    int order3 = scanner.nextInt();
                                    manager.sortByQuantity(order3 == 2);
                                    manager.printReceipts();
                                    break;
                                case 4:
                                    System.out.print("Chọn thứ tự (1: tăng dần, 2: giảm dần): ");
                                    int order4 = scanner.nextInt();
                                    manager.sortByReceiptId(order4 == 1);
                                    manager.printReceipts();
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Lựa chọn không hợp lệ.");
                            }
                        } while (control != 0);

                        break;
                    case 4:
                        System.out.print("Xóa hóa đơn: ");
                        System.out.print("Nhập mã hóa đơn cần xóa: ");
                        int deleteId = Integer.parseInt(scanner.nextLine());
                        manager.deleteReceipt(deleteId);

                        break;
                    case 5:
                        manager.printReceipts();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                }
            } while (choice != 0);


        }


    }
}