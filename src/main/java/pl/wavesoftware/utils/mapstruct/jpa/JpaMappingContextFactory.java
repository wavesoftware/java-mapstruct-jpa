package pl.wavesoftware.utils.mapstruct.jpa;

import java.util.function.Supplier;


/**
 * This factory produce a {@link JpaMappingContext} and to work it requires:
 *
 * <ul>
 *   <li>Supplier of {@link StoringMappingContext} to handle cycles -
 *    {@link CyclicGraphContext} can be used here</li>
 *   <li>{@link Mappings} object that will provides mapping for given source and target class
 *    - mapping is information how to update existing object (managed entity) with data
 *    from source object</li>
 *   <li>{@link IdentifierCollector} should collect managed entity ID from source object</li>
 * </ul>
 *
 * To simplify configuration consider implementing {@link AbstractJpaContextProvider} and
 * configure your DI container to provide {@link JpaMappingContextFactoryImpl} as a
 * implementation of this factory.
 *
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 25.04.18
 */
public interface JpaMappingContextFactory {
  /**
   * Produce a JPA aware mapping context. I will require couple of dependencies.
   *
   * @param storingMappingContext A context that can store bean instances to get over cycles
   *                              in domain model graph. A good candidate can
   *                              be {@link CyclicGraphContext}.
   * @param mappings              A mapping object that can provide a mapping for given input
   *                              and output object for mapping.
   * @param identifierCollector   A collector of ID that can work on input (source) objects.
   * @return a produces context
   */
  JpaMappingContext produce(Supplier<? extends StoringMappingContext> storingMappingContext,
                            Mappings<?> mappings,
                            IdentifierCollector identifierCollector);
}
