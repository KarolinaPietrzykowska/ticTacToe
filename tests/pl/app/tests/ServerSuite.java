package pl.app.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Klasa służąca do stworzenia określonego zestawu klas testujących, które będą uruchamiać się w określonej kolejności
 * */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ServerMainTest.class,
                ServerStageTest.class,
                ServerServiceTest.class,
                GameTest.class
        })

public class ServerSuite
{

}