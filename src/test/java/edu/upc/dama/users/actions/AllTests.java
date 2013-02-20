package edu.upc.dama.users.actions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GetUserActionTest.class, InactivateUserActionTest.class,
		SetUserActionTest.class, SignUpActionTest.class })
public class AllTests {

}
