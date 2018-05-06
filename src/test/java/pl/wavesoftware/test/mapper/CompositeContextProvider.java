package pl.wavesoftware.test.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.wavesoftware.utils.mapstruct.jpa.AbstractCompositeContextProvider;
import pl.wavesoftware.utils.mapstruct.jpa.IdentifierCollector;
import pl.wavesoftware.utils.mapstruct.jpa.JpaMappingContextFactory;
import pl.wavesoftware.utils.mapstruct.jpa.MappingProvider;

import java.util.Collections;
import java.util.Set;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@RequiredArgsConstructor
final class CompositeContextProvider extends AbstractCompositeContextProvider {

  @Getter
  private final JpaMappingContextFactory jpaMappingContextFactory;
  private final Set<MappingProvider<?, ?, ?>> mappingProviders;
  @Getter
  private final IdentifierCollector identifierCollector;

  @Override
  protected Iterable<MappingProvider> getMappingProviders() {
    return Collections.unmodifiableSet(mappingProviders);
  }

}
