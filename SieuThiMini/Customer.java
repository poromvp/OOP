import java.io.*;
import java.util.Scanner;

public class Customer {
    // Thuộc tính
    private int customerID; // Mã khách hàng
    private String name; // Tên khách hàng
    private String contactNumber; // Số điện thoại của khách
    private int loyaltyPoints; // Điểm tích lũy của khách

    // Constructor không tham số
    public Customer() {}

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

    // Phương thức xuất danh sách khách hàng
    public static void output(Customer[] customers) {
        System.out.println("Danh sach khach hang:");
        for (Customer customer : customers) {
            System.out.println("==================================");
            System.out.println("Ma khach hang: " + customer.customerID);
            System.out.println("Ten khach hang: " + customer.name);
            System.out.println("So dien thoai: " + customer.contactNumber);
            System.out.println("Diem tich luy: " + customer.loyaltyPoints);
        }
    }

    // Phương thức thêm khách hàng
    public static Customer[] addCustomer(Customer[] customers, Customer newCustomer) {
        Customer[] updatedCustomers = new Customer[customers.length + 1];
        for (int i = 0; i < customers.length; i++) {
            updatedCustomers[i] = customers[i];
        }
        updatedCustomers[customers.length] = newCustomer;
        System.out.println("Da them khach hang: " + newCustomer.getName());
        return updatedCustomers;
    }

    // Phương thức tìm khách hàng theo ID
    public static Customer findCustomerByID(Customer[] customers, int customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID() == customerID) {
                return customer;
            }
        }
        System.out.println("Khong tim thay khach hang voi ma: " + customerID);
        return null;
    }

    // Phương thức xóa khách hàng theo ID
    public static Customer[] removeCustomerByID(Customer[] customers, int customerID) {
        int count = 0;
        for (Customer customer : customers) {
            if (customer.getCustomerID() != customerID) {
                count++;
            }
        }
        if (count == customers.length) {
            System.out.println("Khong tim thay khach hang voi ma: " + customerID);
            return customers;
        }
        Customer[] updatedCustomers = new Customer[count];
        int index = 0;
        for (Customer customer : customers) {
            if (customer.getCustomerID() != customerID) {
                updatedCustomers[index++] = customer;
            }
        }
        System.out.println("Da xoa khach hang voi ma: " + customerID);
        return updatedCustomers;
    }

    // Phương thức đọc danh sách khách hàng từ file
    public static Customer[] readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Customer[] customers = new Customer[0];
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Loại bỏ khoảng trắng thừa
                if (line.isEmpty()) { // Bỏ qua dòng trống
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length != 4) { // Kiểm tra định dạng đúng (4 phần tử)
                    System.out.println("Dòng không hợp lệ: " + line);
                    continue;
                }
                try {
                    int customerID = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String contactNumber = parts[2].trim();
                    int loyaltyPoints = Integer.parseInt(parts[3].trim());
                    customers = addCustomer(customers, new Customer(customerID, name, contactNumber, loyaltyPoints));
                } 
                catch (NumberFormatException e) {
                    System.out.println("Lỗi chuyển đổi dữ liệu: " + line);
                }
            }
            return customers;
        } 
        catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new Customer[0];
        }
    }


    // Phương thức ghi danh sách khách hàng ra file
    public static void writeToFile(String fileName, Customer[] customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Customer customer : customers) {
                writer.write(customer.customerID + "," + customer.name + "," + customer.contactNumber + "," + customer.loyaltyPoints);
                writer.newLine();
            }
            System.out.println("Da ghi danh sach khach hang vao file: " + fileName);
        } catch (IOException e) {
            System.out.println("Loi khi ghi file: " + e.getMessage());
        }
    }

    // Hàm main
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Đọc danh sách khách hàng từ file
        String fileName = "customers.txt";
        Customer[] customers = readFromFile(fileName);

        // In danh sách khách hàng ban đầu
        System.out.println("Danh sach khach hang ban dau:");
        output(customers);

        // Thêm khách hàng mới
        System.out.println("Them khach hang moi:");
        System.out.print("Nhap ma khach hang: ");
        int newID = scanner.nextInt();
        scanner.nextLine(); // Xóa bỏ dòng trống
        System.out.print("Nhap ten khach hang: ");
        String newName = scanner.nextLine();
        System.out.print("Nhap so dien thoai: ");
        String newContact = scanner.nextLine();
        System.out.print("Nhap diem tich luy: ");
        int newPoints = scanner.nextInt();
        customers = addCustomer(customers, new Customer(newID, newName, newContact, newPoints));

        // Tìm khách hàng
        System.out.print("Nhap ma khach hang de tim: ");
        int searchID = scanner.nextInt();
        Customer foundCustomer = findCustomerByID(customers, searchID);
        if (foundCustomer != null) {
            System.out.println("Thong tin khach hang:");
            System.out.println("Ma khach hang: " + foundCustomer.getCustomerID());
            System.out.println("Ten khach hang: " + foundCustomer.getName());
            System.out.println("So dien thoai: " + foundCustomer.getContactNumber());
            System.out.println("Diem tich luy: " + foundCustomer.getLoyaltyPoints());
        }

        // Xóa khách hàng
        System.out.print("Nhap ma khach hang de xoa: ");
        int deleteID = scanner.nextInt();
        customers = removeCustomerByID(customers, deleteID);

        // Ghi danh sách khách hàng mới ra file
        writeToFile(fileName, customers);
        scanner.close();
    }
}
