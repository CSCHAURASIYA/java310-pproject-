import java.util.HashMap;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class pms {

    public static void main(String[] args) {
        String correctID = "dipesh";
        String correctPassword = "pass123";

        Scanner input = new Scanner(System.in);

        System.out.print("Enter your ID: ");
        String userID = input.nextLine();

        System.out.print("Enter your password: ");
        String userPassword = input.nextLine();

        if (userID.equals(correctID) && userPassword.equals(correctPassword)) {
            System.out.println("Successfully logged in to our payroll management system.");

            PayrollManagementSystem payrollManagementSystem = new PayrollManagementSystem();
            payrollManagementSystem.run();
        } else {
            System.out.println("Incorrect ID or password. Please try again.");
        }
    }
}

class PayrollManagementSystem1 {
    private HashMap<String, Double> employeeSalaries;

    public PayrollManagementSystem1() {
        employeeSalaries = new HashMap<>();
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            System.out.println("Please enter the employee's name and salary (or type 'done' to finish): ");
            String line = input.nextLine();

            if (line.equals("done")) {
                done = true;
            } else {
                String[] parts = line.split(" ");
                if (parts.length != 2) {
                    System.out.println("Invalid input. Please enter the employee's name and salary separated by a space.");
                } else {
                    try {
                        String name = parts[0];
                        double salary = Double.parseDouble(parts[1]);
                        employeeSalaries.put(name, salary);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid salary. Please enter a number.");
                    }
                }
            }
        }

        // Write data to file
        try {
            FileWriter writer = new FileWriter("employee_data.txt");
            for (String name : employeeSalaries.keySet()) {
                double salary = employeeSalaries.get(name);
                writer.write(name + " " + salary + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
class PayrollManagementSystem {

    static HashMap<String, Double> map = new HashMap<>();

    public void run() {
        try {
            Scanner in = new Scanner(System.in);

            System.out.print("Enter Employee details Name: ");
            String name = in.nextLine();
            double salary = 0;

            System.out.println("\nEmployee Types:\n  F - Full Time\n  P - Part Time");
            System.out.print("Enter Employee Type: ");
            String type = in.nextLine();

            if (type.equalsIgnoreCase("F")) {
                FullTimeEmployee employee = new FullTimeEmployee(name);

                System.out.print("\nEnter Monthly Salary: ");
                double sal = in.nextDouble();
                salary = sal;
                employee.setMonthlySalary(sal);

                System.out.println(employee);
            } else if (type.equalsIgnoreCase("P")) {
                PartTimeEmployee employee = new PartTimeEmployee(name);

                System.out.print("\nEnter Rate Per Hour: ");
                double rate = in.nextDouble();

                System.out.print("\nEnter Number of Hours Worked: ");
                employee.setWage(rate, in.nextInt());
                salary = employee.getWage();

                System.out.println(employee);
            }

            double tax = findTax(salary);
            map.put(name, tax);

            System.out.println(name + " tax: " + tax);
        } catch (Exception ex) {
            System.out.println("Unknown exception occurred");
        } finally {
            System.out.println("Thanks for using our payroll management system");
        }
    }

    private double findTax(double salary) {
        double k = (salary * 0.1);
        return k;
    }

    private double findTax(double salary, int percent) {
        double k = (salary * (percent / 100.0));
        return k;
    }
}

abstract class Employee {
    private String name;

    public Employee(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

final class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(String name) {
        super(name);
    }

    public void setMonthlySalary(double salary) {
        monthlySalary = salary;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public String toString() {
        return "\nEmployee Name: " + getName() + "\nMonthly Salary: " + monthlySalary;
    }
}

final class PartTimeEmployee extends Employee {
    private double ratePerHour;
    private int hoursWorked;
    private double wage;

    public PartTimeEmployee(String name) {
        super(name);
    }

    public void setWage(double rate, int hours) {
        ratePerHour = rate;
        hoursWorked = hours;
        wage = ratePerHour * hoursWorked;
    }

    public double getWage() {
        return wage;
    }

    public String toString() {
        return "\nEmployee Name: " + getName() + "\nWage: " + wage;
    }
}