package pl.wavesoftware.test.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.wavesoftware.utils.mapstruct.jpa.AbstractJpaContextProvider;
import pl.wavesoftware.utils.mapstruct.jpa.IdentifierCollector;
import pl.wavesoftware.utils.mapstruct.jpa.MappingProvider;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@RequiredArgsConstructor
final class JpaContextProvider extends AbstractJpaContextProvider {

  @Getter
  private final Supplier<EntityManager> entityManager;
  private final Set<MappingProvider<?, ?, ?>> mappingProviders;
  @Getter
  private final IdentifierCollector identifierCollector;

  @Override
  protected Iterable<MappingProvider<?, ?, ?>> getMappingProviders() {
    return Collections.unmodifiableSet(mappingProviders);
  }

}
