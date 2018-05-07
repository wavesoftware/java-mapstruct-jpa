package pl.wavesoftware.utils.mapstruct.jpa;

import lombok.RequiredArgsConstructor;
import pl.wavesoftware.utils.mapstruct.jpa.Mappings.MappingsBuilder;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 07.05.18
 */
final class JpaMappingContextProviderImpl {
  CompositeContext provide(Supplier<EntityManager> entityManager,
                           Iterable<MappingProvider<?, ?, ?>> mappingProviders,
                           IdentifierCollector identifierCollector) {
    StoringMappingContext cyclicGraphCtx = new CyclicGraphContext();
    CompositeContextBuilder contextBuilder = CompositeContext.builder();
    Supplier<CompositeContext> contextSupplier = new StoringMappingContextSupplier(contextBuilder);
    MappingsBuilder<CompositeContext> mappingsBuilder = Mappings.builder(CompositeContext.class);
    for (MappingProvider<?, ?, ?> mappingProvider : mappingProviders) {
      mappingsBuilder.addMapping(mappingProvider.provide());
    }
    JpaMappingContextFactory contextFactory = new JpaMappingContextFactoryImpl(entityManager);
    JpaMappingContext jpaContext = contextFactory
      .produce(
        contextSupplier,
        mappingsBuilder.build(),
        identifierCollector
      );

    contextBuilder.addContext(cyclicGraphCtx);
    contextBuilder.addContext(jpaContext);

    return contextSupplier.get();
  }

  @RequiredArgsConstructor
  private static final class StoringMappingContextSupplier implements Supplier<CompositeContext> {
    private final CompositeContextBuilder contextBuilder;
    @Nullable
    private CompositeContext context;

    @Override
    public CompositeContext get() {
      if (context == null) {
        context = contextBuilder.build();
      }
      return context;
    }
  }
}
