package pl.wavesoftware.utils.mapstruct.jpa;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 25.04.18
 */
@RequiredArgsConstructor
public final class JpaMappingContextFactoryImpl implements JpaMappingContextFactory {
  private final Supplier<EntityManager> entityManager;

  @Override
  public JpaMappingContext produce(Supplier<? extends StoringMappingContext> storingMappingContext,
                                   Mappings mappings,
                                   IdentifierCollector identifierCollector) {
    return new JpaMappingContextImpl(
      entityManager, storingMappingContext, mappings, identifierCollector
    );
  }
}
