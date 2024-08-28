#### Documentar problemas encontrados no desenvolvimento

- Ao instalar o direnv o mesmo não funcionou até adicionar a linha abaixo no .bashrc

        eval "$(direnv hook bash)"

        PS: lembrar do source após incluir a linhas

#### Acessar o banco via CMD

PGPASSWORD="$QUARKUS_DATASOURCE_DEVSERVICES_PASSWORD" psql -h 127.0.0.1 -U builder -d pizzadb -p 5432

