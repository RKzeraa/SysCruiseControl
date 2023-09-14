# SysCruiseControl

O projeto deve simular sensores de velocidade de um carro(Ou seja uma velocidade variável), para que o Cruise Control possa entrar em ação, dessa forma ele ficará responsável por controlar a velocidade definida pelo motorista dando controle para o Cruise Control manter a velocidade desejada(Acelerando e Freando o carro).

- [Instalação](#Instalação)
- [Teclas de Uso](#teclas-de-uso)


## Tecnologias Usadas

- Java SE Development Kit 17.0.7
- Eclipse IDE 2023‑06
- OpenJFX 17.0.8

## Instalação

É necessario adicionar o OpenJFX ao projeto, para usar o JavaFX (OpenJFX) no Eclipse:

##### Instalar o Eclipse:
- Se não tiver o Eclipse instalado, faça o download e instale a versão mais recente do Eclipse IDE para Java Developers a partir do site oficial: [Eclipse Downloads](https://www.eclipse.org/downloads/).

##### Instale o JDK (Java Development Kit):
- Verifique se você tem o JDK instalado no seu sistema. Você pode baixar o JDK [Java SE Development Kit 17.0.7](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) no site da Oracle ou usar uma distribuição como o OpenJDK.

##### Instale e Adicione a Biblioteca OpenJFX ao Projeto:

- Se não tiver o OpenJFX instalado, faça o download e instale [JavaFX](https://gluonhq.com/products/javafx/). <sub>(Lembrando que a versão deve ser compativel com a versão do seu JDK)</sub>
- Para adicionar clique com o botão direito do mouse no projeto no "Project Explorer" (Explorador de Projetos).
- Escolha "Build Path" (Caminho de Build) > "Configure Build Path" (Configurar Caminho de Build).
- Na guia "Libraries" (Bibliotecas), clique em "Classpath" > "Add Library" (Adicionar Biblioteca).
- Selecione "User Library" (Biblioteca de Usuário) e clique em "Next" (Avançar).
- Clique em "User Libraries" (Bibliotecas de Usuário) > "New" (Novo) e dê um nome para a biblioteca, como "JavaFX".
- Selecione a biblioteca "JavaFX" e clique em "Add External JARs" (Adicionar JARs Externos).
- Navegue até o diretório onde o OpenJFX foi instalado (ou onde você o baixou) e selecione os JARs do OpenJFX (por exemplo, javafx-base.jar, javafx-controls.jar, etc.).
- Clique em "Open" (Abrir) para adicionar os JARs à biblioteca.
- Clique em "Finish" (Concluir) para criar a biblioteca "JavaFX".
- Clique em "Apply and Close" (Aplicar e Fechar) para salvar as configurações.

## Teclas de Uso

- __W__ para Acelerar
- __S__ para Frear
- __Q__ para simular o desligar do carro
- __C__ para Ativar/Desativar o Cruise Control
- __SETA PARA CIMA__ para Aumentar a Velocidade do Cruise Control
- __SETA PARA BAIXO__ para Diminuir a Velocidade do Cruise Control  

