import java.io.*;
import java.util.Scanner;

public class Category {
    public String categoryID;
    public String categoryName;
    public static Category[] categoryList=new Category[10];
    public static int cnt =0;

    public Category() {
    }

    public Category(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category[] getCategoryList() {
        return categoryList;
    }

    public static void setCategoryList(Category[] categoryList) {
        Category.categoryList = categoryList;
    }
    public static Category getCategoryById(String id){
        int tmp = 0;
        int size = categoryList.length;
        for (int i = 0; i < size; i++) {
            if (categoryList[i] != null && categoryList[i].getCategoryID().equals(id)) {
                tmp = i;
                break;
            }
        }
        return categoryList[tmp];
    }
    public static void readCategoryFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (cnt == categoryList.length) {
                    // Mở rộng mảng khi đạt giới hạn
                    Category[] newCategoryList= new Category[categoryList.length*2];
                    System.arraycopy(categoryList, 0, newCategoryList, 0, categoryList.length);
                    categoryList=newCategoryList;
                }
                String[] parts = line.split(",");
                if (parts.length == 2 ) { // Kiểm tra nếu mảng chưa đầy
                    Category a = new Category(
                            parts[0],
                            parts[1]
                    );
                    categoryList[cnt] = a;
                    cnt++; // Di chuyển đến vị trí tiếp theo trong mảng
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
    public static void writeCategoryFromFile(String filePath){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < cnt; i++) {
                Category c = categoryList[i];
                if (c != null) {
                    // Ghi thông tin sản phẩm vào file, cách nhau bởi dấu phẩy
                    writer.write(c.getCategoryID()+','+c.getCategoryName());
                    writer.newLine(); // Xuống dòng cho sản phẩm tiếp theo
                }
            }
            System.out.println("Đã ghi các loại sản phẩm vào file thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    //Them
    public static void addCategory(){
        if (cnt == categoryList.length) {
            // Mở rộng mảng khi đạt giới hạn
            Category[] newCategoryList= new Category[categoryList.length*2];
            System.arraycopy(categoryList, 0, newCategoryList, 0, categoryList.length);
            categoryList=newCategoryList;
        }
        Category a = new Category();
        Scanner sc = new Scanner(System.in);
        int check =0;
        do{
            System.out.println("Nhap category id: ");
            String tmp= sc.nextLine();
            if(!checkIDCategory(tmp) || !checkDuplicateID(tmp)){
                System.out.println("Ban da nhap sai id. Vui long nhap lai");
            }
            else {
                a.setCategoryID(tmp);
                check=1;
            }
        } while(check ==0);
        System.out.println("Nhap vao ten loai san pham: ");
        a.setCategoryName(sc.nextLine());
        categoryList[cnt++]=a;
        System.out.println("Them loai san pham thanh cong.");
    }
    //Xoa
    public static void deleteCategory( String categoryID) {
        int indexToDelete = -1;

        // Tìm chỉ mục sản phẩm cần xóa
        for (int i = 0; i < cnt; i++) {
            if (categoryList[i] != null && categoryList[i].getCategoryID().equals(categoryID)) {
                indexToDelete = i;
                break;
            }
        }

        //Xóa sản phẩm và dịch chuyển các phần tử còn lại
        if (indexToDelete != -1) {
            for (int i = indexToDelete; i < cnt - 1; i++) {
                categoryList[i] = categoryList[i + 1];
            }
            categoryList[--cnt] = null; // Đặt phần tử cuối cùng thành null
            System.out.println("Đã xóa sản phẩm thành công.");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID: " + categoryID);
        }
    }
    //Check id
    //Kiem tra format id
    public static boolean checkIDCategory(String id){
        if(id.length()!=5){
            return false;
        }
        if(!id.startsWith("CT")){
            return false;
        }
        for(int i=2;i<5;i++){
            if(!Character.isDigit(id.charAt(i))){
                return false;
            }
        }
        return true;
    }
    //Kiem tra id co bi trung khong
    public static boolean checkDuplicateID(String id) {
        for (Category ct : categoryList) {
            if (ct!= null && ct.getCategoryID().equals(id)) {
                return false; // Trùng ID
            }
        }
        return true; // Không trùng
    }
    // Sua category
    public static void updateCategory(String id) {
        int choice;
        int index = -1;

        // Tìm vị trí của danh mục cần cập nhật
        for (int i = 0; i < cnt; i++) {
            if (categoryList[i] != null && categoryList[i].getCategoryID().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            Scanner sc = new Scanner(System.in);
            do {
                System.out.println("------ CHINH SUA LOAI SAN PHAM ------");
                System.out.println("0. Thoát.");
                System.out.println("1. Chinh sua id loai san pham.");
                System.out.println("2. Chinh sua ten loai san pham.");
                System.out.print("Lua chon cua ban: ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Lua chon phai la mot so nguyen hop le.");
                    return;
                }
                switch (choice) {
                    case 0:
                        System.out.println("Thoát chỉnh sửa.");
                        break;
                    case 1:
                        System.out.println("Nhap vao id moi(co dang CT___). ");
                        String newId = sc.nextLine();
                        if (!checkIDCategory(newId) || !checkDuplicateID(newId)) {
                            System.out.println("ID nhap vao khong dung dinh dang hoac bi trung !!!");
                        } else {
                            categoryList[index].setCategoryID(newId);
                            System.out.println("Da chinh sua ID thanh cong.");
                        }
                        break;
                    case 2:
                        System.out.println("Nhap vao ten moi. ");
                        String newName = sc.nextLine();
                        categoryList[index].setCategoryName(newName);
                        System.out.println("Da chinh sua ten thanh cong.");
                        break;
                    default:
                        System.out.println("Lua chon sai. Vui long chon lai.");
                }
            } while (choice != 0);

        } else {
            System.out.println("Khong tim thay loai san pham voi ID: " + id);
        }
    }
}
