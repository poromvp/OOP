

public interface QLFile {
    public void writeToFile(String filePath); // Ghi dữ liệu ra file 
    public Object readFromFile(String filePath); // Đọc dữ liệu từ file
}