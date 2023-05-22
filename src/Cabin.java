public class Cabin {
    private final String mainName;
    private final int cabinNum;
    private int guestsInCabin;
    private final Passenger[] passengers = new Passenger[3];

    public Cabin(int l) {
        /*
         * This constructor will give and make own values of cabin when the creating the objects
        15/03/2022
        */
        cabinNum = l+1;
        mainName = "Cabin "+(l+1);
        for (int i=0;i<3;i++) passengers[i]=new Passenger(cabinNum,i);
        System.out.println("|             made a Cabin ("+mainName+")               |");
    }
    public int getCabinNum() {return cabinNum;}
    public String getMainName() {return mainName;}
    public int status() {return guestsInCabin;}
    public void setStatus(int aStatus){ guestsInCabin = aStatus; }
    public Passenger[] getPassengers(){return passengers;}

}
