import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
public class Customer {
    private int customerID; // Mã khách hàng
    private String name; // Tên khách hàng
    private String contactNumber; // Số điện thoại
    private int loyaltyPoints; // Điểm tích lũy

    // Constructor không tham số
    public Customer() {

    }

    // Constructor có tham số
    public Customer(int customerID, String name, String contactNumber, int loyaltyPoints) {
        this.customerID = customerID;
        this.name = name;
        this.contactNumber = contactNumber;
        this.loyaltyPoints = loyaltyPoints;
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
        System.out.println("Danh sách khách hàng:");
        for (Customer customer : customers) {
            System.out.println("==================================");
            System.out.println("Mã khách hàng: " + customer.customerID);
            System.out.println("Tên khách hàng: " + customer.name);
            System.out.println("Số điện thoại: " + customer.contactNumber);
            System.out.println("Điểm tích lũy: " + customer.loyaltyPoints);
        }
    }

    // Phương thức thêm khách hàng vào danh sách (3)
    public static Customer[] addCustomers(Customer[] customers) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Nhập số lượng khách hàng cần thêm: ");
        int n = Integer.parseInt(scanner.nextLine());
    
        // Tạo một mảng mới với kích thước đủ lớn
        Customer[] updatedCustomers = new Customer[customers.length + n];
        System.arraycopy(customers, 0, updatedCustomers, 0, customers.length);
    
        for (int i = 0; i < n; i++) {
            System.out.println("\nNhập thông tin cho khách hàng thứ " + (i + 1) + ":");
    
            // Nhập thông tin khách hàng mới
            System.out.print("Nhập mã khách hàng: ");
            int customerID = Integer.parseInt(scanner.nextLine());
    
            System.out.print("Nhập tên khách hàng: ");
            String name = scanner.nextLine();
    
            System.out.print("Nhập số điện thoại khách hàng: ");
            String contactNumber = scanner.nextLine();
    
            System.out.print("Nhập điểm tích lũy: ");
            int loyaltyPoints = Integer.parseInt(scanner.nextLine());
    
            // Tạo đối tượng Customer mới và thêm vào mảng
            Customer newCustomer = new Customer(customerID, name, contactNumber, loyaltyPoints);
            updatedCustomers[customers.length + i] = newCustomer;
    
            System.out.println("Đã thêm khách hàng: " + name);
        }
        return updatedCustomers;
    }
    
    
    // Phương thức sửa (cập nhật) thông tin của khách hàng trong danh sách (4)
    public static Customer[] updateCustomerByID(Customer[] customers, int customerID) {
        Scanner scanner = new Scanner(System.in);
    
        // Tìm kiếm khách hàng theo ID
        Customer customerToUpdate= null;
        for (Customer customer : customers) {
            if (customer.getCustomerID() == customerID) {
                customerToUpdate = customer;
                break;
            }
        }


        if (customerToUpdate != null) {
            System.out.println("Thông tin khách hàng hiện tại:");
            System.out.println("Mã khách hàng: " + customerToUpdate.getCustomerID());
            System.out.println("Tên khách hàng: " + customerToUpdate.getName());
            System.out.println("Số điện thoại: " + customerToUpdate.getContactNumber());
            System.out.println("Điểm tích lũy: " + customerToUpdate.getLoyaltyPoints());
    
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
            for (Customer customer : result) {
                customer.displayDetails(); // Hiển thị chi tiết mỗi Customer
            }
        }
    }

    public void displayDetails() {
        System.out.println("Mã khách hàng: " + customerID);
        System.out.println("Tên khách hàng: " + name);
        System.out.println("Số điện thoại: " + contactNumber);
        System.out.println("Điểm tích lũy: " + loyaltyPoints);
    }
    
    private static boolean isCustomerMatch(Customer customer, int customerID, String name, String contactNumber, int loyaltyPoints) {
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
    


    // Phương thức đọc file
    public static Customer[] readFromFile(String fileName) {
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
                } 
                catch (NumberFormatException e) {
                    System.out.println("Lỗi chuyển đổi dữ liệu: " + line);
                }
            }
        } 
        catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        return customers;
    }
    
    // Phương thức ghi vào file 
    public static void writeToFile(String fileName, Customer[] customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Customer customer : customers) {
                writer.write(customer.getCustomerID() + "," + customer.getName() + "," + customer.getContactNumber() + "," + customer.getLoyaltyPoints());
                writer.newLine();
            }
            System.out.println("Đã ghi dữ liệu vào file: " + fileName);
        } 
        catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    
}
