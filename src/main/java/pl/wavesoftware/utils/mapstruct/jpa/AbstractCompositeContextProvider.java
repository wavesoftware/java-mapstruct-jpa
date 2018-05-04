package pl.wavesoftware.utils.mapstruct.jpa;

import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author <a href="krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-03
 */
public abstract class AbstractCompositeContextProvider
  implements MapStructContextProvider<CompositeContext> {

  @Override
  public CompositeContext createNewContext() {
    StoringMappingContext cyclicGraphCtx = new CyclicGraphContext();
    CompositeContextBuilder contextBuilder = CompositeContext.builder();
    Supplier<CompositeContext> contextSupplier = new StoringMappingContextSupplier(contextBuilder);
    Mappings.MappingsBuilder mappingsBuilder = Mappings.builder();
    for (MappingProvider<?, ?, ?> mappingProvider : getMappingProviders()) {
      mappingsBuilder.addMapping(mappingProvider.provide());
    }
    JpaMappingContext jpaContext = getJpaMappingContextFactory()
      .produce(
        contextSupplier,
        mappingsBuilder.build(),
        getIdentifierCollector()
      );

    contextBuilder.addContext(cyclicGraphCtx);
    contextBuilder.addContext(jpaContext);

    return contextSupplier.get();
  }

  protected abstract JpaMappingContextFactory getJpaMappingContextFactory();
  protected abstract Iterable<MappingProvider> getMappingProviders();
  protected abstract IdentifierCollector getIdentifierCollector();

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
