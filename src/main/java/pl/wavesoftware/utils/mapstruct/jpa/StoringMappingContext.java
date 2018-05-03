package pl.wavesoftware.utils.mapstruct.jpa;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

/**
 * Represents a context that can store created values.
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 02.05.18
 */
public interface StoringMappingContext extends MappingContext {
  /**
   * Invoking this method stores a passed target object as a mapped
   * instance for input source object.
   *
   * @param source a source object to set mapped instance to
   * @param target a mapped instance object to set
   */
  @BeforeMapping
  void storeMappedInstance(Object source, @MappingTarget Object target);
}
