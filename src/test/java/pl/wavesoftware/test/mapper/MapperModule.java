package pl.wavesoftware.test.mapper;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import lombok.NoArgsConstructor;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;
import pl.wavesoftware.utils.mapstruct.jpa.IdentifierCollector;
import pl.wavesoftware.utils.mapstruct.jpa.JpaMappingContextFactory;
import pl.wavesoftware.utils.mapstruct.jpa.JpaMappingContextFactoryImpl;
import pl.wavesoftware.utils.mapstruct.jpa.MapStructContextProvider;
import pl.wavesoftware.utils.mapstruct.jpa.MappingProvider;

import javax.persistence.EntityManager;
import java.util.Set;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@NoArgsConstructor
public class MapperModule implements Module {

  @Override
  public void configure(Binder binder) {
    try {
      binder.install(new GeneratedMappersModule());
      bindMappingProviders(binder);
    } catch (NoSuchMethodException e) {
      binder.addError(e);
    }
  }

  @Provides
  MapperFacade providesMapperFacade(PetMapper petMapper,
                                    MapStructContextProvider<CompositeContext> contextProvider) {
    return new MapperFacadeImpl(
      petMapper,
      contextProvider
    );
  }

  @Provides
  IdentifierCollector providesIdentifierCollector() {
    return new IdentifierCollectorImpl();
  }

  @Provides
  JpaMappingContextFactory providesJpaMappingContextFactory(EntityManager entityManager) {
    return new JpaMappingContextFactoryImpl(() -> entityManager);
  }

  @Provides
  MapStructContextProvider<CompositeContext> providesContextProvider(
    JpaMappingContextFactory jpaMappingContextFactory,
    Set<MappingProvider<?, ?, ?>> mappingProviders,
    IdentifierCollector identifierCollector) {

    return new CompositeContextProvider(
      jpaMappingContextFactory, mappingProviders, identifierCollector
    );
  }

  private static void bindMappingProviders(Binder binder) throws NoSuchMethodException {
    TypeLiteral<MappingProvider<?, ?, ?>> type
      = new TypeLiteral<MappingProvider<?, ?, ?>>() {};
    Multibinder<MappingProvider<?, ?, ?>> multibinder = Multibinder
      .newSetBinder(binder, type);
    multibinder.addBinding()
      .toConstructor(PetMappingProvider.class.getConstructor(PetMapper.class));
    multibinder.addBinding()
      .toConstructor(OwnerMappingProvider.class.getConstructor(OwnerMapper.class));
  }
}
