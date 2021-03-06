package pl.wavesoftware.utils.mapstruct.jpa.collection;

import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * An uninitialized map that will throw an exception on every method called
 * except {@link #toString()}.
 *
 * @param <K> a type of keys
 * @param <V> a type of value
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-03
 */
@RequiredArgsConstructor
public final class UninitializedMap<K, V> implements Map<K, V>, Uninitialized {

  private final Class<?> keyType;
  private final Class<?> valueType;

  @Override
  public int size() {
    throw newLazyInitializationException();
  }

  @Override
  public boolean isEmpty() {
    throw newLazyInitializationException();
  }

  @Override
  public boolean containsKey(Object key) {
    throw newLazyInitializationException();
  }

  @Override
  public boolean containsValue(Object value) {
    throw newLazyInitializationException();
  }

  @Override
  public V get(Object key) {
    throw newLazyInitializationException();
  }

  @Override
  public V put(K key, V value) {
    throw newLazyInitializationException();
  }

  @Override
  public V remove(Object key) {
    throw newLazyInitializationException();
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    throw newLazyInitializationException();
  }

  @Override
  public void clear() {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public Set<K> keySet() {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public Collection<V> values() {
    throw newLazyInitializationException();
  }

  @Override
  @Nonnull
  public Set<Entry<K, V>> entrySet() {
    throw newLazyInitializationException();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
      + "<" + keyType.getSimpleName() + ","
      + valueType.getSimpleName() + ">";
  }

  private RuntimeException newLazyInitializationException() {
    return new LazyInitializationException(
      "Trying to use uninitialized collection for type: Map<"
        + keyType.getSimpleName() + ","
        + valueType.getSimpleName()
        + ">. You need to fetch this collection before using it, for ex. using " +
        "JOIN FETCH in JPQL. This exception prevents lazy loading n+1 problem."
    );
  }
}
