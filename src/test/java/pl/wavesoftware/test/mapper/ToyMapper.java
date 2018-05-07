package pl.wavesoftware.test.mapper;

import org.mapstruct.Context;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
  @Mapping(target = "reference", ignore = true)
  Toy map(ToyJPA jpa, @Context CompositeContext context);
  @Mapping(target = "id", ignore = true)
  ToyJPA map(Toy toy, @Context CompositeContext context);
  @InheritConfiguration
  void updateFromToy(Toy toy,
                     @MappingTarget ToyJPA jpa,
                     @Context CompositeContext context);
}
