package pl.wavesoftware.test.mapper;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import javax.inject.Provider;
import java.lang.reflect.Field;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
final class GeneratedMappersModule implements Module {

  private final Provider<PetMapper> petMapper = new HoldingProvider<>(PetMapperImpl::new);
  private final Provider<OwnerMapper> ownerMapper = new HoldingProvider<>(OwnerMapperImpl::new);
  private final Provider<ToyMapper> toyMapper = new HoldingProvider<>(ToyMapperImpl::new);

  @Override
  public void configure(Binder binder) {
    /*

      CAUTION!

      There are some static object creation which is required to simulate jsr-330
      containers like Spring and JEE. If ever MapStruct provides support for Guice
      and Dagger2 container model, those lines can be probably simplified

    */
  }

  @Provides
  PetMapper providesPetMapper() throws NoSuchFieldException, IllegalAccessException {
    PetMapper mapper = petMapper.get();
    setField(mapper, "ownerMapper", ownerMapper);
    setField(mapper, "toyMapper", toyMapper);
    return mapper;
  }

  @Provides
  OwnerMapper providesOwnerMapper() throws NoSuchFieldException, IllegalAccessException {
    OwnerMapper mapper = ownerMapper.get();
    setField(mapper, "petMapper", petMapper);
    return mapper;
  }

  @Provides
  ToyMapper providesToyMapper() {
    return toyMapper.get();
  }

  private static void setField(Object mapper, String fieldName, Provider<?> provider)
    throws NoSuchFieldException, IllegalAccessException {
    Field field = mapper.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(mapper, provider.get());
  }

  @RequiredArgsConstructor
  private static final class HoldingProvider<T> implements Provider<T> {

    private final Supplier<T> supplier;
    @Nullable
    private T instance;

    @Override
    public T get() {
      if (instance == null) {
        instance = supplier.get();
      }
      return instance;
    }
  }
}
