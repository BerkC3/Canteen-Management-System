public class Manager extends Person {
    private String username;
    private String password;
    private CanteenInventory canteen;

    public Manager(String name, int age, Gender gender, String username, String password, CanteenInventory canteen) {
        super(name, age, gender);
        this.username = username;
        this.password = password;
        this.canteen = canteen;
        canteen.addManager(username, password);
    }
}

