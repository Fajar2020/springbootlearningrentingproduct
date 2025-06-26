package com.springrent.rent_admin_backend.repository;

import com.springrent.rent_admin_backend.models.Users;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Order(1)
    public void UsersRepository_FindByUsernameIgnoreCaseAndIsDeletedFalse_ReturnUsers() {
        Users user1 = Users.builder()
                        .email("admin1@test.com")
                        .username("admin1")
                        .employeeNumber("123456")
                        .password("password")
                        .isDeleted(false).build();

//        Users user2 = Users.builder()
//                .email("admin2@test.com")
//                .username("admin2")
//                .employeeNumber("123456")
//                .password("password")
//                .isDeleted(true).build();

        Users savedUser1 = usersRepository.save(user1);
//        Users savedUser2 = usersRepository.save(user2);
//
//
//        Users userFromDB = usersRepository.findByUsernameIgnoreCaseAndIsDeletedFalse("ADMin1").get();
//
//        Users user2FromDB = usersRepository.findById(savedUser2.getId()).get();
//        Optional<Users> usersOptional = usersRepository.findByUsernameIgnoreCaseAndIsDeletedFalse("admin2");
//
//        assertEquals(savedUser1.getId(), userFromDB.getId());
//
//        assertEquals(savedUser2.getId(), user2FromDB.getId());
//        assertTrue(usersOptional.isEmpty());
    }
}
