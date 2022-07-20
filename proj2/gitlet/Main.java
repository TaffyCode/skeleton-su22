package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {

        Repository repository = new Repository();

        if (args == null) {
            System.out.println("Please enter a command");
            return;
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                if (lengthTest(args, 1)) {
                    repository.init();
                }
                break;
            case "add":
                if (lengthTest(args, 2)) {
                    repository.add(args[1]);
                }
                break;
            case "commit":
                if (lengthTest(args, 2)) {
                    repository.commit(args[1]);
                }
                break;
            case "rm":
                if (lengthTest(args, 2)) {
                    repository.rm();
                }
                break;
            case "log":
                if (lengthTest(args, 1)) {
                    repository.log();
                }
                break;
            case "global-log":
                if (lengthTest(args, 1)) {
                    repository.globalLog();
                }
                break;
            case "find":
                if (lengthTest(args, 2)) {
                    repository.find(args[1]);
                }
                break;
            case "checkout":
                repository.checkout(args);
                break;
            case "branch":
                if (lengthTest(args, 2)) {
                    repository.branch(args[1]);
                }
                break;
            case "rm-branch":
                if (lengthTest(args, 2)) {
                    repository.rmBranch(args[1]);
                }
            case "reset":
                if (lengthTest(args, 2)) {
                    repository.reset(args[1]);
                }
            case "merge":
                if (lengthTest(args, 2)) {
                    repository.merge(args[1]);
                }
        }
    }

    private static boolean lengthTest(String[] args, int expected) {
        if (args.length == expected) {
            return true;
        }
        else {
            System.out.println("Incorrect operands");
            return false;
        }
    }
}
