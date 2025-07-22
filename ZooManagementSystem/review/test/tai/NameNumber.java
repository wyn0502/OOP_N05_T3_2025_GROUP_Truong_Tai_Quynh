package src;

public class NameNumber {
    private String lastName;
    private String telNumber;

    public NameNumber() {}

    public NameNumber(String name, String num) {
        this.lastName = name;
        this.telNumber = num;
    }

    public String getLastName() { return lastName; }

    public String getTelNumber() { return telNumber; }
}

class NNCollection {
    private NameNumber[] nnArray = new NameNumber[100];
    private int free = 0;

    public void insert(NameNumber n) {
        int index = free;
        while (index > 0 && nnArray[index - 1].getLastName().compareTo(n.getLastName()) > 0) {
            nnArray[index] = nnArray[index - 1];
            index--;
        }
        nnArray[index] = n;
        free++;
    }

    public void printAll() {
        for (int i = 0; i < free; i++) {
            System.out.println(nnArray[i].getLastName() + " - " + nnArray[i].getTelNumber());
        }
    }

    public String findNumber(String lName) {
        for (int i = 0; i < free; i++) {
            if (nnArray[i].getLastName().equals(lName)) {
                return nnArray[i].getTelNumber();
            }
        }
        return "Name not found";
    }
}
