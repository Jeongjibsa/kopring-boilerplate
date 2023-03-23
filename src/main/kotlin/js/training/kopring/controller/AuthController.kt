package js.training.kopring.controller

import jakarta.validation.Valid
import js.training.kopring.model.dto.payload.request.SignInRequest
import js.training.kopring.model.dto.payload.request.SignUpRequest
import js.training.kopring.model.dto.payload.request.TokenRequest
import js.training.kopring.model.dto.payload.response.BaseResponse
import js.training.kopring.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val accountService: AccountService,
) {

    @PostMapping("/sign_in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun signIn(@RequestBody @Valid request: SignInRequest): BaseResponse<*> {
        return accountService.signIn(request)
    }

    @PostMapping("/sign_up")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody @Valid request: SignUpRequest): BaseResponse<*> {
        return accountService.signUp(request)
    }

    @PostMapping("/reissue")
    fun refreshToken(@RequestBody @Valid refreshToken: TokenRequest): BaseResponse<*> {
        return accountService.reissue(refreshToken)
    }
}