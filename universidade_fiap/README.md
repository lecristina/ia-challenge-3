# 🏍️ Projeto Mottu – Detecção de Motos com Spring Boot + Visão Computacional (YOLO)

## 💡 Funcionalidade
- **Detecção de motos em tempo real** usando YOLO (Python)
- **Envio de eventos** para API REST Spring Boot
- **Persistência em banco de dados** com Flyway
- **Visualização em tempo real** com Thymeleaf + AJAX
- **Segurança** com Spring Security (login com perfis)

## 🚀 Como executar

### 1. Backend (Spring Boot)
```bash
# Navegue para a pasta do projeto
cd universidade_fiap

# Execute o Spring Boot
./mvnw spring-boot:run
# ou no Windows:
mvnw.cmd spring-boot:run
```

### 2. Detecção (Python)
```bash
# Instale as dependências Python
pip install -r requirements.txt

# Baixe os arquivos YOLO (primeira vez)
python download_yolo.py

# Baixe manualmente o yolov3.weights (248MB):
# https://pjreddie.com/media/files/yolov3.weights
# Salve como 'yolov3.weights' na pasta do projeto

# Execute o detector
python detector.py
```

### 3. Acesso ao Sistema
- **Login**: http://localhost:8080/login
- **Dashboard**: http://localhost:8080/dashboard
- **API**: http://localhost:8080/api/detections

#### Credenciais de teste:
- **Usuário**: admin
- **Senha**: 123

## 📷 Demonstração

### Funcionalidades do Sistema:
1. **Detecção em Tempo Real**: O script Python detecta motos usando YOLO
2. **Integração com Backend**: Eventos são enviados automaticamente para o Spring Boot
3. **Dashboard Dinâmico**: Visualização em tempo real dos eventos no navegador
4. **Persistência**: Todos os eventos são salvos no banco de dados MySQL

### Como testar:
1. Execute o Spring Boot
2. Faça login no sistema
3. Acesse o Dashboard
4. Execute o script Python `detector.py`
5. Aponte a câmera para motos ou use um vídeo de teste
6. Veja os eventos aparecendo no dashboard em tempo real

## 📦 Tecnologias

### Backend:
- **Spring Boot 3.5.4**
- **Spring Security 6**
- **Spring Data JPA**
- **Flyway** (controle de versões do banco)
- **Thymeleaf** (templates)
- **MySQL** (banco de dados)

### Visão Computacional:
- **OpenCV** (processamento de imagem)
- **YOLO v3** (detecção de objetos)
- **Python 3.8+**

### Frontend:
- **Bootstrap 5**
- **JavaScript** (AJAX)
- **Thymeleaf**

## 🗄️ Estrutura do Banco de Dados

### Tabela `detection_event`:
```sql
CREATE TABLE detection_event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_type VARCHAR(255) NOT NULL,
    x INT NOT NULL,
    y INT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🔧 Configuração

### application.properties:
```properties
# Banco de dados MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/trackzone_mvc2
spring.datasource.username=root
spring.datasource.password=230205

# Flyway habilitado
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

### Dependências Python:
```
opencv-python==4.8.1.78
numpy==1.24.3
requests==2.31.0
```

## 📁 Estrutura do Projeto

```
universidade_fiap/
├── src/main/java/br/com/fiap/universidade_fiap/
│   ├── control/
│   │   └── DetectionEventController.java    # API REST
│   ├── model/
│   │   ├── DetectionEvent.java              # Entidade JPA
│   │   └── DetectionEventDTO.java           # DTO
│   ├── repository/
│   │   └── DetectionEventRepository.java    # Repositório JPA
│   └── security/
│       └── SegurancaConfig.java             # Configuração de segurança
├── src/main/resources/
│   ├── db/migration/
│   │   └── V8__Create_detection_event_table.sql
│   └── templates/home/
│       └── dashboard.html                   # Dashboard com AJAX
├── detector.py                              # Script de detecção YOLO
├── download_yolo.py                         # Download dos arquivos YOLO
├── requirements.txt                         # Dependências Python
└── README.md
```

## 🎯 API Endpoints

### Detecção de Eventos:
- `POST /api/detections` - Salva evento de detecção
- `GET /api/detections/recent` - Lista últimos 10 eventos
- `GET /api/detections/recent/50` - Lista últimos 50 eventos
- `GET /api/detections/by-type/{eventType}` - Eventos por tipo
- `GET /api/detections/count` - Contagem total de eventos

## 🔒 Segurança

- **Autenticação**: Spring Security com login/password
- **Autorização**: Diferentes perfis (ADMIN, GERENTE, OPERADOR, USER)
- **API**: Protegida por autenticação
- **CSRF**: Desabilitado para APIs (apenas para detecção)

## ✅ Testes

1. **Rode o Spring Boot**: `./mvnw spring-boot:run`
2. **Acesse**: http://localhost:8080/login → faça login
3. **Acesse**: http://localhost:8080/dashboard
4. **Rode**: `python detector.py`
5. **Veja**: Eventos aparecendo no dashboard em tempo real

## 🚨 Checklist Final

- [x] Spring Boot + Thymeleaf + Flyway + Security
- [x] API REST funcional
- [x] Dashboard com dados em tempo real
- [x] Script de IA com detecção via YOLO
- [x] Documentação clara (README)
- [x] Código rodando localmente
- [x] Integração Python ↔ Spring Boot
- [x] Persistência de dados
- [x] Interface visual moderna

## 🎥 Vídeo de Demonstração

**Link para vídeo no YouTube**: [A ser adicionado]

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique se o MySQL está rodando
2. Confirme se a porta 8080 está livre
3. Verifique se os arquivos YOLO estão presentes
4. Consulte os logs do Spring Boot

---

**Desenvolvido para o 3º Sprint - Disruptive Architectures: IoT, IOB & Generative IA**