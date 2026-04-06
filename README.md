# BillingApp — Desafio Android

Aplicativo Android de demonstração técnica com landing page de produto e fluxo completo de compra
usando a **Google Play Billing Library v8**.

---

## Arquitetura

### Padrão MVVM

- **Model**: dados e regras de negócio (`PurchaseRepository`, `BillingManager`, Room, DataStore)
- **ViewModel**: `ProductViewModel` — expõe `StateFlow<ProductUiState>` para a UI e
  `Channel<String>` para eventos pontuais (snackbar)
- **View**: `ProductScreen` (Jetpack Compose) — observa o estado e chama funções do ViewModel

### Módulos

```
BillingApp/
├── app/
│   └── src/
│       ├── main/java/com/desafio/billingapp/
│       │   ├── data/
│       │   │   ├── local/        # Room (AppDatabase, PurchaseDao, PurchaseEntity)
│       │   │   │                 # DataStore (UserPreferencesDataStore)
│       │   │   │                 # Android Keystore + AES/GCM (SecurePreferences)
│       │   │   └── repository/   # PurchaseRepository
│       │   ├── di/               # Módulos Koin (AppModule)
│       │   ├── ui/
│       │   │   ├── components/   # HeroBanner, ProductCard,
│       │   │   │                 # PurchaseHistoryItem, ConnectionStatus
│       │   │   ├── screens/      # ProductScreen
│       │   │   └── theme/        # Delegação para o Design System
│       │   ├── utils/            # StringResourceProvider, AndroidStringResourceProvider
│       │   └── viewmodel/        # ProductViewModel, ProductUiState
│       └── test/java/com/desafio/billingapp/
│           └── viewmodel/        # ProductViewModelTest
│
├── billing-module/
│   └── src/
│       ├── main/java/com/desafio/billing/
│       │   ├── BillingManager.kt     # Interface pública + enums
│       │   ├── BillingManagerImpl.kt # Implementação com BillingClient
│       │   └── BillingModule.kt      # Koin binding
│       └── test/java/com/desafio/billing/
│           └── PurchaseResultTest.kt
│
└── design-system/
    └── src/
        ├── main/java/com/desafio/designsystem/
        │   ├── tokens/
        │   │   ├── ColorTokens.kt     # Paletas brutas
        │   │   ├── SemanticColors.kt  # BillingColors — roles semânticas
        │   │   ├── SpacingTokens.kt   # xs · sm · md · lg · xl · xl2 · xl3
        │   │   ├── ShapeTokens.kt     # small · full · button · card · chip · heroBanner
        │   │   ├── ElevationTokens.kt # card · cardHigh
        │   │   └── TypographyTokens.kt# Escala Material 3 completa
        │   ├── theme/
        │   │   └── BillingTheme.kt    # BillingTheme (provider) + BillingThemeTokens (accessor)
        │   └── components/
        │       ├── button/            # BillingButton · BillingButtonOutlined · BillingButtonPremium
        │       ├── card/              # BillingCardSurface · BillingCardPremium
        │       ├── feedback/          # BillingErrorCard · BillingSkeletonBox · BillingSkeletonCard
        │       ├── indicator/         # StatusDot · BillingStatusChip · BillingPremiumBadge
        │       └── layout/            # BillingScaffold (+ BillingTopBar interno) · SectionHeader
        └── test/java/com/desafio/designsystem/
            └── tokens/                # SpacingTokensTest
```

---

## Decisões Técnicas

### 1. Módulo independente de Billing

O módulo `billing-module` encapsula toda a lógica da Google Play Billing Library. O restante do app
interage apenas com a interface `BillingManager`, sem conhecer detalhes de implementação. Facilita
testes via mock da interface e troca de biblioteca no futuro.

### 2. Design System como módulo separado

O módulo `design-system` centraliza tokens (cores, espaçamentos, formas, elevação, tipografia,
motion) e componentes reutilizáveis. A UI consome o tema via `BillingThemeTokens`, um objeto acessor
que distribui os tokens por `CompositionLocal`. Isso desacopla a identidade visual da lógica de
negócio.

### 3. Persistência em camadas

| Dado                           | Armazenamento              | Motivo                                               |
|--------------------------------|----------------------------|------------------------------------------------------|
| Histórico de compras           | Room                       | Consultas, persistência offline                      |
| Status premium, último produto | DataStore                  | Preferências reativas com Flow                       |
| Token de compra                | Android Keystore + AES/GCM | Dado sensível, criptografado sem dependência externa |

### 4. Koin para injeção de dependência

Toda a cadeia `BillingManager → PurchaseRepository → ProductViewModel` é gerenciada pelo Koin. O
`StringResourceProvider` também é injetado no ViewModel, mantendo strings fora das classes de
lógica.

### 5. Strings centralizadas

Strings exibidas na UI ficam em `res/values/strings.xml`. Composables usam `stringResource()`.
Classes fora de Composables (ViewModel, BillingManagerImpl) recebem um `StringResourceProvider` via
injeção, evitando referência direta ao `Context`.

### 6. Eventos pontuais via Channel

O `ProductViewModel` usa `Channel<String>(Channel.BUFFERED)` para eventos de uso único (snackbar de
sucesso, cancelamento, erro). `StateFlow` é usado apenas para estado persistente da UI.

---

## Stack

| Camada                 | Tecnologia                          | Versão         |
|------------------------|-------------------------------------|----------------|
| Linguagem              | Kotlin                              | 2.1.21         |
| UI                     | Jetpack Compose + Material 3        | BOM 2026.03.00 |
| Injeção de dependência | Koin                                | 4.0.4          |
| Billing                | Google Play Billing Library         | 8.3.0          |
| Banco de dados local   | Room                                | 2.7.1          |
| Preferências reativas  | DataStore Preferences               | 1.2.1          |
| Criptografia           | Android Keystore + AES/GCM (nativo) | —              |
| Concorrência           | Kotlin Coroutines + Flow            | 1.10.2         |
| Testes unitários       | JUnit 4 + MockK + coroutines-test   | —              |
| Build                  | Gradle Kotlin DSL + Version Catalog | 8.11.1         |

**SDK**: minSdk 24 · targetSdk 36 · compileSdk 36

---

## Build e Execução

**Pré-requisitos**: Android Studio Hedgehog ou superior, JDK 11+

```bash
git clone https://github.com/faelmg18/BillingApp.git
cd BillingApp
./gradlew assembleDebug
```

### Configurar produto no Play Console

1. Acesse o [Google Play Console](https://play.google.com/console)
2. Vá em **Monetização → Produtos integrados ao app**
3. Crie um produto com ID: `premium_product_001`
4. O app busca esse ID automaticamente via `ProductViewModel.PRODUCT_IDS`

### Testar sem publicar

Use o [Google Play Billing Testing](https://developer.android.com/google/play/billing/test) com
licenciadores de teste configurados no Play Console.

---

## Testes

Cada módulo tem um teste unitário cobrindo sua responsabilidade central:

| Módulo            | Teste                  | O que verifica                                                                     |
|-------------------|------------------------|------------------------------------------------------------------------------------|
| `:app`            | `ProductViewModelTest` | Chamar `purchase(activity, null)` emite o evento "Produto indisponível" no Channel |
| `:billing-module` | `PurchaseResultTest`   | `PurchaseResult.Error` armazena `responseCode` e `message` corretamente            |
| `:design-system`  | `SpacingTokensTest`    | Os valores default do `BillingSpacing` batem com os tokens definidos               |

```bash
./gradlew test
```

---

## Referências

Documentação oficial utilizada durante a implementação:

| Documento                                                                                                               | Descrição                                                |
|-------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------|
| [Google Play Billing Library — Visão geral](https://developer.android.com/google/play/billing/billing_library_overview) | Introdução à biblioteca e conceitos fundamentais         |
| [Integrar a Billing Library](https://developer.android.com/google/play/billing/integrate)                               | Guia passo a passo de integração do BillingClient        |
| [Ciclo de vida do BillingClient](https://developer.android.com/google/play/billing/billing_library_overview#lifecycle)  | Gerenciamento de conexão e reconexão                     |
| [Consultar produtos disponíveis](https://developer.android.com/google/play/billing/integrate#show-products)             | `queryProductDetailsAsync` e `QueryProductDetailsParams` |
| [Iniciar fluxo de compra](https://developer.android.com/google/play/billing/integrate#launch-billing-flow)              | `launchBillingFlow` e `BillingFlowParams`                |
| [Processar compras](https://developer.android.com/google/play/billing/integrate#process-purchase)                       | `PurchasesUpdatedListener` e `acknowledgePurchase`       |
| [Restaurar compras](https://developer.android.com/google/play/billing/integrate#fetch-product-details)                  | `queryPurchasesAsync` e `QueryPurchasesParams`           |
| [Testar compras com o Google Play](https://developer.android.com/google/play/billing/test)                              | Licenciadores de teste e produtos de teste               |
| [Notas de versão — Billing Library 7](https://developer.android.com/google/play/billing/release-notes)                  | Mudanças e APIs introduzidas na v7                       |

---

## Melhorias Futuras

- **Testes instrumentados**: usar `FakeBillingManager` injetado via Koin para testar fluxos de
  compra end-to-end
- **Analytics**: rastrear eventos de compra (initiate, success, cancel) com Firebase Analytics
- **Ofertas e promoções**: suporte a `SubscriptionOfferDetails` e preços introdutórios

## Observações:

Não foi possível validar o fluxo de compra em um ambiente real, pois o teste da Google Play Billing Library exige que o app esteja publicado no Google Play Console (mesmo que em fase de testes internos) e vinculado a uma conta de desenvolvedor ativa — o que não estava disponível no momento do desenvolvimento. Todo o restante do app foi construído e validado normalmente via build de debug.


