package pl.wavesoftware.utils.mapstruct.jpa;

/**
 * Represents a mapping provider that can provide a mapping for given parameters.
 *
 * @param <I> A type of input (source) object for map from.
 * @param <O> A type of output (target) object for map to.
 * @param <C> A type of context to be used in the mapping.
 *
 * @see Mapping
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 26.04.18
 */
public interface MappingProvider<I, O, C> {
  /**
   * Provides a mapping
   *
   * @return a mapping
   */
  Mapping<I, O, C> provide();
}
