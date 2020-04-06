import org.bouncycastle.cert.jcajce.JcaCertStore
import org.bouncycastle.cms.CMSProcessableByteArray
import org.bouncycastle.cms.CMSSignedDataGenerator
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder
import org.bouncycastle.crypto.Signer
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder
import org.bouncycastle.util.Store
import org.bouncycastle.util.encoders.Base64
import java.io.ByteArrayInputStream
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.X509Certificate
import java.util.*


class Signer {
    fun sign(
            content: String,
            password: String,
            pfxBase64: String
    ): String {
        val signer: Signer
        val ks: KeyStore = KeyStore.getInstance("PKCS12", "BC")
        val pfxContent: ByteArray = Base64.decode(pfxBase64)
        val baos: ByteArrayInputStream = ByteArrayInputStream(pfxContent)
        ks.load(baos, password.toCharArray())
        with(ks)
        {
            for (alias in aliases()) {
                if (isKeyEntry(alias)) {
                    val key: PrivateKey = getKey(alias, password.toCharArray()) as PrivateKey
                    val cert: X509Certificate = getCertificate(alias) as X509Certificate
                    val certList = ArrayList<X509Certificate>()
                    certList.add(cert)
                    val certs = JcaCertStore(certList)
                    var gen = CMSSignedDataGenerator()
                    var signer = org.bouncycastle.operator.jcajce.JcaContentSignerBuilder("GOST3411WITHECGOST3410-2012-256").setProvider("BC").build(key)
                    gen.addSignerInfoGenerator(JcaSignerInfoGeneratorBuilder(JcaDigestCalculatorProviderBuilder().setProvider("BC").build()).build(signer, cert))
                    gen.addCertificates(certs)
                    val msg = CMSProcessableByteArray(content.toByteArray())
                    val sigData = gen.generate(msg, false)
                    val sign = sigData.encoded
                    return Base64.toBase64String(sign)
                }
            }
        }
        return ""
    }
}