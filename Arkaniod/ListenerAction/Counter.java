package ListenerAction;

public class Counter {
    private int num;

    /**
     * constractor weith given num that the counter will start with
     * @param num the start of the count
     */
    public Counter(int num){
        this.num = num;
    }

    /**
     * consractor that start the count to count from 0.
     */
    public Counter(){
        this.num = 0;
    }

    /**
     * method that add number to current count.
     * @param number the number to add
     */
    public void increase(int number){
        this.num = this.num+number;
    }

    /**
     * method that subtract number to current count.
     * @param number the number to subtract
     */
    public void decrease(int number){
        this.num = this.num - number;
    }

    /**
     * getter method that return the count
     * @return the count in counter
     */
    public int getValue(){
        return this.num;
    }
}