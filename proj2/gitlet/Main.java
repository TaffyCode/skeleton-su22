package gitlet;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author Raphael Pelayo
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        Repository repository = new Repository();
        if (args == null || args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }
        String firstArg = args[0];
        switch (firstArg) {
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
            case "checkout":
                if (args.length == 2) {
                    repository.checkoutForBranches(args[1]);
                } else if (args.length == 3 && args[1].equals("--")) {
                    repository.checkout(args[2]);
                } else if (args.length == 4 && args[2].equals("--")) {
                    repository.checkout(args[1], args[3]);
                } else {
                    System.out.println("Incorrect operands.");
                }
                break;
            case "log":
                if (lengthTest(args, 1)) {
                    repository.log();
                }
                break;
            case "status":
                if (lengthTest(args, 1)) {
                    repository.status();
                }
                break;
            case "rm":
                if (lengthTest(args, 2)) {
                    repository.rm(args[1]);
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
            case "branch":
                if (lengthTest(args, 2)) {
                    repository.branch(args[1]);
                }
                break;
            case "rm-branch":
                if (lengthTest(args, 2)) {
                    repository.rmBranch(args[1]);
                }
                break;
            case "reset":
                if (lengthTest(args, 2)) {
                    repository.reset(args[1]);
                }
                break;
            case "merge":
                if (lengthTest(args, 2)) {
                    repository.merge(args[1]);
                }
                break;
            default:
                System.out.println("No command with that name exists.");
        }
    }

    private static boolean lengthTest(String[] args, int expected) {
        if (args.length == expected) {
            return true;
        } else {
            System.out.println("Incorrect operands");
            return false;
        }
    }
}
