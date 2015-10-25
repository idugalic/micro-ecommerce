cd microservices-config-server
cf cups config-server -p '{"uri":"http://config-server-idugalic.cfapps.io"}'
cd ..

cd microservices-eureka
cf cups eureka -p '{"uri":"http://eureka-idugalic.cfapps.io"}'
cd ..