package src;

class TH5_CellPhone {
    public TH5_CellPhone() {}

    public void ring(Tune t) {
        t.play();
    }
}

class Tune {
    public void play() {
        System.out.println("Tune.play()");
    }
}

class ObnoxiousTune extends Tune {
    public ObnoxiousTune() {
        System.out.println("ObnoxiousTune created");
    }

    @Override
    public void play() {
        System.out.println("ObnoxiousTune.play()");
    }
}