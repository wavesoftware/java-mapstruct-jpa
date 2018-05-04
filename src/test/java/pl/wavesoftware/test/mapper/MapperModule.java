package pl.wavesoftware.test.mapper;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import javassist.util.proxy.ProxyFactory;
import lombok.NoArgsConstructor;
import pl.wavesoftware.eid.exceptions.Eid;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;
import pl.wavesoftware.utils.mapstruct.jpa.IdentifierCollector;
import pl.wavesoftware.utils.mapstruct.jpa.JpaMappingContextFactory;
import pl.wavesoftware.utils.mapstruct.jpa.JpaMappingContextFactoryImpl;
import pl.wavesoftware.utils.mapstruct.jpa.MapStructContextProvider;
import pl.wavesoftware.utils.mapstruct.jpa.MappingProvider;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@NoArgsConstructor
public class MapperModule implements Module {

  private final ExecutorService executor = Executors.newFixedThreadPool(2);

  @Nullable
  private PetMapper petMapper;
  @Nullable
  private OwnerMapper ownerMapper;

  @Override
  public void configure(Binder binder) {
    bindMappingProviders(binder);
  }

  @Provides
  MapperFacade providesMapperFacade(PetMapper petMapper,
                                    MapStructContextProvider<CompositeContext> contextProvider) {
    return new MapperFacadeImpl(petMapper, contextProvider);
  }

  @Provides
  PetMapper providesPetMapper(Provider<OwnerMapper> ownerMapper) {
    PetMapper mapper = tryToExecute(this::createPetMapperProxy, "20180504:130713");
    executor.submit(() -> {
      PetMapperImpl impl = new PetMapperImpl();
      tryToExecute(() -> {
        Field field = PetMapperImpl.class.getDeclaredField("ownerMapper");
        field.setAccessible(true);
        field.set(impl, ownerMapper.get());
      }, "20180504:131237");
      petMapper = impl;
    });
    return mapper;
  }

  @Provides
  OwnerMapper providesOwnerMapper(Provider<PetMapper> petMapper) {
    OwnerMapper mapper = tryToExecute(this::createOwnerMapperProxy, "20180504:131443");
    executor.submit(() -> {
      OwnerMapperImpl impl = new OwnerMapperImpl();
      tryToExecute(() -> {
        Field field = OwnerMapperImpl.class.getDeclaredField("petMapper");
        field.setAccessible(true);
        field.set(impl, petMapper.get());
      }, "20180504:131611");
      ownerMapper = impl;
    });
    return mapper;
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

  private static void bindMappingProviders(Binder binder) {
    TypeLiteral<MappingProvider<?, ?, ?>> type
      = new TypeLiteral<MappingProvider<?, ?, ?>>() {};
    Multibinder<MappingProvider<?, ?, ?>> multibinder = Multibinder
      .newSetBinder(binder, type);
    multibinder.addBinding()
      .to(PetMappingProvider.class);
    multibinder.addBinding()
      .to(OwnerMappingProvider.class);
  }

  private PetMapper createPetMapperProxy() throws
    InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setInterfaces(new Class<?>[]{ PetMapper.class });
    Object proxy = proxyFactory.create(
      new Class<?>[0],
      new Object[0],
      (self, thisMethod, proceed, args) -> {
        if (petMapper != null) {
          return thisMethod.invoke(petMapper, args);
        }
        throw new EidIllegalStateException(new Eid("20180504:130350"));
      });

    return PetMapper.class.cast(proxy);
  }

  private OwnerMapper createOwnerMapperProxy() throws
    InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setInterfaces(new Class<?>[]{ OwnerMapper.class });
    Object proxy = proxyFactory.create(
      new Class<?>[0],
      new Object[0],
      (self, thisMethod, proceed, args) -> {
        if (ownerMapper != null) {
          return thisMethod.invoke(ownerMapper, args);
        }
        throw new EidIllegalStateException(new Eid("20180504:132707"));
      });

    return OwnerMapper.class.cast(proxy);
  }
}
