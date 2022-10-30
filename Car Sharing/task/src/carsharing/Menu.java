package carsharing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final CompanyDAOImpl companyDao;
    private final Scanner scanner;
    private final CarDAOImpl carDAO;

    private List<Company> companies;

    public Menu() {
        scanner = new Scanner(System.in);
        companyDao = new CompanyDAOImpl();
        carDAO = new CarDAOImpl();
        companyDao.createTable();
        carDAO.createTable();

    }

    public void mainMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
        int choice = scanner.nextInt();
        if (choice == 1) {
            companyMenu();
        } else if (choice == 0) {
            System.exit(0);
        } else {
            mainMenu();
        }
    }

    private void companyMenu() {
        System.out.println();
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
        int choice = scanner.nextInt();
        if (choice == 1) {
            companySubMenu();
        } else if (choice == 2) {
            createCompany();
        } else if (choice == 0) {
            mainMenu();
        } else {
            companyMenu();
        }
    }

    private void companySubMenu() {

        companyList();
        int choice = scanner.nextInt();
        if (choice == 0) {
            companyMenu();
        } else {
            System.out.printf("'%s' company\n", companies.get(choice - 1).getName());
            carMenu(choice);
        }

    }

    private void companyList() {
        System.out.println();
        companies = companyDao.getAllCompanies();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            companyMenu();
        } else {
            System.out.println("Choose the company:");
            int counter = 1;
            for (Company company : companies) {
                System.out.println(counter++ + ". " + company.getName());
            }
            System.out.println("0. Back");

        }
    }

    private void createCompany() {
        System.out.println();
        System.out.println("Enter the company name:");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = scanner.nextLine();
        }
        companyDao.createCompany(name);
        System.out.println("The company was created!");
        companyMenu();
    }

    private void carMenu(int choice) {
        System.out.println();
        System.out.println("""
                1. Car list
                2. Create a car
                0. Back""");

        int choice1 = scanner.nextInt();
        if (choice1 == 1) {
            carList(choice);
        } else if (choice1 == 2) {
            createCar(choice);
        } else if (choice1 == 0) {
            companySubMenu();
        }

    }

    private void createCar(int choice) {
        int companyId = companies.get(choice - 1).getId();
        System.out.println();
        System.out.println("Enter the car name:");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = scanner.nextLine();
        }
        carDAO.createCar(name, companyId);
        System.out.println("The car was added!");
        carMenu(choice);

    }

    private void carList(int choice) {
        List<Car> cars = carDAO.getAllCars(choice);
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            carMenu(choice);
        } else {
            System.out.println("Car list:");
            int counter = 1;
            for (Car car : cars) {
                System.out.println(counter++ + ". " + car.getName());
            }
//            companyMenu();
            companySubMenu();
        }
//        carMenu(choice);
    }
}
