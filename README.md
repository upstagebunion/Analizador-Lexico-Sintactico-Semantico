<p align="center">
  <img src="https://img.shields.io/badge/Language-Java17-orange?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Built-From%20Scratch-blue?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/No-Libraries-red?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Compiler-TortuLang-green?style=for-the-badge"/>
</p>

<h1 align="center">🐢 TortuLang</h1>
<h3 align="center">A compiler-like analyzer made 100% from scratch in Java — no libraries, no shortcuts.</h3>

<p align="center">
  <em>"Slow, steady... and absolutely complete."</em>
</p>

---

## 🔍 Lexical, Syntactic & Semantic Analyzer in Java (No Libraries Used)

A complete compiler-like system written entirely in **pure Java**, without using any external libraries, Java collections (like `List` or `ArrayList`), or even built-in utilities like `split()` or `substring()`. This project features full **lexical**, **syntactic**, and **semantic analysis**, along with **postfix notation generation** and **intermediate code (codeP)** creation.

> 💡 Originally developed as a university project for "Languages and Automata II".

---

## 🚀 Overview

This system reads a source program written in a custom language and performs the following steps:

1. **Lexical Analysis** – Scans the input character by character to generate tokens.
2. **Syntactic Analysis** – Validates grammar and structure.
3. **Semantic Analysis** – Checks for correct types, declarations, assignments.
4. **Symbol Table Construction** – Stores identifiers, types, and values.
5. **Postfix Code Generation** – Converts the program into postfix (reverse Polish) notation.
6. **Intermediate Code (CodeP)** – Generates simplified pseudo-code.
7. **File Output** – Saves both postfix and codeP to `.txt` files.

---

## 🧠 Key Features

- 🧾 **Fully custom tokenizer and parser** without using Java's built-in data structures.
- 📦 **Manual linked list implementation** for managing tokens, errors, and symbols.
- 📚 **Postfix notation (RPN)** generation for expression evaluation.
- 🧮 **Intermediate code execution model** (codeP) based on stack operations.
- 💾 **File output** for both intermediate representations.
- 🛠️ Built using:
  - Java SE Runtime Environment 17.0.6+9-LTS-190
  - Visual Studio Code
  - Windows 10 Professional

---

## 🗂️ Project Flow (Main Class)

```java
public static void main(String[] args) {
    // 1. Read input source code from file
    LectorArchivo la = new LectorArchivo("src/Docs/programa.txt");
    
    // 2. Perform lexical analysis
    AnalizadorLexico al = la.al;
    
    // 3. Display token tables
    new Display(al.lsimbolos, "Non-repeating Symbols Table", false);
    new Display(al.lerrores, "Errors Table", false);
    new Display(al.lpalReservadas, "Reserved Words Table", false);
    new Display(al.ltokens, "All Tokens Table", false);

    // 4. Build symbol table
    TablaSimbolos ts = new TablaSimbolos(al.ltokens);
    new Display(ts.simbolos, "Symbol Table", true);

    // 5. Perform semantic analysis
    new AnalizadorSemantico(ts.tokens, ts.simbolos);

    // 6. Generate Postfix code
    Posfija pf = new Posfija(ts.tokens);
    new SavePf(pf.pf, "src/Docs/posfija.txt");

    // 7. Generate intermediate code (codeP)
    GenCodP gcp = new GenCodP(pf.pf);
    new SavePf(gcp.codigoP, "src/Docs/codigoP.txt");
}
```
## 🧪 Sample Input Programs
Here are a couple of test programs the system is able to analyze and process:

### ✅ Sample 1 – Mixed Arithmetic & I/O
```
Inicio
    Entero multiple, num;
    Real numero, res;

    Leer (num);

    multiple = 10010 + 1001; 
    numero = numero + 123.5 * 101.23; 
    res = numero - 23.32; 

    Escribir(res);
    Escribir(num);
Fin
```

### ✅ Sample 2 – Expressions & Parentheses
```
Inicio
    Real cuenta, numero, resultado;
    Real valor;
    Real f, a, b, c, d, e;

    Leer (valor);
    f = a * b + (c / d) - e;
    cuenta = 23.0 + (numero - valor);
    numero = cuenta / 123.99;
    resultado = numero + cuenta;

    Escribir (resultado);
Fin
```
## 🧠 Educational Purpose
This project was created as part of the Languages and Automata II course at Instituto Tecnológico de León, by:

- Francisco García Solís

The goal was to build a complete analysis system from scratch, to fully understand the inner workings of compilers and interpreters — including custom memory structures, parsing, and intermediate code generation.

## 📌 Tech Details
- Language: Java (no libraries)
- Compiler Techniques: Recursive descent parsing, custom lexical scanning, postfix evaluation
- System Used:
  - AMD Ryzen 5 3400G, 3GHz
  - 16 GB RAM
  - Windows 10 (64-bit)
 
## 📎 Notes
This system is a great demonstration of:
- Compiler theory in practice
- Working with low-level logic and memory management
- Creating a full data pipeline: input → analysis → output

For production-ready compiler development, you’d typically use tools like:
- ANTLR, Flex/Bison, JavaCC, or LLVM
But this project proves deep understanding by building everything manually.

- ![Java](https://img.shields.io/badge/Java-17-orange)
- ![Made From Scratch](https://img.shields.io/badge/Built-From%20Scratch-blue)
