public void setTime(int gio, int phut, int giay) {
    setGio(gio);
    setPhut(phut);
    setGiay(giay);
}

public void setGio(int gio) {
    this.gio = (gio >= 0 && gio < 24) ? gio : 0;
}

public void setPhut(int phut) {
    this.phut = (phut >= 0 && phut < 60) ? phut : 0;
}

public void setGiay(int giay) {
    this.giay = (giay >= 0 && giay < 60) ? giay : 0;
}

public int getGio() {
    return gio;
}

public int getPhut() {
    return phut;
}

public int getGiay() {
    return giay;
}

public String toString() {
    return String.format("%02d:%02d:%02d", gio, phut, giay);
}
}