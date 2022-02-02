import org.jcsp.lang.*;

public class Buffer implements CSProcess {
    private Any2OneChannel toPrev;
    private Any2OneChannel toNext;

    final int id;
    final int maxBuffer;
    private int currentValue;

    public Buffer (int id , Any2OneChannel toPrev, Any2OneChannel toNext ,int maxBuffer) {
        this.id = id;
        this.toPrev = toPrev;
        this.toNext = toNext;
        this.maxBuffer = maxBuffer;
    } // constructor
    public void run () {
        while(true){
            dataBox data = null;
            try {
                data = getDataBox();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("B:" + id + " buffer started. Value = " + data.isProducer() + " " + data.getValue() );
            if(data.checkId(id) == false){

                //System.out.println(id + " Reject!");
            }
            else{
                if(canExecute(data)){
                    //System.out.println("B:" + id + " current state = " + currentValue);
                    data.setIsDone();
                    //System.out.println(id + " Executed!");
                }
                else{
                    data.setNextBuffer(toNext);
                    //System.out.println(id + " Sending to next!");
                }
            }
            sendBack(data);
            //System.out.println(id + " DONE!");
        }
    }

    private dataBox getDataBox() throws InterruptedException {
            //System.out.println("B:" + id + " getting data.");
            AltingChannelInput prev = toPrev.in();
            dataBox data =  (dataBox) prev.read();
            //System.out.println("B:" + id + " got data.");
            return data;
    }

    private boolean tryProduce(int val){
        if(currentValue + val <= maxBuffer){
            currentValue += val;
            return true;
        }
        return false;
    }
    private boolean tryConsume(int val){
        if(currentValue - val >= 0){
            currentValue -= val;
            return true;
        }
        return false;
    }
    private void sendBack(dataBox data){
        ChannelOutput output = data.getChannel().out();
        output.write(data);
    }
    private boolean canExecute(dataBox data){
        if(data.isProducer()){
            return tryProduce(data.getValue());
        }
        else{
            return tryConsume(data.getValue());
        }
    }
    public Any2OneChannel getBufferChannel(){
        return toPrev;
    }

    public int getId(){
        return id;
    }

}