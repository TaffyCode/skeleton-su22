package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Raphael Pelayo
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {

        Repository repository = new Repository();
        boolean done = false;

        if (args == null || args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }

        String firstArg = args[0];

        switch(firstArg) {
            case "init":
                done = true;
                if (lengthTest(args, 1)) {
                    repository.init();
                }
                break;
            case "add":
                done = true;
                if (lengthTest(args, 2)) {
                    repository.add(args[1]);
                }
                break;
            case "commit":
                done = true;
                if (lengthTest(args, 2)) {
                    repository.commit(args[1]);
                }
                break;
            case "checkout":
                done = true;
                if (args.length == 2) {
                    repository.checkoutForBranches(args[1]);
                }
                else if (args.length == 3) {
                    if (args[1].equals("--")) {
                        repository.checkout(args[2]);
                    } else {
                        System.out.println("Incorrect operands.");
                    }
                }
                else if (args.length == 4) {
                    if (args[2].equals("--")) {
                        repository.checkout(args[1], args[3]);
                    } else {
                        System.out.println("Incorrect operands.");
                    }
                } else {
                    System.out.println("Incorrect operands.");
                }
                break;
            case "log":
                done = true;
                if (lengthTest(args, 1)) {
                    repository.log();
                }
                break;
            case "status":
                done = true;
                if (lengthTest(args, 1)) {
                    repository.status();
                }
                break;
            case "rm":
                done = true;
                if (lengthTest(args, 2)) {
                    repository.rm(args[1]);
                }
                break;
            case "global-log":
                done = true;
                if (lengthTest(args, 1)) {
                    repository.globalLog();
                }
                break;
            case "find":
                done = true;
                if (lengthTest(args, 2)) {
                    repository.find(args[1]);
                }
            case "branch":
                done = true;
                if (lengthTest(args, 2)) {
                    repository.branch(args[1]);
                }
                break;
            case "rm-branch":
                done = true;
                if (lengthTest(args, 2)) {
                    repository.rmBranch(args[1]);
                }
                break;
            case "reset":
                done = true;
                if (lengthTest(args, 2)) {
                    repository.reset(args[1]);
                }
                break;
            case "merge":
                done = true;
                System.out.println("lol no");
                break;
        }
        if (!done) {
            System.out.println("No command with that name exists.");
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
