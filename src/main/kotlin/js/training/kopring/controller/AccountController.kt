package js.training.kopring.controller

import js.training.kopring.model.dto.payload.response.BaseResponse
import js.training.kopring.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("/me")
    fun me(): ResponseEntity<*> {
        return ResponseEntity.ok(accountService.findByEmail(SecurityContextHolder.getContext().authentication.name))
    }

    @GetMapping("/{email}")
    fun get(@PathVariable(name = "email") email: String): ResponseEntity<*> {
        return ResponseEntity.ok(accountService.findByEmail(email))
    }

    @DeleteMapping("/{email}")
    fun delete(@PathVariable(name = "email") email: String): BaseResponse<*> {
        return accountService.delete(email)
    }
}