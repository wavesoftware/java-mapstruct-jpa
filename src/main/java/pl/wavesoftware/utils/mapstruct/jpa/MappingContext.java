package pl.wavesoftware.utils.mapstruct.jpa;

import java.util.Optional;

/**
 * A mapping context represents a basic context that can be used as a
 * part of {@link MapStructContext}.
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 02.05.18
 */
public interface MappingContext {

  /**
   * Gets an already mapped instance of targetType for given source object.
   *
   * @param source an input source object
   * @param targetType a target type class
   * @param <T> a type of target type
   * @return an optional mapper instance
   */
  <T> Optional<T> getMappedInstanceOptional(Object source, Class<T> targetType);
}
