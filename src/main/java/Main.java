public class Main {

    public static void main(String[] args) {
        Loader loader = new Loader("src/main/resources/employeeList.csv");
        loader.print();
    }
}
