package js.training.kopring.model.mapper

import js.training.kopring.model.dto.AccountDto
import js.training.kopring.model.dto.SignUpDto
import js.training.kopring.model.entity.Account
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface AccountMapper {

    @Mappings(
        Mapping(source = "email", target = "email"),
        Mapping(source = "name", target = "name"),
        Mapping(source = "phone", target = "phone"),
        Mapping(source = "roleString", target = "roleString"),
    )
    fun toDto(account: Account): AccountDto

    @Mappings(
        Mapping(source = "email", target = "email"),
        Mapping(source = "password", target = "password"),
        Mapping(source = "name", target = "name"),
        Mapping(source = "phone", target = "phone"),
    )
    fun toModel(signUp: SignUpDto): Account
}