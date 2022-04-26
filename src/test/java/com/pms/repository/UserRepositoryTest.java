package com.pms.repository;
import com.pms.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;


@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@ExtendWith(SpringExtension.class)
@DataMongoTest
class UserRepositoryTest<assertThat> {
    /*
    Database test pattern.
    To test the function of a unit, setup a database full of all the test data you’ll need before running any tests. Once this is done, the tests will:

    1. Create the unit under test
    2. Tell it to use the test database
    3. Call methods on the units

     */

    /*
    Database Framework
     */
    @Autowired
    private UserRepository underTest;
    String email = "amhatre@syr.edu";

    //Given
    /*
    Creating a testing a database; only adding data to the database when needed.
     */
    @BeforeEach
    public void setUp() throws Exception{
        /*
        To maintain consistency we clear the database before every test run-session.
        More information: https://raygun.com/blog/unit-testing-patterns/
         */
        underTest.deleteAll();
        underTest.save(new User(
                "apurva",
                "appu",
                "1234",
                email,
                new ArrayList<>()
        )
        );
    }

    /*
    This checks if the save operation was performed correctly
     */
    @Test
    void shouldNotBeEmpty(){
        assertThat(underTest.findAll()).isNotEmpty();
    }

    /*
    Checks if the the code finds user by email
     */
    @Test
    void userShouldNotBeEmpty(){
        assertThat(underTest.findByEmail(email)).isNotNull();
    }

    /*
    Reverse check for the findByEmail where we input an email which
    is not present in the database.
     */
    @Test
    void userShouldBeEmpty(){
        assertThat(underTest.findByEmail("prof@syr.edu")).isNull();
    }

}