package js.training.kopring.controller

import js.training.kopring.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}