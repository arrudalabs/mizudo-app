## Generating Keys with OpenSSL

It is also possible to generate a public and private key pair using the OpenSSL command line tool.

openssl commands for generating keys
```ssh
openssl genrsa -out rsaPrivateKey.pem 2048
openssl rsa -pubout -in rsaPrivateKey.pem -out publicKey.pem
```

An additional step is needed for generating the private key for converting it into the PKCS#8 format.

openssl command for converting private key
```ssh
openssl pkcs8 -topk8 -nocrypt -inform pem -in rsaPrivateKey.pem -outform pem -out privateKey.pem
```
