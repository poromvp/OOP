import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
public class Store implements QLFile{
    public Staff[] staffList;                   // danh sách nhân viên
    public Transaction[] transactions;          // danh sách giao dịch
    public LoyaltyProgram[] loyaltyProgram;     // danh sách khuyến mãi cho khách hàng thân thiết 
    public Order[] orderList;                   // danh sách đơn hàng
    public Store(){
        String filepath=null;
        readFromFile(filepath);
    }
    public Store(Staff[] staffList,
        Transaction[] transactions, LoyaltyProgram[] loyaltyProgram) {
        this.staffList = staffList;
        this.transactions = transactions;
        this.loyaltyProgram = loyaltyProgram; 
    }
    public Staff[] getStaffList() {
        return staffList;
    }
    public void setStaffList(Staff[] staffList) {
        this.staffList = staffList;
    }
    public Transaction[] getTransactions() {
        return transactions;
    }
    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }
    public LoyaltyProgram[] getLoyaltyProgram() {
        return loyaltyProgram;
    }
    public void setLoyaltyProgram(LoyaltyProgram[] loyaltyProgram) {
        this.loyaltyProgram = loyaltyProgram;
    }
    

    /* các thao tác với staffList START */
    
    /* các thao tác với staffList END */

    /* các thao tác cho ds đơn đặt hàng START*/
    public void xuatOrder(){
        for(int i=0;i<orderList.length;i++){
            orderList[i].displayOrderDetails();
        }
    }

    public void addOrder(Scanner scanner){ //thêm đơn hàng
        System.out.print("Bạn muốn thêm bao nhiêu đơn hàng ?: ");
        int n=Integer.parseInt(scanner.nextLine());
        orderList=Arrays.copyOf(orderList, orderList.length+n);
        for(int i=orderList.length-n;i<orderList.length;i++){
            orderList[i]=new Order();
            System.out.print("Bao nhiêu sản phẩm ?: "); //bởi vì 1 đơn hàng có nhiều sản phẩm 
            orderList[i].product=new Product[Integer.parseInt(scanner.nextLine())];
            for(int j=0;j<orderList[i].product.length;j++){
                orderList[i].product[j]=new Product();
            }
            orderList[i].nhapdonhang(scanner, orderList[i].product);
        }
    }

    public void removeOrder(Scanner scanner){ //xóa đơn hàng theo mã
        System.out.print("Bạn muốn xóa đơn hàng nào ? (Nhập mã đơn hàng): ");
        boolean flag=false; //Tạo lính canh để kiểm tra nếu sau khi duyệt mà nó còn false thì sẽ cho nhập lại cho đúng
        do{
            String temp=scanner.nextLine();
            int pos=0; //Vị trí của đơn hàng trong danh sách
            for(int i=0;i<orderList.length;i++){
                if(orderList[i].getOrderId().equals(temp)){
                    pos=i;
                    flag=true;
                    break;
                }
            }
            if(flag){
                if(pos==orderList.length-1){
                    orderList=Arrays.copyOf(orderList, orderList.length-1);
                    System.out.println("Đã xóa đơn hàng");
                }
                else{
                    for(int i=pos;i<orderList.length-1;i++){
                        orderList[i]=orderList[i+1];
                    }
                    orderList=Arrays.copyOf(orderList, orderList.length-1);
                    System.out.println("Đã xóa đơn hàng");
                }
                break;
            }
            System.out.print("Mã Đơn Hàng Mà Bạn Nhập Không Có Trong Danh Sách, Hãy Nhập Lại: ");
        }while(!flag);
    }

    public void editOrder(Scanner scanner){
        System.out.print("Nhập mã đơn hàng cần chỉnh sửa: ");
        String temp=scanner.nextLine();
        boolean flag=false; //dùng lính canh để lặp lại chương trình nếu nhập sai
        byte so_lan_thu=0; // nếu số lần nhập sai quá nhiều thì sẽ break 
        do{
            so_lan_thu++;
            for(int i=0;i<orderList.length;i++){
                if(orderList[i].orderId.equals(temp)){
                    orderList[i].edit(scanner, orderList[i].product); //phương thức chỉnh sửa của class order
                    flag=true;
                    break;
                }
            }
            if(!flag){
                if(so_lan_thu>2){
                    System.out.println("Bạn đã nhập sai quá nhiều lần. Đang thoát...");
                    break;
                }
                System.out.println("Mã Đơn Hàng Bạn Nhập Không Có Trong Danh Sách, Bạn Có Muốn Tiếp Tục Chỉnh Sửa Không?\n 1.Có  0.Không");
                byte choice=Byte.parseByte(scanner.nextLine());
                if(choice==1){
                    System.out.println("Vậy Hãy Nhập Lại Mã Đơn Hàng Chính Xác");
                    System.out.print("--> ");
                    temp=scanner.nextLine();
                }
                else{
                    System.out.println("Đã thoát!");
                    flag=true;
                }
            }
        }while(flag!=true);
    }

    public void thongkeOrder(Scanner scanner){
        System.out.println("\n--- THỐNG KÊ ĐƠN HÀNG ---");
        System.out.println("Nhập tiêu chí để lọc (nhấn Enter để bỏ qua tiêu chí):");

        System.out.print("Mã đơn hàng: ");
        String orderID = scanner.nextLine();
        if (orderID.isEmpty()) orderID = null;

        System.out.print("Ngày đặt hàng (dd/mm/yy): ");
        String orderDate = scanner.nextLine();
        if (orderDate.isEmpty()) orderDate = null;

        System.out.print("Nhập Mã Khách Hàng: ");
        String in=scanner.nextLine();
        int cusID;
        if(in.isEmpty()) cusID=0;
        else{
            cusID = Integer.parseInt(in);
        }

        System.out.print("Nhập Tên Khách Hàng: ");
        String cusName=scanner.nextLine();

        System.out.print("Nhập Số Điện Thoại: ");
        String phone=scanner.nextLine();

        System.out.print("Nhập Điểm Tích Lũy: ");
        int point;
        if(in.isEmpty()) point=0;
        else{
            point = Integer.parseInt(in);
        }

        System.out.print("Mã sản phẩm: ");
        String productID = scanner.nextLine();
        if (productID.isEmpty()) productID = null;

        System.out.print("Tên sản phẩm: ");
        String productName = scanner.nextLine();
        if (productName.isEmpty()) productName = null;
        
        System.out.print("Giá tiền của 1 sản phẩm: ");
        int price;
        in=scanner.nextLine();
        if(in.isEmpty()) price=0;
        else{
            price = Integer.parseInt(in);
        }

        System.out.print("Loại sản phẩm: ");
        String productCatelo = scanner.nextLine();
        if (productCatelo.isEmpty()) productCatelo = null;

        System.out.print("Số lượng: ");
        int productQuanti;
        in=scanner.nextLine();
        if(in.isEmpty()) productQuanti=0;
        else{
            productQuanti = Integer.parseInt(in);
        }

        System.out.print("Nhà cung cấp: ");
        String suppli = scanner.nextLine();
        if (suppli.isEmpty()) suppli = null;


        Order[] filteredOrders = new Order[orderList.length]; // Tạo mảng với kích thước tối đa là độ dài của mảng orders
        int count = 0; // Biến đếm số đơn hàng thỏa mãn điều kiện

        for (Order order : orderList) {
            if (isOrderMatch(order, orderID, orderDate, productID, productName, price, productCatelo, productQuanti, suppli,order.product,cusID,cusName,phone,point)) {
                filteredOrders[count++] = order; // Thêm đơn hàng vào mảng
            }
    }
        Order[] result=new Order[count];
        result=Arrays.copyOf(filteredOrders, count);

        if (result.length == 0) {
            System.out.println("Không tìm thấy đơn hàng nào khớp với tiêu chí.");
        } else {
            System.out.println("\nDanh sách đơn hàng:\n");
            for (Order order : result) {
                order.displayOrderDetails();
            }
        }
    }
    
    public boolean isOrderMatch(Order order, String orderID, String orderDate, String productID, String productName, int price, String productCatelo, int productQuanti, String suppli, Product[] product, int cusID, String cusName, String phone, int point) {
        //Kiểm tra nếu người dùng KO bỏ qua tiêu chí (!=null, !=0) thì kiểm tra tiếp nếu KO trùng khớp (!equal(false)) thì nó sẽ trả về giá trị false để hàm if ở trên kia kiểm trả và vì hàm if trên đó chỉ kiểm tra true nên sẽ không thêm đơn hàng vào mảng
        if (orderID != null && !order.getOrderId().equals(orderID)) return false;
        if (orderDate != null && !order.getOrderDate().equals(orderDate)) return false;
        if (cusID != 0 && order.customer.getCustomerID()!=cusID) return false;
        if (cusName != null && !order.customer.getName().equals(cusName)) return false;
        if (phone != null && !order.customer.getContactNumber().equals(phone)) return false;
        if (point != 0 && order.customer.getLoyaltyPoints()!=point) return false;

        boolean san_pham_thu_may=false;
        for(int i=0;i<product.length;i++){
    //Nếu 1 sản phẩm trong đơn hàng có tiêu chí thì nó sẽ break để kiểm đơn hàng tiếp theo luôn để tiết kiệm thời gian
    //Và nếu ko có 1 tiêu chí thì nó sẽ continute qua sản phẩm khác,khỏi phải kiểm tra các tiêu chí khác để tiết kiệm thời gian
            if (productID != null && !order.product[i].getProductID().equalsIgnoreCase(productID)) continue;
            if (productName != null && !order.product[i].getName().equalsIgnoreCase(productName)) continue;
            if (price != 0 && order.product[i].getPrice()!=price) continue;
            if (productCatelo != null && !order.product[i].getCategory().equalsIgnoreCase(productCatelo)) continue;
            if (productQuanti != 0 && order.product[i].getQuantity()!=productQuanti) continue;
            if (suppli != null && !order.product[i].getSupplier().equalsIgnoreCase(suppli)) continue;
            san_pham_thu_may=true;
            break;
        }
        if(!san_pham_thu_may) return false;
        return true;
    }

    /* các thao tác cho ds đơn đặt hàng END*/

    @Override
    public void readFromFile(String filePath){
        /* Tải danh sách đơn hàng - Kiệt */
        filePath="C:\\Users\\Dell\\OneDrive\\Desktop\\Java\\OOP_DOAN\\SieuThiMini\\donhang.txt";
        int i, n;
        n=0;
        try (BufferedReader br= new BufferedReader(new FileReader(filePath))){
            String Line;
            while ((Line= br.readLine())!=null){
                n++;
            }
            br.close();
            Line=Line+"line"; // Để cho nó không hiện broblem isn't used nữa :)))
        } catch (IOException e){
            e.printStackTrace();
        } //Đoạn này đọc số dòng để lưu số lượng đơn hàng có trong danh sách

        orderList=new Order[n];
        i=0;
        try (BufferedReader br= new BufferedReader(new FileReader(filePath))){
            String Line;
        //Cấu trúc của file gồm 3 thành phần: thông tin đơn hàng; thông tin khách hàng; thông tin sản phẩm
        //trong thông tin khách hàng, mỗi thuộc tính cách nhau bằng 1 dấu ","
        //trong thông tin sản phẩm, cũng cách nhau 1 dấu "," và do 1 đơn hàng có nhiều sản phẩm khác nhau
        //nên trong 1 hóa đơn sẽ chứa 1 mảng các sản phẩm, các sản phẩm và gồm thông tin của nó cách nhau bằng 1 dấu "|"
            while ((Line=br.readLine())!=null){
                orderList[i] = new Order();
                String [] parts= Line.split(";");
                orderList[i].setOrderId(parts[0]);
                orderList[i].setOrderDate(parts[1]);
                String[] customerParts = parts[2].split(","); //lưu các phần của khách hàng như id, tên, ...
                orderList[i].customer.setCustomerID(Integer.parseInt(customerParts[0]));
                orderList[i].customer.setLoyaltyPoints(Integer.parseInt(customerParts[1]));
                orderList[i].customer.setName(customerParts[2]);
                orderList[i].customer.setContactNumber(customerParts[3]);
                String[] productParts = parts[3].split("\\|"); // tách các sản phẩm cùng thông tin của nó ra
                orderList[i].product=new Product[productParts.length];
                for (int j = 0; j < productParts.length; j++) {
                    orderList[i].product[j]=new Product();
                    String[] ThongTinProduct = productParts[j].split(",");//tác các phần của sản phẩm như id, tên,...
                    orderList[i].product[j].setProductID(ThongTinProduct[0]);
                    orderList[i].product[j].setName(ThongTinProduct[1]);
                    orderList[i].product[j].setPrice(Integer.parseInt(ThongTinProduct[2]));
                    orderList[i].product[j].setCategory(ThongTinProduct[3]);
                    orderList[i].product[j].setQuantity(Integer.parseInt(ThongTinProduct[4]));
                    orderList[i].product[j].setSupplier(ThongTinProduct[5]);
                }
                i++;
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        /* Tải danh sách đơn hàng - Kiệt end */
    }
    @Override
    public void writeToFile(String filePath){
        System.out.println();
    }
}
