package pl.wavesoftware.utils.mapstruct.jpa;

import lombok.RequiredArgsConstructor;
import pl.wavesoftware.eid.exceptions.Eid;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.utils.mapstruct.jpa.Mappings.MappingsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-03
 */
final class MappingsBuilderImpl implements MappingsBuilder {
  private final List<Mapping<?,?,?>> mappings = new ArrayList<>();

  @Override
  public void addMapping(Mapping<?,?,?> mapping) {
    mappings.add(mapping);
  }

  @Override
  public Mappings build() {
    return new MappingsImpl(mappings);
  }

  @RequiredArgsConstructor
  private static final class MappingsImpl implements Mappings {
    private final Iterable<Mapping<?,?,?>> mappings;

    @Override
    @SuppressWarnings("unchecked")
    public <I, O, C> Mapping<I,O,C> getMapping(Class<I> sourceClass,
                                               Class<O> targetClass) {
      for (Mapping<?,?,?> mapping : mappings) {
        if (mapping.getSourceClass() == sourceClass && mapping.getTargetClass() == targetClass) {
          return (Mapping<I, O, C>) mapping;
        }
      }
      throw new EidIllegalStateException(
        new Eid("20180425:135245"),
        "AbstractMapping for source class %s and target class %s is not configured!",
        sourceClass.getName(), targetClass.getName()
      );
    }
  }
}
