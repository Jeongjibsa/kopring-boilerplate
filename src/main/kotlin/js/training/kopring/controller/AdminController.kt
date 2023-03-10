package js.training.kopring.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController {

    @GetMapping("")
    fun index(): String {
        return "Hello Admin"
    }
}