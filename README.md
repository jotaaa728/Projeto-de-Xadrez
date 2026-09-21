# Projeto de Xadrez em Java

Aplicação de xadrez executada no terminal, desenvolvida em Java com foco em Programação Orientada a Objetos.

## Funcionalidades

- Tabuleiro 8x8
- Peças: rei, rainha, torre, bispo, cavalo e peão
- Alternância de turnos entre brancas e pretas
- Validação de movimentos
- Captura de peças
- Detecção de xeque
- Detecção de xeque-mate
- Promoção de peão
- Tratamento de jogadas inválidas com exceções

## Estrutura

```text
src/
├── application/
│   ├── Program.java
│   └── UI.java
├── board/
│   ├── Board.java
│   ├── BoardException.java
│   ├── Piece.java
│   └── Position.java
└── chess/
    ├── ChessMatch.java
    ├── ChessPiece.java
    ├── ChessPosition.java
    ├── Color.java
    ├── King.java
    ├── Queen.java
    ├── Rook.java
    ├── Bishop.java
    ├── Knight.java
    └── Pawn.java
```

## Conceitos de POO utilizados

O projeto utiliza conceitos como:

- Encapsulamento
- Herança
- Polimorfismo
- Classes abstratas
- Enumerações
- Exceções personalizadas
- Composição entre objetos

## Como executar

Compile os arquivos Java dentro de `src` e execute a classe:

```text
application.Program
```

Durante a partida, informe as posições utilizando a notação de coordenadas do tabuleiro, por exemplo:

```text
Source: e2
Target: e4
```

## Tecnologias

- Java
- Programação Orientada a Objetos
- Git e GitHub
