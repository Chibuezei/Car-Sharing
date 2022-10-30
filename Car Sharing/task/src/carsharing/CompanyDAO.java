package carsharing;

import java.util.List;

interface CompanyDAO {
    public List<Company> getAllCompanies();
    public Company getCompany(int rollNo);
    public void createCompany(String name);
    public void createTable();
}

interface CarDAO {
    public List<Car> getAllCars(int owner);
    public Car getCar(int rollNo);
    public void createTable();
    void createCar(String car, int companyId);
}
