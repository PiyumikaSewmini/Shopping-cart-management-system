public class Customer {
  private String user_name;
  private String Password;

    public Customer(String user_name,String Password) {
        this.user_name = user_name;
        this.Password=Password;
    }
    public String getUser_name() {

        return user_name;
    }

    public String getPassword() {

        return Password;
    }
    public void setUser_name(String user_name) {

        this.user_name = user_name;
    }
    public void setPassword(String password) {

        Password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "user_name='" + user_name + '\'' +
                ", Password=" + Password +
                '}';
    }
}
