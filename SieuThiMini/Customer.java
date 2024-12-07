import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Customer implements QLFile {
    private int customerID; // Mã khách hàng
    private String name; // Tên khách hàng
    private String contactNumber; // Số điện thoại
    private int loyaltyPoints; // Điểm tích lũy
    private Order order;

    //Order order = new Order(); 

    // Constructor không tham số
    public Customer() {
        
    }

    // Constructor có tham số
    public Customer(int customerID, String name, String contactNumber, int loyaltyPoints) {
        this.customerID = customerID;
        this.name = name;
        this.contactNumber = contactNumber;
        this.loyaltyPoints = loyaltyPoints;
        //this.order = order;
    }

    // Getter và Setter
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    // Phương thức xem danh sách khách hàng (2)
    public static void outputCustomer(Customer[] customers) {
        System.out.printf("%-53s╔══════════════════════════════════════════╗\n", " ");
        System.out.printf("%-53s║           DANH SÁCH KHÁCH HÀNG           ║\n", " ");
        System.out.printf("%-53s╚══════════════════════════════════════════╝\n\n", " ");
        System.out.printf(
                "%40s╔═══════════╦══════════════════╦═══════════════╦═════════════════╗\n",
                " ");
        System.out.printf(
                "%40s║ Mã K.Hàng ║  Tên Khách Hàng  ║ Số Điện Thoại ║  Điểm Tích Lũy  ║\n",
                " ");
        for (Customer customer : customers) {
            System.out.printf(
                    "%40s╠═══════════╬══════════════════╬═══════════════╬═════════════════╣\n", " ");
            System.out.printf("%40s║ %-10s║ %-17s║ %-14s║ %-16s║\n", " ", customer.customerID, customer.name,
                    customer.contactNumber, customer.calculateLoyaltyPoints());
        }
        System.out.printf(
                "%40s╚═══════════╩══════════════════╩═══════════════╩═════════════════╝\n\n",
                " ");
    }

    // Phương thức thêm khách hàng vào danh sách (3)
    public static Customer[] addCustomers(Customer[] customers, Scanner scanner) {
        System.out.print("Khách hàng đã có thẻ thành viên chưa? (y/n): ");
        String hasMembership = scanner.nextLine().trim().toLowerCase();
    
        Order order = new Order(); // Khởi tạo đối tượng Order
    
        if (hasMembership.equals("n")) {
            // Thêm khách hàng mới
            System.out.println("\nThêm khách hàng mới:");
    
            System.out.print("Nhập số điện thoại khách hàng: ");
            int customerID = Integer.parseInt(scanner.nextLine());
    
            System.out.print("Nhập số điện thoại khách hàng: ");
            String contactNumber = scanner.nextLine();
    
            System.out.print("Nhập tên khách hàng: ");
            String name = scanner.nextLine();
    
            // Tạo khách hàng mới với điểm tích lũy mặc định là 0
            Customer newCustomer = new Customer(customerID, name, contactNumber, 0);
    
            // Tạo mảng mới để thêm khách hàng
            Customer[] updatedCustomers = new Customer[customers.length + 1];
            System.arraycopy(customers, 0, updatedCustomers, 0, customers.length);
            updatedCustomers[customers.length] = newCustomer;
    
            System.out.println("Đã thêm khách hàng mới: " + name);
            return updatedCustomers;
    
        } else if (hasMembership.equals("y")) {
            // Cập nhật điểm tích lũy cho khách hàng hiện có
            System.out.print("Nhập số điện thoại khách hàng: ");
            String contactNumber = scanner.nextLine();
    
            boolean customerFound = false;
            for (Customer customer : customers) {
                if (customer.getContactNumber().equals(contactNumber)) {
                    customerFound = true;
    
                    // Tăng điểm tích lũy bằng phương thức calculateLoyaltyPoints
                    int updatedPoints = customer.calculateLoyaltyPoints();
                    System.out.println("Cập nhật điểm tích lũy cho khách hàng " + customer.getName() + ". Điểm tích lũy mới: " + updatedPoints);
                    break;
                }
            }
    
            if (!customerFound) {
                System.out.println("Không tìm thấy khách hàng với số điện thoại: " + contactNumber);
            }
    
            return customers;
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            return customers;
        }
    }
    
    

    // Phương thức sửa (cập nhật) thông tin của khách hàng trong danh sách (4)
    public static Customer[] updateCustomerByID(Customer[] customers, int customerID) {
        Scanner scanner = new Scanner(System.in);

        // Tìm kiếm khách hàng theo ID
        Customer customerToUpdate = null;
        for (Customer customer : customers) {
            if (customer.getCustomerID() == customerID) {
                customerToUpdate = customer;
                break;
            }
        }

        if (customerToUpdate != null) {
            System.out.printf(
                    "%40s╔═══════════╦══════════════════╦═══════════════╦═════════════════╗\n",
                    " ");
            System.out.printf(
                    "%40s║ Mã K.Hàng ║  Tên Khách Hàng  ║ Số Điện Thoại ║  Điểm Tích Lũy  ║\n",
                    " ");
            System.out.printf(
                    "%40s╠═══════════╬══════════════════╬═══════════════╬═════════════════╣\n", " ");
            System.out.printf("%40s║ %-10s║ %-17s║ %-14s║ %-16s║\n", " ", customerToUpdate.customerID,
                    customerToUpdate.name, customerToUpdate.contactNumber, customerToUpdate.loyaltyPoints);
            System.out.printf(
                    "%40s╚═══════════╩══════════════════╩═══════════════╩═════════════════╝\n\n",
                    " ");

            System.out.println("\nNhấn Enter để giữ nguyên thông tin hiện tại.");

            // Cập nhật tên khách hàng
            System.out.print("Tên khách hàng: ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                customerToUpdate.setName(newName);
            }

            // Cập nhật số điện thoại
            System.out.print("Số điện thoại: ");
            String newContact = scanner.nextLine();
            if (!newContact.trim().isEmpty()) {
                customerToUpdate.setContactNumber(newContact);
            }

            // Cập nhật điểm tích lũy
            System.out.print("Điểm tích lũy: ");
            String newPointsStr = scanner.nextLine();
            if (!newPointsStr.trim().isEmpty()) {
                try {
                    int newPoints = Integer.parseInt(newPointsStr);
                    customerToUpdate.setLoyaltyPoints(newPoints);
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Điểm tích lũy không hợp lệ. Giữ nguyên điểm tích lũy hiện tại.");
                }
            }

            System.out.println("\nThông tin khách hàng đã được cập nhật thành công.");
        } else {
            System.out.println("Không tìm thấy khách hàng với mã: " + customerID);
        }

        return customers; // Trả về danh sách khách hàng sau khi cập nhật
    }

    // Phương thức xóa khách hàng ra khỏi danh sách (theo mã khách hàng) (5)
    public static Customer[] removeCustomerByID(Customer[] customers, int customerID) {
        int count = 0;
        for (Customer customer : customers) {
            if (customer.getCustomerID() != customerID) {
                count++;
            }
        }
        if (count == customers.length) {
            System.out.println("Không tìm thấy khách hàng với mã: " + customerID);
            return customers;
        }
        Customer[] updatedCustomers = new Customer[count];
        int index = 0;
        for (Customer customer : customers) {
            if (customer.getCustomerID() != customerID) {
                updatedCustomers[index++] = customer;
            }
        }
        System.out.println("Đã xóa khách hàng với mã: " + customerID);
        return updatedCustomers;
    }

    // Phương thức tìm khách hàng có trong danh sách (theo mã khách hàng) (6)
    public static void searchCustomers(Scanner scanner, Customer[] customers) {
        System.out.println("\n--- TÌM KIẾM KHÁCH HÀNG ---");
        System.out.println("Nhập tiêu chí để tìm (nhấn Enter để bỏ qua tiêu chí):");

        System.out.print("Mã khách hàng: ");
        String customerIDStr = scanner.nextLine();
        int customerID = customerIDStr.isEmpty() ? 0 : Integer.parseInt(customerIDStr);

        System.out.print("Tên khách hàng: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = null;
        }

        System.out.print("Số điện thoại: ");
        String contactNumber = scanner.nextLine();
        if (contactNumber.isEmpty()) {
            contactNumber = null;
        }

        System.out.print("Điểm tích lũy: ");
        String loyaltyPointsStr = scanner.nextLine();
        int loyaltyPoints = loyaltyPointsStr.isEmpty() ? 0 : Integer.parseInt(loyaltyPointsStr);

        // Lọc danh sách khách hàng theo các tiêu chí
        Customer[] filteredCustomers = new Customer[customers.length];
        int count = 0;

        for (Customer customer : customers) {
            if (isCustomerMatch(customer, customerID, name, contactNumber, loyaltyPoints)) {
                filteredCustomers[count++] = customer;
            }
        }

        Customer[] result = Arrays.copyOf(filteredCustomers, count);

        // Hiển thị kết quả
        if (result.length == 0) {
            System.out.println("Không tìm thấy khách hàng nào khớp với tiêu chí.");
        } else {
            System.out.println("\nDanh sách khách hàng tìm thấy:");
            Customer.outputCustomer(result);
            ; // Hiển thị chi tiết mỗi Customer
        }
    }

    public void displayDetails() {
        System.out.println("Mã khách hàng: " + customerID);
        System.out.println("Tên khách hàng: " + name);
        System.out.println("Số điện thoại: " + contactNumber);
        System.out.println("Điểm tích lũy: " + loyaltyPoints);
    }

    private static boolean isCustomerMatch(Customer customer, int customerID, String name, String contactNumber,
            int loyaltyPoints) {
        if (customerID != 0 && customer.getCustomerID() != customerID) {
            return false;
        }
        if (name != null && !customer.getName().toLowerCase().contains(name.toLowerCase())) {
            return false;
        }
        if (contactNumber != null && !customer.getContactNumber().contains(contactNumber)) {
            return false;
        }
        if (loyaltyPoints != 0 && customer.getLoyaltyPoints() != loyaltyPoints) {
            return false;
        }
        return true;
    }

    @Override
    // Phương thức đọc file
    public Customer[] readFromFile(String fileName) {
        Customer[] customers = new Customer[0]; // Khởi tạo mảng rỗng
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Loại bỏ khoảng trắng thừa
                if (line.isEmpty()) {
                    continue; // Bỏ qua dòng trống
                }
                String[] parts = line.split(",");
                if (parts.length != 4) { // Kiểm tra định dạng dòng
                    System.out.println("Dòng không hợp lệ: " + line);
                    continue;
                }
                try {
                    // Chuyển đổi dữ liệu từ chuỗi
                    int customerID = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String contactNumber = parts[2].trim();
                    int loyaltyPoints = Integer.parseInt(parts[3].trim());

                    // Tạo đối tượng Customer mới
                    Customer newCustomer = new Customer(customerID, name, contactNumber, loyaltyPoints);

                    // Tạo mảng mới lớn hơn và thêm đối tượng mới
                    Customer[] newCustomers = new Customer[customers.length + 1];
                    System.arraycopy(customers, 0, newCustomers, 0, customers.length);
                    newCustomers[customers.length] = newCustomer;
                    customers = newCustomers;
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi chuyển đổi dữ liệu: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        return customers;
    }

    public void xoaNoiDungFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Mở file ở chế độ ghi đè nhưng không ghi gì cả
        } catch (IOException e) {
            System.out.println("Lỗi khi xóa dữ liệu trong file: " + e.getMessage());
        }
    }

    @Override
    // Phương thức ghi vào file
    public void writeToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {

            writer.write(getCustomerID() + "," + getName() + "," + getContactNumber() + "," + getLoyaltyPoints());
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    // Phương thức thống kê khách hàng có lượt mua hàng nhiều nhất dựa trên điểm
    // tích lũy
    public static Customer[] rankCustomersByLoyaltyPointsWithFile(Customer[] customers) {
        if (customers == null || customers.length == 0) {
            System.out.println("Danh sách khách hàng rỗng. Không thể thực hiện thống kê.");
            return new Customer[0];
        }
    
        // Tạo một bản sao của mảng để tránh thay đổi mảng gốc
        Customer[] customerCopy = new Customer[customers.length];
        System.arraycopy(customers, 0, customerCopy, 0, customers.length);
    
        // Sắp xếp mảng customerCopy theo điểm tích lũy giảm dần (bubble sort)
        for (int i = 0; i < customerCopy.length - 1; i++) {
            for (int j = 0; j < customerCopy.length - 1 - i; j++) {
                if (customerCopy[j].getLoyaltyPoints() < customerCopy[j + 1].getLoyaltyPoints()) {
                    Customer temp = customerCopy[j];
                    customerCopy[j] = customerCopy[j + 1];
                    customerCopy[j + 1] = temp;
                }
            }
        }
    
        return customerCopy;
    }
    

    public Customer getCustomerById(int id) {
        Customer[] customers = readFromFile("SieuThiMini\\customers.txt");
        int tmp = -1;  // Sử dụng -1 để dễ dàng kiểm tra xem có tìm thấy không
        boolean flag = false;
        
        for (int i = 0; i < customers.length; i++) {
            // Kiểm tra null và so sánh id với customerID
            if (customers[i] != null && customers[i].getCustomerID() == id) {
                tmp = i;
                flag = true;
                break;
            }
        }
    
        // Sửa điều kiện so sánh flag và xử lý khi không tìm thấy
        if (!flag) {  // Thay vì flag = false, sử dụng flag == false hoặc !flag
            return null;
        } else {
            return customers[tmp];
        }
    }
    
    // Phương thức giảm giá đơn hàng cho khách hàng khi điểm tích lũy đạt yêu cầu
    public double isLoyaltyPoints() { 
        double totalAmount = order.calculateTotalAmount(); // Lấy tổng tiền từ hóa đơn
        if (this.loyaltyPoints > 2000) {
            return totalAmount - totalAmount * 0.15; // Giảm 15% khi điểm lớn hơn 2000
        } else if (this.loyaltyPoints > 1500 && this.loyaltyPoints <= 2000) {
            return totalAmount - totalAmount * 0.10; // Giảm 10% khi điểm từ 1500-2000
        } else if (this.loyaltyPoints > 1000 && this.loyaltyPoints <= 1500) {
            return totalAmount - totalAmount * 0.05; // Giảm 5% khi điểm từ 1000-1500
        } else {
            return totalAmount; // Không giảm giá khi điểm dưới 1000
        }
    }
    

    // Phương thức tăng điểm tích lũy cho khách hàng dựa trên hóa đơn
    public int calculateLoyaltyPoints() {
        double totalAmount = order.calculateTotalAmount(); // Lấy tổng tiền từ phương thức calculateTotalAmount() trả về kiểu double
        int loyaltyPointsFromOrder = (int) (totalAmount * 0.10);  // Lấy 10% của tổng tiền và chuyển thành int
        int newLoyaltyPoints = this.loyaltyPoints + loyaltyPointsFromOrder;
        return newLoyaltyPoints;
    }
    
}
