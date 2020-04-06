import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sign")
class RootController(private val signer: Signer) {
    val content: String = ""

    @PostMapping("/", consumes = ["application/json"], produces = ["application/json"])
    fun sign(@RequestBody body: String = ""): String {
        return "{'test':'OK'}"
    }


}
