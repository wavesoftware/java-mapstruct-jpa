package pl.wavesoftware.utils.mapstruct.jpa;

import javax.persistence.EntityManager;
import java.util.function.Supplier;

/**
 * A base abstract class that can be used as a provider for configured and fully working
 * JPA aware mapping context with update capability. It's designed to be implemented and
 * configured in your DI container to used in your mapper facade to produce new context each
 * time you are doing a mapping.
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-03
 */
public abstract class AbstractJpaContextProvider
  implements MapStructContextProvider<CompositeContext> {

  private final JpaMappingContextProviderImpl provider =
    new JpaMappingContextProviderImpl();

  /**
   * A method to that returns a supplier of JPA's {@link EntityManager} bounded to
   * current transaction context.
   *
   * @return a supplier of current <code>EntityManager</code>
   */
  protected abstract Supplier<EntityManager> getEntityManager();

  /**
   * Returns a collection of mapping providers to be used in mapping. Each mapping provider
   * is for other mapping (for ex.: Pet to PetData etc.).
   *
   * @return a collection of mapping providers
   */
  protected abstract Iterable<MappingProvider<?, ?, ?>> getMappingProviders();

  /**
   * Returns a identifier collector that can fetch an ID to be used to fetch a managed entity
   * from {@link EntityManager}. It will try to fetch the ID from source mapping object, so
   * it must contain some kind of way to provide it.
   *
   * @return a identifier collector
   */
  protected abstract IdentifierCollector getIdentifierCollector();

  @Override
  public CompositeContext createNewContext() {
    return provider.provide(
      getEntityManager(), getMappingProviders(), getIdentifierCollector()
    );
  }
}
