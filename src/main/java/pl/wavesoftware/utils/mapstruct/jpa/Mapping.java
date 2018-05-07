package pl.wavesoftware.utils.mapstruct.jpa;

import pl.wavesoftware.lang.TriConsumer;

/**
 * Represents a mapping from input object (typed {@link I}) to target output object (typed {@link O})
 * within specified context (typed {@link C}).
 *
 * @param <I> A type of input (source) object for map from.
 * @param <O> A type of output (target) object for map to.
 * @param <C> A type of context to be used in the mapping.
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
public interface Mapping<I, O, C> extends TriConsumer<I, O, C> {
  /**
   * Gets a source class
   * @return a source class
   */
  Class<I> getSourceClass();

  /**
   * Gets a target class
   * @return a target class
   */
  Class<O> getTargetClass();

  /**
   * Gets a context class
   * @return a context class
   */
  Class<C> getContextClass();
}
