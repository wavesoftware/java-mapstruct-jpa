package pl.wavesoftware.utils.mapstruct.jpa.collection;

import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 17.04.18
 */
@RequiredArgsConstructor
public final class UninitializedList<T> implements List<T> {

  private final Class<?> type;

  @Override
  public int size() {
    throw newLazyInitializationException();
  }

  @Override
  public boolean isEmpty() {
    throw newLazyInitializationException();
  }

  @Override
  public boolean contains(Object o) {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public Iterator<T> iterator() {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public Object[] toArray() {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public <T1> T1[] toArray(T1[] a) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean add(T t) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean remove(Object o) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw newLazyInitializationException();
  }

  @Override
  public void clear() {
    throw newLazyInitializationException();
  }

  @Override
  public T get(int index) {
    throw newLazyInitializationException();
  }

  @Override
  public T set(int index, T element) {
    throw newLazyInitializationException();
  }

  @Override
  public void add(int index, T element) {
    throw newLazyInitializationException();
  }

  @Override
  public T remove(int index) {
    throw newLazyInitializationException();
  }

  @Override
  public int indexOf(Object o) {
    throw newLazyInitializationException();
  }

  @Override
  public int lastIndexOf(Object o) {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public ListIterator<T> listIterator() {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public ListIterator<T> listIterator(int index) {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public List<T> subList(int fromIndex, int toIndex) {
    throw newLazyInitializationException();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "<" + type.getSimpleName() + ">";
  }

  private RuntimeException newLazyInitializationException() {
    return new LazyInitializationException(
      "Trying to use uninitialized collection for type: List<"
        + type.getSimpleName()
        + ">. You need to fetch this collection before using it, for ex. using " +
        "JOIN FETCH in JPQL. This exception prevents lazy loading n+1 problem."
    );
  }
}
