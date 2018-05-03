package pl.wavesoftware.utils.mapstruct;

import lombok.RequiredArgsConstructor;
import pl.wavesoftware.eid.exceptions.Eid;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 25.04.18
 */
public interface Mappings {
  <I, O, C> Mapping<I, O, C> getMapping(Class<I> sourceClass,
                                        Class<O> targetClass);

  static MappingsBuilder builder() {
    return new MappingsBuilderImpl();
  }

  interface MappingsBuilder {
    MappingsBuilder addMapping(Mapping<?, ?, ?> mapping);
    Mappings build();
  }

  final class MappingsBuilderImpl implements MappingsBuilder {
    private final List<Mapping<?, ?, ?>> mappings = new ArrayList<>();

    @Override
    public MappingsBuilder addMapping(Mapping<?, ?, ?> mapping) {
      mappings.add(mapping);
      return this;
    }

    @Override
    public Mappings build() {
      return new MappingsImpl(mappings);
    }

    @RequiredArgsConstructor
    private static final class MappingsImpl implements Mappings {
      private final Iterable<Mapping<?, ?, ?>> mappings;

      @Override
      @SuppressWarnings("unchecked")
      public <I, O, C> Mapping<I, O, C> getMapping(Class<I> sourceClass,
                                                   Class<O> targetClass) {
        for (Mapping<?, ?, ?> mapping : mappings) {
          if (mapping.getSourceClass() == sourceClass && mapping.getTargetClass() == targetClass) {
            return (Mapping<I, O, C>) mapping;
          }
        }
        throw new EidIllegalStateException(
          new Eid("20180425:135245"),
          "Mapping for source class %s and target class %s is not configured!",
          sourceClass.getName(), targetClass.getName()
        );
      }
    }
  }

}
