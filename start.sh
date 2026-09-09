#!/bin/sh
set -e

PORT="${PORT:-10000}"

# Tomcat's default HTTP connector listens on 8080 -- point it at Render's PORT
sed -i "s/port=\"8080\"/port=\"${PORT}\"/" /usr/local/tomcat/conf/server.xml

exec /usr/local/tomcat/bin/catalina.sh run