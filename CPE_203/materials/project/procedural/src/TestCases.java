

import edu.calpoly.testy.Testy;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import static edu.calpoly.testy.Assert.assertEquals;
import static edu.calpoly.testy.Assert.assertTrue;
import java.util.function.ToIntBiFunction;


/**
 * This class contains unit tests for Minecraft 2: Electric Boogaloo.
 */
public class TestCases {

    private void loadImagesTest() {
        // This will fail if an image name is misspelled.  By doing this
        // here, we make checkgit test image loading.
	VirtualWorld.loadEntityImages();
    }

    private void countAnimationLimitedTest(){
        TestAnimatable testEntity = new TestAnimatable(1, 0);
        Action dummyAction = new AnimationAction(testEntity, 31);
        EventSchedule dummySchedule = new EventSchedule(1.0);
        dummySchedule.scheduleEvent(dummySchedule, testEntity, dummyAction, testEntity.getAnimationPeriod());
        dummySchedule.processEvents(dummySchedule , 100.0);
		assertEquals(30, testEntity.called);	
	}
    private void countAnimationIndefinentTest(){
        TestAnimatable testEntity = new TestAnimatable(1, 0);
        Action dummyAction = new AnimationAction(testEntity, 999999);
        EventSchedule dummySchedule = new EventSchedule(1.0);
        dummySchedule.scheduleEvent(dummySchedule, testEntity, dummyAction, testEntity.getAnimationPeriod());
        dummySchedule.processEvents(dummySchedule , 100.0);
		assertEquals(100, testEntity.called);
		
	}
	
	private void neighborTest(Function<Point, List<Point>> potentialNeighbors){
		Point test = new Point(2,2);
		List<Point> neighbors = potentialNeighbors.apply(test);
		assertTrue(neighbors.contains(new Point(2, 3)));
		assertTrue(neighbors.contains(new Point(2, 1)));
		assertTrue(neighbors.contains(new Point(3, 3)));
		assertTrue(neighbors.contains(new Point(1, 3)));
		assertTrue(neighbors.contains(new Point(3, 2)));
		assertTrue(neighbors.contains(new Point(1, 2)));
		assertTrue(neighbors.contains(new Point(3, 1)));
		assertTrue(neighbors.contains(new Point(1, 1)));		
	}

	private void stepstoTest(ToIntBiFunction<Point, Point> stepsFromTo){
		int stepsAway = stepsFromTo.applyAsInt(new Point(1,1), new Point(1,10));
		assertEquals(9, stepsAway);
	}
	
	private void costEstimateTest(){
		AStarPathingStrategy a = new AStarPathingStrategy();
		double i = a.costEstimate(new Point(1,1), new Point(1,4), 3);
		assertEquals(i, 3.0);
		i = a.costEstimate(new Point(1,1), new Point(4,4), 3);
		assertEquals(i, 4.242640687119285);
	}

	
    /**
     * Run the tests.
     *
     * @return The number of failures.
     */
    public int runTests() {
	return Testy.run(
		() -> loadImagesTest(),
		() -> countAnimationLimitedTest(),
		() -> countAnimationIndefinentTest(),
		() -> neighborTest(PathingFunctions.potentialNeighbors),
		() -> stepstoTest(PathingFunctions.stepsFromTo),
		() -> costEstimateTest()
	);
    }
}
