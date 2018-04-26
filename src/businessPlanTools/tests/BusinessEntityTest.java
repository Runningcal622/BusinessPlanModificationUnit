package businessPlanTools.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import Server.*;

import java.util.*;

class BusinessEntityTest
{

	BusinessEntity entity;

	@Test
	void test()
	{
		assertEquals(2, 2);
	}

	@BeforeEach
	void testresetEntityFactory()
	{
		ArrayList<EntityStatement> defaultStatements = new ArrayList<EntityStatement>();
		ArrayList<BusinessEntity> defaultSubentities = new ArrayList<BusinessEntity>();

		entity = new BusinessEntity("Default Title", defaultStatements, defaultSubentities, entity,
				new CentrePlanFactory());
		assertEquals(entity.getEntityTitle(), "Default Title");
		assertEquals(entity.getStatements(), defaultStatements);
		assertEquals(entity.getSubentities(), defaultSubentities);
		Date nd = new Date();
		ArrayList<EntityStatement> statements = new ArrayList<EntityStatement>();
		statements.add(new EntityStatement("name1", "statement1", nd, null));
		statements.add(new EntityStatement("name2", "statement2", nd, null));
		entity.setStatements(statements);
		assertEquals(entity.getStatements(), statements);
		ArrayList<BusinessEntity> subEntities = new ArrayList<BusinessEntity>();
		subEntities.add(new BusinessEntity("title", statements, null, null, null));
		assertEquals(subEntities.get(0).getParentEntity(), null);
		assertEquals(subEntities.get(0).getSubentityFactory(), null);

	}
}
