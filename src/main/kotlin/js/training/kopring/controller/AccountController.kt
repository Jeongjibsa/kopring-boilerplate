package js.training.kopring.controller

import js.training.kopring.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("")
    fun getAccounts(): ResponseEntity<Any> {
        return ResponseEntity.ok(accountService.findAll())
    }

    @GetMapping("/{email}")
    fun getAccount(@PathVariable(name = "email") email: String): ResponseEntity<Any?> {
        return ResponseEntity.ok(accountService.findByEmail(email))
    }
}