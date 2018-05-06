package pl.wavesoftware.utils.mapstruct.jpa;

import java.util.function.Supplier;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 25.04.18
 */
public interface JpaMappingContextFactory {
  JpaMappingContext produce(Supplier<? extends StoringMappingContext> storingMappingContext,
                            Mappings mappings,
                            IdentifierCollector identifierCollector);
}
