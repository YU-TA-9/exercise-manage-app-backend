package com.example.yuta9.exercisemanagebackend.enums;

/** カテゴリー定義 */
public enum Category {
  RUNNING(1),
  LEARNING(2),
  READING(3);

  private int id;

  private Category(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public static Category getById(int id) {
    for (Category category : Category.values()) {
      if (category.getId() == id) {
        return category;
      }
    }
    return null;
  }
}
