CREATE SCHEMA "Core"
    AUTHORIZATION postgres;
GRANT ALL ON SCHEMA "Core" TO PUBLIC;

CREATE TABLE "Core"."Programa"(
    "id" serial primary key,
    "nome" varchar(250) not null
);

CREATE TABLE "Core"."Tela"(
    "id" serial primary key,
    "nome" varchar(250) not null,
    "programaId" integer not null,
    "codigo" varchar(10) not null unique,
    "tipo" integer,
    "rota" varchar(250),
    FOREIGN KEY ("programaId")
    REFERENCES "Core"."Programa"("id")
);

CREATE TABLE "Core"."Usuario" (
    "id" serial primary key,
    "tipo" integer,
    "isAtivo" boolean not null,
    "nome" varchar(250) not null,
    "usuario" varchar(100) not null,
    "email" varchar(150) not null,
    "senha" varchar(250) not null,
    "senhaVencimento" date not null,
    "criadoEm" timestamp not null,
    UNIQUE("usuario","email")
);

CREATE TABLE "Core"."PerfilAcesso"(
    "id" serial primary key,
    "nome" varchar(250) not null
);

CREATE TABLE "Core"."UsuarioPerfisAcesso"(
    "id" serial primary key,
    "usuarioId" integer,
    "perfilAcessoId" integer,
    FOREIGN KEY ("usuarioId")
        REFERENCES "Core"."Usuario"("id"),
    FOREIGN KEY ("perfilAcessoId")
        REFERENCES "Core"."PerfilAcesso"("id")
);

CREATE TABLE "Core"."PerfilPermissaoAcesso"(
    "id" serial primary key,
    "perfilAcessoId" integer,
    "telaId" integer,
    "usuarioId" integer,
    "isMenu" boolean default false,
    "isAcesso" boolean default false,
    "isSalvar" boolean default false,
    "isEditar" boolean default false,
    "isDeletar" boolean default false,
    FOREIGN KEY ("usuarioId")
        REFERENCES "Core"."Usuario"("id"),
    FOREIGN KEY ("perfilAcessoId")
        REFERENCES "Core"."PerfilAcesso"("id"),
    FOREIGN KEY ("telaId")
        REFERENCES "Core"."Tela"("id")
);

CREATE TABLE "Core"."UsuarioPermissaoAcesso"(
    "id" serial primary key,
    "telaId" integer,
    "usuarioId" integer,
    "isMenu" boolean default false,
    "isAcesso" boolean default false,
    "isSalvar" boolean default false,
    "isEditar" boolean default false,
    "isDeletar" boolean default false,
    FOREIGN KEY ("usuarioId")
        REFERENCES "Core"."Usuario"("id"),
    FOREIGN KEY ("telaId")
        REFERENCES "Core"."Tela"("id")
);
