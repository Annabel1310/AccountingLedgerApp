
public class Transaction{
    private final String Date;
    private final String Time;
    private final String  Description;
    private final String Vendor;
    private final double Amount;

    //constructor
    public Transaction(String date, String time, String description, String vendor, double amount){
        this.Date = date;
        this.Time = time;
        this.Description = description;
        this.Vendor = vendor;
        this.Amount = amount;
    }
    //getters and setters
    public String getDate(){ return Date; }
    public String getTime(){ return Time; }
    public String getDescription(){ return Description; }
    public String getVendor(){ return Vendor; }
    public double getAmount(){ return Amount; }
    //toString Method
    @Override
    public String toString() {
        return Date + '|' + Time + '|' + Description + '|' + Vendor + '|' + Amount;
    }
}