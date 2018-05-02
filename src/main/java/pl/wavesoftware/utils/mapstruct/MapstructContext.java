package pl.wavesoftware.utils.mapstruct;

import org.mapstruct.BeforeMapping;
import org.mapstruct.TargetType;

import javax.annotation.Nullable;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 02.05.18
 */
public interface MapstructContext extends StoringMappingContext {

  /**
   * Gets an already mapped instance of targetType for given source object.
   *
   * @param source an input source object
   * @param targetType a target type class
   * @param <T> a type of target type
   * @return an mapper instance if found, null otherwise
   */
  @Nullable
  @BeforeMapping
  <T> T getMappedInstance(Object source,
                          @TargetType Class<T> targetType);

}
