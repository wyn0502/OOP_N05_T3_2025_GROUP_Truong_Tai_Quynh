public class DisruptLecture {
    public static void main(String[] args) {
    CellPhone noiseMaker = new CellPhone();

    Tune t1 = new Tune();               
    Tune t2 = new ObnoxiousTune();      

    noiseMaker.ring(t1);                
    noiseMaker.ring(t2);                

    
}

}
