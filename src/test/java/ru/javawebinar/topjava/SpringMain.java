package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
  public static void main(String[] args) {
/*
    ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    System.out.println("Bean defifnition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//    UserRepository userRepository = (UserRepository) appCtx.getBean("mockUserRepository");
    UserRepository userRepository = appCtx.getBean(UserRepository.class);
    userRepository.getAll();

    UserService userService = appCtx.getBean(UserService.class);
    userRepository.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

    appCtx.close();
    */

    try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/mock.xml")) {
      System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
      AdminRestController adminRestController = appCtx.getBean(AdminRestController.class);
      adminRestController.create(new User(null, "username", "email", "password", Role.ROLE_ADMIN));
      System.out.println();

      MealRestController mealController = appCtx.getBean(MealRestController.class);
      List<MealWithExceed> filteredMealWithExceeded =
          mealController.getBetween(
              LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
              LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
      filteredMealWithExceeded.forEach(System.out::println);
    }
  }
}
