# 📝 ToDo List API - Spring Boot

API REST para gerenciamento de tarefas (ToDo List), desenvolvida com Java e Spring Boot.
Permite criar usuários, autenticar via Basic Auth e gerenciar tarefas de forma segura.

---

## 🚀 Tecnologias utilizadas

* ☕ Java 17+
* 🌱 Spring Boot
* 🗄️ Spring Data JPA
* 🔐 BCrypt (criptografia de senha)
* 🐘 Banco de dados relacional
* 📦 Maven

---

## 📌 Funcionalidades

### 👤 Usuários

* Criar novo usuário
* Validação de e-mail único
* Senha criptografada com BCrypt

### ✅ Tarefas

* Criar tarefas
* Listar tarefas do usuário autenticado
* Atualizar tarefas
* Associação com usuário

### 🔐 Autenticação

* Basic Auth
* Validação de usuário via filtro (`OncePerRequestFilter`)
* Proteção de rotas

---

## 🔑 Autenticação

A API utiliza **Basic Auth**.

### Exemplo:

```
Authorization: Basic base64(email:senha)
```

Exemplo real:

```
Authorization: Basic YW1hbmRhQGVtYWlsLmNvbToxMjM0NTY=
```

---

## 📂 Estrutura do projeto

```
src/main/java/com/amandasantos/todolist
│
├── controller
├── entity
├── repository
├── filter
└── TodolistApplication.java
```

---

## 📬 Endpoints

### 👤 Criar usuário

```
POST /users/
```

Body:

```json
{
  "name": "Amanda",
  "email": "amanda@email.com",
  "password": "123456"
}
```

---

### ✅ Criar tarefa

```
POST /tasks/
```

Body:

```json
{
  "title": "Estudar Spring Boot",
  "description": "Finalizar módulo",
  "priority": "HIGH",
  "dueDate": "2026-03-20T18:00:00"
}
```

---

### 📋 Listar tarefas

```
GET /tasks/
```

---

### ✏️ Atualizar tarefa

```
PUT /tasks/{id}
```

Body:

```json
{
  "title": "Estudar Java",
  "description": "Atualizado",
  "priority": "HIGH",
  "dueDate": "2026-03-20T18:00:00",
  "completed": false
}
```

---

## ⚠️ Regras de negócio

* Usuário só pode acessar suas próprias tarefas
* Não é possível atualizar tarefas de outro usuário
* A data da tarefa não pode ser menor que a atual

---

## ▶️ Como rodar o projeto

### 1. Clonar repositório

```
git clone https://github.com/seu-usuario/seu-repositorio.git
```

### 2. Entrar na pasta

```
cd todolist
```

### 3. Rodar o projeto

```
mvn spring-boot:run
```

---

## 🧪 Testando a API

Você pode usar:

* Insomnia
* Postman

---

## 💡 Melhorias futuras

* 🔐 Autenticação com JWT
* 📊 Paginação de tarefas
* 🔍 Filtros (por status, prioridade)
* 🧾 Logs e tratamento global de erros
* 🌐 Deploy em cloud

---

## 👩‍💻 Autora

Feito com 💙 por **Amanda Santos**

---

## ⭐ Contribuição

Sinta-se à vontade para contribuir ou dar uma ⭐ no projeto!
