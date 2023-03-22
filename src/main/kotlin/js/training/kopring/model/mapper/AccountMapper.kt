package js.training.kopring.model.mapper

import js.training.kopring.model.dto.AccountDto
import js.training.kopring.model.entity.Account
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring", uses = [LazyLoadingMappingConfig::class])
interface AccountMapper {

    @Mappings(
        Mapping(source = "email", target = "email"),
        Mapping(source = "name", target = "name"),
        Mapping(source = "phone", target = "phone"),
        Mapping(source = "roleString", target = "roleString"),
    )
    fun toDto(account: Account): AccountDto
}