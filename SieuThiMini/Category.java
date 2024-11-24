import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Category {
    public String categoryID;
    public String categoryName;
    public static Category[] categoryList=new Category[100];
    private static int cnt =0;

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
    public void readProductsFromFile(String filePath) {
         
        try (BufferedReader br = new BufferedReader(new FileReader("Category.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && cnt< 100) { // Kiểm tra nếu mảng chưa đầy
                    Category a = new Category(
                            parts[0],                // productID
                            parts[1]               // supplier
                    );
                    categoryList[cnt] = a;
                    cnt++; // Di chuyển đến vị trí tiếp theo trong mảng
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}
