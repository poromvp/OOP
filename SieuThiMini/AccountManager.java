import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
public class AccountManager extends Staff {
    protected String Account;
    protected String PassWord;
    protected String Name;
    protected String Status;

    // Constructor mặc định
    public AccountManager(String staffID, String name, String role, Double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    // Constructor mặc định không có tham số
    public AccountManager() {
    }

    public AccountManager(String staffID, String name, String role, Double salary, String contactNum, String aCCount,
            String passWord, String Name, String Status) {
        super(staffID, name, role, salary, contactNum);
        Account = aCCount;
        PassWord = passWord;
        this.Name = Name;
        this.Status = Status;
    }

    public AccountManager(String aCCount, String passWord, String Name, String Status) {
        Account = aCCount;
        PassWord = passWord;
        this.Name = Name;
        this.Status = Status;

    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }



    Scanner sc = new Scanner(System.in);

    public boolean checkaccount ( String acc, String pass){
        AccountManager[] accounts = readFromFile("AccountManager.txt");
        boolean check = false;
        for( AccountManager account : accounts){
            if(acc.equals(account.getAccount()) && pass.equals(account.getPassWord())&&account.getStatus().equals("active")){
                check = true;
                break;
            }
        }
        return check;
    }
    public int returnAccount (String acc, String pass){
        if(checkaccount(acc,pass)){
            if(acc.startsWith("NV")){
                return 1;
            } else if (acc.startsWith("NK")) {
                return 2;
            } else if (acc.startsWith("QL")) {
                return 3;
            }
            else {
                System.out.println("Loi khi tim nhan vien.");
                return -1;
            }
        }
        return -1;
    }

    // Phương thức đọc thông tin phòng ban từ file
    @Override
    public AccountManager[] readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            AccountManager[] Accounts = new AccountManager[0]; // Bắt đầu với một mảng rỗng

            // Đọc từng dòng trong file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");

                // Kiểm tra độ dài mảng parts (số phần tử trong dòng dữ liệu)
                if (parts.length >= 3) { // 3 phần tử cho tài khoản nhân viên, tên nhân viên, và mật khẩu
                    String acc = parts[0];
                    String name = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
                    String pass = parts[1];
                    String status = parts[2];

                    // Tạo một đối tượng Account mới với các tham số đầy đủ
                    AccountManager newAccount = new AccountManager(acc, pass, name, status);

                    // Mở rộng mảng departments và thêm Department mới vào
                    Accounts = Arrays.copyOf(Accounts, Accounts.length + 1);
                    Accounts[Accounts.length - 1] = newAccount;

                }
            }
            return Accounts;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new AccountManager[0]; // Trả về mảng rỗng nếu có lỗi
        }
    }

    public void getdetail() {
        AccountManager[] temp = readFromFile("AccountManager.txt");
        if (temp == null || temp.length == 0) {
            System.out.println("Không có dữ liệu tài khoản");
            return;
        }
    
        System.out.println("Danh sách các tài khoản");
        System.out.println("╔═════════════════╤════════════════════════════════╤═════════════════════════╤════════════════╗");
        System.out.printf("║ %-15s │ %-30s │ %-23s │ %-14s ║\n", "Tài khoản", "Tên nhân viên", "Mật khẩu", "Trạng thái");
        System.out.println("╠═════════════════╪════════════════════════════════╪═════════════════════════╪════════════════╣");
    
        for (AccountManager Account : temp) {
            System.out.printf("║ %-15s │ %-30s │ %-23s │%-15s ║\n",
                Account.getAccount(), Account.getName(), Account.getPassWord(), Account.getStatus());

        }
        System.out.println("╚═════════════════╧════════════════════════════════╧═════════════════════════╧════════════════╝");
        System.out.println("");
    }

    

    // Phương thức ghi lại dữ liệu vào file
    public void writeToFile(String fileName, AccountManager[] acc) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Duyệt qua mảng các đối tượng Account và ghi thông tin vào file
            for (AccountManager Account : acc) {
                // Ghi thông tin phòng ban vào file 
                writer.write(Account.getAccount() + " " + Account.getPassWord() + " " + Account.getStatus() + " "  + Account.getName());
                writer.newLine(); // Thêm dòng mới sau mỗi tài khoản
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void add() {
        int count = 0;
        Manager temp = new Manager();
        Manager[] managers = temp.readFromFile("dsnv.txt");

        // Đọc danh sách tài khoản hiện tại từ file
        AccountManager[] accounts = readFromFile("AccountManager.txt");

        String[] temp3= new String[0];
        String[] Nametemp = new String[0];

        for(Manager manager : managers){
            boolean hasAccount = false;
            for(AccountManager account : accounts){
                if(manager.getStaffID().equals(account.getAccount())){
                    hasAccount = true;
                    break;
                }
            }
            if(!hasAccount){
                System.out.println("===========================================================");
                System.out.println("nhân viên chưa có tài khoản có mã và tên là: "+ manager.getStaffID()+ " || " + manager.getName());
                count++;
                temp3=Arrays.copyOf(temp3, temp3.length +1);
                temp3[temp3.length-1] = manager.getStaffID();
                Nametemp= Arrays.copyOf(Nametemp, Nametemp.length+1);
                Nametemp[Nametemp.length-1]= manager.getName();
            }
        }
        System.out.println("===========================================================");


        if (count == 0){
            System.out.println("Tất cả nhân viên đã có tài khoản !!! Bạn hãy thêm một nhân viên mới !!!");
            System.out.println();
        }else{
            System.out.print("Số lượng nhân viên chưa có tài khoản : "+count);
            System.out.println();
            for(int j=0; j<count; j++){
                // Nhập tài khoản mới
                System.out.print("Tài khoản của nhân viên mới: "+ temp3[j]);
                String acc = temp3[j];
                System.out.println();
            
                System.out.print("Tên của nhân viên sở hữu tài khoản: "+ Nametemp[j]);
                String Name = Nametemp[j];
                System.out.println();
            
                System.out.print("Nhập mật khẩu cho tài khoản: ");
                String Pass = sc.nextLine();
                if (Pass.isEmpty()) {
                    System.out.println("Vì bạn không nhập mật khẩu cho tài khoản mới này nên tài khoản này có mật khẩu mặc định là 1");
                    Pass = "1";
                    System.out.println();
                }

                System.out.print("Bạn muốn trạng thái nào cho tài khoản ( 1.active(hoạt động) hoặc 2.banned(khoá lại) ): ");
                    int option = Integer.parseInt(sc.nextLine());

                    while(option != 1 && option != 2){
                        System.out.print("Lựa chọn không hợp lệ mời nhập lại: ");
                        option = Integer.parseInt(sc.nextLine());
                    }

                    String status;
                    if(option == 1){
                        status = "active";
                    } else{
                        status = "banned";
                    }

                // Tạo tài khoản mới
                AccountManager newaccount = new AccountManager(acc, Pass, Name, status);
            
                accounts = Arrays.copyOf(accounts, accounts.length + 1);
                accounts[accounts.length - 1] = newaccount;
                
        
                // Ghi lại dữ liệu vào file
                writeToFile("AccountManager.txt", accounts);
            
                // Hiển thị danh sách phòng ban sau khi cập nhật
                System.out.println("Danh sách tài khoản đã được cập nhật ! ");
                System.out.println("===========================================================");
            }
         }
    }


    @Override
    public void remove() {
        int count =0;
        Manager temp = new Manager();
        Manager[] managers = temp.readFromFile("dsnv.txt");

        // Đọc danh sách tài khoản hiện tại từ file
        AccountManager[] accounts = readFromFile("AccountManager.txt");
        System.out.println();
    
        for(AccountManager account : accounts){
            boolean hasAccount= false;
            for(Manager manager :managers){
                if(account.getAccount().equals(manager.getStaffID())){
                    hasAccount = true;
                    break;
                }
            }
            if(!hasAccount){
                count++;
                String tmp = account.getAccount();
                // Duyệt qua mảng accout và đếm số lượng tài khoản cần giữ lại
                int newSize = 0;
                for (AccountManager acc : accounts) {
                    if (!acc.getAccount().equals(tmp)) {
                        newSize++; //nếu không phải là tài khoản bị xoá, tăng kích thước mảng mới
                    }
                }
            
                if (newSize == accounts.length) {
                    System.out.println("Không tìm thấy tài khoản với mã: " + tmp);
                } else {
                    // Tạo một mảng mới với kích thước giảm đi 1
                    AccountManager[] updatedAccount = new AccountManager[newSize];
                    int index = 0;
            
                    // Duyệt qua mảng và sao chép các tài khoản không bị xoá
                    for (AccountManager acc : accounts) {
                        if (!acc.getAccount().equals(tmp)) {
                            updatedAccount[index++] = acc;
                        }
                    }
    
                    // Ghi lại danh sách mới vào file
                    writeToFile("AccountManager.txt", updatedAccount);
                    System.out.println("tài khoản " + tmp + " đã được xoá.");
                }
            }
        }

    }

    @Override
    public void ChangeInFo() {
        int count =0;
        Manager temp = new Manager();
        Manager[] managers = temp.readFromFile("dsnv.txt");

        // Đọc danh sách tài khoản hiện tại từ file
        AccountManager[] accounts = readFromFile("AccountManager.txt");
        System.out.println();

        for(AccountManager account : accounts){
            for(Manager manager: managers){
                if(manager.getStaffID().equals(account.getAccount())){
                    if(!manager.getName().equals(account.getName())){
                        count ++;
                        account.setName(manager.getName());

                        System.out.print("Bạn có muốn thay đổi mật khẩu và trạng thái của tài khoản nhân viên này không ? y (có) / n (không)): ");
                        String choice = sc.nextLine();
                        while(choice.equalsIgnoreCase("Y") && choice.equalsIgnoreCase("N")){
                            System.out.print("Lựa chọn của bạn không hợp lệ !!! mời nhập lại (nhập y (có) / n (không)): ");
                            choice = sc.nextLine();
                        }
                        if(choice.equalsIgnoreCase("Y")){
                            System.out.println("Nhập thông tin mới cho tài khoản ");

                            System.out.print("Nhập mật khẩu mới (bỏ trống nếu bạn không muốn thay đổi): ");
                            String newPass = sc.nextLine();
                            if(!newPass.isEmpty()){
                                account.setPassWord(newPass);
                            }

                            System.out.print("Bạn muốn trạng thái nào cho tài khoản ( 1.active(hoạt động) hoặc 2.banned(khoá lại) hoặc 0.(không thay đổi) ): ");
                            int option = Integer.parseInt(sc.nextLine());

                            while(option != 1 && option != 2 && option != 0){
                                System.out.print("Lựa chọn không hợp lệ mời nhập lại: ");
                                option = Integer.parseInt(sc.nextLine());
                            }

                            if(option == 1){
                                account.setStatus("active");
                            } else if(option == 2){
                                account.setStatus("banned");
                            } else {
                                account.setStatus(account.getStatus());
                            }

                            System.out.println("Tài khoản đã được cập nhật !!!");
                            break;
                        }
                        else {
                            System.out.println("Tài khoản không có thay đổi");
                        }
                    }
                }
            }
        }
        if(count == 0 ){
            System.out.print("nhập tài khoản bạn muốn đổi mật khẩu và trạng thái: ");
            String TK = sc.nextLine();

            boolean found = false;
            for(AccountManager account : accounts){
                if(account.getAccount().equals(TK)){
                    found = true;
                    System.out.print("Nhập mật khẩu mới cho tài khoản (để trống nếu không muốn thay đổi): ");
                    String newPass = sc.nextLine();
                    if(!newPass.isEmpty()){
                        account.setPassWord(newPass);
                    }

                    System.out.print("Bạn muốn trạng thái nào cho tài khoản ( 1.active(hoạt động) hoặc 2.banned(khoá lại) hoặc 0.(không thay đổi) ): ");
                    int option = Integer.parseInt(sc.nextLine());

                    while(option != 1 && option != 2 && option != 0){
                        System.out.print("Lựa chọn không hợp lệ mời nhập lại: ");
                        option = Integer.parseInt(sc.nextLine());
                    }

                    if(option == 1){
                        account.setStatus("active");
                    } else if(option == 2){
                        account.setStatus("banned");
                    } else {
                        account.setStatus(account.getStatus());
                    }

                    System.out.println("Tài khoản đã được cập nhật");
                }
            }
            if(!found){
                System.out.println("Không tìm thấy tài khoản");
            }
            writeToFile("AccountManager.txt", accounts);
        }
        writeToFile("AccountManager.txt", accounts);
    }
    

    public void search() {
        // Đọc danh sách tài khoản từ file
        AccountManager[] accounts = readFromFile("AccountManager.txt");
    
        // Yêu cầu nhập các tiêu chí tìm kiếm
        System.out.println("Nhập tiêu chí tìm kiếm (Có thể bỏ qua một số tiêu chí bằng cách nhấn Enter)");
    
        // Tiêu chí tìm kiếm theo tài khoản
        System.out.print("Nhập tài khoản (hoặc nhấn Enter để bỏ qua): ");
        String accountID = sc.nextLine().trim();
    
        // Tiêu chí tìm kiếm theo tên nhân viên
        System.out.print("Nhập tên nhân viên (hoặc nhấn Enter để bỏ qua): ");
        String name = sc.nextLine().trim();
    
        // Tiêu chí tìm kiếm theo mật khẩu
        System.out.print("Nhập mật khẩu (hoặc nhấn Enter để bỏ qua): ");
        String password = sc.nextLine().trim();
    
        // Tiêu chí tìm kiếm theo trạng thái
        System.out.print("Nhập trạng thái (active/banned) (hoặc nhấn Enter để bỏ qua): ");
        String status = sc.nextLine().trim();
    
        // Kiểm tra nếu không có tiêu chí nào được nhập
        boolean found = false;
    
        // In ra tiêu đề bảng
        System.out.println("Danh sách các tài khoản");
        System.out.println("╔════════════════════╤═══════════════════════╤══════════════════════╤════════════════════╗");
        System.out.printf("║ %-18s │ %-20s │ %-20s │ %-16s ║\n", "Tài khoản", "Tên nhân viên", "Mật khẩu", "Trạng thái");
        System.out.println("╠════════════════════╪═══════════════════════╪══════════════════════╪════════════════════╣");
    
        // Duyệt qua mảng accounts và tìm tài khoản thỏa mãn ít nhất một tiêu chí
        for (AccountManager account : accounts) {
            boolean match = true;
    
            // Kiểm tra tài khoản nếu có
            if (!accountID.isEmpty() && !account.getAccount().equalsIgnoreCase(accountID)) {
                match = false;
            }
    
            // Kiểm tra tên nhân viên nếu có
            if (!name.isEmpty() && !account.getName().toLowerCase().contains(name.toLowerCase())) {
                match = false;
            }
    
            // Kiểm tra mật khẩu nếu có
            if (!password.isEmpty() && !account.getPassWord().equals(password)) {
                match = false;
            }
    
            // Kiểm tra trạng thái nếu có
            if (!status.isEmpty() && !account.getStatus().equalsIgnoreCase(status)) {
                match = false;
            }
    
            // Nếu có ít nhất một tiêu chí trùng khớp
            if (match) {
                found = true;
                // In ra thông tin tài khoản
                System.out.printf("║ %-18s │ %-20s │ %-20s │ %-16s ║\n",
                        account.getAccount(),
                        account.getName(),
                        account.getPassWord(),
                        account.getStatus());
            }
        }
    
        // Nếu không tìm thấy tài khoản nào
        if (!found) {
            System.out.println("Không tìm thấy tài khoản thỏa mãn điều kiện tìm kiếm.");
        }
    
        // Đường viền cuối bảng
        System.out.println("╚════════════════════╧═══════════════════════╧══════════════════════╧════════════════════╝");
    }
}
    
    

