import com.sun.tools.javac.util.Convert
import org.bouncycastle.crypto.CipherParameters
import org.bouncycastle.crypto.Signer
import org.bouncycastle.util.encoders.Base64
import java.io.ByteArrayInputStream
import java.security.Key
import java.security.KeyStore
import java.security.cert.Certificate


class Signer {

    fun sign(content: String, password: String, pfxBase64: String) {
        val signer:Signer
        val ks : KeyStore = KeyStore.getInstance("PKCS12", "BC")
        val pfxContent:ByteArray = Base64.decode(pfxBase64)
        val baos:ByteArrayInputStream = ByteArrayInputStream(pfxContent)
        ks.load(baos, password.toCharArray())
        with (ks)
        {
            for (alias in aliases()) {
                if (isKeyEntry(alias)) {
                    val key: Key = getKey(alias, password.toCharArray())
                    val cert: Certificate = getCertificate(alias)
                    addKeyAndCertificateToStore()
                }
            }
        }

    }
}