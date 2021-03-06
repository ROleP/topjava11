package ru.javawebinar.topjava.model;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
public class BaseEntity {
  public static final int START_SEQ = 100000;

  @Id
  @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
  protected Integer id;

  protected BaseEntity() {
  }

  public BaseEntity(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public boolean isNew() {
    return this.id == null;
  }

  @Override
  public String toString() {
    return String.format("Entity %s (%s)", getClass().getName(), getId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseEntity that = (BaseEntity) o;
    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id == null ? 0 : id;
  }
}
