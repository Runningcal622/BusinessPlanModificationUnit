package businessPlanTools.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import Server.*;

import java.util.*;

public class EntityStatementTest
{

	EntityStatement test;

	@Test
	void test()
	{
		assertEquals(2, 2);
	}

	@BeforeEach
	void resetEntityStatementTest()
	{
		Date d = new Date();
		EntityStatement e1 = new EntityStatement("", "", d, null);
		e1.setName("hahah");
		assertEquals(e1.getName(), "hahah");
		e1.setStatement("this is a statement");
		assertEquals(e1.getStatement(), "this is a statement");
		Date d2 = new Date();
		e1.setDate(d2);
		assertEquals(e1.getDate(), d2);
		EntityStatement e2 = new EntityStatement("xxx", "ccc", d2, null);
		e1.setOldStatement(e2);
		assertEquals(e1.getOldStatement(), e2);

	}

	void testConstructor()
	{

	}

	public static void main(String[] Args)
	{
		BusinessEntityTest test = new BusinessEntityTest();
		test.test();

	}

}
