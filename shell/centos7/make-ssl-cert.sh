openssl genrsa -out server.key 1024
openssl req -new -key server.key -out server.csr

openssl genrsa -out ca.key 1024
openssl req -new -key ca.key -out ca.csr
openssl x509 -req -in ca.csr -signkey ca.key -out ca.crt

openssl x509 -req -CA ca/ca.crt -CAkey ca/ca.key -CAcreateserial -in server/server.csr -out server.crt
