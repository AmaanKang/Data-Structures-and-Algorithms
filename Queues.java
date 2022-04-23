import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * This class works with a txt file and displays some information based on the data read from file.
 */
public class Queues {
    /**
     * Main method
     * @param args unused argument
     */
    public static void main(String[] args) {
        String fileName = "CustomerData.txt";
        int expressLines = 0;
        int normalLines = 0;
        int itemsLimit = 0;
        int numOfCustomer = 0;
        int maxCustomerTime = 0;
        ArrayList<Customer> arrayOnFileRead = new ArrayList<>();
        ArrayList<Integer> sumOfExpQueues = new ArrayList<>();
        ArrayList<Integer> sumOfNormQueues = new ArrayList<>();
        ArrayList<LinkedQueue<Customer>> expressQueues = new ArrayList<>();
        ArrayList<LinkedQueue<Customer>> normalQueues = new ArrayList<>();

        try{
            Scanner fileRead = new Scanner(new File(fileName));
            expressLines = Integer.parseInt(fileRead.next());
            normalLines = Integer.parseInt(fileRead.next());
            itemsLimit = Integer.parseInt(fileRead.next());
            numOfCustomer = Integer.parseInt(fileRead.next());
            while(fileRead.hasNext()){
                Customer customer = new Customer(Integer.parseInt(fileRead.next()));
                arrayOnFileRead.add(customer);
                if(customer.getTimeTaken() > maxCustomerTime){
                    maxCustomerTime = customer.getTimeTaken();
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        /**
         * Add the required express queues to expressQueues
         */
        for(int i = 0; i < expressLines; i++){
            expressQueues.add(i,new LinkedQueue<>());
        }
        /**
         * Add the required normal queues to normalQueues
         */
        for(int i = 0; i < normalLines; i++){
            normalQueues.add(i,new LinkedQueue<>());
        }
        /**
         * Loop through arrayOnFileRead and place each customer in the correct queue
         */
        for(int index = 0; index < arrayOnFileRead.size(); index++){
            int compareSumNorm = maxCustomerTime;
            int compareSumExp = maxCustomerTime;
            int requiredIndexNorm = 0;
            int requiredIndexExp = 0;
            boolean elementAdded = false;
            /**
             * Loop through expressQueues if the element was not added and if the customer's cart items are less than or equal to itemsLimit
             */
            for(int j = 0; j < expressQueues.size() && !elementAdded && arrayOnFileRead.get(index).getNumberOfItems() <= itemsLimit; j++){
                /**
                 * Add the Customer if the current queue is empty
                 */
                if(expressQueues.get(j).isEmpty()){
                    expressQueues.get(j).enqueue(arrayOnFileRead.get(index));
                    sumOfExpQueues.add(expressQueues.indexOf(expressQueues.get(j)),arrayOnFileRead.get(index).getTimeTaken());
                    elementAdded = true;
                }
                /**
                 * Else check if the current queue has the minimum total time
                 */
                else{
                    if(sumOfExpQueues.get(j) < compareSumExp){
                        compareSumExp = sumOfExpQueues.get(j);
                        requiredIndexExp = j;
                    }
                }
            }
            /**
             * Loop through normalQueues if the element was not added in expressQueues
             */
            for (int k = 0; k < normalQueues.size() && !elementAdded; k++){
                /**
                 * Add the Customer if the current queue is empty
                 */
                if(normalQueues.get(k).isEmpty()){
                    normalQueues.get(k).enqueue(arrayOnFileRead.get(index));
                    sumOfNormQueues.add(normalQueues.indexOf(normalQueues.get(k)),arrayOnFileRead.get(index).getTimeTaken());
                    elementAdded = true;
                }
                /**
                 * Else check if the current queue has the minimum total time
                 */
                else{
                    if(sumOfNormQueues.get(k) < compareSumNorm){
                        compareSumNorm = sumOfNormQueues.get(k);
                        requiredIndexNorm = k;
                    }
                }
            }
            /**
             * Add the customer to expressQueues in the queue where the sum of the queue was least
             */
            if(arrayOnFileRead.get(index).getNumberOfItems() <= itemsLimit && !elementAdded && compareSumExp <= compareSumNorm){
                expressQueues.get(requiredIndexExp).enqueue(arrayOnFileRead.get(index));
                sumOfExpQueues.set(requiredIndexExp,sumOfExpQueues.get(requiredIndexExp)+arrayOnFileRead.get(index).getTimeTaken());
                elementAdded = true;
            }
            /**
             * if customer is still not added, then add it to normalQueues in the queue where the sum of the queue was least
             */
            else if(!elementAdded){
                normalQueues.get(requiredIndexNorm).enqueue(arrayOnFileRead.get(index));
                sumOfNormQueues.set(requiredIndexNorm,sumOfNormQueues.get(requiredIndexNorm)+arrayOnFileRead.get(index).getTimeTaken());
                elementAdded = true;
            }

        }


        int totalTime = 0;
        int line = 0;
        System.out.println();
        System.out.println("********** PART A -Checkout lines and time estimates for each line **********");
        for(int a = 0; a < expressQueues.size(); a++){
            line++;
            System.out.println("Checkout(Express) #"+ line+ " (Est Time= "+sumOfExpQueues.get(a)+" s) = "+expressQueues.get(a).toString());
            if(sumOfExpQueues.get(a) > totalTime){
                totalTime = sumOfExpQueues.get(a);
            }
        }

        for(int a = 0; a < normalQueues.size(); a++){
            line++;
            System.out.println("Checkout(Normal ) #"+ line+ " (Est Time= "+sumOfNormQueues.get(a)+" s) = "+normalQueues.get(a).toString());
            if(sumOfNormQueues.get(a) > totalTime){
                totalTime = sumOfNormQueues.get(a);
            }
        }
        System.out.println("Time to clear store of all customers = "+totalTime+" s");
        System.out.println();
        System.out.println("********** PART B -Number of customers in line after every 30 s **********");
        System.out.print("t(s):     ");
        for(int k = 1; k <= (expressLines+normalLines); k++){
            System.out.print("Line "+k+"       ");
        }
        System.out.println();
        for(int time = 0; time <= totalTime; time++){
            /**
             * print the number of customers left in each queue after every 30 s
             * Always print the number of customers in each queue when the loop reaches its end or when all queues are empty
             */
            if((time > 0 && time%30 == 0) || time == totalTime){
                System.out.print((time)+"           ");
            }
            for(int i = 0; i < expressQueues.size(); i++){
                if(!expressQueues.get(i).isEmpty()){
                    int timeTaken = expressQueues.get(i).peek().setTimeTaken(0);//same as expressQueues.get(i).peek().getTimeTaken()
                    if(timeTaken <= time){
                        expressQueues.get(i).dequeue();
                        if(!expressQueues.get(i).isEmpty()){
                            /**
                             * Add the total time of the queue that has been processed by now to the first customer in queue
                             */
                            expressQueues.get(i).peek().setTimeTaken(timeTaken);
                        }
                    }
                }
                if((time > 0 && time%30 == 0) || time == totalTime){
                    System.out.print(expressQueues.get(i).size()+"             ");
                }

            }
            for(int i = 0; i < normalQueues.size(); i++){
                if(!normalQueues.get(i).isEmpty()){
                    int timeTaken = normalQueues.get(i).peek().setTimeTaken(0);
                    if(timeTaken <= time){
                        normalQueues.get(i).dequeue();
                        if(!normalQueues.get(i).isEmpty()){
                            normalQueues.get(i).peek().setTimeTaken(timeTaken);
                        }
                    }
                }
                if((time > 0 && time%30 == 0) || time == totalTime){
                    System.out.print(normalQueues.get(i).size()+"            ");
                }

            }
            if((time > 0 && time%30 == 0) || time == totalTime){
                System.out.println();
            }

        }
    }
}
