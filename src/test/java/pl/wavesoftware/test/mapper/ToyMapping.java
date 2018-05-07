package pl.wavesoftware.test.mapper;

import pl.wavesoftware.test.entity.Toy;
import pl.wavesoftware.test.jpa.ToyJPA;
import pl.wavesoftware.utils.mapstruct.jpa.AbstractMapping;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 07.05.18
 */
final class ToyMapping extends AbstractMapping<Toy, ToyJPA, CompositeContext> {

  ToyMapping(Class<Toy> sourceClass,
                    Class<ToyJPA> targetClass,
                    Class<CompositeContext> contextClass) {
    super(sourceClass, targetClass, contextClass);
  }

  @Override
  public void accept(Toy toy, ToyJPA jpa, CompositeContext context) {
    throw new UnsupportedOperationException();
  }
}
