public class Passenger {
    private String firstName;
    private final int passengerNum;
    private String surName;
    private double expenses;

    public Passenger(int cabinNameNum, int passengerNum){
        /*
        * This constructor will give and make own values of passenger when the creating the objects
        15/03/2022
        * */
        this.passengerNum = passengerNum+1;
        System.out.println("|   made a passenger ID ("+ this.passengerNum +") for Room number   |"+ cabinNameNum +"   |");
        System.out.println("|--------------------------------------------------|");
    }
    public int getPassengerNum() {return passengerNum;}
    public void setFirstName(String firstName1) {firstName=firstName1;}
    public String getFirstName() {return firstName;}
    public void setDefFirstName() {firstName=" - ";}
    public void setSurName(String surName1){surName=surName1;}
    public String getSurName() {return surName;}
    public void setDefSurName() {surName=" - ";}
    public void setExpenses(double Expenses1){expenses =Expenses1;}
    public double getExpenses(){return expenses;}
    public void setDefExpenses(){expenses=0.0;}

}
