package pl.wavesoftware.utils.mapstruct.jpa;

import pl.wavesoftware.lang.TriConsumer;

/**
 * @author <a href="krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-02
 */
public abstract class CompositeContextMapping<I, O> extends Mapping<I, O, CompositeContext> {
  protected CompositeContextMapping(Class<I> sourceClass,
                                    Class<O> targetClass) {
    super(sourceClass, targetClass, CompositeContext.class);
  }

  public static <I, O> CompositeContextMapping<I, O> mapperFor(Class<I> inputClass,
                                                               Class<O> outputClass,
                                                               TriConsumer<I, O, CompositeContext> consumer) {
    return new CompositeContextMapping<I, O>(inputClass, outputClass) {
      @Override
      public void accept(I input, O output, CompositeContext context) {
        consumer.accept(input, output, context);
      }
    };
  }
}
