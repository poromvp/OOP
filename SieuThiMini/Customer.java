

// Lớp khách hàng
public class Customer {
    // Thuộc tính
    private int customerID; // Mã khách hàng
    private String name; // Tên khách hàng
    private String contactNumber; // Số điện thoại của khách
    private int loyaltyPoints; // Điểm tích lũy của khách

    // Constructor có tham số
    public Customer(int customerID, String name, String contactNumber, int loyaltyPoints) {
        this.customerID = customerID;
        this.name = name;
        this.contactNumber = contactNumber;
        this.loyaltyPoints = loyaltyPoints;
    }

    // Phương thức Getter và Setter
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
    
    public static void Output(Customer[] customers) { // Hàm xem (xuất) danh sách khách hàng
        System.out.println("Danh sach khach hang:");
        for (Customer customer : customers) {
            System.out.println("==================================");
            System.out.println("Ma khach hang: " + customer.customerID);
            System.out.println("Ten khach hang: " + customer.name);
            System.out.println("So dien thoai: " + customer.contactNumber);
            System.out.println("Diem tich luy cua khach hang: " + customer.loyaltyPoints);
        }
    }

    public static Customer[] removeCustomerByID(Customer[] customers, int customerID) { // Hàm xóa khách hàng theo mã (customerID)
        // Đếm số khách hàng không có mã cần xóa
        int count = 0;
        for (Customer customer : customers) {
            if (customer.customerID != customerID) {
                count++;
            }
        }
    
        // Nếu không tìm thấy mã khách hàng, trả về mảng ban đầu
        if (count == customers.length) {
            System.out.println("Khong tim thay khach hang voi ma: " + customerID);
            return customers;
        }
    
        // Tạo mảng mới để chứa danh sách khách hàng sau khi xóa
        Customer[] updatedCustomers = new Customer[count];
        int index = 0;
        for (Customer customer : customers) {
            if (customer.customerID != customerID) {
                updatedCustomers[index++] = customer;
            }
        }
    
        System.out.println("Da xoa khach voi ma: " + customerID);
        return updatedCustomers;
    }
    
    public static Customer findCustomerByID(Customer[] customers, int customerID) { // Hàm tìm khách hàng theo mã (customerID)
        for (Customer customer : customers) {
            if (customer.customerID == customerID) {
                return customer; // Trả về khách hàng tìm được
            }
        }
        System.out.println("Khong tim thay khach hang voi ma: " + customerID);
        return null; // Trả về null nếu không tìm thấy
    }
    
    public static void main(String[] args) {
        // Khởi tạo mảng khách hàng
        Customer[] customers = {
            new Customer(111, "Nhan", "1234", 5),
            new Customer(112, "Lan", "5678", 10),
            new Customer(113, "Minh", "9101", 15)
        };
        
        // Xuất ra danh sách khách hàng
        Output(customers);
        
        // Tìm kiếm khách hàng
        int searchID = 112;
        Customer foundCustomer = findCustomerByID(customers, searchID);
        if (foundCustomer != null) {
            System.out.println("==================================");
            System.out.println("Tim thay khach hang:");
            System.out.println("Ma: " + foundCustomer.customerID);
            System.out.println("Ten: " + foundCustomer.name);
            System.out.println("So dien thoai: " + foundCustomer.contactNumber);
            System.out.println("Diem tich luy: " + foundCustomer.loyaltyPoints);
        }
    
        // Xóa khách hàng
        int deleteID = 113;
        customers = removeCustomerByID(customers, deleteID);
    
        // In danh sách sau khi xóa
        System.out.println("==================================");
        System.out.println("Danh sach khach hang sau khi xoa:");
        for (Customer customer : customers) {
            System.out.println("==================================");
            System.out.println("Ma khach hang: " + customer.customerID);
            System.out.println("Ten khach hang: " + customer.name);
            System.out.println("So dien thoai: " + customer.contactNumber);
            System.out.println("Diem tich luy cua khach hang: " + customer.loyaltyPoints);
        }
    }
    
}
