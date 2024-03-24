FROM ubuntu:latest
LABEL authors="amito"

ENTRYPOINT ["top", "-b"]