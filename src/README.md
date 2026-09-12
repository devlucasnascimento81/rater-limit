Rate Limiter / Gateway

Stack: Java 21 · Sockets (TCP) · Threads · JUnit 5 · Maven

O que é

Um mini API Gateway em Java puro que recebe requisições via socket TCP e decide, na hora, se cada cliente pode ou não continuar fazendo chamadas — usando o algoritmo Token Bucket.

A ideia por trás disso é simular uma peça que toda API séria tem por trás dos panos: proteção contra abuso e excesso de requisições, sem depender de nenhum framework pronto. O projeto não tem banco de dados nem regra de negócio "de sistema" — o foco é inteiro em rede, concorrência de conexões e controle de fluxo, que é o tipo de problema que aparece bastante em entrevista e em sistemas reais de backend.

Arquitetura

O projeto está dividido em três pacotes, cada um com uma responsabilidade só:

model (Bucket): representa o balde de tokens de um único cliente. Sabe consumir um token, repor tokens com base no tempo que passou (usando ChronoUnit, não soma de horas) e nunca ultrapassar sua capacidade máxima. Não sabe nada sobre rede nem sobre outros clientes.
service (RateLimiter): dono de um ConcurrentHashMap<String, Bucket>, um balde por chave de cliente. Cria o balde na primeira vez que uma chave aparece (computeIfAbsent) e decide, chamando o balde certo, se aquela chamada passa ou não. O mapa é thread-safe porque múltiplas conexões podem chamá-lo ao mesmo tempo.
server (RateLimiterServer): a única parte que sabe que existe rede. Abre um ServerSocket e, para cada conexão aceita, sobe uma thread dedicada que lê uma linha por vez até o cliente desconectar — assim, vários clientes são atendidos ao mesmo tempo, sem um travar o outro.

Escolhi Token Bucket em vez de outro algoritmo (como sliding window) porque ele lida bem com picos curtos de tráfego sem penalizar o cliente depois — e é o algoritmo que mais aparece em rate limiters de verdade (ex: APIs da AWS, Stripe).

Optei por Sockets puros em vez de um framework HTTP porque o objetivo aqui era entender e implementar a camada de rede na mão, sem abstrações escondendo o que está acontecendo — isso fica pra quando eu migrar esse mesmo conceito pra Spring Boot, como um próximo projeto.

A primeira versão do servidor atendia uma conexão por vez, o que não representava um gateway de verdade. Depois de validar a lógica do rate limiter isoladamente, refiz o servidor para atender cada conexão em uma thread separada, e troquei o HashMap interno do RateLimiter por um ConcurrentHashMap, já que múltiplas threads passaram a acessar o mesmo mapa ao mesmo tempo.