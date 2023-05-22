import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//        This array is for store the cabins details and all cabins has own 3 passenger objects.
        Cabin[] shipCabins = new Cabin[12];
        CircularQueue circularQueue;
        Scanner scanner = new Scanner(System.in);
//        Creating the 12 cabin objects using loop and by creating one cabin automatically will create 3 passenger objects
        for (int i=0; i<12; i++) shipCabins[i] = new Cabin(i);
          /*Asking for would you like to continue the program with the previous file record?
          * If he said yes the previous record will be assigned to the cabin objects and passenger objects
          * IF he said No all cabin objects will create with empty cabin details
        15/03/2022
          */
        System.out.print("""
                    
                    
                    
                    
                    
                    |--------------------------------------------------|
                    |          WELCOME TO CRUISE SHIP BOARDING         |
                    |--------------------------------------------------|
                    | would you like to continue the program with the  |
                    | previous file record? [Y/N]                      |
                    |--------------------------------------------------|
                    | ANS :                                            |""");
        if (scanner.next().equalsIgnoreCase("N")) {
            initialise(shipCabins, "N");
        }else {
            initialise(shipCabins, "file.txt");
        }
        do {
          /*This will create CircularQueue object and also will be created the waiting list array of type of
            cabin using the size we entered
        15/03/2022
            */
            System.out.print("""
                    
                    
                    
                    
                    
                    |--------------------------------------------------|
                    |          WELCOME TO CRUISE SHIP BOARDING         |
                    |--------------------------------------------------|
                    | When the cabin is full, enter the size of the    |
                    | waiting list to include the information about the|
                    | cabin you want. (SIZE min = 2)                   |
                    |--------------------------------------------------|
                    | ANS :                                            |""");
            try {
                circularQueue = new CircularQueue(Integer.parseInt(scanner.next()));
                break;
            }catch (NumberFormatException e) {
                wrongInput();
            }
        }while (true);
        int loop=0;
        while (loop==0) {
//            Give the options to choose.
            System.out.print("""
                    |--------------------------------------------------|
                    | CHOOSE OPTION AND ENTER                          |
                    | A: Add a customer to a cabin                     |
                    | V: View all cabins                               |
                    | E: Display Empty cabins                          |
                    | D: Delete customer from cabin                    |
                    | F: Find cabin from customer name                 |
                    | S: Store program data into file                  |
                    | L: Load program data from file                   |
                    | O: View passengersOrdered alphabetically by name |
                    | T: Expenses/total Expenses                       |
                    | Q: To STOP the program                           |
                    
                    |--------------------------------------------------|
                    | ANS :                                            |""");
            String answer = scanner.next();
            switch (answer.toUpperCase()) {
                case ("A") -> addCustomer(shipCabins, circularQueue, scanner);
                case ("V") -> viewAllCabins(shipCabins);
                case ("E") -> emptyCabins(shipCabins);
                case ("D") -> deleteCustomer(shipCabins, circularQueue, scanner);
                case ("F") -> findCabinByName(shipCabins, scanner);
                case ("S") -> intoFile(shipCabins, scanner);
                case ("L") -> loadFile(shipCabins, scanner);
                case ("O") -> passengersOrder(shipCabins, scanner);
                case ("T") -> totalExpenses(shipCabins, scanner);
                case ("Q") -> {stop();loop++;}

                default -> print("|         INPUT IS NOT CORRECT. TRY AGAIN          |");
            }
        }
    }




    //--------------------------------------------------------emptyCabins----------------------------------------------|
    public static void emptyCabins(Cabin[] shipCabin){
        /*
         * Go through the loop and check the cabin status. If cabin status is 0 it is empty, and it will print
         * 28/03/2022
         * */
        int a =0;
        print("|               Display Empty cabins               |");
        for (Cabin cabin:shipCabin) {
            if (cabin.status() == 0) {
                System.out.println("|               Cabin " + cabin.getCabinNum() + " is empty                  |");
                a++;}
        }
//        If all cabins had fulled it will check using variable, and it is it will print
        if (a==0) print("|               All Cabins  are full               |");
    }





    //-------------------------------------------------------viewAllCabins---------------------------------------------|
    public static void viewAllCabins(Cabin[] shipCabin){
        /*
         * show the details of room. All the Passengers' names and Expenses
         * If cabinStatus == 0 it will show cabin is empty
         * If entered number is out of bond it will print and error msg by wrongInput method
         * 01/04/2022
         * */
        print("|                  View all cabins                 |");
        int q=0;
        do {
            for (Cabin cabin:shipCabin){
                print("|                      "+cabin.getMainName()+"                     |");
                if (cabin.status() == 1) {
                    for (Passenger passenger: cabin.getPassengers()){
                        if (passenger.getFirstName().equals(" - ")) continue;
                        System.out.println   ("|    Passenger "+passenger.getPassengerNum()+"     : "+passenger.getFirstName()+" "+passenger.getSurName()+"        |");
                        System.out.println   ("|    Expenses        : "+passenger.getExpenses()+"                  |");
                    }q++;
                }else {q++;
                    print("|                  Room is empty                   |");
                }
            }

        } while (q<1);
        System.out.println("|--------------------------------------------------|");
    }




    //-------------------------------------------------------addCustomer-----------------------------------------------|
    public static void addCustomer(Cabin[] shipCabin, CircularQueue circularQueue, Scanner scanner) {
        /*
        * Ask for cabin number and check it must be between 13 and 0. There haven't any roomNum as 13 and 0
        * After that ask for cabin num they like to add the customer and check the cabin status
        * If cabin states == 0 passengers added to the cabin and if isn't passengers will add to the waiting list
        * Use the Initialize method and if there is any cabinStatus == 0 cabin customers adding to that from waiting list
        as circular queue.
        * 20/03/2022
        * */
        int roomNum = 0;
        print("|             Add customers to a cabin             |");
        do {
            try {
                System.out.println("""
                                    |  instructions :                                  |
                                    | * You can only get 3 places in cabin.            |
                                    | * When you finished adding enter "N" for break   |
                                    | program from adding another customer             |""");
                System.out.println("|--------------------------------------------------|");
                System.out.print( "|          Do you want add passengers (Y/N)       :|");
                if (scanner.next().equalsIgnoreCase("N")) break;
                emptyCabins(shipCabin);
                System.out.println("|--------------------------------------------------|");
                System.out.print("|           Enter the Cabin number                :|");
                roomNum = Integer.parseInt(scanner.next());
                if (roomNum<13 && 0<roomNum){
                    System.out.println("|--------------------------------------------------|");
                    break;
                }
                print("|           We have only 12 cabins                 |");
            } catch (NumberFormatException e) {
                wrongInput();
            }
        }
        while (true);
        for (Cabin cabin :shipCabin){
            if (cabin.status() ==0){
                if (cabin.getCabinNum()==roomNum){
                    String[] tempCabin = addCustomerInput(scanner);
                    for (Passenger passenger : cabin.getPassengers()) {
                        System.out.println();
                        passenger.setFirstName(tempCabin[passenger.getPassengerNum()-1].split(":")[0]);
                        passenger.setSurName(tempCabin[passenger.getPassengerNum()-1].split(":")[1]);
                        passenger.setExpenses(Double.parseDouble(tempCabin[passenger.getPassengerNum()+2]));
                        cabin.setStatus(1);
                        System.out.println("|               Customers added                   :| " + cabin.getCabinNum());
                    }
                }
            }else {
                if (cabin.getCabinNum()==roomNum) {
                    if (circularQueue.isFull()) {
                        System.out.println("|-----        Waiting List is full           -----|");
                        break;
                    }
                    print("|               Adding to waiting list             |");

                    circularQueue.enqueue(addCustomerInput(scanner));
                }
            }
        }// Initialize method from Initialize class
        waitingListInitialize2(shipCabin,circularQueue);

    }




    //-----------------------------addCustomerInput - belongs to add customer method-----------------------------------|
    private static String[] addCustomerInput(Scanner scanner){
        // This method used for collect to the data from customers.
        //02/04/2022
        String[] names = new String[6];
        for (int i =0;i<3;i++){
            if (i>=1) {
                System.out.print("|  Do you want add another customer (Y/N)         :|");
                if (scanner.next().equalsIgnoreCase("N")) {
                    for (; i < 3; i++) {
                        names[i] = " - " + ":" + " - ";
                        names[i + 3] = String.valueOf(0.0);
                    }
                    break;
                }
            }
            print("|                  "+(i+1)+" Customer                     :|");
            System.out.print("|            Enter the Customer's first name      :|");
            String _1stName = scanner.next().replace(" ","");
            System.out.println("|--------------------------------------------------|");
            System.out.print("|            Enter the Customer's surname         :|");
            String _2ndName = scanner.next().replace(" ","");
            System.out.println("|--------------------------------------------------|");
            names[i] = _1stName.substring(0,1).toUpperCase()+_1stName.substring(1)+":"+
                    _2ndName.substring(0,1).toUpperCase()+_2ndName.substring(1);
            do {
                try {
                    System.out.println("|--------------------------------------------------|");
                    System.out.print("|            Enter the Customer's Expenses        :|");
                    names[i+3]= String.valueOf((Double.valueOf(scanner.next())));
                    break;
                } catch (NumberFormatException kl) {
                    print("|         INPUT IS NOT CORRECT. TRY AGAIN          |");
                }
            }while (true);
            print("|                Customer added                   :|");
            System.out.println("|--------------------------------------------------|");
        }
        return names;
    }




    //---------------------------------------------------delete Customer-----------------------------------------------|
    public static void deleteCustomer(Cabin[] _arrayCabins, CircularQueue circularQueue, Scanner scanner) {
        /*
        * Go through entire cabins by loop and checking passengers surname or firstname is equal to the user input name and
        if it's found the equal passenger pass set the mainName value to cabinName value
        * If that name customer couldn't be able to find out in cabin it checked by line 183 and print msg
        * After that if you find out the correct passenger from cabin reset the values in whole cabin,using mainName.
        * Eventually is any customers in waiting List they will be added to the deleted cabin using --------
        Initialize.waitingListInitialize2(_arrayCabins,circularQueue) method.
        * 03/04/2022
        */
        String mainName =" - ";
        int cabinNum = 0;
        print(           "|             Delete customer from cabin           |");
        System.out.println("|--------------------------------------------------|");
        System.out.print("|              Enter the Customer name            :|");
        String g = scanner.next().replace(" ","");
        String customer = g.substring(0,1).toUpperCase()+g.substring(1);
        System.out.println("|--------------------------------------------------|");
        for (Cabin cabin :_arrayCabins){
            for (Passenger passenger:cabin.getPassengers()){
                if (customer.equalsIgnoreCase(passenger.getSurName()) || customer.equalsIgnoreCase(passenger.getFirstName())) {
                    mainName = cabin.getMainName();
                    cabinNum = cabin.getCabinNum();
                    break;
                }
            }
        }
        if (mainName.equals(" - ")||cabinNum==0) {
            print("|        No customer by this name was found        |");
        }
        else {
            System.out.println("|--            Found the cabin number            --|");
            System.out.println("|--------------------------------------------------|");
            System.out.println("|--                     "+_arrayCabins[cabinNum-1].getMainName()+"                   --|");
            for (Passenger passenger : _arrayCabins[cabinNum-1].getPassengers()) {
                System.out.println("|--------------------------------------------------|");
                System.out.println("|                No "+passenger.getPassengerNum()+" customer is deleted          |");
                System.out.println("|--------------------------------------------------|");
                passenger.setDefFirstName();
                passenger.setDefSurName();
                passenger.setDefExpenses();
                _arrayCabins[cabinNum-1].setStatus(0);
            }
            waitingListInitialize2(_arrayCabins,circularQueue);
        }
    }





    //------------------------------------------------------find By Cabins---------------------------------------------|
    public static void findCabinByName(Cabin[] shipCabin, Scanner scanner) {
        /*
        * Go through entire cabins by loop and checking passengers surname or firstname is equal to the user input name and
        if it's found it will print out
        * 17/03/2022
        * */
        print("|           Find cabin from customer name          |");
        System.out.print("|         Enter the Customer first or last name   :|");
        String customer = scanner.next().replace(" ","");int i=0;
        System.out.println("|--------------------------------------------------|");
        for (Cabin cabin :shipCabin){
            for (Passenger passenger: cabin.getPassengers()){
                if (customer.equalsIgnoreCase(passenger.getFirstName())||customer.equalsIgnoreCase(passenger.getSurName())){
                    System.out.println("|                "+cabin.getMainName() + " owned by " + customer.substring(0,1).toUpperCase().concat(customer.substring(1))+"         |");
                    i++;
                }
            }
        }if (i==0) print("|        No customer by this name was found        |");
    }





    //---------------------------------------------------------into File-----------------------------------------------|
    public static void intoFile(Cabin[] shipCabin, Scanner scanner) throws IOException {
        /*
        * By this method will give you two option
        1: To store data into the previous file
        2: To store data into New File
        * if you entered 1, the previous details recorded file "file.txt" use as the storing file if isn't you can give
        path and enter your file name as you like with .txt extension.At last of print in file, it will show you what is
        the pattern of data view on your txt file
        18/03/2022 */
        String path = "file.txt";
        print("|            Store program data into file          |");
        System.out.print("""
                    |--------------------------------------------------|
                    | Choose the option what you want                  |
                    | 1: To store data into the previous file          |
                    | 2: To store data into New File                   |
                    | ANS                                             :|""");

        try {
            int ans = Integer.parseInt(scanner.next());
            if (ans==2) {
                System.out.print("""
                    ----------------------------------------------------
                    | Enter the path to .txt file that you want to store |
                    | Eg: D:\\CW\\project\\file.txt                         |
                    | PATH                                              :|""");
                path= scanner.next();
            }
            FileWriter file = new FileWriter(path);
            String a ="""
                    
                    
                    |------------------------------------------------------------------------------------|
                    |                                         PATTERN                                    |
                    |------------------------------------------------------------------------------------|
                    |                Room condition(R.C.): If 1, there is already a guest                |
                    |                         E: Expenses;         C: Customer                           |
                    |   R.C  :  [1st C]  :  [2nd C]  :  [3rd C]  :  [1st C E] :  [2st C E] :  [3st C E]  |
                    |------------------------------------------------------------------------------------|
                    """;
            System.out.println(a);
            for (Cabin cabin : shipCabin) {
                String[] passengerNames = new String[3];
                String[] passengerSurNames = new String[3];
                String[] passengerExpenses = new String[3];
                int i = 0;
                for (Passenger passenger : cabin.getPassengers()) {
                    passengerNames[i]=passenger.getFirstName();
                    passengerSurNames[i]=passenger.getSurName();
                    passengerExpenses[i] = String.valueOf(passenger.getExpenses());
                    i++;
                }
                String writeDown = cabin.getMainName()+" : "+cabin.status() + " : [" + passengerNames[0] + " : " + passengerSurNames[0] +
                        "] : [" + passengerNames[1] + " : " + passengerSurNames[1] + "] : [" + passengerNames[2] + " : " + passengerSurNames[2] + "] : " +
                        passengerExpenses[0] + " : " + passengerExpenses[1] + " : " + passengerExpenses[2] + " : " ;
                file.write(writeDown + "\n");
                System.out.println(writeDown);
            }
            file.write(a);
            file.close();
        } catch (NumberFormatException e) {
            wrongInput();
        }
    }





    //----------------------------------------------------passenger Order----------------------------------------------|
    public static void passengersOrder(Cabin[] shipCabin, Scanner scanner) {
        /*
        * When user enter the one option it will Go through the loop, and it will collect your surname of firstname to array
        as user entered input.
        * And going through the loop and using selectionAlgorithm() method it will make as an order the list using selection
        Algorithm
        29/03/2022
        */
        int option;
        String[] firstNames = new String[36];
        String[] sureName = new String[36];
        print("| View passengers Ordered alphabetically by name   |");
        do {
            try {
                System.out.print("""
            |   1:Ordered by FIRST NAME                        |
            |   2:Ordered by SUR NAME                          |
            |   ANS                                           :|""");
                option= Integer.parseInt(scanner.next());
                if (!(option==1||option==2)) {
                    wrongInput();
                    continue;
                }
                break;
            }catch (NumberFormatException e) {wrongInput();}
        }while (true);
        int i =0;
        for (Cabin cabin:shipCabin){
            for (Passenger passenger:cabin.getPassengers()) {
                if (!Objects.equals(passenger.getSurName(), " - ") && !Objects.equals(passenger.getFirstName(), " - ")) {
                    if (option == 1) {
                        firstNames[i] = passenger.getFirstName();
                    } else {
                        sureName[i] = passenger.getSurName();
                    }
                    i++;
                }
            }
        }
        System.out.println("|--------------------------------------------------|");

        if (option==1) {
            selectionAlgorithm(firstNames,i);
        }
        else {
            selectionAlgorithm(sureName,i);
        }
    }





    //--------------------------- selection Algorithm - belongs to passenger order ------------------------------------|
    private static void selectionAlgorithm(String[] stringList,int intl) {
        /*
        * If stringList[j] string value is valuable more than stringList[i] it will swap and make and order by going
        through the loop
        03/04/2022
        * */
        for (int i=0;i<intl;i++){
            for (int j=0;j<intl;j++) {
                if (stringList[j].compareTo(stringList[i]) > 0) {
                    String highNum = stringList[i];
                    String smallNum = stringList[j];
                    stringList[i] = smallNum;
                    stringList[j] = highNum;
                }
            }
        }
        for (int j=1;j<intl+1;j++){
            if (stringList[j-1].replace(" ","").equalsIgnoreCase("-")) continue;
            System.out.println("  "+stringList[j-1]+"  ");
        }
    }





    //---------------------------------------------------total Expenses------------------------------------------------|
    public static void totalExpenses(Cabin[] shipCabins,Scanner scanner) {
        /*
        * Ask for user input as given below and if user input is 1 it will ask for the cabin num
        * Then going through the loop, and it will find out sum of the expenses of passengers of inputted cabin
        * If user input is 2 it will be going through cabins and will print sum of all cabin expenses
        04/04/2022
         */
        System.out.print("""
                    ----------------------------------------------------
                    | Choose the option for seeking the expenses       |
                    | 1   : To see the passengers in the selected cabin|
                    | ANY : To see all the passengers                 :|""");
        try {
            if (Integer.parseInt(scanner.next()) == 1) {
                System.out.println("|--------------------------------------------------|");
                System.out.print("| Enter the cabin Number                          :|");
                int roomNum = Integer.parseInt(scanner.next());
                System.out.println("|--------------------------------------------------|");
                for (Cabin cabin : shipCabins) {
                    if (roomNum == cabin.getCabinNum()) {
                        double totalExpenses = 0;
                        System.out.println("|                       "+cabin.getMainName()+"                    |");
                        for (Passenger passenger : cabin.getPassengers()) {
                            print("|           Passenger " + passenger.getPassengerNum() + " expenses                  :| "+
                                    passenger.getExpenses());
                            totalExpenses = totalExpenses+passenger.getExpenses();
                        }System.out.println("|               Total expenses                     | "+totalExpenses);} }
            }else {
                allOptionFind(shipCabins);
            }
        }catch (NumberFormatException ignored){
            allOptionFind(shipCabins);
        }
    }





    //------------------------------- all Option Find - belongs to total expenses -------------------------------------|
    private static void allOptionFind(Cabin[] shipCabins) {
        //This is for find out sum of expenses of all passengers' in ship
        //28/03/2022
        double totalExpenses = 0;
        for (Cabin cabin : shipCabins) {
            print("|                       "+cabin.getMainName()+"                    |");
            for (Passenger passenger : cabin.getPassengers()) {
                System.out.print("|           Passenger " + passenger.getPassengerNum() + " expenses                  :| "+
                        passenger.getExpenses());
                System.out.println();
                totalExpenses = totalExpenses+passenger.getExpenses();
            }
        }print("|               Total expenses                     | "+totalExpenses);
    }





    //--------------------------------------------Load data from file option-------------------------------------------|
    public static void loadFile(Cabin[] shipCabins, Scanner scanner) {
        /*
        * You have two options as below. If your input is one it will load data from previous file that you saved data."file.txt"
        * if your option is 2 it will give you chance to load data from file, but it must have an order as printed msg when last
        printed msg on txt file when we store the data. If it is the program will load the data from that file. You just
        only have to give the path to txt file and put the .txt extension
        02/04/2022
        * */
        int q = 0;
        do {
            try {
                System.out.print("""
                                    |--------------------------------------------------|
                                    | Choose the option what you want                  |
                                    | 1: To load the previous stored data              |
                                    | 2: To load the New File data                     |
                                    | ANY number: To go back                           |
                                    |--------------------------------------------------|
                                    | ANS                                             :|""");
                int ans1 = Integer.parseInt(scanner.next());
                if (ans1 == 2) {
                    System.out.println("|--------------------------------------------------|");
                    System.out.print("""
                                        ----------------------------------------------------
                                        | Enter the path to .txt file                      |
                                        | Eg: D:\\CW\\project\\file.txt                       |
                                        | PATH                                            :|""");
                    String path = scanner.next();
                    initialise(shipCabins, path);
                    System.out.println("|--------------------------------------------------|");
                    q++;
                } else if (ans1 == 1) {
                    initialise(shipCabins, "file.txt");
                    System.out.println("|--------------------------------------------------|");
                } else q++;
            } catch (NumberFormatException | IOException | InputMismatchException e) {
                wrongInput();q++;
            }
        } while (q == 0);
    }



    //-----------------Initialise for starting and when doing the load file method -------------------------------------
    public static void initialise(Cabin[] shipRef, String path) throws IOException {
        /*Asking for would you like to continue the program with the previous file record? in main class
         * If he said yes the previous record will be assigned to the cabin objects and passenger objects
         * IF he said No all cabin objects will create with empty cabin details

        15/03/2022*/
        if (path.equalsIgnoreCase("N")){
            for (Cabin cabin:shipRef) {
                cabin.setStatus(0);
                System.out.println( "Initialise ".concat(cabin.getMainName()));}
        }
        else {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            for (Cabin cabin : shipRef){
                String[] r1 = scanner.nextLine().replace(" ","").split(":");
                cabin.setStatus(Integer.parseInt(r1[1].replace(" ","")));
                int i=2,j=3;
                int k = 8;
                for (Passenger passenger: cabin.getPassengers()){
                    if (cabin.status() ==0) {
                        passenger.setDefFirstName();
                        passenger.setDefSurName();
                        passenger.setDefExpenses();
                        continue;
                    }
                    String first = r1[i].replace("["," ").replace(" ","");
                    String second = r1[j].replace("]","").replace(" ","");
                    passenger.setFirstName(first.substring(0,1).toUpperCase()+first.substring(1));
                    passenger.setSurName(second.substring(0,1).toUpperCase()+second.substring(1));
                    passenger.setExpenses(Double.parseDouble(r1[k].replace(" ","")));
                    i+=2;j+=2;k++;
                }
                System.out.println("|-----------   Initialise: "+ cabin.getMainName()+"   -----------|");
            }
        }
    }



    //-----------------Adding passengers to cabins from waiting list to cabins------------------------------------------
    public static void waitingListInitialize2(Cabin[] shipRef, CircularQueue circularQueue) {
        /*
        * when we have tried to add the passengers to cabin if cabin is fulled, we added the details of passengers to waiting
        list, when we called this method last to the addCustomer and deleteCustomer methods it will enter the passengers' data
        of in waiting list to the cabin.
        * Using circular queue this will be adding passengers to the cabins when he asked cabin is fulled

        27/03/2022
        * */
        if (!circularQueue.isEmpty()) {
            for (Cabin cabin : shipRef) {
                if (cabin.status() == 0) {
                    if (!circularQueue.isEmpty()) {
                        System.out.println("|    Customers added from the waiting list to     :| " + cabin.getCabinNum());
                        String[] tempCabin =  circularQueue.dequeue();
                        for (Passenger passenger : cabin.getPassengers()) {
                            passenger.setFirstName(tempCabin[passenger.getPassengerNum()-1].split(":")[0]);
                            passenger.setSurName(tempCabin[passenger.getPassengerNum()-1].split(":")[1]);
                            passenger.setExpenses(Double.parseDouble(tempCabin[passenger.getPassengerNum()+2]));
                            cabin.setStatus(1);
                        }
                    }
                }
            }
        }
    }




    //---------------------------------------------------print Options-------------------------------------------------|
    public static void stop() { print("|                 STOP the program                 |"); }
    public static void wrongInput(){ print("|               Wrong Input type                  :|"); }
    public static void print(String expression) {
        System.out.println("|--------------------------------------------------|");
        System.out.println(expression);
        System.out.println("|--------------------------------------------------|");
    }
}
