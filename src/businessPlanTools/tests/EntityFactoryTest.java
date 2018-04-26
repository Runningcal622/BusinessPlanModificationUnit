package businessPlanTools.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import Server.*;

import java.util.*;

//The test for the EntityFactory
class EntityFactoryTest
{

	MockEntityFactory factory;

	// Reset the factory before each test
	@BeforeEach
	public void initFactory()
	{
		factory = new MockEntityFactory(new String[] { "Head", "Tail" });
	}

	// The method to be called to run all of our tests
	@Test
	public void test()
	{
		factory = new MockEntityFactory(new String[] { "Head", "Tail" });

		testNextLayer();
		testEntityFactoryStringArrayConstructor();
		testEntityFactoryEntityFactoryStringConstructor();
		testGetSubFactory();
		testGetFactoryFromIndex();
		testSetAllDefaultStatements();
	}

	@Test
	public void testNextLayer()
	{

		BusinessEntity entityOne = factory.nextLayer(null);
		BusinessEntity entityTwo = factory.nextLayer(null);

		// Two separately created BusinessEntites should not be equal, even if they have
		// the same attributes
		assertNotEquals(entityOne, entityTwo);

		// Make entityTwo vary for stronger testing
		ArrayList<EntityStatement> newDefaultStatements = new ArrayList<EntityStatement>();
		newDefaultStatements.add(new EntityStatement("Test Title", "Test Description", null, null));
		factory.setDefaultStatements(newDefaultStatements);
		factory.setEntityTitle("New Head");

		entityTwo = factory.nextLayer(null);

		// Ensure that both entities have the appropriate entityTitle
		assertEquals(entityOne.getEntityTitle(), "Head");
		assertEquals(entityTwo.getEntityTitle(), "New Head");

		// Both entities should have non-null subFactories
		assertNotEquals(entityOne.getSubentityFactory(), null);
		assertNotEquals(entityTwo.getSubentityFactory(), null);

		// Ensure both entities have the been given the correct default statements
		assertEquals(entityOne.getStatements(), null);

		// The factory should have given entityTwo an alias of newDefaultStatements
		assertEquals(entityTwo.getStatements(), newDefaultStatements);

		// Ensure both entities have an empty ArrayList for their subentities
		assertNotEquals(entityOne.getSubentities(), null);
		assertNotEquals(entityTwo.getSubentities(), null);

		assertEquals(entityOne.getSubentities().size(), 0);
		assertEquals(entityTwo.getSubentities().size(), 0);

		// IMPORTANT: If orphans make you sad, ignore this next test
		// Ensure both entities don't have parents (Oh, the humanity! T_T)
		// (In case you can't tell, I don't like testing)
		assertEquals(entityOne.getParentEntity(), null);
		assertEquals(entityOne.getParentEntity(), null);
	}

	// Tests the constructor with a String array parameter
	@Test
	public void testEntityFactoryStringArrayConstructor()
	{
		// Test the case with an empty array
		factory = new MockEntityFactory(new String[] {});

		assertEquals(factory.getEntityTitle(), null);
		assertEquals(factory.getSubFactory(), null);

		// Test the case with one element in the array
		factory = new MockEntityFactory(new String[] { "Only" });

		assertEquals(factory.getEntityTitle(), "Only");
		assertEquals(factory.getSubFactory(), null);

		// Test the case with more than one element in the array
		factory = new MockEntityFactory(new String[] { "First", "Second", "Third" });

		MockEntityFactory surfaceLevel = factory;
		assertEquals(surfaceLevel.getEntityTitle(), "First");
		assertNotEquals(factory.getSubFactory(), null);

		EntityFactory oneLevelDown = factory.getSubFactory();
		assertEquals(oneLevelDown.getEntityTitle(), "Second");
		assertNotEquals(oneLevelDown.getSubFactory(), null);

		EntityFactory twoLevelsDown = oneLevelDown.getSubFactory();
		assertEquals(twoLevelsDown.getEntityTitle(), "Third");
		assertEquals(twoLevelsDown.getSubFactory(), null);

	}

	// Test the constructor that takes an EntityFactory and a String
	@Test
	public void testEntityFactoryEntityFactoryStringConstructor()
	{

		// Check the case where both are null
		factory = new MockEntityFactory(null, null);
		assertEquals(factory.getEntityTitle(), null);
		assertEquals(factory.getSubFactory(), null);

		// Check the case where the EntityFactory is null and the String is not
		MockEntityFactory factoryOne = new MockEntityFactory(null, "Head");
		assertEquals(factoryOne.getEntityTitle(), "Head");
		assertEquals(factoryOne.getSubFactory(), null);

		// Check the case where the EntityFactory is not null and the string is
		MockEntityFactory factoryTwo = new MockEntityFactory(factory, null);
		assertEquals(factoryTwo.getEntityTitle(), null);
		assertEquals(factoryTwo.getSubFactory(), factory);

		// Check the case where neither is null
		MockEntityFactory factoryThree = new MockEntityFactory(factoryOne, "Head 2");
		assertEquals(factoryThree.getEntityTitle(), "Head 2");
		assertEquals(factoryThree.getSubFactory(), factoryOne);
	}

	@Test
	public void testGetSubFactory()
	{
		factory = new MockEntityFactory(new String[] { "Head" });

		// When the factory is constructed with one string, subFactory should be null
		assertEquals(factory.getSubFactory(), null);

		// When the factory is constructed with more than one string, the subFactory
		// should not be null
		factory = new MockEntityFactory(new String[] { "Head", "Tail" });
		assertNotEquals(factory.getSubFactory(), null);
	}

	@Test
	public void testGetFactoryFromIndex()
	{
		factory = new MockEntityFactory(new String[] { "First", "Second", "Third" });

		// Test case where index is negative
		assertEquals(factory.getFactoryFromIndex(-1), null);

		// Test case where index is 0
		assertNotEquals(factory.getFactoryFromIndex(0), null);

		// Test case where index is within the length of the list of factories
		assertNotEquals(factory.getFactoryFromIndex(1), null);

		// Test case where the index is greater than the length of the list of factories
		assertEquals(factory.getFactoryFromIndex(5), null);
	}

	@Test
	public void testSetAllDefaultStatements()
	{
		factory = new MockEntityFactory(new String[] { "UNO", "DOS", "TRES" });

		// Test whenever length of new default statements equals the number of factories
		ArrayList<ArrayList<EntityStatement>> defaultStatements = new ArrayList<ArrayList<EntityStatement>>();

		ArrayList<EntityStatement> defaultStatementOne = new ArrayList<EntityStatement>();
		ArrayList<EntityStatement> defaultStatementTwo = new ArrayList<EntityStatement>();
		ArrayList<EntityStatement> defaultStatementThree = new ArrayList<EntityStatement>();

		defaultStatementOne.add(new EntityStatement("Test Title 1", "Test Description 1", null, null));

		defaultStatementTwo.add(new EntityStatement("Test Title 2", "Test Description 2", null, null));

		defaultStatementThree.add(new EntityStatement("Test Title 3", "Test Description 3", null, null));

		defaultStatements.add(defaultStatementOne);
		defaultStatements.add(defaultStatementTwo);
		defaultStatements.add(defaultStatementThree);

		// System.out.println(factory.getFactoryFromIndex(1));

		factory.setAllDefaultStatements(defaultStatements);

		assertEquals(factory.getFactoryFromIndex(0).getDefaultStatements().get(0).getName(),
				defaultStatementOne.get(0).getName());
		assertEquals(factory.getFactoryFromIndex(1).getDefaultStatements(), defaultStatementTwo);
		assertEquals(factory.getFactoryFromIndex(2).getDefaultStatements(), defaultStatementThree);

		// Test whenever length of new default statements is less than the number of
		// factories
		ArrayList<ArrayList<EntityStatement>> defaultStatements2 = new ArrayList<ArrayList<EntityStatement>>();

		ArrayList<EntityStatement> defaultStatementFour = new ArrayList<EntityStatement>();
		ArrayList<EntityStatement> defaultStatementFive = new ArrayList<EntityStatement>();

		defaultStatementFour.add(new EntityStatement("Test Title 4", "Test Description 4", null, null));

		defaultStatementFive.add(new EntityStatement("Test Title 5", "Test Description 5", null, null));

		defaultStatements2.add(defaultStatementFour);
		defaultStatements2.add(defaultStatementFive);

		factory.setAllDefaultStatements(defaultStatements2);

		assertEquals(factory.getFactoryFromIndex(0).getDefaultStatements(), defaultStatementFour);
		assertEquals(factory.getFactoryFromIndex(1).getDefaultStatements(), defaultStatementFive);
		assertEquals(factory.getFactoryFromIndex(2).getDefaultStatements(), defaultStatementThree);

		// Test whenever length of new default statements is greater than the number of
		// factories
		defaultStatements = new ArrayList<ArrayList<EntityStatement>>();

		ArrayList<EntityStatement> defaultStatementSix = new ArrayList<EntityStatement>();
		ArrayList<EntityStatement> defaultStatementSeven = new ArrayList<EntityStatement>();
		ArrayList<EntityStatement> defaultStatementEight = new ArrayList<EntityStatement>();
		ArrayList<EntityStatement> defaultStatementNine = new ArrayList<EntityStatement>();

		defaultStatementSix.add(new EntityStatement("Test Title 6", "Test Description 6", null, null));

		defaultStatementSeven.add(new EntityStatement("Test Title 7", "Test Description 7", null, null));

		defaultStatementEight.add(new EntityStatement("Test Title 8", "Test Description 8", null, null));

		defaultStatementNine.add(new EntityStatement("Test Title 9", "Test Description 9", null, null));

		defaultStatements.add(defaultStatementSix);
		defaultStatements.add(defaultStatementSeven);
		defaultStatements.add(defaultStatementEight);
		defaultStatements.add(defaultStatementNine);

		factory.setAllDefaultStatements(defaultStatements);

		assertEquals(factory.getFactoryFromIndex(0).getDefaultStatements(), defaultStatementSix);
		assertEquals(factory.getFactoryFromIndex(1).getDefaultStatements(), defaultStatementSeven);
		assertEquals(factory.getFactoryFromIndex(2).getDefaultStatements(), defaultStatementEight);

		// Test whenever length of new default statements is 0
		defaultStatements = new ArrayList<ArrayList<EntityStatement>>();

		factory.setAllDefaultStatements(defaultStatements);

		assertEquals(factory.getFactoryFromIndex(0).getDefaultStatements(), defaultStatementSix);
		assertEquals(factory.getFactoryFromIndex(1).getDefaultStatements(), defaultStatementSeven);
		assertEquals(factory.getFactoryFromIndex(2).getDefaultStatements(), defaultStatementEight);
	}

}
