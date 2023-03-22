package js.training.kopring.service

import js.training.kopring.config.security.JwtProvider
import js.training.kopring.exception.auth.DuplicateUserException
import js.training.kopring.exception.auth.JwtValidateException
import js.training.kopring.model.dto.AccountDto
import js.training.kopring.model.dto.payload.request.SignInRequest
import js.training.kopring.model.dto.payload.request.SignUpRequest
import js.training.kopring.model.dto.payload.response.BaseResponse
import js.training.kopring.model.dto.payload.response.TokenResponse
import js.training.kopring.model.entity.Account
import js.training.kopring.model.entity.AccountRole
import js.training.kopring.model.entity.RefreshToken
import js.training.kopring.model.entity.Role
import js.training.kopring.model.enums.AuthStatus
import js.training.kopring.model.mapper.AccountMapper
import js.training.kopring.repository.AccountRepository
import js.training.kopring.repository.RefreshTokenRepository
import js.training.kopring.repository.RoleRepository
import org.mapstruct.factory.Mappers
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val roleRepository: RoleRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val authenticationManager: AuthenticationManager,
) {
    private val accountMapper: AccountMapper = Mappers.getMapper(AccountMapper::class.java)

    @Transactional(readOnly = true)
    fun findAll(): List<AccountDto> {
        val accounts = accountRepository.findAll()
        return accounts.map { account -> accountMapper.toDto(account) }.toList()
    }

    @Transactional(readOnly = true)
    fun findByEmail(email: String): AccountDto? {
        val account: Account = accountRepository.findByEmail(email) ?: return AccountDto.EMPTY
        return accountMapper.toDto(account)
    }

    fun existsByEmail(email: String): Boolean {
        return accountRepository.existsByEmail(email)
    }

    fun signIn(request: SignInRequest): BaseResponse<*> {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
        val tokenResponse = jwtProvider.getToken(request.email)

        return BaseResponse(AuthStatus.SIGN_IN_SUCCESS, tokenResponse)
    }

    @Transactional
    fun signUp(request: SignUpRequest): BaseResponse<*> {
        if (this.existsByEmail(request.email)) {
            return BaseResponse.of(DuplicateUserException.EXCEPTION)
        }
        val authority = request.role;
        val password = request.password
        request.password = passwordEncoder.encode(password)

        val account = Account(request)
        val role = roleRepository.findByAuthority(authority) ?: Role(authority)
        account.roles.add(AccountRole(account, role))
        accountRepository.save(account)

        val tokenResponse = jwtProvider.getToken(account.email)

        return BaseResponse(AuthStatus.SIGN_UP_SUCCESS, tokenResponse)
    }

    fun reissue(refreshToken: String): BaseResponse<TokenResponse> {
        val token: RefreshToken =
            refreshTokenRepository.findByToken(refreshToken) ?: throw JwtValidateException.EXCEPTION
        token.ttl = jwtProvider.refreshTokenExpireTime
        refreshTokenRepository.save(token)

        return BaseResponse(AuthStatus.REISSUE_SUCCESS, jwtProvider.getAccessToken(token.email))
    }

    @Transactional
    fun delete(email: String): BaseResponse<*> {
        val account = accountRepository.findByEmail(email) ?: return BaseResponse.of(AuthStatus.USER_NOT_FOUND)
        account.roles.clear()
        accountRepository.delete(account)

        return BaseResponse.of(AuthStatus.DELETE_USER_SUCCESS)
    }
}