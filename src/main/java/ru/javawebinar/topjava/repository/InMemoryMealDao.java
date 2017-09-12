package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealDao {

  private static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();

  private final AtomicInteger counter = new AtomicInteger(0);
  private final ConcurrentMap<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

  {
    MealsUtil.MEALS.forEach(m -> save(m, 1));
    save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510), 2);
    save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500), 2);
  }

  public Meal save(Meal meal, int userId) {
    Objects.requireNonNull(meal);
    if (meal.isNew()) {
      meal.setId(counter.incrementAndGet());
    }
    Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
    return meals.put(meal.getId(), meal);
  }

  public Collection<Meal> getAll(int userId) {
    Map<Integer, Meal> meals = repository.get(userId);
    return meals.isEmpty() ?
        Collections.emptyList() :
        meals.values().stream().sorted(MEAL_COMPARATOR).collect(Collectors.toList());
  }

  public Meal get(int id, int uesrId) {
    Map<Integer, Meal> meals = repository.get(uesrId);
    return meals == null ? null : meals.get(id);
  }

  public boolean delete(int id, int userId) {
    Map<Integer, Meal> meals = repository.get(userId);
    return meals != null && meals.remove(id) != null;
  }

  public Collection<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
    Objects.requireNonNull(startDate);
    Objects.requireNonNull(endDate);
    return getAll(userId).stream()
        .filter(m -> TimeUtil.isBetween(m.getDateTime(), startDate, endDate))
        .collect(Collectors.toList());
  }
}
