package pl.wavesoftware.utils.mapstruct.jpa;

/**
 * Mappings represents a set of {@link Mapping}'s with a method to search for specific one.
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 25.04.18
 */
public interface Mappings {

  /**
   * Searches for a AbstractMapping for given source and target class. Source class is a class
   * that we map from and target class is a class that we map to.
   *
   * @param sourceClass a source class to map from
   * @param targetClass a target class to map to
   * @param <S> a type of source class
   * @param <T> a type of target class
   * @param <C> a type of context that will be used for mapping
   * @return a AbstractMapping object for given configuration. If mapping is not found this method will
   * fail with runtime exception as an indication of configuration error.
   */
  <S, T, C> Mapping<S,T,C> getMapping(Class<S> sourceClass,
                                      Class<T> targetClass);

  /**
   * Returns a builder for Mappings.
   *
   * @return a builder interface
   */
  static MappingsBuilder builder() {
    return new MappingsBuilderImpl();
  }

  /**
   * A builder interface for {@link Mappings} class.
   */
  interface MappingsBuilder {
    /**
     * Adds a mapping to the builder
     *
     * @param mapping a mapping to add
     */
    void addMapping(Mapping<?,?,?> mapping);

    /**
     * Will build a Mappings class
     *
     * @return a builded Mappings
     */
    Mappings build();
  }

}
