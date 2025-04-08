import java.io.File;
import java.util.function.Function;

public class SimpleTesting<T, R> {

    private SimpleDebug logger;
    private File file;

    /**
     * Initializes the SimpleTesting class with a specified debug level. Default file path is {@code .\debug.log"}.
     * @param level The debug level for logging.
     */
    public SimpleTesting(int level) {
        logger = new SimpleDebug(level);
        file = new File("debug.log");
    }

    /**
     * Initializes the SimpleTesting class with a specified debug level and file path.
     * @param level The debug level for logging.
     * @param filePath The path to the file where logs will be written.
     */
    public SimpleTesting(int level, String filePath) {
        logger = new SimpleDebug(level);
        this.file = new File(filePath);
    }

    /**
     * Prints the test cases in a formatted manner.
     * @param testCases The array of test cases to be printed.
     */
    public void prettyPrint(T[] testCases) {
        logger.debug("Test Cases:", 1);
        for (int i = 0; i < testCases.length; i++) {
            logger.debug("  " + SimpleColor.INFO("" + i) + SimpleColor.DEBUG(" : ") + SimpleColor.INFO(testCases[i].toString()), 2);
        }
    }

    /**
     * Evaluates the test cases against expected results using a specified operation.
     * @param cases The array of test cases to be evaluated.
     * @param expectedResults The array of expected results corresponding to the test cases.
     * @param operation The function that performs the operation on each test case.
     */
    public void evaulateCases(T[] cases, R[] expectedResults, Function<T, R> operation) {
        for(int i = 0; i < cases.length; i++) {
            T testCase = cases[i];
            R expectedResult = expectedResults[i];
            try {
                R result = operation.apply((T) testCase);
                if (!result.equals(expectedResult)) logger.error("Test case failed. Expected: " + SimpleColor.INFO(expectedResult.toString()) + SimpleColor.ERROR(", but got: ") + SimpleColor.INFO(result.toString()), 2);
                else logger.success(SimpleColor.SUCCESS("Test case passed."), 2);
            } catch (Exception e) {
                logger.error("Test case failed with exception: " + SimpleColor.INFO(e.getMessage()), 2);
            }
        }
    }

    /**
     * Evaluates the test cases against expected results using a specified operation and logs the results to a file.
     * @param testCases The array of test cases to be logged.
     */
    public void logCases(T[] testCases) throws Exception {
        logger.debug("Test Cases:", 1);
        for (int i = 0; i < testCases.length; i++) {
            logger.debug("  " + i + " : " + testCases[i].toString(), file, 2);
        }
    }

    /**
     * Evaluates the test cases against expected results using a specified operation and logs the results to a file.
     * @param cases The array of test cases to be evaluated.
     * @param expectedResults The array of expected results corresponding to the test cases.
     * @param operation The function that performs the operation on each test case.
     */
    public void logEvaluations(T[] cases, R[] expectedResults, Function<T, R> operation) throws Exception {
        for(int i = 0; i < cases.length; i++) {
            T testCase = cases[i];
            R expectedResult = expectedResults[i];
            try {
                R result = operation.apply((T) testCase);
                if (!result.equals(expectedResult)) logger.debug("Test case failed. Expected: " + expectedResult.toString() + ", but got: " + result.toString(), file, 2);
                else logger.debug("Test case passed.", file, 2);
            } catch (Exception e) {
                logger.debug("Test case failed with exception: " + e.getMessage(), file, 2);
            }
        }
    }
}