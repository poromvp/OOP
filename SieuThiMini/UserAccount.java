

public class UserAccount {
    public String username, password, email, phoneNumber, role; //role: vai trò(admin, customer, staff)

    public UserAccount(){}
    public UserAccount(String username, String password, String email, String phoneNumber, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role; 
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean login(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
        // So sánh trực tiếp
    }
    public void logout(){
        //
    }
    public void resetPassword(String newPassword){
        this.password = newPassword; // gán mật khẩu mới vào mật khẩu cũ
    }
}
