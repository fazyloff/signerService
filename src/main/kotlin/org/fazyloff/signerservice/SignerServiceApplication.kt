package org.fazyloff.signerservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SignerServiceApplication

fun main(args: Array<String>) {
	runApplication<SignerServiceApplication>(*args)
}
