package se2203b.ipayroll;
import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.List;

// Class written for assignment requirements - not yet used anywhere
public class PayrollReport
{
    // Fields
    private DoubleProperty totalDeductions;
    private DoubleProperty basePay;
    private DoubleProperty netPay;
    private StringProperty[] period; // [startDate, endDate]
    private EmployeeProfile employee;
    private List<Deduction> deductions;

    // Constructor
    public PayrollReport(double basePay, LocalDate[] period, EmployeeProfile employee)
    {
        double totalDeductions = 0;
        for (Deduction deduction : deductions)
        {
            totalDeductions += deduction.getTotal();
        }

        this.totalDeductions = new SimpleDoubleProperty(totalDeductions);
        this.basePay = new SimpleDoubleProperty(basePay);
        this.netPay = new SimpleDoubleProperty(basePay - totalDeductions);

        if (period == null || period.length != 2 || period[0] == null || period[1] == null) {
            throw new IllegalArgumentException("Period array must contain exactly two elements: start date and end date.");
        }
        else if (period[0].compareTo(period[1]) > 0) {
            throw new IllegalArgumentException("Start date must come before end date");
        }
        else {
            this.period = new SimpleStringProperty[2];
            this.period[0] = new SimpleStringProperty(this.period[0].toString());
            this.period[1] = new SimpleStringProperty(this.period[1].toString());
        }

        this.employee = employee;
    }

    // Accessors
    public double getTotalDeductions(){
        return totalDeductions.get();
    }
    public double getBasePay(){
        return basePay.get();
    }
    public double getNetPay(){
        return netPay.get();
    }
    public LocalDate[] getPeriod(){
        LocalDate[] period = new LocalDate[2];
        period[0] = LocalDate.of(Integer.parseInt(this.period[0].toString().substring(0, 4)),
                Integer.parseInt(this.period[0].toString().substring(5, 7)), Integer.parseInt(this.period[0].toString().substring(8, 10)));
        period[1] = LocalDate.of(Integer.parseInt(this.period[1].toString().substring(0, 4)),
                Integer.parseInt(this.period[1].toString().substring(5, 7)), Integer.parseInt(this.period[1].toString().substring(8, 10)));
        return period;
    }
    public EmployeeProfile getEmployee() {
        return employee;
    }
    public List<Deduction> getDeductions(){
        return deductions;
    }

    // Mutators
    public void setBasePay(double basePay){
        this.basePay = new SimpleDoubleProperty(basePay);
    }
    public void setPeriod(LocalDate[] period){
        if (period == null || period.length != 2 || period[0] == null || period[1] == null) {
            throw new IllegalArgumentException("Period array must contain exactly two elements: start date and end date.");
        }
        else if (period[0].compareTo(period[1]) > 0) {
            throw new IllegalArgumentException("Start date must come before end date");
        }
        else {
            this.period = new SimpleStringProperty[2];
            this.period[0] = new SimpleStringProperty(this.period[0].toString());
            this.period[1] = new SimpleStringProperty(this.period[1].toString());
        }

        // If PayPackage class is written, we need that class to handle math of recalculating base pay for new time period
        // recalculate()
    }
    public void setEmployee(EmployeeProfile employee) {
        this.employee = employee;

        // If PayPackage class is written, we need that class to handle math of recalculating base pay for new employee
        // recalculate()
    }

    // Payroll Report is never instantiated with deductions. They are to be added manually.
    public void addDeduction(Deduction deduction){

        // Recalculate total deductions
        deductions.add(deduction);
        double totalDeductions = 0;
        for (Deduction d : deductions)
        {
            totalDeductions += d.getTotal();
        }
        this.totalDeductions = new SimpleDoubleProperty(totalDeductions);

        // Adjust net pay with new deductions
        this.netPay = new SimpleDoubleProperty(this.getBasePay() - this.getTotalDeductions());
    }
}
