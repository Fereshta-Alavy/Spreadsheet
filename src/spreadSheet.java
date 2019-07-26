import java.util.*;


public class spreadSheet {

    Map<Character, List<String>> sheet = new HashMap<Character, List<String>>();
    Map<String, String> dependencies = new HashMap<String, String>();
    int r = 0;
    int c = 0;

    // Getting dependecies in a new hash map
    public void getDependencies(){
        for (int i = 0; i < r; i++){
            Character row = 'A';
            List<String> content = this.sheet.get(row);
            for (int j = 0; j < c; j++){
                String [] cell = content.get(j).split(" ");
                if (!isNumber(content.get(j)) && cell.length == 1){
                    dependencies.put(cell[0], row.toString() + Integer.toString(j));
                }
            }
        }
    }

    // Read input from scanner to create spreadsheet
    public void readInput(Scanner inputScanner) throws RuntimeException {

        int n = inputScanner.nextInt();
        int m = inputScanner.nextInt();


        this.c = n;
        this.r = m;

        inputScanner.nextLine();
        char firstRow = 'A';
        for (int row = 0; row < m; row++) {
            List<String> list = new ArrayList<String>();
            for (int col = 0; col < n; col++) {
                String data = inputScanner.nextLine().trim().toUpperCase();
                list.add(new String(data));

            }
            list.remove(null);
            sheet.put(firstRow++, list);
        }

        getDependencies();
        printWorksheet();
    }

    public void printWorksheet() {
        sheet.forEach((key, value) -> System.out.println(key + ":" + value));
        for (Character key : sheet.keySet()){
            for (int i = 0; i < this.c; i++) {
                System.out.print(evaluate(sheet.get(key).get(i)));
                System.out.print(' ');
            }
            System.out.println('\n');
            }
        }
    // Print the appropriate cell value
    public String getNumber(String num){
        char row = num.charAt(0);
        int col = Integer.parseInt(num.substring(1))-1;
        return this.sheet.get(row).get(col);
    }

    // Evaluate the expression and return the value
    public float evaluate(String content) {
        if (isNumber(content))
            return Float.parseFloat(content);

        String[] fields = content.split(" ");

        if (fields.length == 1){
            return evaluate(getNumber(fields[0]));
        }

        Stack<Float> stack = new Stack<Float>();
        for (int i = 0; i < fields.length; i++) {
            String element = fields[i];
            if (element.equals("*")) {
                float result = stack.pop() * stack.pop();
                stack.push(result);
            } else if (element.equals("+")) {
                float result = stack.pop() + stack.pop();
                stack.push(result);
            } else if (element.equals("/")) {
                float result = stack.pop() / stack.pop();
                stack.push(result);
            } else if (element.equals("-")) {
                float value1 = stack.pop();
                float result = stack.pop() - value1;
                stack.push(result);
            } else if (isNumber(element)) {
                stack.push(Float.valueOf(element));
            } else {
                stack.push(Float.parseFloat(getNumber(element)));
            }

        }
        return stack.pop();

        }

    private static boolean isNumber(String data) {
        try {
            Double.parseDouble(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }


    public static void main(String[] args) {
        spreadSheet spreadsheet = new spreadSheet();
        Scanner input = new Scanner(System.in);
        spreadsheet.readInput(input);
    }

}
