import java.util.ArrayList;

public class Statistics {
    private final int id;
    private int maxBuffer;
    private int[] producedList;
    private int[] consumedList;
    private int rejected = 0;

    public Statistics(int maxBuffer,int id){
        this.id = id;
        this.maxBuffer = maxBuffer;
        this.producedList = new int[maxBuffer/2 + 1];
        this.consumedList = new int[maxBuffer/2 + 1];
        for(int i=0;i<maxBuffer/2;i+=1){
            producedList[i] = 0;
            consumedList[i] = 0;
        }
    }

    public void newOperation(dataBox box){
        if(box.isProducer()){
            produced(box.getValue());
        } else{
            consumed(box.getValue());
        }
    }

    public void addRejected(){
        rejected+=1;
    }

    private void produced(int val){
        producedList[val] += 1;
    }

    private void consumed(int val){
        consumedList[val] += 1;
    }

    public String returnStats(){
        String result = "Producer: ";

        for(int i = 1;i<maxBuffer/2;i+=1){
            result += (i + " " + producedList[i] + " ");
        }
        result += "\nConsumers: ";
        for(int i = 1;i<maxBuffer/2;i+=1){
            result += (i + " " + consumedList[i] + " ");
        }
        result += ("rejected " + rejected + " ");
        return result;
    }

    public String returnProducerStats(){
        String result = id + ": ";

        for(int i = 1;i<maxBuffer/2;i+=1){
            result += (i + " " + producedList[i] + " ");
        }
        result += ("rejected " + rejected + " ");
        return result;
    }

    public String returnConsumerStats(){
        String result = id + ": ";

        for(int i = 1;i<maxBuffer/2;i+=1){
            result += (i + " " + consumedList[i] + " ");
        }
        result += ("rejected " + rejected + " ");
        return result;
    }
}
