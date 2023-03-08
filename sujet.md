# Static analysis

## Instructions

Some of the exercises in this practical session use PMD, JavaParser or require a full project as input.

To obtain and use PMD, consult the instructions given in https://pmd.github.io/pmd/pmd_userdocs_installation.html

The folder [javaparser-starter](code/javaparser-starter) containsthe code of an application that uses JavaParser to print all public classes and public methods from a given project. You can use this example as a starting point for all exercises using JavaParser.

We recommend you use the following projects as input for the exercises:

- [Apache Commons Collections](https://github.com/apache/commons-collections)
- [Apache Commons CLI](https://github.com/apache/commons-cli)
- [Apache Commons Math](https://github.com/apache/commons-math)
- [Apache Commons Lang](https://github.com/apache/commons-lang)

Feel free to use any other project you want.

## Exercises

1. [TCC *vs* LCC](exercises/tcc-vs-lcc.md)

2. [Using PMD](exercises/using-pmd.md)

3. [Extending PMD](exercises/extending-pmd.md)

4. [No getter!](exercises/no-getter.md)

5. [Class cohesion with JavaParser](exercises/jp-tcc.md) (bonus)

6. [Cyclomatic Complexity with JavaParser](exercises/jp-cc.md) (bonus)
