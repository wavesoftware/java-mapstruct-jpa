package pl.wavesoftware.utils.mapstruct.jpa;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 26.04.18
 */
public interface MappingProvider<I, O, C> {
  Mapping<I, O, C> provide();
}
