## Descrição
Este exercício visa criar um pequeno aplicativo que permite encurtar links e exibir um histórico dos links encurtados recentemente para seus sites favoritos.

Para isso, você utilizará um serviço já implementado que tratará de toda a lógica do backend, que é aceitar os links e retornar um alias para eles.

Esta aplicação é composta por apenas uma tela, que tem:
- Uma entrada de texto na qual o usuário pode digitar a URL do site para encurtar;
- Um botão que acionará a ação de envio deste link para o serviço;
- Uma lista com os links/aliases encurtados recentemente.

API
URL base: https://url-shortener.herokuapp.com

Shorten url
Um método para criar aliases com o seguinte corpo:
Request:
- Path: /api/alias
- Method: POST
- Body (json): { "url": "<the url>"


Response:
- Status: 201 on success with the following body - Body:
  {
  "alias" : "<url alias>", "_links" : {
  "self" : "<original url>",
  "short": "<short url>" }
  }

Ler url encurtado
Um método para recuperar um link por meio de um alias.

Request:
- Path: /api/alias/:id
- Method: GET

Response:
- Status: 200 on success with the following body - Body: { "url": "<the original url>" }

- Status: 404 when not found

Perguntas frequentes
A interface do usuário deve ser igual à apresentada aqui?
Não. A interface do usuário não está sendo avaliada, apenas o código. Sinta-se à vontade para adaptar a interface do usuário àquela com a qual você se sentirá mais confortável.

Os dados devem ser armazenados?
Não. Apenas guarde na memória.

Todos os endpoints da API devem ser usados?
Não. Use apenas o que você sente que é necessário para resolver o problema.

## Descrição do aplicativo:

## Notas importantes: A API não está funcionando!

- A API não está funcionando: se quiser entender como o app funciona, vá no módulo app e execute os testes instrumentados: [UI tests](https://github.com/fredelinhares/shortened-url/tree/master/app/ src/androidTest/java/com/nubank/takehomeevaluation)
- A IU não é feita no Jetpack Compose.
- É de extrema importância ressaltar que não foi levado em consideração o desenvolvimento de um layout com aspecto visual avançado.
- A divisão dos módulos não levou em consideração o fator de escalabilidade. Em um projeto real, poderíamos criar, por exemplo, um aplicativo multi-repositório,
  onde poderíamos ter, por exemplo:
  - Um repositório de dados, seguindo o modelo "fonte única de verdade (SSOT)": https://en.wikipedia.org/wiki/Single_source_of_truth
  - Um repositório separado para cada recurso do projeto - por exemplo, o módulo urlshortener. Tais módulos de recursos seriam, no final, incorporados a um aplicativo pai via maven/gradle, aplicando uma estratégia de gerenciamento de dependência - BOM ou Bill Of Materials (exemplo em https://firebase.blog/posts/2020/11/dependency -management-ios-android).
  - Um repositório para gerenciar a infraestrutura do SDK como o firebase.
  - Um repositório para gerenciar skds para análises...

## O que é este aplicativo?

Urlshortener é um pequeno aplicativo Android que permite encurtar urls e exibir um histórico de links encurtados recentemente para seus sites favoritos.

Este aplicativo é composto por apenas uma tela, que possui:

- Uma entrada de texto onde o usuário pode digitar a URL do site para encurtar;
- Um botão que acionará a ação de envio deste link para o serviço;
- Uma lista com os links/aliases encurtados recentemente.

## Tecnologias/Arquitetura/Princípios:

- [x] Plataforma - Android: https://developer.android.com/
- [x] Linguagem - Kotlin: https://kotlinlang.org/
- [x] Atividade individual - Apresentação de Ian Lake -> 'Atividade individual - Por que, quando e como (Android Dev Summit '18)': https://www.youtube.com/watch?v=2k8x8V77CrU&ab_channel=AndroidDevelopers
- [x] Multi-Módulo - Apresentação de Yigit Boyar, Florina Mutenescu -> 'Construa uma arquitetura modular de aplicativo Android (Google I/O'19)': https://www.youtube.com/watch?v=PZBg5DIzNww&ab_channel= Desenvolvedores Android
- [x] Componente de navegação: https://developer.android.com/guide/navigation/navigation-getting-started
- [x] estrutura Koin -DI: https://insert-koin.io/
- [x] MVVM: https://developer.android.com/topic/architecture
- [x] Arquitetura limpa: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
- [x] Código limpo: https://www.oreilly.com/library/view/clean-code-a/9780136083238/
- [x] Coroutines: https://kotlinlang.org/docs/coroutines-guide.html
- [x] Retrofit: https://square.github.io/retrofit/
- [x] S.O.L.I.D: https://en.wikipedia.org/wiki/SOLID

## Testes:
- [x] Testes de unidade - MockK: https://github.com/mockk/mockk
- [x] Teste de componentes com - Robolectric: http://robolectric.org/
- [x] Testes instrumentados com - Espresso: https://developer.android.com/training/testing/espresso

## Organização do código

O aplicativo é dividido em 03 módulos:

- [app](https://github.com/fredelinhares/shortened-url/tree/master/app): responsável por executar os testes de UI (em um eventual deploy, este módulo - e todo o código nele contido - irá não será implantado na produção).

- [core](https://github.com/fredelinhares/shortened-url/tree/master/core):
  - Infraestrutura para comunicação com a API (ApiClientBuilder, RequestManager), exceções para comunicação com o servidor, etc...
  - Gerenciamento de estado de fragmento: BaseViewModel, ViewState.

- [urlshortener](https://github.com/fredelinhares/shortened-url/tree/master/urlshortener): módulo responsável por implementar a funcionalidade. Contém a tela (UrlShortenerListFragment) com a qual o usuário interage.

## Linha de raciocinio para o desenvolvimento do aplicativo:

A idéia inicial é excluir o máximo de regras do módulo app e, transpor as features em módulos apartados do mesmo. Uma inspiração para tal implementação pode ser encontrada em: https://github.com/android/nowinandroid, que nada mais é do que um aplicativo que o time de engenharia do Google mantém como exemplo de melhores práticas.

Desta feita, eu particionei essa aplicação em 3 módulos como já descritos anteriormente.

O módulo urlshortener concentra a feature a ser entregue. À medida que o mesmo foi sendo desenvolvido, as implementações contidas no módulo core foram sendo construídas.

Para iniciar o desenvolvimento do app, eu preciso entender como é modelado o seu domínio.

Rastreamento do domain

Podemos dizer que o domínio é o núcleo do sistema. É a partir dele que podemos desenhar nossas features. Dito isso, o primeiro passo é modelar as entidades do sistema. Se levarmos em conta os conceitos baseados no Clean Architecture, estou me referindo exatamente a camada de Domain. Nessa camada, as entidades representam os principais objectos referentes a lógica de negócio. Ela está no centro do diagrama do Clean Architecture e todas as outras camadas dependem dela.

Para rastrear as entidades, eu utilizei o postman (https://www.postman.com/) para obter o response JSON dos endpoints. Com isso, eu crieis os data classes:
- UrlLinks
- ShortenedUrl
- OriginalUrl
- OriginalUrlInput
- ShortenedUrlResponse
- UrlLinksResponse

Um ponto de atenção refere-se ao mapeamento das entidades advindas diretamente da API, i.e., OriginalUrlInput, ShortenedUrlResponse e UrlLinksResponse. O mapeamento é feito através da implementação da interface MapToModel:

interface MapToModel<Model> {
fun toModel(): Model
}

A premissa básica da implementação de tal interface refere-se em sua aplicabilidade em cenários reais de produção, onde podemos fazer o rastreamento de quebras de contrato com a API caso algum retorno da mesma seja alterado. Isso evita que bug crashs ocorram inesperadamente em tais cenários e com isso, pode-se dizer que essa é uma estratégia válida para identificar excepctions de contrato.

Obviamente, para um aplicativo tão simples, podemos considerar isso como overengineering. Isso está no projeto apenas como demostração de idéias a explorar em cenários reais.

Service

Após ter as entidades de entrada e sáida do barramento rastreadas e fazer o respectivamento mapeamento para o que poderiamos chamar de view objects (UrlLinks, ShortenedUrl, OriginalUrl), o 2o passo foi criar a interface UrlShortenerService.

No contexto do Retrofit, UrlShortenerService é uma interface que representa os pontos do barramento da API para um serviço (no nosso presente caso, o encurtador de URLs). Nessa interface, temos a definição dos métodos HTTP que podem ser utilizados para interagir com o serviço, juntamente com os tipos de dados de entrada e saída esperados.

A interface UrlShortenerService define um método único chamado: registerUrl, que utiliza a anotação @POST para especificar que deve ser invocado utilizando o método HTTP POST. A anotação @POST especifica também o path do endpoint final, que neste caso é "/api/alias".

A função registerUrl toma um parâmetro de entrada do tipo OriginalUrlInput, que representa o URL original que a pessoa quer encurtar. O método é também marcado com a palavra-chave suspend, indicando que iremos utilizar coroutines.

OBS: caso vocês tenham interesse em alguns escritos que deixei público no meu perfil github sobre Coroutines, compartilho aqui 2 links:
-> https://github.com/fredelinhares/about-kotlin-coroutines: uma humilde sumarização sobre os Coroutines (não abordo Flow).
-> https://github.com/fredelinhares/coroutines-under-hood: como a lib Coroutines funciona por baixo dos panos. Aqui, abordo conceitos como CPS (Continuation-passing style) e State-Machine.

Dando continuidade a explicação, podemos falar que o resultado esperado da função registerUrl é uma instância de Response<ShortenedUrlResponse>, que representa a resposta HTTP devolvida pelo servidor. Esta resposta contém uma instância de ShortenedUrlResponse, que representa o URL abreviado que foi gerado pelo serviço.

No que concerne o funcionamento do Retrofit e como o mesmo lida com a interface UrlShortenerService criada, o mesmo gera uma implementação de tal interface em tempo de execução, com base nas annotations e assinaturas de método definidas em tal interface. A implementação gerada trata da comunicação HTTP com o servidor e o barramento via protocolo REST, incluindo a serialização e desserialização de dados, e o processamento da resposta HTTP.

Falando um pouco mais sobre o Retrofit, podemos também citar alguns design patterns que o mesmo utiliza em suas implementações:
- Builder Pattern, para criar instâncias de seu objeto principal, Retrofit.Builder.
- Factory Pattern, para criar instâncias de objetos que implementam a interface UrlShortenerService.
- Observer Pattern, permitindo que os clientes recebam notificações quando uma solicitação é concluída com sucesso ou com erro.
- Decorator Pattern, para adicionar recursos extras, como autenticação, logging e conversão de dados, a uma solicitação de rede.

Repository

Seguindo a linha de construção das camadas do Clean Architecture (vale aqui dizer que tal empreendimento, em uma aplicação tão trivial e simplória demarca mais uma vez um overengineering!!), o próximo passo foi construir a camada de repository. No Clean Architecture, os repositories situam-se na camada "Interface Adapters Layer". Eles definem como os dados devem ser acessados e fornecem uma abstracção para as fontes de dados.

```kotlin
interface UrlShortenerRepository {
    suspend fun registerUrl(originalUrl: OriginalUrl): ShortenedUrl
}
```

Observe que tanto a entrada como a resposta da função contida no UrlShortenerRepository referem-se a view objects, ou seja, tais objetos não fazem interface direta com o barramento da API.

Data-sources

O próximo passo é desenvolver a classe quer irá implementar o repository. No nosso caso, o UrlShortenerRemoteRepository. No Clean Architecture, essa classe situa-se na camada "Frameworks & Drivers Layer". As classes contidas no data-sources são implementações concretas dos repositórios e tratam do acesso efectivo aos dados (sejam eles remotos/locais).

```kotlin
class UrlShortenerRemoteRepository(private val urlShortenerService: UrlShortenerService) :
    UrlShortenerRepository {
    override suspend fun registerUrl(originalUrl: OriginalUrl): ShortenedUrl {
        return RequestManager.requestFromApi { urlShortenerService.registerUrl(originalUrl.toInput()) }?.toModel()
            ?: throw RepositoryException()
    }
}
```

Obviamente que numa primeira versão, eu construi a função registerUrl retornando um dado mockado, uma vez que eu ainda não havia construido a classe RequestManager...

RequestManager

```kotlin
object RequestManager {

    @Throws(
        InfoNotFoundErrorException::class,
        ServerErrorException::class,
        NetworkException::class,
        RepositoryException::class
    )
    suspend fun <T> requestFromApi(request: (suspend () -> Response<T>)): T? {
        try {
            val response = request()
            if (response.isSuccessful) {
                // attention point: here it may happen to have some crash related to the json conversion
                return response.body()
            } else {
                val message = response.message()
                throw when (response.code()) {
                    404 -> InfoNotFoundErrorException(message = message)
                    500 -> ServerErrorException(message = message)
                    else -> RepositoryException(message = message)
                }
            }
        } catch (exception: Exception) {
            Timber.e(exception, "Request error: ${exception.message}")
            when (exception) {
                is IOException -> throw NetworkException(cause = exception)
                else -> throw exception
            }
        }
    }
}
```

O código contido no RequestManager tem como objetivo gerenciar as requisições feitas para uma API. O RequestManager no nosso aplicativo foi declarado como um objeto, o que significa que é um singleton. Isso implica que apenas uma instância dessa classe é criada e pode ser utilizada em todo o aplicativo.

A função requestFromApi se utiliza de Generics (indicado pelo parâmetro de tipo <T>), o que significa que ela pode ser usada com diferentes tipos de retorno de chamada da API. Ela usa o marcador supend, o que significa que ela é projetada para ser usada com Coroutines, uma abordagem assíncrona que visa não blockar a Main Thread durante o tempo em que a requisição estiver sendo processada.

Podemos ver que a função requestFromApi aceita um parâmetro chamado request, que é uma função lambda que aceita uma suspend function que retorna um objeto Response<T>. Essa função é a chamada à API a ser executada.

Além disso, tal função lança exceções personalizadas, como InfoNotFoundErrorException, ServerErrorException, NetworkException e RepositoryException.

O RequestManager está no módulo "core". Num contexto realista de produção, poderiamos jogar tal implementação num SDK-Network, responsável tão somente para tratar questões relacionadas ao entorno da stack necessária para requisitar dados ao servidor remoto.

RegisterUrlUseCase

class RegisterUrlUseCase(private val repo: UrlShortenerRepository) : UseCase<OriginalUrl, ShortenedUrl> {
override suspend fun execute(param: OriginalUrl) = repo.registerUrl(param)
}

O código apresentado é um exemplo de um  use case (interactor), utilizado em Clean Architecture. Um use case é uma classe que encapsula uma única operação ou funcionalidade do sistema. No exemplo fornecido, o caso de uso é chamado RegisterUrlUseCase e é responsável por registrar uma URL original e gerar uma URL encurtada.

Analisando o código mais de perto, podemos afirmar que:

- A classe RegisterUrlUseCase recebe um parâmetro repo do tipo UrlShortenerRepository. Isso indica que esta classe depende de um repositório para executar a funcionalidade de registro de URL. A classe implementa a interface genérica UseCase, que recebe dois parâmetros tipados: OriginalUrl e ShortenedUrl. Esses tipos representam, respectivamente, a URL original a ser registrada e a URL encurtada gerada pela API.

- Interface UseCase:

```kotlin
interface UseCase<Param, Return> {
    suspend fun execute(param: Param): Return
}
```

A interface UseCase possui uma função chamado execute, que é suspend e recebe um parâmetro do primeiro tipo genérico (neste caso, OriginalUrl) e retorna um valor do segundo tipo genérico (neste caso, ShortenedUrl).

- Método execute: O método execute é sobrescrito na classe RegisterUrlUseCase e recebe um parâmetro chamado param do tipo OriginalUrl. Sua implementação é bem simples: apenas chama o método registerUrl do repositório, passando o parâmetro OriginalUrl.

Vale lembrar que as classes de Use Case fazem parte da camada Application Layer no Clean Architecture. A Camada de Aplicação é criada no Clean Architecture para assegurar uma separação clara de responsabilidades entre a lógica de negócios central e as dependências externas. Isto torna mais fácil manter e testar a lógica de aplicação, mantendo-a independente de qualquer estrutura ou tecnologia específica.

Presenters & Views (User Interface)

Após a construção do RegisterUrlUseCase, o próximo passo seria o desenvolvimento da camada de apresentação. No Clean Architecture, essa é a camada onde se concentra a lógica de interacção e apresentação da interface de usuário. No nosso caso, Activities, Fragments e como utilizamos a abordagem arquitetural MVVM, o ViewModel.

Eu costumo iniciar o desenvolvimento de tal camada pelos fragments, para irei discorrer aqui primeiramente pelo ViewModel...
Desta feita, falando em ViewModel, temos duas classes na aplicação que entram nessa categoria: UrlShortenerListViewModel e BaseViewModel.

BaseViewModel

Usualmente não sou muito fã de utilização de herança em orientação a objetos. Eu prefiro trabalhar com composição para que não amarremos as classes filhas com contratos estabelecidos pela classe pai. Todavia, eu usei herança aqui apenas para apresentar uma forma de abstrair uma lógica que pode ser utilizada em todas as classes ViewModel da aplicação.

O propósito da classe BaseViewModel é fornecer funcionalidades comuns a todos os ViewModels que estendem a mesma, como lidar com estados de visualização (ViewState) e executar trabalhos assíncronos com o auxílio de Coroutines.

Segue abaixo uma análise mais detalhada de tal classe:

- A classe BaseViewModel é declarada como abstrata, o que significa que não pode ser instanciada diretamente, mas outras classes podem estender essa classe para obter as funcionalidades fornecidas por ela.

- _viewState: É uma instância de MutableLiveData que armazena um objeto de ViewState. Isso permite que os estados de visualização (Loading, Success, Error) sejam atualizados e observados por outras classes.

- viewState: Uma propriedade pública imutável LiveData que expõe _viewState para que outras classes possam observar as mudanças no estado da visualização, mas não possam alterá-lo diretamente.

Vale dizer que no exemplo das variáveis _viewState e viewState, é aplicado o conceito de "backing property" para garantir o encapsulamento adequado e a separação de responsabilidades.

A "backing property" é uma técnica usada para criar uma propriedade privada para armazenar e gerenciar os dados de uma propriedade pública relacionada. Essa técnica nos permite controlar como a propriedade pública é acessada e modificada, garantindo que somente a classe que a contém possa modificar o valor da "backing property"...

- Função doAsyncWork: essa função aceita um bloco de trabalho assíncrono como um parâmetro (um lambda com uma função suspend) e executa-o usando Coroutines. A execução ocorre da seguinte maneira:
  a. O estado de visualização é atualizado para ViewState.Loading antes do início do trabalho assíncrono.
  b. O bloco de trabalho é executado dentro de um runCatching, que tenta executar o bloco e captura qualquer exceção lançada.
  c. Se o bloco de trabalho for executado com sucesso, o estado de visualização é atualizado para ViewState.Success.
  d. Se ocorrer uma exceção, o estado de visualização é atualizado para ViewState.Error com a exceção fornecida.

UrlShortenerListViewModel

Quando trabalho com a arquitetura MVVM, eu gosto de aplicar 1 viewmodel por fragment, para que possamos evitar acoplamento desnecessário caso compartilhemos viewmodels entre mais de um fragment (claro, existem exceções à regra, quando preciamos compartilhar dados entre fragments pai e filhos. Mas não é esse o caso da presente aplicação).

A classe UrlShortenerListViewModel irá tratar os dados que serão imputados no Fragment UrlShortenerListFragment e prepará-los para serem exibidos em tal view. Analisando o código de tal classe em maiores detalhes, temos:

-> A classe UrlShortenerListViewModel herda de BaseViewModel e recebe uma instância de RegisterUrlUseCase como parâmetro.

-> _shortenedUrlResultList é uma MutableLiveData, que armazena uma lista de objetos UrlShortenerItemView. Essa lista é exposta como LiveData imutável através da variável
shortenedUrlResultList.

-> _buttonSendUrlIsEnable é uma MutableLiveData que armazena um valor booleano. Essa variável é exposta como LiveData imutável através da variável buttonSendUrlIsEnable.

-> urlShortenerMutableMap é um map mutável que mapeia uma chave do tipo Int para um objeto do tipo UrlShortenerItemView.

-> validateTypedUrl(typedUrl: String) é uma function que verifica se o typedUrl não está vazio e atualiza o valor de _buttonSendUrlIsEnable.

-> registerUrl(sourceUrl: Any?) é uma função que tenta registrar uma URL, isto é, tenta enviar o URL digitado pelo usuário na view (verificando se a URL é válida) e, em caso
afirmativo, realiza uma chamada assíncrona para registrar a URL e atualizar a lista de URLs encurtadas recebidas da API.

-> refreshShortenedList(shortenedUrl: ShortenedUrl) é uma function que atualiza a lista de URLs encurtadas com base no objeto ShortenedUrl fornecido.

-> validateToRegisterUrl(sourceUrl: String) é uma função que verifica se uma URL já está na lista de URLs encurtadas.

-> addShortenedUrlItemToMap(itemView: UrlShortenerItemView) é uma extension function para MutableMap<Int, UrlShortenerItemView>, que adiciona um objeto UrlShortenerItemView ao map.

-> toDescendingSortedList() é outra extension function para MutableMap<Int, UrlShortenerItemView> que converte o map em uma lista ordenada em ordem decrescente.

Sumarizando, tal classe gerencia a lista de URLs encurtadas apresentadas no UrlShortenerListFragment, incluindo a adição de novas URLs e a atualização da lista de URLs encurtadas exibidas em tal tela. A classe também gerencia a ativação ou desativação do botão de envio de URL com base na validação da URL digitada.

Pontos importantes que devem ser observados na implemtação da UrlShortenerListViewModel:
- Eu preferi manter as extension functions addShortenedUrlItemToMap e toDescendingSortedList internas a tal classe. Isso facilita na manutenção da cobertura de testes unitários, uma vez que tornando os métods privados, não precisamos fazer testes unitários explicitos para validar a implementação dos mesmos. Isso também vai de encontro com a questão teórica que ao escrevermos testes unitários, devemos nos preocupar principalmente com o comportamento da função testada e não com sua implementação. Isso faz com que o teste unitário fique mais robusto e menos propenso a quebras caso as implementações internas da função mudem.

- toDescendingSortedList: o principal motivo da existência dessa função é manter o último item adicionado na lista de Urls sendo apresentado no primieiro índice da lista no RecyclerView. Isso nos permite apresentar a atualização da lista ao usuário sem que sejamos forçados a executar um scroll na tela quando a lista fica grande.


UrlShortenerItemView

```kotlin
data class UrlShortenerItemView(
    val originalUrl: String,
    val shortUrl: String
)
```

O data class UrlShortenerItemView foi criado para não precisarmos ter que trabalhar com mais informações do que necessário para gerenciar as informações da view UrlShortenerListFragment.

A classe UrlShortenerListFragment

Essa classe visa apresentar funcionalidades que irão trabalhar em conjunto para consumir/expor os dados ao usuário. Abaixo segue um breve detalhamento da mesma:

- setupView(): function responsável por configurar a exibição do fragment, como configurar o botão para enviar a URL, configurar o EditText da URL, configurar o Adapter da lista contida no RecyclerView e configurar a visibilidade das visualizações.

- addObservers(owner: LifecycleOwner): adiciona observes para diferentes LiveDatas no ViewModel.

- handleError(error: Error): essa função lida com erros que podem ocorrer durante o processo de encurtamento da URL. Ela exibe uma mensagem de erro usando um Toast.

- setupAdapter(): configura o Adapter do RecyclerView utilizado na tela.

- refreshList(urlShortenerViewItemList: List<UrlShortenerItemView>): atualiza a lista de itens encurtados da URL.

- setupButtonSendUrl(): configura o botão para enviar a URL. Ele desativa o botão inicialmente e define um OnClickListener para carregar os dados quando o botão é clicado.

- setupUrlEditText(): configura o EditText da URL. Ele adiciona um TextChangedListener para validar a URL digitada e um FocusChangeListener para fechar o teclado quando o EditText perde o foco.

- setupViewsVisibility(progressBarVisible: Boolean): configura a visibilidade das visualizações com base no valor do parâmetro progressBarVisible.

- loadData(param: String): responsável por carregar os dados, ou seja, registrar a URL, utilizando o método registerUrl do ViewModel.


UrlShortenerListAdapter

A classe UrlShortenerListAdapter é um Adapter personalizado para uma lista de itens que representam URLs encurtadas. Essa classe estende a classe ListAdapter e é projetada para gerenciar a exibição de uma lista de objetos UrlShortenerItemView.


UrlComponent

Custom view que estende a classe ConstraintLayout. Essa classe é um componente personalizado da interface do usuário que exibe informações sobre uma URL. Pode ser utilizado para mostrar a URL original e a URL encurtada na aplicação Android.

Alguns detalhes da implementação de tal custom view:

- O construtor @JvmOverloads permite que a classe UrlComponent seja utilizada em Java com sobrecarga de construtores. Ele recebe três parâmetros: um objeto context (necessário para acessar recursos do sistema e da aplicação), um objeto attrs do tipo AttributeSet? (atributos de estilo XML para o componente, que é opcional) e um defStyleAttr do tipo Int (atributo de estilo padrão, com valor padrão 0).

- A variável binding é inicializada usando a classe UrlshortenerUrlComponentBinding para inflar o layout do componente personalizado.

- O bloco init é usado para inicializar o componente. Dentro desse bloco, os atributos XML personalizados são processados. Se o objeto attrs não for nulo, o código chama a função obtainStyledAttributes() para recuperar os valores dos atributos personalizados definidos em R.styleable.UrlShortenedLabelStyleable. O valor do atributo urlLabel é definido como o texto do elemento titleItem no layout do componente. Por fim, a função recycle() é chamada para liberar os recursos associados aos mesmos.

- A função setUrlContent() é usada para definir o conteúdo da URL exibido no componente. Ela recebe uma string urlContent como parâmetro e define o texto do elemento contentItem no layout do componente.


UrlShortenerMainFragment

O UrlShortenerMainFragment não é necessário na aplicação. Está aqui apenas para exemplificar um ponto de entrada, sendo uma interface com outros módulos que chamam o módulo urlshortener.

Koin

A ferramenta de injeção de dependência utilizada foi o Koin (https://insert-koin.io/). Sabemos que o Koin não é em si um injetor de dependência, mas um Service Locator. Mas isso é outra história que não será detalhada agora.


Uma breve explicação sobre um dos módulos contidos no diretório de injeção de dependências:

Arquivo DataModule

```kotlin
object DataModule {
    val module = module {
        single {
            ApiClientBuilder.createServiceApi(
                serviceClass = UrlShortenerService::class.java,
                baseUrl = BuildConfig.BASE_URL
            )
        }
        factory { UrlShortenerRemoteRepository(urlShortenerService = get()) } bind UrlShortenerRepository::class
    }
}
```

O código apresentado é uma parte importante da configuração da aplicação, pois define a organização e a criação de instâncias de objetos relacionados aos dados e à comunicação com a API.

- A variável module é definida como uma instância do módulo Koin. Como já mencionado anteriormente, o Koin foi escolhido como uma unidade lógica para organizar a configuração de injeção de dependências.

- A função single é utilizada para criar uma instância singleton de UrlShortenerService. A função ApiClientBuilder.createServiceApi() é chamada para criar a instância do serviço, passando UrlShortenerService::class.java como a classe do serviço e BuildConfig.BASE_URL como a URL base para a API.

- A função factory é utilizada para criar instâncias de UrlShortenerRemoteRepository. Cada vez que a função get() for chamada para obter uma instância dessa classe, uma nova instância será criada. A instância do serviço UrlShortenerService é recuperada utilizando a função get() e passada como parâmetro para o construtor do repositório.

- A função bind é usada para associar a implementação UrlShortenerRemoteRepository à interface UrlShortenerRepository. Isso permite que as classes solicitem uma instância de UrlShortenerRepository, mas recebam uma instância de UrlShortenerRemoteRepository.
