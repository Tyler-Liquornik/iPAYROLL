package se2203b.ipayroll;

import javafx.beans.property.*;
import java.time.LocalDate;

// Class written for assignment requirements - not yet used anywhere
public class Deduction {

    // Fields
    private IntegerProperty deductionCode;
    private DoubleProperty amount;
    private DoubleProperty rate;
    private DoubleProperty total;
    private StringProperty[] period; // [startDate, endDate]
    private DoubleProperty maxValue;
    private PayrollReport payrollReport;

    // Constructor
    public Deduction(int deductionCode, double amount, double rate, LocalDate[] period, double maxValue, PayrollReport payrollReport){
        this.deductionCode = new SimpleIntegerProperty(deductionCode);
        this.rate = new SimpleDoubleProperty(rate);
        this.total = new SimpleDoubleProperty(Math.min(maxValue, rate * payrollReport.getBasePay()) + amount); // see recalculateTotal() comment
        this.amount = new SimpleDoubleProperty(amount);

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

        this.maxValue = new SimpleDoubleProperty(maxValue);
        this.payrollReport = payrollReport;
    }

    // Accessors
    public int getDeductionCode(){
        return deductionCode.get();
    }
    public double getAmount(){
        return amount.get();
    }
    public double getRate(){
        return rate.get();
    }
    public double getTotal(){
        return total.get();
    }
    public LocalDate[] getPeriod(){
        LocalDate[] period = new LocalDate[2];
        period[0] = LocalDate.of(Integer.parseInt(this.period[0].toString().substring(0, 4)),
                Integer.parseInt(this.period[0].toString().substring(5, 7)), Integer.parseInt(this.period[0].toString().substring(8, 10)));
        period[1] = LocalDate.of(Integer.parseInt(this.period[1].toString().substring(0, 4)),
                Integer.parseInt(this.period[1].toString().substring(5, 7)), Integer.parseInt(this.period[1].toString().substring(8, 10)));
        return period;
    }
    public double getMaxValue(){
        return maxValue.get();
    }
    public PayrollReport getPayrollReport() {
        return payrollReport;
    }

    // Mutators
    public void setDeductionCode(int deductionCode){
        this.deductionCode = new SimpleIntegerProperty(deductionCode);
    }
    public void setAmount(double amount){
        this.amount = new SimpleDoubleProperty(amount);
        recalculateTotal();
    }
    public void setRate(double rate){
        this.rate = new SimpleDoubleProperty(rate);
        recalculateTotal();
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

        // If PayPackage class is written, we need that class to handle math of recalculating total on the new period
        // improvedRecalculateTotal()
    }
    public void setMaxValue(double maxValue){
        this.maxValue = new SimpleDoubleProperty(maxValue);
        recalculateTotal();
    }

    // Recalculate the total deduction value
    // Improved version would apply the deduction rate over the period specified instead of payrollReport.getBasePay()
    // This current model assumes the deduction period is the same as the payroll reporting period
    // Would need to implement PayPackage class (see class diagram) to calculate specific pay over the deduction period
    // However PayPackage has not been written in this implementation of iPAYROLL yet
    public void recalculateTotal(){
        this.total = new SimpleDoubleProperty(Math.min(this.getMaxValue(), this.getRate() * payrollReport.getBasePay()) + this.getAmount());
    }
}
