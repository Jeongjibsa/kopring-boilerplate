package js.training.kopring.controller

import js.training.kopring.model.dto.SignUpDto
import js.training.kopring.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("")
    fun get(): ResponseEntity<Any> {
        return ResponseEntity.ok(accountService.findAll())
    }

    @GetMapping("/{email}")
    fun getAll(@PathVariable(name = "email") email: String): ResponseEntity<Any?> {
        return ResponseEntity.ok(accountService.findByEmail(email))
    }

    @PostMapping("")
    fun create(@RequestBody signUp: SignUpDto): ResponseEntity<Any?> {
        accountService.save(signUp)

        return ResponseEntity.ok().build()
    }
}