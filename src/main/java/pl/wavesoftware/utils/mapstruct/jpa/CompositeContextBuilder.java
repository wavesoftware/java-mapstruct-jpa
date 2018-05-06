package pl.wavesoftware.utils.mapstruct.jpa;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
public interface CompositeContextBuilder {
  CompositeContextBuilder addContext(MappingContext... mappingContexts);
  CompositeContext build();
}
