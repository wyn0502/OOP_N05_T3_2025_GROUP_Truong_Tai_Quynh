public class NameNumber1 {
    private String lastName;
    private String telNumber;

    public NameNumber1() {}

    public NameNumber1(String name, String num) {
        lastName = name;
        telNumber = num;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTelNumber() {
        return telNumber;
    }
}

class NNCollection {
    private NameNumber1[] nnArray = new NameNumber1[100];
    private int free;

    public NNCollection() {
        free = 0;
    }

    public void insert(NameNumber1 n) {
        int i;
        for (i = free; i > 0 && nnArray[i - 1].getLastName().compareTo(n.getLastName()) > 0; i--) {
            nnArray[i] = nnArray[i - 1];
        }
        nnArray[i] = n;
        free++;
    }

    public String findNumber(String lName) {
        for (int i = 0; i < free; i++) {
            if (nnArray[i].getLastName().equals(lName))
                return nnArray[i].getTelNumber();
        }
        return "Name not found";
    }
}
