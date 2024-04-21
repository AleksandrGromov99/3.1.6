package org.example;

import org.example.configuration.Config;
import org.example.entities.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        Communication communication =
                context.getBean("communication", Communication.class);

        StringBuilder result = new StringBuilder();
        communication.getAllUsers();

        User user = new User(3L, "James", "Brown", (byte) 5);
        result.append(communication.saveUser(user).getBody());
        user.setName("Thomas");
        user.setLastname("Shelby");
        user.setId(3L);
        result.append(communication.updateUser(3L, user));
        result.append(communication.deleteUser(3L));
    }
}
