import java.util.function.Function;

public class SimpleTesting<T, R> {

    private SimpleDebug logger = new SimpleDebug(2);

    /**
     * Initializes the SimpleTesting class with a specified debug level.
     * @param level The debug level for logging.
     */
    public SimpleTesting(int level) { logger = new SimpleDebug(level); }

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
                assert (result.equals(expectedResult)) : SimpleColor.ERROR("Test case failed. Expected: ") + SimpleColor.INFO(expectedResult.toString()) + SimpleColor.ERROR(", but got: ") + SimpleColor.INFO(result.toString());
                logger.success(SimpleColor.SUCCESS("Test case passed."), 2);
            } catch (Exception e) {
                logger.error(SimpleColor.ERROR("Test case failed with exception: ") + SimpleColor.INFO(e.getMessage()), 2);
            }
        }
    }
}