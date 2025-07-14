public class nameNumber {
    private String lastName;
    private String telNumber;
    public nameNumber() {}

    public nameNumber(String name, String num) {
        lastName = name;
        telNumber num;
    }

    public String getLastName() { return lasName; }
    public String getTelNumber() { return telNumber; }

    public class NNCollection {
        private nameNumber[] = new nameNumber[100];
        private int free;
        public void insert(nameNumber n) {
            int index = 0;
            for (int i = free++;
                 i != 0 &&
                 nnArray[i - 1].getLastName().compareTo(n.getLastName()) > 0;
                 i--) {
                nnArray[i] = nnArray[i - 1];
                index = i;
            }
            nnArray[index] = n;
        }
    }
}
