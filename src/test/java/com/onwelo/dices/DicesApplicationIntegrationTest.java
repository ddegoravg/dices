package com.onwelo.dices;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.onwelo.dices.rollerv1.api.RollerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DicesApplicationIntegrationTest {

    @Autowired
    RollerController rollerController;

    @Test
    void contextLoads() {

        assertTrue(rollerController != null);

    }

    @Test
    public void main() {
        DicesApplication.main(new String[] {});
    }

}
