package com.company;

import java.io.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * BestFriendsArrayList.
 * BestFriendsArrayList stores objects of T type and has few comfortable methods to work
 * with stored objects.
 *
 * @param <T> type of stored objects
 * @author BestFriends_IntegrityUnit_id_3
 * @version 1.0
 * @since 11.02.2020
 */
public class BestFriendsArrayList<T> implements Serializable, Cloneable {
  /**
   * serialVersionUID for Serialization.
   */
  private static final long serialVersionUID = 2283221488L;
  /**
   * Array of T type objects.
   */
  private T[] objects;
  /**
   * actualSize stores actual quantity of objects in array.
   */
  private Integer actualSize;

  /**
   * Default constructor that initializes array size by default value of 10, and sets
   * actual size to 0.
   */
  public BestFriendsArrayList() {
    this.objects = (T[]) new Object[10];
    actualSize = 0;
  }

  /**
   * Constructor that sets the inputted array as a base of new ArrayList.
   *
   * @param objects array of T type objects.
   */
  public BestFriendsArrayList(T[] objects) {
    this.objects = objects.clone();
    this.actualSize = objects.length;
  }

  /**
   * Constructor that sets the inputted collection as a base of new ArrayList.
   *
   * @param collection collection of T type objects.
   */
  public BestFriendsArrayList(Collection<T> collection) {
    this.objects = (T[]) collection.toArray();
    this.actualSize = collection.size();
  }

  /**
   * Method that adds an object to a list.
   * If list has reached it defined capacity, new one is created with size
   * that is bigger than previous in 1.5 times.
   *
   * @param object object of T type, that need to be added to list.
   */
  public void add(T object) {
    if (actualSize + 1 > objects.length) {
      T[] newArr = (T[]) new Object[(int) (objects.length * 1.5)];
      System.arraycopy(objects, 0, newArr, 0, actualSize);
      objects = newArr.clone();
      objects[actualSize] = object;
      actualSize++;
    } else {
      objects[actualSize] = object;
      actualSize++;
    }
  }

  /**
   * Method that displays all the objects in the console.
   */
  public void displayObjects() {
    for (int i = 0; i < actualSize; i++) {
      System.out.println(objects[i]);
    }
  }

  /**
   * Method that returns actual size of ArrayList.
   *
   * @return Integer value of actual size of ArrayList.
   */
  public Integer size() {
    return actualSize;
  }

  /**
   * Method that sorts the ArrayList.
   *
   * @param comparator shows the way that List will be sorted.
   */
  public void sort(Comparator<T> comparator) {
    for (int i = 0; i < actualSize; i++) {
      for (int j = 0; j < actualSize - 1; j++) {
        if (comparator.compare(objects[j], objects[j + 1]) > 0) {
          T temp = objects[j];
          objects[j] = objects[j + 1];
          objects[j + 1] = temp;
        }
      }
    }
  }

  /**
   * Method that removes some object from ArrayList.
   *
   * @param object that need to be removed.
   */
  public void removeObject(T object) {
    for (int i = 0; i < actualSize; i++) {
      if (objects[i].equals(object)) {
        T[] newArr = (T[]) new Object[actualSize - 1];
        System.arraycopy(objects, 0, newArr, 0, i);
        System.arraycopy(objects, i + 1, newArr, i, actualSize - i - 1);
        objects = newArr;
        actualSize--;
        return;
      }
    }
  }

  /**
   * Method that returns object on specific position.
   *
   * @param index position of the object.
   * @return object of T type on index position.
   */
  public T get(int index) {
    if (index > actualSize) {
      throw new IndexOutOfBoundsException("List out of bounds: " + index + " of " + actualSize);
    } else if (index < 0) {
      throw new IllegalArgumentException("Index must be greater than zero");
    } else {
      return objects[index];
    }
  }

  /**
   * Method that filters ArrayList depending on condition.
   *
   * @param condition shows how to filter an ArrayList.
   */
  public void filterObjects(Predicate<T> condition) {
    for (int i = 0; i < actualSize; i++) {
      if (!condition.test(objects[i])) {
        removeObject(objects[i]);
        i--;
      }
    }
  }

  /**
   * Method that returns last index of T type object in List.
   *
   * @param object that last index of which should be found.
   * @return last index in the list of the specific object.
   */
  public Integer lastIndexOfObject(T object) {
    for (int i = actualSize - 1; i != -1; i--) {
      if (object.equals(objects[i])) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Method that returns first index of T type object in List.
   *
   * @param object that first index of which should be found.
   * @return first index in the list of the specific object.
   */
  public Integer firstIndexOfObject(T object) {
    for (int i = 0; i < actualSize; i++) {
      if (object.equals(objects[i])) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Method that serialize list in a specific file.
   *
   * @param fileNameWithoutExtension file name that shouldn`t contain file Extension.
   */
  public void serializeList(String fileNameWithoutExtension) {
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
        new FileOutputStream(new File(fileNameWithoutExtension + ".ser")))) {
      objectOutputStream.writeObject(objects);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method that deserialize list from a specific file.
   *
   * @param fileNameWithoutExtension file name that shouldn`t contain file Extension.
   */
  public void getSerializedList(String fileNameWithoutExtension) {
    try (ObjectInputStream objectInputStream = new ObjectInputStream(
        new FileInputStream(new File(fileNameWithoutExtension + ".ser")))) {
      this.objects = (T[]) objectInputStream.readObject();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
