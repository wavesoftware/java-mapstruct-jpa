package pl.wavesoftware.utils.mapstruct.jpa;

import pl.wavesoftware.lang.TriConsumer;

/**
 * An abstract class that can be extended to provide logic in how to apply data from
 * {@link I} input object to an target {@link O} output object. It uses {@link CompositeContext}
 * as a MapStruct context.
 *
 * <p>
 * It's designed to be used easily with
 * <a href="http://mapstruct.org/documentation/stable/reference/html/#updating-bean-instances">
 *     MapStruct update methods</a>.
 * <pre>
 * &#064;Service
 * &#064;RequiredArgsConstructor
 * final class PetCompositeContextMapping extends AbstractCompositeContextMapping&lt;Pet,PetData&gt; {
 *   private final PetMapper petMapper;
 *   &#064;Override
 *   public void accept(Pet pet, PetData data, CompositeContext context) {
 *     petMapper.updateFromPet(pet, data, context);
 *   }
 * }
 * </pre>
 *
 * There is also a convenience method {@link #mappingFor(Class, Class, TriConsumer)} which can be
 * used to create mapping easily with {@link AbstractJpaContextProvider}:
 *
 * <br>
 * <pre>
 * &#064;RequiredArgsConstructor
 * final class OwnerMappingProvider implements MappingProvider&lt;Owner, OwnerJPA, CompositeContext&gt; {
 *   private final OwnerMapper ownerMapper;
 *
 *   &#064;Override
 *   public Mapping&lt;Owner, OwnerJPA, CompositeContext&gt; provide() {
 *     return AbstractCompositeContextMapping.mappingFor(
 *       Owner.class, OwnerJPA.class,
 *       ownerMapper::updateFromOwner
 *     );
 *   }
 * }
 * </pre>
 *
 * @param <I> a type of input object to map from
 * @param <O> a type of output object to map to
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-02
 */
public abstract class AbstractCompositeContextMapping<I, O>
  extends AbstractMapping<I, O, CompositeContext> {

  protected AbstractCompositeContextMapping(Class<I> sourceClass,
                                            Class<O> targetClass) {
    super(sourceClass, targetClass, CompositeContext.class);
  }

  /**
   * A convenience method which can be used to create mapping easily with
   * {@link AbstractJpaContextProvider}.
   *
   * @param inputClass  a class of an input object
   * @param outputClass a class of an output object
   * @param consumer    a consumer of 3 values: input object, output object
   *                    and {@link CompositeContext} object
   * @param <I> a type of input object to map from
   * @param <O> a type of output object to map to
   * @return a mapping for given values
   */
  public static <I, O> Mapping<I, O, CompositeContext> mappingFor(
    Class<I> inputClass,
    Class<O> outputClass,
    TriConsumer<I, O, CompositeContext> consumer) {

    return new AbstractCompositeContextMapping<I, O>(inputClass, outputClass) {
      @Override
      public void accept(I input, O output, CompositeContext context) {
        consumer.accept(input, output, context);
      }
    };
  }
}
