import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.One2OneChannel;

public class dataBox {
    private int value;
    private Any2OneChannel channel;
    private boolean isProducer;
    private boolean isRejected = false;
    private boolean isDone = false;
    private int firstBufferId = -1;
    private Any2OneChannel nextBuffer = null;

    public dataBox(int value,Any2OneChannel channel,boolean isProducer){
        this.value = value;
        this.channel = channel;
        this.isProducer = isProducer;
    }

    public Any2OneChannel getChannel() {
        return channel;
    }

    public int getValue() {
        return value;
    }

    public boolean isProducer() {
        return isProducer;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public boolean checkId(int id){
        if (firstBufferId == id){
            isRejected = true;
            return false;
        }
        if(firstBufferId == -1){
            firstBufferId = id;
            return true;
        }
        return true;
    }
    public void setNextBuffer(Any2OneChannel channel){
        this.nextBuffer = channel;
    }
    public Any2OneChannel getNextBuffer(){
        return this.nextBuffer;
    }

    public boolean isDone(){
        return isDone;
    }
    public void setIsDone(){
        isDone =  true;
    }

    public void refreshBox(int val){
        this.value = val;
        this.isRejected = false;
        this.isDone = false;
        this.nextBuffer = null;
        this.firstBufferId = -1;
    }


}
