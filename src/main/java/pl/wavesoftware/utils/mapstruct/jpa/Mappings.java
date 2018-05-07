package pl.wavesoftware.utils.mapstruct.jpa;

/**
 * Mappings represents a set of {@link Mapping}'s with a method to search for specific one.
 *
 * @param <C> a type of context that will be used for mapping
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 25.04.18
 */
public interface Mappings<C> {

  /**
   * Searches for a AbstractMapping for given source and target class. Source class is a class
   * that we map from and target class is a class that we map to.
   *
   * @param sourceClass a source class to map from
   * @param targetClass a target class to map to
   * @param <S>         a type of source class
   * @param <T>         a type of target class
   * @return  a AbstractMapping object for given configuration. If mapping is not found this
   *          method will fail with runtime exception as an indication of configuration error.
   */
  <S, T> Mapping<S,T,C> getMapping(Class<S> sourceClass,
                                   Class<T> targetClass);

  /**
   * Returns a builder for Mappings.
   *
   * @param contextClass a class of a context
   * @param <C>          a context type used for mappings
   * @return             a builder interface
   */
  static <C> MappingsBuilder<C> builder(Class<C> contextClass) {
    return new MappingsBuilderImpl<>(contextClass);
  }

  /**
   * A builder interface for {@link Mappings} class.
   *
   * @param <C> a type of context that will be used for mapping
   */
  interface MappingsBuilder<C> {
    /**
     * Adds a mapping to the builder.
     * <p>
     * There is no type checking here, because it's done in runtime, while getting proper mapping.
     *
     * @param mapping a mapping to add
     */
    void addMapping(Mapping<?,?,?> mapping);

    /**
     * Will build a Mappings class
     *
     * @return a built Mappings
     */
    Mappings<C> build();
  }

}
