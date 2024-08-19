# CCCrypt

*CCCrypt is a basilar Swing-based Java application for encrypting and decrypting messages*.

It provides only one dynamic algorithm that varies based on the day of the year. The project was created for personal practice purposes only.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled `.class` files will be generated in the `bin` folder by default.

## Requirements

- Java 8 or higher
- Standard Java libraries (Swing, AWT, etc.)

It is strongly recommended to use `Extension Pack for Java` for Visual Studio Code (ID: `vscjava.vscode-java-pack`).

## Installation
#### Clone the repository:

```bash
git clone https://github.com/your-username/cccrypt.git
```

#### Navigate to the project folder:

```bash
cd cccrypt
```

## Compile JAR and open

Before compiling the project, you need to verify the files, then run:

```shell
Get-ChildItem -Recurse bin
jar tf build/cccrypt.jar
```

To compile the project into a single JAR file you need to run the following commands step by step (assuming you are in the main root):

```shell
javac -d bin (Get-ChildItem -Recurse -Filter *.java).FullName
jar cvfm build/cccrypt.jar src/META-INF/MANIFEST.MF -C bin .
```

or you can simply run tasks in `.vscode/tasks.json` (CTRL + SHIFT + B) with the following order:
1. **Compile and Build JAR**
2. **Create JAR**

To open the file use the command:

```shell
java -jar build/cccrypt.jar
```

or right click on the `cccrypt.jar` file and open with Java(TM) Platform SE Binary.

## Usage

### Encryption:

- Enter the message in the text field.
- Click the "Encrypt" button to encrypt the message.

The encrypted message will be displayed in the output section.

### Decryption:

- Enter the encrypted message in the text field.
- Click the "Decrypt" button to decrypt the message.

The decrypted message will be displayed in the output section.

### File Management:

- Use the "Save" and "Load" buttons to save and load messages from files.
- When performing an operation, click the "Copy" button to copy to the clipboard.

### Operation History:

View recent operations in the dedicated section of the GUI.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Contributions

If you want to contribute to the project, follow these steps:

1. Fork the repository
2. Create a new branch (git checkout -b feature/feature-name)
3. Add your changes and commit (git commit -am 'Add new feature')
4. Push the branch (git push origin feature/feature-name)
5. Open a Pull Request

## License

This project is licensed under the MIT License. See the LICENSE file for details.