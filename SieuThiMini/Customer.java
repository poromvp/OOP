import java.io.*;
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
    public static Customer[] addCustomer(Customer[] customers, Customer newCustomer) {
        Customer[] updatedCustomers = new Customer[customers.length + 1];
        System.arraycopy(customers, 0, updatedCustomers, 0, customers.length);
        updatedCustomers[customers.length] = newCustomer;
        return updatedCustomers;
    }

    // Phương thức sửa (cập nhật) thông tin của khách hàng trong danh sách (4)
    public static Customer[] updateCustomerByID(Customer[] customers, int customerID) {
        Scanner scanner = new Scanner(System.in);
        Customer customerToUpdate = findCustomerByID(customers, customerID);
        if (customerToUpdate != null) {
            System.out.println("Thông tin khách hàng hiện tại:");
            System.out.println("Mã khách hàng: " + customerToUpdate.getCustomerID());
            System.out.println("Tên khách hàng: " + customerToUpdate.getName());
            System.out.println("Số điện thoại: " + customerToUpdate.getContactNumber());
            System.out.println("Điểm tích lũy: " + customerToUpdate.getLoyaltyPoints());

            System.out.print("Nhập tên khách hàng: ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                customerToUpdate.setName(newName);
            }

            System.out.print("Nhập số điện thoại: ");
            String newContact = scanner.nextLine();
            if (!newContact.trim().isEmpty()) {
                customerToUpdate.setContactNumber(newContact);
            }

            System.out.print("Nhập điểm tích lũy: ");
            String newPointsStr = scanner.nextLine();
            if (!newPointsStr.trim().isEmpty()) {
                try {
                    int newPoints = Integer.parseInt(newPointsStr);
                    customerToUpdate.setLoyaltyPoints(newPoints);
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Điểm tích lũy không hợp lệ.");
                }
            }
            System.out.println("Thông tin khách hàng đã được cập nhật.");
        } else {
            System.out.println("Không tìm thấy khách hàng với mã: " + customerID);
        }
        scanner.close();
        return customers;
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
    public static Customer findCustomerByID(Customer[] customers, int customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID() == customerID) {
                return customer;
            }
        }
        System.out.println("Không tìm thấy khách hàng với mã: " + customerID);
        return null;
    }

    // Phương thức đọc file
    public static Customer[] readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Customer[] customers = new Customer[0];
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int customerID = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String contactNumber = parts[2].trim();
                    int loyaltyPoints = Integer.parseInt(parts[3].trim());
                    customers = addCustomer(customers, new Customer(customerID, name, contactNumber, loyaltyPoints));
                }
            }
            return customers;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new Customer[0];
        }
    }

    // Phương thức ghi vào file 
    public static void writeToFile(String fileName, Customer[] customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Customer customer : customers) {
                writer.write(customer.customerID + "," + customer.name + "," + customer.contactNumber + "," + customer.loyaltyPoints);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}
