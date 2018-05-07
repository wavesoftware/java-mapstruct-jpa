package pl.wavesoftware.utils.mapstruct.jpa;

/**
 * This is a builder for composite context.
 *
 * @see CompositeContext
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
public interface CompositeContextBuilder {
  /**
   * Adds context to the builder.
   *
   * @param mappingContexts A mapping contexts to be added to builder and then created
   *                        the composite context from it.
   */
  void addContext(MappingContext... mappingContexts);

  /**
   * Builds a composite context from provided set of other mapping contexts.
   *
   * @return a builded instance of composite context
   */
  CompositeContext build();
}
