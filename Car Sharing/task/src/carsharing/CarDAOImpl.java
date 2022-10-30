package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    boolean tableCreated;

    public CarDAOImpl() {
    }

    @Override
    public List<Car> getAllCars(int owner) {
        List<Car> cars = new LinkedList<>();
        if (!tableCreated)  createTable();
        try {

            ResultSet result = Database
                    .runSelect("SELECT * FROM CAR WHERE COMPANY_ID = " + owner + " ORDER BY ID");

            while (result.next()) {
                cars.add(new Car(result.getInt("ID"), result.getString("NAME")));
            }
            Database.closeConnection();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.println(cars);
        return cars;

    }

    @Override
    public Car getCar(int rollNo) {
        return null;
    }

    @Override
    public void createTable() {
        try {
            Database.connectToDB();
            String sql = "CREATE TABLE IF NOT EXISTS CAR (" +
                    "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY" +
                    ", NAME VARCHAR(255) NOT NULL UNIQUE" +
                    ", COMPANY_ID INTEGER NOT NULL" +
                    ", FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                    ");";
            String alter = "ALTER TABLE company ALTER COLUMN id RESTART WITH 1";

            Database.executeUpdate(sql);
            Database.executeUpdate(alter);
            Database.closeConnection();
            tableCreated = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runInsert(String car, int companyId) throws SQLException {
        String sql2 = "INSERT INTO CAR " +
                "VALUES(default,'" + car + "'," + companyId + ")";
        Database.runInsert(sql2);

    }

    @Override
    public void createCar(String car, int companyId) {
        try {
            if (tableCreated) {
                runInsert(car, companyId);
            } else {
                createTable();
                runInsert(car, companyId);
                tableCreated = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
