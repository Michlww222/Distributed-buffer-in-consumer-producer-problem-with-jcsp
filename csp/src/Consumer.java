import org.jcsp.lang.*;

public class Consumer implements CSProcess {

    final int id;
    final int maxBuffer;
    private final boolean isProducer = false;
    private Statistics myStats;
    private Statistics globalStats;
    private dataBox data;
    private int value = -1;
    private Any2OneChannel privateChannel = new StandardChannelFactory().createAny2One();
    private AltingChannelInput input = privateChannel.in();
    private ChannelOutput output = null;


    public Consumer (int id,int maxBuffer,Statistics myStats,Statistics globalStats) {
        this.id = id;
        this.maxBuffer = maxBuffer;
        this.myStats = myStats;
        this.globalStats = globalStats;
        this.data = prepareData();
    }
    public void run () {
        while (true) {
            output = getOutput();
            output.write(data);
            input.read();
            getAnswer();
            refreshStats();
            value = getValue();
            data.refreshBox(value);
        }
    }
    private ChannelOutput getOutput(){
        Buffer newBuffer = Simulation.getRandomBuffer();
        //System.out.println("consumer started " + id + " sending to buffer " + newBuffer.getId() + " value " + value);
        return newBuffer.getBufferChannel().out();
    }
    private dataBox prepareData(){
        this.value = getValue();
        return new dataBox(this.value,this.privateChannel,this.isProducer);
    }

    private int getValue(){
        return (int) (Math.random() * (maxBuffer / 2) + 1);
    }

    private void getAnswer(){
        while(data.isRejected() != true && data.isDone() != true){
            output = data.getNextBuffer().out();
            output.write(data);
            data = (dataBox) input.read();
        }
    }

    private void refreshStats(){
        if (data.isRejected()) {
            //System.out.println("C: " + id + " rejected");
            myStats.addRejected();
            globalStats.addRejected();
        } else {
            //System.out.println("C:" + id + " consumed " + data.getValue());
            myStats.newOperation(data);
            globalStats.newOperation(data);
        }
    }

    public String getStatistic(){
       return myStats.returnConsumerStats();
    }
}