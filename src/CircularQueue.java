public class CircularQueue {
    private int  size = 2;
    private int front = -1;
    private int rear = -1;
    String[][] waitingList;
    public CircularQueue(int size){
        if (size>2) this.size = size;
        System.out.println("|       waiting list created with size of "+size+"        |");
        this.waitingList = new String[size][6];
    }

    public void enqueue(String[] cabin){
        /*
        * This method is used to change the value of rear and front when the passenger is adding to the waiting lis.
        If waitingList is fulled it also will be show up
        15/04/2022 */
        if (isFull()) {
            System.out.println("|-----        Waiting List is full          -----|");
            return;
        }
        else if (isEmpty()) rear=front=0;
        else {
            if (rear==size-1) rear=0;
            else rear++;
        }
        waitingList[rear]=cabin;
        Main.print("|           Front = "+front+"           Rear = "+rear+"           |");
    }

    public String[] dequeue() {
        /*
        * This method is used to change the value of rear and front variables and delete the details of passengers from the
        waiting list when their detail is added to the list
        15/04/2022
        */

        String[] x = new String[0];
        if (isEmpty()) {
            Main.print("|           Front = "+front+"           Rear = "+rear+"           |");
            return x;
        }
        else if (front==rear){
            x = waitingList[front];
            waitingList[front]=null;
            front = rear = -1;
        }
        else {
            x = waitingList[front];
            waitingList[front]=null;
            if (front==size-1) front = 0;
            else front++;
        }
        Main.print("|           Front = "+front+"           Rear = "+rear+"           |");
        return x;
    }



    public boolean isFull(){
        return size-1==Math.abs(rear-front);
    }

    public boolean isEmpty() {
        return (front==-1 && rear==-1);
    }
}