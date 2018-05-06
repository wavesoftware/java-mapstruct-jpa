package pl.wavesoftware.utils.mapstruct.jpa.collection;

import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author <a href="krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-03
 */
@RequiredArgsConstructor
public final class UninitializedSet<E> implements Set<E> {

  private final Class<?> type;

  @Override
  public boolean isEmpty() {
    throw newLazyInitializationException();
  }

  @Override
  public int size() {
    throw newLazyInitializationException();
  }

  @Override
  public boolean contains(Object o) {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public Iterator<E> iterator() {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public Object[] toArray() {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public <T> T[] toArray(T[] a) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean add(E e) {
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
  public boolean addAll(Collection<? extends E> c) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw newLazyInitializationException();
  }

  @Override
  public void clear() {
    throw newLazyInitializationException();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "<" + type.getSimpleName() + ">";
  }

  private RuntimeException newLazyInitializationException() {
    return new LazyInitializationException(
      "Trying to use uninitialized collection for type: Set<"
        + type.getSimpleName()
        + ">. You need to fetch this collection before using it, for ex. using " +
        "JOIN FETCH in JPQL. This exception prevents lazy loading n+1 problem."
    );
  }
}
