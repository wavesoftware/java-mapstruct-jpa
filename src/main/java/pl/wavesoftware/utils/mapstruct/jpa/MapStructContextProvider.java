package pl.wavesoftware.utils.mapstruct.jpa;

/**
 * Represents a provider for MapStruct compatible context.
 *
 * @param <C> A type of context that must be compatible with MapStruct.
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-03
 */
public interface MapStructContextProvider<C extends MapStructContext> {
  /**
   * Creates a new context that is compatible with MapStruct.
   * @return a created context
   */
  C createNewContext();
}
