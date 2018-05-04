package pl.wavesoftware.test.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.wavesoftware.test.entity.Owner;
import pl.wavesoftware.test.jpa.OwnerJPA;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Mapper(
  uses = PetMapper.class,
  componentModel = "jsr330"
)
interface OwnerMapper {
  Owner map(OwnerJPA jpa, @Context CompositeContext context);
  OwnerJPA map(Owner owner, @Context CompositeContext context);
  void updateFromOwner(Owner owner,
                       @MappingTarget OwnerJPA jpa,
                       @Context CompositeContext context);

  @AfterMapping
  default void after(OwnerJPA ownerJPA, @MappingTarget Owner owner) {
    owner.setReference(ownerJPA.getId());
    owner.setName(ownerJPA.getName() + " " + ownerJPA.getSurname());
  }

  @AfterMapping
  default void after(Owner owner, @MappingTarget OwnerJPA ownerJPA) {
    ownerJPA.setName(owner.getName().split(" ")[0]);
    ownerJPA.setSurname(owner.getName().split(" ")[1]);
  }
}
