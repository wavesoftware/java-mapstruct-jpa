package pl.wavesoftware.test.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.wavesoftware.test.entity.Toy;
import pl.wavesoftware.test.jpa.ToyJPA;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Mapper(
  uses = { OwnerMapper.class, PetMapper.class },
  componentModel = "jsr330"
)
public interface ToyMapper {
  Toy map(ToyJPA jpa, @Context CompositeContext context);
  ToyJPA map(Toy toy, @Context CompositeContext context);
  void updateFromToy(Toy toy,
                     @MappingTarget ToyJPA jpa,
                     @Context CompositeContext context);
}
