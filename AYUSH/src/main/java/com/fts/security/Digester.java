package com.fts.security;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.salt.FixedStringSaltGenerator;
import org.springframework.stereotype.Repository;

/**
 * This class lets the user specify the algorithm (and provider) to be used for creating digests, the size of the salt to be applied, the number of times the hash function will be
 * applied (iterations) and the salt generator to be used. This class avoids byte-conversion problems related to the fact of different platforms having different default charsets,
 * and returns digests in the form of BASE64-encoded ((default)) or HEXADECIMAL ASCII Strings. This class is thread-safe.
 */

@Repository
public class Digester
{
    
    public String digest(String str2Digest)
    {
        FixedStringSaltGenerator fixedStringSaltGenerator = new FixedStringSaltGenerator();
        fixedStringSaltGenerator.setSalt(str2Digest + "USER-VPCS");

        StandardStringDigester digester = new StandardStringDigester();
        digester.setAlgorithm("SHA-512");
        digester.setIterations(10024);
        digester.setSaltGenerator(fixedStringSaltGenerator);

        return digester.digest(str2Digest);
    }
}
